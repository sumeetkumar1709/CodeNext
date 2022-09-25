package com.segmnf.myapplication.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.AdminDashboardActivity;
import com.segmnf.myapplication.GetStartedActivity;
import com.segmnf.myapplication.Model.ContestModel;
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.R;
import com.segmnf.myapplication.UserDetailActivity;

import java.util.ArrayList;

public class SettingBottomSheetFragment extends BottomSheetDialogFragment {
    private FirebaseDatabase database;
    SharedPreferences preferences;
    ArrayList<ContestModel> list = new ArrayList<>();
    ArrayList<Integer> cardbg = new ArrayList<>();
    ArrayList<Integer> cardimg = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_bottom_sheet, container, false);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        LinearLayout admin= view.findViewById(R.id.admin);
        LinearLayout logout = view.findViewById(R.id.logout);
        LinearLayout edit= view.findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                startActivity(intent);

            }
        });
        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserModel model= snapshot.getValue(UserModel.class);
                    if(model.getRole().equals("student")){
                        admin.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getContext(), GetStartedActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminDashboardActivity.class));
            }
        });
        return view;
    }
}