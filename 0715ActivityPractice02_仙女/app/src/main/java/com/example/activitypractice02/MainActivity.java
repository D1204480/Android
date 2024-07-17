package com.example.activitypractice02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void SentFruitName(View view){

        // 1.按下按鈕Ａ,傳送Apple 2.按下按鈕Ｂ,傳送Banana
        String fruit;
        if(view.getId() == R.id.buttonA){
            fruit = "Apple";
        }else{
            fruit = "Banana";
        }
        Intent intent = new Intent(this,FruitActivity2.class);

        // 設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("FRUIT",fruit);

        // 利用intent攜帶bundle資料
        intent.putExtras(bundle);
        startActivity(intent);

    }
}