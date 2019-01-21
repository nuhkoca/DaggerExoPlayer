package com.nuhkoca.myapplication.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.myapplication.BR;
import com.nuhkoca.myapplication.R;
import com.nuhkoca.myapplication.api.NetworkState;
import com.nuhkoca.myapplication.base.BaseViewHolder;
import com.nuhkoca.myapplication.data.remote.video.VideoResponse;
import com.nuhkoca.myapplication.databinding.NetworkStateItemBinding;
import com.nuhkoca.myapplication.databinding.VideoItemLayoutBinding;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A {@link RecyclerView.Adapter} that holds video results
 *
 * @author nuhkoca
 */
public class VideoAdapter extends PagedListAdapter<VideoResponse, RecyclerView.ViewHolder> {

    private NetworkState mNetworkState;

    private VideoItemListener mVideoItemListener;
    private NetworkItemListener mNetworkItemListener;

    /**
     * A default constructor that initializes callbacks
     *
     * @param videoItemListener   represents an instance of {@link VideoItemListener}
     * @param networkItemListener epresents an instance of {@link NetworkItemListener}
     */
    public VideoAdapter(@NonNull VideoItemListener videoItemListener, @NonNull NetworkItemListener networkItemListener) {
        super(VideoResponse.DIFF_CALLBACK);
        this.mVideoItemListener = videoItemListener;
        this.mNetworkItemListener = networkItemListener;
    }

    /**
     * Inflates relative layout files in accordance with {@link VideoAdapter#getItemViewType(int)}
     *
     * @param parent   represent parent view
     * @param viewType represents item type
     * @return relative layout files in accordance with {@link VideoAdapter#getItemViewType(int)}
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == R.layout.video_item_layout) {
            VideoItemLayoutBinding videoItemLayoutBinding =
                    VideoItemLayoutBinding.inflate(inflater, parent, false);

            return new VideoViewHolder(videoItemLayoutBinding.getRoot());
        } else {
            NetworkStateItemBinding networkStateItemBinding = DataBindingUtil.inflate(inflater,
                    R.layout.network_state_item, parent, false);

            return new NetworkViewHolder(networkStateItemBinding.getRoot());
        }
    }

    /**
     * Binds relative ViewHolder to make items visible on the screen
     *
     * @param holder   a ViewHolder below
     * @param position represents position in model
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.video_item_layout:
                VideoResponse videoResponse = getItem(position);

                if (videoResponse != null) {
                    ((VideoViewHolder) holder).setListener(mVideoItemListener);
                    ((VideoViewHolder) holder).bindTo(videoResponse);
                }

                break;

            case R.layout.network_state_item:
                ((NetworkViewHolder) holder).setListener(mNetworkItemListener);
                ((NetworkViewHolder) holder).bindTo(mNetworkState);
                break;

        }
    }

    /**
     * Get the number of items currently presented by this Differ. This value can be directly
     * returned to {@link RecyclerView.Adapter#getItemCount()}.
     *
     * @return Number of items being presented.
     */
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    /**
     * Indicates that if there is an extra row of search result
     *
     * @return {@code true} if there is an extra row
     * otherwise {code false}
     */
    private boolean hasExtraRow() {
        return mNetworkState != null && mNetworkState != NetworkState.LOADED;
    }

    /**
     * Returns relative type to show in RecyclerView
     *
     * @param position represents position in model
     * @return relative type to show in RecyclerView
     */
    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.video_item_layout;
        }
    }

    /**
     * Sets a network state in order to take an action
     *
     * @param newNetworkState represents a network state
     */
    @SuppressWarnings("ConstantConditions")
    @Contract("null->fail")
    public void setNetworkState(@NonNull NetworkState newNetworkState) {
        NetworkState previousState = this.mNetworkState;
        boolean previousExtraRow = hasExtraRow();
        this.mNetworkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    /**
     * A {@link RecyclerView.ViewHolder} that holds video items
     */
    class VideoViewHolder extends BaseViewHolder<VideoItemLayoutBinding, VideoResponse, VideoItemListener> {

        /**
         * A default obligatory constructor
         *
         * @param itemView that holds entire layout
         */
        VideoViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Sets listener for each video item
         *
         * @param listener represents any interface
         */
        @Override
        public void setListener(VideoItemListener listener) {
            this.listener = listener;
        }

        /**
         * Binds a model into layout items
         *
         * @param item a model
         */
        @Override
        public void bindTo(@NonNull VideoResponse item) {
            dataBinding.setVariable(BR.videoResponse, item);
            dataBinding.setVariable(BR.size, item.getPictures().getSizes().get(5)); //large size
            dataBinding.setVariable(BR.videoId, getVideoId(item.getUri()));
            dataBinding.setVariable(BR.videoItemListener, listener);
        }
    }

    /**
     * Returns video id without slashes
     *
     * @param videoId indicates the long video id
     * @return video id without slashes
     */
    @NotNull
    private String getVideoId(@NonNull String videoId) {
        int index = videoId.lastIndexOf("/");
        return videoId.substring(index + 1);
    }

    /**
     * A {@link RecyclerView.ViewHolder} that manages network processes
     */
    class NetworkViewHolder extends BaseViewHolder<NetworkStateItemBinding, NetworkState, NetworkItemListener> {

        /**
         * A default obligatory constructor
         *
         * @param itemView that holds entire layout
         */
        NetworkViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Sets listener for each artist item
         *
         * @param listener represents any interface
         */
        @Override
        public void setListener(NetworkItemListener listener) {
            this.listener = listener;
        }

        /**
         * Binds a model into layout items
         *
         * @param networkState a network state
         */
        @Override
        public void bindTo(@NonNull NetworkState networkState) {
            dataBinding.setVariable(BR.networkItemListener, listener);

            if (networkState.getStatus() == NetworkState.Status.RUNNING) {
                dataBinding.clNetwork.setVisibility(View.VISIBLE);
                dataBinding.pbNetwork.setVisibility(View.VISIBLE);
                dataBinding.btnNetworkStateErrButton.setVisibility(View.GONE);
                dataBinding.tvNetworkStateErrText.setVisibility(View.GONE);
            } else if (networkState.getStatus() == NetworkState.Status.FAILED) {
                dataBinding.clNetwork.setVisibility(View.VISIBLE);
                dataBinding.pbNetwork.setVisibility(View.GONE);
                dataBinding.btnNetworkStateErrButton.setVisibility(View.VISIBLE);
                dataBinding.tvNetworkStateErrText.setVisibility(View.VISIBLE);
                dataBinding.tvNetworkStateErrText.setText(networkState.getMessage());
            } else {
                dataBinding.clNetwork.setVisibility(View.GONE);
                dataBinding.pbNetwork.setVisibility(View.GONE);
                dataBinding.btnNetworkStateErrButton.setVisibility(View.GONE);
                dataBinding.tvNetworkStateErrText.setVisibility(View.GONE);
            }

            dataBinding.executePendingBindings();
        }
    }

    /**
     * An interface that sniffs click event for video item
     */
    public interface VideoItemListener {

        /**
         * Gets called when a click event has been triggered for any of video item
         *
         * @param videoId indicates the video id of the selected item
         */
        void onVideoItemClicked(@Nullable String videoId);
    }

    /**
     * An interface that sniffs click event for network item
     *
     * @author nuhkoca
     */
    public interface NetworkItemListener {

        /**
         * Gets called when a click event has been triggered for any of network item
         */
        void onNetworkItemClicked();
    }
}
