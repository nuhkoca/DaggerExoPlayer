package com.nuhkoca.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhkoca.myapplication.BR
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.api.NetworkState
import com.nuhkoca.myapplication.api.NetworkState.Status.FAILED
import com.nuhkoca.myapplication.api.NetworkState.Status.RUNNING
import com.nuhkoca.myapplication.base.BaseViewHolder
import com.nuhkoca.myapplication.data.remote.video.VideoResponse
import com.nuhkoca.myapplication.databinding.NetworkStateItemBinding
import com.nuhkoca.myapplication.databinding.VideoItemLayoutBinding
import com.nuhkoca.myapplication.util.ext.SingleLiveEvent
import org.jetbrains.annotations.Contract
import javax.inject.Inject

/**
 * A [RecyclerView.Adapter] that holds video results
 *
 * @author nuhkoca
 */
class VideoAdapter @Inject constructor() :
    PagedListAdapter<VideoResponse, RecyclerView.ViewHolder>(VideoResponse.DIFF_CALLBACK) {

    val videoItemClick = SingleLiveEvent<String>()
    private var mNetworkState: NetworkState? = null

    /**
     * Inflates relative layout files in accordance with [VideoAdapter.getItemViewType]
     *
     * @param parent   represent parent view
     * @param viewType represents item type
     * @return relative layout files in accordance with [VideoAdapter.getItemViewType]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return if (viewType == R.layout.video_item_layout) {
            val videoItemLayoutBinding = VideoItemLayoutBinding.inflate(inflater, parent, false)
            VideoViewHolder(videoItemLayoutBinding.root)
        } else {
            val networkStateItemBinding = NetworkStateItemBinding.inflate(inflater, parent, false)
            NetworkViewHolder(networkStateItemBinding.root)
        }
    }

    /**
     * Binds relative ViewHolder to make items visible on the screen
     *
     * @param holder   a ViewHolder below
     * @param position represents position in model
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                val videoResponse = getItem(position)

                videoResponse?.let {
                    holder.bindTo(videoResponse)
                }
            }

            is NetworkViewHolder -> {
                mNetworkState?.let(holder::bindTo)
            }
        }
    }

    /**
     * Indicates that if there is an extra row of search result
     *
     * @return `true` if there is an extra row
     * otherwise {code false}
     */
    private fun hasExtraRow(): Boolean {
        return mNetworkState != null && mNetworkState != NetworkState.LOADED
    }

    /**
     * Returns relative type to show in RecyclerView
     *
     * @param position represents position in model
     * @return relative type to show in RecyclerView
     */
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.video_item_layout
        }
    }

    /**
     * Sets a network state in order to take an action
     *
     * @param newNetworkState represents a network state
     */
    @Contract("null->fail")
    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.mNetworkState
        val previousExtraRow = hasExtraRow()
        this.mNetworkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    /**
     * A [RecyclerView.ViewHolder] that holds video items
     */
    internal inner class VideoViewHolder(itemView: View) :
        BaseViewHolder<VideoItemLayoutBinding, VideoResponse>(itemView) {

        /**
         * Binds a model into layout items
         *
         * @param item a model
         */
        override fun bindTo(item: VideoResponse) {
            dataBinding?.apply {
                setVariable(BR.videoResponse, item)
                setVariable(BR.size, item.pictures.sizes[INDEX_LARGE_IMAGE])

                videoHolder.setOnClickListener { videoItemClick.value = getVideoId(item.uri) }

                executePendingBindings()
            }
        }
    }

    /**
     * Returns video id without slashes
     *
     * @param videoId indicates the long video id
     * @return video id without slashes
     */
    private fun getVideoId(videoId: String): String {
        val index = videoId.lastIndexOf("/")
        return videoId.substring(index + 1)
    }

    /**
     * A [RecyclerView.ViewHolder] that manages network processes
     */
    internal inner class NetworkViewHolder(itemView: View) :
        BaseViewHolder<NetworkStateItemBinding, NetworkState>(itemView) {

        /**
         * Binds a model into layout items
         *
         * @param item a network state
         */
        override fun bindTo(item: NetworkState) {
            dataBinding?.apply {
                when {
                    item.status === RUNNING -> {
                        clNetwork.visibility = View.VISIBLE
                        pbNetwork.visibility = View.VISIBLE
                        btnNetworkStateErrButton.visibility = View.GONE
                        tvNetworkStateErrText.visibility = View.GONE
                    }
                    item.status === FAILED -> {
                        clNetwork.visibility = View.VISIBLE
                        pbNetwork.visibility = View.GONE
                        btnNetworkStateErrButton.visibility = View.VISIBLE
                        tvNetworkStateErrText.visibility = View.VISIBLE
                        tvNetworkStateErrText.text = item.message
                    }
                    else -> {
                        clNetwork.visibility = View.GONE
                        pbNetwork.visibility = View.GONE
                        btnNetworkStateErrButton.visibility = View.GONE
                        tvNetworkStateErrText.visibility = View.GONE
                    }
                }

                executePendingBindings()
            }
        }
    }

    companion object {
        private const val INDEX_LARGE_IMAGE = 5
    }
}
