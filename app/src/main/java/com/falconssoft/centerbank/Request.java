package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.ShowNotifications.showNotification;

public class Request extends AppCompatActivity {
    public  String FROMUSER_No="";
    public  String FROMUSER_Name="";
    public  String FROMUSER="";
    public  String TOUSER_no="";
    public  String TOUSER_Name="";
    public  String COMPNAME="";
    public  String NOTE="";

    public  String AMOUNT="";
    EditText edit_customerName,phoneNo,amountDinar,company,note;
    Button sendButton,cancelButton;
    boolean isFull=true;
    public  static   String language="", serverLink="http://falconssoft.net/ScanChecks/APIMethods.dll/";
    JSONObject obj;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    private LinearLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

           initView();
    }

    private boolean validateRequired() {

        TOUSER_no = phoneNo.getText().toString();
        TOUSER_Name = edit_customerName.getText().toString();
        AMOUNT = amountDinar.getText().toString();
        if(!TextUtils.isEmpty(TOUSER_no)){}
        else
        {
            phoneNo.setError(getResources().getString(R.string.required));
            isFull=false;
        }

        if(!TextUtils.isEmpty(TOUSER_Name)){}
        else
        {
            edit_customerName.setError(getResources().getString(R.string.required));
            isFull=false;
        }

        if(!TextUtils.isEmpty(AMOUNT)){}
        else
        {
            amountDinar.setError(getResources().getString(R.string.required));
            isFull=false;
        }
        return isFull;

    }

    private void initView() {
        coordinatorLayout=findViewById(R.id.request_coordinatorLayout);
        edit_customerName = findViewById(R.id.edit_customerName);
        phoneNo = findViewById(R.id.edit_phoneNo);
        amountDinar = findViewById(R.id.denier);
        company = findViewById(R.id.edit_company);
        note = findViewById(R.id.edit_notes);
        sendButton = findViewById(R.id.AcceptButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( validateRequired())
               {
                   try {
                       getUserInfo();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   sendData();
               }
               else {
                   isFull=true;
               }


            }
        });
        cancelButton = findViewById(R.id.RejectButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent( Request.this,MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void getUserInfo() throws JSONException {
        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        FROMUSER_No = loginPrefs.getString("mobile", "");
        FROMUSER_Name = loginPrefs.getString("name", "");
        NOTE=note.getText().toString();
        COMPNAME=company.getText().toString();
        Log.e("getUserInfo",""+FROMUSER_No+"\t"+FROMUSER_Name+"\t"+NOTE);

        obj = new JSONObject();


        obj.put("FROMUSER", FROMUSER_No);
        obj.put("FROMUSERNM", FROMUSER_Name);
        obj.put("TOUSER", TOUSER_no);
        obj.put("TOUSERNM", TOUSER_Name);
        obj.put("COMPNAME", COMPNAME);
        obj.put("NOTE", NOTE);
        obj.put("AMOUNT", AMOUNT);

    }

    private void sendData() {
        new AddRequest().execute();

    }
    protected class AddRequest extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
        try{
                String JsonResponse = null;

                String data = "REQINFO=" + URLEncoder.encode(obj.toString(), "UTF-8");
                String link = serverLink + "AddRequest?";
//
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                JsonResponse = sb.toString();
                Log.e("tagAlertScreenImage", "" + JsonResponse);

                return  JsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("tag", "Error closing stream", e);
                }
            }
        }
            return null;



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("onPostExecute/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Request");
                    showSweetDialog();
                    clearData();

                } else {
                    Log.e("tag", "****Failed to Send data"+s.toString());
                }
            } else {
                showSnackbar("Check internet connection", false);
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }
    void showSnackbar(String text, boolean showImage) {

        if (showImage) {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#3167F0\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_24dp, 0, 0, 0);
        } else {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#D11616\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error, 0, 0, 0);
        }
        snackbar.show();
    }

    private void clearData() {
        note.setText("");
        company.setText("");
        phoneNo.setText("");
        edit_customerName.setText("");
        amountDinar.setText("");


    }

    private void showSweetDialog() {
        new SweetAlertDialog(Request.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("SUCCESS")
                .setContentText(getResources().getString(R.string.save_success))
                .setConfirmText(getResources().getString(R.string.ok)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();

            }
        })
                .show();
    }

}
