package com.falconssoft.centerbank.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CashierChequeModel {



    private String BANKNO;
    private String BANKNM;
    private String BRANCHNM;
    private String TOCUSTMOB;

    private String TOCUSTNATID;
    private String TOCUSTNAME;
    private String TOCUSTFNAME;
    private String TOCUSTGNAME;

    private String TOCUSTFAMNAME;
    private String ADDRESS;
    private String CUSTNAME;
    private String CUSTMOBNO;

    private String AMTJD;
    private String AMTFILS;
    private String AMTWORD;
    private String REQDATE;

    private String PURPOSE;
    private String RELATIONSHIP;
    private String ACCFORCUST;
    private String RECIPTNAME;

    private String RECIPTFNAME;
    private String RECIPTGNAME;
    private String RECIPTFAMNAME;

    public CashierChequeModel() {

    }


    public CashierChequeModel(String BANKNO, String BANKNM, String BRANCHNM, String TOCUSTMOB, String TOCUSTNATID, String TOCUSTNAME, String TOCUSTFNAME, String TOCUSTGNAME, String TOCUSTFAMNAME, String ADDRESS, String CUSTNAME, String CUSTMOBNO, String AMTJD, String AMTFILS, String AMTWORD, String REQDATE, String PURPOSE, String RELATIONSHIP, String ACCFORCUST, String RECIPTNAME, String RECIPTFNAME, String RECIPTGNAME, String RECIPTFAMNAME) {
        this.BANKNO = BANKNO;
        this.BANKNM = BANKNM;
        this.BRANCHNM = BRANCHNM;
        this.TOCUSTMOB = TOCUSTMOB;
        this.TOCUSTNATID = TOCUSTNATID;
        this.TOCUSTNAME = TOCUSTNAME;
        this.TOCUSTFNAME = TOCUSTFNAME;
        this.TOCUSTGNAME = TOCUSTGNAME;
        this.TOCUSTFAMNAME = TOCUSTFAMNAME;
        this.ADDRESS = ADDRESS;
        this.CUSTNAME = CUSTNAME;
        this.CUSTMOBNO = CUSTMOBNO;
        this.AMTJD = AMTJD;
        this.AMTFILS = AMTFILS;
        this.AMTWORD = AMTWORD;
        this.REQDATE = REQDATE;
        this.PURPOSE = PURPOSE;
        this.RELATIONSHIP = RELATIONSHIP;
        this.ACCFORCUST = ACCFORCUST;
        this.RECIPTNAME = RECIPTNAME;
        this.RECIPTFNAME = RECIPTFNAME;
        this.RECIPTGNAME = RECIPTGNAME;
        this.RECIPTFAMNAME = RECIPTFAMNAME;
    }


    public String getBANKNO() {
        return BANKNO;
    }

    public void setBANKNO(String BANKNO) {
        this.BANKNO = BANKNO;
    }

    public String getBANKNM() {
        return BANKNM;
    }

    public void setBANKNM(String BANKNM) {
        this.BANKNM = BANKNM;
    }

    public String getBRANCHNM() {
        return BRANCHNM;
    }

    public void setBRANCHNM(String BRANCHNM) {
        this.BRANCHNM = BRANCHNM;
    }

    public String getTOCUSTMOB() {
        return TOCUSTMOB;
    }

    public void setTOCUSTMOB(String TOCUSTMOB) {
        this.TOCUSTMOB = TOCUSTMOB;
    }

    public String getTOCUSTNATID() {
        return TOCUSTNATID;
    }

    public void setTOCUSTNATID(String TOCUSTNATID) {
        this.TOCUSTNATID = TOCUSTNATID;
    }

    public String getTOCUSTNAME() {
        return TOCUSTNAME;
    }

    public void setTOCUSTNAME(String TOCUSTNAME) {
        this.TOCUSTNAME = TOCUSTNAME;
    }

    public String getTOCUSTFNAME() {
        return TOCUSTFNAME;
    }

    public void setTOCUSTFNAME(String TOCUSTFNAME) {
        this.TOCUSTFNAME = TOCUSTFNAME;
    }

    public String getTOCUSTGNAME() {
        return TOCUSTGNAME;
    }

    public void setTOCUSTGNAME(String TOCUSTGNAME) {
        this.TOCUSTGNAME = TOCUSTGNAME;
    }

    public String getTOCUSTFAMNAME() {
        return TOCUSTFAMNAME;
    }

    public void setTOCUSTFAMNAME(String TOCUSTFAMNAME) {
        this.TOCUSTFAMNAME = TOCUSTFAMNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCUSTNAME() {
        return CUSTNAME;
    }

    public void setCUSTNAME(String CUSTNAME) {
        this.CUSTNAME = CUSTNAME;
    }

    public String getCUSTMOBNO() {
        return CUSTMOBNO;
    }

    public void setCUSTMOBNO(String CUSTMOBNO) {
        this.CUSTMOBNO = CUSTMOBNO;
    }

    public String getAMTJD() {
        return AMTJD;
    }

    public void setAMTJD(String AMTJD) {
        this.AMTJD = AMTJD;
    }

    public String getAMTFILS() {
        return AMTFILS;
    }

    public void setAMTFILS(String AMTFILS) {
        this.AMTFILS = AMTFILS;
    }

    public String getAMTWORD() {
        return AMTWORD;
    }

    public void setAMTWORD(String AMTWORD) {
        this.AMTWORD = AMTWORD;
    }

    public String getREQDATE() {
        return REQDATE;
    }

    public void setREQDATE(String REQDATE) {
        this.REQDATE = REQDATE;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getRELATIONSHIP() {
        return RELATIONSHIP;
    }

    public void setRELATIONSHIP(String RELATIONSHIP) {
        this.RELATIONSHIP = RELATIONSHIP;
    }

    public String getACCFORCUST() {
        return ACCFORCUST;
    }

    public void setACCFORCUST(String ACCFORCUST) {
        this.ACCFORCUST = ACCFORCUST;
    }

    public String getRECIPTNAME() {
        return RECIPTNAME;
    }

    public void setRECIPTNAME(String RECIPTNAME) {
        this.RECIPTNAME = RECIPTNAME;
    }

    public String getRECIPTFNAME() {
        return RECIPTFNAME;
    }

    public void setRECIPTFNAME(String RECIPTFNAME) {
        this.RECIPTFNAME = RECIPTFNAME;
    }

    public String getRECIPTGNAME() {
        return RECIPTGNAME;
    }

    public void setRECIPTGNAME(String RECIPTGNAME) {
        this.RECIPTGNAME = RECIPTGNAME;
    }

    public String getRECIPTFAMNAME() {
        return RECIPTFAMNAME;
    }

    public void setRECIPTFAMNAME(String RECIPTFAMNAME) {
        this.RECIPTFAMNAME = RECIPTFAMNAME;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("BANKNO", BANKNO );
            obj.put("BANKNM", BANKNM );
            obj.put("BRANCHNM", BRANCHNM );
            obj.put("TOCUSTMOB", TOCUSTMOB );

            obj.put("TOCUSTNATID", TOCUSTNATID );
            obj.put("TOCUSTNAME", TOCUSTNAME );
            obj.put("TOCUSTFNAME", TOCUSTFNAME );
            obj.put("TOCUSTGNAME", TOCUSTGNAME );

            obj.put("TOCUSTFAMNAME", TOCUSTFAMNAME );
            obj.put("ADDRESS", ADDRESS );
            obj.put("CUSTNAME", CUSTNAME );
            obj.put("CUSTMOBNO", CUSTMOBNO );

            obj.put("AMTJD", AMTJD );
            obj.put("AMTFILS", AMTFILS );
            obj.put("AMTWORD", AMTWORD );
            obj.put("REQDATE", REQDATE );

            obj.put("PURPOSE", PURPOSE );
            obj.put("RELATIONSHIP", RELATIONSHIP );
            obj.put("ACCFORCUST", ACCFORCUST );
            obj.put("RECIPTNAME", RECIPTNAME );

            obj.put("RECIPTFNAME",  RECIPTFNAME);
            obj.put("RECIPTGNAME",  RECIPTGNAME);
            obj.put("RECIPTFAMNAME",  RECIPTFAMNAME);

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

}
