package com.jsfarley.slam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jsfarley.slam.databinding.ActivityMovieBinding;
import com.jsfarley.slam.databinding.MovieContentBinding;
import com.jsfarley.slam.model.Movie;

public class MovieActivity extends AppCompatActivity {
	private Movie mMovie;
	private ActivityMovieBinding mActivityMovieBinding;
	private MovieContentBinding mMovieContentBinding;
	//Firebase
	FirebaseDatabase mFirebaseDatabase;
	DatabaseReference mDatabaseReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);

		mDatabaseReference = FirebaseDatabase.getInstance().getReference();

		mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

		Intent intent = getIntent();
		if(intent.hasExtra("movie")){
			mMovie = getIntent().getParcelableExtra("movie");
			mActivityMovieBinding.setMovie(mMovie);
		}


	}

	//Todo: add content to firebase including the link. create intent to navigate back to search results screen
	public void addToFirebase(View view){
		sendToFirebase();

		Toast.makeText(this,"Sent", Toast.LENGTH_SHORT).show();
	}

	public void sendToFirebase(){
		String mTitle = mMovie.getTitle();
		String mOverView = mMovie.getOverview();
		String mMovieLink = mMovie.getMovieLink();
		String mMovieImage = mMovie.getPosterPath();

		Movie movies = new Movie(mTitle, mOverView, mMovieLink, mMovieImage);

		mDatabaseReference.child("slam/mtv_item").push().setValue(movies);
	}

}