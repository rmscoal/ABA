package com.example.aba.data.preferences

import com.beust.klaxon.Json

data class EksplorAngka(
    @Json(index = 1) var nol: Boolean,
    @Json(index = 2) var satu: Boolean,
    @Json(index = 3) var dua: Boolean,
    @Json(index = 4) var tiga: Boolean,
    @Json(index = 5) var empat: Boolean,
    @Json(index = 6) var lima: Boolean,
    @Json(index = 7) var enam: Boolean,
    @Json(index = 8) var tujuh: Boolean,
    @Json(index = 9) var delapan: Boolean,
    @Json(index = 10) var sembilan: Boolean,
    @Json(index = 11) var sepuluh: Boolean,
    )
