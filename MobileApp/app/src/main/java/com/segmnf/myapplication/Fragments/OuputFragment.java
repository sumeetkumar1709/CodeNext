package com.segmnf.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.segmnf.myapplication.ApiHandler;
import com.segmnf.myapplication.ApiService;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.Utils.PostData;
import com.segmnf.myapplication.databinding.FragmentOuputBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuputFragment extends Fragment {
    FragmentOuputBinding binding;
    private FirebaseDatabase database;
    public static final String MY_SHARED_PREFERENCES = "CODE";
    String language = "cpp", versionIndex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOuputBinding.inflate(inflater);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");


        ContestQuestionDetailActivity contestQuestionDetailActivity = (ContestQuestionDetailActivity) getActivity();
        String testcase = contestQuestionDetailActivity.getTestcases();
        String testouts = contestQuestionDetailActivity.getTestoutputs();
        String cpu = contestQuestionDetailActivity.getCpu();
        String mem = contestQuestionDetailActivity.getMemory();
        String contestid  = contestQuestionDetailActivity.getContestid();
        String qid= contestQuestionDetailActivity.getQuid();
        String marks = contestQuestionDetailActivity.getMarks();

        int testcases = testcase.trim().charAt(0);
//        String[] testoutputs = testouts.trim().split("\\$");

        String clientId = "681fc3e94a1a80550c41aaa7ab53ede4"; //Replace with your client ID
        String clientSecret = "a12c4c9f6354909cb749ecae8774aac3025853f1d40b80bde7e4fd0c611528b"; //Replace with your client Secret
        binding.status.setText("Awaiting run");

        binding.runCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.outputwindow.setText("");
                binding.status.setText("Queued");

                SharedPreferences myPreferences = getActivity().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String restoredText = myPreferences.getString("code", null);
                language = myPreferences.getString("lang", "");
                String script = restoredText;
                String stdin = binding.inputwindow.getText().toString();
                if (language.equals("cpp")) {
                    language = "cpp14";
                    versionIndex = "4";
                }
                if (language.equals("java"))
                    versionIndex = "4";

                if (language.equals("c"))
                    versionIndex = "5";

                if (language.equals("python3"))
                    versionIndex = "4";

                if (language.equals("csharp"))
                    versionIndex = "4";

                if (language.equals("kotlin"))
                    versionIndex = "3";

                if (language.equals("ruby"))
                    versionIndex = "4";


                Log.d("TAG", language + versionIndex);
                Log.d("TAG", script);
                Log.d("TAG", stdin);

                String output =CallCompiler(script,stdin,language,versionIndex,0);

            }
        });


        binding.finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.outputwindow.setText("");

                SharedPreferences myPreferences = getActivity().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String restoredText = myPreferences.getString("code", null);
                language = myPreferences.getString("lang", "");
                String script = restoredText;
                binding.status.setText("Running TestCases, Please wait.");
                if (language.equals("cpp")) {
                    language = "cpp14";
                    versionIndex = "4";
                }
                if (language.equals("java"))
                    versionIndex = "4";

                if (language.equals("c"))
                    versionIndex = "5";

                if (language.equals("python3"))
                    versionIndex = "4";

                if (language.equals("csharp"))
                    versionIndex = "4";

                if (language.equals("kotlin"))
                    versionIndex = "3";

                if (language.equals("ruby"))
                    versionIndex = "4";

                Log.d("TAG", language + versionIndex);
                Log.d("TAG", script);
                Log.d("TAG", testcase);
                Log.d("TAG", qid);
//                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child(qid).child("status").setValue("Accepted");

                String output = CallCompiler(script, testcase,language, versionIndex,1);

                binding.sampler.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {


                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("Question").child(qid).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.getValue().equals("Accepted")){
                                    binding.status.setText("Already Scored!");
                                }
                                else
                                {
                                    if(binding.cputime.getText().equals("null")){
                                        binding.status.setText("TLE");
                                    }
                                    else if(Double.parseDouble(binding.cputime.getText().toString().trim())>Double.parseDouble(cpu) ||
                                            Double.parseDouble(binding.memtaken.getText().toString().trim())>Double.parseDouble(mem)){
                                        binding.status.setText("TLE");
                                    }
                                    else if(binding.sampler.getText().toString().trim().equals(testouts.trim())){
                                        binding.outputwindow.setText("CODE ACCEPTED!");
                                        binding.status.setText("All test cases passed");
                                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("Question").child(qid).child("status").setValue("Accepted");
                                        database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                int prevmarks= Integer.parseInt(snapshot.getValue().toString().trim());
                                                database.getReference().child("Score").child(FirebaseAuth.getInstance().getUid()).child("Contests").child(contestid).child("score").
                                                        setValue(String.valueOf(prevmarks+Integer.parseInt(marks)));
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                    else
                                        binding.status.setText("Failed test cases!");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                    }
                });



            }
        });
        return binding.getRoot();
    }

    public String CallCompiler(String script , String stdin, String language, String versionIndex,int sender ){
        ApiService apiService = ApiHandler.getRetrofitInstance();
        final String[] output = new String[1];
        Call<String> execute = apiService.execute(new PostData(script, stdin, language, versionIndex));


        execute.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    if (response.isSuccessful()) {
                        Log.d("TAG", response.toString());
                        JSONObject responseJson = new JSONObject(response.body().toString());
                        output[0] = responseJson.getString("output");
                        String memory = responseJson.getString("memory");
                        String cpu = responseJson.getString("cpuTime");
                        binding.memcpu.setText("Memory: "+memory+"\nCpu: "+cpu);
                        binding.memtaken.setText(memory);
                        binding.cputime.setText(cpu);
                        if(sender==0)
                        {
                            binding.outputwindow.setText(output[0]);
                        }
                        if(sender==1)
                        {
                            binding.outputwindow.setText("");
                            binding.sampler.setText(output[0]);
                        }

                    } else {

                        binding.status.setText(response.errorBody().string());
                    }

                } catch (JSONException e) {
                            Toast.makeText(getContext(), "CodeNext Parsing JSON : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                binding.status.setText("Failed to fetch response");
                Toast.makeText(getContext(), "CodeNext : " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return output[0];
    }
}