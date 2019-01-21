package com.nuhkoca.myapplication.binding;

import android.text.TextUtils;
import android.widget.ImageView;

import com.nuhkoca.myapplication.di.module.ExoGlide;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

/**
 * A {@link BindingAdapter} to bind images into ImageViews.
 *
 * @author nuhkoca
 */
public class ImageBindingAdapter {

    /**
     * A default constructor that gets several dependents
     */
    @Inject
    public ImageBindingAdapter() {
    }

    /**
     * A {@link BindingAdapter} method that binds image into view
     *
     * @param view represents an {@link ImageView}
     * @param url  represents image url
     */
    @BindingAdapter(value = {"android:src"})
    public void bindImage(@NonNull ImageView view, String url) {
        if (!TextUtils.isEmpty(url) || url == null) {
            ExoGlide.with(view.getContext())
                    .asBitmap()
                    .load(url)
                    .into(view);
        }
    }
}
