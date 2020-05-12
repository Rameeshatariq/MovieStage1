package com.example.movie_stage1.RoomDatabase;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteMovies.class}, version = 2, exportSchema = true)
public abstract class MoviesRoomDatabase extends RoomDatabase {

    public abstract FavouriteMoviesDao daoAccess();
}