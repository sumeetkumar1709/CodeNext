package com.segmnf.myapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.R;
import com.segmnf.myapplication.databinding.FragmentEditorBinding;
import com.segmnf.myapplication.databinding.FragmentQuestionBinding;


public class QuestionFragment extends Fragment {


    FragmentQuestionBinding binding;
    private FirebaseDatabase database;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuestionBinding.inflate(inflater);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");


        ContestQuestionDetailActivity contestQuestionDetailActivity = (ContestQuestionDetailActivity) getActivity();
        binding.questionname.setText(contestQuestionDetailActivity.getName());
        binding.avgtime.setText("Avg "+contestQuestionDetailActivity.getAvgtime()+" mins");
        binding.difficulty.setText(contestQuestionDetailActivity.getDifficulty());
        binding.eg1.setText(contestQuestionDetailActivity.getEg1());
        binding.eg2.setText(contestQuestionDetailActivity.getEg2());
        binding.constraint.setText(contestQuestionDetailActivity.getConstraints());
        binding.quesdescription.setText(contestQuestionDetailActivity.getDescription());
        binding.score.setText("Score: "+contestQuestionDetailActivity.getMarks());

        return binding.getRoot();

    }
}