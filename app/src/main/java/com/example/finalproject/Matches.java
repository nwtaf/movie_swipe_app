package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Matches extends AppCompatActivity {
    private EditText usersInput;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // Initialize views
        usersInput = findViewById(R.id.users_input);
        queryButton = findViewById(R.id.query_button);

        // Set onClickListener for searchButton
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the query from the EditText
                String query = usersInput.getText().toString();

                // Create an intent to start the MatchesResults activity
                Intent intent = new Intent(Matches.this, MatchesResults.class);
                intent.putExtra("query", query);

                // Start the MatchesResults activity
                startActivity(intent);
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
                Intent mainIntent = new Intent(Matches.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                return true;
            case R.id.menu_search:
                Intent searchIntent = new Intent(Matches.this, Search2.class);
                startActivity(searchIntent);
                finish();
                return true;
            case R.id.menu_logout:
                Intent logoutIntent = new Intent(Matches.this, Logout.class);
                startActivity(logoutIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
