package com.example.aba.data.response

import com.google.gson.annotations.SerializedName

data class KataRecordingResponse(

	@field:SerializedName("result")
	val result: Double? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
