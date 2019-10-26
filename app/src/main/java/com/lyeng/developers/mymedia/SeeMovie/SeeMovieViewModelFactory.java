package com.lyeng.developers.mymedia.SeeMovie;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lyeng.developers.mymedia.data.MediaRepository;

import java.lang.reflect.InvocationTargetException;

class SeeMovieViewModelFactory implements ViewModelProvider.Factory {
    private MediaRepository mRepository;
    private Application mApplication;
    private String movId;

    public SeeMovieViewModelFactory(Application pApplication, String pMovieId) {
        mApplication = pApplication;
        mRepository = new MediaRepository(pApplication);
        movId = pMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(MediaRepository.class, String.class)
                    .newInstance(mRepository, movId);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
