package com.jsfarley.slam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jsfarley.slam.databinding.ActivityMovieBinding;
import com.jsfarley.slam.model.Movie;

public class MovieActivity extends AppCompatActivity {
	private Movie mMovie;
	private ActivityMovieBinding mActivityMovieBinding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		FloatingActionButton fab = findViewById(R.id.floatingActionButton);

		mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
		Intent intent = getIntent();
		if(intent.hasExtra("movie")){
			mMovie = getIntent().getParcelableExtra("movie");
			mActivityMovieBinding.setMovie(mMovie);
		}


		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(v, "Added to Firebase", Snackbar.LENGTH_LONG).show();
			}
		});
	}
}