package com.falconssoft.centerbank.Models;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ChequeInfo implements Serializable {

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
    private  String toCustName;
    private  String toCustFName;
    private  String toCustGName;
    private  String toCustFamalyName;


    private  String IsJoin;
    private  String JOIN_FirstMOB   ;
    private  String JOIN_SecondSMOB ;
    private  String JOIN_TheredMOB  ;

    private  String JOIN_F_STATUS   ;
    private  String JOIN_F_REASON   ;
    private  String JOIN_S_STATUS   ;

    private  String JOIN_S_REASON   ;
    private  String JOIN_T_STATUS   ;
    private  String JOIN_T_REASON    ;
    private  String NOTFROWID;
    private  String WICHEUSER;

    public String getWICHEUSER() {
        return WICHEUSER;
    }

    public void setWICHEUSER(String WICHEUSER) {
        this.WICHEUSER = WICHEUSER;
    }

    public String getNOTFROWID() {
        return NOTFROWID;
    }

    public void setNOTFROWID(String NOTFROWID) {
        this.NOTFROWID = NOTFROWID;
    }

    public String getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(String isJoin) {
        IsJoin = isJoin;
    }

    public String getJOIN_FirstMOB() {
        return JOIN_FirstMOB;
    }

    public void setJOIN_FirstMOB(String JOIN_FirstMOB) {
        this.JOIN_FirstMOB = JOIN_FirstMOB;
    }

    public String getJOIN_SecondSMOB() {
        return JOIN_SecondSMOB;
    }

    public void setJOIN_SecondSMOB(String JOIN_SecondSMOB) {
        this.JOIN_SecondSMOB = JOIN_SecondSMOB;
    }

    public String getJOIN_TheredMOB() {
        return JOIN_TheredMOB;
    }

    public void setJOIN_TheredMOB(String JOIN_TheredMOB) {
        this.JOIN_TheredMOB = JOIN_TheredMOB;
    }

    public String getJOIN_F_STATUS() {
        return JOIN_F_STATUS;
    }

    public void setJOIN_F_STATUS(String JOIN_F_STATUS) {
        this.JOIN_F_STATUS = JOIN_F_STATUS;
    }

    public String getJOIN_F_REASON() {
        return JOIN_F_REASON;
    }

    public void setJOIN_F_REASON(String JOIN_F_REASON) {
        this.JOIN_F_REASON = JOIN_F_REASON;
    }

    public String getJOIN_S_STATUS() {
        return JOIN_S_STATUS;
    }

    public void setJOIN_S_STATUS(String JOIN_S_STATUS) {
        this.JOIN_S_STATUS = JOIN_S_STATUS;
    }

    public String getJOIN_S_REASON() {
        return JOIN_S_REASON;
    }

    public void setJOIN_S_REASON(String JOIN_S_REASON) {
        this.JOIN_S_REASON = JOIN_S_REASON;
    }

    public String getJOIN_T_STATUS() {
        return JOIN_T_STATUS;
    }

    public void setJOIN_T_STATUS(String JOIN_T_STATUS) {
        this.JOIN_T_STATUS = JOIN_T_STATUS;
    }

    public String getJOIN_T_REASON() {
        return JOIN_T_REASON;
    }

    public void setJOIN_T_REASON(String JOIN_T_REASON) {
        this.JOIN_T_REASON = JOIN_T_REASON;
    }


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

    public ChequeInfo(String chequeNo, String bankNo, String branchNo, String accCode, String ibanNo, String custName, String qrCode, String serialNo, String bankName, String chequeData, String toCustomerName, String moneyInDinar, String moneyInFils, String moneyInWord, String recieverMobileNo, String recieverNationalID, String chequeImage, String rowId, String toCustomerMobel, String toCustomerNationalId, String cheqPIc, String customerWriteDate, String checkDueDate, String checkIsSueDate, String transType, String status, String date, String userName, String ISCO, String ISBF, String ISOpen, String noteCheck, String companyName, String resonOfreject, String transSendOrGero, String toCustName, String toCustFName, String toCustGName, String toCustFamalyName) {
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

        this.chequeImage = chequeImage;
        this.rowId = rowId;
        ToCustomerMobel = toCustomerMobel;
        ToCustomerNationalId = toCustomerNationalId;
        CheqPIc = cheqPIc;
        this.customerWriteDate = customerWriteDate;
        this.checkDueDate = checkDueDate;
        this.checkIsSueDate = checkIsSueDate;
        this.transType = transType;
        this.status = status;
        Date = date;
        this.userName = userName;
        this.ISCO = ISCO;
        this.ISBF = ISBF;
        this.ISOpen = ISOpen;
        this.noteCheck = noteCheck;
        this.companyName = companyName;
        this.resonOfreject = resonOfreject;
        this.transSendOrGero = transSendOrGero;
        this.toCustName = toCustName;
        this.toCustFName = toCustFName;
        this.toCustGName = toCustGName;
        this.toCustFamalyName = toCustFamalyName;
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

    public String getToCustName() {
        return toCustName;
    }

    public void setToCustName(String toCustName) {
        this.toCustName = toCustName;
    }

    public String getToCustFName() {
        return toCustFName;
    }

    public void setToCustFName(String toCustFName) {
        this.toCustFName = toCustFName;
    }

    public String getToCustGName() {
        return toCustGName;
    }

    public void setToCustGName(String toCustGName) {
        this.toCustGName = toCustGName;
    }

    public String getToCustFamalyName() {
        return toCustFamalyName;
    }

    public void setToCustFamalyName(String toCustFamalyName) {
        this.toCustFamalyName = toCustFamalyName;
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
            obj.put("TOCUSTOMERMOB", ToCustomerMobel );
            obj.put("TOCUSTOMERNATID", ToCustomerNationalId );
            obj.put("CHECKPIC", chequeImage );
            obj.put("USERNO",  userName);
            obj.put("ISCO",  ISCO);
            obj.put("ISFB",  ISBF);
            obj.put("COMPANY",  companyName);
            obj.put("NOTE",  noteCheck);
            obj.put("CUSTNAME",toCustName);
            obj.put("CUSTFNAME",toCustFName);
            obj.put("CUSTGNAME",toCustGName);
            obj.put("CUSTFAMNAME",toCustFamalyName);



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
