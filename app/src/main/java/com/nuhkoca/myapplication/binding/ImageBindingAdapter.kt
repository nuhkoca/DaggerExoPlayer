package com.nuhkoca.myapplication.binding

import android.text.TextUtils
import android.widget.ImageView

import com.nuhkoca.myapplication.di.module.ExoGlide

import javax.inject.Inject
import androidx.databinding.BindingAdapter

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
    fun bindImage(view: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url) || url == null) {
            ExoGlide.with(view.context)
                    .asBitmap()
                    .load(url)
                    .into(view)
        }
    }
}
