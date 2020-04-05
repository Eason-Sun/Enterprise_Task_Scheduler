package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static android.content.Intent.*;
import static org.junit.Assert.*;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class ContactEmployeeTest {

    private ContactEmployee activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(ContactEmployee.class)
                .create()
                .start()
                .get();
    }

    @Test
    public void checkOnCreateShowButton() {
        Button send = activity.findViewById(R.id.sendContactEmployee);

        assertNotNull(send);
    }

    @Test
    public void checkOnCreateShowText() {
        TextView textTo = activity.findViewById(R.id.contactTextTo);
        TextView textSubject = activity.findViewById(R.id.contactTextSubject);
        TextView textMessage = activity.findViewById(R.id.contactTextMessage);

        assertNotNull(textTo);
        assertNotNull(textSubject);
        assertNotNull(textMessage);
    }

    @Test
    public void checkOnCreateShowMessageBox() {
        TextView to = activity.findViewById(R.id.contactEmailText);
        TextView subject = activity.findViewById(R.id.contactSubjectInput);
        TextView message = activity.findViewById(R.id.contactMessageInput);

        assertNotNull(to);
        assertNotNull(subject);
        assertNotNull(message);
    }

    @Test
    public void buttonSendOnClickShowActionChooser() {
        Button send = activity.findViewById(R.id.sendContactEmployee);
        send.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertEquals(ACTION_CHOOSER, actualIntent.getAction());
    }

    @Test
    public void buttonSendOnClickCheckDataIsNull() {
        Button send = activity.findViewById(R.id.sendContactEmployee);
        send.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertNull(actualIntent.getData());
    }

    @Test
    public void checkOnCreateShowCorrectMessage() {
        Intent intent = new Intent();
        intent.putExtra("fromActivity", "AddTask");
        intent.putExtra("email", "jd@test.com");
        intent.putExtra("subject", "New task assigned to you!");
        String msg = String.format("Hello %s,\n\nThe %s level task: %s has been assigned to you. Please submit the work before %s.\n\nThank you!",
                "David", "Entry", "Test", "2020/04/07");
        intent.putExtra("msg", msg);

        ContactEmployee intentActivity = Robolectric.buildActivity(ContactEmployee.class, intent)
                .create()
                .start()
                .get();

        TextView message = intentActivity.findViewById(R.id.contactMessageInput);
        TextView email = intentActivity.findViewById(R.id.contactEmailText);
        TextView subject = intentActivity.findViewById(R.id.contactSubjectInput);
        assertEquals("jd@test.com", email.getText().toString());
        assertEquals("New task assigned to you!", subject.getText().toString());
        assertEquals(msg, message.getText().toString());
    }

}
