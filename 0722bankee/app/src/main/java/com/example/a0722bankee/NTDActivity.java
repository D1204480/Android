package com.example.a0722bankee;

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

public class NTDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ntdactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void GoBack(View view) {
        double amount = Double.parseDouble(((EditText)findViewById(R.id.edit_rate)).getText().toString()); // EditText轉為Double

        Intent intent = new Intent();  // 跳轉回mainActivity

        if (view.getId() == R.id.deposit) {  // 看是哪顆按鈕
            intent.putExtra("ACTION", "deposit");  //  回傳資料

        } else {
            intent.putExtra("ACTION", "withdraw");
        }

        intent.putExtra("AMOUNT", amount);  // 資料傳回main
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}