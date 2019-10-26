package com.lyeng.developers.mymedia.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.lyeng.developers.mymedia.data.DateHelper.stringToDate;

@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class MediaDatabase extends RoomDatabase {
    public abstract MediaDao mediaDao();

    private static MediaDatabase sInstance = null;

    public static synchronized MediaDatabase getInstance(final Context pContext) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(pContext.getApplicationContext(),
                    MediaDatabase.class, "media_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return sInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new LoadMovieAsyncTask(sInstance).execute();
        }
    };

    private static class LoadMovieAsyncTask extends AsyncTask<Void, Void, Void> {
        private MediaDao mediaDao;

        public LoadMovieAsyncTask(MediaDatabase db) {
            mediaDao = db.mediaDao();
        }

        @Override
        protected Void doInBackground(Void... pVoids) {
            //tring[] pMovieGenre, String pMovieSize, String pMovieLanguage, String pMovieQuality, int pMovieRating
            mediaDao.insertMovie(new Movie("MV0001", "Inception", true,
                    ("2010-10-10"), "Own", "Thriller,Suspense", "900MB",
                    "English","720P", 5, 120));
            mediaDao.insertMovie(new Movie("MV0002", "Source Code", true,
                    ("2011-10-10"), "Netflix", "Train,Suspense", "700MB",
                    "English","720P", 3, 130));
            mediaDao.insertMovie(new Movie("MV0003", "Lion", true,
                    ("2019-05-10"), "Amazon Prime Video", "Sentiment, True Story", "800MB",
                    "English","1080P", 4, 140));
            return null;
        }
    }
}
