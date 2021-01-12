package com.jsfarley.slam.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jsfarley.slam.MovieActivity;
import com.jsfarley.slam.R;
import com.jsfarley.slam.databinding.MovieListItemBinding;
import com.jsfarley.slam.model.Movie;
import com.jsfarley.slam.model.MovieDBResponse;

import java.util.List;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {
	private Context mContext;
	private MovieDBResponse mMovieDBResponse;
	public MovieAdapter(Context context){
		super(Movie.CALLBACK);
		mContext = context;

	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
													,R.layout.movie_list_item,parent,false);
		return new MovieViewHolder(movieListItemBinding);

	}

	@Override
	public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
		Movie movie = getItem(position);
		holder.mMovieListItemBinding.setMovie(movie);
	}



	public class MovieViewHolder extends RecyclerView.ViewHolder{

		private MovieListItemBinding mMovieListItemBinding;
		public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
			super(movieListItemBinding.getRoot());
			this.mMovieListItemBinding = movieListItemBinding;

			movieListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = getAdapterPosition();
					if(position != RecyclerView.NO_POSITION){
						Movie selectedMovie = getItem(position);
						Intent intent = new Intent(mContext, MovieActivity.class);
						intent.putExtra("movie", selectedMovie);
						mContext.startActivity(intent);
					}
				}
			});
		}
	}
}
