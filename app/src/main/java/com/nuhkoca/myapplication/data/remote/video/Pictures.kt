package com.nuhkoca.myapplication.data.remote.video

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.nuhkoca.myapplication.BR

data class Pictures(@SerializedName("sizes") var _sizes: List<Size>) : BaseObservable() {

    var sizes: List<Size>
        @Bindable get() = _sizes
        set(value) {
            _sizes = value
            notifyPropertyChanged(BR.sizes)
        }
}
