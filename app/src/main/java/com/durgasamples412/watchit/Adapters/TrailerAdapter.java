package com.durgasamples412.watchit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.durgasamples412.watchit.POJO.Videos.VideoDetails;
import com.durgasamples412.watchit.R;

import java.util.List;

/**
 * Created by USER on 01-03-2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

   private List<VideoDetails> videoDetailsList;
   private Context context;

    public TrailerAdapter(List <VideoDetails> videoDetailsList, Context context) {
        this.videoDetailsList = videoDetailsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers_pattern,null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,  int position) {
        Glide.with(context.getApplicationContext()).load("http://img.youtube.com/vi/" + videoDetailsList.get(position).getKey()+ "/hqdefault.jpg")
                .into(holder.trailerthumb);
        Log.d("thumbnail","http://img.youtube.com/vi/" + videoDetailsList.get(position).getKey()+ "/hqdefault.jpg");
        holder.trailerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v="+videoDetailsList.get(holder.getAdapterPosition()).getKey()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView trailerthumb;
        ImageButton trailerbtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            trailerbtn = itemView.findViewById(R.id.moretrailersplaybutton);
            trailerthumb = itemView.findViewById(R.id.moretrailerthumbnail);
        }
    }
}
