package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.Article;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.BaseView;

import java.util.List;

public class ArticlesContract {
    public interface View extends BaseView<Presenter> {
        void getArticlesSuccess(List<Article> result);
    }

    interface Presenter  {
        void getArticles();
        void unsubscribe();
    }
}
