package com.segmnf.myapplication.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.AddQuizActivity;
import com.segmnf.myapplication.AdminDashboardActivity;
import com.segmnf.myapplication.LearnCActivity;
import com.segmnf.myapplication.LearnCSharpActivity;
import com.segmnf.myapplication.LearnCppActivity;
import com.segmnf.myapplication.LearnJavaActivity;
import com.segmnf.myapplication.LearnJavascriptActivity;
import com.segmnf.myapplication.LearnPyActivity;
import com.segmnf.myapplication.LearnRubyActivity;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.NewsActivity;
import com.segmnf.myapplication.ProfileActivity;
import com.segmnf.myapplication.R;
import com.segmnf.myapplication.Utils.LeaderboardModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private FirebaseDatabase database;
    int nexx=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ArrayList<LeaderboardModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView nexxpoints = view.findViewById(R.id.streak);

        TextView name = view.findViewById(R.id.username);
        ImageView pp = view.findViewById(R.id.pp);
        ImageView stonks = view.findViewById(R.id.stonks);

        ImageView news = view.findViewById(R.id.imageView4);
        TextView contests = view.findViewById(R.id.contestsdone);
        TextView quizzes = view.findViewById(R.id.quizesdone);
        CardView java = view.findViewById(R.id.java);
        CardView c = view.findViewById(R.id.c);
        CardView cpp = view.findViewById(R.id.cpp);
        CardView js = view.findViewById(R.id.javascript);
        CardView csharp = view.findViewById(R.id.csharp);
        CardView ruby = view.findViewById(R.id.ruby);
        CardView py = view.findViewById(R.id.py);



        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnJavaActivity.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnCActivity.class));
            }
        });
        cpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnCppActivity.class));
            }
        });
        csharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnCSharpActivity.class));
            }
        });
        ruby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnRubyActivity.class));
            }
        });
        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnJavascriptActivity.class));
            }
        });

        py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), LearnPyActivity.class));
            }
        });


        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        if(snapshot1.exists()){
                            ContestModel model = snapshot1.getValue(ContestModel.class);
                            if(model.getTough().toLowerCase(Locale.ROOT).equals("easy"))
                                nexx= nexx+2;
                            else  if(model.getTough().toLowerCase(Locale.ROOT).equals("medium"))
                                nexx= nexx+4;
                            else  if(model.getTough().toLowerCase(Locale.ROOT).equals("hard"))
                                nexx= nexx+8;
                        }
                    }
                    nexxpoints.setText(nexx+"");
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
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        if(snapshot1.exists()){
                            QuizModel model = snapshot1.getValue(QuizModel.class);
                            if(model.getDifficulty().toLowerCase(Locale.ROOT).equals("easy"))
                                nexx= nexx+2;
                            else  if(model.getDifficulty().toLowerCase(Locale.ROOT).equals("medium"))
                                nexx= nexx+4;
                            else  if(model.getDifficulty().toLowerCase(Locale.ROOT).equals("hard"))
                                nexx= nexx+8;
                        }
                    }
                    nexxpoints.setText(nexx+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nexxpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Dialog myDialog = new Dialog(getContext());
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                myDialog.setContentView(R.layout.layout_info_dialog);
                myDialog.setCancelable(false);
                myDialog.setCanceledOnTouchOutside(true);

                myDialog.show();
                myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        stonks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(getContext());
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                myDialog.setContentView(R.layout.layout_info_dialog);
                myDialog.setCancelable(false);
                myDialog.setCanceledOnTouchOutside(true);

                myDialog.show();
                myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    contests.setText(+snapshot.getChildrenCount() + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    quizzes.setText(+snapshot.getChildrenCount() + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel model = snapshot.getValue(UserModel.class);
                    name.setText(model.getName());
                    if (getContext() != null)
                        Glide.with(getContext()).load(model.getImage()).into(pp);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                startActivity(intent);
            }
        });
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        CardView card;
        switch (day) {
            case Calendar.SUNDAY:
                card = view.findViewById(R.id.cardSunday);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.MONDAY:
                card = view.findViewById(R.id.cardMon);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.TUESDAY:
                card = view.findViewById(R.id.cardTue);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.WEDNESDAY:
                card = view.findViewById(R.id.card);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.THURSDAY:
                card = view.findViewById(R.id.cardThu);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.FRIDAY:
                card = view.findViewById(R.id.cardFri);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;
            case Calendar.SATURDAY:
                card = view.findViewById(R.id.cardSat);
                card.setCardBackgroundColor(Color.parseColor("#AC0000"));
                break;

        }

        return view;
    }

}
