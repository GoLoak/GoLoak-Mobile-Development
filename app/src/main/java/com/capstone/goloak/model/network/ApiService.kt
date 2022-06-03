package com.capstone.goloak.model.network

import com.capstone.goloak.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("auth/signup")
    fun register(
        @Field("fullname") fullName: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber : String,
        @Field("address") address: String,
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("api/home/{id}")
    fun getHome(
        @Path("id") id: String
    ): Call<HomeResponse>

    @GET("api/selling/{id}")
    fun getHistory(
        @Path("id") id: String
    ): Call<HistoryResponse>

    @GET("api/profile/{id}")
    fun getProfile(
        @Path("id") id: String
    ): Call<ProfileResponse>

    @GET("api/point/{id}")
    fun getPointHistory(
        @Path("id") id: String
    ): Call<PointHistoryResponse>
}