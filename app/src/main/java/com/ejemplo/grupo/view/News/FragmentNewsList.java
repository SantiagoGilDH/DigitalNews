package com.ejemplo.grupo.view.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.NewsController;
import com.ejemplo.grupo.model.News;
import com.ejemplo.grupo.util.HTTPConnectionManager;
import com.ejemplo.grupo.util.ResultListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentNewsList extends Fragment {

    private String title;
    private List<News> newsList;
    private RecyclerView aRecyclerView;
    private NewsController newsController;
    private FragmentNewsListRecyclerViewAdapter aFragmentNewsListRecyclerViewAdapter;
    private ListenerNews aListenerNews;
    private SwipeRefreshLayout pullToRefresh;
    private SwipeRefreshLayout pullToRefreshEmptyState;
    private SwipeRefreshLayout pullToRefreshEmptyStateSaved;
    private String aCategory;



    public static final String CATEGORY = "category";
    public static final String LINK = "link";
    public static final String GUID = "guid";
    public static final String DATETOSHOW = "dateToShow";


    public static FragmentNewsList fragmentsGiver(String category){
        FragmentNewsList aFragmentNewsList = new FragmentNewsList();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        aFragmentNewsList.setArguments(args);
        aFragmentNewsList.setTitle(category.toUpperCase());
        return aFragmentNewsList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.fragment_news_list_layout, container, false);
        Bundle aBundle = getArguments();
        aCategory = aBundle.getString(CATEGORY);

        pullToRefresh = (SwipeRefreshLayout)fragmentView.findViewById(R.id.pullToRefresh);
        pullToRefreshEmptyState = (SwipeRefreshLayout)fragmentView.findViewById(R.id.SwipeEmptyState);
        pullToRefreshEmptyStateSaved = (SwipeRefreshLayout)fragmentView.findViewById(R.id.SwipeEmptyStateSaved);

        newsList = new ArrayList<>();
        newsController = new NewsController(fragmentView.getContext());

        aRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.RecyclerViewFragmentNewsList);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        aRecyclerView.setHasFixedSize(true);

        aListenerNews = new ListenerNews();



        update(aCategory);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(aCategory);
            }
        });

        pullToRefreshEmptyState.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(aCategory);
            }
        });

        pullToRefreshEmptyStateSaved.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update(aCategory);
            }
        });




        return fragmentView;
    }

    public void update(final String aCategory){
        pullToRefreshEmptyStateSaved.setRefreshing(true);
        pullToRefreshEmptyState.setRefreshing(false);
        pullToRefresh.setRefreshing(false);
        
        if(newsController.getNewsList(aCategory).size()==0 && aCategory.equals("saved")){
            pullToRefresh.setVisibility(View.GONE);
            pullToRefreshEmptyState.setVisibility(View.GONE);
            pullToRefreshEmptyStateSaved.setVisibility(View.VISIBLE);




        }else if(!HTTPConnectionManager.isNetworkingOnline(getContext()) &&  !aCategory.equals("saved")){

            pullToRefresh.setVisibility(View.GONE);
            pullToRefreshEmptyStateSaved.setVisibility(View.GONE);
            pullToRefreshEmptyState.setVisibility(View.VISIBLE);


        }
        else
        {

            pullToRefresh.setVisibility(View.VISIBLE);
            pullToRefreshEmptyState.setVisibility(View.GONE);
            pullToRefreshEmptyStateSaved.setVisibility(View.GONE);

            if(aCategory.equals("saved")){
                newsList = newsController.getNewsList(aCategory);
                aFragmentNewsListRecyclerViewAdapter = new FragmentNewsListRecyclerViewAdapter(newsList);
                aRecyclerView.setAdapter(aFragmentNewsListRecyclerViewAdapter);
                aFragmentNewsListRecyclerViewAdapter.setOnClickListener(aListenerNews);
                aFragmentNewsListRecyclerViewAdapter.notifyDataSetChanged();
            } else{

                newsController.getNewsFromInternet(new ResultListener<List<News>>() {
                    @Override
                    public void finish(List<News> result) {
                        aFragmentNewsListRecyclerViewAdapter = new FragmentNewsListRecyclerViewAdapter(result);
                        aRecyclerView.setAdapter(aFragmentNewsListRecyclerViewAdapter);
                        aFragmentNewsListRecyclerViewAdapter.setOnClickListener(aListenerNews);
                        aFragmentNewsListRecyclerViewAdapter.notifyDataSetChanged();
                        newsList=result;

                    }
                },getContext(), aCategory);

            }


        }

        pullToRefresh.setRefreshing(false);
        pullToRefreshEmptyState.setRefreshing(false);
        pullToRefreshEmptyStateSaved.setRefreshing(false);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }



    private class ListenerNews implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            Bundle bundle = new Bundle();
            bundle.putInt("position", aRecyclerView.getChildAdapterPosition(view));
            //bundle.putString(CATEGORY, getArguments().getString(CATEGORY));
            bundle.putString(CATEGORY, aCategory);
            bundle.putString(LINK, newsList.get(aRecyclerView.getChildAdapterPosition(view)).getLink());
            bundle.putString(DATETOSHOW, newsList.get(aRecyclerView.getChildAdapterPosition(view)).getDateToShow());
            bundle.putString(GUID, newsList.get(aRecyclerView.getChildAdapterPosition(view)).getGuid());

            Gson gson = new Gson();

            bundle.putSerializable("NewsList", (ArrayList<News>) newsList);
            Intent intent = new Intent(getContext(), ActivityNews.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
}
