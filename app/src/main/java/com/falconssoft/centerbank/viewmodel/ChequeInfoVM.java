package com.falconssoft.centerbank.viewmodel;

import java.io.Serializable;

public class ChequeInfoVM implements Serializable {


    private String chequeNo;
    private String bankNo;
    private String branchNo;
    private String accCode;
    private String ibanNo;
    private String ownerName;
    private String qrCode;
    private String serialNo;
    private String ownerID;
    private String ownerPhone;

    private String bankName;
    private String chequeData;
    private String receiverName;
    private String moneyInDinar;
    private String moneyInFils;
    private String moneyInWord;
    private String receiverMobileNo;
    private String receiverNationalID;
    private String chequeImage;
    private String rowId;

    private String ChequePIc;

    private String customerWriteDate;
    private String checkDueDate;
    private String checkIssueDate;

    private String transType;
    private String status;
    private String Date;
    private String userName;
    private String ISCO;
    private String ISBF;

    private String ISOpen;
    private String noteCheck;
    private String companyName;
    private String rejectReason;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public ChequeInfoVM() {
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public String getReceiverMobileNo() {
        return receiverMobileNo;
    }

    public void setReceiverMobileNo(String receiverMobileNo) {
        this.receiverMobileNo = receiverMobileNo;
    }

    public String getReceiverNationalID() {
        return receiverNationalID;
    }

    public void setReceiverNationalID(String receiverNationalID) {
        this.receiverNationalID = receiverNationalID;
    }

    public String getChequeImage() {
        return chequeImage;
    }

    public void setChequeImage(String chequeImage) {
        this.chequeImage = chequeImage;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
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

    public String getChequePIc() {
        return ChequePIc;
    }

    public void setChequePIc(String chequePIc) {
        ChequePIc = chequePIc;
    }

    public String getCheckIssueDate() {
        return checkIssueDate;
    }

    public void setCheckIssueDate(String checkIssueDate) {
        this.checkIssueDate = checkIssueDate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getISCO() {
        return ISCO;
    }

    public void setISCO(String ISCO) {
        this.ISCO = ISCO;
    }

    public String getISBF() {
        return ISBF;
    }

    public void setISBF(String ISBF) {
        this.ISBF = ISBF;
    }

    public String getISOpen() {
        return ISOpen;
    }

    public void setISOpen(String ISOpen) {
        this.ISOpen = ISOpen;
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
}
