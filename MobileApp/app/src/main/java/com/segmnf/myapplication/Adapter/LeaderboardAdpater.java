package com.segmnf.myapplication.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.R;
import com.segmnf.myapplication.Utils.LeaderboardModel;

import java.util.ArrayList;

public class LeaderboardAdpater extends RecyclerView.Adapter<LeaderboardAdpater.Hold> {

    ArrayList<LeaderboardModel> items;
    FirebaseDatabase database;

    public LeaderboardAdpater(ArrayList<LeaderboardModel> items) {
        this.items = items;

    }

    @NonNull
    @Override
    public LeaderboardAdpater.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_rowview, parent, false);

        return new LeaderboardAdpater.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdpater.Hold holder, int position) {
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        Log.d("TAG", position+"");

        LeaderboardModel model = items.get(position);

        if (position==1 || position==2 || position==0){
            holder.layout.getLayoutParams().height = 1;
        }
        if(position!=1 && position!=2 && position!=0) {
            Toast.makeText(holder.itemView.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            holder.layout.setVisibility(View.VISIBLE);

            database.getReference().child("User_details").child(model.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if(userModel.getUid().equals(FirebaseAuth.getInstance().getUid()))
                        userModel.setName("You");
                    holder.name.setText(userModel.getName());
                    holder.position.setText(String.valueOf(holder.getAdapterPosition()+1));
                    Glide.with(holder.image).load(userModel.getImage()).into(holder.image);
                    holder.score.setText(model.getScore()+"\n"+model.getTimetaken());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//            holder.name.setText(model. ());
        }
//        holder.card.setImageResource(cardbg.get(index));
//        holder.cardimage.setImageResource(cardimg.get(indeximage));
//        holder.marks.setText(model.getMarks() + " Marks");
//        holder.duration.setText(model.getDuration() + " mins");
//        holder.date.setText(model.getDate());
//        holder.questions.setText(model.getQuestions() + " Questions");
//        if (model.getId().equals("112")) {
//            holder.parent.setVisibility(View.INVISIBLE);
//        }
//        holder.tough.setText(model.getTough());
//

//        database.getReference().child("LeaderBoard").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Hold extends RecyclerView.ViewHolder {
        TextView name, score, position;
        ImageView image;
        ConstraintLayout layout;
        CardView parent;

        public Hold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameC);
            score = itemView.findViewById(R.id.name2C);
            position = itemView.findViewById(R.id.position);
            image = itemView.findViewById(R.id.image);
            parent = itemView.findViewById(R.id.cardView7);
            layout= itemView.findViewById(R.id.layout);



        }

    }
}
