package com.capstone.goloak.ui.point

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.ProfileListPointHistory
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.PointHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PointViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getId(): LiveData<String> {
        return pref.getIdUserSetting().asLiveData()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _data = MutableLiveData<ArrayList<ProfileListPointHistory>?>()
    val data : LiveData<ArrayList<ProfileListPointHistory>?> = _data

    fun getPointHistory(idUser: String) {
        _loading.postValue(true)
        val client = ApiConfig.getApiServices().getPointHistory(idUser)
        client.enqueue(object : Callback<PointHistoryResponse> {
            override fun onResponse(call: Call<PointHistoryResponse>, response: Response<PointHistoryResponse>) {
                _loading.postValue(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success"){
                    _data.postValue(responseBody.listPointHistory)
                }else{
                    _data.postValue(responseBody?.listPointHistory)
                    Log.e(TAG, "message : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<PointHistoryResponse>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object{
        const val TAG = "PointViewModel"
    }
}