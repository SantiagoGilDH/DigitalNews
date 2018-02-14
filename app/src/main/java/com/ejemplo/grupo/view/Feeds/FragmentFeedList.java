package com.ejemplo.grupo.view.Feeds;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.FeedController;
import com.ejemplo.grupo.model.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentFeedList extends Fragment {

    private String title;

    public List<Feed> feedList;
    RecyclerView aRecyclerView;
    FeedController feedController;
    FragmentFeedListRecyclerViewAdapter aFragmentFeedListRecyclerViewAdapter;
    SwipeRefreshLayout pullToRefresh;

    Context context;

    public static final String CATEGORY = "category";


    public static FragmentFeedList fragmentsGiver(String category){
        FragmentFeedList aFragmentFeedList = new FragmentFeedList();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        aFragmentFeedList.setArguments(args);
        aFragmentFeedList.setTitle(category.toUpperCase());
        return aFragmentFeedList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.fragment_feed_list_layout, container, false);
        Bundle aBundle = getArguments();
        final String aCategory = aBundle.getString(CATEGORY);

        pullToRefresh = (SwipeRefreshLayout)fragmentView.findViewById(R.id.pullToRefresh);

        feedList = new ArrayList<>();
        feedController = new FeedController(fragmentView.getContext());

        aRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.RecyclerViewFragmentFeedList);
        aRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        update(aCategory);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(aCategory);
            }
        });

        return fragmentView;
    }

    public void update(String aCategory){
        pullToRefresh.setRefreshing(true);
        feedList=feedController.getFeedList(aCategory);
        aFragmentFeedListRecyclerViewAdapter = new FragmentFeedListRecyclerViewAdapter(feedList);
        aRecyclerView.setAdapter(aFragmentFeedListRecyclerViewAdapter);
        aFragmentFeedListRecyclerViewAdapter.notifyDataSetChanged();
        pullToRefresh.setRefreshing(false);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
