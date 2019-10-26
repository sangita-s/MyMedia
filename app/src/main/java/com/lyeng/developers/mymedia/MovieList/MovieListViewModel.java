package com.lyeng.developers.mymedia.MovieList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.lyeng.developers.mymedia.data.MediaRepository;
import com.lyeng.developers.mymedia.data.Movie;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MediaRepository repo;
    private LiveData<List<Movie>> allMovies;
    private LiveData<Integer> allMovieCount;

    public MovieListViewModel(final MediaRepository pMediaRepository) {
        repo = pMediaRepository;
        allMovies = repo.getAllMovies();
        allMovieCount = repo.getMovieCount();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public LiveData<Integer> getAllMovieCount() {
        return allMovieCount;
    }

    public void deleteMovie(Movie m){
        repo.deleteTheMovie(m);
    }
}
