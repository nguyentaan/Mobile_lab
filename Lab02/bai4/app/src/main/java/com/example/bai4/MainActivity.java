package com.example.bai4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextProjectName;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextProjectName = findViewById(R.id.editTextProjectName);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String projectName = editTextProjectName.getText().toString();

                // Start the ViewInformationActivity and pass data as extras
                Intent intent = new Intent(MainActivity.this, ViewInformationActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });
    }
}
