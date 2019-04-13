package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.R;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.databinding.FragmentArticleBinding;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.Article;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.BUNDLE_ARTICLE;

public class ArticleFragment extends Fragment{
    private FragmentArticleBinding mBinder;
    private Article mArticle;
    public interface OnBackToArticles {
        void getArticles();
    }

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false);
        View view = mBinder.getRoot();
        if (getArguments() != null) {
            final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
            final String json = getArguments().getString(BUNDLE_ARTICLE, null);
            mArticle = gson.fromJson(json, Article.class);
        }
        loadArticle();
        return view;
    }

    private void loadArticle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mBinder.webview.setWebContentsDebuggingEnabled(true);
        }
        mBinder.webview.getSettings().setLoadWithOverviewMode(true);
        mBinder.webview.getSettings().setUseWideViewPort(true);

        mBinder.webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocusFromTouch();
                        }
                        break;
                }
                return false;
            }
        });

        mBinder.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        mBinder.webview.setWebChromeClient(new WebChromeClient());
        mBinder.webview.loadUrl(mArticle.getUrl());
    }
}
