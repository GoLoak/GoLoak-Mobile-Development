package com.capstone.goloak.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.goloak.model.network.ApiConfig
import com.capstone.goloak.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun register(fullName: String, password: String, email: String,  phoneNumber: String, address: String){
        _loading.value = true
        val client = ApiConfig.getApiServices().register(fullName, password, email, phoneNumber, address)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _loading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "ok"){
                    _message.value = "Registration successful!"
                    _error.value = false
                } else {
                    _message.value = responseBody?.message.toString()
                    _loading.value = false
                    _error.value = true
                    Log.e(TAG, "error : ${responseBody?.message}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _loading.value = false
                _error.value = true
                Log.e(TAG, "message : ${t.message.toString()}")
            }

        })

    }

    companion object{
        const val TAG = "RegisterViewModel"
    }
}