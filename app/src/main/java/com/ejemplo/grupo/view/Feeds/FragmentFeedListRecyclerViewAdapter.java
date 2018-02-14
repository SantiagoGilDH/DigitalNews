package com.ejemplo.grupo.view.Feeds;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.FeedController;
import com.ejemplo.grupo.model.Feed;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentFeedListRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Feed> listToShow = new ArrayList<>();

    public FragmentFeedListRecyclerViewAdapter(List<Feed> feedList) {
        this.listToShow = feedList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_list_recycler_view_detail, parent, false);

        FeedViewHolder aFeedViewHolder = new FeedViewHolder(itemView);

        return aFeedViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Feed aFeed = listToShow.get(position);

        FeedViewHolder aFeedViewHolder = (FeedViewHolder) holder;

        aFeedViewHolder.bindFeed(aFeed);

    }

    @Override
    public int getItemCount() {
        return this.listToShow.size();
    }

    private static class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private SwitchCompat switchCompactToggleView;
        //private ImageView imageViewPhoto;
        private CircularImageView circularImageView;
        private FeedController feedController;
        private final List<Character> doNotEndTitlesWithTheseCharacters = Arrays.asList(':', ',', ';', '-', '"', '/', '+');
        private SwitchCompat switchCompat;


        public FeedViewHolder(View itemView) {
            super(itemView);
            
            textViewTitle = (TextView) itemView.findViewById(R.id.TextViewRecyclerViewDetailTitle);
            //imageViewPhoto = (ImageView) itemView.findViewById(R.id.ImageViewRecyclerViewDetailPhoto);
            circularImageView = (CircularImageView) itemView.findViewById(R.id.ImageViewRecyclerViewDetailPhoto);
            switchCompactToggleView = (SwitchCompat) itemView.findViewById(R.id.SwitchCompactToggleSuscribed);

        }


        public void bindFeed( final Feed aFeed) {

            Integer maxTitleLength = 70;

            textViewTitle.setText(this.buildTitle(aFeed.getTitle(), maxTitleLength));
            //Picasso.with(itemView.getContext()).load(aFeed.getIconLink()).placeholder(R.drawable.loading).error(R.drawable.error).into(imageViewPhoto);
            //Picasso.with(itemView.getContext()).load(aFeed.getIconLink()).placeholder(R.drawable.loading).error(R.drawable.error).into(circularImageView);


            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(R.drawable.error)
                    .resetViewBeforeLoading(true)
                    .showImageOnFail(R.drawable.error)
                    .showImageOnLoading(R.drawable.loading).bitmapConfig(Bitmap.Config.RGB_565).build();

            //imageLoader.displayImage(aFeed.getIconLink(), imageViewPhoto, options);
            imageLoader.displayImage(aFeed.getIconLink(), circularImageView, options);

            switchCompactToggleView.setChecked(aFeed.getSubscribedTo()>0);

            switchCompactToggleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (switchCompactToggleView.isChecked()){
                        Snackbar.make(v, v.getResources().getString(R.string.subscribed) + " "  + aFeed.getTitle() ,Snackbar.LENGTH_SHORT).show();
                    } else { Snackbar.make(v, v.getResources().getString(R.string.unsubscribed) + " " + aFeed.getTitle(), Snackbar.LENGTH_SHORT).show();}

                    feedController = new FeedController(itemView.getContext());
                    feedController.toggleSubscribed(aFeed,itemView.getContext());


                }
            });
        }


        public String buildTitle(String originalTitle, Integer maxLength) {

            String editedTitle;

            if (originalTitle.length() <= maxLength) {
                return originalTitle;
            }

            editedTitle = originalTitle.substring(0, maxLength);
            editedTitle = editedTitle.substring(0, editedTitle.lastIndexOf(' '));

            while (endsInAWeirdCharacter(editedTitle)) {
                editedTitle = editedTitle.substring(0, editedTitle.length() - 1);
            }

            editedTitle = editedTitle + "...";

            return editedTitle;
        }

        public Boolean endsInAWeirdCharacter(String aString){

            return !(doNotEndTitlesWithTheseCharacters.indexOf(aString.charAt(aString.length() - 1)) == -1);
        }

    }
}

