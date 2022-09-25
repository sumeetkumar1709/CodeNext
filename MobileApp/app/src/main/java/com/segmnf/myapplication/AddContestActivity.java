package com.segmnf.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.segmnf.myapplication.Adapter.QuestionAdminContestAdapter;
import com.segmnf.myapplication.Adapter.QuestionAdminQuizAdapter;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.databinding.ActivityAddContestBinding;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddContestActivity extends AppCompatActivity {
    ActivityAddContestBinding binding;

    private FirebaseDatabase database;
    private int days = 0, month = 0, year = 0;
    String arr[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
    DatePickerDialog datePickerDialog;
    ContestModel model;
    ArrayList<QuestionModel> list = new ArrayList<>();
    SharedPreferences prefs;
    int marks=0;


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
        binding = ActivityAddContestBinding.inflate(getLayoutInflater());
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

        binding.datecontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(AddContestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        if(String.valueOf(dayOfMonth).length()==1)
                            binding.datecontest.setText("0"+dayOfMonth + " " + arr[month]);
                        else
                            binding.datecontest.setText(dayOfMonth + " " + arr[month]);
                    }
                }, year, month, days);
                datePickerDialog.show();
            }
        });
        binding.starttimecontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        binding.starttimecontest.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                };
                new TimePickerDialog(AddContestActivity.this, timeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), false).show();
            }
        });
        binding.endtimecontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        binding.endtimecontest.setText(simpleDateFormat.format(calendar1.getTime()));
                    }
                };
                new TimePickerDialog(AddContestActivity.this, timeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), false).show();
            }
        });

        binding.selectquestionscontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] count = new String[1];
                database.getReference().child("Extras").child("contest").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        count[0] = (snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                QuestionAdminContestAdapter adapter = new QuestionAdminContestAdapter(list, count[0]);
                list.clear();
                database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.exists()) {
                                    QuestionModel model = snapshot1.getValue(QuestionModel.class);
                                    if(model.getContestid().equals(""))
                                        list.add(model);

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        if(list.size()==0)
                            Toast.makeText(AddContestActivity.this, "Please add questions first", Toast.LENGTH_SHORT).show();
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
                LinearLayoutManager manager = new LinearLayoutManager(AddContestActivity.this, LinearLayoutManager.VERTICAL, false);
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
        binding.hostcontestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("QuestionList", Context.MODE_PRIVATE);
                if (binding.namecontest.getText().toString().trim().length() > 0 &&
                        binding.contestdurationadd.getText().toString().trim().length() > 0 &&
                        binding.difficultycontest.getText().toString().toLowerCase(Locale.ROOT).trim().length() > 0
                        && binding.questioncountcontest.getText().toString().trim().length() > 0 &&
                        binding.endtimecontest.getText().toString().trim().length() > 0 &&
                        binding.starttimecontest.getText().toString().trim().length() > 0
                        && binding.datecontest.getText().toString().trim().length() > 0 ) {

                    database.getReference().child("Extras").child("contest").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int count = Integer.parseInt(snapshot.getValue().toString());
                                database.getReference().child("Extras").child("contest").setValue(String.valueOf(count + 1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()) {

                                            model = new ContestModel(binding.namecontest.getText().toString().trim(),
                                                    binding.datecontest.getText().toString().trim(),
                                                    binding.contestdurationadd.getText().toString().trim(),
                                                    binding.questioncountcontest.getText().toString().trim(),
                                                    "", String.valueOf(count), binding.starttimecontest.getText().toString().trim(),
                                                    binding.endtimecontest.getText().toString().trim(),"",
                                                   FirebaseAuth.getInstance().getUid(),
                                                    "0","0","0",binding.difficultycontest.getText().toString().toLowerCase(Locale.ROOT).trim(), binding.editoriallink.getText().toString().trim());



                                            String quizquesids = "";
                                            ArrayList<String> listnew = getListProductModel("listcontest");
                                            if (listnew.size() == Integer.parseInt(model.getQuestions())) {
                                                for (int i = 0; i < listnew.size(); i++) {
                                                    quizquesids = quizquesids + " " + listnew.get(i);

                                                    database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("questions").child(listnew.get(i).toString()).child("contestid")
                                                            .setValue(model.getId());
                                                    database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("questions").child(listnew.get(i).toString()).child("marks").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            marks= marks+Integer.parseInt(snapshot.getValue().toString());
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }
                                                model.setQid(quizquesids.trim());

                                            }
                                            database.getReference().child("Contests").child(model.getId()).setValue(model);
                                            Toast.makeText(AddContestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
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