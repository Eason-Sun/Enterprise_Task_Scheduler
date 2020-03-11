package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class EmployeeListAdapter extends ItemListAdapter {


    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Employee> employees) {
        super(context, resource, employees);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = super.getInflater().inflate(super.getViewResourceId(),null);
        Employee e = (Employee) super.getItems().get(position);
        if (e != null) {
            TextView empListIdText = convertView.findViewById(R.id.empListIdText);
            TextView empListNameText = convertView.findViewById(R.id.empListNameText);
            TextView empListDeptText = convertView.findViewById(R.id.empListDeptText);
            TextView empListStartDateText = convertView.findViewById(R.id.empListStartDateText);
            empListIdText.setText(" #" + e.getId());
            empListNameText.setText(e.getName());
            empListDeptText.setText(e.getDept());
            empListStartDateText.setText(e.getStartDate());
        }
        return convertView;
    }
}
