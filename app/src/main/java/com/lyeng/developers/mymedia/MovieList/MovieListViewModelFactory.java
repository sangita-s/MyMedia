package com.lyeng.developers.mymedia.MovieList;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.lyeng.developers.mymedia.data.MediaRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

public class MovieListViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private MediaRepository mRepository;
    private ExecutorService mExecutorService;


    public MovieListViewModelFactory(Application pApplication) {
        mApplication = pApplication;
        mRepository = new MediaRepository(pApplication);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(MediaRepository.class)
                    .newInstance(mRepository);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
