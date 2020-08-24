package com.example.learnswedish.sqldb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AndroidRuntimeException;
import android.util.Log;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;


public class DBController extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public static String tableName = "TableV1";
    public static String colSwedish = "Swedish";
    public static String colEnglish = "English";

    public DBController(Context applicationcontext) {

        super(applicationcontext, "mydb.db", null, 1); // creating DATABASE

        Log.d(LOGCAT, "Created");

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String query;

        query = "CREATE TABLE IF NOT EXISTS " + tableName + "( " + colSwedish + " TEXT, " + colEnglish + " TEXT, PRIMARY KEY( " + colSwedish + "," + colEnglish + "))" ;
        Log.e("create Query", query);
        database.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tableName;
        database.execSQL(query);
        onCreate(database);
    }

    public void insertWords(SQLiteDatabase database, String word1, String word2){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(colSwedish, word1);
            contentValues.put(colEnglish, word2);
            database.insertOrThrow(tableName, null, contentValues);
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }

    }

    public void deleteAll(SQLiteDatabase database)
    {Cursor mCursor = database.rawQuery("SELECT * FROM " + tableName, null);
    if(mCursor.getCount() == 0){throw new EmptyStackException();}
    else{database.delete(tableName, null, null);
    database.execSQL("delete from " + tableName);
        }
    mCursor.close();
    }


    public ArrayList<HashMap<String, String>> getAllProducts() {

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
//Id, Company,Name,Price
                HashMap<String, String> map = new HashMap<>();
                map.put("a", cursor.getString(0));
                map.put("b", cursor.getString(1));
                wordList.add(map);
                Log.e("dataofList", cursor.getString(0) + "," + cursor.getString(1) );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordList;

    }

}