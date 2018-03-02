package com.example.waseem.splash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Waseem on 1/7/2018.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "counter.db";
    private static final String TABLE_NAME = "Counter";
    //private static final String UID = "-id";
    private static final String NAMEE = "NAME";
    private static final String status = "COUNTERSTATUS";
    private static final String Date = "DATE";
    private static final int DataBase_Version = 1;
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     *
     */
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DataBase_Version);


    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (Name TEXT,COUNTERSTATUS TEXT,DATE TEXT)");

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean saveCounterStateWithUserName(String user, String counts){

        String saveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();


        contentvalues.put(Date,saveDate);
        contentvalues.put(status,counts);
        contentvalues.put(NAMEE,user);




       long result = db.insert(TABLE_NAME,null,contentvalues);
        if(result== -1)

            return false;
        else
            return true;
    }


    public boolean updateCurrentUser(String user, String counts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();


        contentvalues.put(status,counts);
        String[]  whereArgs ={user};
        int updateResult =db.update(TABLE_NAME,contentvalues,NAMEE+" =? ", whereArgs);
        if(updateResult== -1)

            return false;
        else
            return true;

    }

    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor back = db.rawQuery("select * from "+TABLE_NAME,null);

return back ;

    }






    public Integer deleteData(String userName){

        SQLiteDatabase db = this.getWritableDatabase();
         int num = db.delete(TABLE_NAME, NAMEE+ "=?", new String[] {userName});


        return  num;

    }

    public Integer clearHistory (){
        SQLiteDatabase db = this.getWritableDatabase();
         int number =   db.delete(TABLE_NAME,"1",null);
        return number;
    }



}
