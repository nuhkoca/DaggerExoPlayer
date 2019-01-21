package com.nuhkoca.myapplication.data.remote.player;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

public class Files {

    @Expose
    @SerializedName("progressive")
    private List<Progressive> progressive;

    public List<Progressive> getProgressive() {
        return progressive;
    }

    public void setProgressive(List<Progressive> progressive) {
        this.progressive = progressive;
    }

    @NonNull
    @Override
    public String toString() {
        return "Files{" +
                "progressive=" + progressive +
                '}';
    }
}
