package com.durgasamples412.watchit.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.durgasamples412.watchit.DBHelper;
import com.durgasamples412.watchit.MovieDetails;
import com.durgasamples412.watchit.POJO.Result;
import com.durgasamples412.watchit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 30-01-2018.
 */

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.MyViewHolder> {

   private List<Result>viewallList = new ArrayList <>();
  private   Context context;
    private List<Integer>idslist;
  private DBHelper helper;

    public ViewAllAdapter(List<Result> viewallList, Context context) {
        this.viewallList = viewallList;
        this.context = context;
    }

    public ViewAllAdapter(Context context, List <Integer> idslist) {
        this.context = context;
        this.idslist = idslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.viewalllayout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         helper = new DBHelper(context);
        holder.title.setText(viewallList.get(position).getTitle());
        String imageurl = "https://image.tmdb.org/t/p/original"+viewallList.get(position).getPosterPath();

        Glide.with(context).load(imageurl).asBitmap().placeholder(R.drawable.placeholder).centerCrop().into(holder.imageView);

        if(helper.isMovieFavourite(viewallList.get(position).getId())){
            holder.favbutton.setTag("Fav");
            holder.favbutton.setImageResource(R.drawable.fav_filled);
        }else {
            holder.favbutton.setTag("Not_Fav");
            holder.favbutton.setImageResource(R.drawable.favourites);
        }


    }

    @Override
    public int getItemCount() {
        return viewallList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title;
        ImageView imageView;
        ImageButton favbutton;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.adapcardviewall);
            title = itemView.findViewById(R.id.adapvalltv);
            imageView = itemView.findViewById(R.id.adapvalliv);
            favbutton = itemView.findViewById(R.id.adapvallib);
            imageView.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.31);
            imageView.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("id",viewallList.get(getAdapterPosition()).getId());
                    Log.d("id",String.valueOf(viewallList.get(getAdapterPosition()).getId()));
                    context.startActivity(intent);


                }
            });

            favbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(favbutton.getTag()=="Fav"){
                        helper.removeFromFav(viewallList.get(getAdapterPosition()).getId());
                        favbutton.setImageResource(R.drawable.favourites);
                    }else if(favbutton.getTag()=="Not_Fav"){
                        helper.insertMovie(viewallList.get(getAdapterPosition()).getId(),viewallList.get(getAdapterPosition()).getTitle(),viewallList.get(getAdapterPosition()).getPosterPath());
                        favbutton.setImageResource(R.drawable.fav_filled);
                    }
                }
            });

        }


    }
}
