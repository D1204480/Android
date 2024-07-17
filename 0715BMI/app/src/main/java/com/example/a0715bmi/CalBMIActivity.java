package com.example.a0715bmi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalBMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cal_bmiactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void CalBMI(View view) {
        EditText edit_weight = (EditText) findViewById(R.id.edit_weight);
        EditText edit_height = (EditText) findViewById(R.id.edit_height);

        double weight = Double.parseDouble(edit_weight.getText().toString());
        double height = Double.parseDouble(edit_height.getText().toString());
        double bmi = weight / (height * height);

        Intent intent = new Intent();  // 跳轉回mainActivity
        intent.putExtra("BMI", bmi);  // 資料傳回main
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}