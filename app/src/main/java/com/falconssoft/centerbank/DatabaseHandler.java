package com.falconssoft.centerbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.falconssoft.centerbank.Models.NewAccount;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 0;
    private static final String BD_NAME = "cheque_editor";

    // ********************************************************************
    private final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    private final String ACCOUNT_ID = "ACCOUNT_ID";
    private final String ACCOUNT_BANK = "ACCOUNT_BANK";
    private final String ACCOUNT_BANK_NO = "ACCOUNT_BANK_NO";

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNewAccount(NewAccount newAccount){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BANK_NO, newAccount.getAccountNo());
        contentValues.put(ACCOUNT_BANK, newAccount.getBank());

        database.insert(ACCOUNT_TABLE, null, contentValues);
        database.close();

    }
}
