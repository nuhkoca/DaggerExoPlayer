package com.nuhkoca.myapplication.data.remote.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.myapplication.BR;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Paging extends BaseObservable {

    @Expose
    @SerializedName("next")
    private String next;

    @Bindable
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
        notifyPropertyChanged(BR.next);
    }

    @NonNull
    @Override
    public String toString() {
        return "Paging{" +
                "next='" + next + '\'' +
                '}';
    }
}
