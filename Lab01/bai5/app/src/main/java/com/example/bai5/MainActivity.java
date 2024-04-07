// MainActivity.java
package com.example.bai5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String input = "";
    private double num1 = Double.NaN;
    private double num2 = Double.NaN;
    private char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        findViewById(R.id.btn_zero).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_one).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_two).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_three).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_four).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_five).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_six).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_seven).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_eight).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_nine).setOnClickListener(buttonClickListener);
        findViewById(R.id.btn_decimal).setOnClickListener(buttonClickListener);

        findViewById(R.id.btn_add).setOnClickListener(operatorClickListener);
        findViewById(R.id.btn_subtract).setOnClickListener(operatorClickListener);
        findViewById(R.id.btn_multiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.btn_divide).setOnClickListener(operatorClickListener);

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        findViewById(R.id.btn_equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            input += button.getText().toString();
            textView.setText(input);
        }
    };

    private View.OnClickListener operatorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            if (!input.isEmpty()) {
                num1 = Double.parseDouble(input);
                operator = button.getText().charAt(0);
                input = "";
                textView.setText(String.valueOf(num1) + operator);
            }
        }
    };

    private void compute() {
        if (!Double.isNaN(num1) && !input.isEmpty()) {
            num2 = Double.parseDouble(input);
            double result = 0;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case 'x':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
            }
            textView.setText(String.valueOf(result));
            num1 = result;
            input = "";
        }
    }

    private void clear() {
        num1 = Double.NaN;
        num2 = Double.NaN;
        operator = '\0';
        input = "";
        textView.setText("");
    }
}
