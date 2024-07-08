package com.example.counter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public void Count(View view) {
        TextView timesText = (TextView) findViewById(R.id.times);  // 計數按鈕按下, 次數+1
        Button countButton = (Button) findViewById(R.id.count);  // 計數按鈕
        EditText limitEditView = (EditText) findViewById(R.id.limit);  // 步數輸入匡

        int limit = Integer.parseInt(limitEditView.getText().toString());  // 輸入次數
        int times = Integer.parseInt(timesText.getText().toString());   // 點擊次數

        if (limit > times) {
            times += 1;
            timesText.setText(String.valueOf(times));  // 更新timesTexts內容
        } else {
            timesText.setText("已達上限");
            countButton.setEnabled(false);  // 使按鈕不能按
        }
    }

    public void Init(View view) {
        Button countButton = (Button) findViewById(R.id.count);  // 計數button
        TextView timesText = (TextView) findViewById(R.id.times);  // 輸入匡

        timesText.setText("0");   // 輸入匡顯示為0
        countButton.setEnabled(true);  // 使按鈕可以按

    }

} // end of MainActivity