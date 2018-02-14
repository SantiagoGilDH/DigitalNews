package com.ejemplo.grupo.view.News;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.NewsController;

/**
 * Created by Esteban Perini on 1/7/2016.
 */
public class OptionNewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.option_news_fragment_layout, container, false);

        ViewPager viewPager = (ViewPager) fragmentView.findViewById(R.id.View_Pager_Categorias_Main);
        FragmentNewsListAdapter adapterViewPager = new FragmentNewsListAdapter(getActivity().getSupportFragmentManager());
        NewsController newsController = new NewsController(getContext());

        viewPager.setClipToPadding(false);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(newsController.getPositionToFocus());

        TabLayout tabLayout = (TabLayout) fragmentView.findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);


        return fragmentView;
    }
}
