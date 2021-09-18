package com.example.innsightdemo.roomdatabase

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MovieEntity(
    @PrimaryKey var title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
) : Serializable