package com.lyeng.developers.mymedia.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class MediaRepository {
    private MediaDao mMediaDao;
    //    private final ExecutorService mIoExecutor;
    LiveData<List<Movie>> allMovies;

    private MutableLiveData<Movie> movie = new MutableLiveData<>();

    private void asyncFinished(Movie pMovie) {
        movie.setValue(pMovie);
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    private MutableLiveData<String> maxMovieId = new MutableLiveData<>();

    private void asyncFinishedMovieId(String movId) {
        maxMovieId.setValue(movId);
    }

    public MutableLiveData<String> getMaxMovId() {
        return maxMovieId;
    }

    //Constructor - With passed ExecutorService
//    public MediaRepository(Application app, ExecutorService pIoExecutor) {
//        mIoExecutor = pIoExecutor;
//        MediaDatabase mediaDb = MediaDatabase.getInstance(app);
//        mMediaDao = mediaDb.mediaDao();
//    }

    public MediaRepository(Application app) {
        MediaDatabase mediaDb = MediaDatabase.getInstance(app);
        mMediaDao = mediaDb.mediaDao();
    }

    //Get movie List
//    public LiveData<PagedList<Movie>> getAllMovies() {
//        allMovies = mMediaDao.getAllMovies(
//                new SimpleSQLiteQuery("SELECT * FROM movie_table ORDER BY movieId"));
//        return allMovies;
//    }

    public LiveData<List<Movie>> getAllMovies() {
        return mMediaDao.getAllMovies(
                new SimpleSQLiteQuery("SELECT * FROM movie_table ORDER BY movieId"));
    }

    //Get movie count
    public LiveData<Integer> getMovieCount() {
        return mMediaDao.getMovieCount(new SimpleSQLiteQuery("SELECT COUNT(*) FROM movie_table"));
    }

//    @Nullable
//    public Movie getMovie(String movieId) {
//        try {
//            return mIoExecutor.submit(mMediaDao::getMovie).get(movieId);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //Load movie from async task to Mutable Live Data
    public void runGetMovie(String movieId) {
        getMovieAsyncTask task = new getMovieAsyncTask(mMediaDao);
        task.delegate = this;
        task.execute(movieId);
    }

    private static class getMovieAsyncTask extends AsyncTask<String, Void, Movie> {
        private MediaDao mMediaDao;
        private MediaRepository delegate = null;

        private getMovieAsyncTask(MediaDao pMediaDao) {
            this.mMediaDao = pMediaDao;
        }

        @Override
        protected Movie doInBackground(final String... movieId) {
            return mMediaDao.getMovie(movieId[0]);
        }

        @Override
        protected void onPostExecute(Movie pMovie) {
            delegate.asyncFinished(pMovie);
        }
    }

    //Insert movie with executor janaku async service
    public void insertTheMovie(Movie pMovie) {
        new InsertMovieAsyncTask(mMediaDao).execute(pMovie);
//        mIoExecutor.execute(() -> mMediaDao.insertMovie(pMovie));
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MediaDao mMediaDao;

        private InsertMovieAsyncTask(MediaDao pMediaDao) {
            this.mMediaDao = pMediaDao;
        }

        @Override
        protected Void doInBackground(Movie... pMovies) {
            mMediaDao.insertMovie(pMovies[0]);
            return null;
        }
    }

    //Update movie with async
    public void updateTheMovie(Movie pM) {
        new UpdateMovieAsyncTask(mMediaDao).execute(pM);
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MediaDao mMediaDao;

        private UpdateMovieAsyncTask(MediaDao pMediaDao) {
            this.mMediaDao = pMediaDao;
        }

        @Override
        protected Void doInBackground(Movie... pMovies) {
            mMediaDao.updateMovie(pMovies[0]);
            return null;
        }
    }

    //Load movie id from async task to Mutable Live Data
    public void runGetMovieId() {
        getMovieIdAsyncTask task = new getMovieIdAsyncTask(mMediaDao);
        task.delegate = this;
        task.execute();
    }

    private static class getMovieIdAsyncTask extends AsyncTask<Void, Void, String> {
        private MediaDao mMediaDao;
        private MediaRepository delegate = null;

        private getMovieIdAsyncTask(MediaDao pMediaDao) {
            this.mMediaDao = pMediaDao;
        }

        @Override
        protected String doInBackground(Void... pVoids) {
            return mMediaDao.getMaxMovId();
        }

        @Override
        protected void onPostExecute(String pMovie) {
            delegate.asyncFinishedMovieId(pMovie);
        }
    }

    //Insert movie with executor janaku async service
    public void deleteTheMovie(Movie pMovie) {
        new DeleteMovieAsyncTask(mMediaDao).execute(pMovie);
//        mIoExecutor.execute(() -> mMediaDao.insertMovie(pMovie));
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MediaDao mMediaDao;

        private DeleteMovieAsyncTask(MediaDao pMediaDao) {
            this.mMediaDao = pMediaDao;
        }

        @Override
        protected Void doInBackground(Movie... pMovies) {
            mMediaDao.deleteMovie(pMovies[0]);
            return null;
        }
    }
}
