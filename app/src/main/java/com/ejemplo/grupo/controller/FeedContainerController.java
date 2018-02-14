package com.ejemplo.grupo.controller;

import android.content.Context;

import com.ejemplo.grupo.dao.FeedContainerDAO;
import com.ejemplo.grupo.model.Feed;

import java.util.List;

/**
 * Created by lab on 11-Jul-16.
 */
public class FeedContainerController {

    private Context context;
    private FeedContainerDAO feedContainerDAO;

    public FeedContainerController(Context context){
        this.context = context;
        this.feedContainerDAO = new FeedContainerDAO(context);
    }

    public List<Feed> getAllFeeds(){
        return this.feedContainerDAO.getAllFeeds();
    }

}