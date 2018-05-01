package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.R;


public class ArticleDescription extends Fragment {
    ImageView imageView;
    WebView webView;
    ProgressBar progressBar;
    ScrollView scrollView;
    private SetWebBackArticleDesc setWebBackArticle;

    public ArticleDescription() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_description, container, false);

        Bundle bundle = getArguments();
        String dec = bundle.getString("desc1");

        imageView = (ImageView) view.findViewById(R.id.articleDescImage);
        webView = (WebView) view.findViewById(R.id.articleDecsWeb);
        progressBar = (ProgressBar) view.findViewById(R.id.articleDescProgress);
        scrollView = (ScrollView) view.findViewById(R.id.scrollArticleDesc);


        Glide.with(getContext()).load( "http://bhaktidarshan.in/manage_panel/" + bundle.getString("img")).into(imageView);

        webView.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>"+ dec, "text/html", "utf-8", null);
/*
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                scrollView.setScrollY(0);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (setWebBackArticle != null) {

                    setWebBackArticle.onsetWebBackViewArticleDesc(webView, imageView);
                    imageView.setVisibility(View.VISIBLE);

                }
                imageView.setVisibility(View.GONE);

                return false;

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        return view;
    }

    public void setInterfaceArticleDesc(Article setWebBackArticle) {
        this.setWebBackArticle = setWebBackArticle;
    }

    public interface SetWebBackArticleDesc {
        public void onsetWebBackViewArticleDesc(WebView webView, ImageView imageView);
    }

}
