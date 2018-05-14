package com.example.waseem.splash;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class athkar extends AppCompatActivity implements View.OnClickListener {


    private Button athkarMorning;
    private Button athkarEveninig;
    private Button athkarEntryMosque;
    private Button athkarPrayer;
    private Button athkarLivelhood;
    private Button athkarSleep;
    private Button athkarWakeup;
    private Button athkarEntryHome;
    private Button athkarLeavingHome;
    private Map athkarRep;
    private  Fragment frag ;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athkar);
        athkarMorning = findViewById(R.id.morningAthkar);
        athkarEveninig = findViewById(R.id.eveningAthkar);
        athkarEntryMosque = findViewById(R.id.mosqueAthkar);
        athkarPrayer = findViewById(R.id.prayerAthkar);
        athkarLivelhood = findViewById(R.id.livelihood);
        athkarSleep = findViewById(R.id.sleepAthkar);
        athkarWakeup = findViewById(R.id.wakeupAthkar);
        athkarEntryHome= findViewById(R.id.entryHomeAthkar);
        athkarLeavingHome= findViewById(R.id.leavingHomeAthkar);

        athkarMorning.setOnClickListener(this);
        athkarEveninig.setOnClickListener(this);
        athkarEntryMosque.setOnClickListener(this);
        athkarPrayer.setOnClickListener(this);
        athkarLivelhood.setOnClickListener(this);
        athkarSleep.setOnClickListener(this);
        athkarWakeup.setOnClickListener(this);
        athkarEntryHome.setOnClickListener(this);
        athkarLeavingHome.setOnClickListener(this);

         frag = new Fragment();
        toolbar = findViewById(R.id.toolbarAthkar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void getAthkarWithRep(String[] athkarList) {
        athkarRep = new HashMap<String, String>();
        for (int i = 0; i < athkarList.length; i++) {
            String thikr[] = athkarList[i].split("-");
            String rep = thikr[0];
            String Text = thikr[1];
            athkarRep.put(Text, rep);

        }

    }



    public  void setfragment(String title){

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle bundel = new Bundle();
        bundel.putSerializable("was", (Serializable) athkarRep);
        bundel.putString("title",title);
        frag.setArguments(bundel);

        transaction.add(R.id.athkarActivity,frag,"was");

        transaction.addToBackStack(null).commit();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        int currentViewId = v.getId();

        switch (currentViewId){

            case R.id.morningAthkar:
                String[] athkarArrayList = getResources().getStringArray(R.array.MorningAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list1_));

                break;

            case R.id.eveningAthkar:
                 athkarArrayList = getResources().getStringArray(R.array.EveningAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list2_));

                break;

            case R.id.mosqueAthkar:

                athkarArrayList = getResources().getStringArray(R.array.MosqueAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list3_));
                break;
            case R.id.entryHomeAthkar:
                athkarArrayList = getResources().getStringArray(R.array.entryHomeAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list6_));

                break;

            case R.id.leavingHomeAthkar:
                athkarArrayList = getResources().getStringArray(R.array.LeavingHomeAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list7_));

                break;

            case R.id.prayerAthkar:

                athkarArrayList = getResources().getStringArray(R.array.afterPrayergAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list8_));
                break;

            case R.id.sleepAthkar:
                athkarArrayList = getResources().getStringArray(R.array.SleepAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list4_));

                break;

            case R.id.livelihood:

                athkarArrayList = getResources().getStringArray(R.array.livelhoodAthkar);
                getAthkarWithRep(athkarArrayList);
                setfragment(getResources().getString(R.string.list9_));
                break;

            case R.id.wakeupAthkar:

            athkarArrayList = getResources().getStringArray(R.array.WakeUpAthkar);
            getAthkarWithRep(athkarArrayList);
            setfragment(getResources().getString(R.string.list5_));
            break;


            default:
                Toast.makeText(this, ""+getResources().getString(R.string.ErrorMsg), Toast.LENGTH_SHORT).show();

        }

    }
}



