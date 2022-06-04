package com.example.aba.data.api

import com.example.aba.data.database.UploadRecordingResponse
import com.example.aba.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiService {

    @GET("users/")
    fun getDataUser(
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    @Multipart
    @POST("predictions/a/")
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
