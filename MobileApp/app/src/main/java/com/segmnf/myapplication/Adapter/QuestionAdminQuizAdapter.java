package com.segmnf.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.R;

import java.util.ArrayList;

public class QuestionAdminQuizAdapter extends RecyclerView.Adapter<QuestionAdminQuizAdapter.Hold> {

    ArrayList<QuizQuestionModel> items;
    FirebaseDatabase database;
    ArrayList<String> list1 = new ArrayList<>();
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String quiz;


    public QuestionAdminQuizAdapter(ArrayList<QuizQuestionModel> items, String quiz) {
        this.items = items;
        this.quiz=quiz;

    }

    @NonNull
    @Override
    public QuestionAdminQuizAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_quiz_question_admin, parent, false);
        return new QuestionAdminQuizAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdminQuizAdapter.Hold holder, @SuppressLint("RecyclerView") int position) {
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        QuizQuestionModel model = items.get(position);
        prefs = holder.itemView.getContext().getSharedPreferences("QuestionList", Context.MODE_PRIVATE);

        holder.name.setText(model.getQuestion());
        holder.marks.setText("Scores: " + model.getMarks());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.check.isChecked()) {
                    list1.add(String.valueOf(model.getQuestionid()));
                    database.getReference().child("Admin").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuestionid()).child("quizid").setValue(quiz);
                } else {
                    list1.remove(String.valueOf(model.getQuestionid()));
                    database.getReference().child("Admin").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(model.getQuestionid()).child("quizid").setValue("");

                }
                setListToPreferance("list" , list1);
                Toast.makeText(holder.itemView.getContext(), "Total selections: "+list1.size(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setListToPreferance(String key, ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String model = list.get(i);
            Log.d("list0000000", "" + model);
        }

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor = prefs.edit();
        editor.putString(key, json);
        editor.apply();
    }


    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks;
        MaterialCardView card;
        CheckBox check;


        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namequestion);
            marks = itemView.findViewById(R.id.scores10);
            check = itemView.findViewById(R.id.check);
            card = itemView.findViewById(R.id.qcard);


        }

    }

}
