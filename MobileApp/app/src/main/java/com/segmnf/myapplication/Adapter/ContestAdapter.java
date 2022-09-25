package com.segmnf.myapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.Hold> {

    ArrayList<ContestModel> items;
    FirebaseDatabase database;
    ArrayList<Integer> cardbg = new ArrayList<>();
    ArrayList<Integer> cardimg = new ArrayList<>();
    private Random randomGenerator, random;
    boolean flag = false;
    ArrayList<LeaderboardModel> list = new ArrayList<>();


    public ContestAdapter(ArrayList<ContestModel> items, ArrayList<Integer> cardbg, ArrayList<Integer> cardimg) {
        this.items = items;
        this.cardbg = cardbg;
        this.cardimg = cardimg;
    }

    @NonNull
    @Override
    public ContestAdapter.Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_cardview, parent, false);

        return new ContestAdapter.Hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestAdapter.Hold holder, int position) {

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        ContestModel model = items.get(position);
        holder.name.setText(model.getName());
        randomGenerator = new Random();
        random = new Random();
        int index = randomGenerator.nextInt(cardbg.size());
        int indeximage = random.nextInt(cardimg.size());
        holder.card.setImageResource(cardbg.get(index));
        holder.cardimage.setImageResource(cardimg.get(indeximage));
//        Toast.makeText(holder.itemView.getContext(), ""+model.getMarks(), Toast.LENGTH_SHORT).show();
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


        database.getReference().child("LeaderBoard").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                SimpleDateFormat dateformatter = new SimpleDateFormat("dd MMM");
                String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
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
                if (snapshot.exists() && flag == true) {
                    holder.cardLeader.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
//                database.getReference().child("LeaderBoard").child("2").child("1").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","40","21:22"));
//                database.getReference().child("LeaderBoard").child("2").child("2").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","10","101:22"));
//                database.getReference().child("LeaderBoard").child("2").child("3").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","90","111:22"));
//                database.getReference().child("LeaderBoard").child("2").child("4").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","700","21:22"));
//                database.getReference().child("LeaderBoard").child("2").child("6").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","50","51:22"));
//                database.getReference().child("LeaderBoard").child("2").child("7").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","50","51:22"));
//
//                database.getReference().child("LeaderBoard").child("2").child("5").setValue(new LeaderboardModel("LUhln8QO6yeTKVKM1bdRENOaDtD3","50","51:22"));
                database.getReference().child("LeaderBoard").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            list.add(snapshot1.getValue(LeaderboardModel.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Collections.sort(list);
                Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.contest_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                TextView content = (TextView) dialog.findViewById(R.id.dialogcontent);
                TextView yes = (TextView) dialog.findViewById(R.id.back_Ok);
                TextView no = (TextView) dialog.findViewById(R.id.backCancel);
                TextView start = (TextView) dialog.findViewById(R.id.starttime);
                TextView end = (TextView) dialog.findViewById(R.id.endtime);
                TextView leader = (TextView) dialog.findViewById(R.id.leaderboardbutton);
                TextView editorial = (TextView) dialog.findViewById(R.id.editorial);

                start.setText(model.getStart());
                end.setText(model.getEnd());
                content.setText("Languages supported for the contest are: C++, Java, Python, C, JavaScript, Ruby, C#.\n\n" +
                        "Each submission will be tested based on our critical test data.\n\n" +
                        "Please refrain from discussing strategy during the contest. All submissions are run through a plagiarism detector. Any case of code plagiarism will reduce the score of the concerned participants to 0." +
                        "");


                editorial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat dateformatter = new SimpleDateFormat("dd MMM");
                        String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                        if (currentDate.equals(model.getDate())) {


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
                            if (date_to.before(dateNow)) {
                                Uri uri = Uri.parse(model.getEditorial()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                v.getContext().startActivity(intent);

                            } else
                                Toast.makeText(v.getContext(), "Content not yet ended", Toast.LENGTH_SHORT).show();
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
                            if (today.after(from)) {
                                Uri uri = Uri.parse(model.getEditorial()); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                v.getContext().startActivity(intent);
                            }else
                                Toast.makeText(v.getContext(), "Content not yet ended", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
                leader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (list.size() > 0) {
                            ShowDialogForLeaderboard(v.getContext(), model, list);
                        } else
                            Toast.makeText(v.getContext(), "Leaderboard not available", Toast.LENGTH_SHORT).show();


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

                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(model.getId()).child("submissiontime").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    if (snapshot.getValue().equals("0")) {
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
                                                String[] splitStr = model.getQid().trim().split("\\s+");


                                                Intent intent = new Intent(v.getContext(), ContestQuestionActivity.class);
                                                intent.putExtra("qid", model.getQid());
                                                intent.putExtra("duration", model.getDuration());
                                                intent.putExtra("name", model.getName());
                                                intent.putExtra("id", model.getId());
                                                intent.putExtra("adminid", model.getAdminid());

                                                v.getContext().startActivity(intent);

                                                dialog.dismiss();


                                            } else
                                                Toast.makeText(v.getContext(), "Contest not Started or Expired", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(v.getContext(), "Please check contest date before starting.", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(v.getContext(), "Contest already Submitted", Toast.LENGTH_SHORT).show();
                                } else {
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
                                            String[] splitStr = model.getQid().trim().split("\\s+");

                                            database.getReference().child("Contests").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(model.getId()).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isComplete()) {
                                                                for (int i = 0; i < splitStr.length; i++) {
                                                                    int finalI = i;
                                                                    database.getReference().child("Admins").child(model.getAdminid()).child("questions").child(splitStr[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                            if (snapshot.exists()) {
                                                                                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(model.getId()).child("Question").child(splitStr[finalI]).setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isComplete()) {

                                                                                            Intent intent = new Intent(v.getContext(), ContestQuestionActivity.class);
                                                                                            intent.putExtra("qid", model.getQid());
                                                                                            intent.putExtra("duration", model.getDuration());
                                                                                            intent.putExtra("name", model.getName());
                                                                                            intent.putExtra("id", model.getId());
                                                                                            intent.putExtra("adminid", model.getAdminid());

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
                                            Toast.makeText(v.getContext(), "Contest not Started or Expired", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(v.getContext(), "Please check contest date before starting.", Toast.LENGTH_SHORT).show();
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


    public void ShowDialogForLeaderboard(Context context, ContestModel contestModel, ArrayList<LeaderboardModel> list1) {
        Collections.sort(list1);
        Dialog myDialog = new Dialog(context);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        myDialog.setContentView(R.layout.leaderboard_dialog);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(true);
        TextView two, three, name1, name2, name3, score1, score2, score3, total;
        ImageView image1, image2, image3;
        MaterialCardView card2, card3;
        two = myDialog.findViewById(R.id.two);
        three = myDialog.findViewById(R.id.three);
        image1 = myDialog.findViewById(R.id.image1);
        image2 = myDialog.findViewById(R.id.image2);
        image3 = myDialog.findViewById(R.id.image3);
        name1 = myDialog.findViewById(R.id.name1);
        name2 = myDialog.findViewById(R.id.name2);
        name3 = myDialog.findViewById(R.id.name3);
        score1 = myDialog.findViewById(R.id.score1);
        score2 = myDialog.findViewById(R.id.score2);
        score3 = myDialog.findViewById(R.id.score3);
        total = myDialog.findViewById(R.id.total);
        card2 = myDialog.findViewById(R.id.materialCardView2);
        card3 = myDialog.findViewById(R.id.materialCardView3);
        two.setVisibility(View.INVISIBLE);
        three.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        name3.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);
        card3.setVisibility(View.INVISIBLE);
        score2.setVisibility(View.INVISIBLE);
        score3.setVisibility(View.INVISIBLE);

        total.setText("Total: " + list1.size());

        for (int i = 0; i < list1.size(); i++) {
            int finalI = i;
            database.getReference().child("User_details").child(list1.get(i).getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserModel model = snapshot.getValue(UserModel.class);
                        if (model.getUid().equals(FirebaseAuth.getInstance().getUid()))
                            model.setName("You");

                        if (finalI == 0) {
                            Glide.with(image1).load(model.getImage()).into(image1);
                            name1.setText(model.getName());
                            score1.setText(list1.get(0).getScore() + "\n" + list1.get(0).getTimetaken());
                        }
                        if (finalI == 1) {
                            Glide.with(image2).load(model.getImage()).into(image2);
                            name2.setText(model.getName());
                            score2.setText(list1.get(1).getScore() + "\n" + list1.get(1).getTimetaken());
                            name2.setVisibility(View.VISIBLE);
                            card2.setVisibility(View.VISIBLE);
                            score2.setVisibility(View.VISIBLE);
                            two.setVisibility(View.VISIBLE);
                        }
                        if (finalI == 2) {
                            Glide.with(image3).load(model.getImage()).into(image3);
                            name3.setText(model.getName());
                            score3.setText(list1.get(2).getScore() + "\n" + list1.get(2).getTimetaken());
                            name3.setVisibility(View.VISIBLE);
                            card3.setVisibility(View.VISIBLE);
                            score3.setVisibility(View.VISIBLE);
                            three.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        RecyclerView recyclerView = myDialog.findViewById(R.id.leaderboardrecyccler);
        LeaderboardAdpater adapter = new LeaderboardAdpater(list1);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        myDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        myDialog.setOnShowListener(dialogInterface -> {
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


    }

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
