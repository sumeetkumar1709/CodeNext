package com.segmnf.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.Utils.SyntaxHighlighter;
import com.segmnf.myapplication.databinding.FragmentEditorBinding;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.util.HashSet;
import java.util.Set;


public class EditorFragment extends Fragment {

    FragmentEditorBinding binding;
    private FirebaseDatabase database;
    SharedPreferences preferences;
    public static final String MY_SHARED_PREFERENCES = "CODE" ;
    String cppCode = "#include <iostream>\n" +
            "using namespace std;\n" +
            "\n" +
            "int main() {\n" +
            "\t// your code goes here\n" +
            "\treturn 0;\n" +
            "}";
    String javacode ="public class Contest  \n" +
            "{  \n" +
            "public static void main(String args[])   \n" +
            "{  \n" +
            "\t// your code goes here\n" +
            "}  \n" +
            "}  ";

    String ccode="#include <stdio.h>\n" +
            "int main() {\n" +
            "\t// your code goes here\n" +
            "\treturn 0;\n" +
            "}";


    String pycode="# This program prints Hello, world!\n" +
            "\n" +
            "    print('Hello, world!')";

    String kotlin="fun main(args: Array<String>) {\n" +
            "    println(\"Hello, World!\")\n" +
            "}";

    String csharp="using System;\n" +
            "using System.Collections.Generic;\n" +
            "using System.Linq;\n" +
            "using System.Text;\n" +
            "using System.Threading.Tasks;\n" +
            "\n" +
            "namespace CSharpTut\n" +
            "{\n" +
            "    class Program\n" +
            "    {\n" +
            "        static void Main(string[] args)\n" +
            "        {\n" +
            "            string message = \"Hello World!!\";\n" +
            "\n" +
            "            Console.WriteLine(message);\n" +
            "        }\n" +
            "    }\n" +
            "}";

    String rubycode="\n" +
            "puts \"Hello World\"";

    String lang= "cpp";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditorBinding.inflate(inflater);
        database = FirebaseDatabase.getInstance("https://tynkr-3915c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        ContestQuestionDetailActivity contestQuestionDetailActivity = (ContestQuestionDetailActivity) getActivity();

//        for(int i =0; i< testcases.length;i++)
//            Toast.makeText(getContext(), ""+testcases[i], Toast.LENGTH_SHORT).show();


        binding.codeView.setTextHighlighted(cppCode);
        SyntaxHighlighter.applyMonokaiTheme(getContext(), binding.codeView);
        binding.codeView.setEnableLineNumber(true);
        binding.codeView.setLineNumberTextColor(Color.parseColor("#BABABA"));
        binding.codeView.setLineNumberTextSize(24f);
        Set<Character> indentationStart = new HashSet<>();
        indentationStart.add('{');
        binding.codeView.setIndentationStarts(indentationStart);


        Set<Character> indentationEnds = new HashSet<>();
        indentationEnds.add('}');
        binding.codeView.setIndentationEnds(indentationEnds);
        binding.codeView.setTabLength(4);
        binding.codeView.setEnableAutoIndentation(true);
        binding.codeView.setMatchingHighlightColor(Color.parseColor("#BABABA"));


        binding.spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int oldIndex, @Nullable String oldItem, int newIndex, String newItem) {
                if(newIndex==0)
                {
                    binding.codeView.setTextHighlighted(cppCode);
                    lang= "cpp";
                }
               else if(newIndex==1)
                {
                    binding.codeView.setTextHighlighted(ccode);
                    lang="c";
                }
               else if(newIndex==2)
                {
                    binding.codeView.setTextHighlighted(javacode);
                    lang ="java";
                }
                else if(newIndex==3)
                {
                    binding.codeView.setTextHighlighted(pycode);
                    lang ="python3";
                }
                else if(newIndex==4)
                {
                    binding.codeView.setTextHighlighted(csharp);
                    lang ="csharp";
                }
                else if(newIndex==5)
                {
                    binding.codeView.setTextHighlighted(kotlin);
                    lang ="kotlin";
                }
                else if(newIndex==6)
                {
                    binding.codeView.setTextHighlighted(rubycode);
                    lang ="ruby";
                }
            }
        });


        binding.runCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.codeView.getText().toString();
                preferences = getActivity().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("code", code);
                editor.putString("lang", lang);
                editor.commit();
                Toast.makeText(getContext(), "Saved.", Toast.LENGTH_SHORT).show();

            }
        });
        binding.restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = getActivity().getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                binding.codeView.setTextHighlighted(preferences.getString("code",""));

                Toast.makeText(getContext(), "Restored last saved code.", Toast.LENGTH_SHORT).show();

            }
            });


        return binding.getRoot();
    }
}