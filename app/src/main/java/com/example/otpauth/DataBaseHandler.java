package com.example.otpauth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.otpauth.Util.ContactInfo;
import com.example.otpauth.Util.OTPDetailsDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactsDB";
    private static final String TABLE_Demo = "ContactsDB";
    private static final String KEY_MOBNO = "mobno";
    private static final String KEY_NAME= "contactname";

    private static final String TABLE_OTP_DETAILS = "OTPDetails";
    private static final String OTP_SENT= "otpsent";
    private static final String OTP_SENT_TIME= "otpsenttime";



    String CREATE_DEMO_TABLE = "CREATE TABLE " + TABLE_Demo +   "("+
            KEY_MOBNO + " TEXT  PRIMARY KEY,"+ KEY_NAME +  " TEXT" + ")";

    String CREATE_OTP_DETAIL_TABLE = "CREATE TABLE " + TABLE_OTP_DETAILS +   "("+
            KEY_MOBNO + " TEXT ,"+ OTP_SENT +  " TEXT," + OTP_SENT_TIME +  " TEXT "+")";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DEMO_TABLE);
        db.execSQL(CREATE_OTP_DETAIL_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Demo );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTP_DETAILS );
        onCreate(db);
    }

    void addJson(JSONObject json) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MOBNO,   json.getString("mobno"));
        values.put(KEY_NAME,json.getString("contactname"));
        db.insert(TABLE_Demo , null, values);
        db.close();
    }

    public List<ContactInfo> getAllContacts() {
       try {
        List<ContactInfo> contactList = new ArrayList<ContactInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Demo;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactInfo contact = new ContactInfo();
                contact.setPhoneNo(cursor.getString(0));
                contact.setContactName(cursor.getString(1));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;

       }
       catch (Exception e)
       {
           Log.e(TAG,"error in getAllContacts method");
           return null;
       }
    }



    void addOTPDetails(OTPDetailsDAO otpDetailsDAO) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MOBNO,   otpDetailsDAO.getOtpSentMobNo());
        values.put(OTP_SENT, otpDetailsDAO.getOtpSent());
        values.put(OTP_SENT_TIME, otpDetailsDAO.getOtpSentTime());
        db.insert(TABLE_OTP_DETAILS , null, values);
        db.close();
    }



    public List<OTPDetailsDAO> getAllOtpDetailsSent() {
        try {
            List<OTPDetailsDAO> contactListOTP = new ArrayList<OTPDetailsDAO>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_OTP_DETAILS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    OTPDetailsDAO otpObj = new OTPDetailsDAO();
                    otpObj.setOtpSentMobNo(cursor.getString(0));
                    otpObj.setOtpSent(cursor.getString(1));
                    otpObj.setOtpSentTime(cursor.getString(2));

                    // Adding contact to list
                    contactListOTP.add(otpObj);
                } while (cursor.moveToNext());
            }

            // return contact list
            return contactListOTP;

        }
        catch (Exception e)
        {
            Log.e(TAG,"error in getAllContacts method");
            return null;
        }
    }

}
