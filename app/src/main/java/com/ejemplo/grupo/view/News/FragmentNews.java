package com.ejemplo.grupo.view.News;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ejemplo.grupo.R;
import com.ejemplo.grupo.controller.NewsController;
import com.ejemplo.grupo.model.News;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by digitalhouse on 14/06/16.
 */
public class FragmentNews extends Fragment {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PUBDATE = "pubDate";
    public static final String AUTHOR = "author";
    public static final String IMAGE = "image";
    public static final String CATEGORY = "category";
    public static final String SAVED = "saved";
    public static final String LINK = "link";
    public static final String GUID = "guid";
    public static final String DATETOSHOW = "dateToShow";

    private String title;
    private String description;
    private String image;
    private String link;
    private String guid;
    private String category;
    private News aNews;

    private FloatingActionButton floatingActionButton;
    private ShareLinkContent content;
    private ShareButton shareButton;
    private CallbackManager callbackManager;
    private ActivityNews context;
    private int color;
    private CircularImageView circularImageView;
    private String dateToShow;
    private ImageButton imageButton;

    public static FragmentNews fragmentsGiver(News news, String category){
        FragmentNews aFragmentNews = new FragmentNews();
        Bundle args = new Bundle();
        args.putString(TITLE, news.getTitle());
        args.putString(DESCRIPTION, news.getDescription());
        args.putString(PUBDATE, news.getPubDate());
        args.putString(AUTHOR, news.getAuthor());
        //args.putString(CATEGORY, news.getCategory());
        args.putString(CATEGORY, category);
        args.putString(IMAGE, news.getImage());
        args.putString(LINK, news.getLink());
        args.putBoolean(SAVED, news.getSaved());
        args.putString(GUID, news.getGuid());
        args.putString(DATETOSHOW, news.getDateToShow());
        aFragmentNews.setArguments(args);
        return aFragmentNews;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_news_layout, container, false);

        context = ((ActivityNews)getActivity());
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
        Bundle aBundle = getArguments();
        this.title = aBundle.getString(TITLE);
        this.description = aBundle.getString(DESCRIPTION);
        this.image = aBundle.getString(IMAGE);
        this.link = aBundle.getString(LINK);
        this.guid = aBundle.getString(GUID);
        this.category = aBundle.getString(CATEGORY);
        this.dateToShow = aBundle.getString(DATETOSHOW);

        aNews = new News();
        aNews.setTitle(title);
        aNews.setDescription(description);
        aNews.setLink(link);
        aNews.setImage(image);
        aNews.setGuid(guid);
        aNews.setCategory(category);
        aNews.setDateToShow(this.dateToShow);

        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.detail_toolbar);
        context.setSupportActionBar(toolbar);
        //context.getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
        //context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        toolbar.setTitle(category.toUpperCase());


        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) fragmentView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setBackgroundColor(Color.parseColor("#1e9ab3"));
        collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#1e9ab3"));
        collapsingToolbar.setContentScrimColor(Color.parseColor("#1e9ab3"));

        WebView webView = (WebView) fragmentView.findViewById(R.id.webViewNews);
        openWebView(webView,description);

        //webView.loadData(description, "text/html", null);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUseWideViewPort(true);

        TextView textViewTitle = (TextView) fragmentView.findViewById(R.id.TextViewLayoutTitle);
        ImageView imageViewPhoto = (ImageView) fragmentView.findViewById(R.id.imageViewNewsPhoto);
        textViewTitle.setText(title);
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.error)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.error)
                .showImageOnLoading(R.drawable.loading).build();

        imageLoader.displayImage(image, imageViewPhoto, options);

        if(aNews.getImage()==null){
            imageViewPhoto.setVisibility(View.GONE);
        }else {
            imageViewPhoto.setVisibility(View.VISIBLE);
        }

//       Bitmap bm=((BitmapDrawable)imageViewPhoto.getDrawable()).getBitmap();
//        Palette palette = Palette.generate(bm);
//        color = palette.getDarkMutedColor(0x000000);
//        collapsingToolbar.setBackgroundColor(color);
//        collapsingToolbar.setStatusBarScrimColor(color);
//        collapsingToolbar.setContentScrimColor(color);

        //Boton para compartir en facebook
        content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .build();

        imageButton = (ImageButton) fragmentView.findViewById(R.id.open_in_full);
        imageButton.setImageResource(R.drawable.icon_open);


        shareButton = (ShareButton)fragmentView.findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

        //Fecha de la noticia
        TextView textViewExactDate = (TextView) fragmentView.findViewById(R.id.TextViewExactDate);
        textViewExactDate.setText(dateToShow);



        /*
        //Boton para tweetear
        ImageView tweetButton = (ImageView) fragmentView.findViewById(R.id.tweet_button);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                if(session != null){

                    ComposerActivity.Builder composerActivityBuilder = new ComposerActivity.Builder(getContext());
                    composerActivityBuilder.session(session);
                    final Intent intent = composerActivityBuilder.createIntent();

                    startActivity(intent);

                }
                else {
                    Toast.makeText(FragmentNews.this.getContext(), "No estas logueado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */



        return fragmentView;
    }

    @SuppressLint("NewApi")
    private void openWebView(WebView webView,String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        String data = description;
        webView.loadDataWithBaseURL("file:///android_asset/", getHtmlData(data), "text/html", "utf-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
