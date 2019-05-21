package com.nuhkoca.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nuhkoca.myapplication.BR
import com.nuhkoca.myapplication.R
import com.nuhkoca.myapplication.api.NetworkState
import com.nuhkoca.myapplication.base.BaseViewHolder
import com.nuhkoca.myapplication.data.remote.video.VideoResponse
import com.nuhkoca.myapplication.databinding.NetworkStateItemBinding
import com.nuhkoca.myapplication.databinding.VideoItemLayoutBinding
import com.nuhkoca.myapplication.ui.main.adapter.VideoAdapter.NetworkItemListener
import com.nuhkoca.myapplication.ui.main.adapter.VideoAdapter.VideoItemListener
import org.jetbrains.annotations.Contract

/**
 * A [RecyclerView.Adapter] that holds video results
 *
 * @author nuhkoca
 */
class VideoAdapter
/**
 * A default constructor that initializes callbacks
 *
 * @param videoItemListener   represents an instance of [VideoItemListener]
 * @param networkItemListener epresents an instance of [NetworkItemListener]
 */
(private val mVideoItemListener: VideoItemListener, private val mNetworkItemListener: NetworkItemListener) : PagedListAdapter<VideoResponse, RecyclerView.ViewHolder>(VideoResponse.DIFF_CALLBACK) {

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
            val networkStateItemBinding = DataBindingUtil.inflate<NetworkStateItemBinding>(inflater,
                    R.layout.network_state_item, parent, false)

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
        when (getItemViewType(position)) {
            R.layout.video_item_layout -> {
                val videoResponse = getItem(position)

                if (videoResponse != null) {
                    (holder as VideoViewHolder).setListener(mVideoItemListener)
                    holder.bindTo(videoResponse)
                }
            }

            R.layout.network_state_item -> {
                (holder as NetworkViewHolder).setListener(mNetworkItemListener)
                holder.bindTo(mNetworkState!!)
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
    internal inner class VideoViewHolder(itemView: View) : BaseViewHolder<VideoItemLayoutBinding, VideoResponse, VideoItemListener>(itemView) {

        /**
         * Sets listener for each video item
         *
         * @param listener represents any interface
         */
        override fun setListener(listener: VideoItemListener) {
            this.listener = listener
        }

        /**
         * Binds a model into layout items
         *
         * @param item a model
         */
        override fun bindTo(item: VideoResponse) {
            dataBinding!!.setVariable(BR.videoResponse, item)
            dataBinding!!.setVariable(BR.size, item.pictures.sizes[5]) //large size
            dataBinding!!.setVariable(BR.videoId, getVideoId(item.uri))
            dataBinding!!.setVariable(BR.videoItemListener, listener)
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
    internal inner class NetworkViewHolder(itemView: View) : BaseViewHolder<NetworkStateItemBinding, NetworkState, NetworkItemListener>(itemView) {

        /**
         * Sets listener for each artist item
         *
         * @param listener represents any interface
         */
        override fun setListener(listener: NetworkItemListener) {
            this.listener = listener
        }

        /**
         * Binds a model into layout items
         *
         * @param item a network state
         */
        override fun bindTo(item: NetworkState) {
            dataBinding!!.setVariable(BR.networkItemListener, listener)

            if (item.status === NetworkState.Status.RUNNING) {
                dataBinding!!.clNetwork.visibility = View.VISIBLE
                dataBinding!!.pbNetwork.visibility = View.VISIBLE
                dataBinding!!.btnNetworkStateErrButton.visibility = View.GONE
                dataBinding!!.tvNetworkStateErrText.visibility = View.GONE
            } else if (item.status === NetworkState.Status.FAILED) {
                dataBinding!!.clNetwork.visibility = View.VISIBLE
                dataBinding!!.pbNetwork.visibility = View.GONE
                dataBinding!!.btnNetworkStateErrButton.visibility = View.VISIBLE
                dataBinding!!.tvNetworkStateErrText.visibility = View.VISIBLE
                dataBinding!!.tvNetworkStateErrText.text = item.message
            } else {
                dataBinding!!.clNetwork.visibility = View.GONE
                dataBinding!!.pbNetwork.visibility = View.GONE
                dataBinding!!.btnNetworkStateErrButton.visibility = View.GONE
                dataBinding!!.tvNetworkStateErrText.visibility = View.GONE
            }

            dataBinding!!.executePendingBindings()
        }
    }

    /**
     * An interface that sniffs click event for video item
     */
    interface VideoItemListener {

        /**
         * Gets called when a click event has been triggered for any of video item
         *
         * @param videoId indicates the video id of the selected item
         */
        fun onVideoItemClicked(videoId: String?)
    }

    /**
     * An interface that sniffs click event for network item
     *
     * @author nuhkoca
     */
    interface NetworkItemListener {

        /**
         * Gets called when a click event has been triggered for any of network item
         */
        fun onNetworkItemClicked()
    }
}
