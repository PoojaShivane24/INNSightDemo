package com.example.innsightdemo.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getUserDetailDao() : Dao
    companion object {
        var movieDatabase: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            var tempInstance = movieDatabase
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                var instance =
                    Room.databaseBuilder(context, MovieDatabase::class.java, "userDatabase").build()
                movieDatabase = instance
                return instance
            }
        }
    }
}