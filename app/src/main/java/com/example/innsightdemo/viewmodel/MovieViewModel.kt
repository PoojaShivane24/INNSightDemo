package com.example.innsightdemo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innsightdemo.model.Search
import com.example.innsightdemo.repository.Repository
import com.example.innsightdemo.roomdatabase.MovieEntity

class MovieViewModel : ViewModel() {
    var repo = Repository()
    var movieListLiveData : MutableLiveData<List<MovieEntity?>> = MutableLiveData()

    init {
        movieListLiveData = repo.movieListLiveData
    }


    fun storeData(context : Context, searchList: List<Search>?) {
        repo.storeData(context, searchList)
    }

    fun getMovieList(context: Context) {
        repo.getMovieList(context)
    }

}