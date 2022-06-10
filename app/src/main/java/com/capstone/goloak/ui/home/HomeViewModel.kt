package com.capstone.goloak.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.HomeListTrash
import com.capstone.goloak.model.response.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref : SettingPreferences) : ViewModel() {

    fun getId(): LiveData<String> {
        return pref.getIdUserSetting().asLiveData()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _dataFullName = MutableLiveData<String?>()
    val dataFullName : LiveData<String?> = _dataFullName

    private val _dataPoint = MutableLiveData<Int?>()
    val dataPoint : LiveData<Int?> = _dataPoint

    private val _dataListTrash = MutableLiveData<ArrayList<HomeListTrash>>()
    val dataListTrash : LiveData<ArrayList<HomeListTrash>> = _dataListTrash

    fun getUserHome(idUser: String) {
        _loading.postValue(true)
        val client = ApiConfig.getApiServices().getHome(idUser)
        client.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                _loading.postValue(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success"){
                    _dataFullName.postValue(responseBody.fullName)
                    _dataPoint.postValue(responseBody.point)
                    _dataListTrash.postValue(responseBody.listTrash)
                } else {
                    Log.e(TAG, "message : ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                _loading.postValue(false)
                Log.e(TAG, "error : ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}