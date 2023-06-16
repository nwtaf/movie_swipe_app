package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Most of this does not work! To get matches, do the opposite of how the data was loaded into the firebase (i.e. the opposite of SwipeToDelete.jave)
public class MatchesResults extends AppCompatActivity {
    private Map<String, User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_results);

        // Get the query string from the intent
        String query = getIntent().getStringExtra("query");

        // Split the query string by commas
        String[] usernames = query.split(",");
        String username0 = usernames[0].trim();
        Log.d("MatchesResults", "username0: " + username0);
        String username1 = usernames[1].trim();
        Log.d("MatchesResults", "username1: " + username1);

        // Initialize the TableLayout
        //mTableLayout = findViewById(R.id.matches_table);

        // Get a reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

// Add a listener to retrieve the data
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Convert the DataSnapshot to a JSON string
                String json = new Gson().toJson(dataSnapshot.getValue());

                // Parse the JSON data and store it in the users Map
                Type type = new TypeToken<Map<String, User>>() {}.getType();
                users = new Gson().fromJson(json, type);

                // Example code to get matches for a specific user
                List<String> matches = getMatchesBetweenUsers(username0, username1);
                Log.d("MatchesResults", "match results for "+ username0 +", "+ username1 + "List<String> matches: "+matches);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
                Log.d("MatchesResults", "Failed to read value: " + databaseError.toException());
            }
        });

    }

    public List<String> getMatchesByUserAndSwipe(String username) {
        List<String> matches = new ArrayList<>();
        User user = users.get(username);
        Log.d("MatchesResults", "method: getMacthesByUserAndSwipe, user: " + user);
        if (user != null) {
            Log.d("MatchesResults", "method: getMatchesByUserAndSwipe user found: " + username);
            for (String movieID : user.movies.keySet()) {
                DatabaseMovie movie = user.movies.get(movieID);
                Log.d("MatchesResults", "Movie found: " + movie.title);
                if ("right".equals(movie.swipe)) {
                    matches.add(movie.title);
                    Log.d("MatchesResults", "Match found: " + movie.title);
                }
            }
        } else {
            Log.d("MatchesResults", "User not found: " + username);
        }
        return matches;
    }

        private List<String> getMatchesBetweenUsers(String usernameA, String usernameB){
            if (users == null) {
                Log.d("MatchesResults", "method: getMatchesBetweenUsers, users: ", null);
            }
            MatchesResults results = new MatchesResults();
            List<String> matches1 = results.getMatchesByUserAndSwipe(usernameA);
            List<String> matches2 = results.getMatchesByUserAndSwipe(usernameB);

            List<String> mutualMatches = new ArrayList<>();
            for (String match : matches1) {
                if (matches2.contains(match)) {
                    mutualMatches.add(match);
                }
            }
            return mutualMatches;
        }

    public static class User {
        public Map<String, DatabaseMovie> movies;
    }

    public static class DatabaseMovie {
        public String poster_path;
        public String swipe;
        public String title;
    }
}
