package com.example.innsightdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innsightdemo.adapter.MovieAdapter
import com.example.innsightdemo.constant.Config
import com.example.innsightdemo.databinding.ActivityMainBinding
import com.example.innsightdemo.model.Movie
import com.example.innsightdemo.model.Search
import com.example.innsightdemo.retrofit.ApiClient
import com.example.innsightdemo.retrofit.ApiInterface
import com.example.innsightdemo.viewmodel.MovieViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var movie : Movie? = null
    var searchList : List<Search>? = ArrayList()
    private val TAG = "MainActivity"
    lateinit var viewModel : MovieViewModel
    var movieAdapter : MovieAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        getMovieList(this)

        mainBinding.rvMovieList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val apiInterface = ApiClient.getInstance().create(ApiInterface::class.java)

        val call = apiInterface.getMovieList(Config.apiKey, Config.search, 1)

        call.enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movie = response.body()
                Log.e(TAG, "onResponse: "+ response.body() )
                Log.e(TAG, "onResponse: "+ call.request().url() )
                searchList = movie?.Search
                Log.e(TAG, "onResponse: "+searchList.toString() )
                storeData(this@MainActivity, searchList)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG, "onFailure: "+t )
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.movieListLiveData.observe(this) {
            Log.e(TAG, "onCreate: "+ it.toString())
            if (movieAdapter == null) {
                movieAdapter = MovieAdapter(this, it)
                mainBinding.rvMovieList.adapter = movieAdapter
            } else {
                movieAdapter?.updateList(it)
            }
        }

    }

    private fun storeData(context: Context, searchList: List<Search>?) {
        viewModel.storeData(context, searchList)
    }

    private fun getMovieList(context: Context) {
        viewModel.getMovieList(context)
    }


}