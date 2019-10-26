package com.lyeng.developers.mymedia.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "movie_table")
public class Movie {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    private String movieId;
    @NonNull
    private String movieName;
    @NonNull
    private Boolean movieSeenStatus;
    private String movieSeenDate;
    private String movieMedium;
    private String movieGenre;
    private String movieSize;
    private String movieLanguage;
    private String movieQuality;
    private int movieRating;
    private int movieRuntime;

//    public Movie(@NonNull String pMovieId, @NonNull String pMovieName, Boolean pMovieStatus) {
//        movieId = pMovieId;
//        movieName = pMovieName;
//        movieSeenStatus = pMovieStatus;
//        movieSeenDate = Calendar.getInstance().getTime();
//        movieMedium = "unspecified";
//        movieGenre = new String[]{"unspecified"};
//        movieSize = "unspecified";
//        movieLanguage = "unspecified";
//        movieQuality = "unspecified";
//        movieRating = 0;
//    }

    public Movie() {
    }

    public Movie(@NonNull String pMovieId, @NonNull String pMovieName, Boolean pMovieSeenStatus,
                 String pMovieSeenDate, String pMovieMedium, String pMovieGenre,
                 String pMovieSize, String pMovieLanguage, String pMovieQuality, int pMovieRating,
                 int pMovieRuntime) {
        movieId = pMovieId;
        movieName = pMovieName;
        movieSeenStatus = pMovieSeenStatus;
        movieSeenDate = (pMovieSeenDate != null) ? pMovieSeenDate : "2000-01-01";
        movieMedium = (pMovieMedium != null) ? pMovieMedium : "unspecified";
        movieGenre = (pMovieGenre != null) ? pMovieGenre : "unspecified";
        movieSize = (pMovieSize != null) ? pMovieSize : "unspecified";
        movieLanguage = (pMovieLanguage != null) ? pMovieLanguage : "unspecified";
        movieQuality = (pMovieQuality != null) ? pMovieQuality : "unspecified";
        movieRating = pMovieRating;
        movieRuntime = pMovieRuntime;
    }

    //Getters
    @NonNull
    public String getMovieId() {
        return movieId;
    }

    @NonNull
    public String getMovieName() {
        return movieName;
    }

    public Boolean getMovieSeenStatus() {
        return movieSeenStatus;
    }

    public String getMovieSeenDate() {
        return movieSeenDate;
    }

    public String getMovieMedium() {
        return movieMedium;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public String getMovieSize() {
        return movieSize;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public String getMovieQuality() {
        return movieQuality;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    //Setters

    public void setMovieId(@NonNull String pMovieId) {
        movieId = pMovieId;
    }

    public void setMovieName(@NonNull String pMovieName) {
        movieName = pMovieName;
    }

    public void setMovieSeenStatus(@NonNull Boolean pMovieSeenStatus) {
        movieSeenStatus = pMovieSeenStatus;
    }

    public void setMovieSeenDate(String pMovieSeenDate) {
        movieSeenDate = pMovieSeenDate;
    }

    public void setMovieMedium(String pMovieMedium) {
        movieMedium = pMovieMedium;
    }

    public void setMovieGenre(String pMovieGenre) {
        movieGenre = pMovieGenre;
    }

    public void setMovieSize(String pMovieSize) {
        movieSize = pMovieSize;
    }

    public void setMovieLanguage(String pMovieLanguage) {
        movieLanguage = pMovieLanguage;
    }

    public void setMovieQuality(String pMovieQuality) {
        movieQuality = pMovieQuality;
    }

    public void setMovieRating(int pMovieRating) {
        movieRating = pMovieRating;
    }

    public void setMovieRuntime(int pMovieRuntime) {
        movieRuntime = pMovieRuntime;
    }
}
