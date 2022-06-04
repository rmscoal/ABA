package com.example.aba.data.api

import com.example.aba.data.database.UploadRecordingResponse
import com.example.aba.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/")
    fun getDataUser(
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    @Multipart
    @POST("predictions/m/")
    fun uploadRecording(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): Call<UploadRecordingResponse>


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
