package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;

public class Search extends AppCompatActivity implements API.ApiCallback {

    private API api;
    private RecyclerView myRecyclerView;
    private MovieAdapter myAdapter;
    private String searchQuery;
    private String userId;
    private RecyclerView.LayoutManager myLayoutManager;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the search query from the intent
        Intent intent = getIntent();
        searchQuery = intent.getStringExtra("searchquery");

        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);
        Log.d("SEARCH", "class: Search, method: onCreate, userId: " + userId);
        username = sharedPreferences.getString("username", null);
        Log.d("SEARCH", "method: onCreate, username: " + username);

        // Set up the RecyclerView
        myRecyclerView = findViewById(R.id.recycler_view);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        // Initialize the API and load the search results
        api = new API(this);
        api.searchAPI(searchQuery, this);
    }

    @Override
    public void onMoviesLoaded(ArrayList<Movie> movies) {
        myAdapter = new MovieAdapter(this, movies); //initialize MovieAdapter to use methods
        myRecyclerView.setAdapter(myAdapter); //passes adapter to recyclerview
        myRecyclerView.scrollToPosition(0); // Scroll to the top of the list
        myAdapter.notifyDataSetChanged(); // tell the adapter that the data has changed

        //swipes
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this, myAdapter, userId, username);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(myRecyclerView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_discover:
                Intent mainIntent = new Intent(Search.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                return true;
            case R.id.menu_matches:
                Intent matchesIntent = new Intent(Search.this, Matches.class);
                startActivity(matchesIntent);
                finish();
                return true;
            case R.id.menu_logout:
                Intent logoutIntent = new Intent(Search.this, Login.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

