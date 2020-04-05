package com.example.enterprisetaskscheduler;

import android.app.Notification;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.Service ;
import android.content.Intent ;
import android.database.Cursor;
import android.os.Handler ;
import android.os.IBinder ;
import androidx.core.app.NotificationCompat;
import android.util.Log ;

import java.util.Calendar;
import java.util.Timer ;
import java.util.TimerTask ;
public class NotificationService extends Service {
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;
    TaskTableHelper taskDb;

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log.e ( TAG , "onStartCommand" ) ;
        super.onStartCommand(intent , flags , startId);
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onCreate () {
        Log.e (TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        stopTimerTask() ;
        super .onDestroy() ;
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        timer.schedule(timerTask , 1000, Your_X_SECS * 432000) ; //
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer.cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler.post( new Runnable() {
                    public void run () {
                        int NumberOfTask = getTaskNumber();
                        createNotification(NumberOfTask) ;
                    }
                }) ;
            }
        } ;
    }

    public int getTaskNumber (){
        taskDb = new TaskTableHelper(getApplicationContext());
        Calendar currDate = Calendar.getInstance();
        int currDay = currDate.get(Calendar.DAY_OF_MONTH);
        int currMonth = currDate.get(Calendar.MONTH);
        int currYear = currDate.get(Calendar.YEAR);
        String date = currYear + "/" + (currMonth + 1) + "/" + currDay;
        Cursor Data = taskDb.getDataByDueDate(date);
        return Data.getCount();
    }

    public void createNotification (int NumberOfTask) {
        String NOTIFICATION_CHANNEL_ID = "10001";
        String default_notification_channel_id = "default";
        String Note;
        if (NumberOfTask > 0){
            NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
            mBuilder.setContentTitle( "Task Due Notification" );
            if (NumberOfTask > 1){
                Note = "There are " + NumberOfTask+ " tasks due today";
            }else{
                Note = "There is " + NumberOfTask + " task due today";
            }
            mBuilder.setContentText(Note) ;
            mBuilder.setTicker("Notification Listener Service") ;
            mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground) ;
            mBuilder.setAutoCancel( true ) ;
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel) ;
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        }
    }
}