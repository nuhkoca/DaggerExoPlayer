package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Files {

    @Expose
    @SerializedName("progressive")
    var progressive: List<Progressive>? = null

    override fun toString(): String {
        return "Files{" +
                "progressive=" + progressive +
                '}'.toString()
    }
}
