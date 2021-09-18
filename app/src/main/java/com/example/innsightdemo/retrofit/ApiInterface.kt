package com.example.innsightdemo.retrofit

import com.example.innsightdemo.model.Movie
import com.example.innsightdemo.model.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

//    http://www.omdbapi.com/?apikey=e5311742&s=Batman&page=1

    @GET("/")
    fun getMovieList(@Query("apikey") apikey : String,
                     @Query("s")search : String,
                     @Query("page")page : Int) : Call<Movie>

    @GET("/")
    fun getMovieDetail(
        @Query("apiKey") apiKey: String,
        @Query("i") movieId: String): Call<MovieDetail>
}