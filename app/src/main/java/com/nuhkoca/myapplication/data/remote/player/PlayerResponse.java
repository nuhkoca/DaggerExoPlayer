package com.nuhkoca.myapplication.data.remote.player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class PlayerResponse {

    @Expose
    @SerializedName("request")
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlayerResponse{" +
                "request=" + request +
                '}';
    }
}
