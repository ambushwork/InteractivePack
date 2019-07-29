package com.netatmo.ylu.interactivepack.coordinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.netatmo.ylu.interactivepack.R;
import com.netatmo.ylu.interactivepack.Utils;
import com.squareup.picasso.Picasso;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.FeedHolder> {
    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coordinator_list, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {
        Picasso.get()
                .load(this.getAvatarUrl(i))
                .centerInside()
                .fit()
                .into(feedHolder.mIvAvatar);
        Picasso.get()
                .load(this.getContentUrl(i))
                .centerInside()
                .fit()
                .into(feedHolder.mIvContent);

        feedHolder.mTvNickname.setText("Person " + i);
    }

    private String getAvatarUrl(int position) {
        return "https://i.pravatar.cc/150?img=" + position;
    }

    private String getContentUrl(int position) {
        return Utils.Companion.getRandomLargePic(position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    static class FeedHolder extends RecyclerView.ViewHolder {

        final ImageView mIvAvatar;
        final ImageView mIvContent;
        final TextView mTvNickname;

        FeedHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.item_coordinator_avatar);
            mIvContent = itemView.findViewById(R.id.item_coordinator_preview);
            mTvNickname = itemView.findViewById(R.id.item_coordinator_name);
        }
    }

}
