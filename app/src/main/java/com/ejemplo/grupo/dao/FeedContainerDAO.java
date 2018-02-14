package com.ejemplo.grupo.dao;

import android.content.Context;
import android.content.res.AssetManager;

import com.ejemplo.grupo.model.Feed;
import com.ejemplo.grupo.model.FeedContainer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by lab on 03-Jul-16.
 */
public class FeedContainerDAO {

    private FeedContainer feedContainer;
    private Context context;

    public FeedContainerDAO(Context context) {

        this.context= context;
        this.feedContainer = getFeedContainer();
    }

    public FeedContainer getFeedContainer() {

        FeedContainer feedContainer = new FeedContainer();

        try {
            AssetManager manager = context.getAssets();
            InputStream rSSFeedJson = manager.open("RSSFeedShort.json");
            BufferedReader bufferedReaderIn = new BufferedReader(new InputStreamReader(rSSFeedJson));

            Gson gson = new Gson();

            feedContainer = gson.fromJson(bufferedReaderIn, FeedContainer.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return feedContainer;
    }

    public List<Feed> getAllFeeds(){
        return this.feedContainer.getAllFeeds();
    }
}