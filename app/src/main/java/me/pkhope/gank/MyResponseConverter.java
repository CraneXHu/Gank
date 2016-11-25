package me.pkhope.gank;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.pkhope.gank.model.Gank;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by pkhope on 16/11/25.
 */

public class MyResponseConverter<T> implements Converter<ResponseBody, T> {

    @Override
    public T convert(ResponseBody value) throws IOException {



        String json = value.string();
        Gank gank = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++){
                JSONObject obj = results.getJSONObject(i);
                if (!obj.has("images")){
                    obj.put("images", new JSONArray());
                }
            }
            Gson gson = new Gson();
            gank = gson.fromJson(jsonObject.toString(), Gank.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return (T)gank;
    }
}
