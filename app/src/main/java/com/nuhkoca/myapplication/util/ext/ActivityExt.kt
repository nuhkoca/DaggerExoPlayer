package com.nuhkoca.myapplication.util.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.nuhkoca.myapplication.ui.video.ExoUtilHandler
import com.nuhkoca.myapplication.util.exo.ExoUtil

fun Activity.lifecycleAwareHandler(lifecycleOwner: LifecycleOwner, exoUtil: ExoUtil) {
    lifecycleOwner.lifecycle.addObserver(ExoUtilHandler(exoUtil))
}

inline fun <reified T : Any> Activity.launchActivityWithExtra(
    noinline init: Intent.() -> Unit = {},
    crossinline extras: Bundle.() -> Unit = {}
) {
    val intent = newIntent<T>()
    intent.init()
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

inline fun <reified T : Any> Activity.newIntent(): Intent =
    Intent(this, T::class.java)
