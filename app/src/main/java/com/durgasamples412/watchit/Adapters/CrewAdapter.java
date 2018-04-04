package com.durgasamples412.watchit.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.durgasamples412.watchit.POJO.Crew;
import com.durgasamples412.watchit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 07-02-2018.
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.MyViewHolder> {

    Context context;
    List<Crew> crewList = new ArrayList<>();


    public CrewAdapter(Context context, List<Crew> crewList) {
        this.context = context;
        this.crewList = crewList;
    }

    public CrewAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.castncrewname.setText(crewList.get(position).getName());
        holder.charactername.setText(crewList.get(position).getJob());

        String castimage = "https://image.tmdb.org/t/p/original"+ crewList.get(position).getProfilePath();
        Glide.with(context).load(castimage).centerCrop().placeholder(R.drawable.person).into(holder.castimage);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView castcard;
        ImageView castimage;
        TextView castncrewname,charactername;
        public MyViewHolder(View itemView) {
            super(itemView);
            castcard = itemView.findViewById(R.id.crewcard);
            castimage = itemView.findViewById(R.id.crewimage);
            castncrewname = itemView.findViewById(R.id.crewname);
            charactername = itemView.findViewById(R.id.job);
        }
    }
}
