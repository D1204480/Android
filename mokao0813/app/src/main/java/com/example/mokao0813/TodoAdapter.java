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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_view, parent, false);
        }
        // Lookup view for data population
        TextView tv_title = (TextView) convertView.findViewById(R.id.listTvTitle);
        TextView tv_content = (TextView) convertView.findViewById(R.id.listTvContent);
        // Populate the data into the template view using the data object
        tv_title.setText(todo.getTitle());
        String contentText = String.valueOf(todo.getContent());
        if (contentText.length() >= 10) {
            contentText = contentText.substring(0, 10) + "...";
        }
        tv_content.setText(contentText);
        // Return the completed view to render on screen
        return convertView;
    }
}
