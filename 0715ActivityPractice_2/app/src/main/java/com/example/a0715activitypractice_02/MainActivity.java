package com.example.a0715activitypractice_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.utilities.SchemeFruitSalad;

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

    public void sentFruitName(View view) {
        // 按下按鈕A, 傳送"apple", 按下按鈕B, 傳送"banana"

        String fruit;
        if (view.getId() == R.id.appleBtn) {
            fruit = "Apple";
        } else {
            fruit = "Banana";
        }

        Intent intent = new Intent(this, fruitActivity.class);

        // 設定一個bundle(Map容器)來放資料
        Bundle bundle = new Bundle();
        bundle.putString("FRUIT", fruit);  // bundle為key/value組合

        // 利用intent攜帶bundle的資料
        intent.putExtras(bundle);
        startActivity(intent);
    }
}