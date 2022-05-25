package com.example.aba.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("eksplor_huruf")
	val eksplorHuruf: String,

	@field:SerializedName("eksplor_angka")
	val eksplorAngka: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("achv_id")
	val achvId: Int,

	@field:SerializedName("latMenyusunKatalvl1")
	val latMenyusunKatalvl1: Int,

	@field:SerializedName("nama_user")
	val namaUser: String,

	@field:SerializedName("latMenyusunKatalvl3")
	val latMenyusunKatalvl3: Int,

	@field:SerializedName("latMengejaHuruflvl3")
	val latMengejaHuruflvl3: Int,

	@field:SerializedName("latMenyusunKatalvl2")
	val latMenyusunKatalvl2: Int,

	@field:SerializedName("latMengejaHuruflvl2")
	val latMengejaHuruflvl2: Int,

	@field:SerializedName("latMengejaHuruflvl1")
	val latMengejaHuruflvl1: Int
)
