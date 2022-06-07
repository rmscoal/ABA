package com.example.aba.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String? = null,
    var token: String? =null,
    var email: String? = null,
    var password: String? = null,
    var achv_id: Int? = null,
    var latMenyusunKatalvl1: Int? = null,
    var latMenyusunKatalvl2: Int? = null,
    var latMenyusunKatalvl3: Int? = null,
    var latMengejaHuruflv1: ArrayList<String>? = null,
    var latMengejaHuruflv2: ArrayList<String>? = null,
    var latMengejaHuruflv3: ArrayList<String>? = null,
    var eksplor_huruf: ArrayList<String>? = null,
    var eksplor_angka: ArrayList<String>? = null,

//    var eksplor_huruf: Map<String,Boolean> = mapOf<String,Boolean>(
//        "a" to false,
//        "b" to false,
//        "c" to false,
//        "d" to false,
//        "e" to false,
//        "f" to false,
//        "g" to false,
//        "h" to false,
//        "i" to false,
//        "j" to false,
//        "k" to false,
//        "l" to false,
//        "m" to false,
//        "n" to false,
//        "o" to false,
//        "p" to false,
//        "q" to false,
//        "r" to false,
//        "s" to false,
//        "t" to false,
//        "u" to false,
//        "v" to false,
//        "w" to false,
//        "x" to false,
//        "y" to false,
//        "z" to false
//    ),
//    var eksplor_angka: Map<String,Boolean> = mapOf<String,Boolean>(
//        "1" to false,
//        "2" to false,
//        "3" to false,
//        "4" to false,
//        "5" to false,
//        "6" to false,
//        "7" to false,
//        "8" to false,
//        "9" to false
//    ),

) : Parcelable