package com.segmnf.myapplication.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.segmnf.myapplication.ContestQuestionDetailActivity;
import com.segmnf.myapplication.Model.NewsModel;
import com.segmnf.myapplication.NewsDetailActivity;
import com.segmnf.myapplication.R;


import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    ArrayList<NewsModel> list;

    public NewsAdapter(ArrayList<NewsModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel model1 = list.get(position);

        if(!model1.getTitle().equals("null")){

            holder.newsHeadline1.setText(model1.getTitle());

        }
        else{
            holder.newsHeadline1.setText("");
        }

        if(!model1.getDescription().equals("null")){

            holder.newsHeadline2.setText(model1.getDescription());
        }
        else{
            holder.newsHeadline2.setText("");
        }
        Glide.with(holder.newsImage.getContext()).load(model1.getNewsImage()).placeholder(R.drawable.maintopbg).into(holder.newsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                intent.putExtra("name", model1.getAuthor());
                intent.putExtra("title", model1.getTitle());
                intent.putExtra("time", model1.getDate());
                intent.putExtra("content", model1.getContent());
                intent.putExtra("image", model1.getNewsImage());

                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView newsImage,newsExpand;
        TextView newsHeadline1, newsHeadline2;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsHeadline1= itemView.findViewById(R.id.News_Headline1);
            newsHeadline2= itemView.findViewById(R.id.News_Headline2);
            newsImage= itemView.findViewById(R.id.News_Image);
            newsExpand= itemView.findViewById(R.id.News_expand);

        }
    }
}