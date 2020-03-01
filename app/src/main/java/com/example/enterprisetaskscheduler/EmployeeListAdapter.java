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

public class EmployeeListAdapter extends ArrayAdapter<Employee> {

    private ArrayList<Employee> employees;
    private LayoutInflater inflater;
    private int viewResourceId;

    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Employee> employees) {
        super(context, resource, employees);
        this.employees = employees;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(viewResourceId,null);
        Employee e = employees.get(position);
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

    public void update(ArrayList<Employee> filteredEmployees) {
        employees = new ArrayList<>();
        employees.addAll(filteredEmployees);
        notifyDataSetChanged(); // Notify android to update the listView
    }

    // Must be overridden for updated size of filtered employee list
    @Override
    public int getCount() {
        return employees.size();
    }

}
