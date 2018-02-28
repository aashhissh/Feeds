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

        holder.tvTitle.setText(feed.getTitle());
        holder.tvDescription.setText(feed.getDescription());
        if(!CodeUtil.isEmpty(feed.getImageUrl())) {
            holder.ivFeedImage.setImageURI(Uri.parse(feed.getImageUrl()));
        } else {
            holder.ivFeedImage.setImageURI(null);
        }
    }

    @Override
    public int getItemCount() {
        return feeds == null ? 0 : feeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDescription;
        private ImageView ivFeedImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivFeedImage = itemView.findViewById(R.id.iv_feed_image);
        }
    }
}
