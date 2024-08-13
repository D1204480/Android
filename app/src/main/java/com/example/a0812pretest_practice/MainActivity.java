package com.example.a0812pretest_practice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private String title;
    private String content;
    private ListView todoListView;
    private List<String> todoItems = new ArrayList<>();


    private class todoAdapter extends ArrayAdapter<Todo> {

        public todoAdapter(@NonNull Context context, ArrayList<Todo> todoItems) {
            super(context, 0, todoItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 找到哪一筆資料
            Todo todo = getItem(position);

            // 設定要塞入的樣式
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_information, parent, false);
            }

            // 將資料塞入設計好的樣式
            TextView tv_todoTitle = (TextView) convertView.findViewById(R.id.ed_title);
            TextView tv_todoContent = (TextView) convertView.findViewById(R.id.ed_content);

            tv_todoTitle.setText(todo.title);
            tv_todoContent.setText(todo.content);

            return convertView;
        }
    }


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


        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 寫另一個Activity回傳後, 得到回傳的資料之後的方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                            title = result.getData().getStringExtra("TITLE");
                            content = result.getData().getStringExtra("CONTENT");

                        }
                    }
                }
        );


        // 使用方法新增array裡的資料
        todoListView = (ListView) findViewById(R.id.todo_listView);
        settodo();
        todoListView.setOnItemClickListener(this);
    }


    private void settodo() {
//        cities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));  // 取得array裡的資料
//        cities.add("Taichung");
//        cities.add("Hualien");
//        cities.add("Taitung");
//
//        // new ArrayAdapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
//        cityListView.setAdapter(adapter);


//        -------
        ArrayList<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo("wake up", "起床起床起床起床"));
        todoList.add(new Todo("刷牙", "洗臉洗臉洗臉洗臉洗臉洗臉"));
        todoList.add(new Todo("吃", "eaaaaaaaaaaaaaaaat"));


        todoAdapter adapter = new todoAdapter(this, todoList);
        todoListView.setAdapter(adapter);


    }


    public void GotoAddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intentActivityResultLauncher.launch(intent);

    }


    public void updateItem() {
//        EditText
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        TextView textView = (TextView) findViewById(R.id.textView);
//        String[] citiesArray = getResources().getStringArray(R.array.cities);   // 找到目標array裡的資料

        // Modal彈跳視窗
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("城市");
        dialog.setMessage("您選擇的是: " + todoItems.get(i));
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", null);
        dialog.setNeutralButton("取消", null);
        dialog.setNegativeButton("放棄", null);
        dialog.show();

//        textView.setText("您選擇的是: " + citiesArray[i]);

        // Toast顯示視窗
//        Toast.makeText(this, "您選擇的是: " + citiesArray[i], Toast.LENGTH_LONG).show();  // 顯示結果在畫面上

    }

}