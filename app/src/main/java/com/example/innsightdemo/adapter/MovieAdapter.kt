package com.example.innsightdemo.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.innsightdemo.MovieDetailsActivity
import com.example.innsightdemo.R
import com.example.innsightdemo.databinding.MovieItemBinding
import com.example.innsightdemo.roomdatabase.MovieEntity
import java.io.Serializable

class MovieAdapter(context: Context, movieList: List<MovieEntity?>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Serializable {

    private var movieList : MutableList<MovieEntity?> = ArrayList()
    private var context : Context
    init {
        this.movieList.addAll(movieList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieItemBinding : MovieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item, parent, false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.view.tvTitle.text = movie?.title
        holder.view.tvYear.text = movie?.year
        Glide.with(context).load(movie?.poster).into(holder.view.ivPoster)

        holder.view.root.setOnClickListener {
            Log.e("TAG", "onBindViewHolder: "+movieList )
            Log.e("TAG", "onBindViewHolder: "+movie )

            var intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra("movie", movie)
            }
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateList(list: List<MovieEntity?>) {
        this.movieList.addAll(0, list)
        notifyItemRangeChanged(0, list.size)
    }

    class MovieViewHolder(val view: MovieItemBinding) : RecyclerView.ViewHolder(view.root)
}