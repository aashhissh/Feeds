package com.ashish.feeds.presentation.ui.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.feeds.R;
import com.ashish.feeds.presentation.models.FeedModel;
import com.ashish.feeds.presentation.ui.custom_views.image_view.CachedImageView;
import com.ashish.feeds.utils.CodeUtil;

import java.util.ArrayList;

/**
 * @author ashish
 * @since 28/02/18
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private ArrayList<FeedModel> feeds;

    public FeedsAdapter(ArrayList<FeedModel> feeds) {
        this.feeds = feeds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.feed_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedModel feed = feeds.get(holder.getAdapterPosition());

        CodeUtil.setOrHideText(feed.getTitle(), holder.tvTitle);
        CodeUtil.setOrHideText(feed.getDescription(), holder.tvDescription);
        if(!CodeUtil.isEmpty(feed.getImageUrl())) {
            holder.ivFeedImage.setVisibility(View.VISIBLE);
            holder.ivFeedImage.setUri(feed.getImageUrl());
        } else {
            holder.ivFeedImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return feeds == null ? 0 : feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDescription;
        private CachedImageView ivFeedImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivFeedImage = itemView.findViewById(R.id.iv_feed_image);
        }
    }
}
