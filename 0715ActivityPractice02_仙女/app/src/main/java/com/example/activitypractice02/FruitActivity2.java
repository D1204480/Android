package com.example.activitypractice02;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FruitActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fruit2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 接受資料
        Bundle bundle = this.getIntent().getExtras();

        // 點到BtnA Textview顯示Apple, 點到BtnB TextView顯示Banana
        if(bundle != null){
            String fruit = String.format(bundle.getString("FRUIT"));

            // set name
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(fruit);


            // set image
            ImageView imageView = (ImageView) findViewById(R.id.image);
            String filename = fruit.toLowerCase();
            int imgId = getResources().getIdentifier(filename,"drawable",getPackageName());
            Drawable drawableImg = ContextCompat.getDrawable(this, imgId);
            imageView.setImageDrawable(drawableImg);
        }
    }

    public void  CloseActivity(View view){
        finish();
    }
}