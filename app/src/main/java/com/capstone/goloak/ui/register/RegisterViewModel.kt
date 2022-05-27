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

    fun register( fullname: String,password: String, email: String,  phone_number: String, address: String){
        _loading.value = true
        val client = ApiConfig.getApiServices().register( fullname, password, email, phone_number, address)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _loading.value = false
                val responseBoby = response.body()
                if (response.isSuccessful && responseBoby != null){
                    _message.value = responseBoby.message!!
                }else{
                    _loading.value = false
                    Log.e(TAG, "error : ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "message : ${t.message.toString()}")
            }

        })

    }

    companion object{
        const val TAG = "RegisterViewModel"
    }
}