package com.lyeng.developers.mymedia.SeeMovie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lyeng.developers.mymedia.AddMovie.EditMovieActivity;
import com.lyeng.developers.mymedia.R;
import com.lyeng.developers.mymedia.data.Movie;

import static com.lyeng.developers.mymedia.data.ConversionHelper.intToBoolArrayConv5Star;

public class SeeMovieActivity extends AppCompatActivity {

    public static final int EDIT_REQ_CODE = 1;
    SeeMovieViewModel mSeeMovieViewModel;

    TextView tv_movie_medium, tv_movie_size, tv_movie_language, tv_movie_genre, tv_movie_runtime,
            tv_movie_quality;
    MovieRatingView movieRating;
    CollapsingToolbarLayout toolbar_layout;

    Movie m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_movie_medium = findViewById(R.id.tv_movie_medium);
        tv_movie_size = findViewById(R.id.tv_movie_size);
        tv_movie_language = findViewById(R.id.tv_movie_language);
        tv_movie_genre = findViewById(R.id.tv_movie_genre);
        tv_movie_runtime = findViewById(R.id.tv_movie_runtime);
        tv_movie_quality = findViewById(R.id.tv_movie_quality);
        movieRating = findViewById(R.id.movieRatingBar);
        toolbar_layout = findViewById(R.id.toolbar_layout);

        Intent i = getIntent();
        String movieId = i.getStringExtra("MOVIE_ID");

        mSeeMovieViewModel = ViewModelProviders
                .of(this, new SeeMovieViewModelFactory(this.getApplication(), movieId))
                .get(SeeMovieViewModel.class);
        mSeeMovieViewModel.getMovie().observe(this,
                new Observer<Movie>() {
                    @Override
                    public void onChanged(@Nullable Movie movie) {
                        m = movie;
                        toolbar_layout.setTitle(movie.getMovieName());
                        tv_movie_medium.setText(movie.getMovieMedium());
                        tv_movie_size.setText(movie.getMovieSize());
                        tv_movie_language.setText(movie.getMovieLanguage());
                        tv_movie_genre.setText(movie.getMovieGenre());
                        tv_movie_runtime.setText(movie.getMovieRuntime() + " minutes");
                        tv_movie_quality.setText(movie.getMovieQuality());
                        movieRating.setMovieRating(intToBoolArrayConv5Star(movie.getMovieRating()));
                        Log.i("AddMovieActivitySEE0",String.valueOf(intToBoolArrayConv5Star(movie.getMovieRating())[0]));
                        Log.i("AddMovieActivitySEE4",String.valueOf(intToBoolArrayConv5Star(movie.getMovieRating())[4]));
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_movie_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeMovieActivity.this, EditMovieActivity.class);
                //(new Movie("MV0001", "Inception", true,
                //                    ("2010-10-10"), "Own", "Thriller,Suspense", "900MB",
                //                    "English","720P", 5, 120));
                i.putExtra("EXTRA_MOVIE_ID", m.getMovieId());
                i.putExtra("EXTRA_MOVIE_NAME", m.getMovieName());
                i.putExtra("EXTRA_MOVIE_SEEN_STATUS", m.getMovieSeenStatus());
                i.putExtra("EXTRA_MOVIE_SEEN_DATE", m.getMovieSeenDate());
                i.putExtra("EXTRA_MOVIE_SEEN_MEDIUM", m.getMovieMedium());
                i.putExtra("EXTRA_MOVIE_SEEN_GENRE", m.getMovieGenre());
                i.putExtra("EXTRA_MOVIE_SEEN_SIZE", m.getMovieGenre());
                i.putExtra("EXTRA_MOVIE_SEEN_LANGUAGE", m.getMovieLanguage());
                i.putExtra("EXTRA_MOVIE_SEEN_QUALITY",m.getMovieQuality() );
                i.putExtra("EXTRA_MOVIE_SEEN_RATING", m.getMovieRating());
                i.putExtra("EXTRA_MOVIE_SEEN_RUNTIME", m.getMovieRuntime());
                startActivityForResult(i, EDIT_REQ_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
