package com.lyeng.developers.mymedia.AddMovie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;

import com.lyeng.developers.mymedia.data.MediaRepository;
import com.lyeng.developers.mymedia.data.Movie;


public class AddMovieViewModel extends ViewModel {
    private MediaRepository repo;
    private Movie m;

    public AddMovieViewModel(final MediaRepository pRepo, Movie pMovie) {
        repo = pRepo;
        m = pMovie;
        repo.runGetMovieId();
    }

    public void addMovieToDB(Movie m){
        repo.insertTheMovie(m);
    }

    public MutableLiveData<String> getMovieIs(){return repo.getMaxMovId();}

    public void updateMovieToDB(Movie pM) {
        repo.updateTheMovie(pM);
    }
}
