package com.example.aba.data.database

import com.google.gson.annotations.SerializedName

data class UploadRecordingResponse(

	@field:SerializedName("result")
	val result: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updated")
	val updated: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null
)
