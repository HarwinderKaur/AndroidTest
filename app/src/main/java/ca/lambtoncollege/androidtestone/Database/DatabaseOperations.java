package ca.lambtoncollege.androidtestone.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ca.lambtoncollege.androidtestone.Recipe;


public class DatabaseOperations extends SQLiteOpenHelper {


    public DatabaseOperations(Context context) {
        super(context, TableData.Tableinfo.DATABASE_NAME, null, database_version);
    }

    public static final int database_version = 2;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.Tableinfo.TABLE_NAME + "(" + TableData.Tableinfo.publisher + " TEXT," +TableData.Tableinfo.f2f_url + " TEXT,"+TableData.Tableinfo.title + " TEXT," + TableData.Tableinfo.source_url + " TEXT," +TableData.Tableinfo.recipe_id+" TEXT,"+ TableData.Tableinfo.publisher_url+" TEXT,"+ TableData.Tableinfo.image_url+" TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);

        Log.d("Database operations", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableData.Tableinfo.TABLE_NAME);
        onCreate(db);
    }

    public void putInformation(DatabaseOperations dop, String publisher, String f2f_url, String title, String source_url, String recipe_id, String publisher_url, String image_url)

    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.Tableinfo.publisher, publisher);
        cv.put(TableData.Tableinfo.f2f_url, f2f_url);
        cv.put(TableData.Tableinfo.title, title);
        cv.put(TableData.Tableinfo.source_url, source_url);
        cv.put(TableData.Tableinfo.recipe_id, recipe_id);
        cv.put(TableData.Tableinfo.publisher_url, publisher_url);
        cv.put(TableData.Tableinfo.image_url, image_url);
        long k = SQ.insert(TableData.Tableinfo.TABLE_NAME, null, cv);
        Log.d("Database Created", "true");

    }



    public ArrayList<Recipe> readData(DatabaseOperations dop) {
        ArrayList<Recipe> listData = new ArrayList<>();
        SQLiteDatabase SQ = dop.getReadableDatabase();

        Log.d("DatabasRead","");
//        String[] coloumns = {TableData.Tableinfo.CAB_NO, TableData.Tableinfo.TIME,TableData.Tableinfo.USER_ID,TableData.Tableinfo.PICKUP_LOCATION,TableData.Tableinfo.TIMETOSTART,};
//        Cursor cursor = SQ.query(TableData.Tableinfo.TABLE_NAME, coloumns, null, null, null, null, null);
        Cursor cursor = SQ.rawQuery("SELECT * from " + TableData.Tableinfo.TABLE_NAME + " ORDER BY " + TableData.Tableinfo.recipe_id + " ASC", null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                //create a new movie object
                // and retrieve the data from the cursor to be stored in this movie object
                Recipe item = new Recipe();
                item.setPublisher(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.publisher)));
                item.setF2fUrl(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.f2f_url)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.title)));
                item.setSourceUrl(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.source_url)));
                item.setPublisherUrl(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.recipe_id)));
                item.setPublisherUrl(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.publisher_url)));
                item.setImageUrl(cursor.getString(cursor.getColumnIndex(TableData.Tableinfo.image_url)));
                listData.add(item);
                Log.d("Database read", "true");
            }
            while (cursor.moveToNext());
        }
        return listData;
    }

    public void deleteRow(DatabaseOperations dop,String id)
    {

        SQLiteDatabase SQ = dop.getWritableDatabase();
            SQ.delete(TableData.Tableinfo.TABLE_NAME, TableData.Tableinfo.recipe_id+"="+id, null);


    }

    public  void eraseData(DatabaseOperations dop){
        SQLiteDatabase db = dop.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TableData.Tableinfo.TABLE_NAME, null, null);
        Log.d("Database Erased", "true");
    }

    public String getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TableData.Tableinfo.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        String count = Integer.toString(cnt);
        return count;
    }
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.

    }


}