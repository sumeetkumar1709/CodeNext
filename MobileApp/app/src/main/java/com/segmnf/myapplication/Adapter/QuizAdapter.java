package com.segmnf.myapplication.Adapter;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.HomeActivity;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.QuizQuestionActivity;
import com.segmnf.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.Hold> {

    ArrayList<QuizModel> items;
    FirebaseDatabase database;
    ArrayList<Integer> cardbg = new ArrayList<>();
    private Random randomGenerator;
    boolean flag = false;
    private Shape.DrawableShape drawableShape = null;

    public QuizAdapter(ArrayList<QuizModel> items, ArrayList<Integer> cardbg) {
        this.items = items;
        this.cardbg = cardbg;

    }

    @NonNull
    @Override
    public QuizAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_cardview, parent, false);
        return new QuizAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.Hold holder, int position) {

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        QuizModel model = items.get(position);
        holder.name.setText(model.getName());
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(cardbg.size());
        holder.cardimage.setImageResource(cardbg.get(index));
        holder.duration.setText(model.getDuration() + " min/q");
        holder.date.setText(model.getDate());
        holder.questions.setText(model.getQuestioncount() + " Questions");
        if (model.getQuizid().equals("112")) {
            holder.parent.setVisibility(View.INVISIBLE);
        }
        holder.topic.setText(model.getTopic());
        holder.tough.setText(model.getDifficulty());
        holder.deletequiz.setVisibility(View.INVISIBLE);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.quiz_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                TextView content = (TextView) dialog.findViewById(R.id.dialogcontent);
                TextView yes = (TextView) dialog.findViewById(R.id.back_Ok);
                TextView no = (TextView) dialog.findViewById(R.id.backCancel);
                TextView start = (TextView) dialog.findViewById(R.id.starttime);
                TextView end = (TextView) dialog.findViewById(R.id.endtime);
                TextView leader = (TextView) dialog.findViewById(R.id.leaderboardbutton);
                LinearLayout l1 = (LinearLayout) dialog.findViewById(R.id.startlinear);
                LinearLayout l2 = (LinearLayout) dialog.findViewById(R.id.endlinear);


                if (model.getVisibility().toLowerCase(Locale.ROOT).equals("false")) {
                    start.setText(model.getStart());
                    end.setText(model.getEnd());
                } else {
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                }
                content.setText(model.getRules());


                leader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getIsresultvisible().equals("true")) {
                            database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).child("userscore").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        int finalValue = Integer.parseInt(snapshot.getValue().toString());
                                        final Drawable drawable = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_heart);
                                        drawableShape = new Shape.DrawableShape(drawable, true);
                                        Dialog myDialog = new Dialog(v.getContext());
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
                                                final Drawable drawable = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_heart);
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
                                                time.setVisibility(View.GONE);

                                            }
                                        });
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
                                    } else
                                        Toast.makeText(v.getContext(), "Please attempt the quiz first", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else
                        Toast.makeText(v.getContext(), "Admin chose to hide results", Toast.LENGTH_SHORT).show();

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).child("userscore").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).child("isgiven").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                            if (snapshot2.getValue().equals("false")) {
                                                Toast.makeText(v.getContext(), "Quiz Re-Enter not allowed or Time over.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(v.getContext(), "Quiz already given.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                } else {
                                    if (model.getVisibility().equals("false")) {

                                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                        String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                        if (currentDate.equals(model.getDate())) {

                                            Date date_from = null;
                                            try {
                                                date_from = formatter.parse(model.getStart());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Date date_to = null;
                                            try {
                                                date_to = formatter.parse(model.getEnd());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Date dateNow = null;
                                            try {
                                                dateNow = formatter.parse(currentTime);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            if (date_from.before(dateNow) && date_to.after(dateNow)) {
                                                Toast.makeText(v.getContext(), "Starting Now.", Toast.LENGTH_SHORT).show();
                                                String[] splitStr = model.getQuid().trim().split("\\s+");
                                                database.getReference().child("Quizzes").child(model.getQuizid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isComplete()) {
                                                                    for (int i = 0; i < splitStr.length; i++) {
                                                                        int finalI = i;
                                                                        database.getReference().child("Admins").child(model.getAdminid()).child("Quizzes").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                                if (snapshot.exists()) {
                                                                                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).child("Question").child(splitStr[finalI]).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isComplete()) {

                                                                                                Intent intent = new Intent(v.getContext(), QuizQuestionActivity.class);
                                                                                                intent.putExtra("admin", model.getAdminid());
                                                                                                intent.putExtra("quiz", model.getQuizid());
                                                                                                intent.putExtra("namequiz", model.getName());
                                                                                                intent.putExtra("duration", model.getDuration());
                                                                                                intent.putExtra("quizids", model.getQuid());
                                                                                                intent.putExtra("visibility", model.getVisibility());
                                                                                                intent.putExtra("isresultvisible", model.getIsresultvisible());

                                                                                                v.getContext().startActivity(intent);
                                                                                                dialog.dismiss();
                                                                                            } else
                                                                                                Toast.makeText(v.getContext(), "Something went wrong, Please contact developer", Toast.LENGTH_SHORT).show();
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

                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            } else {
                                                Toast.makeText(v.getContext(), "This is a Date and time bound quiz", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(v.getContext(), "Check Quiz timing first", Toast.LENGTH_SHORT).show();

                                            }


                                        } else {
                                            Toast.makeText(v.getContext(), "This is a Date bound quiz", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(v.getContext(), "Check Quiz date first", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {


                                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                        SimpleDateFormat dateformatter = new SimpleDateFormat("dd MMM");
                                        String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                        Log.d("Aasasssadsda",currentDate+"   "+model.getDate());
                                        if (currentDate.equals(model.getDate())) {
                                            Date date_to = null;

                                            try {
                                                date_to = formatter.parse(model.getStart());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Date dateNow = null;
                                            try {
                                                dateNow = formatter.parse(currentTime);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            if (dateNow.after(date_to)) {
                                                flag = true;
                                            }
                                        } else {
                                            Date from = null;
                                            try {
                                                from = dateformatter.parse(model.getDate());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Date today = null;
                                            try {
                                                today = dateformatter.parse(currentDate);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            if (today.after(from))
                                                flag = true;

                                        }

                                        if (flag == true) {
                                            Toast.makeText(v.getContext(), "This quiz is available since hosted", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(v.getContext(), "Starting Now..", Toast.LENGTH_SHORT).show();

                                            String[] splitStr = model.getQuid().trim().split("\\s+");
                                            database.getReference().child("Quizzes").child(model.getQuizid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isComplete()) {
                                                                for (int i = 0; i < splitStr.length; i++) {
                                                                    int finalI = i;
                                                                    database.getReference().child("Admins").child(model.getAdminid()).child("Quizzes").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                            if (snapshot.exists()) {
                                                                                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuizid()).child("Question").child(splitStr[finalI]).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isComplete()) {

                                                                                            Intent intent = new Intent(v.getContext(), QuizQuestionActivity.class);
                                                                                            intent.putExtra("admin", model.getAdminid());
                                                                                            intent.putExtra("quiz", model.getQuizid());
                                                                                            intent.putExtra("namequiz", model.getName());
                                                                                            intent.putExtra("duration", model.getDuration());
                                                                                            intent.putExtra("quizids", model.getQuid());
                                                                                            intent.putExtra("visibility", model.getVisibility());
                                                                                            intent.putExtra("isresultvisible", model.getIsresultvisible());

                                                                                            v.getContext().startActivity(intent);
                                                                                            dialog.dismiss();
                                                                                        } else
                                                                                            Toast.makeText(v.getContext(), "Something went wrong, Please contact developer", Toast.LENGTH_SHORT).show();
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

                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        } else
                                            Toast.makeText(v.getContext(), "This quiz hasn't started", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });


                dialog.show();


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

//

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
//


//    public void ShowDialogForLeaderboard(Context context, ContestModel contestModel, ArrayList<LeaderboardModel> list1) {
//        Collections.sort(list1);
//        Dialog myDialog = new Dialog(context);
//        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        myDialog.setContentView(R.layout.leaderboard_dialog);
//        myDialog.setCanceledOnTouchOutside(false);
//        myDialog.setCancelable(true);
//        TextView  two, three, name1, name2, name3, score1, score2, score3, total;
//        ImageView image1,image2, image3;
//        MaterialCardView card2, card3;
//        two = myDialog.findViewById(R.id.two);
//        three = myDialog.findViewById(R.id.three);
//        image1 = myDialog.findViewById(R.id.image1);
//        image2 = myDialog.findViewById(R.id.image2);
//        image3 = myDialog.findViewById(R.id.image3);
//        name1 = myDialog.findViewById(R.id.name1);
//        name2 = myDialog.findViewById(R.id.name2);
//        name3 = myDialog.findViewById(R.id.name3);
//        score1 = myDialog.findViewById(R.id.score1);
//        score2 = myDialog.findViewById(R.id.score2);
//        score3 = myDialog.findViewById(R.id.score3);
//        total = myDialog.findViewById(R.id.total);
//        card2 = myDialog.findViewById(R.id.materialCardView2);
//        card3 = myDialog.findViewById(R.id.materialCardView3);
//        two.setVisibility(View.INVISIBLE);
//        three.setVisibility(View.INVISIBLE);
//        name2.setVisibility(View.INVISIBLE);
//        name3.setVisibility(View.INVISIBLE);
//        card2.setVisibility(View.INVISIBLE);
//        card3.setVisibility(View.INVISIBLE);
//        score2.setVisibility(View.INVISIBLE);
//        score3.setVisibility(View.INVISIBLE);
//
//        total.setText("Total: "+list1.size());
//
//        for(int i=0; i<list1.size();i++)
//        {
//            int finalI = i;
//            database.getReference().child("User_details").child(list1.get(i).getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if(snapshot.exists()){
//                        UserModel model = snapshot.getValue(UserModel.class);
//                        if(model.getUid().equals(FirebaseAuth.getInstance().getUid()))
//                            model.setName("You");
//
//                        if(finalI ==0) {
//                            Glide.with(image1).load(model.getImage()).into(image1);
//                            name1.setText(model.getName());
//                            score1.setText(list1.get(0).getScore()+"\n"+list1.get(0).getTimetaken());
//                        }
//                        if(finalI==1){
//                            Glide.with(image2).load(model.getImage()).into(image2);
//                            name2.setText(model.getName());
//                            score2.setText(list1.get(1).getScore()+"\n"+list1.get(1).getTimetaken());
//                            name2.setVisibility(View.VISIBLE);
//                            card2.setVisibility(View.VISIBLE);
//                            score2.setVisibility(View.VISIBLE);
//                            two.setVisibility(View.VISIBLE);
//                        }
//                        if(finalI==2){
//                            Glide.with(image3).load(model.getImage()).into(image3);
//                            name3.setText(model.getName());
//                            score3.setText(list1.get(2).getScore()+"\n"+list1.get(2).getTimetaken());
//                            name3.setVisibility(View.VISIBLE);
//                            card3.setVisibility(View.VISIBLE);
//                            score3.setVisibility(View.VISIBLE);
//                            three.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//        RecyclerView recyclerView = myDialog.findViewById(R.id.leaderboardrecyccler);
//        LeaderboardAdpater adapter = new LeaderboardAdpater(list1);
//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
//        myDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//        myDialog.setOnShowListener(dialogInterface -> {
//        });
//        myDialog.show();
//        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    dialog.cancel();
//
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//    }

    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks, duration, date, questions, tough, topic;
        ImageView cardimage, deletequiz;
        CardView parent;

        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.quizname);
            questions = itemView.findViewById(R.id.quizquestions);
            duration = itemView.findViewById(R.id.quizduration);
            date = itemView.findViewById(R.id.quizdate);
            cardimage = itemView.findViewById(R.id.imagequiz);
            parent = itemView.findViewById(R.id.cardparentquiz);
            tough = itemView.findViewById(R.id.difficultycardquiz);
            topic = itemView.findViewById(R.id.quiztopic);
            deletequiz= itemView.findViewById(R.id.deletequiz);

        }

    }
}
