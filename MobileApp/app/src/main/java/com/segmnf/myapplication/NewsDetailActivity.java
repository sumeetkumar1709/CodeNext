package com.segmnf.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.Model.NewsModel;
import com.segmnf.myapplication.databinding.ActivityNewsDetailBinding;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {
    ArrayList<NewsModel> list;
    ActivityNewsDetailBinding binding;
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
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        String content = intent.getStringExtra("content");
        String image = intent.getStringExtra("image");
        if(content.equals("null"))
        {
            content="An error occured while fetching the news, Please retry";
        }

        if(name.equals("null"))
        {
            name= "Anonymous";
        }

        String s= time.trim().substring(0,10);

        binding.authorName.setText(name);
        binding.newsTitle.setText(title);
        binding.date.setText(s);
        binding.newsContent.setText(content);
        Glide.with(NewsDetailActivity.this).load(image).placeholder(R.drawable.ic_undraw_profile_pic_ic5t).into(binding.NewsImage);
    }
}