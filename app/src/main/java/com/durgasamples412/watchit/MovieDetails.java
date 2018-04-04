package com.durgasamples412.watchit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.durgasamples412.watchit.Adapters.CastAdapter;
import com.durgasamples412.watchit.Adapters.CrewAdapter;
import com.durgasamples412.watchit.Adapters.TrailerAdapter;
import com.durgasamples412.watchit.Networking.ApiClient;
import com.durgasamples412.watchit.Networking.ApiInterface;
import com.durgasamples412.watchit.POJO.Cast;
import com.durgasamples412.watchit.POJO.CastAndCrewModel;
import com.durgasamples412.watchit.POJO.Crew;
import com.durgasamples412.watchit.POJO.Details;
import com.durgasamples412.watchit.POJO.Result;
import com.durgasamples412.watchit.POJO.Videos.VideoResult;
import com.durgasamples412.watchit.POJO.Videos.VideoDetails;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    List<Cast> castList;
    List<Crew>crewList;
    List<Result>detailslist;
    AppBarLayout mAppBarLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    TextView title,tagline,vote_avg,productionHouse,duration;
    ImageView details_poster,thumbnail;
    ImageButton play,fav;
    CrewAdapter crewAdapter;
    CastAdapter adapter;
    TrailerAdapter trailerAdapter;
    ProgressBar bar1,bar2;
    List<VideoDetails>videoDetailsList;
    RecyclerView castrv,crewrv,trailersRv;
    TextView trailers,casttext,crewtext;
    DBHelper helper;
    ExpandableTextView expandableTextView;
    TextView expandabletext;
    View view1,view2;
    ImageButton expandcollpase;
    int id;
     int mPosterHeight;
     int mPosterWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mAppBarLayout = findViewById(R.id.appbarlayout);
        bar1 = findViewById(R.id.bar);
        bar2 =findViewById(R.id.bar1);
        view1 =findViewById(R.id.view1);
        view2 =findViewById(R.id.view2);
        expandableTextView = findViewById(R.id.expand_text_view);
        expandabletext = findViewById(R.id.expandable_text);
        expandcollpase = findViewById(R.id.expand_collapse);
        castrv = findViewById(R.id.castRv);
        videoDetailsList = new ArrayList <>();
        trailersRv = findViewById(R.id.Trailersrv);
        trailerAdapter = new TrailerAdapter(videoDetailsList,this);
        trailersRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        trailersRv.addItemDecoration(new SpacesItemDecoration(6));
        trailersRv.setAdapter(trailerAdapter);
        trailers = findViewById(R.id.detailstrailertext);
        trailers.setVisibility(View.INVISIBLE);
        casttext = findViewById(R.id.castTitle);
        casttext.setVisibility(View.INVISIBLE);
        crewtext = findViewById(R.id.crewTitle);
        crewtext.setVisibility(View.INVISIBLE);

        fav = findViewById(R.id.details_fav);
        title = findViewById(R.id.detailstitle);
      //  tagline = findViewById(R.id.details_overview);
        vote_avg = findViewById(R.id.details_vote_average);
        duration = findViewById(R.id.duration);
        thumbnail = findViewById(R.id.trailer_thumbnail);
        play = findViewById(R.id.playbutton);
        productionHouse = findViewById(R.id.productioncompany);
        details_poster = findViewById(R.id.details_img);
        mCollapsingToolbarLayout =findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setTitle("");
        helper = new DBHelper(MovieDetails.this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       setTitle("");
        detailslist = new ArrayList<>();
         id=getIntent().getExtras().getInt("id");

         mPosterWidth = (getResources().getDisplayMetrics().widthPixels);
        mPosterHeight = (int) (mPosterWidth / 0.90);

        details_poster.getLayoutParams().width = mPosterWidth;
        details_poster.getLayoutParams().height = mPosterHeight;



        //***********************Movie details Call*****************************//
        ApiInterface apiService5 = ApiClient.getClient().create(ApiInterface.class);
        Call<Details>movieDetailscall = apiService5.getMovieDetails(id,getResources().getString(R.string.tmdb_pi_key));
        movieDetailscall.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, final Response<Details> response) {


                bar1.setVisibility(View.GONE);
                expandableTextView.setText(response.body().getOverview());
                Log.d("details url",response.toString());
                title.setText(response.body().getOriginalTitle());
              //  tagline.setText(response.body().getOverview());
                vote_avg.setText(String.valueOf(response.body().getVoteAverage()));
                vote_avg.append("/10");
                vote_avg.append(" from "+String.valueOf(response.body().getVoteCount())+" users");
                vote_avg.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_action_rating),null,null,null);
                vote_avg.setBackground(getResources().getDrawable(R.drawable.background));

                if(response.body().getProductionCompanies()!=null){

                    for(int i=0;i<response.body().getProductionCompanies().size();i++){
                        String name = response.body().getProductionCompanies().get(i).getName();
                        productionHouse.setText(name);
                        productionHouse.setBackground(getResources().getDrawable(R.drawable.background));
                        productionHouse.append(",");
                        productionHouse.append(response.body().getProductionCompanies().get(i).getName());
                    }
                }else{
                    productionHouse.setVisibility(View.GONE);
                }

                    duration.setText(String.valueOf(response.body().getRuntime()));
                duration.append(" minutes");
                duration.setBackground(getResources().getDrawable(R.drawable.background));

                String imageurl = "https://image.tmdb.org/t/p/original"+response.body().getBackdropPath();

                Glide.with(MovieDetails.this).load(imageurl).asBitmap().centerCrop().into(details_poster);
                mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {

                            mCollapsingToolbarLayout.setTitle(response.body().getOriginalTitle());
                            toolbar.setVisibility(View.VISIBLE);
                        } else {
                            mCollapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                 }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {

            }
        });

        //***************************Videos Call*******************************//
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<VideoResult>videoDetailsCall = apiService.getvideodetails(id,getResources().getString(R.string.tmdb_pi_key));
        videoDetailsCall.enqueue(new Callback<VideoResult>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<VideoResult> call, final Response<VideoResult> response) {

                bar2.setVisibility(View.GONE);
                if (response.body().getResults().size()>0){
                    trailers.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    for(int i=1;i<response.body().getResults().size();i++){
                        VideoDetails details = new VideoDetails();
                        details.setKey(response.body().getResults().get(i).getKey());
                        videoDetailsList.add(details);

                    }
                    Glide.with(MovieDetails.this).load("http://img.youtube.com/vi/" + response.body().getResults().get(0).getKey() + "/hqdefault.jpg")
                            .asBitmap()
                            .centerCrop().error(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(thumbnail);


                }else if(response.body().getResults().size()==1){
                    trailers.setVisibility(View.VISIBLE);
                    Glide.with(MovieDetails.this).load("http://img.youtube.com/vi/" + response.body().getResults().get(0).getKey() + "/hqdefault.jpg")
                            .asBitmap()
                            .centerCrop().error(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(thumbnail);
                    VideoDetails details = new VideoDetails();
                    details.setKey(response.body().getResults().get(0).getKey());
                    videoDetailsList.add(details);
                }else
                {
                    trailers.setVisibility(View.VISIBLE);
                    trailers.setText(" No Trailers.Sorry...");
                    Glide.with(MovieDetails.this).load(R.drawable.na)
                            .asBitmap()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(thumbnail);
                    play.setEnabled(false);

                }
                Log.d("Videos Url",response.toString());
                play.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String key = response.body().getResults().get(0).getKey();
                       Intent i =new Intent(Intent.ACTION_VIEW);
                       i.setData(Uri.parse("https://www.youtube.com/watch?v="+key));
                       startActivity(i);
                   }
               });
                trailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {

            }
        });

        //****************************cast call**************************//
        castList = new ArrayList<>();
        adapter = new CastAdapter(this, castList);
        castrv.setAdapter(adapter);
        castrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        castrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService3 = ApiClient.getClient().create(ApiInterface.class);
        Call<CastAndCrewModel>castAndCrewModelCall = apiService3.getcastcall(id,getResources().getString(R.string.tmdb_pi_key));
       castAndCrewModelCall.enqueue(new Callback<CastAndCrewModel>() {
           @Override
           public void onResponse(Call<CastAndCrewModel> call, Response<CastAndCrewModel> response) {
               if(response.isSuccessful()){
                   casttext.setVisibility(View.VISIBLE);
                   view2.setVisibility(View.VISIBLE);
               }
               for(int i=0;i<response.body().getCast().size();i++){

                   if(response.body().getCast().get(i).getProfilePath()!=null){
                       Cast  model = new Cast();
                       model.setName(response.body().getCast().get(i).getName());
                       model.setCharacter(response.body().getCast().get(i).getCharacter());
                       model.setProfilePath(response.body().getCast().get(i).getProfilePath());
                       Log.d("Cast url",response.toString());
                       castList.add(model);
                   }

               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<CastAndCrewModel> call, Throwable t) {

           }
       });


        //****************************crew call**************************//
        crewrv = findViewById(R.id.crewRv);
        crewList = new ArrayList<>();
        crewAdapter = new CrewAdapter(this, crewList);
        crewrv.setAdapter(crewAdapter);
        crewrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        crewrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService4 = ApiClient.getClient().create(ApiInterface.class);
        Call<CastAndCrewModel>crewModelCall = apiService4.getcastcall(id,getResources().getString(R.string.tmdb_pi_key));
        crewModelCall.enqueue(new Callback<CastAndCrewModel>() {
            @Override
            public void onResponse(Call<CastAndCrewModel> call, Response<CastAndCrewModel> response) {
                if(response.isSuccessful()){
                    crewtext.setVisibility(View.VISIBLE);
                }
                for(int i=0;i<response.body().getCrew().size();i++){


                        Crew  crew = new Crew();
                        crew.setName(response.body().getCrew().get(i).getName());
                        crew.setJob(response.body().getCrew().get(i).getJob());
                    if(response.body().getCrew().get(i).getProfilePath()!=null){
                        crew.setProfilePath(response.body().getCrew().get(i).getProfilePath());

                    }
                    crewList.add(crew);
                    Log.d("crew url",response.toString());

                }
                crewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastAndCrewModel> call, Throwable t) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
