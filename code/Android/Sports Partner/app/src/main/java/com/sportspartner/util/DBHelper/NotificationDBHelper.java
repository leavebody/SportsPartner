package com.sportspartner.util.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sportspartner.models.FriendRequestNotification;
import com.sportspartner.models.JoinSANotification;
import com.sportspartner.models.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yujiaxiao on 11/11/17.
 */

public class NotificationDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notification.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SportPartnerDBContract.NotificationDB.TABLE_NAME + " (" +
                    SportPartnerDBContract.NotificationDB.COLUMN_ID + " TEXT PRIMARY KEY," +
                    SportPartnerDBContract.NotificationDB.COLUMN_TITLE_NAME + " TEXT," +
                    SportPartnerDBContract.NotificationDB.COLUMN_DETAIL_NAME + " TEXT," +
                    SportPartnerDBContract.NotificationDB.COLUMN_SENDER_NAME + " TEXT," +
                    SportPartnerDBContract.NotificationDB.COLUMN_TYPE_NAME + " TEXT," +
                    SportPartnerDBContract.NotificationDB.COLUMN_TIME_NAME + " TEXT," +
                    SportPartnerDBContract.NotificationDB.COLUMN_PRIORITY_NAME + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SportPartnerDBContract.NotificationDB.TABLE_NAME;

    // implement the singleton pattern
    private static NotificationDBHelper instance;

    private NotificationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NotificationDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * get all records
     * @return all record
     */
    public ArrayList<Notification> getAll() throws ParseException {
        ArrayList<Notification> allNoti = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ SportPartnerDBContract.NotificationDB.TABLE_NAME + " ORDER BY " + SportPartnerDBContract.NotificationDB.COLUMN_PRIORITY_NAME + " ASC", null);

        if(cursor.moveToFirst())

        {
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_TITLE_NAME));
                String detail = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_DETAIL_NAME));
                String sender = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_SENDER_NAME));
                String type = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_TYPE_NAME));
                String time = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_TIME_NAME));
                int priority = cursor.getInt(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_PRIORITY_NAME));

                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

                Date date = format.parse(time);
                if((priority==1)&&(type.equals("INTERACTION"))) {
                    allNoti.add(new FriendRequestNotification(uuid, title, detail, sender, type, date, priority, false));
                }else if((priority==2)&&(type.equals("INTERACTION"))) {
                    allNoti.add(new JoinSANotification(uuid, title, detail, sender, type, date, priority, false));
                }else{
                    allNoti.add(new Notification(uuid, title, detail, sender, type, date, priority, false));
                }
                cursor.moveToNext();
            }
        }
        return allNoti;
    }

    public ArrayList<String> getAllUUID() throws ParseException {
        ArrayList<String> allNoti = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + SportPartnerDBContract.NotificationDB.COLUMN_ID + " from "+ SportPartnerDBContract.NotificationDB.TABLE_NAME + " ORDER BY " + SportPartnerDBContract.NotificationDB.COLUMN_PRIORITY_NAME + " ASC", null);

        if(cursor.moveToFirst())

        {
            while (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NotificationDB.COLUMN_ID));

                allNoti.add(uuid);
                cursor.moveToNext();
            }
        }
        return allNoti;
    }

    /**
     * Insert a new notification record.
     * @param uuid the notification UUID
     * @param title the notification title
     * @param detail the notification detail
     * @param sender the dender of the notification
     * @param type the type of the notification
     * @param time the time of the notification
     * @param priority the priority of the notification
     */
    public void insert(String uuid, String title, String detail, String sender, String type, String time, int priority){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_ID, uuid);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_TITLE_NAME, title);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_DETAIL_NAME, detail);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_SENDER_NAME, sender);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_TYPE_NAME, type);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_TIME_NAME, time);
        values.put(SportPartnerDBContract.NotificationDB.COLUMN_PRIORITY_NAME, priority);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SportPartnerDBContract.NotificationDB.TABLE_NAME, null, values);
    }

    /**
     * delete record by UUID
     * @param uuid
     */
    public void deleteById(String uuid){
        String[] arg = new String[1];
        arg[0] = uuid;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SportPartnerDBContract.NotificationDB.TABLE_NAME, SportPartnerDBContract.NotificationDB.COLUMN_ID + "=? ", arg);
    }

    /**
     * Clear the  records.
     */
    public void deleteAllrows(){
        // clear the table
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SportPartnerDBContract.NotificationDB.TABLE_NAME, null, null);
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
