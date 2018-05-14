package com.example.waseem.splash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch switchOfPopUp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
         switchOfPopUp = findViewById(R.id.PopUPOnOff);
        switchOfPopUp.setOnCheckedChangeListener(this);
        SharedPreferences sharedPrefs = getSharedPreferences("com.example.waseem.splash", MODE_PRIVATE);

        switchOfPopUp.setChecked(sharedPrefs.getBoolean("PopUp",true));




    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            buttonView.setText("On");
            SharedPreferences.Editor editor = getSharedPreferences("com.example.waseem.splash", MODE_PRIVATE).edit();
            editor.putBoolean("PopUp", true);
            editor.apply();
            scheduleAlarm();

        }else if (!(isChecked)){

            buttonView.setText("OFF");
            SharedPreferences.Editor editor = getSharedPreferences("com.example.waseem.splash", MODE_PRIVATE).edit();
            editor.putBoolean("PopUp", false);
            editor.apply();
            closeService();
        }


    }

    public  void closeService (){
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 12345, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    public void scheduleAlarm() {
        // getting Old Status Of popup Messages
        int counter = 0 ;

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor edit;
        sharedPreferences = getSharedPreferences("popCounter", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        edit.putInt("counter",counter);

        edit.apply();

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);

     //   String [] popUPText =getResources().getStringArray(R.array.popUp_Athkar);

    //   intent.putExtra("popUpText",popUPText);


        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                60*1000, pIntent);
        Toast.makeText(this, " setting class", Toast.LENGTH_SHORT).show();
    }
}
