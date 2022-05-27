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

class LoginViewModel (private val pref : SettingPreferences) : ViewModel() {

    //ini untuk datastore
    fun saveSessi(sesi: Boolean){
        viewModelScope.launch {
            pref.saveSesiSetting(sesi)
        }
    }

    fun saveToken(token: String){
        viewModelScope.launch {
            pref.saveTokenSetting(token)
        }
    }


    private val _saveUser = MutableLiveData<Boolean>()
    val saveUser : LiveData<Boolean> = _saveUser

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _data = MutableLiveData<String>()
    val data : LiveData<String> = _data

    fun login(email: String, password: String){
        _loading.value = true
        val client = ApiConfig.getApiServices().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _loading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _data.value = responseBody.token!!
                }else{
                    _loading.value = false
                    Log.e(TAG, "message : ${response.errorBody().toString()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object{
        const val TAG = "LoginViewModel"
    }
}