package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView nameTextView, emailTextView, introductionTextView, subjectTextView, learningTextView;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailValueTextView);
        introductionTextView = findViewById(R.id.introductionValueTextView);
        subjectTextView = findViewById(R.id.subjectValueTextView);
        learningTextView = findViewById(R.id.learningValueTextView);
        profileImageView = findViewById(R.id.profileImageView);

        String name = "Nguyễn Văn Tân";
        String email = "vantaan2002@gmail.com";
        String introduction = "I like playing video games and listening music";
        String subject = "I learn at UIT-VNUHCM from 2020.";
        String learning = "Web application development, Mobile development";

        nameTextView.setText(name);
        emailTextView.setText(email);
        introductionTextView.setText(introduction);
        subjectTextView.setText(subject);
        learningTextView.setText(learning);

        profileImageView.setImageResource(R.drawable.dreams);
    }
}
