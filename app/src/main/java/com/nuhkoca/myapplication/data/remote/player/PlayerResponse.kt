package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("request")
    var request: Request? = null
)
