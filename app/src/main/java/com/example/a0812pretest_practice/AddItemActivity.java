package com.example.a0812pretest_practice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }


    public void safe(View view) {
        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        String title = String.valueOf(editTitle.getText());   // 取得title

        TextInputLayout contentTextInput = (TextInputLayout)findViewById(R.id.edit_content);
        String content = String.valueOf(contentTextInput.getEditText().getText());   //  取得內容


        Intent intent = new Intent();  // 跳轉回mainActivity
        intent.putExtra("TITLE", title);  // 資料傳回main
        intent.putExtra("CONTENT", content);  // 資料傳回main
        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void leave(View view) {
        finish();
    }

}