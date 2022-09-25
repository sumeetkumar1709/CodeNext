package com.segmnf.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Adapter.HistoryContestAdapter;
import com.segmnf.myapplication.Adapter.HistoryQuizAdapter;
import com.segmnf.myapplication.Adapter.YourActivityAdapter;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.databinding.ActivityAddContestBinding;
import com.segmnf.myapplication.databinding.ActivityHistoryBinding;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;

    private FirebaseDatabase database;
    ArrayList<QuizModel> list = new ArrayList<>();



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
    public void hideStatus() {
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        list = new ArrayList<>();

        binding.imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list.clear();
//        Toast.makeText(this, "" + getIntent().getStringExtra("type"), Toast.LENGTH_SHORT).show();
        if (getIntent().getStringExtra("type").equals("quiz")) {
            list.clear();
            binding.textView20.setText("Quizzes");
            RecyclerView recy = binding.historyrecyeler;
            list = new ArrayList<>();
            HistoryQuizAdapter adapter = new HistoryQuizAdapter(list);
            LinearLayoutManager manager = new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false);
            recy.setLayoutManager(manager);
            recy.setAdapter(adapter);
            database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        if (s.exists()) {
                            QuizModel model2 = s.getValue(QuizModel.class);
                            if (model2.getAdminid().equals(FirebaseAuth.getInstance().getUid()))
                                list.add(model2);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            list.clear();
            binding.textView20.setText("Contests");
            RecyclerView recy = binding.historyrecyeler;
            ArrayList<ContestModel> list = new ArrayList<>();
            HistoryContestAdapter adapter = new HistoryContestAdapter(list);
            LinearLayoutManager manager = new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false);
            recy.setLayoutManager(manager);
            recy.setAdapter(adapter);
            database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        if (s.exists()) {
                            ContestModel model2 = s.getValue(ContestModel.class);
//                            Toast.makeText(HistoryActivity.this, "" + model2.getAdminid(), Toast.LENGTH_SHORT).show();
                            if (model2.getAdminid().equals(FirebaseAuth.getInstance().getUid()))
                                list.add(model2);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}