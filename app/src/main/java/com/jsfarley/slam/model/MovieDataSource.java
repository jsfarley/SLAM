package com.jsfarley.slam.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.jsfarley.slam.R;
import com.jsfarley.slam.service.MovieDataService;
import com.jsfarley.slam.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {
	private MovieDataService mMovieDataService;
	private Application mApplication;

	public MovieDataSource(MovieDataService movieDataService, Application application) {
		this.mMovieDataService = movieDataService;
		this.mApplication = application;
	}

	@Override
	public void loadInitial(@NonNull final LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {
		mMovieDataService = RetrofitInstance.getService();
		Call<MovieDBResponse> call = mMovieDataService.getPopularMoviesPaging(mApplication.getApplicationContext().getString(R.string.api_key), 1);
		call.enqueue(new Callback<MovieDBResponse>() {
			@Override
			public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
				MovieDBResponse movieDBResponse = response.body();
				ArrayList<Movie> movies = new ArrayList<>();
				if(movieDBResponse != null && movieDBResponse.getResults() != null){
					movies = (ArrayList<Movie>) movieDBResponse.getResults();
					callback.onResult(movies, null, (long)2);
				}
			}

			@Override
			public void onFailure(Call<MovieDBResponse> call, Throwable t) {

			}
		});
	}

	@Override
	public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

	}

	@Override
	public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {
		mMovieDataService = RetrofitInstance.getService();
		Call<MovieDBResponse> movieDBResponseCall = mMovieDataService.getPopularMoviesPaging(mApplication.getApplicationContext().getString(R.string.api_key), params.key);
		movieDBResponseCall.enqueue(new Callback<MovieDBResponse>() {
			@Override
			public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
				MovieDBResponse movieDBResponse = response.body();
				ArrayList<Movie> movies;
				if(movieDBResponse != null && movieDBResponse.getResults() != null){
					movies = (ArrayList<Movie>) movieDBResponse.getResults();
					callback.onResult(movies, params.key + 1);
				}
			}

			@Override
			public void onFailure(Call<MovieDBResponse> call, Throwable t) {

			}
		});
	}
}
