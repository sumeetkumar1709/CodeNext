package com.segmnf.myapplication;


import android.text.TextUtils;
import android.util.Log;

import com.segmnf.myapplication.Model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JsonData {

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
//            Log.e("LOG_TAG", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
//                Log.e("LOG_TAG", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
//            Log.e("LOG_TAG", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<NewsModel> extractFeatureFromJson(String nameJSON) {
        if (TextUtils.isEmpty(nameJSON)) {
            return null;
        }

        ArrayList<NewsModel> news = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(nameJSON);

            JSONArray nameArray = baseJsonResponse.getJSONArray("articles");

            for (int i = 0; i < nameArray.length(); i++) {

                JSONObject News = nameArray.getJSONObject(i);

                String name = News.getString("author");
                String title = News.getString("title");
                String description = News.getString("description");
                String image = News.getString("urlToImage").toString();
                String time = News.getString("publishedAt");
                String content = News.getString("content");

                news.add(new NewsModel(title,description,content,name,image,time));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
//            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        System.out.println("nameJSON = " + news);
        return news;
    }
    public static ArrayList<NewsModel> fetchNameData(String requestUrl) {

        URL url = createUrl(requestUrl);
        System.out.println("requestUrl = " + url);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            System.out.println("requestUrl = " + jsonResponse);
        } catch (IOException e) {
            Log.d("LOG_TAG", "Problem making the HTTP request.", e);
        }

        ArrayList<NewsModel> newsModelList = extractFeatureFromJson(jsonResponse);

        return newsModelList;
    }

}

