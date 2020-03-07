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
            TextView idText = convertView.findViewById(R.id.empListIdText);
            TextView fstNameText = convertView.findViewById(R.id.empListFstNameText);
            TextView lstNameText = convertView.findViewById(R.id.empListLstNameText);
            TextView deptText = convertView.findViewById(R.id.empListDeptText);
            TextView startDateText = convertView.findViewById(R.id.empListStartDateText);
            idText.setText(" #" + e.getId());
            fstNameText.setText(e.getFirstName());
            lstNameText.setText(e.getLastName());
            deptText.setText(e.getDepartment());
            startDateText.setText(e.getStartDate());
        }
        return convertView;
    }
}
