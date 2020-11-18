package com.jsfarley.slam.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
	private static Retrofit sRetrofit = null;
	private static String BASE_URL = "https://api.themoviedb.org/3/";

	public static MovieDataService getService(){
		if(sRetrofit == null){
			sRetrofit = new Retrofit.Builder()
									.baseUrl(BASE_URL)
									.addConverterFactory(GsonConverterFactory.create())
									.build();
		}
		return sRetrofit.create(MovieDataService.class);
	}
}
