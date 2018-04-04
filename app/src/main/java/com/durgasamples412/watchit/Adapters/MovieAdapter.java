package com.durgasamples412.watchit.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.durgasamples412.watchit.DBHelper;
import com.durgasamples412.watchit.MainActivity;
import com.durgasamples412.watchit.MovieDetails;
import com.durgasamples412.watchit.POJO.Result;
import com.durgasamples412.watchit.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by USER on 24-01-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.UMViewHolder> {

    Context context;
    List<Result>moviemodelList = new ArrayList<>();
    DBHelper helper;

    public MovieAdapter(Context context, List<Result> upComingMovieModelList) {
        this.context = context;
        this.moviemodelList = upComingMovieModelList;
    }

    @Override
    public UMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.umadapterlayout,null);

        return new UMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UMViewHolder holder, int position) {
        helper = new DBHelper(context);

        if(moviemodelList.get(position).getPosterPath()!=null){
            String imageurl = "https://image.tmdb.org/t/p/original"+moviemodelList.get(position).getBackdropPath();
            Log.d("imageurl",imageurl);
            Glide.with(context).load(imageurl).asBitmap().placeholder(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(holder.moviePosterImage);
            holder.movieTitleText.setText(moviemodelList.get(position).getTitle());
            if(moviemodelList.get(position).getVoteAverage()>0) {
                float rating = (float) moviemodelList.get(position).getVoteAverage() / 2;
                holder.movieRating.setRating(rating);
            }
            if(helper.isMovieFavourite(moviemodelList.get(position).getId())){
                holder.FavImageButton.setTag("Fav");
                holder.FavImageButton.setImageResource(R.drawable.fav_filled);
            }else {
                holder.FavImageButton.setTag("Not_Fav");
                holder.FavImageButton.setImageResource(R.drawable.favourites);
            }

        }

      //  holder.releasedate.setText(upComingMovieModelList.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return moviemodelList.size();
    }

    public class UMViewHolder extends RecyclerView.ViewHolder{
        public CardView movieCardView;
       public RelativeLayout imageLayout;
        public ImageView moviePosterImage;
        public TextView movieTitleText;
        public RatingBar movieRating;
        TextView releasedate;

        public ImageButton FavImageButton;
    public UMViewHolder(View itemView) {
        super(itemView);
        movieCardView = itemView.findViewById(R.id.card_view_show_card);
     imageLayout = itemView.findViewById(R.id.image_layout_show_card);
        moviePosterImage = itemView.findViewById(R.id.image_view_show_card);
        movieTitleText = itemView.findViewById(R.id.text_view_title_show_card);
        movieRating = itemView.findViewById(R.id.rating_show_card);
        FavImageButton = itemView.findViewById(R.id.image_button_fav_show_card);
    //    releasedate = itemView.findViewById(R.id.release_date);
       imageLayout.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85);
     imageLayout.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.85) / 3);

     movieCardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(context, MovieDetails.class);
             ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,((Activity) context).findViewById(R.id.image_view_show_card),"poster");
             intent.putExtra("id",moviemodelList.get(getAdapterPosition()).getId());
             context.startActivity(intent,optionsCompat.toBundle());
         }
     });


        FavImageButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(FavImageButton.getTag()=="Fav"){
                 helper.removeFromFav(moviemodelList.get(getAdapterPosition()).getId());
                 FavImageButton.setImageResource(R.drawable.favourites);
           }else if(FavImageButton.getTag()=="Not_Fav"){
               helper.insertMovie(moviemodelList.get(getAdapterPosition()).getId(),moviemodelList.get(getAdapterPosition()).getTitle(),moviemodelList.get(getAdapterPosition()).getPosterPath());
               FavImageButton.setImageResource(R.drawable.fav_filled);
           }
         }


     });
    }
}

}
