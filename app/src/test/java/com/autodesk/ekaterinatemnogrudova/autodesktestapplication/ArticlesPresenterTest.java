package com.autodesk.ekaterinatemnogrudova.autodesktestapplication;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.Article;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.ArticlesResponse;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui.ArticlesContract;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui.ArticlesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArticlesPresenterTest {
    private ArticlesPresenter presenter;
    private TestSchedulerProvider testSchedulerProvider;
    private TestScheduler testScheduler;
    @Mock
    private ArticlesContract.View mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations
        // Mock scheduler using RxJava TestScheduler.
        testScheduler = new TestScheduler();
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);

        // Make presenter a mock while using mock repository and viewContract created above
        presenter  = new ArticlesPresenter(mView, testSchedulerProvider);
    }

    @Test
    public void handleGetArticleResponse_Success(){
        presenter.getArticles();
    }
}