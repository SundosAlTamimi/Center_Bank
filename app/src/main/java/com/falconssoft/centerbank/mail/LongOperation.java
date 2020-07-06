package com.falconssoft.centerbank.mail;

import android.os.AsyncTask;
import android.util.Log;

public class LongOperation extends AsyncTask<Void, Void, String> {
    private String emailSender, password, emailReciever, userName;


    public LongOperation(String emailSender, String password, String emailReciever, String userName) {
        this.emailSender = emailSender;
        this.password = password;
        this.emailReciever = emailReciever;
        this.userName = userName;
    }

    @Override
    protected String doInBackground(Void... params) {


        try {GMailSender sender = new GMailSender(userName, password);
            sender.sendMail("Recover old password",
                    "Your old password is " + password, emailSender,
                    emailReciever);
//            GMailSender sender = new GMailSender("gsolc.developers@gmail.com", "your@password");
//            sender.sendMail("Verification Code",
//                    "Your old password is ", "gsolc.developers@gmail.com",
//                    "pankajgurjar90@gmail.com,gsolc.developers@gmail.com");

        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return "Email Not Sent";
        }

        return "Email Sent";

    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("LongOperation", result + "");
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

}
