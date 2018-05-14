package com.example.waseem.splash;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Waseem on 3/14/2018.
 */

public class MyTestService extends IntentService {
    public static final String ACTION = "com.example.waseem.intentsrvice";
    private Handler mHandler;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyTestService() {
        super("MyTestService");


    }

    @Override
    public void onCreate() {
        super.onCreate();


    }


    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     *               This may be null if the service is being restarted after
     *               its process has gone away; see
     *               {@link Service#onStartCommand}
     *               for details.
     */
    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {




        mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {



                showToast(MyTestService.this,"byree",intent.getStringArrayExtra("popUpText"));
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showToast (Context context , String title, String[] messagePop){
        String message ="";
        // inflate your xml layout
        //  LayoutInflater inflater = a.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.t,
                null);
        // set the custom display
        int counter=0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("popCounter", MODE_PRIVATE);
        counter  =sharedPreferences.getInt("counter",counter);
if(counter < messagePop.length){
    message= messagePop[counter];
    counter++;

}else {
    counter=0 ;
    message= messagePop[counter];
 counter++;
}

        SharedPreferences sharedPreferencesAfterIncrement;
        SharedPreferences.Editor edit2;
        sharedPreferencesAfterIncrement = getSharedPreferences("popCounter", MODE_PRIVATE);
        edit2 = sharedPreferencesAfterIncrement.edit();
        edit2.putInt("counter",counter);

        edit2.apply();



        ((TextView) layout.findViewById(R.id.message)).setText(message);
        layout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.left));
        // initialize your popupWindow and use your custom layout as the view
        final PopupWindow pw = new PopupWindow(layout,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // set windowType to TYPE_TOAST (requires API 23 above)
        // this will make popupWindow still appear even the activity was closed
        pw.setWindowLayoutType(WindowManager.LayoutParams.TYPE_TOAST);
        pw.showAtLocation(layout, Gravity.RIGHT | Gravity.TOP, 0, 500);
        pw.setAnimationStyle(R.anim.left);
        // handle popupWindow click event
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do anything when popupWindow was clicked
                pw.dismiss(); // dismiss the window
            }
        });
        // dismiss the popup window after 10 sec
        new Handler().postDelayed(new Runnable() {
            public void run() {
                pw.dismiss();
            }
        }, 10000);
    }


}

