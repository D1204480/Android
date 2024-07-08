package com.example.a0708calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 加法
    public void add(View view) {
        TextView num1Text = (TextView) findViewById(R.id.num1);
        TextView num2Text = (TextView) findViewById(R.id.num2);
        TextView result = (TextView) findViewById(R.id.result);

        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double add = num1 + num2;
        result.setText(String.valueOf(add));
    }

    // 減法
    public void sub(View view) {
        TextView num1Text = (TextView) findViewById(R.id.num1);
        TextView num2Text = (TextView) findViewById(R.id.num2);
        TextView result = (TextView) findViewById(R.id.result);

        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double sub = num1 - num2;
        result.setText(String.valueOf(sub));
    }
    
    // 乘法
    public void multi(View view) {
        TextView num1Text = (TextView) findViewById(R.id.num1);
        TextView num2Text = (TextView) findViewById(R.id.num2);
        TextView result = (TextView) findViewById(R.id.result);

        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double multi = num1 * num2;
        result.setText(String.valueOf(multi));
    }
    
    // 除法
    public void mod(View view) {
        TextView num1Text = (TextView) findViewById(R.id.num1);
        TextView num2Text = (TextView) findViewById(R.id.num2);
        TextView result = (TextView) findViewById(R.id.result);
        Button modBtn = (Button) findViewById(R.id.modBtn);

        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        
        if (num2 != 0) {
            double mod = num1 / num2;
            result.setText(String.valueOf(mod));
            
        } else {
            result.setText("num2不能為0");
            modBtn.setEnabled(false);
        }
    }
}