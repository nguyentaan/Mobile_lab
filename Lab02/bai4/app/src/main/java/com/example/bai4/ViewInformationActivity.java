package com.example.bai4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_information);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewProjectName = findViewById(R.id.textViewProjectName);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Retrieve data from intent extras
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String projectName = getIntent().getStringExtra("projectName");

        // Set data to TextViews
        textViewName.setText("Name: " + name);
        textViewEmail.setText("Email: " + email);
        textViewProjectName.setText("Project Name: " + projectName);

        // Set click listener for the back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                finish(); // Close the current activity
            }
        });
    }
}


