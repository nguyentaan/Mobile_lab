package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText=(EditText) findViewById(R.id.editTextContent);
        final Button btn=(Button) findViewById(R.id.buttonReadData);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data;
                InputStream in = getResources().openRawResource(R.raw.myfile);
                InputStreamReader inreader = new InputStreamReader(in);
                BufferedReader bufreader = new BufferedReader(inreader);
                StringBuilder builder = new StringBuilder();
                if(in!=null){
                    try {
                        while((data=bufreader.readLine())!=null){
                            builder.append(data);
                            builder.append("\n");
                        }
                        in.close();
                        editText.setText(builder.toString());
                    }
                    catch (IOException ex){
                        Log.e("EROOR",ex.getMessage());
                    }
                }
            }
        });
    }
}