package com.kitty.movietune;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

/**
 * Created by Mohita on 5/2/2017.
 */
public interface MovieService {
    // newRelease
    @GET("movie/now_playing")
    Call<MoviesResponse> getNewReleaseMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );

    //TopRated
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );


    //upcoming
    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageIndex
    );

}
