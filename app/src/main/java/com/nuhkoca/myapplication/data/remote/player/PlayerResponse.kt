package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlayerResponse {

    @Expose
    @SerializedName("request")
    var request: Request? = null

    override fun toString(): String {
        return "PlayerResponse{" +
                "request=" + request +
                '}'.toString()
    }
}
