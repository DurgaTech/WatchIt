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
import com.durgasamples412.watchit.POJO.Cast;
import com.durgasamples412.watchit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 07-02-2018.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    Context context;
    List<Cast> castArrayList = new ArrayList<>();


    public CastAdapter(Context context, List<Cast> castArrayList) {
        this.context = context;
        this.castArrayList = castArrayList;
    }

    public CastAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.castncrewname.setText(castArrayList.get(position).getName());
        holder.charactername.setText(castArrayList.get(position).getCharacter());

        String castimage = "https://image.tmdb.org/t/p/original"+ castArrayList.get(position).getProfilePath();
        Glide.with(context).load(castimage).placeholder(R.drawable.person).centerCrop().into(holder.castimage);
    }

    @Override
    public int getItemCount() {
        return castArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView castcard;
        ImageView castimage;
        TextView castncrewname,charactername;
        public MyViewHolder(View itemView) {
            super(itemView);
            castcard = itemView.findViewById(R.id.castcard);
            castimage = itemView.findViewById(R.id.castimage);
            castncrewname = itemView.findViewById(R.id.castname);
            charactername = itemView.findViewById(R.id.role);
        }
    }
}
