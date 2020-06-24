package com.falconssoft.centerbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.Setting;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 2;
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
