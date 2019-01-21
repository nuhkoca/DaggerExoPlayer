package com.nuhkoca.myapplication.data.remote.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.myapplication.BR;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;

public class VideoResponse extends BaseObservable {

    public static DiffUtil.ItemCallback<VideoResponse> DIFF_CALLBACK = new DiffUtil.ItemCallback<VideoResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull VideoResponse oldItem, @NonNull VideoResponse newItem) {
            return oldItem.uri.equals(newItem.uri);
        }

        @Override
        public boolean areContentsTheSame(@NonNull VideoResponse oldItem, @NonNull VideoResponse newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Expose
    @SerializedName("uri")
    private String uri;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("pictures")
    private Pictures pictures;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("duration")
    private int duration;

    @Bindable
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
        notifyPropertyChanged(BR.uri);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Pictures getPictures() {
        return pictures;
    }

    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
        notifyPropertyChanged(BR.pictures);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        notifyPropertyChanged(BR.duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() == obj.getClass()) {
            return true;
        }

        VideoResponse videoResponse = (VideoResponse) obj;

        return videoResponse.uri.equals(this.uri);
    }

    @NonNull
    @Override
    public String toString() {
        return "VideoResponse{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pictures=" + pictures +
                ", status='" + status + '\'' +
                ", duration=" + duration +
                '}';
    }
}
