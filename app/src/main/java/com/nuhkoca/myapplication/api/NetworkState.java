package com.nuhkoca.myapplication.api;


import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A class that handles all network traffics
 *
 * @author nuhkoca
 */
public class NetworkState extends Exception {
    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED,
        NO_ITEM
    }

    private final Status status;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;

    /**
     * @param status  from {@link Status}
     * @param message an error message
     */
    @Inject
    public NetworkState(@NonNull Status status, @Nullable String message) {
        super(message);
        this.status = status;
    }

    static {
        LOADED = new NetworkState(Status.SUCCESS, "");
        LOADING = new NetworkState(Status.RUNNING, "");
    }

    /**
     * Returns a status
     *
     * @return a {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns message
     *
     * @return message
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
