package com.nuhkoca.myapplication.binding

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.nuhkoca.myapplication.di.module.ExoGlide
import javax.inject.Inject

/**
 * A [BindingAdapter] to bind images into ImageViews.
 *
 * @author nuhkoca
 */
class ImageBindingAdapter @Inject constructor() {

    /**
     * A [BindingAdapter] method that binds image into view
     *
     * @param view represents an [ImageView]
     * @param url  represents image url
     */
    @BindingAdapter(value = ["android:src"])
    fun ImageView.bindImage(url: String?) {
        if (!TextUtils.isEmpty(url) || url == null) {
            ExoGlide.with(context)
                .asBitmap()
                .load(url)
                .into(this)
        }
    }
}
