package com.jsfarley.slam.viewmodel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jsfarley.slam.model.Movie;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {
	private Context mContext;

	public MovieAdapter(Context context){
		super(Movie.CALLBACK);
		mContext = context;

	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

	}

	public class MovieViewHolder extends RecyclerView.ViewHolder{
		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
