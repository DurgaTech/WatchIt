package com.durgasamples412.watchit;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.durgasamples412.watchit.Adapters.ViewAllAdapter;
import com.durgasamples412.watchit.Networking.ApiClient;
import com.durgasamples412.watchit.Networking.ApiInterface;
import com.durgasamples412.watchit.POJO.Popular;
import com.durgasamples412.watchit.POJO.Result;
import com.durgasamples412.watchit.POJO.TopRatedMovieModel;
import com.durgasamples412.watchit.POJO.UpComingMovieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUpComing extends AppCompatActivity {

    RecyclerView allupcomingrv;
    List<Result> resultArrayList;
    ViewAllAdapter adapter;
    int pagecount = 1;
    boolean loading = true;
    ConstraintLayout constraintLayout;

    boolean pagesover = false;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_up_coming);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resultArrayList = new ArrayList<>();
        constraintLayout = findViewById(R.id.cl);
        allupcomingrv = findViewById(R.id.allupcomingrv);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(AllUpComing.this, 3);
        allupcomingrv.setLayoutManager(gridLayoutManager);
        int keyvalue = getIntent().getExtras().getInt("key");
        switch (keyvalue) {
            case 1:
                setTitle("Upcoming");

                adapter = new ViewAllAdapter(resultArrayList, this);
                allupcomingrv.setAdapter(adapter);
                allupcomingrv.addItemDecoration(new SpacesItemDecoration(3));


                getUpcomingmovies(3);
                getUpcomingmovies(4);
                getUpcomingmovies(5);
                getUpcomingmovies(6);
                Snackbar.make(constraintLayout,"More Upcoming movies",Snackbar.LENGTH_SHORT).show();
                break;

            case 2:
                setTitle("Popular");
                adapter = new ViewAllAdapter(resultArrayList, this);
                allupcomingrv.setAdapter(adapter);
                allupcomingrv.addItemDecoration(new SpacesItemDecoration(3));
                getpopularmovies(3);
                getpopularmovies(4);
                getpopularmovies(5);
                getpopularmovies(6);
                Snackbar.make(constraintLayout,"More Popular movies",Snackbar.LENGTH_SHORT).show();
                break;
            case 3:
                setTitle("Top Rated");
                adapter = new ViewAllAdapter(resultArrayList, this);
                allupcomingrv.setAdapter(adapter);
                allupcomingrv.addItemDecoration(new SpacesItemDecoration(3));

                getTopRatedMovies(3);
                getTopRatedMovies(4);
                getTopRatedMovies(5);
                getTopRatedMovies(6);
                Snackbar.make(constraintLayout,"More Top rated movies",Snackbar.LENGTH_SHORT).show();
                break;
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void getUpcomingmovies(int page) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<UpComingMovieModel> upComingMovieModelCall = apiService.upComingMovieModelCall(getResources().getString(R.string.tmdb_pi_key), page);
        upComingMovieModelCall.enqueue(new Callback<UpComingMovieModel>() {
            @Override
            public void onResponse(Call<UpComingMovieModel> call, Response<UpComingMovieModel> response) {

                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Log.d("Total pages url", response.toString());
                    Result result = new Result();
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    resultArrayList.add(result);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UpComingMovieModel> call, Throwable t) {

            }
        });
    }

    public void getpopularmovies(int page) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<Popular> getPopularcall = apiService.popularmoviecall(getResources().getString(R.string.tmdb_pi_key), page);
        getPopularcall.enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {

                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Log.d("Total pages url", response.toString());
                    Result result = new Result();
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    resultArrayList.add(result);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {

            }
        });

    }

    public void getTopRatedMovies(int page) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<TopRatedMovieModel> topRatedMovieModelCall = apiService.topraredmoviecall(getResources().getString(R.string.tmdb_pi_key), page);
        topRatedMovieModelCall.enqueue(new Callback<TopRatedMovieModel>() {
            @Override
            public void onResponse(Call<TopRatedMovieModel> call, Response<TopRatedMovieModel> response) {

                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Log.d("Total pages url", response.toString());
                    Result result = new Result();
                    result.setId(response.body().getResults().get(i).getId());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    resultArrayList.add(result);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TopRatedMovieModel> call, Throwable t) {

            }
        });

    }

}