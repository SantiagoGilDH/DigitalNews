package com.ejemplo.grupo.view.News;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.NewsController;
import com.ejemplo.grupo.model.News;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentNewsListRecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private View.OnClickListener listener;
    private List<News> listToShow = new ArrayList<>();

    public FragmentNewsListRecyclerViewAdapter(List<News> newsList) {
        this.listToShow = newsList;
    }

    public void setOnClickListener(View.OnClickListener aListener) {
        listener = aListener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_list_recycler_view_detail, parent, false);

        NewsViewHolder aNewsViewHolder = new NewsViewHolder(itemView);

        itemView.setOnClickListener(this);

        return aNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News aNews = listToShow.get(position);

        NewsViewHolder aNewsViewHolder = (NewsViewHolder) holder;

        aNewsViewHolder.bindNews(aNews);

    }

    @Override
    public int getItemCount() {
        return this.listToShow.size();
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDate;
        private TextView textViewTitle;
        private ImageView imageViewPhoto;
        private NewsController newsController;
        private final List<Character> doNotEndTitlesWithTheseCharacters = Arrays.asList(':', ',', ';', '-', '"', '/', '+');
        private CircularImageView feedIcon;
        private FrameLayout frameLayout;


        public NewsViewHolder(View itemView) {
            super(itemView);

            newsController = new NewsController(itemView.getContext());
            textViewTitle = (TextView) itemView.findViewById(R.id.TextViewRecyclerViewDetailTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.TextViewDate);
            imageViewPhoto = (ImageView) itemView.findViewById(R.id.ImageViewRecyclerViewDetailPhoto);
            feedIcon = (CircularImageView) itemView.findViewById(R.id.feedIcon);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayoutNoImage);


        }

        public void bindNews(final News aNews) {

            Integer maxTitleLength = 70;

            textViewTitle.setText(this.buildTitle(aNews.getTitle(), maxTitleLength));
            textViewDate.setText(this.buildDate(aNews.getDateInMilliseconds()));

            //Picasso.with(itemView.getContext()).load(aNews.getImage()).placeholder(R.drawable.loading).error(R.drawable.error).into(imageViewPhoto);



            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(R.drawable.error)
                    .resetViewBeforeLoading(true)
                    .showImageOnFail(R.drawable.error)
                    .showImageOnLoading(R.drawable.loading).bitmapConfig(Bitmap.Config.RGB_565).build();

            imageLoader.displayImage(aNews.getImage(), imageViewPhoto, options);

            if(aNews.getImage()==null){
                imageViewPhoto.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }else {
                frameLayout.setVisibility(View.GONE);
                imageViewPhoto.setVisibility(View.VISIBLE);
            }

            imageLoader.displayImage(aNews.getFeedIconLink(), feedIcon, options);


        }

        public String buildDate (long dateInMiliseconds ){

            long currentTimeInMiliseconds = System.currentTimeMillis();
            long difference = currentTimeInMiliseconds - dateInMiliseconds;
            Integer differenceInMinutes = (int) ((difference / 1000) / 60);

            if(differenceInMinutes > 59){
                Integer differenceInHours = differenceInMinutes / 60;

                if(differenceInHours > 23){

                    Integer differenceInDays = differenceInHours /24;

                    if(differenceInDays == 1){
                        return "1 day ago";
                    } else{
                        return differenceInDays + " days ago";
                    }
                } else{
                    if(differenceInHours == 1){
                        return "1 hour ago";
                    }else {
                        return differenceInHours + " hours ago";
                    }
                }
            }
            else{
                if(differenceInMinutes == 1){
                    return "1 minute ago";
                }else {
                    return differenceInMinutes + " minutes ago";
                }
            }

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

