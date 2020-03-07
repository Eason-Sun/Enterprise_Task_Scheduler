package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskListAdapter extends ItemListAdapter {

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks) {
        super(context, resource, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = super.getInflater().inflate(super.getViewResourceId(), null);
        Task task = (Task) super.getItems().get(position);
        if (task != null) {
            TextView taskListIdText = convertView.findViewById(R.id.taskListIdText);
            TextView taskNameText = convertView.findViewById(R.id.taskNameText);
            TextView taskEmpNameText = convertView.findViewById(R.id.taskEmpNameText);
            TextView taskListEndDateText = convertView.findViewById(R.id.taskListEndDateText);
            taskListIdText.setText(" #" + task.getId());
            taskNameText.setText(task.getTaskName());
            taskListEndDateText.setText(task.getEndDate());
            taskEmpNameText.setText(task.getEmpName());
        }
        return convertView;
    }
}
