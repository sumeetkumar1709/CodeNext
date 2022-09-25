package com.segmnf.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Adapter.QuestionPagerAdapter;
import com.segmnf.myapplication.Utils.LeaderboardModel;
import com.segmnf.myapplication.databinding.ActivityContestQuestionDetailBinding;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class ContestQuestionDetailActivity extends AppCompatActivity {
    ActivityContestQuestionDetailBinding binding;
    private FirebaseDatabase database;
    String contestid;
    String name;
    String avgtime;
    String difficulty;
    String marks;
    String description;
    String eg1;
    String eg2;
    String constraints;
    String testcases;
    String testoutputs;
    String cpu;
    String memory;
    String status;
    String quid;
    Long millis;
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
        binding = ActivityContestQuestionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        TabLayout tabLayout = findViewById(R.id.tab_question);
        ViewPager2 viewPager2 = findViewById(R.id.profile_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("QUESTION"));
        tabLayout.addTab(tabLayout.newTab().setText("EDITOR"));
        tabLayout.addTab(tabLayout.newTab().setText("OUTPUT"));
        tabLayout.setTabRippleColor(null);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.setAdapter(new QuestionPagerAdapter(getSupportFragmentManager(), getLifecycle()));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FBC820"));
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                } else {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFA233"));

                    tabLayout.selectTab(tabLayout.getTabAt(position));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        Intent intent= getIntent();

        contestid= intent.getStringExtra("contestid");
        name= intent.getStringExtra("name");
        avgtime= intent.getStringExtra("avgtime");
        difficulty= intent.getStringExtra("difficulty");
        marks= intent.getStringExtra("marks");
        description= intent.getStringExtra("description");
        eg1= intent.getStringExtra("eg1");
        eg2= intent.getStringExtra("eg2");
        constraints= intent.getStringExtra("constraints");
        testcases= intent.getStringExtra("testcases");
        testoutputs= intent.getStringExtra("testoutput");
        cpu= intent.getStringExtra("cpu");
        memory= intent.getStringExtra("memory");
        status= intent.getStringExtra("status");
        quid = intent.getStringExtra("questionid");
        millis = Long.parseLong(intent.getStringExtra("millis"));
        duration= intent.getStringExtra("duration");

        CountDownTimer mCountDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millis = millisUntilFinished;
                binding.textView7.setText(String.format("%02d:%02d",
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
                        Dialog myDialog = new Dialog(ContestQuestionDetailActivity.this);
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
                                        counting.setText("Scored: "+valueAnimator.getAnimatedValue().toString());
                                    }
                                });
                                valueAnimator.start();
                                TextView time = myDialog.findViewById(R.id.textView14);
                                time.setText("Time: "+duration + ":00"+" mins");

                            }
                        });
                        myDialog.show();
                        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    dialog.cancel();
                                    Intent intent1 = new Intent(ContestQuestionDetailActivity.this, HomeActivity.class);
                                    startActivity(intent1);
                                    finishAffinity();
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

    }

    public String getContestid() {
        return contestid;
    }

    public String getName() {
        return name;
    }

    public String getAvgtime() {
        return avgtime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getMarks() {
        return marks;
    }

    public String getDescription() {
        return description;
    }

    public String getEg1() {
        return eg1;
    }

    public String getEg2() {
        return eg2;
    }

    public String getConstraints() {
        return constraints;
    }

    public String getTestcases() {
        return testcases;
    }

    public String getTestoutputs() {
        return testoutputs;
    }

    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public String getStatus() {
        return status;
    }

    public String getQuid() {
        return quid;
    }


}