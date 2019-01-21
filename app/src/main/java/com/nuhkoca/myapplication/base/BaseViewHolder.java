package com.nuhkoca.myapplication.base;

import android.view.View;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A base {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
 * @param <DB> a {@link ViewDataBinding}
 * @param <T> a model
 *
 * @author nuhkoca
 */
public abstract class BaseViewHolder<DB extends ViewDataBinding, T, L> extends RecyclerView.ViewHolder {

    protected L listener;

    /**
     * A {@link ViewDataBinding} instance
     */
    protected DB dataBinding;

    /**
     * A default obligatory constructor
     *
     * @param itemView that holds entire layout
     */
    public BaseViewHolder(View itemView) {
        super(itemView);

        dataBinding = DataBindingUtil.getBinding(itemView);
    }

    /**
     * Sets corresponding listener to {@link BaseViewHolder#listener}
     *
     * @param listener represents any interface
     */
    public abstract void setListener(L listener);

    /**
     * A default method that binds model into layout
     *
     * @param item a model
     */
    @Contract("null->fail")
    public abstract void bindTo(@NonNull T item);
}
