package com.example.movie_stage1.RoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavouriteMoviesDao {

        @Insert
        Long insertTask(FavouriteMovies favouriteMovies);


        @Query("SELECT * FROM FavouriteMovies ORDER BY id desc")
        LiveData<List<FavouriteMovies>> fetchAllTasks();


        @Query("SELECT * FROM FavouriteMovies WHERE id =:taskId")
        LiveData<FavouriteMovies> getTask(int taskId);

        @Query("SELECT * FROM FavouriteMovies WHERE movie_id =:movieid")
        LiveData<FavouriteMovies> getMovie(String movieid);

    @Query("Delete from FavouriteMovies where movie_id=:movie_id")
    int getSingleRecordDelete(String movie_id);


    @Update
        void updateTask(FavouriteMovies favouriteMovies);


        @Delete
        void deleteTask(FavouriteMovies favouriteMovies);
    }

