package com.ejemplo.grupo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ejemplo.grupo.controller.FeedContainerController;
import com.ejemplo.grupo.model.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lab on 03-Jul-16.
 */
public class FeedDAO extends SQLiteOpenHelper{

    private FeedContainerController feedContainerController;

    private static final String DATABASENAME = "FeedsDB";
    private static final Integer DATABASEVERSION = 1;

    private static final String TABLEFEEDS = "savedFeeds";
    private static final String CATEGORY = "category";
    private static final String FEEDLINK = "feedLink";
    private static final String ICONLINK = "iconLink";
    private static final String ID = "id";
    private static final String SUBSCRIBEDTO = "subscribedTo";
    private static final String TITLE = "title";



    public FeedDAO(Context context){
        super(context, DATABASENAME, null, DATABASEVERSION);

        this.feedContainerController = new FeedContainerController(context);
    }

    @Override
    public void onCreate(SQLiteDatabase database){

        String createTable = "CREATE TABLE " + TABLEFEEDS + "("
                + ID + " INTEGER PRIMARY KEY,"
                + TITLE + " TEXT, "
                + FEEDLINK + " TEXT, "
                + ICONLINK + " TEXT, "
                + SUBSCRIBEDTO + " NUMERIC, "
                + CATEGORY + " TEXT " + ")";

        database.execSQL(createTable);

        List<Feed> allFeeds = feedContainerController.getAllFeeds();

        for (Feed i : allFeeds) {
            ContentValues row = new ContentValues();

            row.put(TITLE, i.getTitle());
            row.put(FEEDLINK, i.getFeedLink());
            row.put(ICONLINK, i.getIconLink());
            row.put(SUBSCRIBEDTO, i.getSubscribedTo());
            row.put(CATEGORY, i.getCategory());

            database.insert(TABLEFEEDS, null, row);
            Log.v("FEED", "Feed" + i.getTitle() + " has been inserted");

        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Feed> getFeedList(String aCategory){


        List<Feed> feedList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLEFEEDS
                + " WHERE " + CATEGORY + "== '" + aCategory + "'";

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor result = database.rawQuery(selectQuery, null);


        while (result.moveToNext()){
            Feed feed = new Feed();
            feed.setTitle(result.getString(result.getColumnIndex(TITLE)));
            feed.setCategory(result.getString(result.getColumnIndex(CATEGORY)));
            feed.setFeedLink(result.getString(result.getColumnIndex(FEEDLINK)));
            feed.setIconLink(result.getString(result.getColumnIndex(ICONLINK)));
            feed.setSubscribedTo(result.getInt(result.getColumnIndex(SUBSCRIBEDTO)));

            feedList.add(feed);

        }

        database.close();

        return feedList;
    }

    public List<Feed> getSubscribedFeedList(String aCategory){


        List<Feed> feedList = new ArrayList<>();

        String selectQuery = "SELECT *  FROM " + TABLEFEEDS
                  + " WHERE " + CATEGORY + "== \"" + aCategory + "\" "
                + " AND " + SUBSCRIBEDTO + "== 1;";

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor result = database.rawQuery(selectQuery, null);

        while (result.moveToNext()){
            Feed feed = new Feed();
            feed.setTitle(result.getString(result.getColumnIndex(TITLE)));
            feed.setCategory(result.getString(result.getColumnIndex(CATEGORY)));
            feed.setFeedLink(result.getString(result.getColumnIndex(FEEDLINK)));
            feed.setIconLink(result.getString(result.getColumnIndex(ICONLINK)));
            feed.setSubscribedTo(result.getInt(result.getColumnIndex(SUBSCRIBEDTO)));

            feedList.add(feed);

        }

        database.close();

        return feedList;
    }

    public boolean isSaved(String title){

        SQLiteDatabase database = getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLEFEEDS
                + " WHERE " + TITLE + "== '" + title + "'";

        Cursor result = database.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        Log.v("Feed", "Feed " + title + " is already on the Database");

        database.close();

        return (count > 0);
    }

    public void addFeedToDatabase(Feed feed) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues row = new ContentValues();

        row.put(TITLE, feed.getTitle());
        row.put(FEEDLINK, feed.getFeedLink());
        row.put(ICONLINK, feed.getIconLink());
        row.put(SUBSCRIBEDTO, feed.getSubscribedTo());
        row.put(CATEGORY, feed.getCategory());

        database.insert(TABLEFEEDS, null, row);
        database.close();
    }

    public void toggleSubscribed(Feed feed, Context context) {

        String updateQuery;

        SQLiteDatabase database = getWritableDatabase();

        if(feed.getSubscribedTo()>0){

            updateQuery = "UPDATE " + TABLEFEEDS
                    + " SET " + SUBSCRIBEDTO  + " = " + 0 + " "
                    + " WHERE " + TITLE + " == '" + feed.getTitle() + "';";


        } else {

            updateQuery = "UPDATE " + TABLEFEEDS
                    + " SET " + SUBSCRIBEDTO  + " = " + 1 + " "
                    + " WHERE " + TITLE + " == '" + feed.getTitle() + "';";

        }

        database.execSQL(updateQuery);

        database.close();

    }
}