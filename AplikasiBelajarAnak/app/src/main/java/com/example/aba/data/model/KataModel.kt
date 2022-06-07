package com.example.aba.data.model

import com.google.gson.annotations.SerializedName

data class KataModel(

    @field:SerializedName("nilai")
    val nilai: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("lema")
    val lema: String
)
