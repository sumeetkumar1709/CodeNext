package com.segmnf.myapplication;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Adapter.QuestionInsideAdapter;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.Utils.LeaderboardModel;
import com.segmnf.myapplication.databinding.ActivityContestQuestionBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class ContestQuestionActivity extends AppCompatActivity {
    ActivityContestQuestionBinding binding;
    private FirebaseDatabase database;
    Long millis;
    QuestionInsideAdapter adapter;
    String contestid;
    ArrayList<QuestionModel> list = new ArrayList<>();
    String qid;
    String start;
    int finalValue;
    private Shape.DrawableShape drawableShape = null;
    String duration;

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
        binding = ActivityContestQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        Intent intent = getIntent();
        qid = intent.getStringExtra("qid");
        String name = intent.getStringExtra("name");
        duration = intent.getStringExtra("duration");
        contestid = intent.getStringExtra("id");
        String adminid = intent.getStringExtra("adminid");
        binding.contestnameinside.setText(name);

        adapter = new QuestionInsideAdapter(list, ContestQuestionActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(ContestQuestionActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.questionrecycler.setLayoutManager(manager);
        binding.questionrecycler.setAdapter(adapter);


        String[] splitStr = qid.trim().split("\\s+");
//        this.contestid = contestid;
//        this.name = name;
//        this.avgtime = avgtime;
//        this.difficulty = difficulty;
//        this.marks = marks;
//        this.description = description;
//        this.eg1 = eg1;
//        this.eg2 = eg2;
//        this.constraints = constraints;
//        this.testcases = testcases;
//        this.testoutputs = testoutputs;
//        this.cpu = cpu;
//        this.memory = memory;
//        database.getReference().child("Admins").child(adminid).child("questions").child("23").setValue(new QuestionModel("2","Maximum Number of Pairs in Array"
//        ,"60","Hard","30",
//                "You are given a 0-indexed integer array nums. In one operation, you may do the following:\n" +
//                "\n" +
//                "Choose two integers in nums that are equal.\n" +
//                "Remove both integers from nums, forming a pair.\n" +
//                "The operation is done on nums as many times as possible.\n" +
//                "\n" +
//                "Return a 0-indexed integer array answer of size 2 where answer[0] is the number of pairs that are formed and answer[1] is the number of leftover integers in nums after doing the operation as many times as possible."
//        ,"Input: nums = [1,3,2,1,3,2,2]\n\n" +
//                "Output: [3,1]\n\n" +
//                "Explanation:\n" +
//                "Form a pair with nums[0] and nums[3] and remove them from nums. Now, nums = [3,2,3,2,2].\n" +
//                "Form a pair with nums[0] and nums[2] and remove them from nums. Now, nums = [2,2,2].\n" +
//                "Form a pair with nums[0] and nums[1] and remove them from nums. Now, nums = [2].\n" +
//                "No more pairs can be formed. A total of 3 pairs have been formed, and there is 1 number leftover in nums.",
//                "Input: nums = [1,1]\n\n" +
//                        "Output: [1,0]\n\n" +
//                        "Explanation: Form a pair with nums[0] and nums[1] and remove them from nums. Now, nums = [].\n" +
//                        "No more pairs can be formed. A total of 1 pair has been formed, and there are 0 numbers leftover in nums.",
//                "1 <= nums.length <= 100\n" +
//                        "0 <= nums[i] <= 100","4\n3\n1 2 3\n4\n5 5 5 5\n4\n2 2 1 1\n3\n1 1 2","2\n0\n2\n1","1.00","6000000","","23"));
//        list.clear();
//        for (int i = 0; i < splitStr.length; i++) {
//            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(id).child("Question").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if (snapshot.exists()) {
//                        QuestionModel model1 = snapshot.getValue(QuestionModel.class);
////                        Toast.makeText(getApplicationContext(), ""+model1.getAvgtime(), Toast.LENGTH_SHORT).show();
//                        list.add(model1);
//
//                        adapter.notifyDataSetChanged();
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();

                for (int i = 0; i < splitStr.length; i++) {
                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("Question").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                QuestionModel model1 = snapshot.getValue(QuestionModel.class);
//                        Toast.makeText(getApplicationContext(), ""+model1.getAvgtime(), Toast.LENGTH_SHORT).show();
                                list.add(model1);

                                adapter.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        list.clear();
        adapter.notifyDataSetChanged();

        for (int i = 0; i < splitStr.length; i++) {
            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("Question").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        QuestionModel model1 = snapshot.getValue(QuestionModel.class);
//                        Toast.makeText(getApplicationContext(), ""+model1.getAvgtime(), Toast.LENGTH_SHORT).show();
                        list.add(model1);

                        adapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                    ContestModel contestModel = snapshot.getValue(ContestModel.class);
                    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                    start = contestModel.getStart();
                    Date end = null;
                    try {
                        end = formatter.parse(contestModel.getEnd());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date dateNow = null;
                    try {
                        dateNow = formatter.parse(currentTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    CountDownTimer mCountDownTimer = new CountDownTimer(end.getTime() - dateNow.getTime(), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millis = millisUntilFinished;
                            binding.timerinside.setText(String.format("%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                        }

                        @Override
                        public void onFinish() {
                            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("submissiontime").setValue(duration + ":00");

                            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    database.getReference().child("LeaderBoard").child(contestid).child(FirebaseAuth.getInstance().getUid()).
                                            setValue(new LeaderboardModel(FirebaseAuth.getInstance().getUid(), snapshot.getValue().toString(), duration + ":00"));

                                    finalValue = Integer.parseInt(snapshot.getValue().toString());
                                    final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
                                    drawableShape = new Shape.DrawableShape(drawable, true);
                                    Dialog myDialog = new Dialog(ContestQuestionActivity.this);
                                    myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    myDialog.setContentView(R.layout.dialog_progress);
                                    myDialog.setCanceledOnTouchOutside(false);
                                    myDialog.setCancelable(true);
                                    KonfettiView konfettiView = myDialog.findViewById(R.id.konfettiView);
                                    myDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

                                    myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialogInterface) {

                                            KonfettiView konfettiView = myDialog.findViewById(R.id.konfettiView);
                                            final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
                                            drawableShape = new Shape.DrawableShape(drawable, true);

                                            EmitterConfig emitterConfig = new Emitter(2, TimeUnit.SECONDS).perSecond(30);
                                            konfettiView.start(
                                                    new PartyFactory(emitterConfig)
                                                            .angle(Angle.RIGHT - 45)
                                                            .spread(Spread.WIDE)
                                                            .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                                                            .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                                                            .setSpeedBetween(10f, 30f)
                                                            .position(new Position.Relative(0.0, 0.5))
                                                            .build(),
                                                    new PartyFactory(emitterConfig)
                                                            .angle(Angle.LEFT + 45)
                                                            .spread(Spread.WIDE)
                                                            .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                                                            .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                                                            .setSpeedBetween(10f, 30f)
                                                            .position(new Position.Relative(1.0, 0.5))
                                                            .build()
                                            );

                                            int initialValue = 0;
                                            TextView counting = myDialog.findViewById(R.id.textView12);


                                            ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
                                            valueAnimator.setDuration(1000);
                                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                @Override
                                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                    counting.setText("Scored: " + valueAnimator.getAnimatedValue().toString());
                                                }
                                            });
                                            valueAnimator.start();
                                            TextView time = myDialog.findViewById(R.id.textView14);
                                            time.setText("Time: " + duration + ":00" + " mins");

                                        }
                                    });
                                    myDialog.show();
                                    myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                        @Override
                                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                                dialog.cancel();

                                                finish();
                                                return true;
                                            }
                                            return false;
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }.start();
//
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.finalSubmittt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Date startt = null;
                try {
                    startt = formatter.parse(start + ":00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date dateNow = null;
                try {
                    dateNow = formatter.parse(currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long millis = dateNow.getTime() - startt.getTime();
                long minutes
                        = TimeUnit.MILLISECONDS.toMinutes(millis);

                long seconds
                        = (TimeUnit.MILLISECONDS.toSeconds(millis)
                        % 60);


                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("submissiontime").setValue(minutes + ":" + seconds);
                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        database.getReference().child("LeaderBoard").child(contestid).child(FirebaseAuth.getInstance().getUid()).
                                setValue(new LeaderboardModel(FirebaseAuth.getInstance().getUid(), snapshot.getValue().toString(), minutes + ":" + seconds));
                        Toast.makeText(ContestQuestionActivity.this, "" + snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                        finalValue = Integer.parseInt(snapshot.getValue().toString());
                        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
                        drawableShape = new Shape.DrawableShape(drawable, true);
                        Dialog myDialog = new Dialog(ContestQuestionActivity.this);
                        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        myDialog.setContentView(R.layout.dialog_progress);
                        myDialog.setCanceledOnTouchOutside(false);
                        myDialog.setCancelable(true);
                        KonfettiView konfettiView = myDialog.findViewById(R.id.konfettiView);
                        myDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

                        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {

                                KonfettiView konfettiView = myDialog.findViewById(R.id.konfettiView);
                                final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
                                drawableShape = new Shape.DrawableShape(drawable, true);

                                EmitterConfig emitterConfig = new Emitter(2, TimeUnit.SECONDS).perSecond(30);
                                konfettiView.start(
                                        new PartyFactory(emitterConfig)
                                                .angle(Angle.RIGHT - 45)
                                                .spread(Spread.WIDE)
                                                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                                                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                                                .setSpeedBetween(10f, 30f)
                                                .position(new Position.Relative(0.0, 0.5))
                                                .build(),
                                        new PartyFactory(emitterConfig)
                                                .angle(Angle.LEFT + 45)
                                                .spread(Spread.WIDE)
                                                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                                                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                                                .setSpeedBetween(10f, 30f)
                                                .position(new Position.Relative(1.0, 0.5))
                                                .build()
                                );

                                int initialValue = 0;
                                TextView counting = myDialog.findViewById(R.id.textView12);

                                ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
                                valueAnimator.setDuration(1000);
                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        counting.setText("Scored: " + valueAnimator.getAnimatedValue().toString());
                                    }
                                });
                                valueAnimator.start();
                                TextView time = myDialog.findViewById(R.id.textView14);
                                time.setText("Time: " + minutes + ":" + seconds + " mins");

                            }
                        });
                        myDialog.show();
                        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    dialog.cancel();

                                    finish();
                                    return true;
                                }
                                return false;
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    public Long getMillis() {
        return millis;
    }

    public String getduration() {
        return duration;
    }

    private void updatemillis() //Call this method to refresh time
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ContestQuestionActivity.this, "" + millis, Toast.LENGTH_SHORT).show();
                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("millis").setValue(millis.toString());
                    }

                    ;
                });
            }
        }, 0, 10000);//1000 is a Refreshing Time (1second)
    }

}