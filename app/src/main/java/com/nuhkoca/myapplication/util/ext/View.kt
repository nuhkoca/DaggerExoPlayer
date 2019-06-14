package com.nuhkoca.myapplication.util.ext

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

inline var View.isVisible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

inline var View.isHidden: Boolean
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

inline var View.isInvisible: Boolean
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}
