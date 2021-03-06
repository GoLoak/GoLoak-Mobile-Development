package com.capstone.goloak.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val pref : SettingPreferences) : ViewModel() {
    fun getId(): LiveData<String> {
        return pref.getIdUserSetting().asLiveData()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading
    
    private val _data = MutableLiveData<ProfileResponse?>()
    val data : LiveData<ProfileResponse?> = _data
    
    fun getUserProfile(idUser: String) {
        _loading.postValue(true)
        val client = ApiConfig.getApiServices().getProfile(idUser)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                _loading.postValue(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success"){
                    _data.postValue(responseBody)
                } else {
                    Log.e(TAG, "message : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object{
        const val TAG = "ProfileViewModel"
    }
}