package com.falconssoft.centerbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.NewAccount;
import com.falconssoft.centerbank.Models.Setting;
import com.falconssoft.centerbank.viewmodel.SignupVM;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 22;
    private static final String BD_NAME = "cheque_editor";

    // ********************************************************************
    private final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    private final String ACCOUNT_ID = "ACCOUNT_ID";
    private final String ACCOUNT_BANK = "ACCOUNT_BANK";
    private final String ACCOUNT_BANK_NO = "ACCOUNT_BANK_NO";
    private final String ACCOUNT_STATUS = "ACCOUNT_STATUS";

    // ********************************************************************
    private final String SETTING_TABLE = "SETTING_TABLE";
    private final String SETTING_IP = "SETTING_IP";

    // ********************************************************************
    private final String LOGIN_TABLE = "LOGIN_TABLE";
    private final String USER_NAME = "USER_NAME";
    private final String PASSWORD = "PASSWORD";
    private final String LOGIN_NATIONAL_ID = "LOGIN_NATIONAL_ID";
    private final String LOGIN_FIRST_NAME = "LOGIN_FIRST_NAME";
    private final String LOGIN_SECOND_NAME = "LOGIN_SECOND_NAME";
    private final String LOGIN_THIRD_NAME = "LOGIN_THIRD_NAME";
    private final String LOGIN_FOURTH_NAME = "LOGIN_FOURTH_NAME";
    private final String LOGIN_DOB = "LOGIN_DOB";
    private final String LOGIN_GENDER = "LOGIN_GENDER";
    private final String LOGIN_ADDRESS = "LOGIN_ADDRESS";
    private final String LOGIN_EMAIL = "LOGIN_EMAIL";
    private final String LOGIN_INACTIVE = "LOGIN_INACTIVE";
    private final String LOGIN_INDATE = "LOGIN_INDATE";
    private final String LOGIN_REMEMBER = "LOGIN_REMEMBER";
    private final String LOGIN_ACTIVE_NOW = "LOGIN_ACTIVE_NOW"; // 1 for the user log in , 0 fot other accounts

    // ********************************************************************
//    private final String SIGNUP_TABLE = "SIGNUP_TABLE";
//    private final String SIGNUP_NATIONAL_ID = "SIGNUP_NATIONAL_ID";
//    private final String SIGNUP_FIRST_NAME = "SIGNUP_FIRST_NAME";
//    private final String SIGNUP_SECOND_NAME = "SIGNUP_SECOND_NAME";
//    private final String SIGNUP_THIRD_NAME = "SIGNUP_THIRD_NAME";
//    private final String SIGNUP_FOURTH_NAME = "SIGNUP_FOURTH_NAME";
//    private final String SIGNUP_DOB = "SIGNUP_DOB";
//    private final String SIGNUP_GENDER = "SIGNUP_GENDER";
//    private final String SIGNUP_MOBILE = "SIGNUP_MOBILE";
//    private final String SIGNUP_ADDRESS = "SIGNUP_ADDRESS";
//    private final String SIGNUP_EMAIL = "SIGNUP_EMAIL";
//    private final String SIGNUP_PASSWORD = "SIGNUP_PASSWORD";
    // ********************************************************************
    private final String CHEQU_NOTIFICATION_TABLE = "CHEQU_NOTIFICATION_TABLE";
    private final String KEY_CHEQUE = "KEY_CHEQUE";
    private final String ROW_ID = "ROW_ID";
    private final String ISJOIN = "ISJOIN";
    private final String NUMBER_CHEQUE = "NUMBER_CHEQUE";
    private final String DATE_CHEQUE = "DATE_CHEQUE";
    private final String TOCUSTOMER_MOBILE = "TOCUSTOMER_MOBILE";
    private final String TRANSType = "TRANSType";
    private final String STATUS = "STATUS";
    private final String USERNAME = "USERNAME";
    private final String SEND_OR_GERO = "SEND_OR_GERO";
    private final String JOIN_FirstMOB = "JOIN_FirstMOB";
    private final String JOIN_SecondSMOB = "JOIN_SecondSMOB";
    private final String JOIN_TheredMOB = "JOIN_TheredMOB";
    private final String JOIN_F_STATUS = "JOIN_F_STATUS";
    private final String JOIN_F_REASON = "JOIN_F_REASON";
    private final String JOIN_S_STATUS = "JOIN_S_STATUS";

    private final String JOIN_S_REASON = "JOIN_S_REASON";
    private final String JOIN_T_STATUS = "JOIN_T_STATUS";
    private final String JOIN_T_REASON = "JOIN_T_REASON";
    private final String AMOUNT_CHEQUE = "AMOUNT_CHEQUE";





    public DatabaseHandler(@Nullable Context context) {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String createTableSignup = "CREATE TABLE " + SIGNUP_TABLE
//                + " ("
//                + SIGNUP_NATIONAL_ID + " INTEGER, "
//                + SIGNUP_FIRST_NAME + " TEXT, "
//                + SIGNUP_SECOND_NAME + " TEXT, "
//                + SIGNUP_THIRD_NAME + " TEXT, "
//                + SIGNUP_FOURTH_NAME + " TEXT, "
//                + SIGNUP_DOB + " TEXT, "
//                + SIGNUP_GENDER + " TEXT, "
//                + SIGNUP_MOBILE + " INTEGER, "
//                + SIGNUP_ADDRESS + " TEXT, "
//                + SIGNUP_EMAIL + " TEXT, "
//                + SIGNUP_PASSWORD + " TEXT "
//                + ")";
//        db.execSQL(createTableSignup);
//*******************************************************************************
        try {
        String createTableAccounts = "CREATE TABLE " + ACCOUNT_TABLE
                + " ("
                + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ACCOUNT_BANK + " TEXT, "
                + ACCOUNT_BANK_NO + " TEXT, "
                + ACCOUNT_STATUS + " TEXT "
                + ")";
            sqLiteDatabase.execSQL(createTableAccounts);

        }
        catch (Exception e)
        {

        }

        String createTableSetting = "CREATE TABLE " + SETTING_TABLE
                + " ("
                + SETTING_IP + " TEXT "
                + ")";
        sqLiteDatabase.execSQL(createTableSetting);
        try {
            String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
                    + " ("
                    + USER_NAME + " TEXT,"
                    + PASSWORD + " TEXT, "
                    + LOGIN_NATIONAL_ID + " TEXT, "
                    + LOGIN_FIRST_NAME + " TEXT, "
                    + LOGIN_SECOND_NAME + " TEXT, "
                    + LOGIN_THIRD_NAME + " TEXT, "
                    + LOGIN_FOURTH_NAME + " TEXT, "
                    + LOGIN_DOB + " TEXT, "
                    + LOGIN_GENDER + " TEXT, "
                    + LOGIN_ADDRESS + " TEXT, "
                    + LOGIN_EMAIL + " TEXT, "
                    + LOGIN_INACTIVE + " TEXT, "
                    + LOGIN_INDATE + " TEXT, "
                    + LOGIN_REMEMBER + " INTEGER ,"
                    + LOGIN_ACTIVE_NOW + " INTEGER "
                    + ")";
            sqLiteDatabase.execSQL(createTableLOGIN);

        }
        catch (Exception e)
        {

        }
        try {

        String createTableCHEQU_NOTIFICATION_TABLE = "CREATE TABLE " + CHEQU_NOTIFICATION_TABLE
                + " ("
                + KEY_CHEQUE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_ID + " TEXT NOT NULL, "
                + ISJOIN + " INTEGER NOT NULL, "
                + NUMBER_CHEQUE + " INTEGER NOT NULL, "

                + DATE_CHEQUE + " TEXT NOT NULL, "
                + TOCUSTOMER_MOBILE + " INTEGER NOT NULL, "
                + TRANSType + " INTEGER NOT NULL, "
                + STATUS + " INTEGER NOT NULL, "
                + USERNAME + " INTEGER NOT NULL, "
                + SEND_OR_GERO + " INTEGER NOT NULL, "
                + JOIN_FirstMOB + " INTEGER, "
                + JOIN_SecondSMOB + " INTEGER, "
                + JOIN_TheredMOB + " INTEGER, "
                + JOIN_F_STATUS + " INTEGER, "
                + JOIN_F_REASON + " TEXT, "
                + JOIN_S_STATUS + " INTEGER, "
                + JOIN_S_REASON + " TEXT, "
                + JOIN_T_STATUS + " INTEGER, "
                + JOIN_T_REASON + " TEXT, "
                + AMOUNT_CHEQUE + " TEXT "

                + ")";
            sqLiteDatabase.execSQL(createTableCHEQU_NOTIFICATION_TABLE);
        Log.e("NOTIFICATION_TABLE","onCreate");
        }
        catch (Exception e)
        {

        }
        //***********************************************************************


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        try {

//            String createTableaccount = "ALTER TABLE ACCOUNT_TABLE ADD  ACCOUNT_STATUS TEXT";
//            db.execSQL(createTableaccount);


//            String createTableSignup = "CREATE TABLE IF NOT EXISTS " + SIGNUP_TABLE
//                    + " ("
//                    + SIGNUP_NATIONAL_ID + " INTEGER, "
//                    + SIGNUP_FIRST_NAME + " TEXT, "
//                    + SIGNUP_SECOND_NAME + " TEXT, "
//                    + SIGNUP_THIRD_NAME + " TEXT, "
//                    + SIGNUP_FOURTH_NAME + " TEXT, "
//                    + SIGNUP_DOB + " TEXT, "
//                    + SIGNUP_GENDER + " TEXT, "
//                    + SIGNUP_MOBILE + " INTEGER, "
//                    + SIGNUP_ADDRESS + " TEXT, "
//                    + SIGNUP_EMAIL + " TEXT, "
//                    + SIGNUP_PASSWORD + " TEXT "
//                    + ")";
//            db.execSQL(createTableSignup);

//            String createTableSetting = "CREATE TABLE " + SETTING_TABLE
//                    + " ("
//                    + SETTING_IP + " TEXT "
//                    + ")";
//            db.execSQL(createTableSetting);
//            Log.e("Setting","found");
//
//            String createTableLOGIN = "CREATE TABLE " + LOGIN_TABLE
//                    + " ("
//                    + USER_NAME + " TEXT,"
//                    + PASSWORD + " TEXT "
//                    + ")";
//            db.execSQL(createTableLOGIN);

            String createTableSignup = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_REMEMBER + " INTEGER ";
            database.execSQL(createTableSignup);

            String createTableSignup2 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_ACTIVE_NOW + " INTEGER ";
            database.execSQL(createTableSignup2);
//            String createTableSignup1 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_FIRST_NAME + " TEXT ";
//            db.execSQL(createTableSignup1);
//            String createTableSignup2 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_SECOND_NAME + " TEXT ";
//            db.execSQL(createTableSignup2);
//            String createTableSignup3 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_THIRD_NAME + " TEXT ";
//            db.execSQL(createTableSignup3);
//            String createTableSignup4 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_FOURTH_NAME + " TEXT ";
//            db.execSQL(createTableSignup4);
//            String createTableSignup5 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_DOB + " TEXT ";
//            db.execSQL(createTableSignup5);
//            String createTableSignup6 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_GENDER + " TEXT ";
//            db.execSQL(createTableSignup6);
//            String createTableSignup7 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_ADDRESS + " TEXT ";
//            db.execSQL(createTableSignup7);
//            String createTableSignup8 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_EMAIL + " TEXT ";
//            db.execSQL(createTableSignup8);
//            String createTableSignup9 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_INACTIVE + " TEXT ";
//            db.execSQL(createTableSignup9);
//            String createTableSignup10 = "ALTER TABLE LOGIN_TABLE ADD " + LOGIN_INDATE + " TEXT ";
//            db.execSQL(createTableSignup10);

//            String dropTable = "DROP TABLE IF EXISTS SIGNUP_TABLE";
//            db.execSQL(dropTable);
        } catch (Exception e) {

        }
        try {
            String createTableCHEQU_NOTIFICATION_TABLE = "CREATE TABLE " + CHEQU_NOTIFICATION_TABLE
                    + " ("
                    + KEY_CHEQUE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ROW_ID + " TEXT NOT NULL, "
                    + ISJOIN + " INTEGER NOT NULL, "
                    + NUMBER_CHEQUE + " INTEGER NOT NULL, "

                    + DATE_CHEQUE + " TEXT NOT NULL, "
                    + TOCUSTOMER_MOBILE + " INTEGER NOT NULL, "
                    + TRANSType + " INTEGER NOT NULL, "
                    + STATUS + " INTEGER NOT NULL, "
                    + USERNAME + " INTEGER NOT NULL, "
                    + SEND_OR_GERO + " INTEGER NOT NULL, "
                    + JOIN_FirstMOB + " INTEGER, "
                    + JOIN_SecondSMOB + " INTEGER, "
                    + JOIN_TheredMOB + " INTEGER, "
                    + JOIN_F_STATUS + " INTEGER, "
                    + JOIN_F_REASON + " TEXT, "
                    + JOIN_S_STATUS + " INTEGER, "
                    + JOIN_S_REASON + " TEXT, "
                    + JOIN_T_STATUS + " INTEGER, "
                    + JOIN_T_REASON + " TEXT, "
                    + AMOUNT_CHEQUE + " TEXT "

                    + ")";
            database.execSQL(createTableCHEQU_NOTIFICATION_TABLE);

        }
        catch (Exception e)
        {

        }

    }

    public void addNotificationInfo(ChequeInfo chequeInfo) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROW_ID,chequeInfo.getRowId());
        contentValues.put(ISJOIN,chequeInfo.getIsJoin());
        contentValues.put(NUMBER_CHEQUE,chequeInfo.getChequeNo());
        contentValues.put(DATE_CHEQUE,chequeInfo.getCheckDueDate());
        contentValues.put(TOCUSTOMER_MOBILE,chequeInfo.getToCustomerMobel());
        contentValues.put(TRANSType,chequeInfo.getTransType());
        contentValues.put(STATUS,chequeInfo.getStatus());
        contentValues.put(USERNAME,chequeInfo.getUserName());
        contentValues.put(SEND_OR_GERO,chequeInfo.getTransSendOrGero());
        contentValues.put(JOIN_FirstMOB,chequeInfo.getJOIN_FirstMOB());
        contentValues.put(JOIN_SecondSMOB,chequeInfo.getJOIN_SecondSMOB());
        contentValues.put(JOIN_TheredMOB,chequeInfo.getJOIN_TheredMOB());
        contentValues.put(JOIN_F_STATUS,chequeInfo.getJOIN_F_STATUS());
        contentValues.put(JOIN_F_REASON,chequeInfo.getJOIN_F_REASON());
        contentValues.put(JOIN_S_STATUS,chequeInfo.getJOIN_S_STATUS());
        contentValues.put(JOIN_S_REASON,chequeInfo.getJOIN_S_REASON());
        contentValues.put(JOIN_T_STATUS,chequeInfo.getJOIN_T_STATUS());
        contentValues.put(JOIN_T_REASON,chequeInfo.getJOIN_T_REASON());
        contentValues.put(AMOUNT_CHEQUE,chequeInfo.getMoneyInDinar());

        database.insert(CHEQU_NOTIFICATION_TABLE, null, contentValues);
      //  Log.e("addNotificationInfo",""+  database.insert(CHEQU_NOTIFICATION_TABLE, null, contentValues));

        database.close();

    }

    public void addNewAccount(NewAccount newAccount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_BANK_NO, newAccount.getAccountNo());
        contentValues.put(ACCOUNT_BANK, newAccount.getBank());
        contentValues.put(ACCOUNT_STATUS, newAccount.getStatus());

        database.insert(ACCOUNT_TABLE, null, contentValues);
        database.close();

    }

    // ************************************** ADD ************************************
//    public void addSignupInfo(LoginINFO loginINFO) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(SIGNUP_ADDRESS, loginINFO.getAddress());
//        contentValues.put(SIGNUP_DOB, loginINFO.getBirthDate());
//        contentValues.put(SIGNUP_EMAIL, loginINFO.getEmail());
//        contentValues.put(SIGNUP_FIRST_NAME, loginINFO.getFirstName());
//        contentValues.put(SIGNUP_SECOND_NAME, loginINFO.getSecondName());
//        contentValues.put(SIGNUP_THIRD_NAME, loginINFO.getThirdName());
//        contentValues.put(SIGNUP_FOURTH_NAME, loginINFO.getFourthName());
//        contentValues.put(SIGNUP_GENDER, loginINFO.getGender());
//        contentValues.put(SIGNUP_MOBILE, loginINFO.getUsername());
//        contentValues.put(SIGNUP_NATIONAL_ID, loginINFO.getNationalID());
//        contentValues.put(SIGNUP_PASSWORD, loginINFO.getPassword());
//
//        database.insert(SIGNUP_TABLE, null, contentValues);
//        database.close();
//
//    }

    public void addLoginInfo(SignupVM loginINFO) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, loginINFO.getUsername());
        contentValues.put(PASSWORD, loginINFO.getPassword());
        contentValues.put(LOGIN_NATIONAL_ID, loginINFO.getNationalID());
        contentValues.put(LOGIN_FIRST_NAME, loginINFO.getFirstName());
        contentValues.put(LOGIN_SECOND_NAME, loginINFO.getSecondName());
        contentValues.put(LOGIN_THIRD_NAME, loginINFO.getThirdName());
        contentValues.put(LOGIN_FOURTH_NAME, loginINFO.getFourthName());
        contentValues.put(LOGIN_DOB, loginINFO.getBirthDate());
        contentValues.put(LOGIN_GENDER, loginINFO.getGender());
        contentValues.put(LOGIN_ADDRESS, loginINFO.getAddress());
        contentValues.put(LOGIN_EMAIL, loginINFO.getEmail());
        contentValues.put(LOGIN_INACTIVE, loginINFO.getInactive());
        contentValues.put(LOGIN_INDATE, loginINFO.getIndate());
        contentValues.put(LOGIN_REMEMBER, loginINFO.getIsRemember());
        contentValues.put(LOGIN_ACTIVE_NOW, loginINFO.getIsNowActive());

        database.insert(LOGIN_TABLE, null, contentValues);
        database.close();

    }

    public void addSetting(Setting setting) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SETTING_IP, setting.getIp());


        database.insert(SETTING_TABLE, null, contentValues);
        database.close();

    }

    // ************************************** GET ************************************
    public LoginINFO getActiveUserInfo() {
        LoginINFO user = new LoginINFO();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + LOGIN_TABLE + " WHERE LOGIN_ACTIVE_NOW = 1" ;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setNationalID(cursor.getString(2));
                user.setFirstName(cursor.getString(3));
                user.setSecondName(cursor.getString(4));
                user.setThirdName(cursor.getString(5));
                user.setFourthName(cursor.getString(6));
                user.setBirthDate(cursor.getString(7));
                user.setGender(cursor.getString(8));
                user.setAddress(cursor.getString(9));
                user.setEmail(cursor.getString(10));
                user.setInactive(cursor.getString(11));
                user.setIndate(cursor.getString(12));
                user.setIsRemember(cursor.getInt(13));
                user.setIsNowActive(cursor.getInt(14));

                Log.e("user", "" + cursor.getString(0) + cursor.getString(13));

            } while (cursor.moveToNext());
        }
        return user;
    }

    public LoginINFO getUserInfo(String mobileNo) {
        LoginINFO user = new LoginINFO();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + LOGIN_TABLE + " WHERE USER_NAME = '" + mobileNo + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setNationalID(cursor.getString(2));
                user.setFirstName(cursor.getString(3));
                user.setSecondName(cursor.getString(4));
                user.setThirdName(cursor.getString(5));
                user.setFourthName(cursor.getString(6));
                user.setBirthDate(cursor.getString(7));
                user.setGender(cursor.getString(8));
                user.setAddress(cursor.getString(9));
                user.setEmail(cursor.getString(10));
                user.setInactive(cursor.getString(11));
                user.setIndate(cursor.getString(12));
                user.setIsRemember(cursor.getInt(13));
                user.setIsNowActive(cursor.getInt(14));

                Log.e("user", "" + cursor.getString(0) + cursor.getString(13));

            } while (cursor.moveToNext());
        }
        return user;
    }

    public LoginINFO getLoginInfo(String word) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + LOGIN_TABLE + " WHERE LOGIN_REMEMBER = 1 AND USER_NAME LIKE '" + word + "%'";
        Cursor cursor = database.rawQuery(selectQuery, null);
//        Cursor cursor = m_db.query(MY_TABLE, new String[] {"rowid","Word"},"Word LIKE '?'", new String[]{name+"%"}, null, null, null);
//        Cursor cursor = database.query(LOGIN_TABLE,null,"LOGIN_REMEMBER = 1 AND USER_NAME LIKE '?'", new String[]{word+"%"}, null, null, null );
//        List<LoginINFO> list = new ArrayList<>();
        LoginINFO user = new LoginINFO();
        if (cursor.moveToFirst()) {
            do {
//                LoginINFO user = new LoginINFO();
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setNationalID(cursor.getString(2));
                user.setFirstName(cursor.getString(3));
                user.setSecondName(cursor.getString(4));
                user.setThirdName(cursor.getString(5));
                user.setFourthName(cursor.getString(6));
                user.setBirthDate(cursor.getString(7));
                user.setGender(cursor.getString(8));
                user.setAddress(cursor.getString(9));
                user.setEmail(cursor.getString(10));
                user.setInactive(cursor.getString(11));
                user.setIndate(cursor.getString(12));
                user.setIsRemember(cursor.getInt(13));
                user.setIsNowActive(cursor.getInt(14));

                Log.e("getLoginInfo", "" + cursor.getString(0));
//                list.add(user);

            } while (cursor.moveToNext());
        }
        return user;// list;
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

    public boolean IfAccountFound(String AccountNo) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT ACCOUNT_BANK_NO FROM " + ACCOUNT_TABLE + " where substr(ACCOUNT_BANK_NO,2,15) = '" + AccountNo + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        boolean item = false;
        if (cursor.moveToFirst()) {
            do {
//                item = true;

                if (cursor.getString(0) != null) {
                    item = true;
                } else {
                    item = false;
                }

            } while (cursor.moveToNext());
        }
        return item;
    }

    public List<NewAccount> getAllAcCount() {
        ArrayList<NewAccount> newAccounts = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + ACCOUNT_TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                NewAccount item = new NewAccount();

                item.setBank(cursor.getString(1));
                item.setAccountNo(cursor.getString(2));
                item.setStatus(cursor.getString(3));

                newAccounts.add(item);

            } while (cursor.moveToNext());
        }
        return newAccounts;
    }

    // ************************************** DELETE ************************************
    public void deleteLoginInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + LOGIN_TABLE);
        db.close();
    }

    public void deleteAllSetting() {
//             Idb.execSQL("DELETE FROM "+tableName); //delete all rows in a table
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + SETTING_TABLE);
        db.close();
    }

    public void deleteAccount(String AccountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ACCOUNT_TABLE + " where ACCOUNT_BANK_NO = '" + AccountNo + "'"); //delete item code rows in a table
        db.close();

    }

    // ************************************** UPDATE ************************************
    public void updateLoginActive(String mobile) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(LOGIN_ACTIVE_NOW, 0);
        database.update(LOGIN_TABLE, args, null, null);

        ContentValues values = new ContentValues();
        values.put(LOGIN_ACTIVE_NOW, 1);
        database.update(LOGIN_TABLE, values, USER_NAME + " =? ", new String[]{mobile});
        database.close();
    }

    public void updateRememberState(int state, String mobile){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(LOGIN_REMEMBER, state);
        database.update(LOGIN_TABLE, args, USER_NAME + " =? ", new String[]{mobile});
    }

    public void updateStatus(String Status) {
        SQLiteDatabase Idb = this.getWritableDatabase();

        Idb = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(ACCOUNT_STATUS, Status);

        Idb.update(ACCOUNT_TABLE, args, null, null);


    }
    // SELECT ROW_ID from CHEQU_NOTIFICATION_TABLE WHERE ROW_ID = 'AAAth+AAuAAAADcAAA'
    public String getRowID(String row) {

        String id="";
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT ROW_ID from CHEQU_NOTIFICATION_TABLE WHERE ROW_ID =  '" + row + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                id=(cursor.getString(0));


                Log.e("getRowID", "" + id );

            } while (cursor.moveToNext());
        }
        return id;
    }
    public String getLastTransTypeByChequeNo(int chequNo)
    {
       // select TRANSType , Max(KEY_CHEQUE) from CHEQU_NOTIFICATION_TABLE WHERE NUMBER_CHEQUE = '39009'
        int stat=-1;
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "select TRANSType , Max(KEY_CHEQUE) from CHEQU_NOTIFICATION_TABLE WHERE NUMBER_CHEQUE =  '" + chequNo + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    stat=(cursor.getInt(0));
                }
                catch (Exception e)
                {stat=-1;

                }


                Log.e("getLastTrans", "" + stat );

            } while (cursor.moveToNext());
        }
        return stat+"";

    }
    public String getLastStateByChequeNo(int chequNo,int flag)
    {
        String orderMob="";
        switch (flag)
        {
            case 1:
                orderMob="JOIN_F_STATUS";
                break;
            case 2:
                orderMob="JOIN_S_STATUS";
                break;
            case 3:
                orderMob="JOIN_T_STATUS";
                break;

        }
        int stat=-1;
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "select    "+orderMob+"   , Max(KEY_CHEQUE) from CHEQU_NOTIFICATION_TABLE WHERE NUMBER_CHEQUE =  '" + chequNo + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    stat=(cursor.getInt(0));
                }
                catch (Exception e)
                {stat=-1;

                }


                Log.e("statorderMob", "" + stat+"\t"+orderMob );

            } while (cursor.moveToNext());
        }
        return stat+"";

    }
}
