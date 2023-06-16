package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class Search2 extends AppCompatActivity {
    Button searchButton;
    TextInputEditText searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        // Initialize views
        searchQuery = findViewById(R.id.search_query);
        searchButton = findViewById(R.id.search_button);

        // Set onClickListener for searchButton
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the query from the EditText
                String searchquery = searchQuery.getText().toString();

                // Create an intent to start the Search activity
                Intent searchIntent = new Intent(Search2.this, Search.class);
                searchIntent.putExtra("searchquery", searchquery);
                // Start the Search activity
                startActivity(searchIntent);
                finish();
            }
        });
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
                Intent mainIntent = new Intent(Search2.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                return true;
            case R.id.menu_matches:
                Intent matchesIntent = new Intent(Search2.this, Matches.class);
                startActivity(matchesIntent);
                finish();
                return true;
            case R.id.menu_logout:
                Intent logoutIntent = new Intent(Search2.this, Logout.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
