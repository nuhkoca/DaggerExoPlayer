package com.nuhkoca.myapplication.di.module;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.nuhkoca.myapplication.helper.Constants;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

/**
 * A GlideModule for this app to manage all Glide structures in a single place
 * This will give the same impact on all Glide.
 *
 * @author nuhkoca
 */
@GlideModule(glideName = "ExoGlide")
@Excludes(value = {OkHttpLibraryGlideModule.class})
public final class ExoGlideModule extends AppGlideModule {

    /**
     * Registers necessary components for Glide
     *
     * @param context  represents an instance of {@link Context}
     * @param glide    represents an instance of {@link Glide}
     * @param registry represents an instance of {@link Registry}
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);

        glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
    }

    /**
     * Applies specific options to Glide
     *
     * @param context represents an instance of {@link Context}
     * @param builder represents an instance of {@link GlideBuilder}
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 300; // 300mb cache
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, memoryCacheSizeBytes));
        builder.setDefaultRequestOptions(requestOptions());
    }

    /**
     * A default {@link RequestOptions} to customize Glide
     *
     * @return an instance of {@link RequestOptions}
     */
    private RequestOptions requestOptions() {
        return new RequestOptions()
                .signature(new ObjectKey(
                        System.currentTimeMillis() / (168 * 60 * 60 * 1000))) // 1 week cache
                .centerCrop()
                .dontAnimate()
                .override(Target.SIZE_ORIGINAL)
                .encodeFormat(Bitmap.CompressFormat.PNG)
                .encodeQuality(100)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .format(PREFER_ARGB_8888)
                .skipMemoryCache(true);
    }
}
