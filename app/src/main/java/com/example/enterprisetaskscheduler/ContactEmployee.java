package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContactEmployee extends AppCompatActivity {
    TextView contactEmailText;
    EditText contactSubjectInput, contactMessageInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_employee);

        contactEmailText = findViewById(R.id.contactEmailText);
        contactSubjectInput = findViewById(R.id.contactSubjectInput);
        contactMessageInput = findViewById(R.id.contactMessageInput);
        String empName = getIntent().getStringExtra("empName");
        String fstName = empName.split(" ")[0];
        String empEmail = getIntent().getStringExtra("empEmail");
        contactEmailText.setText(empEmail);
        contactMessageInput.setText("Hello " + fstName + ",\n\n");

    }

    public void sendOnClick(View view) {
        String subject = contactSubjectInput.getText().toString();
        String message = contactMessageInput.getText().toString();
        String[] recipients = {contactEmailText.getText().toString()};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client:"));
    }
}
