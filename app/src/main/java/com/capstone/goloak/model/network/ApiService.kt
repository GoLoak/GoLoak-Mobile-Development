package com.capstone.goloak.model.network

import com.capstone.goloak.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("api/selling/{id}")
    fun postSellTrash(
        @Path("id") id: String,
        @Part("total_trash") totalTrash: Int,
        @Part("total_point") totalPoint: Int,
        @Part("nameTrash") nameTrash: RequestBody,
        @Part file: MultipartBody.Part
    ) : Call<SellingPostResponse>

    @FormUrlEncoded
    @POST("auth/signup")
    fun register(
        @Field("fullname") fullName: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber : String,
        @Field("address") address: String
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