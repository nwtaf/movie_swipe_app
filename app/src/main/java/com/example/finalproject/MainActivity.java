package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements API.ApiCallback{
    private API api;
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    private MovieAdapter myAdapter;
    private String userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null); //from Login
        Log.d("MainActivity", "method: onCreate, userId: " + userId);
        username = sharedPreferences.getString("username", null); //from Login
        Log.d("MainActivity", "method: onCreate, username: "+ username);

        myRecyclerView = findViewById(R.id.recycler_view);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        api = new API(this); //initialize API to use methods

        Log.d("MAIN", "At main page");

        api.discoverAPI(this); //loads discover
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
                return true;
            case R.id.menu_search:
                Intent search2Intent = new Intent(MainActivity.this, Search2.class);
                startActivity(search2Intent);
                finish();
                return true;
            case R.id.menu_matches:
                Intent matchesIntent = new Intent(MainActivity.this, Matches.class);
                startActivity(matchesIntent);
                finish();
                return true;
            case R.id.menu_logout:
                Intent logoutIntent = new Intent(MainActivity.this, Logout.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

