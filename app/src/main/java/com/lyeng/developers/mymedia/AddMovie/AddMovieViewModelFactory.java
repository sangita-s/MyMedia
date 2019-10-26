package com.lyeng.developers.mymedia.AddMovie;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lyeng.developers.mymedia.data.MediaRepository;
import com.lyeng.developers.mymedia.data.Movie;

import java.lang.reflect.InvocationTargetException;

public class AddMovieViewModelFactory implements ViewModelProvider.Factory {
    private MediaRepository mRepository;
    private Application mApplication;
    private Movie movie;

    public AddMovieViewModelFactory(Application pApplication, Movie pMovie) {
        mApplication = pApplication;
        mRepository = new MediaRepository(pApplication);
        movie = pMovie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(MediaRepository.class, Movie.class)
                    .newInstance(mRepository, movie);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
