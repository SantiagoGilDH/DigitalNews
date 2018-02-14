package com.ejemplo.grupo.controller;

import android.content.Context;
import android.widget.Toast;

import com.ejemplo.grupo.dao.NewsDAO;
import com.ejemplo.grupo.model.News;
import com.ejemplo.grupo.util.HTTPConnectionManager;
import com.ejemplo.grupo.util.ResultListener;
import com.ejemplo.grupo.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esteban Perini on 16/6/2016.
 */
public class NewsController {

    Context context;
    NewsDAO newsDAO;

    public NewsController(Context context) {
        this.context = context;
        newsDAO = new NewsDAO(context);
    }

    public List<News> getNewsList(String aCategory){
        List<News> newsList;
        newsList = newsDAO.getNewsList(aCategory,context);
        return newsList;
    }

    public void getNewsFromInternet(final ResultListener<List<News>> listener, Context context, String aCategory){
        NewsDAO newsDAO = new NewsDAO(context);

        newsDAO.getNewsFromInternet(new ResultListener<List<News>>() {
            @Override
            public void finish(List<News> resultado) {
                listener.finish(resultado);
            }
        }, aCategory, context);
    }

    public boolean isSaved(String guid){
        return newsDAO.isSaved(guid);
    }

    public void addNewsToSaved(News aNews){
        if(!newsDAO.isSaved(aNews.getGuid())){
            newsDAO.addNewsToSaved(aNews);
        } else {
            Toast.makeText(context, "News is already saved", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeNewsFromSaved(String guid){
        newsDAO.removeNewsFromSaved(guid);
    }

    public int getPositionToFocus(){
        if(HTTPConnectionManager.isNetworkingOnline(context)){
            return 0;
        }
        else{
            return 5;
        }
    }
}
