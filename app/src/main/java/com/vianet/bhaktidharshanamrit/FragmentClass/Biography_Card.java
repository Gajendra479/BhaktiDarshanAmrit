package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Biography_adaptor;
import com.vianet.bhaktidharshanamrit.R;

public class Biography_Card extends Fragment {
    ImageView imageView;
    String title;
    ProgressBar progressBar;
    ScrollView scrollView;
    FloatingActionButton floatingActionButtonShareBio;
    WebView webView;
    String desc, thumb;
    private SetWebBack setWebBack;
    // TODO: Rename parameter arguments, choose names that match


    public Biography_Card() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        desc = bundle.getString("desc");
        thumb = bundle.getString("thumb");
        title = bundle.getString("name");

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biography__card, container, false);


        imageView = (ImageView) view.findViewById(R.id.bio_card_thumb);

        webView = (WebView) view.findViewById(R.id.webview1);
        scrollView = (ScrollView) view.findViewById(R.id.scroll);

        progressBar = (ProgressBar) view.findViewById(R.id.bio_card_prog);
        floatingActionButtonShareBio = (FloatingActionButton) view.findViewById(R.id.bioShareButton);

        Glide.with(getContext()).load(getString(R.string.path) + thumb).into(imageView);


        webView.loadDataWithBaseURL(null, desc, "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
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

                if (setWebBack != null) {

                    setWebBack.setWebView(webView, imageView, floatingActionButtonShareBio);
                    imageView.setVisibility(View.VISIBLE);
                    floatingActionButtonShareBio.setVisibility(View.VISIBLE);

                }

                imageView.setVisibility(View.GONE);
                floatingActionButtonShareBio.setVisibility(View.GONE);
                return false;

            }
        });


        floatingActionButtonShareBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(desc)+ getString(R.string.app_link));
                startActivity(Intent.createChooser(intent, "share this via :-"));

            }
        });

        return view;
    }

    public void setInterface(Biography_adaptor setWebBack) {
        this.setWebBack = setWebBack;
    }

    public interface SetWebBack {
        public void setWebView(WebView webView, ImageView imageView, FloatingActionButton floatingActionButton);
    }
}
