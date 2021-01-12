package com.jsfarley.slam.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jsfarley.slam.R;
import com.jsfarley.slam.service.MovieDataService;
import com.jsfarley.slam.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
	private ArrayList<Movie> mMovieArrayList = new ArrayList<>();
	private MutableLiveData<List<Movie>> mListMutableLiveData = new MutableLiveData<>();
	//Application is needed as parameter
	private Application mApplication;
	private Movie movie;
	private String query;

	public MovieRepository(Application application){
		mApplication = application;
	}
	public MutableLiveData<List<Movie>> getListMutableLiveData(){
		MovieDataService movieDataService = RetrofitInstance.getService();
		//Call<MovieDBResponse> call = movieDataService.getPopularMovies(mApplication.getApplicationContext().getString(R.string.api_key));
		Call<MovieDBResponse> searchCall = movieDataService.getSearchesPaging(mApplication.getApplicationContext().getString(R.string.api_key),
																				query, (long)1);
		searchCall.enqueue(new Callback<MovieDBResponse>() {
			@Override
			public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
				MovieDBResponse movieDBResponse = response.body();
				if(movieDBResponse != null && movieDBResponse.getResults() != null){
					mMovieArrayList = (ArrayList<Movie>) movieDBResponse.getResults();
					//How livedata receives array list values!
					mListMutableLiveData.setValue(mMovieArrayList);

				}
			}

			@Override
			public void onFailure(Call<MovieDBResponse> call, Throwable t) {

			}
		});
		return mListMutableLiveData;

	}
}
