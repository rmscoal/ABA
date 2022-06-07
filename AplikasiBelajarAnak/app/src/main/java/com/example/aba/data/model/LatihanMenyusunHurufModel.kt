package com.example.aba.data.model

import com.google.gson.annotations.SerializedName

data class LatihanMenyusunHurufModel(

    @field:SerializedName("level1")
	val level1: ArrayList<KataModel>,

    @field:SerializedName("level3")
	val level3: ArrayList<KataModel>,

    @field:SerializedName("level2")
	val level2: ArrayList<KataModel>
)

