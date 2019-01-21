package com.nuhkoca.myapplication.data.remote.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.myapplication.BR;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class VideoWrapper extends BaseObservable {

    @Expose
    @SerializedName("paging")
    private Paging paging;
    @Expose
    @SerializedName("data")
    private List<VideoResponse> data;

    @Bindable
    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
        notifyPropertyChanged(BR.paging);
    }

    @Bindable
    public List<VideoResponse> getData() {
        return data;
    }

    public void setData(List<VideoResponse> data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    @NonNull
    @Override
    public String toString() {
        return "VideoWrapper{" +
                "paging=" + paging +
                ", data=" + data +
                '}';
    }
}
