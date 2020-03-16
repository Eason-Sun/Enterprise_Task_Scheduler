package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class EmployeeListAdapter extends ItemListAdapter {

    private char[] charsHeaderArray;

    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Employee> employees, @NonNull String headerIndex) {
        super(context, resource, employees);
        charsHeaderArray = headerIndex.toCharArray();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = super.getInflater().inflate(super.getViewResourceId(),null);
        Employee e = (Employee) super.getItems().get(position);
        if (e != null) {
            ArrayList<TextView> textViewArray;
            textViewArray = new ArrayList<>();
            //TextView For List
            TextView empListIdText = convertView.findViewById(R.id.empListIdText);
            TextView empListNameText = convertView.findViewById(R.id.empListNameText);
            TextView empListLevelText = convertView.findViewById(R.id.empListLevelText);
            TextView empListDeptText = convertView.findViewById(R.id.empListDeptText);
            TextView empListStartDateText = convertView.findViewById(R.id.empListStartDateText);
            TextView empListEmailText = convertView.findViewById(R.id.empListEmailText);
            //Set Content for List
            empListIdText.setText(" #" + e.getId());
            empListNameText.setText(e.getName());
            empListLevelText.setText(e.getLevel());
            empListDeptText.setText(e.getDept());
            empListStartDateText.setText(e.getStartDate());
            empListEmailText.setText(e.getEmail());
            //Fill the arrayList(Order is important)
            textViewArray.add(empListIdText);
            textViewArray.add(empListNameText);
            textViewArray.add(empListLevelText);
            textViewArray.add(empListDeptText);
            textViewArray.add(empListStartDateText);
            textViewArray.add(empListEmailText);
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
                    if(textView != empListIdText){
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                        int width = screenWidth/count;
                        params.width = width;
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                    }
                }
            }else{
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) empListIdText.getLayoutParams();
                params.width= 1100;
                empListIdText.setLayoutParams(params);
            }
        }
        return convertView;
    }
}
