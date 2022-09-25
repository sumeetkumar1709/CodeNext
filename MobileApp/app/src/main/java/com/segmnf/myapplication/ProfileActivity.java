package com.segmnf.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.gesture.GestureLibraries;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Adapter.ContestAdapter;
import com.segmnf.myapplication.Adapter.YourActivityAdapter;
import com.segmnf.myapplication.Fragments.SettingBottomSheetFragment;
import com.segmnf.myapplication.Model.ActivityModel;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.NewsModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.databinding.ActivityNewsDetailBinding;
import com.segmnf.myapplication.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private FirebaseDatabase database;
    ArrayList<ActivityModel> list= new ArrayList<>();


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
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                binding.name.setText(model.getName());
                Glide.with(binding.pp).load(model.getImage()).into(binding.pp);
                if(model.getBio().equals("")){

                }
                else{
                    binding.bio.setText(model.getBio());
                }
                if(model.getRole().toLowerCase(Locale.ROOT).trim().equals("admin")){
                    binding.admin.setText("Admin: Yes");
                }
                else
                    binding.admin.setText("Admin: No");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        SettingBottomSheetFragment settingBottomSheetFragment = new SettingBottomSheetFragment();
                        settingBottomSheetFragment.show(getSupportFragmentManager(), settingBottomSheetFragment.getTag());

            }
        });

        RecyclerView recy = binding.activityrecycler;
        list= new ArrayList<>();
        YourActivityAdapter adapter = new YourActivityAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        recy.setLayoutManager(manager);
        recy.setAdapter(adapter);
        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot s:snapshot.getChildren())
                    {
                        if(s.exists()){
                            ContestModel model = s.getValue(ContestModel.class);
                            list.add(new ActivityModel(model.getName(), "Contest", model.getScore()));
                            adapter.notifyDataSetChanged();

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot s:snapshot.getChildren())
                    {
                        if(s.exists()){
                            QuizModel model = s.getValue(QuizModel.class);
                            if(model.getIsresultvisible().toLowerCase(Locale.ROOT).equals("true")) {
                                list.add(new ActivityModel(model.getName(), "Quiz", model.getUserscore()));
                            }
                            else
                                list.add(new ActivityModel(model.getName(), "Quiz", "-"));
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}