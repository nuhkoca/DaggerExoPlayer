package com.nuhkoca.myapplication.data.remote.player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Request {

    @Expose
    @SerializedName("files")
    private Files files;

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    @NonNull
    @Override
    public String toString() {
        return "Request{" +
                "files=" + files +
                '}';
    }
}
