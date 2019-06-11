package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.SerializedName

data class Progressive(
    @SerializedName("url")
    var url: String? = null
)
