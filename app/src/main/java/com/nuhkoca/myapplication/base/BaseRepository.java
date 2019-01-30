package com.nuhkoca.myapplication.base;

import android.content.Context;

import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.api.IExoAPI;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import retrofit2.Response;

/**
 * A base repository class that provides an instance of {@link IExoAPI}
 *
 * @author nuhkoca
 */
public class BaseRepository {

    private IExoAPI iExoAPI;
    private Context context;

    /**
     * A default constructor
     *
     * @param iExoAPI represents an instance of {@link IExoAPI}
     * @param context       represents an instance of {@link Context}
     */
    public BaseRepository(@NonNull IExoAPI iExoAPI,
                          @NonNull Context context) {
        this.iExoAPI = iExoAPI;
        this.context = context;
    }

    /**
     * Returns an instance of {@link IExoAPI}
     *
     * @return an instance of {@link IExoAPI}
     */
    protected IExoAPI getIExoAPIService() {
        return iExoAPI;
    }

    /**
     * Merges a response with in-case error.
     *
     * @param <T> represents any generic item
     * @return a result as {@link Single}
     */
    @Contract("null->fail")
    protected <T> Single<Response<T>> interceptError(@NonNull Response<T> response) {
        int requestCode = response.code();

        if (requestCode == 401) { /* do nothing this error is handled in Auth interceptor */
            return Single.error(new Throwable(context.getString(R.string.auth_error)));
        } else if (requestCode <= 501 && requestCode >= 400) {
            try {
                return Single.error(new Throwable(response.message()));
            } catch (Exception ignore) {
                return Single.error(new Throwable(context.getString(R.string.network_call_initial_error)));
            }
        }

        return Single.just(response);
    }
}
