package com.ejemplo.grupo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.ejemplo.grupo.controller.FeedController;
import com.ejemplo.grupo.model.Feed;
import com.ejemplo.grupo.model.News;
import com.ejemplo.grupo.util.DAOException;
import com.ejemplo.grupo.util.HTTPConnectionManager;
import com.ejemplo.grupo.util.ResultListener;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Esteban Perini on 16/6/2016.
 */
public class NewsDAO extends SQLiteOpenHelper{

    private static final String DATABASENAME = "NewsDB";
    private static final Integer DATABASEVERSION = 1;

    private static final String TABLESAVEDNEWS = "savedNews";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String ID = "id";
    private static final String GUID = "guid";
    private static final String LINK = "link";
    private static final String FEEDICONLINK = "feedIconLink";
    private static final String DATEINMS = "dateInMS";

    public static List<News> savedNews = new ArrayList<>();
    public static Context context;

    public NewsDAO(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String createTable = "CREATE TABLE " + TABLESAVEDNEWS + "("
                            + ID + " INTEGER PRIMARY KEY,"
                            + TITLE + " TEXT,"
                            + DESCRIPTION + " TEXT,"
                            + IMAGE + " TEXT, "
                            + LINK + " TEXT, "
                            + FEEDICONLINK + " TEXT, "
                            + DATEINMS + " NUMBER, "
                            + GUID + " TEXT " + ")";

        database.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean isSaved(String guid){

        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLESAVEDNEWS
                + " WHERE " + GUID + " = '" + guid + "'";

        Cursor result = database.rawQuery(selectQuery, null);
        Integer count = result.getCount();

        Log.v("News", "News " + guid + " is already on the database");

        database.close();

        return (count > 0);
    }

    public void addNewsToSaved(News news) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues row = new ContentValues();

        row.put(TITLE, news.getTitle());
        row.put(DESCRIPTION, news.getDescription());
        row.put(IMAGE, news.getImage());
        row.put(GUID, news.getGuid());
        row.put(LINK, news.getLink());
        row.put(FEEDICONLINK, news.getFeedIconLink());
        row.put(DATEINMS, news.getDateInMilliseconds());

        database.insert(TABLESAVEDNEWS, null, row);
        database.close();
    }

    public void removeNewsFromSaved(String guid) {

        SQLiteDatabase database = getWritableDatabase();

        String deleteNews = "DELETE FROM " + TABLESAVEDNEWS
                            + " WHERE " +  GUID + "= '" +  guid + "'";

        database.execSQL(deleteNews);
        database.close();
    }

    public List<News> getSavedNews() {
        SQLiteDatabase database = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLESAVEDNEWS;
        Cursor cursor = database.rawQuery(selectQuery, null);

        List<News> newsList = new ArrayList<>();
        while(cursor.moveToNext()){

            //TOMO LOS DATOS DE CADA PERSONA
            News news = new News();
            news.setSaved(true);
            news.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            news.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            news.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            news.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
            news.setLink(cursor.getString(cursor.getColumnIndex(LINK)));
            news.setFeedIconLink(cursor.getString(cursor.getColumnIndex(FEEDICONLINK)));
            news.setDateInMilliseconds(cursor.getLong(cursor.getColumnIndex(DATEINMS)));

            //AGREGO LA PERSONA A LA LISTA
            newsList.add(news);
        }

        database.close();
        return newsList;
    }


    public List<News> getNewsList(String aCategory, Context context) {

        if (aCategory.equals("saved")){
            return getSavedNews();
        }

        List<News> result = new ArrayList<>();

//        XmlPullParser parser = Xml.newPullParser();
//
//
//        try {
//            AssetManager manager = context.getAssets();
//            InputStream input = manager.open(aCategory + ".xml");
//            parser.setInput(input, null);
//            Integer status = parser.getEventType();
//
//            News currentNews = null;
//
//            while (status != XmlPullParser.END_DOCUMENT) {
//                switch (status) {
//                    case XmlPullParser.START_DOCUMENT:
//                        result = new ArrayList<>();
//                        break;
//                    case XmlPullParser.START_TAG:
//                        if (parser.getName().equals("item")) {
//                            currentNews = new News();
//                        }
//                        if (currentNews != null) {
//                            if (parser.getName().equals("title")) {
//                                currentNews.setTitle(parser.nextText());
//                            } else if (parser.getName().equals("description")) {
//                                currentNews.setDescription(parser.nextText());
//                            } else if (parser.getName().equals("creator")) {
//                                currentNews.setAuthor(parser.nextText());
//                            } else if (parser.getName().equals("category")) {
//                                currentNews.setCategory(parser.nextText());
//                            } else if (parser.getName().equals("link")) {
//                                currentNews.setLink(parser.nextText());
//                            } else if (parser.getName().equals("guid")) {
//                                currentNews.setGuid(parser.nextText());
//                            } else if (parser.getName().equals("pubDate")) {
//                                currentNews.setPubDate(parser.nextText());
//                            } else if (parser.getName().equals("feedIconLink")) {
//                                currentNews.setFeedIconLink(parser.nextText());
//                            } else if (parser.getName().equals("enclosure")) {
//                                currentNews.setImage(parser.getAttributeValue(null, "url"));
//                                Log.d("INFO", currentNews.toString());
//
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        if (parser.getName().equals("item")) {
//                            Log.d("INFO", currentNews.toString());
//                            result.add(currentNews);
//                            currentNews = null;
//                        }
//                        break;
//                }
//                status = parser.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return result;
    }

    public void getNewsFromInternet(final ResultListener<List<News>> listener, String category, Context context) {
        RetrieveFeedTask retrieveFeedTask = new RetrieveFeedTask(listener, category, context);
        retrieveFeedTask.execute();
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, List<News>> {

        private ResultListener<List<News>> listener;
        private String category;
        private List<Feed> feedList = new ArrayList<>();
        private Context context;

        public RetrieveFeedTask(ResultListener<List<News>> listener, String category, Context context) {
            this.listener = listener;
            this.category = category;
            this.context = context;

            FeedController feedController = new FeedController(context);
            List<Feed> feedList = feedController.getSubscribedFeedList(category);
            for (Feed aFeed : feedList){
                this.feedList.add(aFeed);
            }



        }

        @Override
        protected List<News> doInBackground(String... params) {

            List<News> result = new ArrayList<>();
            int flag = 0;

            for (Feed aFeed : feedList) {

                HTTPConnectionManager connectionManager = new HTTPConnectionManager();
                InputStream input = null;

                try {
                    input = connectionManager.getRequestStream(aFeed.getFeedLink());
                } catch (DAOException e) {
                    e.printStackTrace();
                }

                XmlPullParser parser = Xml.newPullParser();
                News currentNews = null;
                try {
                    parser.setInput(input, null);
                    Integer status = parser.getEventType();
                    while (status != XmlPullParser.END_DOCUMENT) {
                        switch (status) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                if (parser.getName().equals("item")) {
                                    currentNews = new News();
                                    flag=0;
                                }
                                if (currentNews != null) {
                                    if (parser.getName().equals("title")) {
                                        if(currentNews.getTitle()==null){
                                            currentNews.setTitle(parser.nextText());
                                        }
                                    } else if (parser.getName().equals("description")) {
                                        currentNews.setDescription(parser.nextText());
                                    } else if (parser.getName().equals("creator")) {
                                        currentNews.setAuthor(parser.nextText());
                                    } else if (parser.getName().equals("category")) {
                                        currentNews.setCategory(parser.nextText());
                                    } else if (parser.getName().equals("link")) {
                                        currentNews.setLink(parser.nextText());
                                    } else if (parser.getName().equals("guid")) {
                                        currentNews.setGuid(parser.nextText());
                                    } else if (parser.getName().equals("pubDate")) {
                                        currentNews.setPubDate(parser.nextText());
                                    } else if (parser.getName().equals("thumbnail") && currentNews.getImage()==null) {
                                        currentNews.setImage(parser.getAttributeValue(null, "url"));
                                    } else if (parser.getName().equals("content")) {
                                        if(currentNews.getImage()==null && !category.equals("technology")){
                                            currentNews.setImage(parser.getAttributeValue(null, "url"));
                                            if(aFeed.getFeedLink().equals("http://fourhourworkweek.com/blog/feed/") && flag==0){
                                                currentNews.setImage(null);
                                                flag=1;
                                            }
                                        }
//                                        else if(!category.equals("gadgets")){
//                                            currentNews.setImage(parser.getAttributeValue(null, "url"));
//                                        }
                                    } 
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("item")) {
                                    if(currentNews.getGuid()==null){
                                        currentNews.setGuid(currentNews.getLink());
                                    }
                                    currentNews.setCategory(category);
                                    currentNews.setFeedIconLink(aFeed.getIconLink());
                                    result.add(currentNews);
                                    currentNews = null;
                                }
                                break;
                        }
                        status = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(List<News> input) {

//            for (News aNews : input){
//                Log.v("Before", aNews.getFeedIconLink());
//            }

            Collections.sort(input);

//            for (News aNews : input){
//                Log.v("After", aNews.getFeedIconLink());
//            }

            this.listener.finish(input);
        }
    }
}






