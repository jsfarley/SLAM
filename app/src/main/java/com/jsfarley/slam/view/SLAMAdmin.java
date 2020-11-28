package com.jsfarley.slam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.jsfarley.slam.R;
import com.jsfarley.slam.databinding.ActivitySLAMAdminBinding;
import com.jsfarley.slam.model.Movie;
import com.jsfarley.slam.viewmodel.AdminViewModel;
import com.jsfarley.slam.viewmodel.MovieAdapter;

public class SLAMAdmin extends AppCompatActivity {
	private PagedList<Movie> mMovies;
	private RecyclerView mRecyclerView;
	private MovieAdapter mMovieAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private AdminViewModel mAdminViewModel;
	private ActivitySLAMAdminBinding mActivitySLAMAdminBinding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s_l_a_m_admin);

		mActivitySLAMAdminBinding = DataBindingUtil.setContentView(this, R.layout.activity_s_l_a_m_admin);

		mAdminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

		getMovies();
		mSwipeRefreshLayout = mActivitySLAMAdminBinding.swipeRefresh;
		mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getMovies();
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

	private void showRecyclerView() {
		mRecyclerView = mActivitySLAMAdminBinding.movieList;
		mMovieAdapter = new MovieAdapter(this);
		mMovieAdapter.submitList(mMovies);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mMovieAdapter);
		mMovieAdapter.notifyDataSetChanged();
	}
}