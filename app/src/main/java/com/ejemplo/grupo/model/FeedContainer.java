package com.ejemplo.grupo.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.ejemplo.grupo.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lab on 03-Jul-16.
 */
public class FeedContainer
{

    @SerializedName("results")
    private List<Feed> feedList;

    public FeedContainer(){

        this.feedList = new ArrayList<>();

    }

    public List<Feed> getAllFeeds() {

        return feedList;
    }
}