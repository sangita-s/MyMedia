package com.lyeng.developers.mymedia.paging;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyeng.developers.mymedia.R;
import com.lyeng.developers.mymedia.data.Movie;
import com.lyeng.developers.mymedia.data.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> listMovies = new ArrayList<>();

    private OnItemMovieClickListener listener;
    public interface OnItemMovieClickListener {
        void onMovieItemClick(Movie movie);
    }
    public void setOnItemMovieClickListener(OnItemMovieClickListener listener) {
        this.listener = listener;
    }
    public Movie getMovieAt(int pos) {
        return listMovies.get(pos);
    }
    public void setMovielist(List<Movie> pListMovies) {
        this.listMovies = pListMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
        View itemView = LayoutInflater.from(pViewGroup.getContext())
                .inflate(R.layout.movie_list_item, pViewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder pMovieViewHolder, int pI) {
        Movie selectedMovie = listMovies.get(pI);

        pMovieViewHolder.mMovieName.setText(selectedMovie.getMovieName());
        pMovieViewHolder.mMovieGenre.setText(selectedMovie.getMovieGenre());
        pMovieViewHolder.mMovieRuntime.setText(selectedMovie.getMovieRuntime() + " minutes");
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView mMovieName;
        public TextView mMovieGenre;
        public TextView mMovieRuntime;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieName = itemView.findViewById(R.id.movieName);
            mMovieGenre = itemView.findViewById(R.id.movieGenre);
            mMovieRuntime = itemView.findViewById(R.id.movieRuntime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION)
                        listener.onMovieItemClick(listMovies.get(pos));
                }
            });
        }
    }
}
