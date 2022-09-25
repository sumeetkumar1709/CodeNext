package com.segmnf.myapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.ContestQuestionActivity;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class QuestionInsideAdapter extends RecyclerView.Adapter<QuestionInsideAdapter.Hold> {

    ArrayList<QuestionModel> items;
    FirebaseDatabase database;
    private Context mContext;


    public QuestionInsideAdapter(ArrayList<QuestionModel> items, Context mContext) {
        this.items = items;
        this.mContext= mContext;
    }

    @NonNull
    @Override
    public QuestionInsideAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row_view, parent, false);
        return new QuestionInsideAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionInsideAdapter.Hold holder, int position) {
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        QuestionModel model = items.get(position);
        holder.name.setText(model.getName());
        holder.difficulty.setText(model.getDifficulty());
        holder.marks.setText("Scores: "+model.getMarks());
        holder.status.setText(model.getStatus());
        if(model.getStatus().equals("")){
            holder.status.setText("Not accepted");
        }
        else
            holder.status.setText(model.getStatus());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContestQuestionDetailActivity.class);
                intent.putExtra("contestid", model.getContestid());
                intent.putExtra("avgtime", model.getAvgtime());
                intent.putExtra("name", model.getName());
                intent.putExtra("difficulty", model.getDifficulty());
                intent.putExtra("eg1", model.getEg1());
                intent.putExtra("eg2", model.getEg2());
                intent.putExtra("marks", model.getMarks());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("constraints", model.getConstraints());
                intent.putExtra("testcases", model.getTestcases());
                intent.putExtra("testoutput", model.getTestoutputs());
                intent.putExtra("cpu", model.getCpu());
                intent.putExtra("memory", model.getMemory());
                intent.putExtra("status", model.getStatus());
                intent.putExtra("questionid", model.getQuid());
                intent.putExtra("millis",((ContestQuestionActivity)mContext).getMillis().toString());
                intent.putExtra("duration",((ContestQuestionActivity)mContext).getduration());



                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks, difficulty,status;
        MaterialCardView card;


        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namequestion);
            marks = itemView.findViewById(R.id.scores10);
            difficulty = itemView.findViewById(R.id.difficultycardz);
            status = itemView.findViewById(R.id.statusaccepted);
            card = itemView.findViewById(R.id.qcard);


        }

    }
}
