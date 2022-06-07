package com.example.aba.data.response

import com.example.aba.data.model.KataModel
import com.google.gson.annotations.SerializedName

data class RimaKataResponse(

	@field:SerializedName("data")
	val data: Kategori,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Kategori(

	@field:SerializedName("sedang")
	val sedang: ArrayList<KataModel>,

	@field:SerializedName("mudah")
	val mudah: ArrayList<KataModel>,

	@field:SerializedName("sulit")
	val sulit: ArrayList<KataModel>
)
