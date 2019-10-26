package com.lyeng.developers.mymedia.MovieList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.lyeng.developers.mymedia.AddMovie.AddMovieActivity;
import com.lyeng.developers.mymedia.R;
import com.lyeng.developers.mymedia.SeeMovie.SeeMovieActivity;
import com.lyeng.developers.mymedia.data.Movie;
import com.lyeng.developers.mymedia.paging.MovieAdapter;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    MovieAdapter adapterMovie;
    MovieListViewModel mMovieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvMovie = findViewById(R.id.rv_movieList);
        rvMovie.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));
        rvMovie.setHasFixedSize(true);
        adapterMovie = new MovieAdapter();
        rvMovie.setAdapter(adapterMovie);

        mMovieListViewModel = ViewModelProviders
                .of(this, new MovieListViewModelFactory(this.getApplication()))
                .get(MovieListViewModel.class);
        mMovieListViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> pMovies) {
                adapterMovie.setMovielist(pMovies);
            }
        });

        adapterMovie.setOnItemMovieClickListener(new MovieAdapter.OnItemMovieClickListener() {
            @Override
            public void onMovieItemClick(Movie movie) {
                Intent i = new Intent(MovieListActivity.this, SeeMovieActivity.class);
                i.putExtra("MOVIE_ID", movie.getMovieId());
                startActivity(i);
            }
        });

        FloatingActionButton fab = findViewById(R.id.edit_movie_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddMovieActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //For slide deletion - start
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView pRecyclerView, @NonNull RecyclerView.ViewHolder
                    pViewHolder, @NonNull RecyclerView.ViewHolder pViewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder pViewHolder, int pI) {
                mMovieListViewModel.deleteMovie(adapterMovie.getMovieAt(pViewHolder.getAdapterPosition()));
                Toast.makeText(MovieListActivity.this, "Movie deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(rvMovie);
        //For slide deletion - end
    }

}
