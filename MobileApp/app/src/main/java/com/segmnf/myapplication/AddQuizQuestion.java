package com.segmnf.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.segmnf.myapplication.Model.QuizModel;
import com.segmnf.myapplication.Model.QuizQuestionModel;
import com.segmnf.myapplication.databinding.ActivityAddQuizBinding;
import com.segmnf.myapplication.databinding.ActivityAddQuizQuestionBinding;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AddQuizQuestion extends AppCompatActivity {
    ActivityAddQuizQuestionBinding binding;
    private FirebaseDatabase database;
    Uri uri2;
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
        binding = ActivityAddQuizQuestionBinding.inflate(getLayoutInflater());
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
                if(binding.namequizquestion.getText().toString().trim().length()>0  && binding.marksquizquestion.getText().toString().trim().length()>0  &&
                        binding.op1question.getText().toString().trim().length()>0  && binding.op2question.getText().toString().trim().length()>0  && binding.op3question.getText().toString().trim().length()>0  &&
                        binding.op4question.getText().toString().trim().length()>0  && binding.correctopquestion.getText().toString().trim().length()>0) {

                    database.getReference().child("Extras").child("quizquestion").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            value = Integer.parseInt(snapshot.getValue().toString());
                            QuizQuestionModel model = new QuizQuestionModel(FirebaseAuth.getInstance().getUid(), "", String.valueOf(value), binding.marksquizquestion.getText().toString().trim(),
                                    binding.namequizquestion.getText().toString().trim(), "", binding.op1question.getText().toString().trim(), binding.op2question.getText().toString().trim(),
                                    binding.op3question.getText().toString().trim(),
                                    binding.op4question.getText().toString().trim(), binding.correctopquestion.getText().toString().trim());
                            if(uri2!=null)
                            uploadToFirebase(uri2);

                            database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(String.valueOf(value)).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        database.getReference().child("Extras").child("quizquestion").setValue(String.valueOf(value + 1));
                                        Toast.makeText(AddQuizQuestion.this, "Added Successfully", Toast.LENGTH_SHORT).show();
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
                else Toast.makeText(AddQuizQuestion.this, "Enter all fields", Toast.LENGTH_SHORT).show();


            }
        });
        binding.questionimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddQuizQuestion.this)
                        .compress(200)		//Final image size will be less than 1 MB(Optional)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent Intent) {
                                startForProfileImageResult.launch(Intent );
                                return null;
                            }
                        });
            }
        });
    }
    ActivityResultLauncher startForProfileImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent intent = result.getData();
                        if (intent != null) {
                             uri2 = intent.getData();
                            binding.questionimage.setImageURI(uri2);


                        }
                    }
                }
            });

    public void uploadToFirebase(Uri uri){
        StorageReference sref = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = sref.child("Images").child((FirebaseAuth.getInstance().getUid())).child("QuestionPic");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        database.getReference().child("Admins").child(FirebaseAuth.getInstance().getUid()).child("Quizzes").child(String.valueOf(value)).child("image").setValue(uri.toString());
                        Toast.makeText(AddQuizQuestion.this,"Picture updated",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddQuizQuestion.this,"Uploading Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}