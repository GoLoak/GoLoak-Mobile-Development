package com.capstone.goloak.ui.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.SellingPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel(private val pref : SettingPreferences) : ViewModel() {
    fun getId(): LiveData<String> {
        return pref.getIdUserSetting().asLiveData()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _data = MutableLiveData<String>()
    val data : LiveData<String> = _data

    fun postSellTrash(idUser: String, totalTrash: Int, totalPoint: Int, nameTrash: RequestBody, image: MultipartBody.Part) {
        _loading.postValue(true)
        val client = ApiConfig.getApiServices().postSellTrash(idUser, totalTrash, totalPoint, nameTrash, image)
        client.enqueue(object : Callback<SellingPostResponse> {
            override fun onResponse(
                call: Call<SellingPostResponse>,
                response: Response<SellingPostResponse>
            ) {
                _loading.postValue(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success") _data.postValue(responseBody.message)
                else Log.e(TAG, "message : ${responseBody?.message}")
            }

            override fun onFailure(call: Call<SellingPostResponse>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "CameraViewModel"
    }
}