package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Progressive {

    @Expose
    @SerializedName("url")
    var url: String? = null

    override fun toString(): String {
        return "Progressive{" +
                "url='" + url + '\''.toString() +
                '}'.toString()
    }
}
