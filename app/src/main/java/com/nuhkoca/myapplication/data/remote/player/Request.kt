package com.nuhkoca.myapplication.data.remote.player

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {

    @Expose
    @SerializedName("files")
    var files: Files? = null

    override fun toString(): String {
        return "Request{" +
                "files=" + files +
                '}'.toString()
    }
}
