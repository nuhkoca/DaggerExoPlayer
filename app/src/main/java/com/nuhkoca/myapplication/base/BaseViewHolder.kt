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
abstract class BaseViewHolder<DB : ViewDataBinding, T, L>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var listener: L? = null
    internal var dataBinding: DB? = null

    init {
        dataBinding = DataBindingUtil.getBinding(itemView)
    }

    /**
     * Sets corresponding listener to [BaseViewHolder.listener]
     *
     * @param listener represents any interface
     */
    abstract fun setListener(listener: L)

    /**
     * A default method that binds model into layout
     *
     * @param item a model
     */
    @Contract("null->fail")
    abstract fun bindTo(item: T)
}
