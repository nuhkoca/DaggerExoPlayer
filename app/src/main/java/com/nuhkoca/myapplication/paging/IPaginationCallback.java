package com.nuhkoca.myapplication.paging;


import java.util.List;

import androidx.paging.PageKeyedDataSource;

/**
 * An interface to handle pagination process
 * @param <Wrapper> represents the most top model
 * @param <Result> represent the actual response
 *
 * @author nuhkoca
 */
public interface IPaginationCallback<Wrapper, Result> {

    /**
     * Gets called when there is any error at first fetch
     *
     * @param throwable represents any error
     */
    void onInitialError(Throwable throwable);

    /**
     * Gets called when first fetch is successful
     *
     * @param wrapper  represents the most top model
     * @param callback represents {@link PageKeyedDataSource.LoadInitialCallback}
     * @param model    represents the actual response
     */
    void onInitialSuccess(Wrapper wrapper, PageKeyedDataSource.LoadInitialCallback<Integer, Result> callback, List<Result> model);

    /**
     * Gets called when there is any error at next fetches
     *
     * @param throwable represents any error
     */
    void onPaginationError(Throwable throwable);

    /**
     * Gets called when next fetches are successful
     *
     * @param wrapper  represents the most top model
     * @param callback represents {@link paging.PageKeyedDataSource.LoadInitialCallback}
     * @param model    represents the actual response
     */
    void onPaginationSuccess(Wrapper wrapper, PageKeyedDataSource.LoadCallback<Integer, Result> callback, PageKeyedDataSource.LoadParams<Integer> params, List<Result> model);

    /**
     * Clears references
     */
    void clear();
}