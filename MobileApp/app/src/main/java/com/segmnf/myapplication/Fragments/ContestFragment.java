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
import com.segmnf.myapplication.Adapter.ContestAdapter;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ContestFragment extends Fragment {

    private FirebaseDatabase database;
    SharedPreferences preferences;
    ArrayList<ContestModel> list = new ArrayList<>();
    ArrayList<Integer> cardbg = new ArrayList<>();
    ArrayList<Integer> cardimg = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        cardbg.add(R.drawable.bg1);
        cardbg.add(R.drawable.bg2);
        cardbg.add(R.drawable.bg3);
        cardbg.add(R.drawable.bg4);
        cardbg.add(R.drawable.bg5);
        cardimg.add(R.drawable.ic_undraw_cloud_hosting_a1gf);
        cardimg.add(R.drawable.ic_undraw_code_thinking_re_gka2);
        cardimg.add(R.drawable.ic_undraw_pair_programming_re_or4x);
        cardimg.add(R.drawable.ic_undraw_developer_activity_re_39tg);
        cardimg.add(R.drawable.ic_undraw_functions_re_alho);

        RecyclerView contestrecycler = view.findViewById(R.id.contestreecycler);
//        ContestModel model = new ContestModel("Interview Prep 3.0", "24 Jul", "120", "3", "60", "2", "00:00", "02:00", "21 22 23", "100", "0", "0", "0","Easy");
//        database.getReference().child("Contests").child("4").setValue(model);
        ContestAdapter adapter = new ContestAdapter(list, cardbg, cardimg);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        contestrecycler.setLayoutManager(manager);
        contestrecycler.setAdapter(adapter);

        database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        ContestModel model = snapshot1.getValue(ContestModel.class);


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
                    database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    ContestModel model = snapshot1.getValue(ContestModel.class);


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
                    database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    ContestModel model = snapshot1.getValue(ContestModel.class);
                                    if (!model.getId().equals("112")) {
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

                                    }

                                    adapter.notifyDataSetChanged();
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
                    database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    ContestModel model = snapshot1.getValue(ContestModel.class);
                                    if (!model.getId().equals("112")) {
                                        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM");

                                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                        String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                                        Date from = null;
                                        try {
                                            from = dateformat.parse(model.getDate());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Date today = null;
                                        try {
                                            today = dateformat.parse(currentDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                        if (from.after(today)) {
                                            list.add(model);
                                            adapter.notifyDataSetChanged();
                                        }
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
                                            if (dateNow.before(date_from)) {
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
                    database.getReference().child("Contests").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                    ContestModel model = snapshot1.getValue(ContestModel.class);
                                    if (!model.getId().equals("112")) {
                                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(model.getId())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (!snapshot.exists()) {
                                                            SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM");

                                                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                                            String currentDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
                                                            Date from = null;
                                                            try {
                                                                from = dateformat.parse(model.getDate());
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Date today = null;
                                                            try {
                                                                today = dateformat.parse(currentDate);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                                            if (today.after(from)) {
                                                                list.add(model);
                                                                adapter.notifyDataSetChanged();
                                                            }
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
                                                                if (dateNow.after(date_from)) {
                                                                    list.add(model);
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

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return view;
    }

}