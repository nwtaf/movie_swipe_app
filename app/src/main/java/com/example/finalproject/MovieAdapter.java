package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public ArrayList<Movie> movieList;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView overviewTextView;
        private TextView releaseDateTextView;

        //constructor
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.image_background);
            titleTextView = itemView.findViewById(R.id.text_title);
            overviewTextView = itemView.findViewById(R.id.text_description);
            releaseDateTextView = itemView.findViewById(R.id.text_release_date);
        }
    }

    //pass array list of movie objects to adapter
    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    //pass layout of cards to adapter
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MovieViewHolder mvh = new MovieViewHolder(view);
        return mvh;
    }

    //gets info and passes it to views
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Log.d("ADAPTER", "class: MovieAdapter, method: onBindViewHolder");
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.overviewTextView.setText(movie.getOverview());
        holder.releaseDateTextView.setText(movie.getReleaseDate());
        // Load the image using Glide
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void deleteItem(int position) {
        movieList.remove(position);
        notifyItemRemoved(position);
    }
}
