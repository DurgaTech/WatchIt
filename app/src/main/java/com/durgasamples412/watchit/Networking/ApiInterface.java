package com.durgasamples412.watchit.Networking;

import com.durgasamples412.watchit.POJO.CastAndCrewModel;
import com.durgasamples412.watchit.POJO.Details;
import com.durgasamples412.watchit.POJO.Popular;
import com.durgasamples412.watchit.POJO.NowShowingMovieModel;
import com.durgasamples412.watchit.POJO.TopRatedMovieModel;
import com.durgasamples412.watchit.POJO.UpComingMovieModel;
import com.durgasamples412.watchit.POJO.Videos.VideoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by USER on 24-01-2018.
 */

public interface ApiInterface {

    @GET("movie/upcoming")
    Call<UpComingMovieModel>upComingMovieModelCall(@Query("api_key") String apiKey, @Query("page") Integer page);
    @GET("movie/upcoming")
    Call<UpComingMovieModel>upComingMovieModelCallwithourpage(@Query("api_key") String apiKey);
    @GET("movie/{id}/credits")
    Call<CastAndCrewModel> getcastcall(@Path("id") Integer movieId, @Query("api_key") String apiKey);
    @GET("movie/{id}/credits")
    Call<CastAndCrewModel> getcrewcall(@Path("id") Integer movieId, @Query("api_key") String apiKey);
    @GET("movie/now_playing")
    Call<NowShowingMovieModel>nowshowingmoviemodelcall(@Query("api_key") String apiKey, @Query("page") Integer page);
    @GET("movie/popular")
    Call<Popular>popularmoviecall(@Query("api_key") String apiKey, @Query("page") Integer page);
    @GET("movie/top_rated")
    Call<TopRatedMovieModel>topraredmoviecall(@Query("api_key") String apiKey, @Query("page") Integer page);
    @GET("movie/{id}")
    Call<Details> getMovieDetails(@Path("id") Integer movieId, @Query("api_key") String apiKey);
    @GET("movie/{id}/videos")
    Call<VideoResult>getvideodetails(@Path("id")Integer movieId, @Query("api_key")String apikey);

}
