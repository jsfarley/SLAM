package com.jsfarley.slam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsfarley.slam.R;
import com.jsfarley.slam.databinding.ActivitySLAMAdminBinding;
import com.jsfarley.slam.model.Movie;
import com.jsfarley.slam.model.MovieDBResponse;
import com.jsfarley.slam.model.MovieRepository;
import com.jsfarley.slam.model.SearchMovieDataSource;
import com.jsfarley.slam.viewmodel.AdminViewModel;
import com.jsfarley.slam.viewmodel.MovieAdapter;

import retrofit2.Call;

public class SLAMAdmin extends AppCompatActivity {
	private PagedList<Movie> mMovies;
	private PagedList<Movie> mMovieSearches;
	private RecyclerView mRecyclerView;
	private MovieAdapter mMovieAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private AdminViewModel mAdminViewModel;
	private ActivitySLAMAdminBinding mActivitySLAMAdminBinding;

	private Movie mMovie;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s_l_a_m_admin);

		mActivitySLAMAdminBinding = DataBindingUtil.setContentView(this, R.layout.activity_s_l_a_m_admin);




		mAdminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

		//getMovies();
		searchView();
		getSearches();
		mSwipeRefreshLayout = mActivitySLAMAdminBinding.swipeRefresh;
		mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				//getMovies();
				//getSearches();
				searchView();
				mSwipeRefreshLayout.setRefreshing(false);
			}
		});

	}

	private void getMovies() {
		mAdminViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>() {
			@Override
			public void onChanged(PagedList<Movie> moviesFromLiveData) {
				mMovies = moviesFromLiveData;
				showRecyclerView();
			}
		});
	}
	private void getSearches(){
		mAdminViewModel.getSearchResults().observe(this, new Observer<PagedList<Movie>>() {
			@Override
			public void onChanged(PagedList<Movie> movieSearches) {
				mMovieSearches = movieSearches;
				searchRecyclerView();
			}
		});
	}


	private void searchView(){


		mActivitySLAMAdminBinding.searchView.setActivated(true);
		mActivitySLAMAdminBinding.searchView.setQueryHint("Search For Movies");
		mActivitySLAMAdminBinding.searchView.onActionViewExpanded();
		mActivitySLAMAdminBinding.searchView.setIconified(false);
		mActivitySLAMAdminBinding.searchView.clearFocus();
		//mActivitySLAMAdminBinding.searchView.setQuery(query,true);

		mActivitySLAMAdminBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				//mMovieAdapter.submitList(mMovieSearches);
				//mMovie.setSearch(query);
				mActivitySLAMAdminBinding.setMovieSearch(mMovie);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				//mMovieAdapter.submitList(newText);
				return false;
			}
		});
	}
	private void showRecyclerView() {
		mRecyclerView = mActivitySLAMAdminBinding.movieList;
		mMovieAdapter = new MovieAdapter(this);
		mMovieAdapter.submitList(mMovies);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mMovieAdapter);
		mMovieAdapter.notifyDataSetChanged();
	}

	private void searchRecyclerView(){
		mRecyclerView = mActivitySLAMAdminBinding.movieList;
		mMovieAdapter = new MovieAdapter(this);
		mMovieAdapter.submitList(mMovieSearches);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mMovieAdapter);
		mMovieAdapter.notifyDataSetChanged();
	}

}

