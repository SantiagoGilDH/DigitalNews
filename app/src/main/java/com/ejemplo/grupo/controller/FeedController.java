package com.ejemplo.grupo.controller;

import android.content.Context;

import com.ejemplo.grupo.dao.FeedDAO;
import com.ejemplo.grupo.model.Feed;

import java.util.List;

/**
 * Created by lab on 03-Jul-16.
 */
public class FeedController {

    private Context context;
    private FeedDAO feedDAO;

    public FeedController(Context context) {
        this.context = context;
        feedDAO = new FeedDAO(context);
    }

    public List<Feed> getFeedList(String aCategory){

        List<Feed> feedList;
        feedList = feedDAO.getFeedList(aCategory);

        return feedList;
    }

    public void toggleSubscribed(Feed aFeed, Context context){
        feedDAO.toggleSubscribed(aFeed, context);
    }

    public List<Feed> getSubscribedFeedList(String category){
        return feedDAO.getSubscribedFeedList(category);
    }


}
