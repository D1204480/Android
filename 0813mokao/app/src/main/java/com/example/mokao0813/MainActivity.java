package com.example.mokao0813;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Todo> todoArrayList = new ArrayList<Todo>();   // ArrayList存放待辦事項
    private ActivityResultLauncher<Intent> intentActivityResultLanucher;

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

        intentActivityResultLanucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getData() != null && o.getResultCode() == Activity.RESULT_OK){
                            String action = o.getData().getStringExtra("ACTION");  // 得到回傳的action

                            if(action.equals("new")){  // (綁定為新增功能的)按鈕
                                String newTitle = o.getData().getStringExtra("TITLE");  // 讀取回傳的title
                                String newContent = o.getData().getStringExtra("CONTENT");   // 讀取回傳的content

                                // 建立物件
                                Todo newData = new Todo(newTitle, newContent);
                                // 放入陣列
                                todoArrayList.add(newData);

                            } else if (action.equals("edit")) {  // (綁定為) listView 的待辦事項欄位
                                String index = o.getData().getStringExtra("INDEX");  // 陣列裡的第幾項
                                String newTitle = o.getData().getStringExtra("TITLE");
                                String newContent = o.getData().getStringExtra("CONTENT");

                                todoArrayList.get(Integer.parseInt(index)).setTitle(newTitle);   // 把編輯後的title放回陣列
                                todoArrayList.get(Integer.parseInt(index)).setContent(newContent);   // 把編輯後的content放回陣列

                            } else{  // 移除
                                int removeIndex = o.getData().getIntExtra("INDEX", 0);
                                todoArrayList.remove(removeIndex);  // 移除陣列第index項
                            }
                        }
                    }
                }
        );

        ListView todoListView = (ListView) findViewById(R.id.todoListView);

        if(todoArrayList.isEmpty()){  // 陣列裡沒有資料
            Log.d("Test", "todoArrayList empty.");

            ArrayList<String> empty = new ArrayList<String>();  // 建立字串ArrayList
            empty.add("還沒有待辦事項，趕緊去新增！");

            // 建立adapter, 選擇要套用的Layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            todoListView.setAdapter(adapter);  // 設定ListView的adapter

        }else{  // 陣列裡已有資料
            TodoAdapter adapter = new TodoAdapter(this, todoArrayList);  // 建立adapter
            todoListView.setAdapter(adapter);  // 設定ListView的adapter
            todoListView.setOnItemClickListener(this);  // 監聽click事件
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        ListView todoListView = (ListView) findViewById(R.id.todoListView);

        if(todoArrayList.isEmpty()){
            Log.d("Test", "todoArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("還沒有待辦事項，趕緊去新增！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            todoListView.setAdapter(adapter);

        }else{
            TodoAdapter adapter = new TodoAdapter(this, todoArrayList);
            todoListView.setAdapter(adapter);
            todoListView.setOnItemClickListener(this);
        }
    }

    // 新增的按鈕
    public void newButtonClick(View view){
        Intent intent = new Intent(this, TodoActivity.class);  // 建立跳轉頁面

        Bundle bundle = new Bundle();   // 設定一個bundle來放資料
        bundle.putString("ACTION", "new");  // 綁定ACTION的key/value

        // 利用intent攜帶bundle的資料
        intent.putExtras(bundle);
        intentActivityResultLanucher.launch(intent);   // 跳轉頁面, intentActivityResultLanucher是為了接收, 送出去後回傳回來的資料
    }


    // 修改(ListView)欄位資料
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Todo item = todoArrayList.get(i);  // 取出陣列裡的(Todo)物件
        String title = item.getTitle();
        String content = item.getContent();

        Intent intent = new Intent(this, TodoActivity.class);   // 建立跳轉頁面

        // 設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION", "edit"); // 綁定ACTION的key/value
        bundle.putString("TITLE", title);
        bundle.putString("CONTENT", content);
        bundle.putInt("INDEX", i);

        // 利用intent攜帶bundle的資料
        intent.putExtras(bundle);  // putExtras送出資料
        intentActivityResultLanucher.launch(intent);  // 跳轉頁面, intentActivityResultLanucher是為了接收, 送出去後回傳回來的資料
    }
}