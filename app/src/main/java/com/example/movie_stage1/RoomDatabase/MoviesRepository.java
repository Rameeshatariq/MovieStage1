package com.example.movie_stage1.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class MoviesRepository {

    private String DB_NAME = "movies_db";

    private MoviesRoomDatabase noteDatabase;
    public MoviesRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, MoviesRoomDatabase.class, DB_NAME).build();
    }


    public void insertTask(String movie_id,
                           String movie_name, String image, String released, String rating, String synopsis) {

        FavouriteMovies note = new FavouriteMovies();
        note.setMovie_id(movie_id);
        note.setMovie_name(movie_name);
        note.setImage(image);
        note.setRating(rating);
        note.setSynopsis(synopsis);
        note.setReleased_date(released);

        insertTask(note);
    }

    public void insertTask(final FavouriteMovies note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().insertTask(note);
                return null;
            }
        }.execute();
    }

/*
    public void updateTask(final FavouriteMovies note) {
        note.setModifiedAt(AppUtils.getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().updateTask(note);
                return null;
            }
        }.execute();
    }
*/

    public void deleteTask(final int id) {
        final LiveData<FavouriteMovies> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final FavouriteMovies note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteTask(note);
                return null;
            }
        }.execute();
    }

    public LiveData<FavouriteMovies> getTask(int id) {
        return noteDatabase.daoAccess().getTask(id);
    }

    public int delete(String movie_id){
        return noteDatabase.daoAccess().getSingleRecordDelete(movie_id);
    }


    public LiveData<FavouriteMovies> getMovie(String movieid) {
        return noteDatabase.daoAccess().getMovie(movieid);
    }

    public LiveData<List<FavouriteMovies>> getTasks() {
        return noteDatabase.daoAccess().fetchAllTasks();
    }
}

