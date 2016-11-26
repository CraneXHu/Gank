package me.pkhope.gank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import me.pkhope.gank.api.GankApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pkhope on 16/11/25.
 */

public class GankRetrofit {

    public static final String HOST = "http://gank.io/api/";
    private static GankRetrofit gankRetrofit = null;

    private GankApi gankApi = null;


    private GankRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    public static GankRetrofit getInstance(){

        if (gankRetrofit == null){
            synchronized (GankRetrofit.class){
                if (gankRetrofit == null){
                    gankRetrofit = new GankRetrofit();
                }
            }
        }
        return gankRetrofit;
    }

    public GankApi gankApi(){
        return gankApi;
    }
}
