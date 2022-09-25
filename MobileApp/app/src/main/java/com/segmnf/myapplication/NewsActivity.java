package com.segmnf.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.Adapter.NewsAdapter;
import com.segmnf.myapplication.Model.NewsModel;
import com.segmnf.myapplication.databinding.ActivityNewsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends AppCompatActivity {
    ArrayList<NewsModel> list;
    ActivityNewsBinding binding;
    private FirebaseDatabase database;


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideStatus(){
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://newsapi.org/v2/top-headlines?language=en&category=technology&apiKey=0e842291f8b24303b084d97e5f352773";

        request();
    }

    public void request() {

        RequestQueue requestQueue;
        list = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(NewsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v2/top-headlines?language=en&category=technology&apiKey=0e842291f8b24303b084d97e5f352773", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("response = ", response.getString("status"));

                    JSONArray nameArray = response.getJSONArray("articles");

                    for (int i = 0; i < nameArray.length(); i++) {

                        JSONObject News = nameArray.getJSONObject(i);

                        String name = News.getString("author");
                        String title = News.getString("title");
                        String description = News.getString("description");
                        String image = News.getString("urlToImage");
                        String time = News.getString("publishedAt");
                        String content = News.getString("content");

                        System.out.println("name = " + name);
                        System.out.println("name = " + time);
                        System.out.println("name = " + title);
                        System.out.println("name = " + content);
                        System.out.println("name = " + description);

                        list.add(new NewsModel(title, description, content, name, image, time));
                    }


                    NewsAdapter adapter = new NewsAdapter(list);
                    LinearLayoutManager manager = new LinearLayoutManager(NewsActivity.this, LinearLayoutManager.VERTICAL,false);
                    binding.newsRecyclerview.setLayoutManager(manager);
                    binding.newsRecyclerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");

                return params;
            }

        };


        requestQueue.add(jsonObjectRequest);
    }


}
