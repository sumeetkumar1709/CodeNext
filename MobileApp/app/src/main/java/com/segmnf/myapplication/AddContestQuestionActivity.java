package com.segmnf.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.segmnf.myapplication.Model.QuestionModel;
import com.segmnf.myapplication.databinding.ActivityAddContestQuestionBinding;

import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AddContestQuestionActivity extends AppCompatActivity {
    ActivityAddContestQuestionBinding binding;
    private FirebaseDatabase database;
    int value;


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideStatus() {
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        binding = ActivityAddContestQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        binding.imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.addquestionquizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.namecontestquestion.getText().toString().trim().length() > 0
                        && binding.markscontestquestion.getText().toString().trim().length() > 0
                        && binding.contestdifficultyquestion.getText().toString().trim().length() > 0
                        && binding.contestquestiondescription.getText().toString().trim().length() > 0
                        && binding.eg1question.getText().toString().trim().length() > 0 &&
                        binding.eg2question.getText().toString().trim().length() > 0
                        && binding.constraintsquestioncontest.getText().toString().trim().length() > 0
                        && binding.testcases.getText().toString().trim().length() > 0
                        && binding.testoutput.getText().toString().trim().length() > 0
                        && binding.cpuusageallowed.getText().toString().trim().length() > 0
                        && binding.memoryusageallowed.getText().toString().trim().length() > 0)

                    database.getReference().child("Extras").child("contestquestion").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            value = Integer.parseInt(snapshot.getValue().toString());
                            QuestionModel model = new QuestionModel("", binding.namecontestquestion.getText().toString().trim(), "",
                                    binding.contestdifficultyquestion.getText().toString().trim(), binding.markscontestquestion.getText().toString().trim()
                                    , binding.contestquestiondescription.getText().toString().trim(), binding.eg1question.getText().toString().trim(),
                                    binding.eg2question.getText().toString().trim(),
                                    binding.constraintsquestioncontest.getText().toString().trim(), binding.testcases.getText().toString().trim(),
                                    binding.testoutput.getText().toString().trim(), binding.cpuusageallowed.getText().toString().trim(),
                                    binding.memoryusageallowed.getText().toString().trim(), "", String.valueOf(value));
                            if (binding.contestdifficultyquestion.getText().toString().toLowerCase(Locale.ROOT).equals("hard")) {
                                model.setDifficulty("Hard");
                                model.setAvgtime("60");
                            } else if (binding.contestdifficultyquestion.getText().toString().toLowerCase(Locale.ROOT).equals("easy")) {
                                model.setDifficulty("Easy");
                                model.setAvgtime("20");
                            } else if (binding.contestdifficultyquestion.getText().toString().toLowerCase(Locale.ROOT).equals("medium")) {
                                model.setDifficulty("Medium");
                                model.setAvgtime("40");
                            } else {
                                model.setDifficulty("NA");
                                model.setAvgtime("NA");
                            }


                            database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("questions").child(String.valueOf(value)).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        database.getReference().child("Extras").child("contestquestion").setValue(String.valueOf(value + 1));
                                        Toast.makeText(AddContestQuestionActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            }
        });
    }
}