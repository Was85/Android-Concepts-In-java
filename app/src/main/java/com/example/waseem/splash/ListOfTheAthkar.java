package com.example.waseem.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

public class ListOfTheAthkar extends AppCompatActivity {

    private listviewAdapter adapter ;
    private HashMap<String, String> athkarRep;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_the_athkar);


        RecyclerView recView = findViewById(R.id.recView);
        Intent intent = getIntent();
        athkarRep = ((HashMap<String, String>) intent.getSerializableExtra("hash"));





         adapter = new listviewAdapter(athkarRep, this);



        recView.setAdapter(adapter);
        DefaultItemAnimator a= new DefaultItemAnimator() ;
        recView.setItemAnimator(a);
        a.setAddDuration(500);
        a.setRemoveDuration(500);
        a.runPendingAnimations();
        recView.setLayoutManager(new LinearLayoutManager(this));





    }


}
