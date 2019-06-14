package com.nuhkoca.myapplication.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.Contract

/**
 * A base [androidx.recyclerview.widget.RecyclerView.ViewHolder]
 * @param <DB> a [ViewDataBinding]
 * @param <T> a model
 *
 * @author nuhkoca
 */
abstract class BaseViewHolder<DB : ViewDataBinding, T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var dataBinding: DB? = null

    init {
        dataBinding = DataBindingUtil.getBinding(itemView)
    }

    /**
     * A default method that binds model into layout
     *
     * @param item a model
     */
    @Contract("null->fail")
    abstract fun bindTo(item: T)
}
