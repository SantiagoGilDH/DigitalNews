package com.ejemplo.grupo.view.News;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentNewsListAdapter extends FragmentStatePagerAdapter {

    public List<FragmentNewsList> fragmentNewsListList;

    // This generates the list of news fragments, every news category plus saved
    public FragmentNewsListAdapter(FragmentManager fm){
        super(fm);
        fragmentNewsListList = new ArrayList<>();
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("Business"));
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("Technology"));
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("Gadgets"));
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("Finance"));
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("Travel"));
        fragmentNewsListList.add(FragmentNewsList.fragmentsGiver("saved"));
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentNewsListList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentNewsListList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.fragmentNewsListList.get(position).getTitle();
    }
}

