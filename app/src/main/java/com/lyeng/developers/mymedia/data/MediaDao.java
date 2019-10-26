package com.lyeng.developers.mymedia.data;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie m);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateMovie(Movie pMovie);

    @Delete
    void deleteMovie(Movie m);

    @RawQuery(observedEntities = Movie.class)
    LiveData<List<Movie>> getAllMovies(SimpleSQLiteQuery query);

    @RawQuery(observedEntities = Movie.class)
    LiveData<Integer> getMovieCount(SimpleSQLiteQuery query);

    @Query("SELECT * FROM movie_table WHERE movieId=:movieID LIMIT 1")
    Movie getMovie(String movieID);

    @Query("SELECT movieId FROM MOVIE_TABLE ORDER BY movieId desc LIMIT 1")
    String getMaxMovId();


}
