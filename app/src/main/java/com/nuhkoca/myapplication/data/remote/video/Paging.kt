package com.nuhkoca.myapplication.data.remote.video

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.nuhkoca.myapplication.BR

data class Paging(@SerializedName("next") var _next: String) : BaseObservable() {

    var next: String
        @Bindable get() = _next
        set(value) {
            _next = value
            notifyPropertyChanged(BR.next)
        }
}