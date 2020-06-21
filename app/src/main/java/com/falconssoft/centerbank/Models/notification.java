package com.falconssoft.centerbank.Models;

import android.graphics.Bitmap;

public class notification {
    private String amount_check;
    private String date;
    private String source;
    private Bitmap check_photo;

    public notification() {
    }

    public String getAmount_check() {
        return amount_check;
    }

    public void setAmount_check(String amount_check) {
        this.amount_check = amount_check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Bitmap getCheck_photo() {
        return check_photo;
    }

    public void setCheck_photo(Bitmap check_photo) {
        this.check_photo = check_photo;
    }
}
