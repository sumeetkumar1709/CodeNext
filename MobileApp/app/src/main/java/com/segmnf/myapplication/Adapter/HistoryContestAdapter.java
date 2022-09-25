package com.segmnf.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.ContestQuestionActivity;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.R;
import com.segmnf.myapplication.Utils.LeaderboardModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class HistoryContestAdapter extends RecyclerView.Adapter<HistoryContestAdapter.Hold> {

    ArrayList<ContestModel> items;
    FirebaseDatabase database;

    boolean flag = false;


    public HistoryContestAdapter(ArrayList<ContestModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryContestAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_cardview, parent, false);

        return new HistoryContestAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryContestAdapter.Hold holder, @SuppressLint("RecyclerView") int position) {

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ContestModel model = items.get(position);
        holder.name.setText(model.getName());
        holder.cardLeader.setImageResource(R.drawable.ic_baseline_delete_forever_24);
        holder.cardLeader.setVisibility(View.VISIBLE);
        ImageViewCompat.setImageTintList(holder.cardLeader, ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(),R.color.white)));
        holder.cardLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot s:snapshot.getChildren())
                        {
                            if(s.exists())
                            {
                                ContestModel model2 = s.getValue(ContestModel.class);
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
        holder.marks.setText(model.getMarks() + " Marks");
        holder.duration.setText(model.getDuration() + " mins");
        holder.date.setText(model.getDate());
        holder.questions.setText(model.getQuestions() + " Questions");
        if (model.getId().equals("112")) {
            holder.parent.setVisibility(View.INVISIBLE);
        }
        if (model.getTough().toLowerCase(Locale.ROOT).equals("hard")) {
            holder.tough.setText("Hard");

        } else if (model.getTough().toLowerCase(Locale.ROOT).equals("medium")) {
            holder.tough.setText("Medium");

        } else if (model.getTough().toLowerCase(Locale.ROOT).equals("easy")) {
            holder.tough.setText("Easy");

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
//



    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks, duration, date, questions, start, end, tough;
        ImageView cardimage, cardLeader;
        ImageView card;
        CardView parent;

        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contestname);
            questions = itemView.findViewById(R.id.contestquestions);
            marks = itemView.findViewById(R.id.contestmarks);
            duration = itemView.findViewById(R.id.contestduration);
            date = itemView.findViewById(R.id.contestdate);
            card = itemView.findViewById(R.id.cardviewcontest);
            cardimage = itemView.findViewById(R.id.imagecontest);
            parent = itemView.findViewById(R.id.cardparent);
            cardLeader = itemView.findViewById(R.id.leaderboard);
            tough = itemView.findViewById(R.id.difficultycardzz);


        }

    }
}
