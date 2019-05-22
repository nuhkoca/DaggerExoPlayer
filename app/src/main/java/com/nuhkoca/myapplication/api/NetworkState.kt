package com.nuhkoca.myapplication.api

import com.nuhkoca.myapplication.api.NetworkState.Status
import javax.inject.Inject

/**
 * A class that handles all network traffics
 *
 * @author nuhkoca
 */
class NetworkState
/**
 * @param status  from [Status]
 * @param message an error message
 */
@Inject
constructor(val status: Status, message: String?) : Exception(message) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        NO_ITEM
    }

    /**
     * Returns message
     *
     * @return message
     */
    override val message: String?
        get() = super.message

    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "")

    }
}
