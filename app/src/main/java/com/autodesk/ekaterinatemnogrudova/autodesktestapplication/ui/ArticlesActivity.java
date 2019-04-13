package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.R;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.databinding.ActivityArticlesBinding;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.Article;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.newsApi.NewsService;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.SchedulerProvider;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.BUNDLE_ARTICLE;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.FRAGMENT_ARTICLE;

public class ArticlesActivity extends AppCompatActivity implements ArticlesContract.View,
        ArticlesAdapter.IArticleClicked{
    ActivityArticlesBinding mBinder;
    private ArticlesContract.Presenter mPresenter;

    private List<Article> mArticles = new ArrayList<>();
    private ArticlesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_articles);
        mBinder.toolBar.setTitle(getString(R.string.activity_articles_tool_bar_title));
        setSupportActionBar(mBinder.toolBar);
        mPresenter = new ArticlesPresenter(this, new NewsService(), new SchedulerProvider());
        mBinder.articlesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ArticlesAdapter(mArticles, this);
        mBinder.articlesList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getArticles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void onArticleClick(Article currentArticle) {
        addBackToToolBar();
        mBinder.toolBar.setTitle(currentArticle.getTitle());
        ArticleFragment articleFragment = ArticleFragment.newInstance();
        Bundle articleItem = new Bundle();
        final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
        articleItem.putString(BUNDLE_ARTICLE, gson.toJson(currentArticle));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (articleItem != null) {
            articleFragment.setArguments(articleItem);
        }
        fragmentTransaction.replace(R.id.fragment_container, articleFragment, FRAGMENT_ARTICLE);
        fragmentTransaction.addToBackStack(FRAGMENT_ARTICLE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void setPresenter(ArticlesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getArticlesSuccess(List<Article> result) {
        mArticles.clear();
        mArticles.addAll(result);
        mAdapter.refreshList(mArticles);
    }

    public void removeIconFromToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void addBackToToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            removeIconFromToolBar();
            mBinder.toolBar.setTitle(getString(R.string.activity_articles_tool_bar_title));
            mPresenter.getArticles();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
