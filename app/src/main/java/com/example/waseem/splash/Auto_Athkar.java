package com.example.waseem.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Auto_Athkar extends AppCompatActivity {

    private ListView auto_listView;
    private String[] autoAthkarArray;
    private AutoAthkarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto__athkar);
        auto_listView = findViewById(R.id.auto_athkarlist);

        autoAthkarArray = getResources().getStringArray(R.array.popUp_Athkar);

        adapter = new AutoAthkarAdapter(this, autoAthkarArray);
        auto_listView.setAdapter(adapter);
        Toast.makeText(this, "view done", Toast.LENGTH_SHORT).show();
adapter.defaultPopList(auto_listView);
    }




}

class AutoAthkarAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    private String[] autoAthkarList;
    private ListView auto_listView;
    private  Context context;
    private  int pos;
    private ArrayList<String> selectedPopList;
    private LayoutInflater inflater ;
    private View row ;
    private ArrayList<Integer> listIndexToBeCheckedOnStart ;

 // Constructor
    public AutoAthkarAdapter(Context c, String[] autoList) {
        context = c;
        autoAthkarList = autoList;
        selectedPopList = new ArrayList<>();


        listIndexToBeCheckedOnStart = new ArrayList<>();
    }

    public List<String> getSelectedPopList() {
        return selectedPopList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return autoAthkarList.length;
    }


    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return autoAthkarList[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

         inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         row = inflater.inflate(R.layout.auto_athkar_list, parent, false);
        auto_listView = parent.findViewById(R.id.auto_athkarlist);
         TextView autoThikrText = row.findViewById(R.id.autoThikr);
         CheckBox markedThikr = row.findViewById(R.id.checkedThikr);
         markedThikr.setOnCheckedChangeListener(this);
         autoThikrText.setText(autoAthkarList[position]);

         return row;
    }


    public int getPos() {
        return pos;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

         pos = auto_listView.getPositionForView(buttonView);

        if (pos != auto_listView.INVALID_POSITION && isChecked) {
            buttonView.setSelected(isChecked);

            TextView textcheck= auto_listView.getChildAt(pos).findViewById(R.id.autoThikr);
            if(textcheck!= null){
                selectedPopList.add(textcheck.getText().toString());
                Toast.makeText(context, "ListUpdated ", Toast.LENGTH_SHORT).show();
                //Set the values

                updateListWithJsonHelp(selectedPopList);


            }


        }else if(pos != auto_listView.INVALID_POSITION && (!(isChecked))) {

            buttonView.setSelected(isChecked);
            TextView textcheck= (TextView)auto_listView.getChildAt(pos).findViewById(R.id.autoThikr);
            if(textcheck!= null){
                selectedPopList.remove(pos);
                Toast.makeText(context, "ListUpdated ! one recorde deleted", Toast.LENGTH_SHORT).show();
                updateListWithJsonHelp(selectedPopList);
            }

        }

    }
    public void updateListWithJsonHelp (ArrayList <String >selectedPopList){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();

        String stringList = "";

        for(String text : selectedPopList){

            stringList+=text + ",";
         }
              mEdit1.putString("list",stringList);

              mEdit1.apply();

    }



    public void defaultPopList(ListView auto_listView){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        String stringList = sp.getString("list",null);

        if(stringList != null && (!(stringList.isEmpty()))){


            ArrayList<String> popList = new ArrayList<String>(Arrays.asList(stringList.split(",")));

            Toast.makeText(context, "size "+ popList.size(), Toast.LENGTH_SHORT).show();
            for(int i = 0 ; i < popList.size();i++){

                String textToBeChecked = popList.get(i);
                for ( int j = 0 ; j < auto_listView.getCount() ; j++){
                    View v = getViewByPosition(j,auto_listView);
                  TextView s = (TextView) v.findViewById(R.id.autoThikr);
                    Toast.makeText(context, "???  "+s.getText().toString() , Toast.LENGTH_SHORT).show();
                }


                    }
                }

            }

      public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition =firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
         }
        }







