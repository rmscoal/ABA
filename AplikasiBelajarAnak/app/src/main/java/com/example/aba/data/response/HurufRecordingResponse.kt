package com.example.aba.data.response

import com.google.gson.annotations.SerializedName

data class HurufRecordingResponse(

	@field:SerializedName("result")
	val result: Float? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updated")
	val updated: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null
)
