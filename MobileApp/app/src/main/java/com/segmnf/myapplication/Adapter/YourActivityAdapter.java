package com.segmnf.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.ContestQuestionActivity;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.Model.ActivityModel;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.R;

import java.util.ArrayList;

public class YourActivityAdapter extends RecyclerView.Adapter<YourActivityAdapter.Hold> {

    ArrayList<ActivityModel> items;
    FirebaseDatabase database;
    private Context mContext;


    public YourActivityAdapter(ArrayList<ActivityModel> items) {
        this.items = items;

    }



    @NonNull
    @Override
    public YourActivityAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_activity_row_view, parent, false);
        return new YourActivityAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourActivityAdapter.Hold holder, int position) {
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ActivityModel model = items.get(position);
        holder.name.setText(model.getName());
        holder.type.setText(model.getType());
        holder.marks.setText("Score: "+model.getScore());


//        holder.difficulty.setText(model.getDifficulty());
//        holder.marks.setText("Scores: "+model.getMarks());
//        holder.status.setText(model.getStatus());
//        if(model.getStatus().equals("")){
//            holder.status.setText("Not accepted");
//        }
//        else
//            holder.status.setText(model.getStatus());



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Hold extends RecyclerView.ViewHolder {
        TextView name, marks, type;


        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameofactivtity);
            marks = itemView.findViewById(R.id.type);
            type = itemView.findViewById(R.id.Score);


        }

    }
}
