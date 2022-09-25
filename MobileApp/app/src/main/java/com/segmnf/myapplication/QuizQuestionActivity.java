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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.databinding.ActivityQuizQuestionBinding;

import java.util.ArrayList;
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

public class QuizQuestionActivity extends AppCompatActivity {
    ActivityQuizQuestionBinding binding;
    private FirebaseDatabase database;
    ArrayList<QuizQuestionModel> list = new ArrayList<>();
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    TextView progress;
    String admin;
    String quiz;
    String namequiz;
    String duration;
    String quizids;
    String visibility;
    int a = -1, i = 0;
    String selectedoption = "", isresultvisible = "";
    private Shape.DrawableShape drawableShape = null;


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
        binding = ActivityQuizQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        list = new ArrayList<>();
        progressBar = findViewById(R.id.progress_bar);
        progress = findViewById(R.id.progress_text);


        Intent intent = getIntent();
        admin = intent.getStringExtra("admin");
        quiz = intent.getStringExtra("quiz");
        namequiz = intent.getStringExtra("namequiz");
        duration = intent.getStringExtra("duration");
        quizids = intent.getStringExtra("quizids");
        visibility = intent.getStringExtra("visibility");
        isresultvisible = intent.getStringExtra("isresultvisible");

        binding.quiznameinside.setText(namequiz);
        progressBar.setMax((int) (Double.parseDouble(intent.getStringExtra("duration")) * 60));
        startTimer(Double.parseDouble(intent.getStringExtra("duration")));


        binding.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("Question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    a = (int) snapshot.getChildrenCount();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        QuizQuestionModel quizQuestionModel = snapshot1.getValue(QuizQuestionModel.class);
                        list.add(quizQuestionModel);

                    }
                }
                binding.numberofquestions.setText("/" + a);
                if (i < list.size()) {
                    QuizQuestionModel model = list.get(i);
                    binding.quizquestiondisplay.setText(model.getQuestion());
                    binding.points.setText("Points: " + model.getMarks());
                    binding.option1.setText("A. " + model.getOp1());
                    binding.option2.setText("B. " + model.getOp2());
                    binding.option3.setText("C. " + model.getOp3());
                    binding.option4.setText("D. " + model.getOp4());
                    if(model.getImage().trim().length()>1)
                    {
                        Glide.with(binding.imageView11).load(model.getImage()).into(binding.imageView11);
                        binding.imageView11.setVisibility(View.VISIBLE);
                    }
                    binding.submitthisquestion.setText("Next");
                    binding.questionnumber.setText(String.valueOf(i + 1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.submitthisquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clickfn();
            }
        });

        binding.op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedoption = "1";
                binding.op1.setCardBackgroundColor(getResources().getColor(R.color.facebook));
                binding.op2.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op3.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op4.setCardBackgroundColor(Color.parseColor("#0079DA"));


            }
        });
        binding.op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedoption = "2";
                binding.op2.setCardBackgroundColor(getResources().getColor(R.color.facebook));
                binding.op1.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op3.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op4.setCardBackgroundColor(Color.parseColor("#0079DA"));


            }
        });

        binding.op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedoption = "3";
                binding.op3.setCardBackgroundColor(getResources().getColor(R.color.facebook));
                binding.op2.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op1.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op4.setCardBackgroundColor(Color.parseColor("#0079DA"));


            }
        });
        binding.op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedoption = "4";
                binding.op4.setCardBackgroundColor(getResources().getColor(R.color.facebook));
                binding.op2.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op3.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op1.setCardBackgroundColor(Color.parseColor("#0079DA"));


            }
        });
    }


    private void Clickfn() {
        if (list.size() > i) {
            countDownTimer.cancel();
            binding.imageView11.setVisibility(View.GONE);
            String correctoption = list.get(i).getCorrectop();

            if (selectedoption.equals(correctoption)) {
                binding.op1.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op2.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op3.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op4.setCardBackgroundColor(Color.parseColor("#0079DA"));

                selectedoption = "";
                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("userscore").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int oldscore = Integer.parseInt((String) snapshot.getValue());
                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("userscore")
                                .setValue(String.valueOf(oldscore + Integer.parseInt(list.get(i).getMarks()))).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        i++;
                                        if (i == list.size()) {
                                            Toast.makeText(getApplicationContext(), "Thanks for Submission", Toast.LENGTH_SHORT).show();
                                            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("isgiven").setValue("true");

                                            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("isgiven").setValue("true");
                                            if (isresultvisible.equals("true")) {
                                                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("userscore").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            int finalValue = Integer.parseInt(snapshot.getValue().toString());
                                                            final Drawable drawable = ContextCompat.getDrawable(QuizQuestionActivity.this, R.drawable.ic_heart);
                                                            drawableShape = new Shape.DrawableShape(drawable, true);
                                                            Dialog myDialog = new Dialog(QuizQuestionActivity.this);
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
                                                                    final Drawable drawable = ContextCompat.getDrawable(QuizQuestionActivity.this, R.drawable.ic_heart);
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
                                                                    time.setVisibility(View.GONE);

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
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                            else{
                                                finish();
                                            }
                                        } else if (i < list.size()) {
                                            QuizQuestionModel model = list.get(i);
                                            binding.quizquestiondisplay.setText(model.getQuestion());
                                            binding.points.setText("Points: " + model.getMarks());
                                            binding.option1.setText("A. " + model.getOp1());
                                            if(model.getImage().trim().length()>1)
                                            {
                                                Glide.with(binding.imageView11).load(model.getImage()).into(binding.imageView11);
                                                binding.imageView11.setVisibility(View.VISIBLE);
                                            }
                                            binding.option2.setText("B. " + model.getOp2());
                                            binding.option3.setText("C. " + model.getOp3());
                                            binding.option4.setText("D. " + model.getOp4());
                                            binding.submitthisquestion.setText("Next");
                                            binding.questionnumber.setText(String.valueOf(i + 1));

                                        }
                                        if (i + 1 == list.size()) {

                                            binding.submitthisquestion.setText("Submit");
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {

                binding.op1.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op2.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op3.setCardBackgroundColor(Color.parseColor("#0079DA"));
                binding.op4.setCardBackgroundColor(Color.parseColor("#0079DA"));

                selectedoption = "";
                i++;
//                Toast.makeText(QuizQuestionActivity.this, "Value of  Correct i" + i, Toast.LENGTH_SHORT).show();


                if (i == list.size()) {
                    Toast.makeText(getApplicationContext(), "Thanks for Submission", Toast.LENGTH_SHORT).show();
                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("isgiven").setValue("true");
                    if (isresultvisible.equals("true")) {
                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(quiz).child("userscore").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    int finalValue = Integer.parseInt(snapshot.getValue().toString());
                                    final Drawable drawable = ContextCompat.getDrawable(QuizQuestionActivity.this, R.drawable.ic_heart);
                                    drawableShape = new Shape.DrawableShape(drawable, true);
                                    Dialog myDialog = new Dialog(QuizQuestionActivity.this);
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
                                            final Drawable drawable = ContextCompat.getDrawable(QuizQuestionActivity.this, R.drawable.ic_heart);
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
                                            time.setVisibility(View.GONE);

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
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else
                        finish();



                } else if (i < list.size()) {
                    QuizQuestionModel model = list.get(i);
                    if(model.getImage().trim().length()>1)
                    {
                        Glide.with(binding.imageView11).load(model.getImage()).into(binding.imageView11);
                        binding.imageView11.setVisibility(View.VISIBLE);
                    }
                    binding.quizquestiondisplay.setText(model.getQuestion());
                    binding.points.setText("Points: " + model.getMarks());
                    binding.option1.setText("A. " + model.getOp1());
                    binding.option2.setText("B. " + model.getOp2());
                    binding.option3.setText("C. " + model.getOp3());
                    binding.option4.setText("D. " + model.getOp4());
                    binding.submitthisquestion.setText("Next");
                    binding.questionnumber.setText(String.valueOf(i + 1));

                }
                if (i == list.size() - 1) {
                    binding.submitthisquestion.setText("Submit");
                }
            }
            startTimer(Double.parseDouble(duration));

        }
    }


    private void startTimer(final double minuti) {
        countDownTimer = new CountDownTimer((long) (60 * minuti * 1000), 100) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {

                long seconds = leftTimeInMilliseconds / 1000;

                progressBar.setProgress((int) seconds);
                progress.setText((int) seconds + "");

            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                Clickfn();


            }
        }.start();

    }
}

