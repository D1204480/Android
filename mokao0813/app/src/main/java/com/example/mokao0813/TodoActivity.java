package com.example.mokao0813;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class TodoActivity extends AppCompatActivity {
    String title, content, action;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = this.getIntent().getExtras();
        action = bundle.getString("ACTION");

        if(bundle != null && action.equals("edit")){
            title = String.format(bundle.getString("TITLE"));
            content = String.format(bundle.getString("CONTENT"));
            index = bundle.getInt("INDEX");

            EditText tvTitle = (EditText) findViewById(R.id.newTodoTitle);
            tvTitle.setText(title);
            TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.contentTextInputLayout);
            textInputLayout.getEditText().setText(content);
        }
    }

    // 彈跳視窗
    public void saveButtonClick(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String titleText = "確定新增？";
        String contentText = "確定內容並儲存？";
        if(action.equals("edit")){
            titleText = "確定修改？";
            contentText = "確定修改的內容並儲存？";
        }
        dialog.setTitle(titleText);
        dialog.setMessage(contentText);
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = ((EditText)findViewById(R.id.newTodoTitle)).getText().toString();
                TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.contentTextInputLayout);
                String content = String.valueOf(textInputLayout.getEditText().getText());

                Intent intent = new Intent();
                intent.putExtra("TITLE", title);
                intent.putExtra("CONTENT", content);

                intent.putExtra("ACTION", action);
                if(action.equals("edit")){
                    intent.putExtra("INDEX", String.valueOf(index));
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }


    // 彈跳視窗
    public void giveUpButtonClick(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("確定放棄？");
        dialog.setMessage("確定放棄並返回主頁面？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }
}