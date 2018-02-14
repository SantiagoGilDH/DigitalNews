package com.ejemplo.grupo.view.Feeds;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentFeedListAdapter extends FragmentStatePagerAdapter {

    public List<FragmentFeedList> fragmentFeedListList;

    public FragmentFeedListAdapter(FragmentManager fm){
        super(fm);
        fragmentFeedListList = new ArrayList<>();
        fragmentFeedListList.add(FragmentFeedList.fragmentsGiver("Business"));
        fragmentFeedListList.add(FragmentFeedList.fragmentsGiver("Technology"));
        fragmentFeedListList.add(FragmentFeedList.fragmentsGiver("Gadgets"));
        fragmentFeedListList.add(FragmentFeedList.fragmentsGiver("Finance"));
        fragmentFeedListList.add(FragmentFeedList.fragmentsGiver("Travel"));
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentFeedListList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentFeedListList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.fragmentFeedListList.get(position).getTitle();
    }
}

