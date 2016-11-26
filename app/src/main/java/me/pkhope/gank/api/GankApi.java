package me.pkhope.gank.api;

import me.pkhope.gank.model.Gank;
import me.pkhope.gank.model.GankItem;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pkhope on 16/11/25.
 */

public interface GankApi {

    @GET("data/{type}/" + 10 + "/{page}")
    Observable<Gank> getGankData(@Path("type") String type, @Path("page") int page);

    @GET("search/query/{query}/category/{type}/count/" + 10 + "/page/{page}")
    Observable<Gank> getSearchData(@Path("query") String query, @Path("type") String type, @Path("page") int page);
}
