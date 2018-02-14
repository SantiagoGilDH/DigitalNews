package com.ejemplo.grupo.view.News;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ejemplo.grupo.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentNewsAdapter extends FragmentStatePagerAdapter {

    public List<FragmentNews> fragmentNewsList;
    Context context;

    public FragmentNewsAdapter(FragmentManager fm, List<News> newsList, String category) {
        super(fm);
        fragmentNewsList = new ArrayList<>();
        for (News news: newsList) {
            fragmentNewsList.add(FragmentNews.fragmentsGiver(news, category));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentNewsList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentNewsList.size();
    }
}
