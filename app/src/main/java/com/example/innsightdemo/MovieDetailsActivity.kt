package com.example.innsightdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.innsightdemo.constant.Config
import com.example.innsightdemo.databinding.ActivityMainBinding
import com.example.innsightdemo.databinding.ActivityMovieDetailsBinding
import com.example.innsightdemo.model.Movie
import com.example.innsightdemo.model.MovieDetail
import com.example.innsightdemo.retrofit.ApiClient
import com.example.innsightdemo.retrofit.ApiInterface
import com.example.innsightdemo.roomdatabase.MovieEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {
    private var movieDetail: MovieDetail? = null
    private var movie: MovieEntity? = null
    private val TAG = "MovieDetailsActivity"
    lateinit var movieDetailsBinding : ActivityMovieDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(movieDetailsBinding.root)


        getIncomingIntent()
        movieDetailsBinding.progressBar.visibility = View.VISIBLE

        val apiInterface = ApiClient.getInstance().create(ApiInterface::class.java)

        val call = movie?.imdbID?.let { apiInterface.getMovieDetail(Config.apiKey, it) }

        call?.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                movieDetailsBinding.progressBar.visibility = View.GONE
                movieDetailsBinding.clContainer.visibility = View.VISIBLE
                movieDetail = response.body()
                Log.e(TAG, "onResponse: "+ response.body() )
                Log.e(TAG, "onResponse: "+ call.request().url() )

                setDataOnView(movieDetail)
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                movieDetailsBinding.progressBar.visibility = View.GONE
                Log.e(TAG, "onFailure: "+t )
                Toast.makeText(this@MovieDetailsActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun setDataOnView(movieDetail: MovieDetail?) {
        Glide.with(this).load(movieDetail?.Poster).into(movieDetailsBinding.ivPoster)
        movieDetailsBinding.tvActors.text = movieDetail?.Actors
        movieDetailsBinding.tvBoxOffice.text = movieDetail?.BoxOffice
        movieDetailsBinding.tvDirector.text = movieDetail?.Director
        movieDetailsBinding.tvGenre.text = movieDetail?.Genre
        movieDetailsBinding.tvLanguage.text = movieDetail?.Language
        movieDetailsBinding.tvRuntime.text = movieDetail?.Runtime
        movieDetailsBinding.tvTitle.text = movieDetail?.Title
        movieDetailsBinding.tvWriter.text = movieDetail?.Writer
        movieDetailsBinding.tvYear.text = movieDetail?.Year
    }

    private fun getIncomingIntent() {
        if (intent != null) {
            if (intent.hasExtra("movie"))
                movie = intent.getSerializableExtra("movie") as MovieEntity
        }
    }
}