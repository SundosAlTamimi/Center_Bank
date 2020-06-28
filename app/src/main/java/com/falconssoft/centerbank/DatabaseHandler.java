package com.falconssoft.centerbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.Setting;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String BD_NAME = "cheque_editor";

    // ********************************************************************
    private final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    private final String ACCOUNT_ID = "ACCOUNT_ID";
    private final String ACCOUNT_BANK = "ACCOUNT_BANK";
    private final String ACCOUNT_BANK_NO = "ACCOUNT_BANK_NO";

    // ********************************************************************

    private final String SETTING_TABLE = "SETTING_TABLE";
    private final String SETTING_IP = "SETTING_IP";


    // ********************************************************************

    private final String LOGIN_TABLE = "LOGIN_TABLE";
    private final String USER_NAME = "USER_NAME";
    private final String PASSWORD = "PASSWORD";


    // ********************************************************************

    public DatabaseHandler(@Nullable Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAccounts = "CREATE TABLE " + ACCOUNT_TABLE
                + " ("
                + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACCOUNT_BANK + " TEXT, "
                + ACCOUNT_BANK_NO + " TEXT "
                + ")";
        db.execSQL(createTableAccounts);

        String createTableSetting = "CREATE TABLE " + SETTING_TABLE
                + " ("
                + SETTING_IP + " TEXT "
                + ")";
        db.execSQL(createTableSetting);
        String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
                + " ("
                + USER_NAME + " TEXT,"
                + PASSWORD + " TEXT "
                + ")";
        db.execSQL(createTableLOGIN);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{}catch (Exception e){

            String createTableSetting = "CREATE TABLE " + SETTING_TABLE
                    + " ("
                    + SETTING_IP + " TEXT "
                    + ")";
            db.execSQL(createTableSetting);
            Log.e("Setting","found");

            String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
                    + " ("
                    + USER_NAME + " TEXT,"
                    + PASSWORD + " TEXT "
                    + ")";
            db.execSQL(createTableLOGIN);
        }

    }

    public void addNewAccount(NewAccount newAccount){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BANK_NO, newAccount.getAccountNo());
        contentValues.put(ACCOUNT_BANK, newAccount.getBank());

        database.insert(ACCOUNT_TABLE, null, contentValues);
        database.close();

    }
    public void addLoginInfo(LoginINFO loginINFO){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, loginINFO.getUsername());
        contentValues.put(PASSWORD, loginINFO.getPassword());


        database.insert(LOGIN_TABLE, null, contentValues);
        database.close();

    }
    public LoginINFO getLoginInfo() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + LOGIN_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        LoginINFO user = new LoginINFO();
        if (cursor.moveToFirst()) {
            do {

                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                Log.e("user",""+cursor.getString(0)+cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return user;
    }
    public void deleteLoginInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + LOGIN_TABLE);
        db.close();
    }
    public void addSetting(Setting setting){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SETTING_IP, setting.getIp());


        database.insert(SETTING_TABLE, null, contentValues);
        database.close();

    }

    public Setting getAllSetting() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + SETTING_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        Setting item = new Setting();
        if (cursor.moveToFirst()) {
            do {

                item.setIp(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return item;
    }



    public void deleteAllSetting()
    {
//             Idb.execSQL("DELETE FROM "+tableName); //delete all rows in a table
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + SETTING_TABLE);
        db.close();
    }


}
