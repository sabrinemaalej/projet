package com.example.apppfe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder> {
    private ArrayList<personne> mlist;
    private Context context;
    public  myadapter(Context context,ArrayList<personne> mlist){
        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
      return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mlist.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView   imageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.m_image);
        }
    }
}
