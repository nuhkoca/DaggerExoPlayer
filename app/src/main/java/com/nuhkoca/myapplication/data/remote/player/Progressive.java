package com.nuhkoca.myapplication.data.remote.player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Progressive {

    @Expose
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Progressive{" +
                "url='" + url + '\'' +
                '}';
    }
}
