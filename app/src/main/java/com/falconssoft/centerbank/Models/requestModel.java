package com.falconssoft.centerbank.Models;
/*
* ROWID":"AABYTMAAPAAAADbAAD","FROMUSER":"0790790791","FROMUSERNM":"tahani","TOUSER":"0772095887","TOUSERNM":"rawan",

"COMPNAME":"compan","NOTE":"noteee","AMOUNT":"1500","TRANSSTATUS":"0","INDATE":"7\/8\/2020 3:40:30 PM","REASON":""}*/
public class requestModel {
    private  String ROWID;
    private  String FROMUSER_No;
    private  String FROMUSER_name;
    private  String TOUSER_No;
    private  String TOUSER_name;
    private  String COMPNAME;
    private  String NOTE;
    private  String AMOUNT;
    private  String TRANSSTATUS;
    private  String INDATE;
    private  String REASON;
    private String witch;
    private String kind;
    private String notif_ROWID;
    private String SEEN;
    private String  WICHEUSER;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNotif_ROWID() {
        return notif_ROWID;
    }

    public void setNotif_ROWID(String notif_ROWID) {
        this.notif_ROWID = notif_ROWID;
    }

    public String getSEEN() {
        return SEEN;
    }

    public void setSEEN(String SEEN) {
        this.SEEN = SEEN;
    }

    public String getWICHEUSER() {
        return WICHEUSER;
    }

    public void setWICHEUSER(String WICHEUSER) {
        this.WICHEUSER = WICHEUSER;
    }

    public String getWitch() {
        return witch;
    }

    public void setWitch(String witch) {
        this.witch = witch;
    }

    public requestModel() {
    }

    public String getROWID() {
        return ROWID;
    }

    public void setROWID(String ROWID) {
        this.ROWID = ROWID;
    }

    public String getFROMUSER_No() {
        return FROMUSER_No;
    }

    public void setFROMUSER_No(String FROMUSER_No) {
        this.FROMUSER_No = FROMUSER_No;
    }

    public String getFROMUSER_name() {
        return FROMUSER_name;
    }

    public void setFROMUSER_name(String FROMUSER_name) {
        this.FROMUSER_name = FROMUSER_name;
    }

    public String getTOUSER_No() {
        return TOUSER_No;
    }

    public void setTOUSER_No(String TOUSER_No) {
        this.TOUSER_No = TOUSER_No;
    }

    public String getTOUSER_name() {
        return TOUSER_name;
    }

    public void setTOUSER_name(String TOUSER_name) {
        this.TOUSER_name = TOUSER_name;
    }

    public String getCOMPNAME() {
        return COMPNAME;
    }

    public void setCOMPNAME(String COMPNAME) {
        this.COMPNAME = COMPNAME;
    }

    public String getNOTE() {
        return NOTE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getTRANSSTATUS() {
        return TRANSSTATUS;
    }

    public void setTRANSSTATUS(String TRANSSTATUS) {
        this.TRANSSTATUS = TRANSSTATUS;
    }

    public String getINDATE() {
        return INDATE;
    }

    public void setINDATE(String INDATE) {
        this.INDATE = INDATE;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }
}
