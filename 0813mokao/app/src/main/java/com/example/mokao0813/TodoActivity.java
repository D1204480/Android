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

        Bundle bundle = this.getIntent().getExtras();  // 建立接收資料, getExtras接收資料
        action = bundle.getString("ACTION");  // 接收傳來的ACTION資料

        if(bundle != null && action.equals("edit")){  // 來自(main)編輯欄位的資料
            title = String.format(bundle.getString("TITLE"));
            content = String.format(bundle.getString("CONTENT"));
            index = bundle.getInt("INDEX");

            EditText tvTitle = (EditText) findViewById(R.id.newTodoTitle);
            tvTitle.setText(title);  // 來自main欄位的title顯示在, 編輯區的title
            TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.contentTextInputLayout);
            textInputLayout.getEditText().setText(content);  // 來自main欄位的content顯示在, 編輯區的content
        }
    }

    // 彈跳視窗
    public void saveButtonClick(View view){  // (儲存)按鈕
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
                String content = String.valueOf(textInputLayout.getEditText().getText());  // textInputLayout轉型為字串

                Intent intent = new Intent();  // 建立intent物件, 傳資料
                intent.putExtra("TITLE", title);  // intent傳回title
                intent.putExtra("CONTENT", content);   // intent傳回content

                intent.putExtra("ACTION", action);   // intent傳回action
                if(action.equals("edit")){  // 如ACTION為編輯
                    intent.putExtra("INDEX", String.valueOf(index));  // 回傳資料原本在陣列的index值
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }


    // 彈跳視窗
    public void giveUpButtonClick(View view){  // (離開)按鈕
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("確定放棄？");
        dialog.setMessage("確定放棄並返回主頁面？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }  // finish關閉頁面
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }
}