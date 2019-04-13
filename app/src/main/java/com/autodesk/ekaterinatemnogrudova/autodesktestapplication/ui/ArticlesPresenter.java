package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui;

import android.util.Log;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.ArticlesResponse;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.newsApi.NewsService;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.IScheduler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.API_KEY;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.QUERY_BITCOIN;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.QUERY_PUBLISHED;

public class ArticlesPresenter implements ArticlesContract.Presenter {
    private ArticlesContract.View mView;
    private Disposable mDisposable;
    private IScheduler mScheduler;
    private NewsService mNewsService;

    public ArticlesPresenter(ArticlesContract.View view, NewsService newsService, IScheduler scheduler) {
        mView = view;
        mView.setPresenter(this);
        mScheduler = scheduler;
        mNewsService = newsService;
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void getArticles() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        mDisposable = mNewsService.getArticles(QUERY_BITCOIN, formattedDate, QUERY_PUBLISHED, API_KEY)
                    .subscribeOn(mScheduler.io())
                    .observeOn(mScheduler.ui()).subscribeWith(new DisposableObserver<ArticlesResponse>() {
            @Override
            public void onNext(ArticlesResponse response) {
                mView.getArticlesSuccess(response.getArticles());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ERROR", "!!!!");
            }

            @Override
            public void onComplete() {
            }

        });
    }
}
