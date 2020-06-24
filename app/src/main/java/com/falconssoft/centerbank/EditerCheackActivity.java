package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditerCheackActivity extends AppCompatActivity {

    LinearLayout linerEditing, linerBarcode;
    TextView scanBarcode, AmouWord, date;
    Button SingUpButton;
    EditText Danier, phails;
    private ProgressDialog progressDialog;

    int flag = 0;
    CircleImageView CheckPic;
    static final int CAMERA_PIC_REQUEST = 1337;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    String today;
    Calendar myCalendar;
    static String qrCode = "";
    static  String [] arr ;

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
        SingUpButton = findViewById(R.id.SingUpButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Waiting...");
        CheckPic = findViewById(R.id.CheckPic);
        date = findViewById(R.id.date);
        myCalendar = Calendar.getInstance();

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

                    String ST=Result.getContents();
                     arr =ST.split(";");

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

    void showSweetDialog(boolean check) {
        if (check) {
            new SweetAlertDialog(EditerCheackActivity.this, R.style.alert_dialog_dark)
                    .setTitleText("Successful")
                    .setContentText("Cheque is validate")
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
            new SweetAlertDialog(EditerCheackActivity.this, R.style.alert_dialog_dark)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            })
                    .show();

            linerEditing.setVisibility(View.VISIBLE);
            linerBarcode.setVisibility(View.GONE);
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
                } else {
                    Log.e("tag", "****Failed to export data");
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }


}
