package com.example.waseem.splash;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class dataBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout userHistory ;
    private String id , name , counts;
    private ArrayList<String> retrived  ;
    private  AlertDialog.Builder builder;

    public  Database tasbihaDataBase  ;
  //  private android.support.v7.app.ActionBar ab ;
    private TextView headerOfHistoryPage ;
    private Button options ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerOfHistoryPage = findViewById(R.id.headerOfHistoryPage);


        setContentView(R.layout.activity_data_base);

        userHistory = (TableLayout) findViewById(R.id.showLayout);
        retrived =new ArrayList<String>();
        retrived = getIntent().getStringArrayListExtra("userList");
        tasbihaDataBase = new Database(this);
        getUserInfo(retrived);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  ab = getSupportActionBar() ;

        ab.setLogo(R.drawable.islamic);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowTitleEnabled(false);*/





    }

    public TextView getHeaderOfHistoryPage() {
        return headerOfHistoryPage;
    }



    public  void getUserInfo(List retrived){

        for(int i = 0 ; i < retrived.size(); i++){
            String user =null ;
            user = retrived.get(i).toString();
         String [] sequencOfUser =  user.split(",");
            splitUser(sequencOfUser,i);


        }

    }




    @SuppressLint("ResourceType")

    public  void splitUser(String [] user, int i){

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
          final TableLayout.LayoutParams lparams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableRow row = new TableRow(this);

        row.setLayoutParams(lparams);
        row.setId(i);
        row.setDividerPadding(10);

        for(int j = 0 ; j < user.length ; j++){
            final TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);

            final TextView textView = new TextView(this);

            params.setMargins(5,5,5,20);
            textView.setLayoutParams(params);

            textView.setId(j);
           // textView.setText(user[j]);


            textView.setTextColor(Color.parseColor("#000000"));



            if(textView.getId()==0){

                textView.setText(user[j]);
                textView.setGravity(Gravity.LEFT);
               textView.setTextSize(20);

            }else if(textView.getId()==1){
                textView.setText(user[j] + "  TASBIHA");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(15);
            }


              row.addView(textView);

        }

        options = new Button(this);
        final TableRow.LayoutParams buttonparams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        buttonparams.setMargins(3,3,3,3);

        options.setLayoutParams(buttonparams);
        options.setText(R.string.optionsButton);
        options.setBackgroundColor(Color.parseColor("#FFF8DC"));
        options.setPadding(1,1,1,1);

        options.setId(i);
        options.setOnClickListener(this);

        row.addView(options);

        // set saving date of tasbihat inside the linearLayout
        TextView dateView = new TextView (this);

        dateView.setText("Date : "+ user[2]);
        dateView.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(row);

        linearLayout.addView(dateView);

        userHistory.addView(linearLayout, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }

    public Button getOptions() {
        return options;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
          final int id =  v.getId();







        String itemList[] = new String[] {getResources().getString(R.string.alerDialogcountinue) , getResources().getString(R.string.alerDialogDelete) };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alerDialogTitle);

        builder.setItems(itemList, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
// Continue
                    userContinue(id);

                }else if (which==1){
// Delet
                   Integer deletedRow = userDelete(id);


                   if(deletedRow>0){

                       Toast.makeText(getApplicationContext(), " One Record Deleted Successfuly "+ deletedRow, Toast.LENGTH_SHORT).show();

                   }else
                   {
                       Toast.makeText(getApplicationContext(), " No Data Available To Be Deleted ", Toast.LENGTH_SHORT).show();

                   }
                }

            }
        });

        builder.show();
    }

    private void userContinue(int id) {


         LinearLayout linearView=null ;
        TableRow rowView=null ;
        View rowView2=null ;
        String counterNumber = null ;
        String userName=null;
        int layoutViewCount = userHistory.getChildCount();



           if (userHistory != null)

           {

             //  linearView = userHistory.getChildAt(id);

               linearView = (LinearLayout) userHistory.getChildAt(id);

            rowView = (TableRow) linearView.getChildAt(0);

               TextView userView = (TextView) rowView.getChildAt(0);


               TextView countsView = (TextView) rowView.getChildAt(1);



                         userName = ((TextView) userView).getText().toString();
                         counterNumber =      ((TextView) countsView).getText().toString();

                    Toast.makeText(this, "counter"+ counterNumber, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "name " + userName, Toast.LENGTH_SHORT).show();


                    Intent moveBack = new Intent(dataBaseActivity.this,HomeActivity.class);
                    moveBack.putExtra("counterNumber",counterNumber);
                    moveBack.putExtra("userName",userName);
                    startActivity(moveBack);


                  }
           }







    private Integer userDelete(int id) {
        LinearLayout linearView=null ;
        TableRow rowView=null ;
        View rowView2=null ;
        String userName=null;
        int layoutViewCount = userHistory.getChildCount();
        if (userHistory != null) {

            linearView = (LinearLayout) userHistory.getChildAt(id);

            rowView = (TableRow) linearView.getChildAt(0);

            TextView userView = (TextView) rowView.getChildAt(0);

            userName = ((TextView) userView).getText().toString();

        }
         int num =tasbihaDataBase.deleteData(userName);
           LinearLayout   deletlinearView = (LinearLayout) userHistory.getChildAt(id);
         userHistory.removeView(deletlinearView);
         return num;
    }

    public Integer clearHistory (){

       int numberDeleted = tasbihaDataBase.clearHistory();
      //  TableLayout table = (TableLayout) findViewById(R.id.showLayout);

        userHistory.removeAllViews();
        return numberDeleted;
    }

}
