package com.jsfarley.slam.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.jsfarley.slam.service.MovieDataService;

public class DataSourceFactory extends DataSource.Factory {
	private MovieDataService mMovieDataService;
	private MovieDataSource mMovieDataSource;
	private Application mApplication;
	private MutableLiveData<MovieDataSource> mMovieDataSourceMutableLiveData;

	public DataSourceFactory(MovieDataService movieDataService, Application application) {
		mMovieDataService = movieDataService;
		mApplication = application;
		mMovieDataSourceMutableLiveData = new MutableLiveData<>();
	}

	@NonNull
	@Override
	public DataSource create() {
		mMovieDataSource = new MovieDataSource(mMovieDataService, mApplication);
		mMovieDataSourceMutableLiveData.postValue(mMovieDataSource);
		return mMovieDataSource;
	}
	public MutableLiveData<MovieDataSource> getMovieDataSourceMutableLiveData(){
		return mMovieDataSourceMutableLiveData;
	}
}
