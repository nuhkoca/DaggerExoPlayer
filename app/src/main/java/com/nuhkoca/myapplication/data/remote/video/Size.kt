package com.nuhkoca.myapplication.data.remote.video

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.nuhkoca.myapplication.BR

data class Size(@SerializedName("link_with_play_button") var _linkWithPlayButton: String) : BaseObservable() {

    var linkWithPlayButton: String
        @Bindable get() = _linkWithPlayButton
        set(value) {
            _linkWithPlayButton = value
            notifyPropertyChanged(BR.linkWithPlayButton)
        }
}
