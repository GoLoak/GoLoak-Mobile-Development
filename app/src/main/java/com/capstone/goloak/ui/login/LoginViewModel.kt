package com.capstone.goloak.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref : SettingPreferences) : ViewModel() {

    //ini untuk datastore
    fun saveSesi(sesi: Boolean){
        viewModelScope.launch {
            pref.saveSesiSetting(sesi)
        }
    }

    fun saveToken(token: String){
        viewModelScope.launch {
            pref.saveTokenSetting(token)
        }
    }

    fun saveIdUser(idUser: String){
        viewModelScope.launch {
            pref.saveIdUserSetting(idUser)
        }
    }

    private val _saveUser = MutableLiveData<Boolean>()
    val saveUser : LiveData<Boolean> = _saveUser

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _dataToken = MutableLiveData<String?>()
    val dataToken : LiveData<String?> = _dataToken

    private val _dataId = MutableLiveData<String?>()
    val dataId : LiveData<String?> = _dataId

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun login(email: String, password: String){
        _loading.value = true
        val client = ApiConfig.getApiServices().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _loading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success"){
                    _dataToken.value = responseBody.token
                    _dataId.value = responseBody.id
                    _message.value = "Login successful!"
                    _saveUser.value = true
                }else{
                    _message.value = "Make sure the email and password is correct."
                    _saveUser.value = false
                    Log.e(TAG, "message : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _saveUser.value = false
                _loading.value = false
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object{
        const val TAG = "LoginViewModel"
    }
}