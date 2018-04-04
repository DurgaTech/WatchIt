package com.durgasamples412.watchit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durgasamples412.watchit.Adapters.MovieAdapter;
import com.durgasamples412.watchit.Networking.ApiClient;
import com.durgasamples412.watchit.Networking.ApiInterface;
import com.durgasamples412.watchit.POJO.NowShowingMovieModel;
import com.durgasamples412.watchit.POJO.Popular;
import com.durgasamples412.watchit.POJO.Result;
import com.durgasamples412.watchit.POJO.TopRatedMovieModel;
import com.durgasamples412.watchit.POJO.UpComingMovieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    RecyclerView upcomingrv,nowshowingrv,popularrv,topratedrv;
    TextView viewallupcoming,viewallnowshowing,viewallpopular,viewalltoprated;
    FrameLayout upcomingframelayout,nowshowingfl,popularfl,topratedfl;
    MovieAdapter upcomingmovieadapter,nowshowingadapter,popularadapter,topratedadapter;
    List<Result> upcomingmovemodellist = new ArrayList<>();
    List<Result>nowshowingmoviemodelList= new ArrayList<>();
    List<Result>popularmovielist = new ArrayList<>();
    List<Result>topRatedMovieList = new ArrayList<>();
    ProgressBar progressBar;
    FloatingActionButton favFab,watchlistfab,fab;
    Animation fabopen,fabclose;
    boolean isfabopen = false;
    RelativeLayout fablayout;
    DBHelper helper;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upcomingframelayout = (FrameLayout)findViewById(R.id.layout_upcoming);
        nowshowingfl = findViewById(R.id.layout_Now_showing);
        popularfl = findViewById(R.id.layout_popular);
        favFab = findViewById(R.id.fabFav);
        helper = new DBHelper(this);
        watchlistfab = findViewById(R.id.fab3);
        fabopen = AnimationUtils.loadAnimation(this,R.anim.fabopen);
        fabclose = AnimationUtils.loadAnimation(this,R.anim.fabclose);
        topratedfl  = findViewById(R.id.layout_top_rated);
        viewallnowshowing = findViewById(R.id.text_view_view_all_nowshowing);
        viewallpopular = findViewById(R.id.text_view_view_all_popular);
        viewallupcoming = findViewById(R.id.text_view_view_all_upcoming);
        viewalltoprated = findViewById(R.id.text_view_view_all_toprated);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        upcomingframelayout = findViewById(R.id.layout_upcoming);

        //***************Upcoming movies recyclerview****************//
        upcomingrv = findViewById(R.id.recycler_view_upcoming);
        upcomingrv.setVisibility(View.VISIBLE);
        upcomingmovieadapter = new MovieAdapter(this,upcomingmovemodellist);
        upcomingrv.setAdapter(upcomingmovieadapter);
        upcomingmovieadapter.notifyDataSetChanged();
        new LinearSnapHelper().attachToRecyclerView(upcomingrv);
        upcomingrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        upcomingrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<UpComingMovieModel>upComingMovieModelCall = apiService.upComingMovieModelCall(getResources().getString(R.string.tmdb_pi_key),2);
        upComingMovieModelCall.enqueue(new Callback<UpComingMovieModel>() {
            @Override
            public void onResponse(retrofit2.Call<UpComingMovieModel> call, Response<UpComingMovieModel> response) {
                Log.d("ImageUrl",response.toString());

                upcomingframelayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Result result = new Result();
                        result.setBackdropPath(response.body().getResults().get(i).getBackdropPath());
                        result.setId(response.body().getResults().get(i).getId());
                        result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                        result.setOriginalTitle(response.body().getResults().get(i).getOriginalTitle());
                        result.setVoteAverage(response.body().getResults().get(i).getVoteAverage());
                        result.setTitle(response.body().getResults().get(i).getTitle());
                        result.setReleaseDate(response.body().getResults().get(i).getReleaseDate());
                        upcomingmovemodellist.add(result);
                    }

             upcomingmovieadapter.notifyDataSetChanged();

            }



            @Override
            public void onFailure(retrofit2.Call<UpComingMovieModel> call, Throwable t) {

            }
        });

        //*********************All Upcoming click here****************************//
        viewallupcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,AllUpComing.class);
                intent.putExtra("key",1);
                startActivity(intent);

            }
        });


        //***********************All Popular movies click here*****************************//
        viewallpopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllUpComing.class);
                intent.putExtra("key",2);
                startActivity(intent);

            }
        });


        //***********************All top rated movies click here*****************************//
        viewalltoprated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllUpComing.class);
                intent.putExtra("key",3);
                startActivity(intent);

            }
        });

//****************************now showing recylcerview****************************************//

        nowshowingrv = findViewById(R.id.recycler_view_nowshowing);
        nowshowingrv.setVisibility(View.VISIBLE);
        nowshowingadapter= new MovieAdapter(MainActivity.this,nowshowingmoviemodelList);
        nowshowingrv.setAdapter(nowshowingadapter);
        new LinearSnapHelper().attachToRecyclerView(nowshowingrv);
        nowshowingrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        nowshowingrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);
        final retrofit2.Call<NowShowingMovieModel>nowShowingMovieModelCall = apiService2.nowshowingmoviemodelcall(getResources().getString(R.string.tmdb_pi_key),2);
        nowShowingMovieModelCall.enqueue(new Callback<NowShowingMovieModel>() {
            @Override
            public void onResponse(retrofit2.Call<NowShowingMovieModel> call, Response<NowShowingMovieModel> response) {

                Log.d("Now showing",response.toString());
                nowshowingfl.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Result result = new Result();
                    result.setBackdropPath(response.body().getResults().get(i).getBackdropPath());
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setOriginalTitle(response.body().getResults().get(i).getOriginalTitle());
                    result.setVoteAverage(response.body().getResults().get(i).getVoteAverage());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    result.setReleaseDate(response.body().getResults().get(i).getReleaseDate());
                    nowshowingmoviemodelList.add(result);
                }

                nowshowingadapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(retrofit2.Call<NowShowingMovieModel> call, Throwable t) {

            }
        });


//******************************Popular Movies***********************************//
        popularrv = findViewById(R.id.recycler_view_popular);
        popularrv.setVisibility(View.VISIBLE);
        popularadapter = new MovieAdapter(this,popularmovielist);
        popularrv.setAdapter(popularadapter);
        new LinearSnapHelper().attachToRecyclerView(popularrv);
        popularrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        popularrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService3 = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<Popular>popularCall = apiService3.popularmoviecall(getResources().getString(R.string.tmdb_pi_key),2);
        popularCall.enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(retrofit2.Call<Popular> call, Response<Popular> response) {
                Log.d("ImageUrl",response.toString());

                popularfl.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Result result = new Result();
                    result.setBackdropPath(response.body().getResults().get(i).getBackdropPath());
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setOriginalTitle(response.body().getResults().get(i).getOriginalTitle());
                    result.setVoteAverage(response.body().getResults().get(i).getVoteAverage());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    result.setReleaseDate(response.body().getResults().get(i).getReleaseDate());
                    popularmovielist.add(result);
                }

                popularadapter.notifyDataSetChanged();
            }



            @Override
            public void onFailure(retrofit2.Call<Popular> call, Throwable t) {

            }
        });

//******************************Top Rated  movies*********************************//
        topratedrv = findViewById(R.id.recycler_view_toprated);
        topratedrv.setVisibility(View.VISIBLE);
        topratedadapter = new MovieAdapter(this,topRatedMovieList);
        topratedrv.setAdapter(topratedadapter);
        new LinearSnapHelper().attachToRecyclerView(topratedrv);
        topratedrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        topratedrv.addItemDecoration(new SpacesItemDecoration(6));
        ApiInterface apiService4 = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<TopRatedMovieModel>topRatedMovieModelCall = apiService4.topraredmoviecall(getResources().getString(R.string.tmdb_pi_key),2);
        topRatedMovieModelCall.enqueue(new Callback<TopRatedMovieModel>() {
            @Override
            public void onResponse(retrofit2.Call<TopRatedMovieModel> call, Response<TopRatedMovieModel> response) {
                Log.d("ImageUrl",response.toString());

                topratedfl.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Result result = new Result();
                    result.setBackdropPath(response.body().getResults().get(i).getBackdropPath());
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setOriginalTitle(response.body().getResults().get(i).getOriginalTitle());
                    result.setVoteAverage(response.body().getResults().get(i).getVoteAverage());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    result.setReleaseDate(response.body().getResults().get(i).getReleaseDate());
                    topRatedMovieList.add(result);
                }

                topratedadapter.notifyDataSetChanged();
            }



            @Override
            public void onFailure(retrofit2.Call<TopRatedMovieModel> call, Throwable t) {

            }
        });




          fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                favFab.setVisibility(View.VISIBLE);
                watchlistfab.setVisibility(View.VISIBLE);
animateFab();


            }
        });
        favFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "favourites", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,FavouritesActivity.class);
                startActivity(intent);
            }
        });

        watchlistfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "watchlist", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void animateFab(){
        if(isfabopen){
            favFab.startAnimation(fabclose);
            watchlistfab.startAnimation(fabclose);
            favFab.setClickable(false);
            watchlistfab.setClickable(false);
            fab.setImageResource(R.drawable.closedhand);
            isfabopen = false;

        }else{
            favFab.startAnimation(fabopen);
            watchlistfab.startAnimation(fabopen);
            favFab.setClickable(true);
            watchlistfab.setClickable(true);
            isfabopen = true;
            fab.setImageResource(R.drawable.click);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        upcomingmovieadapter.notifyDataSetChanged();
        nowshowingadapter.notifyDataSetChanged();
        topratedadapter.notifyDataSetChanged();
        popularadapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
