package com.example.tempcounter;

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

    // 轉成華氏
    public void toFtemp(View view) {
        TextView tempText = (TextView) findViewById(R.id.temp);
        Button fTempButton = (Button) findViewById(R.id.fTemp);   //華氏按鈕
        TextView showTemp = (TextView) findViewById(R.id.showTemp);  // 顯示溫度區

        int temp = Integer.parseInt(tempText.getText().toString());  // 取得溫度
        int toCtemp = ((9 * temp) / 5) + 32;
        showTemp.setText(String.valueOf(toCtemp));
    }

    // 轉成攝氏
    public void toCtemp(View view) {
        TextView tempText = (TextView) findViewById(R.id.temp);
        Button cTempButton = (Button) findViewById(R.id.fTemp);   //攝氏按鈕
        TextView showTemp = (TextView) findViewById(R.id.showTemp);  // 顯示溫度區


        int temp = Integer.parseInt(tempText.getText().toString());  // 取得溫度
        int toCtemp = (5 * (temp -32)) / 9;
        showTemp.setText(String.valueOf(toCtemp));

    }
}