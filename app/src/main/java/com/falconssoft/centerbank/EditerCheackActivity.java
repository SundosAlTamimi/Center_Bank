package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.LinearLayout.VERTICAL;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditerCheackActivity extends AppCompatActivity {

    LinearLayout linerEditing, linerBarcode;
    TextView scanBarcode, AmouWord, date;
    Button pushCheque;
    EditText Danier, phails, nationalNo, phoneNo, sender, reciever;
    private ProgressDialog progressDialog;

    int flag = 0;
    CircleImageView CheckPic;
    static final int CAMERA_PIC_REQUEST = 1337;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    String today;
    Calendar myCalendar;
    private JSONObject jsonObject;

    static String qrCode = "";
    static String[] arr;

    static String  CHECKNO= "";
    static String  ACCCODE= "";
    static String  IBANNO= "";
    static String  CUSTOMERNM= "";
    static String  QRCODE= "";
    static String  SERIALNO= "";
    static String  BANKNO= "";
    static String  BRANCHNO= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editer_check_layout);

        initi();
//        arr=new String[5];
        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        date.setText(convertToEnglish(today));

        linerEditing.setVisibility(View.GONE);
        linerBarcode.setVisibility(View.VISIBLE);
        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBarCode();
            }
        });

        AmouWord.setMovementMethod(new ScrollingMovementMethod());

        phails.addTextChangedListener(textWatcher);

        Danier.addTextChangedListener(textWatcher);

        CheckPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flag = 0;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });


    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = "";
            if (!Danier.getText().toString().equals("")) {

                if (!phails.getText().toString().equals("")) {
                    amount = Danier.getText().toString() + "." + phails.getText().toString();
                } else {
                    amount = Danier.getText().toString() + "." + "00";
                }
            }

            if (!phails.getText().toString().equals("")) {

                if (!Danier.getText().toString().equals("")) {
                    amount = Danier.getText().toString() + "." + phails.getText().toString();
                } else {
                    amount = "00" + "." + phails.getText().toString();
                }
            }


            NumberToArabic numberToArabic = new NumberToArabic();
            String amountWord = numberToArabic.getArabicString(amount);

            Log.e("Ammount", "Jd +" + amountWord);
            AmouWord.setText(amountWord);


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void initi() {
        linerEditing = findViewById(R.id.linerEditing);
        linerBarcode = findViewById(R.id.linerBarcode);
        scanBarcode = findViewById(R.id.scanBarcode);
        Danier = findViewById(R.id.denier);
        phails = findViewById(R.id.Phils);
        AmouWord = findViewById(R.id.AmouWord);
        pushCheque = findViewById(R.id.SingUpButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Waiting...");
        CheckPic = findViewById(R.id.CheckPic);
        date = findViewById(R.id.editorCheque_date);

        nationalNo = findViewById(R.id.editorCheque_nationalNo);
        phoneNo = findViewById(R.id.editorCheque_phoneNo);
        sender = findViewById(R.id.editorCheque_sender);
        reciever = findViewById(R.id.editorCheque_reciever);

        myCalendar = Calendar.getInstance();

        pushCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// CHECKINFO={"BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000"
// ,"IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968"
// ,"CHECKDUEDATE":"21/12/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0"
// ,"AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKPIC":""}
                String localNationlNo = nationalNo.getText().toString();
                String localPhoneNo = phoneNo.getText().toString();
                String localSender = sender.getText().toString();
                String localReciever = reciever.getText().toString();
                String localDinar = Danier.getText().toString();
                String localFils = "" + phails.getText().toString();
                String localMoneyInWord = AmouWord.getText().toString();
                String localDate = date.getText().toString();

                if (!TextUtils.isEmpty(localNationlNo)
                        && !TextUtils.isEmpty(localPhoneNo)
                        && !TextUtils.isEmpty(localSender)
                        && !TextUtils.isEmpty(localReciever)
                        && !TextUtils.isEmpty(localDinar)
                ) {

//                    String checkNo = arr[0];
//                    String bankNo = arr[1];
//                    String branchNo = arr[2];
//                    String accCode = arr[3];
//                    String ibanNo = arr[4];
//                    String custName= "";
                    ChequeInfo chequeInfo = new ChequeInfo();
                    chequeInfo.setBankNo(BANKNO);
                    chequeInfo.setBankName("Jordan Bank");
                    chequeInfo.setBranchNo(BRANCHNO);
                    chequeInfo.setChequeNo(CHECKNO);
                    chequeInfo.setAccCode(ACCCODE);
                    chequeInfo.setIbanNo(IBANNO);
                    chequeInfo.setCustName(CUSTOMERNM);
                    chequeInfo.setQrCode(QRCODE);
                    chequeInfo.setSerialNo(SERIALNO);
                    chequeInfo.setChequeData(localDate);
                    chequeInfo.setToCustomerName(localReciever);
                    chequeInfo.setMoneyInDinar(localDinar);
                    chequeInfo.setMoneyInFils(localFils);
                    chequeInfo.setMoneyInWord(localMoneyInWord);
                    chequeInfo.setRecieverMobileNo(localPhoneNo);
                    chequeInfo.setRecieverNationalID(localNationlNo);
                    chequeInfo.setChequeImage("");

                    jsonObject = new JSONObject();
                    jsonObject=chequeInfo.getJSONObject();

                     new JSONTask1().execute();

                }


            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditerCheackActivity.this, openDatePickerDialog(date), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    //TextView itemCodeText, int swBarcode
    public void readBarCode() {

        flag = 1;
//        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(EditerCheackActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (flag == 1) {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Log.d("MainActivity", "cancelled scan");
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
                } else {

                    Log.e("MainActivity", "" + Result.getContents());
                    Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
//                openEditerCheck();

                    String ST = Result.getContents();
                    arr = ST.split(";");

//                    String checkNo = arr[0];
//                    String bankNo = arr[1];
//                    String branchNo = arr[2];
//                    String accCode = arr[3];
//                    String ibanNo = arr[4];
//                    String custName= "";

                    //qrCode = "CHECKNO=" + arr[0] + "&BANKNO=" + arr[1] + "&BTANCHNO=" + arr[2] + "&ACCCODE=" + arr[3] + "&IBANNOo=" + arr[4] + "&CUSTOMERNM=''"  ;
                    new JSONTask().execute();


                    // showSweetDialog(true);

                }

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {//
            try {
                if (requestCode == CAMERA_PIC_REQUEST) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    if (image != null) {
                        CheckPic.setImageBitmap(image);
                    }
                }


            } catch (Exception e) {
                Log.e("not getCamera", "message " + e.toString());
            }

        }

    }

    void showSweetDialog(boolean check,String customerName,String BankNo,String accountNo) {
        if (check) {
            String message="Cheque is validate \n"+"Customer Name :"+customerName+" \n"+"Bank Name : "+"بنك الاردن "+"\n"+"Account No : "+accountNo+"\n";
            new SweetAlertDialog(EditerCheackActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successful")
                    .setContentText(message)
                    .setConfirmText("Next")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            linerEditing.setVisibility(View.VISIBLE);
                            linerBarcode.setVisibility(View.GONE);
                            sDialog.dismissWithAnimation();
                        }
                    }).setCancelText("Cancel").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            })
                    .show();
        } else {
            new SweetAlertDialog(EditerCheackActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setConfirmText("Ok")
.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        sweetAlertDialog.dismissWithAnimation();
    }
})

                    .show();

        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final TextView editText) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };
        return date;
    }

    private void updateLabel(TextView editText) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));

    }

    // ******************************************** CHECK QR VALIDATION *************************************
   /*
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                // http://10.0.0.16:8081/VerifyCheck?CHECKNO=390144&BANKNO=004&BTANCHNO=0099&ACCCODE=1014569990011000&IBANNO=""&CUSTOMERNM=""
//                request.setURI(new URI("http://" + generalSettings.getIpAddress() + "/export.php"));//import 10.0.0.214
                request.setURI(new URI("http://10.0.0.16:8081/VerifyCheck?CHECKNO=390144&BANKNO=004&BTANCHNO=0099&ACCCODE=1014569990011000&IBANNO=111111111111&CUSTOMERNM=ahmad"));


//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                Log.e("addToInventory/", "" + jsonArrayBundles.toString());
//                nameValuePairs.add(new BasicNameValuePair("UPDATE_RAW_INFO", "1"));// list

//                nameValuePairs.add(new BasicNameValuePair("TRUCK", oldTruck));//oldTruck
//                nameValuePairs.add(new BasicNameValuePair("RAW_INFO_DETAILS", jsonArray.toString().trim()));// list
//                nameValuePairs.add(new BasicNameValuePair("RAW_INFO_MASTER", masterData.toString().trim())); // json object
//                Log.e("addNewRow/", "update" + masterData.toString().trim() + " ///oldTruck" + oldTruck);

//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("editCheckActivity/", "verify" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("tag of update row info", s);
            progressDialog.dismiss();
            if (s != null) {
                if (s.contains("UPDATE RAWS SUCCESS")) {
                    showSweetDialog(true);

                    Log.e("tag", "update Success");
                } else {
                    showSweetDialog(false);
                    Log.e("tag", "****Failed to export data");
//                    Toast.makeText(AddToInventory.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
                Toast.makeText(EditerCheackActivity.this, "Failed to export data Please check internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }

*/

    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://10.0.0.16:8081/VerifyCheck?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", arr[0]));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", arr[1]));
                nameValuePairs.add(new BasicNameValuePair("BTANCHNO", arr[2]));
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", arr[3]));
                nameValuePairs.add(new BasicNameValuePair("IBANNO", ""));
                nameValuePairs.add(new BasicNameValuePair("CUSTOMERNM", ""));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("tag", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****Success");
                    try {
                        JSONObject jsonObject=new JSONObject(s);


                        CHECKNO = jsonObject.get("CHECKNO").toString();
                        ACCCODE = jsonObject.get("ACCCODE").toString();
                        IBANNO = jsonObject.get("IBANNO").toString();
                        CUSTOMERNM =jsonObject.get("CUSTOMERNM").toString();
                        QRCODE = jsonObject.get("QRCODE").toString();
                        SERIALNO = jsonObject.get("SERIALNO").toString();
                        BANKNO = jsonObject.get("BANKNO").toString();
                        BRANCHNO = jsonObject.get("BRANCHNO").toString();

                        showSweetDialog(true,jsonObject.get("CUSTOMERNM").toString(),jsonObject.get("BANKNO").toString(),jsonObject.get("ACCCODE").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    showSweetDialog(false,"","","");

                    Log.e("tag", "****Failed to export data");
                }
            } else {
                showSweetDialog(false,"","","");

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    // ******************************************** SAVE *************************************
    private class JSONTask1 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//http://localhost:8081/SaveTempCheck?
// CHECKINFO={"BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000"
// ,"IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968"
// ,"CHECKDUEDATE":"21/12/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0"
// ,"AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKPIC":""}
                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI("http://10.0.0.16:8081/SaveTempCheck?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKINFO", jsonObject.toString()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();

                JsonResponse = sb.toString();
                Log.e("tag", "" + JsonResponse);

                return JsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Edit");
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(EditerCheackActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Successful")
                            .setContentText("Save Successful")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                     finish();
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else {
                    Log.e("tag", "****Failed to export data");
                    new SweetAlertDialog(EditerCheackActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Fail to send!")
                            .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    })
                            .show();
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }
}
