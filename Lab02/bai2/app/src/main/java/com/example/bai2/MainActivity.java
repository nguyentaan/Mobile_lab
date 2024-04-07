package com.example.bai2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup colorRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorRadioGroup = findViewById(R.id.color_radio_group);
        
        findViewById(R.id.red_radio_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackgroundColor(Color.RED);
            }
        });

        findViewById(R.id.green_radio_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackgroundColor(Color.GREEN);
            }
        });

        findViewById(R.id.blue_radio_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackgroundColor(Color.BLUE);
            }
        });
    }

    private void changeBackgroundColor(int color) {
        getWindow().getDecorView().setBackgroundColor(color);
    }
}

