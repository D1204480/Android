package com.example.mokao0813;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<Todo> {

    public TodoAdapter(@NonNull Context context, ArrayList<Todo> Todos) {
        super(context, 0, Todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        // convertView is the view to reuse, 如果null了, 表示沒有可reuse的view了, 需用inflate方法, 產生出新的view來用
        // inflate()會讀取要套用Layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_view, parent, false);
        }

        // Lookup view for data population
        TextView tv_title = (TextView) convertView.findViewById(R.id.listTvTitle);  // todo_view的title區塊
        TextView tv_content = (TextView) convertView.findViewById(R.id.listTvContent);   // todo_view的content區塊

        // Populate the data into the template view using the data object
        tv_title.setText(todo.getTitle());    // 在todo_view, 顯示todo物件的title
        String contentText = String.valueOf(todo.getContent());  // 取得todo物件的content
        if (contentText.length() >= 10) {
            contentText = contentText.substring(0, 10) + "...";
        }
        tv_content.setText(contentText);   // 在todo_view, 顯示todo物件的content

        // Return the completed view to render on screen
        return convertView;
    }
}
