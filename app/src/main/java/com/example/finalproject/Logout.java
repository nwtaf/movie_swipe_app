package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logout extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    Button logoutButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logoutButton = findViewById(R.id.logout_button);
        textView = findViewById(R.id.user_details);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
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
                Intent mainIntent = new Intent(Logout.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                return true;
            case R.id.menu_search:
                Intent searchIntent = new Intent(Logout.this, Search2.class);
                startActivity(searchIntent);
                finish();
                return true;
            case R.id.menu_matches:
                Intent matchesIntent = new Intent(Logout.this, Matches.class);
                startActivity(matchesIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}