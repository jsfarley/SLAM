package com.jsfarley.slam.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.jsfarley.slam.model.DataSourceFactory;
import com.jsfarley.slam.model.Movie;
import com.jsfarley.slam.model.MovieDataSource;
import com.jsfarley.slam.model.MovieRepository;
import com.jsfarley.slam.model.SearchMovieDataSource;
import com.jsfarley.slam.service.MovieDataService;
import com.jsfarley.slam.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminViewModel extends AndroidViewModel {
	private MovieRepository mMovieRepository;
	LiveData<MovieDataSource> mMovieDataSourceLiveData;
	LiveData<SearchMovieDataSource> mSearchMovieDataSourceLiveData;
	private Executor mExecutor;
	private LiveData<PagedList<Movie>> moviesPagedList;
	private LiveData<PagedList<Movie>> movieSearchedPagedList;

	public AdminViewModel(@NonNull Application application) {
		super(application);
		mMovieRepository = new MovieRepository(application);
		MovieDataService movieDataService = RetrofitInstance.getService();
		DataSourceFactory factory = new DataSourceFactory(movieDataService, application);

		//popular movies
		/*mMovieDataSourceLiveData = factory.getMovieDataSourceMutableLiveData();
		PagedList.Config config = (new PagedList.Config.Builder())
				.setEnablePlaceholders(true)
				.setInitialLoadSizeHint(10)
				.setPageSize(20)
				.setPrefetchDistance(4)
				.build();
		mExecutor = Executors.newFixedThreadPool(5);
		moviesPagedList = (new LivePagedListBuilder<Long, Movie>(factory, config))
				.setFetchExecutor(mExecutor)
				.build();*/

		//movie search
		mSearchMovieDataSourceLiveData = factory.getSearchMovieDataSourceMutableLiveData();
		PagedList.Config searchConfig = (new PagedList.Config.Builder())
									.setEnablePlaceholders(true)
									.setInitialLoadSizeHint(10)
									.setPageSize(20)
									.setPrefetchDistance(4)
									.build();
		mExecutor = Executors.newFixedThreadPool(5);
		movieSearchedPagedList = (new LivePagedListBuilder<Long, Movie>(factory, searchConfig))
							.setFetchExecutor(mExecutor)
							.build();



	}
	public LiveData<List<Movie>> getAllMovies(){
		return mMovieRepository.getListMutableLiveData();
	}
	public LiveData<PagedList<Movie>> getMoviesPagedList(){
		return moviesPagedList;
	}
	public LiveData<PagedList<Movie>> getSearchResults(){
		return movieSearchedPagedList;
	}
}
