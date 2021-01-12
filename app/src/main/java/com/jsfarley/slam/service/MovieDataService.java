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

	@GET("search/movie")
	Call<MovieDBResponse>getSearchesPaging(@Query("api_key") String apiKey, @Query("query") String query,
	                                       @Query("page") long page);

}
