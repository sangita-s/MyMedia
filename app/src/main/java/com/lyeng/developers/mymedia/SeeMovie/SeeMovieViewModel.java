package com.lyeng.developers.mymedia.SeeMovie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lyeng.developers.mymedia.data.MediaRepository;
import com.lyeng.developers.mymedia.data.Movie;

public class SeeMovieViewModel extends ViewModel {
    private MediaRepository repo;
    MutableLiveData<Movie> movie;

    public SeeMovieViewModel(final MediaRepository pMediaRepository, String movId) {
        repo = pMediaRepository;
        repo.runGetMovie(movId);
    }

//    public Movie getMovie(){
////        repo.runGetMovie(movId);
//        movie = repo.getMovie();
//        return movie.getValue();
//    }

    public MutableLiveData<Movie> getMovie(){
        return repo.getMovie();
    }
}
