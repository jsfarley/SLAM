package com.jsfarley.slam.service;

import com.jsfarley.slam.model.MovieDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
	@GET("movie/popular")
	Call<MovieDBResponse>getPopularMovies(@Query("api_key") String apiKey);

	@GET("movie/popular")
	Call<MovieDBResponse>getPopularMoviesPaging(@Query("api_key") String apiKey, @Query("page") long page);

	//prior to implementing, make sure connecting to tmdb is working with above call response.
	@GET("/search/movie")
	Call<MovieDBResponse>getSearchesPaging(@Query("api_key") String apiKey, @Query("page") long page);

}
