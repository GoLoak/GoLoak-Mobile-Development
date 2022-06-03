package com.capstone.goloak.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.HomeListHistory
import com.capstone.goloak.model.HomeListTrash
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.HistoryResponse
import com.capstone.goloak.model.response.HomeResponse
import com.capstone.goloak.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val pref : SettingPreferences) : ViewModel() {
    fun getId(): LiveData<String> {
        return pref.getIdUserSetting().asLiveData()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _dataListHistory = MutableLiveData<ArrayList<HomeListHistory>>()
    val dataListHistory : LiveData<ArrayList<HomeListHistory>> = _dataListHistory

    fun getListHistory(idUser: String) {
        _loading.postValue(true)
        val client = ApiConfig.getApiServices().getHistory(idUser)
        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                _loading.postValue(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success") _dataListHistory.postValue(responseBody.listHistory)
                else Log.e(TAG, "message : ${responseBody?.message}")
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "HistoryViewModel"
    }
}