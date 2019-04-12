package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.newsApi;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.ArticlesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("v2/everything")
    Observable<ArticlesResponse> getArticles (@Query("q") String query, @Query("from") String from, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);
}