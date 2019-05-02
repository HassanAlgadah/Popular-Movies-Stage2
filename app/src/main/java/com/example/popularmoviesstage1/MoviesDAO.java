package com.example.popularmoviesstage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface MoviesDAO {
    @Insert
    public void insertMovie(Movie movie);

    @Query("DELETE FROM Movie WHERE image = :id")
    void deleteMovie(String id);

    @Query("Select * from Movie")
    public List<Movie> getAllMovies();

    @Query("SELECT * FROM Movie WHERE image = :id")
    public Movie getSingleMovie (String id);
}
