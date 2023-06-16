package com.example.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final MovieAdapter mAdapter;
    private final Context mContext;
    private final String mUserId;
    private final String mUsername;

    public SwipeToDeleteCallback(Context context, MovieAdapter adapter, String userId, String username) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mContext = context;
        mUserId = userId;
        mUsername = username;
        Log.d("USERID", "class: SwipeToDeleteCallback, userId: " + mUserId);
        Log.d("USERNAME", "class: SwipeToDeleteCallback, username: "+mUsername);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getBindingAdapterPosition();
        Movie movie = mAdapter.movieList.get(position);
        if (mUserId == null) {
            Log.d("SWIPE", "class: SwipeToDeleteCallback, method: onSwiped: userId (null)" + mUserId);
            return;
        }
        mAdapter.deleteItem(position);
        if (direction == ItemTouchHelper.LEFT) {
            Log.d("Swiped", "swiped left = no");
            FirebaseDatabase.getInstance().getReference("users")
                    .child(mUserId)
                    .child(mUsername)
                    .child("movies")
                    .child(movie.getId())
                    .child("swipe")
                    .setValue("left");
            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
        } else if (direction == ItemTouchHelper.RIGHT) {
            Log.d("Swiped", "swiped right = yes");
            FirebaseDatabase.getInstance().getReference("users")
                    .child(mUserId)
                    .child(mUsername)
                    .child("movies")
                    .child(movie.getId())
                    .child("swipe")
                    .setValue("right");
            FirebaseDatabase.getInstance().getReference("users")
                    .child(mUserId)
                    .child(mUsername)
                    .child("movies")
                    .child(movie.getId())
                    .child("title")
                    .setValue(movie.getTitle());
            FirebaseDatabase.getInstance().getReference("users")
                    .child(mUserId)
                    .child(mUsername)
                    .child("movies")
                    .child(movie.getId())
                    .child("poster_path")
                    .setValue(movie.getPosterPath());
            Toast.makeText(mContext, "Added", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }
}
