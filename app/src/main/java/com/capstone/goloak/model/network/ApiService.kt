package com.capstone.goloak.model.network

import com.capstone.goloak.model.response.LoginResponse
import com.capstone.goloak.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/signup")
    fun register(
        @Field("fullname") fullname: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone_number") phonenumber : String,
        @Field("address") address: String,
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}