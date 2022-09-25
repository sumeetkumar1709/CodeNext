package com.segmnf.myapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.Adapter.QuizAdapter;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class QuizFragment extends Fragment {
    private FirebaseDatabase database;
    SharedPreferences preferences;
    ArrayList<QuizModel> list = new ArrayList<>();
    ArrayList<Integer> cardbg = new ArrayList<>();
    boolean flag=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        cardbg.add(R.color.yellow_icon_color);
        cardbg.add(R.color.basket);
        cardbg.add(R.color.BasketCard);
        cardbg.add(R.color.teal_200);
        cardbg.add(R.color.TennisCard);

        RecyclerView quizrecycler = view.findViewById(R.id.quizesrecycler);

        QuizModel model = new QuizModel("1", "112", "GetIT3", "#cpp", "true", "", "10", "20", "0", "false", "Medium", "2 3 4 5 6 7 8 9 10 11",
                "02:00", "23:00", "02 Aug", "0", "false");
//        database.getReference().child("Quizzes").child("112").setValue(model);


        QuizQuestionModel quizQuestionModel = new QuizQuestionModel("100", "10", "2", "1", "MS Dhoni is  a?", "", "Legend", "Wicketkeeper", "Captain", "Plauer", "Legend");
//        database.getReference().child("Admins").child("100").child("Quizzes").child("11").setValue(quizQuestionModel);

        QuizAdapter adapter = new QuizAdapter(list, cardbg);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        quizrecycler.setLayoutManager(manager);
        quizrecycler.setAdapter(adapter);

        database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        QuizModel model = snapshot1.getValue(QuizModel.class);


                        list.add(model);
                        adapter.notifyDataSetChanged();

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        PowerSpinnerView spinner = view.findViewById(R.id.spinnercontest);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int oldIndex, @Nullable String oldItem, int newIndex, String newItem) {
                if (newIndex == 0) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    QuizModel model = snapshot1.getValue(QuizModel.class);


                                    list.add(model);
                                    adapter.notifyDataSetChanged();


                                }
                            }
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (newIndex == 1) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    QuizModel model = snapshot1.getValue(QuizModel.class);
                                    if (!model.getQuizid().equals("112")) {
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

                                                    list.add(model);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }

                                        } else {
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

                                            if (flag == true) {
                                                list.add(model);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (newIndex == 2) {

                    list.clear();
                    adapter.notifyDataSetChanged();
                    database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    QuizModel model = snapshot1.getValue(QuizModel.class);
                                    if (!model.getQuizid().equals("112")) {
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

                                                Date dateNow = null;
                                                try {
                                                    dateNow = formatter.parse(currentTime);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                if (date_from.after(dateNow)) {
                                                    list.add(model);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                            else{
                                                SimpleDateFormat dateformatter = new SimpleDateFormat("dd MMM");

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
                                                if (today.before(from))
                                                    list.add(model);
                                                adapter.notifyDataSetChanged();

                                            }

                                        } else {
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

                                                if (dateNow.before(date_to)) {
                                                    list.add(model);
                                                    adapter.notifyDataSetChanged();
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
                                                if (today.before(from))
                                                    list.add(model);
                                                adapter.notifyDataSetChanged();
                                            }

                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (newIndex == 3) {

                    list.clear();
                    adapter.notifyDataSetChanged();
                    database.getReference().child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    QuizModel model = snapshot1.getValue(QuizModel.class);
                                    if (!model.getQuizid().equals("112")) {
                                        if (model.getVisibility().equals("false")) {

                                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                            String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                                            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                            if (currentDate.equals(model.getDate())) {

                                                Date date_end = null;
                                                try {
                                                    date_end = formatter.parse(model.getEnd());
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                Date dateNow = null;
                                                try {
                                                    dateNow = formatter.parse(currentTime);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                if (date_end.before(dateNow)) {
                                                    list.add(model);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                            else{
                                                SimpleDateFormat dateformatter = new SimpleDateFormat("dd MMM");

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
                                                    list.add(model);
                                                adapter.notifyDataSetChanged();

                                            }

                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
//                else if (newIndex == 4) {
//
//                    list.clear();
//                    adapter.notifyDataSetChanged();
//                    database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            if (snapshot.exists()) {
//
//                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//
//                                    QuizModel model = snapshot1.getValue(QuizModel.class);
//                                                    list.add(model);
//                                                adapter.notifyDataSetChanged();
//
//                                        adapter.notifyDataSetChanged();
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
            }
        });
        return view;
    }
}