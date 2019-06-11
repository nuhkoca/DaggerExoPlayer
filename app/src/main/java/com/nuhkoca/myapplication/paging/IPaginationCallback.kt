package com.nuhkoca.myapplication.paging

import androidx.paging.PageKeyedDataSource

/**
 * An interface to handle pagination process
 * @param <Wrapper> represents the most top model
 * @param <Result> represent the actual response
 *
 * @author nuhkoca
 */
interface IPaginationCallback<Wrapper, Result> {

    /**
     * Gets called when there is any error at first fetch
     *
     * @param throwable represents any error
     */
    fun onInitialError(throwable: Throwable)

    /**
     * Gets called when first fetch is successful
     *
     * @param wrapper  represents the most top model
     * @param callback represents [PageKeyedDataSource.LoadInitialCallback]
     * @param model    represents the actual response
     */
    fun onInitialSuccess(
        wrapper: Wrapper,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Result>,
        model: MutableList<Result>
    )

    /**
     * Gets called when there is any error at next fetches
     *
     * @param throwable represents any error
     */
    fun onPaginationError(throwable: Throwable)

    /**
     * Gets called when next fetches are successful
     *
     * @param wrapper  represents the most top model
     * @param callback represents [paging.PageKeyedDataSource.LoadInitialCallback]
     * @param model    represents the actual response
     */
    fun onPaginationSuccess(
        wrapper: Wrapper,
        callback: PageKeyedDataSource.LoadCallback<Int, Result>,
        params: PageKeyedDataSource.LoadParams<Int>,
        model: MutableList<Result>
    )
}