package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskListAdapter extends ItemListAdapter {

    private char[] charsHeaderArray;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks, @NonNull String headerIndex) {
        super(context, resource, tasks);
        charsHeaderArray = headerIndex.toCharArray();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = super.getInflater().inflate(super.getViewResourceId(), null);
        Task task = (Task) super.getItems().get(position);
        if (task != null) {
            ArrayList<TextView> textViewArray;
            textViewArray = new ArrayList<>();
            //TextView For List
            TextView taskListIdText = convertView.findViewById(R.id.taskListIdText);
            TextView taskNameText = convertView.findViewById(R.id.taskNameText);
            TextView taskLevelText = convertView.findViewById(R.id.taskLevelText);
            TextView taskDeptNameText = convertView.findViewById(R.id.taskDeptNameText);
            TextView taskEmpNameText = convertView.findViewById(R.id.taskEmpNameText);
            TextView taskStatusText = convertView.findViewById(R.id.taskStatusText);
            TextView taskListStartDateText = convertView.findViewById(R.id.taskListStartDateText);
            TextView taskListEndDateText = convertView.findViewById(R.id.taskListEndDateText);
            //Set Content for List
            taskListIdText.setText(" #" + task.getId());
            taskNameText.setText(task.getName());
            taskListEndDateText.setText(task.getDueDate());
            taskEmpNameText.setText(task.getEmpName());
            taskLevelText.setText(task.getLevel());
            taskDeptNameText.setText(task.getDept());
            taskStatusText.setText(task.getStatus());
            taskListStartDateText.setText(task.getStartDate());
            //Fill the arrayList(Order is important)
            textViewArray.add(taskListIdText);
            textViewArray.add(taskNameText);
            textViewArray.add(taskLevelText);
            textViewArray.add(taskDeptNameText);
            textViewArray.add(taskEmpNameText);
            textViewArray.add(taskStatusText);
            textViewArray.add(taskListStartDateText);
            textViewArray.add(taskListEndDateText);
            //Set Visibility for List
            int index = 0;
            int count = 0;
            int screenWidth = 1100;
            for (TextView textView : textViewArray) {
                if (Character.valueOf(charsHeaderArray[index]).compareTo('1') == 0) {
                    textView.setVisibility(View.VISIBLE);
                    count = count + 1;
                    if (index == 0){
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                        params.width= 125;
                        textView.setLayoutParams(params);
                        screenWidth = screenWidth -125;
                        count  = count - 1;
                    }
                }
                else {
                    textView.setVisibility(View.GONE);
                }
                index = index + 1;
            }
            if (count > 0) {
                for (TextView textView : textViewArray) {
                    if(textView != taskListIdText){
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                        int width = screenWidth/count;
                        params.width = width;
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                    }
                }
            }else{
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) taskListIdText.getLayoutParams();
                params.width= 1100;
                taskListIdText.setLayoutParams(params);
            }
        }
        return convertView;
    }
}
