package com.nuhkoca.myapplication.util.ext

import kotlin.LazyThreadSafetyMode.NONE

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(NONE, initializer)
