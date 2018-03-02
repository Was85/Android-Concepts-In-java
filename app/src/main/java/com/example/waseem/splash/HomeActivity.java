package com.example.waseem.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener {
    private Toolbar toolbar ;
    private Locale myLocale ;
    private String nameText = null;
    public  Database athkarDataBase;
    private dataBaseActivity database ;
    private int counter = 0;
    private TextView outPut;
    private String tasbihaNumber = null;
    private ImageView incrmentTouch;
    private ImageView reset;
    private Button saveTasbeha;
    private Button load;
    private Button clear;
    private Button athkar;
    private String retrivedCountsForUser;
    private String retrivedUserName;
    private boolean updateDone;
  //  private android.support.v7.app.ActionBar ab ;
    private MainActivity main ;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        main = new MainActivity() ;


        database = new dataBaseActivity();
        updateDone =false ;
        athkarDataBase = new Database(this);

        incrmentTouch = (ImageView) findViewById(R.id.increment);
        outPut = (TextView) findViewById(R.id.Screen);
        reset = (ImageView) findViewById(R.id.reset);
        saveTasbeha = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear);
        athkar = findViewById(R.id.athkar);
        incrmentTouch.setOnClickListener(this);
        reset.setOnClickListener(this);
        saveTasbeha.setOnClickListener(this);
        load.setOnClickListener(this);
        clear.setOnClickListener(this);
        athkar.setOnClickListener(this);

        loadlocal();
        userCountinue();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lanuage , menu );

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String lang = "en";
        switch (item.getItemId()){


            case R.id.EN :
                lang = "en" ;

                Toast.makeText(this, "English ", Toast.LENGTH_SHORT).show();
                changeLang(lang);

                return  true ;
                
            case R.id.AR:

                lang = "AR" ;

                Toast.makeText(this, "Arabic", Toast.LENGTH_SHORT).show();
                changeLang(lang);

                return  true ;
                default:
                    changeLang(lang);
                return super.onOptionsItemSelected(item);
        }




    }
    private void loadlocal() {

        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);

    }

    public  void vibrateOnclick (){


        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);


    }


    private void changeLang(String lang) {

        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();


    }

    private void updateTexts() {
//        main.getTextView().setText(R.string.StartUpHeaderName);
        saveTasbeha.setText(R.string.saveYourTasbiha);
        load.setText(R.string.TasbihHistory);
        clear.setText(R.string.ClearHistory);


    }

    private void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();

    }

    public  void userCountinue (){
        if (getIntent().getExtras() != null) {
            retrivedCountsForUser = getIntent().getStringExtra("counterNumber");
            retrivedUserName = getIntent().getStringExtra("userName");



            String counters = retrivedCountsForUser.replaceAll("[A-z]+"," ").trim();
            outPut.setText(counters);


          counter = Integer.parseInt(counters);
            Toast retrivedInfo = Toast.makeText(this, " Welcome Back " + retrivedUserName +" Your last Tasbeha is "+ retrivedCountsForUser, Toast.LENGTH_LONG);
            retrivedInfo.setGravity(Gravity.TOP | Gravity.CENTER,0,0);
            retrivedInfo.show();
        }

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.increment) {
            vibrateOnclick();
            counter++;
            tasbihaNumber = Integer.toString(counter);
            outPut.setText(tasbihaNumber);
        }

        if (v.getId() == R.id.reset) {
            counter = 0;
            outPut.setText(Integer.toString(counter));
        }


        if (v.getId() == R.id.save) {

            if (tasbihaNumber == null) {
                Toast.makeText(this, " Please Start Tasbih First ... ", Toast.LENGTH_LONG).show();
            } else {


                NameOfTheUser();
            }


        }

        if(v.getId()== R.id.athkar){

            Intent athkar = new Intent(HomeActivity.this, athkar.class);
            // finish();



            startActivity(athkar);

        }

        if(v.getId()==R.id.clear){


            int rowDeleted = clearHistory();

            if(rowDeleted > 0){
                Toast.makeText(this, "History Cleared.."+rowDeleted+ "Records Deleted successfully ", Toast.LENGTH_SHORT).show();
            }else if(rowDeleted==0)
            {
                Toast.makeText(this, "No History Available To be Deleted ! ", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.load) {

            Cursor collectedData = athkarDataBase.getData();

            getDataAndPassToNewActivity(collectedData);

        }
    }
// check if there are any history to be display
    public void getDataAndPassToNewActivity(Cursor collectedData) {

        if (collectedData.getCount() == 0) {
            Toast.makeText(this, "No Saved Date To Display", Toast.LENGTH_SHORT).show();
        }

        ArrayList<String> savedAthkarList = new ArrayList<>();

        while (collectedData.moveToNext()) {

            String user = collectedData.getString(0) + "," + collectedData.getString(1) + "," + collectedData.getString(2);

            savedAthkarList.add(user);

        }

        Intent dataBaseIntent = new Intent(HomeActivity.this, dataBaseActivity.class);
        // finish();

        dataBaseIntent.putStringArrayListExtra("userList", savedAthkarList);

        startActivity(dataBaseIntent);
    }

    // pop up Alert Dialog when click on save button and saved the Entered Named to string

       public void NameOfTheUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" Enter Your Name ");

//     Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text


            input.setInputType(InputType.TYPE_CLASS_TEXT);

            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nameText = input.getText().toString();
                    if(nameText.trim().length()==0){
                        Toast.makeText(HomeActivity.this, " Enter Name Of User First ...", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        //////
                        String UserName =null;
                        Cursor dataBaseTable = athkarDataBase.getData();

                        for(int i = 0 ; i <= dataBaseTable.getCount();i++){

                            if(dataBaseTable.moveToFirst()){
                                UserName = dataBaseTable.getString(0);
                                if(UserName.equals(nameText)){
                                    Boolean savingState = athkarDataBase.updateCurrentUser(nameText, tasbihaNumber);
                                    if (savingState) {
                                        Toast confirm = Toast.makeText(getApplicationContext(), " User Status Updated Successfully ", Toast.LENGTH_SHORT);
                                        confirm.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                                        confirm.show();
                                        updateDone=true;

                                    }
                                }
                            }


                        }



                        ///////

                    if(!(updateDone)){
                        Boolean savingState = athkarDataBase.saveCounterStateWithUserName(nameText, tasbihaNumber);
                        if (savingState) {
                            Toast confirm = Toast.makeText(getApplicationContext(), " Added Successfully ", Toast.LENGTH_SHORT);
                            confirm.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                            confirm.show();
                        }
                    }


                }
            }});
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }

    private Integer clearHistory(){

          return  athkarDataBase.clearHistory();




    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("counter",counter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter =  savedInstanceState.getInt("counter");
        String retrivedvalue = Integer.toString(counter);
        outPut = (TextView) findViewById(R.id.Screen);
        outPut.setText(retrivedvalue);

    }




}
