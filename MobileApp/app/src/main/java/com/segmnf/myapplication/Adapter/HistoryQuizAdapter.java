package com.segmnf.myapplication.Adapter;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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


public class HistoryQuizAdapter extends RecyclerView.Adapter<HistoryQuizAdapter.Hold> {

    ArrayList<QuizModel> items;
    FirebaseDatabase database;

    boolean flag = false;

    public HistoryQuizAdapter(ArrayList<QuizModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryQuizAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_cardview, parent, false);
        return new HistoryQuizAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryQuizAdapter.Hold holder, @SuppressLint("RecyclerView") int position) {

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        QuizModel model = items.get(position);
        holder.name.setText(model.getName());

        holder.duration.setText(model.getDuration() + " min/q");
        holder.date.setText(model.getDate());
        holder.questions.setText(model.getQuestioncount() + " Questions");
        if (model.getQuizid().equals("112")) {
            holder.parent.setVisibility(View.INVISIBLE);
        }
        holder.topic.setText(model.getTopic());
        holder.tough.setText(model.getDifficulty());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s:snapshot.getChildren())
                        {
                            if(s.exists())
                            {
                                QuizModel model2 = s.getValue(QuizModel.class);
                                if(model2.getAdminid().equals(FirebaseAuth.getInstance().getUid()))
                                    s.getRef().removeValue();
                                items.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, items.size());
                                holder.itemView.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks, duration, date, questions, tough, topic;
        ImageView cardimage, delete;
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
            delete= itemView.findViewById(R.id.deletequiz);

        }

    }
}
