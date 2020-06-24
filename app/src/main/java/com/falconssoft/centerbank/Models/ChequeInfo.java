package com.falconssoft.centerbank.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ChequeInfo {

    private String chequeNo;
    private String bankNo;
    private String branchNo;
    private String accCode;
    private String ibanNo;
    private String custName;
    private String qrCode;
    private String serialNo;


    public ChequeInfo(String chequeNo, String bankNo, String branchNo, String accCode, String ibanNo, String custName, String qrCode, String serialNo) {
        this.chequeNo = chequeNo;
        this.bankNo = bankNo;
        this.branchNo = branchNo;
        this.accCode = accCode;
        this.ibanNo = ibanNo;
        this.custName = custName;
        this.qrCode = qrCode;
        this.serialNo = serialNo;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }

    public String getIbanNo() {
        return ibanNo;
    }

    public void setIbanNo(String ibanNo) {
        this.ibanNo = ibanNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("CHECKNO", chequeNo );
            obj.put("BANKNO", bankNo );
            obj.put("BTANCHNO", branchNo );
            obj.put("ACCCODE", accCode );
            obj.put("IBANNO", ibanNo );
            obj.put("CUSTOMERNM", custName );


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
