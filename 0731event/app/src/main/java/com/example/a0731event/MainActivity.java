package com.example.a0731event;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

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


        // 設定button監聽
        Button button = (Button) findViewById(R.id.button);
        Button bigBtn = (Button) findViewById(R.id.big_btn);
        Button smallBtn = (Button) findViewById(R.id.small_btn);

        button.setOnClickListener(this);
        bigBtn.setOnClickListener(this);
        smallBtn.setOnClickListener(this);

        // 設定長按監聽
        button.setOnLongClickListener(this);

        // 設定監聽layout的touch事件
        ConstraintLayout main = (ConstraintLayout) findViewById(R.id.main);
        main.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView textText = (TextView) findViewById(R.id.textText);  // 一串字
        TextView sizeText = (TextView) findViewById(R.id.sizeText);   // hello world

        // 改變字的大小
        float currentSize = textText.getTextSize() / getResources().getDisplayMetrics().scaledDensity;  // 計算出真正的字體大小sp(根據螢幕密度)
        textText.setTextSize(currentSize + 5);   // 變更字的尺寸
        sizeText.setText(String.valueOf(currentSize + 5));  // 顯示尺寸


        if (view.getId() == R.id.big_btn) {  // 放大的按鈕
            textText.setTextSize(currentSize + 5);   // 變更字的尺寸
            sizeText.setText(String.valueOf(currentSize + 5));  // 顯示尺寸

        } else if (view.getId() == R.id.small_btn) {   // 縮小的按鈕
            textText.setTextSize(currentSize - 5);
            sizeText.setText(String.valueOf(currentSize - 5));
        }
    }


    @Override
    public boolean onLongClick(View view) {  // 長按
        TextView textText = (TextView) findViewById(R.id.textText);  // 一串字
        TextView sizeText = (TextView) findViewById(R.id.sizeText);   // hello world

        float currentSize = textText.getTextSize() / getResources().getDisplayMetrics().scaledDensity;  // 計算出真正的字體大小
        textText.setTextSize(currentSize - 5);
        sizeText.setText(String.valueOf(currentSize - 5));

        return true;  // true/false表示會不會繼續後面的event行為
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TextView actionText = (TextView) findViewById(R.id.action);
        TextView positionText = (TextView) findViewById(R.id.position);

        int act = motionEvent.getAction();  // 讀取執行的是哪個event

        switch (act) {
            case MotionEvent.ACTION_DOWN:
                actionText.setText("ACTION DOWN");
                actionText.setTextColor(Color.parseColor("#992233"));
                break;

            case MotionEvent.ACTION_MOVE:
                actionText.setText("ACTION MOVE");
                actionText.setTextColor(Color.parseColor("#223399"));
                break;

            case MotionEvent.ACTION_UP:
                actionText.setText("ACTION UP");
                actionText.setTextColor(Color.parseColor("#229933"));
                break;
        }

        // 取得XY位置
        positionText.setText("X: " + motionEvent.getX() + "\n" + "Y: " + motionEvent.getY());

        return true;
    }
}