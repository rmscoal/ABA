package com.example.aba.data.api

import com.example.aba.data.response.RimaKataResponse
import com.example.aba.data.response.HurufRecordingResponse
import com.example.aba.data.response.KataRecordingResponse
import com.example.aba.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/")
    fun getDataUser(
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    //belajar kata
    @GET("rimakatawords/")
    fun cariRimaKata(
        @Header("Authorization") token: String,
    ): Call<RimaKataResponse>

    //latihan mengeja huruf
    @Multipart
    @POST("predictions/huruf/{recordhuruf}")
    fun hurufRecording(
        @Header("Authorization") token: String,
        @Path("recordhuruf") recordhuruf: String,
        @Part file: MultipartBody.Part,
    ): Call<HurufRecordingResponse>

    //latihan mengeja kata
    @Multipart
    @POST("predictions/kata/{recordkata}")
    fun kataRecording(
        @Header("Authorization") token: String,
        @Path("recordkata") recordkata: String,
        @Part file: MultipartBody.Part,
    ): Call<KataRecordingResponse>

}
