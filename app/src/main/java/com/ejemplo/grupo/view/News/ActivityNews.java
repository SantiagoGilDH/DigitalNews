package com.ejemplo.grupo.view.News;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.NewsController;
import com.ejemplo.grupo.model.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivityNews extends AppCompatActivity {

    private Context context;
    private Bundle bundle;
    private ViewPager viewPager;
    private List<News> newsList;
    private Intent intent;
    private String category;
    private String dateToShow;
    private FloatingActionButton floatingActionButton;
    private NewsController newsController;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_layout);
        context = this;
        intent = getIntent();
        bundle = intent.getExtras();
        category = bundle.getString(FragmentNewsList.CATEGORY);
        dateToShow = bundle.getString(FragmentNewsList.DATETOSHOW);
        Integer position = bundle.getInt("position");
        newsController = new NewsController(context);


        newsList = (ArrayList<News>) bundle.getSerializable("NewsList");


        viewPager = (ViewPager) findViewById(R.id.View_Pager_News);
        final FragmentNewsAdapter adapterViewPager = new FragmentNewsAdapter(getSupportFragmentManager(), newsList, category);


        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(position);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                News currentNews = newsList.get(viewPager.getCurrentItem());
                isChecked(currentNews);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.floatingCoordinator);


        News currentNews = newsList.get(viewPager.getCurrentItem());
        isChecked(currentNews);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                News currentNews = newsList.get(viewPager.getCurrentItem());

                if (!newsController.isSaved(currentNews.getGuid())) {
                    floatingActionButton.setImageResource(R.drawable.removeicon);
                    newsController.addNewsToSaved(currentNews);
                    Snackbar.make(coordinatorLayout,v.getResources().getString(R.string.news_added), Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(v.getContext(), "News added to Saved", Toast.LENGTH_SHORT).show();

                } else {
                    floatingActionButton.setImageResource(R.drawable.addicon);
                    newsController.removeNewsFromSaved(currentNews.getGuid());
                    Snackbar.make(v,v.getResources().getString(R.string.news_removed), Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(v.getContext(), "News removed from saved", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_news, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.open_in_new:
//
//                String link = newsList.get(viewPager.getCurrentItem()).getLink();
//                Bundle aBundle = new Bundle();
//                aBundle.putString(FragmentNewsList.LINK, link);
//                aBundle.putString(FragmentNewsList.CATEGORY, category);
//                Intent intent = new Intent(context, ActivityFullNews.class);
//                intent.putExtras(aBundle);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void isChecked(News aNews){
        if (newsController.isSaved(aNews.getGuid())) {
            floatingActionButton.setImageResource(R.drawable.removeicon);
        } else {floatingActionButton.setImageResource(R.drawable.addicon);}
    }

    public void openInFull(View view){

        String link = newsList.get(viewPager.getCurrentItem()).getLink();

        Bundle aBundle = new Bundle();
        aBundle.putString(FragmentNewsList.LINK, link);
        aBundle.putString(FragmentNewsList.CATEGORY, category);
        Intent intent = new Intent(context, ActivityFullNews.class);
        intent.putExtras(aBundle);
        startActivity(intent);
    }
}
