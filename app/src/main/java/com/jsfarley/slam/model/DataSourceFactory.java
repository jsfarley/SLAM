package com.jsfarley.slam.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.jsfarley.slam.service.MovieDataService;

public class DataSourceFactory extends DataSource.Factory {
	private MovieDataService mMovieDataService;
	private MovieDataSource mMovieDataSource;
	private SearchMovieDataSource mSearchMovieDataSource;
	private Application mApplication;
	//private MutableLiveData<MovieDataSource> mMovieDataSourceMutableLiveData;
	private MutableLiveData<SearchMovieDataSource> mSearchMovieDataSourceMutableLiveData;

	public DataSourceFactory(MovieDataService movieDataService, Application application) {
		mMovieDataService = movieDataService;
		mApplication = application;
		//mMovieDataSourceMutableLiveData = new MutableLiveData<>();
		mSearchMovieDataSourceMutableLiveData = new MutableLiveData<>();
	}

	@NonNull
	@Override
	public DataSource create() {
		mSearchMovieDataSource = new SearchMovieDataSource(mApplication, mMovieDataService);
		mSearchMovieDataSourceMutableLiveData.postValue(mSearchMovieDataSource);

		/*mMovieDataSource = new MovieDataSource(mMovieDataService, mApplication);
		mMovieDataSourceMutableLiveData.postValue(mMovieDataSource);*/
		//return mSearchMovieDataSource;

		return mSearchMovieDataSource;
	}

	public MutableLiveData<SearchMovieDataSource> getSearchMovieDataSourceMutableLiveData(){
		return mSearchMovieDataSourceMutableLiveData;
	}
/*	public MutableLiveData<MovieDataSource> getMovieDataSourceMutableLiveData(){
		return mMovieDataSourceMutableLiveData;
	}*/
}
