package com.example.innsightdemo.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.innsightdemo.model.Movie
import com.example.innsightdemo.model.Search
import com.example.innsightdemo.retrofit.ApiClient
import com.example.innsightdemo.retrofit.ApiInterface
import com.example.innsightdemo.roomdatabase.MovieDatabase
import com.example.innsightdemo.roomdatabase.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    var count : Long = 0L
    private val TAG = "Repository"
    var coroutineScope = CoroutineScope(SupervisorJob())
    var list : List<MovieEntity?> = ArrayList()
    var movieListLiveData : MutableLiveData<List<MovieEntity?>> = MutableLiveData()


    fun storeData(context: Context, searchList: List<Search>?) {
        storeMovieData(context, searchList)
    }

    private fun storeMovieData(context: Context, searchList: List<Search>?) {
        Log.e(TAG, "storeData: size "+searchList?.size )
        if (searchList != null) {
            for (search in searchList) {
                coroutineScope.launch {
                    val entity = MovieEntity (
                        search.Title,
                        search.Year,
                        search.imdbID,
                        search.Type,
                        search.Poster
                    )
                    count = MovieDatabase.getInstance(context).getUserDetailDao().insert(entity)
                    if (count > 0L) {
                        Log.e(TAG, "storeData: successfully ",)
                        getMovieList(context)
                    } else {
                        Log.e(TAG, "storeData: already present ",)
                    }
                }
            }
        }
    }

    fun getMovieList(context: Context) {
        coroutineScope.launch {
            list = MovieDatabase.getInstance(context).getUserDetailDao().getMovieList()
            Log.e(TAG, "getMovieList: " + list)

            movieListLiveData.postValue(list)
        }
    }
}