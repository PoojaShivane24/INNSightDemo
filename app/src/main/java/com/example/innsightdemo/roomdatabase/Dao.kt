package com.example.innsightdemo.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.innsightdemo.roomdatabase.MovieEntity

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: MovieEntity) : Long

//    @Query("Select * from UserEntity where userName = :userName AND password = :password")
//    fun getUser(userName : String, password : String) : UserEntity?

    @Query("Select * from MovieEntity")
    fun getMovieList() : List<MovieEntity?>
}