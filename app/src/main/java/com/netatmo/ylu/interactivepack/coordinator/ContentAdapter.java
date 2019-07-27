package com.netatmo.ylu.interactivepack.coordinator;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.netatmo.ylu.interactivepack.R;
import com.squareup.picasso.Picasso;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.FeedHolder> {
    private Context context;
    private AppCompatActivity activity;

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coordinator_list, viewGroup, false);
        return new FeedHolder(view);
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedHolder feedHolder, int i) {
        final String url = this.getAvatarUrl(i);
        Picasso.get()
                .load(url)
                .centerInside()
                .fit()
                .into(feedHolder.mIvAvatar);
        Picasso.get()
                .load(this.getContentUrl(i))
                .centerInside()
                .fit()
                .into(feedHolder.mIvContent);
        ViewCompat.setTransitionName(feedHolder.mIvAvatar, url);
        feedHolder.mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDialogFragment dialogFragment = PersonDialogFragment.Companion.newInstance(url);
                dialogFragment.show(activity.getSupportFragmentManager(), "Dialog");
                /*dialogFragment.setSharedElementEnterTransition(new DetailTransition());
                dialogFragment.setEnterTransition(new Fade());
                dialogFragment.setSharedElementReturnTransition(new DetailTransition());
                activity.getSupportFragmentManager().beginTransaction()
                        .addSharedElement(feedHolder.mIvAvatar, url)
                        //.replace(R.id.item_coordinator_list, dialogFragment)
                        .addToBackStack(null)
                        .commit();*/
            }

        });
        feedHolder.mTvNickname.setText("Person " + i);
    }

    private String getAvatarUrl(int position) {
        return "https://i.pravatar.cc/150?img=" + position;
    }

    private String getContentUrl(int position) {
        return String.format("https://picsum.photos/id/%s/1920/1080", String.valueOf(position));
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
