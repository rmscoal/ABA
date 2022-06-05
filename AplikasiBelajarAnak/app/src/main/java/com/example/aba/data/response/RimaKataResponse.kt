package com.example.aba.data.response

import com.google.gson.annotations.SerializedName

data class RimaKataResponse(

	@field:SerializedName("data")
	val data: Kategori,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class MudahItem(

	@field:SerializedName("nilai")
	val nilai: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("lema")
	val lema: String,
)

data class SulitItem(

	@field:SerializedName("nilai")
	val nilai: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("lema")
	val lema: String
)

data class SedangItem(

	@field:SerializedName("nilai")
	val nilai: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("lema")
	val lema: String
)

data class KataModel2(

	@field:SerializedName("nilai")
	val nilai: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("lema")
	val lema: String
)

data class Kategori(

	@field:SerializedName("sedang")
	val sedang: ArrayList<KataModel2>,

	@field:SerializedName("mudah")
	val mudah: ArrayList<KataModel2>,

	@field:SerializedName("sulit")
	val sulit: ArrayList<KataModel2>
)
