package com.segmnf.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.segmnf.myapplication.Model.UserModel;
import com.segmnf.myapplication.databinding.ActivityUserDetailBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserDetailActivity extends AppCompatActivity {

    ActivityUserDetailBinding binding;
    private FirebaseDatabase database;
    String photouriString;
    int admin;


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
    public void hideStatus(){
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");


        binding.myPhotoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UserDetailActivity.this)
                        .crop(1,1)//Crop image(Optional), Check Customization for more option
                        .compress(200)		//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(480,480)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent Intent) {

                                startForProfileImageResult.launch(Intent );
                                return null;
                            }
                        });
            }
        });

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!binding.nameuser.getText().toString().equals("")) {

                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            uploadToFirebase(Uri.parse(photouriString));

                            String curr = new SimpleDateFormat("dd  MMM  yyyy").format(new Date());
                            UserModel model;
                            if(admin==1){
                                 model = new UserModel(FirebaseAuth.getInstance().getUid(), binding.nameuser.getText().toString(), binding.Biouser.getText().toString(), photouriString, "admin"
                                );
                            }
                            else
                            {
                                model = new UserModel(FirebaseAuth.getInstance().getUid(), binding.nameuser.getText().toString(), binding.Biouser.getText().toString(), photouriString, "student"
                                );
                            }
                            snapshot.getRef().setValue(model);

                            database.getReference().child("Incomplete_login").child(FirebaseAuth.getInstance().getUid()).removeValue();

                            Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {

                    Toast.makeText(UserDetailActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        binding.admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.admin.setTextColor(getResources().getColor(R.color.basket));
                admin=1;
                binding.student.setTextColor(getResources().getColor(R.color.background3));
            }
        });

        binding.student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.student.setTextColor(getResources().getColor(R.color.basket));
                admin=0;
                binding.admin.setTextColor(getResources().getColor(R.color.background3));
            }
        });

    }
    ActivityResultLauncher startForProfileImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent intent = result.getData();
                        if (intent != null) {
                            Uri uri2 = intent.getData();
                            photouriString = String.valueOf(uri2);
                            binding.myPhotoLoad.setImageURI(uri2);
                            binding.myPhoto.setVisibility(View.INVISIBLE);


                        }
                    }
                }
            });

        public void uploadToFirebase(Uri uri){
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            StorageReference fileRef = sref.child("Images").child((FirebaseAuth.getInstance().getUid())).child("UserPic");
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            database.getReference().child("User_details").child(currentUser.getUid()).child("image").setValue(uri.toString());
                            Toast.makeText(UserDetailActivity.this,"Profile picture updated",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserDetailActivity.this,"Uploading Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
}