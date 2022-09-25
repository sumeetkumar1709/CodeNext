package com.segmnf.myapplication;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiHandler {

    private static final String BASE_URL = "https://api.jdoodle.com/v1/";

    public static final String API_ID = "681fc3e94a1a80550c41aaa7ab53ede4";
    public static final String API_SECRET = "a12c4c9f6354909cb749ecae8774aac3025853f1d40b80bde7e4fd0c611528b";



    private static Retrofit retrofit;

    public static ApiService getRetrofitInstance() {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}