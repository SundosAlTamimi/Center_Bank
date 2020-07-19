package com.falconssoft.centerbank.Models;

import android.graphics.Bitmap;
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

    private String bankName;
    private String chequeData;
    private String toCustomerName;
    private String moneyInDinar;
    private String moneyInFils;
    private String moneyInWord;
    private String recieverMobileNo;
    private String recieverNationalID;
    private String chequeImage;
    private String rowId;

    private String ToCustomerMobel;
    private String ToCustomerNationalId;
    private String CheqPIc;


    private String customerWriteDate;
    private String checkDueDate;
    private String checkIsSueDate;

    private String transType;
    private String status;
    private String Date;
    private String userName;
    private String ISCO;
    private String ISBF;

    private String ISOpen;
    private String noteCheck;
    private String companyName;
    private String resonOfreject;
    private  String transSendOrGero;

    public String getTransSendOrGero() {
        return transSendOrGero;
    }

    public void setTransSendOrGero(String transSendOrGero) {
        this.transSendOrGero = transSendOrGero;
    }

    public String getResonOfreject() {
        return resonOfreject;
    }

    public void setResonOfreject(String resonOfreject) {
        this.resonOfreject = resonOfreject;
    }

    public String getNoteCheck() {
        return noteCheck;
    }

    public void setNoteCheck(String noteCheck) {
        this.noteCheck = noteCheck;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public ChequeInfo() {
    }

    public ChequeInfo(String chequeNo, String bankNo, String branchNo, String accCode, String ibanNo, String custName, String qrCode, String serialNo, String bankName, String chequeData, String toCustomerName, String moneyInDinar, String moneyInFils, String moneyInWord, String recieverMobileNo, String recieverNationalID, String chequeImage, String rowId, String toCustomerMobel, String toCustomerNationalId, String cheqPIc, String customerWriteDate, String checkDueDate, String checkIsSueDate, String transType, String status, String date, String ISOpen) {
        this.chequeNo = chequeNo;
        this.bankNo = bankNo;
        this.branchNo = branchNo;
        this.accCode = accCode;
        this.ibanNo = ibanNo;
        this.custName = custName;
        this.qrCode = qrCode;
        this.serialNo = serialNo;
        this.bankName = bankName;
        this.chequeData = chequeData;
        this.toCustomerName = toCustomerName;
        this.moneyInDinar = moneyInDinar;
        this.moneyInFils = moneyInFils;
        this.moneyInWord = moneyInWord;
        this.recieverMobileNo = recieverMobileNo;
        this.recieverNationalID = recieverNationalID;
        this.chequeImage = chequeImage;
        this.rowId = rowId;
        this. ToCustomerMobel = toCustomerMobel;
        this. ToCustomerNationalId = toCustomerNationalId;
        this.CheqPIc = cheqPIc;
        this.customerWriteDate = customerWriteDate;
        this.checkDueDate = checkDueDate;
        this.checkIsSueDate = checkIsSueDate;
        this.transType = transType;
        this.status = status;
        this.Date = date;
        this.ISOpen = ISOpen;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChequeData() {
        return chequeData;
    }

    public void setChequeData(String chequeData) {
        this.chequeData = chequeData;
    }

    public String getToCustomerName() {
        return toCustomerName;
    }

    public void setToCustomerName(String toCustomerName) {
        this.toCustomerName = toCustomerName;
    }

    public String getMoneyInDinar() {
        return moneyInDinar;
    }

    public void setMoneyInDinar(String moneyInDinar) {
        this.moneyInDinar = moneyInDinar;
    }

    public String getMoneyInFils() {
        return moneyInFils;
    }

    public void setMoneyInFils(String moneyInFils) {
        this.moneyInFils = moneyInFils;
    }

    public String getMoneyInWord() {
        return moneyInWord;
    }

    public void setMoneyInWord(String moneyInWord) {
        this.moneyInWord = moneyInWord;
    }

    public String getRecieverMobileNo() {
        return recieverMobileNo;
    }

    public void setRecieverMobileNo(String recieverMobileNo) {
        this.recieverMobileNo = recieverMobileNo;
    }

    public String getRecieverNationalID() {
        return recieverNationalID;
    }

    public void setRecieverNationalID(String recieverNationalID) {
        this.recieverNationalID = recieverNationalID;
    }

    public String getChequeImage() {
        return chequeImage;
    }

    public void setChequeImage(String chequeImage) {
        this.chequeImage = chequeImage;
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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getISOpen() {
        return ISOpen;
    }

    public void setISOpen(String ISOpen) {
        this.ISOpen = ISOpen;
    }

    public String getToCustomerMobel() {
        return ToCustomerMobel;
    }

    public void setToCustomerMobel(String toCustomerMobel) {
        ToCustomerMobel = toCustomerMobel;
    }

    public String getToCustomerNationalId() {
        return ToCustomerNationalId;
    }

    public void setToCustomerNationalId(String toCustomerNationalId) {
        ToCustomerNationalId = toCustomerNationalId;
    }

    public String getCheqPIc() {
        return CheqPIc;
    }

    public void setCheqPIc(String cheqPIc) {
        CheqPIc = cheqPIc;
    }

    public String getCustomerWriteDate() {
        return customerWriteDate;
    }

    public void setCustomerWriteDate(String customerWriteDate) {
        this.customerWriteDate = customerWriteDate;
    }

    public String getCheckDueDate() {
        return checkDueDate;
    }

    public void setCheckDueDate(String checkDueDate) {
        this.checkDueDate = checkDueDate;
    }

    public String getCheckIsSueDate() {
        return checkIsSueDate;
    }

    public void setCheckIsSueDate(String checkIsSueDate) {
        this.checkIsSueDate = checkIsSueDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setISCO(String ISCO) {
        this.ISCO = ISCO;
    }


    public String getISCO() {
        return ISCO;
    }

    public String getISBF() {
        return ISBF;
    }

    public void setISBF(String ISBF) {
        this.ISBF = ISBF;
    }

    // CHECKINFO={"BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000"
// ,"IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968"
// ,"CHECKDUEDATE":"21/12/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0"
// ,"AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKPIC":""}
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("BANKNO", bankNo );

            obj.put("CHECKNO", chequeNo );
            obj.put("BTANCHNO", branchNo );
            obj.put("ACCCODE", accCode );
            obj.put("IBANNO", ibanNo );
            obj.put("CUSTOMERNM", custName );

            obj.put("BANKNM", bankName );
            obj.put("BRANCHNO", branchNo );
            obj.put("ACCCODE", accCode );
            obj.put("CUSTOMERNM", custName );
            obj.put("QRCODE", qrCode );
            obj.put("SERIALNO", serialNo );
            obj.put("CHECKDUEDATE", chequeData );
            obj.put("TOCUSTOMERNM", toCustomerName );
            obj.put("AMTJD", moneyInDinar );
            obj.put("AMTFILS", moneyInFils );
            obj.put("AMTWORD", moneyInWord );
            obj.put("TOCUSTOMERMOB", recieverMobileNo );
            obj.put("TOCUSTOMERNATID", recieverNationalID );
            obj.put("CHECKPIC", chequeImage );
            obj.put("USERNO",  userName);
            obj.put("ISCO",  ISCO);
            obj.put("ISFB",  ISBF);
            obj.put("COMPANY",  companyName);
            obj.put("NOTE",  noteCheck);



        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

    public JSONObject getJSONIsPinding() {
        JSONObject obj = new JSONObject();
//ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144
        try {
            obj.put("ACCCODE", accCode );
            obj.put("IBANNO", ibanNo );
            obj.put("SERIALNO", serialNo );
            obj.put("BANKNO", bankName );
            obj.put("BRANCHNO", branchNo );
            obj.put("CHECKNO", chequeNo );

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }

}
