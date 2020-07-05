package com.falconssoft.centerbank.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NewAccount {
    private String RowId;
    private String accountNo;
    private String bank;
    private String Status;


    public NewAccount() {
    }

    public NewAccount(String rowId, String accountNo, String bank, String status) {
        this.RowId = rowId;
        this.accountNo = accountNo;
        this.bank = bank;
        this.Status = status;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRowId() {
        return RowId;
    }

    public void setRowId(String rowId) {
        RowId = rowId;
    }


    public JSONObject getJSONObject(String userNo) {
        JSONObject obj = new JSONObject();


        //{"MOBILENO":"0798899716","ACCOUNTNO":"123","ACCOUNTBANK":"Jordan Bank"}


        try {
            obj.put("MOBILENO", userNo );
            obj.put("ACCOUNTNO", accountNo );
            obj.put("ACCOUNTBANK", bank );


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
