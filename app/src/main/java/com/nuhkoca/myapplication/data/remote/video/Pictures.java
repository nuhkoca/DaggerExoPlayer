package com.nuhkoca.myapplication.data.remote.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.myapplication.BR;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Pictures extends BaseObservable {

    @Expose
    @SerializedName("sizes")
    private List<Size> sizes;

    @Bindable
    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
        notifyPropertyChanged(BR.sizes);
    }
}
