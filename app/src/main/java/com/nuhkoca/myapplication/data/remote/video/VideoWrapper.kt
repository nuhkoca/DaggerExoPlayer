package com.nuhkoca.myapplication.data.remote.video

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.nuhkoca.myapplication.BR

data class VideoWrapper(
    @SerializedName("paging") var _paging: Paging,
    @SerializedName("data") var _data: List<VideoResponse>
) : BaseObservable() {

    var paging: Paging
        @Bindable get() = _paging
        set(value) {
            _paging = value
            notifyPropertyChanged(BR.paging)
        }

    var data: List<VideoResponse>
        @Bindable get() = _data
        set(value) {
            _data = value
            notifyPropertyChanged(BR.data)
        }
}
