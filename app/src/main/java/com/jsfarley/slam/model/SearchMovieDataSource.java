package com.jsfarley.slam.model;

import android.app.Application;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.jsfarley.slam.R;
import com.jsfarley.slam.service.MovieDataService;
import com.jsfarley.slam.service.RetrofitInstance;
import com.jsfarley.slam.view.SLAMAdmin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieDataSource extends PageKeyedDataSource<Long, Movie> {
	private Application mApplication;
	private MovieDataService mMovieDataService;
	private Movie mMovie;
	private String query;
	public SearchMovieDataSource(Application application, MovieDataService movieDataService) {
		mApplication = application;
		mMovieDataService = movieDataService;


	}

	public void searchMovies(String query){

	}

	@Override
	public void loadInitial(@NonNull final LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {
		mMovieDataService = RetrofitInstance.getService();
		mMovie = new Movie();

		//TODO: HOW TO CONNECT WITH search view FROM FRONT END WITH DATABINDING????????? (QUERY)

		Call<MovieDBResponse> searchCall = mMovieDataService.getSearchesPaging(mApplication.getApplicationContext().getString(R.string.api_key),
			mMovie.getSearch(), 1);
		searchCall.enqueue(new Callback<MovieDBResponse>() {
			@Override
			public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
				MovieDBResponse movieDBResponse = response.body();
				ArrayList<Movie> list = new ArrayList<>();
				if (movieDBResponse != null && movieDBResponse.getResults() != null) {
					list = (ArrayList<Movie>) movieDBResponse.getResults();
					callback.onResult(list, null, (long) 2);
					Log.d("TAG", "Movie search result: " + mMovie.getSearch());

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
		Call<MovieDBResponse> searchCall = mMovieDataService.getSearchesPaging(mApplication.getApplicationContext().getString(R.string.api_key),
				query, params.key);
		searchCall.enqueue(new Callback<MovieDBResponse>() {
			@Override
			public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
				MovieDBResponse movieDBResponse = response.body();
				ArrayList<Movie> list = new ArrayList<>();
				if (movieDBResponse != null && movieDBResponse.getResults() != null) {
					list = (ArrayList<Movie>) movieDBResponse.getResults();
					callback.onResult(list, params.key + 1);
				}
			}

			@Override
			public void onFailure(Call<MovieDBResponse> call, Throwable t) {

			}
		});
	}
}
