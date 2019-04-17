package com.example.eko8757.footballclubfinal.model

import com.google.gson.annotations.SerializedName

data class League(
        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strLeague")
        var league: String? = null
)