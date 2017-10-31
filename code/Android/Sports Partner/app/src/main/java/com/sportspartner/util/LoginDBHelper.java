package com.sportspartner.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by xc on 10/23/17.
 */

public class LoginDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Login.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SportPartnerDBContract.LoginDB.TABLE_NAME + " (" +
                    SportPartnerDBContract.LoginDB.COLUMN_EMAIL_NAME + " TEXT PRIMARY KEY," +
                    SportPartnerDBContract.LoginDB.COLUMN_KEY_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SportPartnerDBContract.LoginDB.TABLE_NAME;

    // implement the singleton pattern
    private static LoginDBHelper instance;

    private LoginDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static LoginDBHelper getInstance(Context context) {
        if (instance==null) {
            instance = new LoginDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    // main methods
    public boolean isLogedIn() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SportPartnerDBContract.LoginDB.COLUMN_EMAIL_NAME
        };

        Cursor cursor = db.query(
                SportPartnerDBContract.LoginDB.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        boolean logedin = cursor.getCount()==1;
        cursor.close();
        return logedin;
    }

    public void insert(String email, String key){
        this.delete();
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SportPartnerDBContract.LoginDB.COLUMN_EMAIL_NAME, email);
        values.put(SportPartnerDBContract.LoginDB.COLUMN_KEY_NAME, key);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SportPartnerDBContract.LoginDB.TABLE_NAME, null, values);
    }

    public void delete(){
        // clear the table
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SportPartnerDBContract.LoginDB.TABLE_NAME, null, null);
    }

    public String getEmail(){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SportPartnerDBContract.LoginDB.COLUMN_EMAIL_NAME
        };

        Cursor cursor = db.query(
                SportPartnerDBContract.LoginDB.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToLast();
        String email = cursor.getString(0);
        cursor.close();

        return email;
    }

    public String getKey(){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                SportPartnerDBContract.LoginDB.COLUMN_KEY_NAME
        };

        Cursor cursor = db.query(
                SportPartnerDBContract.LoginDB.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToLast();
        String key = cursor.getString(0);
        cursor.close();

        return key;
    }

    // implement methods from superclass
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}