package com.segmnf.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.segmnf.myapplication.Adapter.QuestionAdminQuizAdapter;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.databinding.ActivityAddQuizBinding;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddQuizActivity extends AppCompatActivity {
    ActivityAddQuizBinding binding;

    private FirebaseDatabase database;
    private int days = 0, month = 0, year = 0;
    String arr[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
    DatePickerDialog datePickerDialog;
    QuizModel model;
    ArrayList<QuizQuestionModel> list = new ArrayList<>();
    SharedPreferences prefs;


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
        binding = ActivityAddQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        list = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        binding.imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        binding.datequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(AddQuizActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        if(String.valueOf(dayOfMonth).length()==1)
                        binding.datequiz.setText("0"+dayOfMonth + " " + arr[month]);
                        else
                            binding.datequiz.setText(dayOfMonth + " " + arr[month]);
                    }
                }, year, month, days);
                datePickerDialog.show();
            }
        });
        binding.starttimequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        binding.starttimequiz.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                };
                new TimePickerDialog(AddQuizActivity.this, timeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), false).show();
            }
        });
        binding.endtimequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        binding.endtimequiz.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                };
                new TimePickerDialog(AddQuizActivity.this, timeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), false).show();
            }
        });

        binding.selectquestionsquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] count = new String[1];
                database.getReference().child("Extras").child("quiz").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         count[0] = (snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                QuestionAdminQuizAdapter adapter = new QuestionAdminQuizAdapter(list, count[0]);
                list.clear();
                database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.exists()) {
                                    QuizQuestionModel model = snapshot1.getValue(QuizQuestionModel.class);
                                    if(model.getQuizid().equals(""))
                                    list.add(model);

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        if(list.size()==0)
                            Toast.makeText(AddQuizActivity.this, "Please add questions first", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.dialog_choosequesquiz);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView2);
                TextView close = (TextView) dialog.findViewById(R.id.closequestionlist);
                dialog.show();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                LinearLayoutManager manager = new LinearLayoutManager(AddQuizActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        binding.hostquizbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("QuestionList", Context.MODE_PRIVATE);
                if (binding.namequiz.getText().toString().trim().length() > 0 && binding.topicquiz.getText().toString().trim().length() > 0 &&
                        binding.visibilityquiz.getText().toString().toLowerCase(Locale.ROOT).trim().length() > 0 && binding.rulesquiz.getText().toString().trim().length() > 0 &&
                        binding.questioncountquiz.getText().toString().trim().length() > 0 && binding.durationquiz.getText().toString().trim().length() > 0 && binding.difficultyquiz.getText().toString().trim().length() > 0 &&
                        binding.starttimequiz.getText().toString().trim().length() > 0 && binding.endtimequiz.getText().toString().trim().length() > 0 &&
                        binding.datequiz.getText().toString().trim().length() > 0 &&
                        binding.isresultvisiblequiz.getText().toString().toLowerCase(Locale.ROOT).trim().length() > 0) {
                    database.getReference().child("Extras").child("quiz").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int count = Integer.parseInt(snapshot.getValue().toString());
                                database.getReference().child("Extras").child("quiz").setValue(String.valueOf(count + 1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()) {
                                            model = new QuizModel(FirebaseAuth.getInstance().getUid(), String.valueOf(count), binding.namequiz.getText().toString().trim(), binding.topicquiz.getText().toString().trim(),
                                                    binding.visibilityquiz.getText().toString().toLowerCase(Locale.ROOT).trim(), binding.rulesquiz.getText().toString().trim(),
                                                    binding.questioncountquiz.getText().toString().trim(), binding.durationquiz.getText().toString().trim(), "0", "false", binding.difficultyquiz.getText().toString().trim(),
                                                    "", binding.starttimequiz.getText().toString().trim(), binding.endtimequiz.getText().toString().trim(),
                                                    binding.datequiz.getText().toString().trim(),
                                                    "0", binding.isresultvisiblequiz.getText().toString().toLowerCase(Locale.ROOT).trim());

                                            Log.d("ad ", model.getQuestioncount());
                                            String quizquesids = "";
                                            ArrayList<String> listnew = getListProductModel("list");
                                            if (listnew.size() == Integer.parseInt(model.getQuestioncount())) {
                                                for (int i = 0; i < listnew.size(); i++) {
                                                    quizquesids = quizquesids + " " + listnew.get(i);
                                                    database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(listnew.get(i).toString()).child("quizid")
                                                            .setValue(model.getQuizid());
                                                }
                                                model.setQuid(quizquesids.trim());

                                            }
                                            database.getReference().child("Quizzes").child(model.getQuizid()).setValue(model);
                                            Toast.makeText(AddQuizActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    public ArrayList<String> getListProductModel(String key) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> list = gson.fromJson(prefs.getString(key, ""), type);
        if (list == null) {
            list = new ArrayList<String>();
        }
        return list;
    }
}