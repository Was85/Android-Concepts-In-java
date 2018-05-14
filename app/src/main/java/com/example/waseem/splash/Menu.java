package com.example.waseem.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button athkarButton;
    private Button TasbihButton;
    private Button autoAthkarButton;
    private Button IslamicGuidence;
    private Toolbar toolbar;
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        athkarButton = findViewById(R.id.Athkary);
        TasbihButton = findViewById(R.id.Tasbih);
        autoAthkarButton = findViewById(R.id.AthkarAndIstighfar);
        IslamicGuidence = findViewById(R.id.islamicGuidence);
        athkarButton.setOnClickListener(this);
        TasbihButton.setOnClickListener(this);
        autoAthkarButton.setOnClickListener(this);
        IslamicGuidence.setOnClickListener(this);

        toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        loadlocal();
    }

    private void defaultSetting() {


    }
    //////
    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link android.view.Menu#CATEGORY_SYSTEM} group so that
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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
         getMenuInflater().inflate(R.menu.menu_lanuage, menu);

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
        switch (item.getItemId()) {
            case R.id.EN:
                lang = "en";
                Toast.makeText(this, "English ", Toast.LENGTH_SHORT).show();
                changeLang(lang);
                return true;
            case R.id.AR:
                lang = "AR";
                Toast.makeText(this, "Arabic", Toast.LENGTH_SHORT).show();
                changeLang(lang);
                return true;
            case R.id.setting:
                SharedPreferences sharedPrefs = getSharedPreferences("com.example.xyle", MODE_PRIVATE);


                Intent settingIntent =  new Intent(this,SettingActivity.class);

                startActivity(settingIntent);
                return true;
            default:
                changeLang(lang);
                return super.onOptionsItemSelected(item);
        }


    }



    //////
    private void loadlocal() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");

        changeLang(language);

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
        athkarButton.setText(R.string.AvailableList);
        //info.setText(R.string.Aut);
        TasbihButton.setText(R.string.Counter);
        IslamicGuidence.setText(R.string.guidanceButton);

    }

    private void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int clickedView = v.getId();
        switch (clickedView) {
            case R.id.Athkary:

                Intent athkar = new Intent(Menu.this, athkar.class);
                startActivity(athkar);


                break;

            case R.id.Tasbih:
                Intent intent = new Intent(Menu.this,HomeActivity.class);
                startActivity(intent);

                break;


            case R.id.AthkarAndIstighfar:
                Intent autoAthkar = new Intent(Menu.this,Auto_Athkar.class);
                startActivity(autoAthkar);
                break;


            case 1:

                Intent Guidanace = new Intent(Menu.this,Guidance.class);
                startActivity(Guidanace);


                break;
                
                default:
                    Toast.makeText(this, "Nothing Here Yet ", Toast.LENGTH_SHORT).show();
        }
    }
}
