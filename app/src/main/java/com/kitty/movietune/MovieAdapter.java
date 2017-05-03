package com.kitty.movietune;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohita on 4/30/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<MovieList> movieResults;
    private LayoutInflater mInflater;
    private Context mContext;

    private boolean isLoadingAdded = false;

    public MovieAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        movieResults = new ArrayList<>();
    }

    public List<MovieList> getMovies() {
        return movieResults;
    }

    public void setMovies(List<MovieList> movieResults) {
        this.movieResults = movieResults;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieResults.get(position));
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }
    // Picasso for loading Poster
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieList movie = movieResults.get(position);
        Picasso.with(mContext).load(movie.getPoster()).placeholder(R.color.colorAccent).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return (movieResults == null) ? 0 : movieResults.size();
    }
    public void setMovieList(List<MovieList> movieList) {
        this.movieResults = new ArrayList<>();
        this.movieResults.addAll(movieList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(MovieList r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<MovieList> moveResults) {
        for (MovieList result : moveResults) {
            add(result);
        }
    }

    public void remove(MovieList r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MovieList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        MovieList result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MovieList getItem(int position) {
        return movieResults.get(position);
    }


}

