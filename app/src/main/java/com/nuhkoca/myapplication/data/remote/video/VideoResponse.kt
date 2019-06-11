package com.nuhkoca.myapplication.data.remote.video

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import com.nuhkoca.myapplication.BR
import java.util.Objects

data class VideoResponse(
    @SerializedName("uri") var _uri: String,
    @SerializedName("uri") var _name: String,
    @SerializedName("uri") var _description: String,
    @SerializedName("uri") var _pictures: Pictures,
    @SerializedName("uri") var _status: String,
    @SerializedName("uri") var _duration: Int
) : BaseObservable() {

    var uri: String
        @Bindable get() = _uri
        set(value) {
            _uri = value
            notifyPropertyChanged(BR.uri)
        }

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var description: String
        @Bindable get() = _description
        set(value) {
            _description = value
            notifyPropertyChanged(BR.description)
        }

    var pictures: Pictures
        @Bindable get() = _pictures
        set(value) {
            _pictures = value
            notifyPropertyChanged(BR.pictures)
        }

    var status: String
        @Bindable get() = _status
        set(value) {
            _status = value
            notifyPropertyChanged(BR.status)
        }

    var duration: Int
        @Bindable get() = _duration
        set(value) {
            _duration = value
            notifyPropertyChanged(BR.duration)
        }

    override fun equals(other: Any?): Boolean {
        if (javaClass == other?.javaClass) {
            return true
        }

        val videoResponse = other as VideoResponse

        return videoResponse.uri == this.uri
    }

    override fun hashCode(): Int {
        return Objects.hashCode(uri)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<VideoResponse> = object : DiffUtil.ItemCallback<VideoResponse>() {
            override fun areItemsTheSame(oldItem: VideoResponse, newItem: VideoResponse): Boolean {
                return oldItem.uri == newItem.uri
            }

            override fun areContentsTheSame(oldItem: VideoResponse, newItem: VideoResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
