package com.example.aba.data.api

import com.example.aba.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/")
    fun getDataUser(
        @Header("Authorization") token: String,
    ): Call<UserResponse>
//
//    @FormUrlEncoded
//    @POST("v1/register")
//    fun registerUser(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Call<RegisterResponse>
//
//    @Multipart
//    @POST("v1/stories")
//    fun uploadStory(
//        @Header("Authorization") token: String,
//        @Part("description") description: RequestBody,
//        @Part file: MultipartBody.Part,
//    ): Call<AddNewStoryResponse>

//    @GET("users")
//    suspend fun getUsers(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int,
//    ): ListStoryResponse

//    @GET("stories")
//    fun getLocation(
//        @Header("Authorization") token: String,
//        @Query("location") location: Int? = 0
//    ): Call<ListStoryResponse>
}
