package com.falconssoft.centerbank.Models;

public class NewAccount {

    private String accountNo;
    private String bank;
    private String Status;


    public NewAccount() {
    }

    public NewAccount(String accountNo, String bank, String status) {
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
}
