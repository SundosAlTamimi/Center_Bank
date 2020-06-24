package com.falconssoft.centerbank.Models;

public class Setting {

    private  String Ip;

    public static String SettingIp ="";

    public Setting() {

    }

    public Setting(String ip) {
        Ip = ip;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }
}
