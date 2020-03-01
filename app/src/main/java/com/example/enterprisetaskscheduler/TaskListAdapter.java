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

public class TaskListAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;
    private LayoutInflater inflater;
    private int viewResourceId;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(viewResourceId,null);
        Task task = tasks.get(position);
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

    public void update(ArrayList<Task> filteredTasks) {
        tasks = new ArrayList<>();
        tasks.addAll(filteredTasks);
        notifyDataSetChanged(); // Notify android to update the listView
    }

    // Must be overridden for updated size of filtered employee list
    @Override
    public int getCount() {
        return tasks.size();
    }

}
