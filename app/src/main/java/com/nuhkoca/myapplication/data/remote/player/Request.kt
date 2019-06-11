package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("files")
    var files: Files? = null
)
