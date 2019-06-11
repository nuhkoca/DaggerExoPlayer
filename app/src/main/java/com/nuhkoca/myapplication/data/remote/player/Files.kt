package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.SerializedName

data class Files(
    @SerializedName("progressive")
    var progressive: List<Progressive>? = null
)
