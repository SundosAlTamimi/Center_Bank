package com.falconssoft.centerbank;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hbb20.CountryCodePicker;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.centerbank.AlertScreen.checkInfoNotification;
import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditerCheackActivity extends AppCompatActivity {
    String mCameraFileName, path;
    ImageView imageView;
    Uri image;

    private LinearLayout linerEditing, linerBarcode, linearPhone, linearOptional, linearAmount, linearCo;
    TextView scanBarcode, AmouWord, date;
    Button pushCheque;
    TableRow reciever;
    EditText Danier, phails, nationalNo, phoneNo, company, notes, fName, sName, tName, fourthName, rowDate;
    private ProgressDialog progressDialog;
    private TextView bankNameTV, chequeWriterTV, chequeNoTV, accountNoTV, okTV, cancelTV, check, amountTV;
    private LinearLayout haveAProblem, serialLinear;
    private TextInputEditText serial;
    private Animation animation;
    private TableRow picRow;
    int flag = 0;
    boolean validDate = false;
    CircleImageView CheckPic;
    static final int CAMERA_PIC_REQUEST = 1337;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    Bitmap serverPicBitmap;
    private String today, serverPic = "", language, serverLink = "http://falconssoft.net/ScanChecks/APIMethods.dll/";
    Calendar myCalendar;
    private JSONObject jsonObject;

    TextView CheckPicText;
    static String qrCode = "";
    static String[] arr;
    CheckBox checkBox_CO, checkBox_firstpinifit;
    //
    static String CHECKNO = "";
    static String ACCCODE = "";
    static String IBANNO = "";
    static String CUSTOMERNM = "";
    static String QRCODE = "";
    static String SERIALNO = "";
    static String BANKNO = "";
    static String BRANCHNO = "";
    public static String localNationlNo = "";
    String phoneNoUser;
    String intentReSend;
    SweetAlertDialog pd,pdValidation,pdValidationDialog;
    boolean isPermition;
    ChequeInfo chequeInfoReSendEd;
    private String currencyLanguage = "عربي", amountWord, countryCode = "962";
    private NumberToArabic numberToArabic;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private CountryCodePicker ccp;
    LoginINFO userSend ;
    boolean isInGetData=true,userFound=false;
    private String validateBySerial = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LocaleAppUtils().changeLayot(EditerCheackActivity.this);
        setContentView(R.layout.editer_check_layout);

        initi();

        chequeInfoReSendEd = new ChequeInfo();

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs.getString("link", "");
        phoneNoUser = loginPrefs.getString("mobile", "");


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

//_________________________________________________________________________


        Danier.addTextChangedListener(textWatcherDeletePic);

        phails.addTextChangedListener(textWatcherDeletePic);
        nationalNo.addTextChangedListener(textWatcherDeletePic);

//        phoneNo.addTextChangedListener(textWatcherDeletePic);
        company.addTextChangedListener(textWatcherDeletePic);

        notes.addTextChangedListener(textWatcherDeletePic);
        fName.addTextChangedListener(textWatcherDeletePic);

        sName.addTextChangedListener(textWatcherDeletePic);
        tName.addTextChangedListener(textWatcherDeletePic);

        fourthName.addTextChangedListener(textWatcherDeletePic);
        rowDate.addTextChangedListener(textWatcherDeletePic);


        CheckPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                File file = new File(Environment.getExternalStorageDirectory(),  "/DCIM/Camera/IMG_20200629_112400" +".jpg");//+(cal.getTimeInMillis()
//                if (ContextCompat.checkSelfPermission(EditerCheackActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//                    // Permission is not granted
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(EditerCheackActivity.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//
//                    } else {
//                        // No explanation needed; request the permission
//                        ActivityCompat.requestPermissions(EditerCheackActivity.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                1);
//                    }
//                }
//
//                if(!file.exists()){
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }else{
//                    file.delete();
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//                if (file.exists()){
//                    capturedImageUri = Uri.fromFile(file) ;
//                    Log.e("uri", capturedImageUri.getPath());
//                }
//                flag = 0;
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//
////                file = Uri.fromFile(getFile());
////
////                //Setting the file Uri to my photo
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,file);
//
//                if(cameraIntent.resolveActivity(getPackageManager())!=null)
//                {
//                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//                }
                flag = 0;
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                isPermition = isStoragePermissionGranted();
                if (isPermition) {
                    cameraIntent();
                }

            }
        });

        intentReSend = getIntent().getStringExtra("ReSend");
        chequeInfoReSendEd = (ChequeInfo) getIntent().getSerializableExtra("ChequeInfo");

        if (intentReSend != null && intentReSend.equals("ReSend")) {
            userFound=true;
        }

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();

            }
        });

       phoneNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                {
                    new SharedClass(EditerCheackActivity.this).showPhoneOptions(phoneNo.getText().toString());
                    return true;
                }
            }
        });

    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private void cameraIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("_mm_ss");

        String newPicFile = "in" + ".png";
        String outPath = Environment.getExternalStorageDirectory() + File.separator + newPicFile;
        Log.e("InventoryDBFolder", "" + outPath);
        File outFile = new File(outPath);
        path = outPath;
        mCameraFileName = outFile.toString();
        Uri outuri = Uri.fromFile(outFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        startActivityForResult(intent, 2);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            ConvertCurrency();
            serverPic = "";
            CheckPic.setImageBitmap(null);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher textWatcherDeletePic = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            serverPic = "";
            CheckPic.setImageBitmap(null);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    void ConvertCurrency() {
        String amount = "", amount2 = "";
        amountWord = "";
        if (currencyLanguage.equals("En")) {
            TafqeetEnglish tafqeetEnglish = new TafqeetEnglish();

            if (!Danier.getText().toString().equals("")) {
                if (!phails.getText().toString().equals("")) { // dinar and fils
                    amount = Danier.getText().toString();// + "." + phails.getText().toString();
                    amount2 = phails.getText().toString();

                   String dinar=tafqeetEnglish.convert(Long.parseLong(amount));
                   String philses=tafqeetEnglish.convert(Long.parseLong(amount2));

//                    amountWord = tafqeetEnglish.convert(Long.parseLong(amount)) + " Dinar And " + tafqeetEnglish.convert(Long.parseLong(amount2)) + " Fils";

                    if(dinar.equals("")&&philses.equals("")){
                        amountWord="";
                    }else if(!dinar.equals("")&&philses.equals("")){
                        amountWord = tafqeetEnglish.convert(Long.parseLong(amount)) + " Dinar";
                    }else if(dinar.equals("")&&!philses.equals("")) {
                        amountWord=tafqeetEnglish.convert(Long.parseLong(amount2)) + " Fils";
                    }else  if(!dinar.equals("")&&!philses.equals("")) {
                        amountWord = tafqeetEnglish.convert(Long.parseLong(amount)) + " Dinar And " + tafqeetEnglish.convert(Long.parseLong(amount2)) + " Fils";

                    }



                } else { // dinar
                    amount = Danier.getText().toString();// + "." + phails.getText().toString();
                    if(Integer.parseInt(Danier.getText().toString())!=0) {
                        amountWord = tafqeetEnglish.convert(Long.parseLong(amount)) + " Dinar";
                    }else {
                        amountWord="";
                    }
                }
            } else if (!phails.getText().toString().equals("")) { //  fils
                if (Danier.getText().toString().equals("")) {
                    amount2 = phails.getText().toString();
                    if(Integer.parseInt(phails.getText().toString())!=0) {
                        amountWord = tafqeetEnglish.convert(Long.parseLong(amount2)) + " Fils";
                    }else{
                        amountWord="";
                    }
                }
            }


            if (amountWord.equals("")) {
                AmouWord.setText("");
            } else {
                AmouWord.setText(amountWord + " Only");
            }


        } else if (currencyLanguage.equals("عربي")) {

//            if (!Danier.getText().toString().equals("")) {
//
//                if (!phails.getText().toString().equals("")) {
//                    amount = Danier.getText().toString() + "." + phails.getText().toString();
//                } else {
//                    amount = Danier.getText().toString() + "." + "00";
//                }
//            }
//
//            if (!phails.getText().toString().equals("")) {
//
//                if (!Danier.getText().toString().equals("")) {
//                    amount = Danier.getText().toString() + "." + phails.getText().toString();
//                } else {
//                    amount = "00" + "." + phails.getText().toString();
//                }
//            }
//            numberToArabic = new NumberToArabic();
//            String amountWord = numberToArabic.getArabicString(amount);
//            AmouWord.setText(amountWord + " فقط لا غير");


            NumberToArabic numberToArabic = new NumberToArabic();


            if (!Danier.getText().toString().equals("")) {
                if (!phails.getText().toString().equals("")) {
                    amount = Danier.getText().toString();// + "." + phails.getText().toString();
                    amount2 = phails.getText().toString();
                    if (Integer.parseInt(phails.getText().toString()) != 0) {// dinar and fils

                        if (Integer.parseInt(Danier.getText().toString()) != 0) {

                            amountWord = numberToArabic.getArabicString(amount) + " و " + convertDinarToFilse(numberToArabic.getArabicString(amount2));
                        } else {
                            amountWord = convertDinarToFilse(numberToArabic.getArabicString(amount2));

                        }
                    } else {
                        if (Integer.parseInt(Danier.getText().toString()) != 0) {

                            amountWord = numberToArabic.getArabicString(amount);
                        }
                    }

                } else { // dinar

                    amount = Danier.getText().toString();// + "." + phails.getText().toString();
                    amountWord = numberToArabic.getArabicString(amount);
                }
            } else if (!phails.getText().toString().equals("")) { //  fils
                if (Danier.getText().toString().equals("")) {
                    amount2 = phails.getText().toString();

                    amountWord = convertDinarToFilse(numberToArabic.getArabicString(amount2));
                } else if (Integer.parseInt(Danier.getText().toString()) == 0) {
                    amountWord = convertDinarToFilse(numberToArabic.getArabicString(amount2));
                }
            }

            if (amountWord.equals("")) {
                AmouWord.setText("");
            } else {
                AmouWord.setText(amountWord + " فقط لا غير ");
            }
            //

        }

        Log.e("Ammount", "Jd +" + amountWord);


        if (phails.getText().toString().equals("") && Danier.getText().toString().equals("")) {
            AmouWord.setText("");
        } else if (!phails.getText().toString().equals("") && !Danier.getText().toString().equals("")) {
            if (Integer.parseInt(Danier.getText().toString()) == 0 && Integer.parseInt(phails.getText().toString()) == 0) {
                AmouWord.setText("");
            }
        }

    }

    String convertDinarToFilse(String ammount) {
        String filsAmm = "";
        filsAmm = ammount.replace("ديناراً", "فلس").replace("دينار", "فلس").replace("دنانير", "فلس");

        return filsAmm;

    }


    private void initi() {
        linerEditing = findViewById(R.id.linerEditing);
        linerBarcode = findViewById(R.id.linerBarcode);
        scanBarcode = findViewById(R.id.scanBarcode);
        Danier = findViewById(R.id.denier);
        phails = findViewById(R.id.Phils);
        AmouWord = findViewById(R.id.AmouWord);
        pushCheque = findViewById(R.id.SingUpButton);
        company = findViewById(R.id.editorCheque_company);
        notes = findViewById(R.id.editorCheque_notes);
        picRow = findViewById(R.id.editorCheque_picLinear);
        rowDate = findViewById(R.id.rowDate);
        rowDate.setEnabled(false);
        amountTV = findViewById(R.id.editorCheque_amountTV);
        amountTV = findViewById(R.id.editorCheque_amountTV);
        spinner = findViewById(R.id.editorCheque_amount_lang);
        ccp = findViewById(R.id.editorCheque_ccp);
        linearPhone = findViewById(R.id.editorCheque_phone_linear);
        linearOptional = findViewById(R.id.editorCheque_linear_optional);
        linearAmount = findViewById(R.id.editorCheque_linear_amount);
        linearCo = findViewById(R.id.editorCheque_linear_co);

        arrayList.add("عربي");
        arrayList.add("En");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencyLanguage = adapterView.getItemAtPosition(i).toString();
                ConvertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_waiting));

        CheckPic = findViewById(R.id.CheckPic);
        date = findViewById(R.id.editorCheque_date);

        nationalNo = findViewById(R.id.editorCheque_nationalNo);
        phoneNo = findViewById(R.id.editorCheque_phoneNo);
        reciever = findViewById(R.id.editorCheque_reciever);
        fName = findViewById(R.id.first_name);
        sName = findViewById(R.id.second_name);
        tName = findViewById(R.id.thered_name);
        fourthName = findViewById(R.id.fourth_name);
        checkBox_CO = findViewById(R.id.checkBox_CO);
        checkBox_firstpinifit = findViewById(R.id.checkBox_firstpinifit);
        haveAProblem = findViewById(R.id.editorCheque_haveAProblem);
        serialLinear = findViewById(R.id.editorCheque_serial_linear);
        serial = findViewById(R.id.editorCheque_serial);
        check = findViewById(R.id.editorCheque_check);
        serialLinear.setVisibility(View.GONE);
        CheckPicText = findViewById(R.id.CheckPicText);

//        new Image().execute();

        haveAProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serialLinear.getVisibility() == View.VISIBLE) {
                    serialLinear.setVisibility(View.GONE);

                } else {
                    serialLinear.setVisibility(View.VISIBLE);
                    serial.setError(null);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(serial.getText().toString())) {
                    serial.setError(null);
                    validateBySerial = serial.getText().toString().toUpperCase();
                    new JSONTaskSerial().execute();
//                    new Presenter(EditerCheackActivity.this).checkBySerial(serial.getText().toString().toUpperCase(), null, null, EditerCheackActivity.this);

                } else {
                    serial.setError("Required");
                }

            }
        });

        myCalendar = Calendar.getInstance();

        pushCheque.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                sendCheque();
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
        validDate = true;


        phoneNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL) {

                    getInfoByCustomer();
                }

                return false;
            }
        });

        phoneNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    getInfoByCustomer();
                }
            }
        });

//        phoneNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(phoneNo.hasFocus()) {
//                isInGetData=true;
//                }
//            }
//        });

    }

    void getInfoByCustomer() {

        if (isInGetData) {
            isInGetData = false;
            if (!phoneNo.getText().toString().equals("")) {
                String ToPhoneNo = countryCode + phoneNo.getText().toString();
                new GetUserInfoByMobo(ToPhoneNo).execute();
            } else {
                isInGetData = true;
            }
        }
    }

    private boolean compareDate(String chequeDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);


        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Date date1 = dateFormat.parse(chequeDate);

        Date dateToday = dateFormat.parse(today);
        calendar1.setTime(dateToday);
        calendar2.setTime(date1);
        calendar1.add(Calendar.YEAR, 5);
        calendar1.add(Calendar.MONTH, 1);

        if (calendar2.compareTo(calendar1) == 1) {
            return false;
        } else {
            return true;
        }

    }

    @SuppressLint("SetTextI18n")
    void fillTheCheck(ChequeInfo chequeInfo) {
//if(chequeInfo.getChequeNo().equals()) {
        Danier.setText("" + chequeInfo.getMoneyInDinar());
        phails.setText("" + chequeInfo.getMoneyInFils());
        AmouWord.setText("" + chequeInfo.getMoneyInWord());
        nationalNo.setText("" + chequeInfo.getToCustomerNationalId());
        String splitString = chequeInfo.getToCustomerMobel().substring(chequeInfo.getToCustomerMobel().length() - 9);
        Log.e("splitString", "" + splitString + "    " + chequeInfo.getToCustomerMobel());
        phoneNo.setText("" + splitString);
        company.setText("" + chequeInfo.getCompanyName());
        notes.setText("" + chequeInfo.getNoteCheck());
        fName.setText("" + chequeInfo.getToCustName());
        sName.setText("" + chequeInfo.getToCustFName());
        tName.setText("" + chequeInfo.getToCustGName());
        fourthName.setText("" + chequeInfo.getToCustFamalyName());
        date.setText("" + chequeInfo.getCheckDueDate());

        if (chequeInfo.getISCO().equals("1")) {
            checkBox_CO.setChecked(true);
        } else {
            checkBox_CO.setChecked(false);

        }

        if (chequeInfo.getISBF().equals("1")) {
            checkBox_firstpinifit.setChecked(true);
        } else {
            checkBox_firstpinifit.setChecked(false);
        }
//}

    }

    void sendCheque() {

// CHECKINFO={"BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000"
// ,"IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968"
// ,"CHECKDUEDATE":"21/12/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0"
// ,"AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKPIC":""}


        localNationlNo = nationalNo.getText().toString();
        String localPhoneNo = phoneNo.getText().toString();
//                String localSender = sender.getText().toString();

        String localReciever = "" + fName.getText().toString() + " " + sName.getText().toString() + " " + tName.getText().toString() + " " + fourthName.getText().toString();
        String localDinar = Danier.getText().toString();
        String localFils = "" + phails.getText().toString();
        String localMoneyInWord = AmouWord.getText().toString();
        String localDate = date.getText().toString();
        try {
            validDate = false;
            validDate = compareDate(localDate);
            Log.e("validDate", "" + validDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(localNationlNo) && localNationlNo.length() == 10)
            if (!TextUtils.isEmpty(localPhoneNo) && localPhoneNo.length() == 9)
                if (!String.valueOf(localPhoneNo.charAt(0)).equals("0"))
                    if (!TextUtils.isEmpty(localReciever))
                        if (!TextUtils.isEmpty(localDate))
                            if (validDate)
                                if (!TextUtils.isEmpty(localDinar)) {
                                    if(Double.parseDouble(localDinar+"."+localFils)!=0){
                                    if (!TextUtils.isEmpty(serverPic)) {
                                        if (userFound) {
                                            pushCheque.setEnabled(false);
                                            SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
                                            String phoneNo1 = loginPrefs.getString("mobile", "");

                                            Danier.setError(null);
                                            date.setError(null);
                                            fName.setError(null);
                                            sName.setError(null);
                                            tName.setError(null);
                                            fourthName.setError(null);
                                            phoneNo.setError(null);
                                            nationalNo.setError(null);

                                            String checkBox_C = "", checkBox_Fb = "";

                                            if (checkBox_CO.isChecked()) {
                                                checkBox_C = "1";
                                            } else {
                                                checkBox_C = "0";
                                            }

                                            if (checkBox_firstpinifit.isChecked()) {
                                                checkBox_Fb = "1";
                                            } else {
                                                checkBox_Fb = "0";
                                            }

                                            ChequeInfo chequeInfo = new ChequeInfo();
                                            chequeInfo.setBankNo(BANKNO);
                                            chequeInfo.setBankName("Bank Of Jordan");
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
                                            chequeInfo.setToCustomerMobel(countryCode + localPhoneNo);
                                            chequeInfo.setToCustomerNationalId(localNationlNo);
                                            chequeInfo.setChequeImage(serverPic);
                                            chequeInfo.setUserName(phoneNo1);
                                            chequeInfo.setISCO(checkBox_C);
                                            chequeInfo.setISBF(checkBox_Fb);
                                            chequeInfo.setCompanyName(company.getText().toString());
                                            chequeInfo.setToCustName(fName.getText().toString());
                                            chequeInfo.setToCustFName(sName.getText().toString());
                                            chequeInfo.setToCustGName(tName.getText().toString());
                                            chequeInfo.setToCustFamalyName(fourthName.getText().toString());
                                            chequeInfo.setNoteCheck(notes.getText().toString());
                                            Log.e("showpic", serverPic);

                                            jsonObject = new JSONObject();
                                            jsonObject = chequeInfo.getJSONObject();
                                            if (!(countryCode + localPhoneNo).equals(phoneNoUser)) {//no send to the same phone no
                                                new SaveCheckTemp().execute();




                                            } else {
                                                SweetAlertDialog sw = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE);
                                                sw.setTitleText( EditerCheackActivity.this.getResources().getString(R.string.phone_no) );
                                                sw.setContentText("Please , change Phone No ,You Can't Send The Cheque To Yourself");
                                                sw.setConfirmText(EditerCheackActivity.this.getResources().getString(R.string.ok));
                                                sw.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @SuppressLint("WrongConstant")
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        phoneNo.setError("Change");
                                                        pushCheque.setEnabled(true);
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                });
                                                sw.show();
                                            }

//                                    imageSend();
//                uploadMultipart(String.valueOf(creatFile(serverPicBitmap)));
//                new Image().execute();
//                                   new  IsCheckPinding().execute();
                                        } else {
                                            SweetAlertDialog sw = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE);
                                            sw.setTitleText("***" + EditerCheackActivity.this.getResources().getString(R.string.phone_no) + "***");
                                            sw.setContentText("Cheque App not install in this Phone No  " + "+" + countryCode + localPhoneNo);
                                            sw.setConfirmText(EditerCheackActivity.this.getResources().getString(R.string.ok));
                                            sw.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @SuppressLint("WrongConstant")
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            });
                                            sw.show();
                                        }


                                    } else {
                                        CheckPicText.setError("Required!");
                                    }
                                    } else {
                                        Danier.setError("Not Correct Amount !");
                                    }
                                } else {
                                    Danier.setError("Required!");
                                }
                            else {
                                rowDate.setError("Not valid Date");
                            }
                        else {
                            date.setError("Required!");
                        }
                    else {
                        fName.setError("Required!");
                        sName.setError("Required!");
                        tName.setError("Required!");
                        fourthName.setError("Required!");
                    }
                else
                    phoneNo.setError(getResources().getString(R.string.zero_digit));
            else {
                phoneNo.setError("Required!");
            }
        else {
            nationalNo.setError("Required!");
        }

    }

    void checkLanguage() {
        if (language.trim().equals("ar")) {
            LocaleAppUtils.setLocale(new Locale("ar"));
            LocaleAppUtils.setConfigChange(EditerCheackActivity.this);

            nationalNo.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_person_black_24dp), null);
//            reciever.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_location_on_black_24dp), null);
            date.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_date_range_black_24dp), null);
            company.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_https_black_24dp), null);
            notes.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_email_black_24dp), null);
//            amountTV.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_attach_money_black_24dp), null);
            date.setGravity(Gravity.RIGHT);
            haveAProblem.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            picRow.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            LocaleAppUtils.setLocale(new Locale("en"));
            LocaleAppUtils.setConfigChange(EditerCheackActivity.this);

            nationalNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_person_black_24dp), null
                    , null, null);
//            reciever.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_location_on_black_24dp), null
//                    , null, null);
            date.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_date_range_black_24dp), null
                    , null, null);
            company.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_https_black_24dp), null
                    , null, null);
            notes.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_email_black_24dp), null
                    , null, null);
//            amountTV.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(EditerCheackActivity.this, R.drawable.ic_attach_money_black_24dp), null
//                    , null, null);
            date.setGravity(Gravity.LEFT);
            haveAProblem.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            picRow.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        nationalNo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearPhone.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        reciever.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        date.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearOptional.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearAmount.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearCo.setAnimation(animation);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

                    if (intentReSend != null && intentReSend.equals("ReSend")) {
                        if (arr[0].equals(chequeInfoReSendEd.getChequeNo())) {
                            Log.e("ReSend 708", "" + "JSONTask");
                            new JSONTask().execute();
                        } else {
                            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.cheque_app))
                                    .setContentText(EditerCheackActivity.this.getResources().getString(R.string.CHEQUE_NOT_FOR_YOU))
                                    .setConfirmText(EditerCheackActivity.this.getResources().getString(R.string.ok))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            finish();
//
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                            Log.e("SweetAlertDialog 724", "" + "JSONTask");
                        }


                    } else {
                        Log.e("SweetAlertDialog 730", "" + "JSONTask");
                        new JSONTask().execute();
                    }


                    // showSweetDialog(true);

                }

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {

            if (requestCode == 2) {
                if (data != null) {
                    image = data.getData();
                    CheckPic.setImageURI(image);
//                CheckPic.setVisibility(View.VISIBLE);
                }
                if (image == null && mCameraFileName != null) {
                    image = Uri.fromFile(new File(mCameraFileName));
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png";
                    serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png");
                    CheckPic.setImageBitmap(serverPicBitmap);
                    serverPic = bitMapToString(serverPicBitmap);
                    deleteFiles(path);
                    CheckPicText.setError(null);
                }
                File file = new File(mCameraFileName);
                if (!file.exists()) {
                    file.mkdir();
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png";
                    serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png");
                    CheckPic.setImageBitmap(serverPicBitmap);
                    serverPic = bitMapToString(serverPicBitmap);
                    deleteFiles(path);
//                    Bitmap bitmap1 = StringToBitMap(serverPic);
//                    showImageOfCheck(bitmap1);
                } else {

                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png";
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                serverPicBitmap = BitmapFactory.decodeFile(path, options);
                    serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/in.png");
                    CheckPic.setImageBitmap(serverPicBitmap);
                    serverPic = bitMapToString(serverPicBitmap);
                    deleteFiles(path);
//                Bitmap bitmap1 = StringToBitMap(serverPic);
//                showImageOfCheck(bitmap1);

                }
            }
        }


    }


    public void deleteFiles(String path) {
        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) {

            }
        }

    }

    void showSweetDialog(boolean check, String customerName, String BankNo, String accountNo) {
        if (check) {
            String message = "Cheque is validate \n" + "Customer Name :" + customerName + " \n" + "Bank Name : " + "بنك الاردن " + "\n" + "Account No : " + accountNo + "\n";
            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("")
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
            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })

                    .show();
            pdValidation.dismissWithAnimation();


        }
    }

    public void showValidationDialog(boolean check, String customerName, String BankNo, String accountNo, String chequeNo) {
        Log.e("VerifyCheck 849", "" + "JSONTask dialog");
        if (check) {
            Log.e("VerifyCheck 851", "JSONTask dialog in ");
            new LocaleAppUtils().changeLayot(EditerCheackActivity.this);
            final Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_after_validation);
            dialog.setCancelable(false);

            ImageView pic_bank=dialog.findViewById(R.id.bank_pic);;
            bankNameTV = dialog.findViewById(R.id.dialog_validation_bankName);
            chequeWriterTV = dialog.findViewById(R.id.dialog_validation_chequeWriter);
            chequeNoTV = dialog.findViewById(R.id.dialog_validation_chequeNo);
            accountNoTV = dialog.findViewById(R.id.dialog_validation_accountNo);
            okTV = dialog.findViewById(R.id.dialog_validation_ok);
            cancelTV = dialog.findViewById(R.id.dialog_validation_cancel);


            if (LocaleAppUtils.language.equals("ar")) {
                chequeWriterTV.setText(customerName);
                accountNoTV.setText(convertToArabic(accountNo));
                chequeNoTV.setText(convertToArabic(chequeNo));
            } else {

                chequeWriterTV.setText(customerName);
                accountNoTV.setText(accountNo);
                chequeNoTV.setText(chequeNo);
            }

            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new IsCheckForThisAcc().execute();
//                    linerEditing.setVisibility(View.VISIBLE);
//                    linerBarcode.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            });


            switch (BankNo){

                case"004":

                    pic_bank.setImageDrawable(EditerCheackActivity.this.getResources().getDrawable(R.drawable.jordan_bank));
                    bankNameTV.setText(EditerCheackActivity.this.getResources().getString(R.string.bank_of_jordan));

                    break;
                case "009":
                    pic_bank.setImageDrawable(EditerCheackActivity.this.getResources().getDrawable(R.drawable.cairo_amman_bank));
                    bankNameTV.setText(EditerCheackActivity.this.getResources().getString(R.string.cairo_amman_bank));
                    break;

            }

            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            Log.e("VerifyCheck 890", "JSONTask dialog in ");
            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            }).show();

            linerEditing.setVisibility(View.VISIBLE);
            linerBarcode.setVisibility(View.GONE);
        }

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public String convertToArabic(String value) {
        String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
        Log.e("convertToArabic", value + "      " + newValue);
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
        String dateSelected = sdf.format(myCalendar.getTime());

        editText.setText(dateSelected);

    }

    public String bitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
//            byte[] encoded = Base64.encode(arr, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

            String result = Base64.encodeToString(arr, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            return result;
        }
        return "";
    }

//public Bitmap getpic(){
//    byte[] encodeByte = Base64.decode(image, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
//
//    Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
//    bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(encodeByte));
//    return bitmap;
//}
//
//
//public String setPic(Bitmap bitmap){
//    ByteBuffer buffer = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
//    bitmap.copyPixelsToBuffer(buffer);
//    byte[] data = buffer.array();
//    return Base64.encodeToString(data, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
//
//}

    void close() {
        finish();
    }

    public String encodeTobase64(Bitmap image) {

        byte[] array = copyToBuffer(image);


        String imageEncoded = Base64.encodeToString(array, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public Bitmap decodeBase64(String input) {

        byte[] encodeByte = Base64.decode(input, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }


    public static byte[] convertBitmapToByteArrayUncompressed(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        byte[] b = byteBuffer.array();
        return byteBuffer.array();
    }

    /**
     * Converts compressed byte array to bitmap
     *
     * @param src source array
     * @return result bitmap
     */
    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src) {
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }


    private byte[] copyToBuffer(Bitmap bitmap) {

        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());


        bitmap.copyPixelsToBuffer(buffer);
        boolean check = buffer.hasArray();
        buffer.rewind();
        if (buffer.hasArray()) {
            byte[] NewData = buffer.array();
            return NewData;
        } else return null;
    }


    public String getBase64Image(Bitmap bitmap) {
        try {
            ByteBuffer buffer =
                    ByteBuffer.allocate(bitmap.getRowBytes() *
                            bitmap.getHeight());
            bitmap.copyPixelsToBuffer(buffer);
            byte[] data = buffer.array();
            return Base64.encodeToString(data, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Bitmap StringToBitMap(String image) {
        try {

            byte[] encodeByte = Base64.decode(image, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
//            String decoded = new String(encodeByte);

//            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    // ******************************************** CHECK QR VALIDATION 1*************************************
    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdValidation = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidation.setTitleText(EditerCheackActivity.this.getResources().getString(R.string.verification));
            pdValidation.setCancelable(false);
            pdValidation.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("VerifyCheck 1067", "" + "JSONTask");

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(serverLink + "VerifyCheck?"));

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
//        Log.e("VerifyCheck 1112", "" + "JSONTask"+s.toString());
            Log.e("JSONTask", "JSONTaskpost");
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****Success");
                    Log.e("VerifyCheck 1116", "" + "JSONTask" + s.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(s);


                        CHECKNO = jsonObject.get("CHECKNO").toString();
                        ACCCODE = jsonObject.get("ACCCODE").toString();
                        IBANNO = jsonObject.get("IBANNO").toString();
                        CUSTOMERNM = jsonObject.get("CUSTOMERNM").toString();
                        QRCODE = jsonObject.get("QRCODE").toString();
                        SERIALNO = jsonObject.get("SERIALNO").toString();
                        BANKNO = jsonObject.get("BANKNO").toString();
                        BRANCHNO = jsonObject.get("BRANCHNO").toString();

                        pdValidation.dismissWithAnimation();

                        showValidationDialog(true, CUSTOMERNM, BANKNO, ACCCODE, CHECKNO);


//                        showSweetDialog(true, jsonObject.get("CUSTOMERNM").toString(), jsonObject.get("BANKNO").toString(), jsonObject.get("ACCCODE").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    showSweetDialog(false, "", "", "");

                    Log.e("tag", "****Failed to export data");
                }
            } else {
                showSweetDialog(false, "", "", "");

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    // ******************************************** Serial VALIDATION *************************************
//    private class JSONTaskSerial extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                Log.e("VerifyCheck 1067", "" + "JSONTask");
//
//                String JsonResponse = null;
//                HttpClient client = new DefaultHttpClient();
//                HttpPost request = new HttpPost();
//                request.setURI(new URI(serverLink + "VerifyCheckBySerial?"));
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                nameValuePairs.add(new BasicNameValuePair("SERIALNO", validateBySerial));
//
//                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                HttpResponse response = client.execute(request);
//
//                BufferedReader in = new BufferedReader(new
//                        InputStreamReader(response.getEntity().getContent()));
//
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                in.close();
//
//                JsonResponse = sb.toString();
//                Log.e("tag", "" + JsonResponse);
//
//                return JsonResponse;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//
//            ///
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
////        Log.e("VerifyCheck 1112", "" + "JSONTask"+s.toString());
//            if (s != null) {
//                if (s.contains("\"StatusDescreption\":\"OK\"")) {
//                    Log.e("tag", "****Success");
//                    Log.e("VerifyCheck 1116", "" + "JSONTask" + s.toString());
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//
//
//                        CHECKNO = jsonObject.get("CHECKNO").toString();
//                        ACCCODE = jsonObject.get("ACCCODE").toString();
//                        IBANNO = jsonObject.get("IBANNO").toString();
//                        CUSTOMERNM = jsonObject.get("CUSTOMERNM").toString();
//                        QRCODE = jsonObject.get("QRCODE").toString();
//                        SERIALNO = jsonObject.get("SERIALNO").toString();
//                        BANKNO = jsonObject.get("BANKNO").toString();
//                        BRANCHNO = jsonObject.get("BRANCHNO").toString();
//
//                        showValidationDialog(true, CUSTOMERNM, BANKNO, ACCCODE, CHECKNO);
//
//
////                        showSweetDialog(true, jsonObject.get("CUSTOMERNM").toString(), jsonObject.get("BANKNO").toString(), jsonObject.get("ACCCODE").toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else {
//
//                    showSweetDialog(false, "", "", "");
//
//                    Log.e("tag", "****Failed to export data");
//                }
//            } else {
//                showSweetDialog(false, "", "", "");
//
//                Log.e("tag", "****Failed to export data Please check internet connection");
//            }
//        }
//    }

    // ******************************************** SAVE *************************************
    private class SaveCheckTemp extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
            pd = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText(EditerCheackActivity.this.getResources().getString(R.string.save_success));
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = serverLink + "SaveTempCheck";


                String data = "CHECKINFO=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
                Log.e("jsonObject.toString()", "save -->" + jsonObject.toString());

                Log.e("tag", "dataSave  -->" + data);

                return stringBuffer.toString();

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
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Edit");
                    pd.dismissWithAnimation();
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    SweetAlertDialog sweet = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sweet.setTitleText("");
                    sweet.setContentText(EditerCheackActivity.this.getResources().getString(R.string.sent));
                    sweet.setCanceledOnTouchOutside(false);
                    sweet.setConfirmText("Ok");
                    sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            if (intentReSend != null && intentReSend.equals("ReSend")) {

                                updateNotificationState();
                            }
                            finish();
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sweet.show();
                    pushCheque.setEnabled(true);
                } else {
                    Log.e("tag", "****Failed to export data");

                    SweetAlertDialog sweet = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweet.setTitleText("WARNING");
                    sweet.setContentText("Fail to send!" + JsonResponse.toString());
                    sweet.setCanceledOnTouchOutside(false);
                    sweet.setConfirmText("Close");
                    sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sweet.show();

                    pd.dismissWithAnimation();

                    pushCheque.setEnabled(true);
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
                pd.dismissWithAnimation();
                pushCheque.setEnabled(true);
            }
        }
    }

    // ********************************************  Serial VALIDATION *************************************
    private class JSONTaskSerial extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = serverLink + "VerifyCheckBySerial";


                String data = "SERIALNO=" + URLEncoder.encode(validateBySerial, "UTF-8");
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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();



                return stringBuffer.toString();

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
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****Success");
                    Log.e("VerifyCheck 1116", "" + "JSONTask" + s.toString());
                    try {

                        JSONObject parentArray = new JSONObject(s);
                        JSONArray parentInfo = parentArray.getJSONArray("INFO");

//                    INFO":[{"ROWID":"AABX2UAAPAAAACDAA1","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390144;004;0099;1014569990011000","SERIALNO":"635088CD7E6D405B","CHECKISSUEDATE":"7\/2\/2020 12:51:57 PM","CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":"","TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":"","USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":""}]}


                            JSONObject finalObject = parentInfo.getJSONObject(0);


//      [{"NATID":"4236828854","FIRSTNM":"alaa","FATHERNM":"t","GRANDNM":"yg","FAMILYNM":"ug","DOB":"22\/07\/2020","GENDER":"1","MOBILENO":"962798899716","ADDRESS":"amman","EMIAL":"alaa@gmail.com","PASSWORD":"AalaaA7$","INACTIVE":"0","INDATE":"22\/07\/2020 17:36:22","PASSKIND":"0"}]}


//                            userSend.setNationalID(finalObject.getString("NATID"));
                            CHECKNO = finalObject.get("CHECKNO").toString();
                            ACCCODE = finalObject.get("ACCCODE").toString();
                            IBANNO = finalObject.get("IBANNO").toString();
                            CUSTOMERNM = finalObject.get("CUSTOMERNM").toString();
                            QRCODE = finalObject.get("QRCODE").toString();
                            SERIALNO = finalObject.get("SERIALNO").toString();
                            BANKNO = finalObject.get("BANKNO").toString();
                            BRANCHNO = finalObject.get("BRANCHNO").toString();


                        showValidationDialog(true, CUSTOMERNM, BANKNO, ACCCODE, CHECKNO);


//                        showSweetDialog(true, jsonObject.get("CUSTOMERNM").toString(), jsonObject.get("BANKNO").toString(), jsonObject.get("ACCCODE").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    showSweetDialog(false, "", "", "");

                    Log.e("tag", "****Failed to export data");
                }
            } else {
                showSweetDialog(false, "", "", "");

                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    // ******************************************** Check Pending 3*************************************
    private class IsCheckPinding extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));
            pdValidationDialog.getProgressHelper().setBarColor(Color.parseColor("#1E88E5"));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                Log.e("Edit_1494", "JSONTask dialog in ");

                String link = serverLink + "IsCheckPinding";

//ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144
                String data = "ACCCODE=" + URLEncoder.encode(ACCCODE, "UTF-8") + "&"
                        + "IBANNO=" + URLEncoder.encode(IBANNO, "UTF-8") + "&"
                        + "SERIALNO=" + URLEncoder.encode(SERIALNO, "UTF-8") + "&"
                        + "BANKNO=" + URLEncoder.encode(BANKNO, "UTF-8") + "&"
                        + "BRANCHNO=" + URLEncoder.encode(BRANCHNO, "UTF-8") + "&"
                        + "CHECKNO=" + URLEncoder.encode(CHECKNO, "UTF-8");
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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
                Log.e("tag", "dataSave  -->" + data);

                return stringBuffer.toString();

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
            Log.e("editorChequeActivity/", "saved//" + s);
            Log.e("Edit_1388", "JSONTask dialog in " + s.toString());
            Log.e("IsCheckPinding", "JSONTaskpost");


            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    pdValidationDialog.dismissWithAnimation();

                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.pending_))
                            .setContentText(EditerCheackActivity.this.getResources().getString(R.string.cantsendchech))
                            .setConfirmText(EditerCheackActivity.this.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();

                    pushCheque.setEnabled(true);
                } else if (s.contains("\"StatusDescreption\":\"Check not pindding.\"")) {
//                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("WARNING")
//                        .setContentText("Check not pinding!")
//                        .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//
//
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//
//                    }
//                })
//                        .show();


                    if (intentReSend != null && intentReSend.equals("ReSend")) {
                        new TillerGetCheck(chequeInfoReSendEd).execute();
                    } else {

                        ChequeInfo chequeInfo = new ChequeInfo();

                        chequeInfo.setAccCode(ACCCODE);
                        chequeInfo.setIbanNo(IBANNO);
                        chequeInfo.setSerialNo(SERIALNO);
                        chequeInfo.setBankNo(BANKNO);
                        chequeInfo.setBranchNo(BRANCHNO);
                        chequeInfo.setChequeNo(CHECKNO);
  
                        new IsCheckGero(chequeInfo).execute();
//                        linerEditing.setVisibility(View.VISIBLE);
//                        linerBarcode.setVisibility(View.GONE);

                    }


                    pushCheque.setEnabled(true);


                }
            } else {
                pdValidationDialog.dismissWithAnimation();

                Log.e("tag", "****Failed to export data");
                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.warning))
                        .setContentText(EditerCheackActivity.this.getResources().getString(R.string.failtoSend))
                        .setCancelText(EditerCheackActivity.this.getResources().getString(R.string.close)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                    }
                })
                        .show();
                pushCheque.setEnabled(true);

            }

        }
    }

    // ******************************************** Is Check For This Acc 2*************************************
    private class IsCheckForThisAcc extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdValidationDialog = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pdValidationDialog.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdValidationDialog.setTitleText(EditerCheackActivity.this.getResources().getString(R.string.verification));
            pdValidationDialog.setCancelable(false);
            pdValidationDialog.show();


//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = serverLink + "IsCheckForThisAcc";

//                ACCCODE=0014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390105&USERNO=0798899716

                String data = "ACCCODE=" + URLEncoder.encode(ACCCODE, "UTF-8") + "&"
                        + "IBANNO=" + URLEncoder.encode(IBANNO, "UTF-8") + "&"
                        + "SERIALNO=" + URLEncoder.encode(SERIALNO, "UTF-8") + "&"
                        + "BANKNO=" + URLEncoder.encode(BANKNO, "UTF-8") + "&"
                        + "BRANCHNO=" + URLEncoder.encode(BRANCHNO, "UTF-8") + "&"
                        + "CHECKNO=" + URLEncoder.encode(CHECKNO, "UTF-8") + "&"
                        + "USERNO=" + URLEncoder.encode(phoneNoUser, "UTF-8");
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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
                Log.e("tag", "dataSave  -->" + data);

                return stringBuffer.toString();

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
//            Log.e("editorChequeActivity/", "saved//" + s);
            Log.e("IsCheckForThisAcc", "JSONTaskpost");

            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {

                    new IsCheckPinding().execute();
                    pushCheque.setEnabled(true);
                } else if (s.contains("\"StatusDescreption\":\"This user not has this check.\"")) {
//                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("WARNING")
//                        .setContentText("Check not pinding!")
//                        .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//
//
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//
//                    }
//                })
//                        .show();
                    pdValidationDialog.dismissWithAnimation();

                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.cheq_acc))
                            .setContentText(EditerCheackActivity.this.getResources().getString(R.string.thisusernotcheq))
                            .setConfirmText(EditerCheackActivity.this.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
//                                    Intent intentGiro=new Intent(EditerCheackActivity.this,JeroActivity.class);
//                                    startActivity(intentGiro);

                                    sDialog.dismissWithAnimation();
                                }
                            }).show();


                    pushCheque.setEnabled(true);


                } else {
                    pdValidationDialog.dismissWithAnimation();
                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.warning))
                            .setContentText(EditerCheackActivity.this.getResources().getString(R.string.failtoSend) + s)
                            .setCancelText(EditerCheackActivity.this.getResources().getString(R.string.close)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    }).show();
                    pushCheque.setEnabled(true);
                }
            } else {
                pdValidationDialog.dismissWithAnimation();

                Log.e("tag", "****Failed to export data");
                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.warning))
                        .setContentText(EditerCheackActivity.this.getResources().getString(R.string.failtoSend))
                        .setCancelText(EditerCheackActivity.this.getResources().getString(R.string.close)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                    }
                }).show();
                pushCheque.setEnabled(true);

            }

        }
    }

    // ******************************************** Tiller Get Check 5*************************************
    public class TillerGetCheck extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        ChequeInfo chequeInfo;

        public TillerGetCheck(ChequeInfo chequeInfo) {
            this.chequeInfo = chequeInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdValidationDialog.getProgressHelper().setBarColor(Color.parseColor("#43A047"));

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                String link = serverLink + "TillerGetCheck";


//                ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144&USESERIAL=0

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "ACCCODE=" + URLEncoder.encode(chequeInfo.getAccCode(), "UTF-8") + "&" +
                        "IBANNO=" + URLEncoder.encode(chequeInfo.getIbanNo(), "UTF-8") + "&" +
                        "SERIALNO=" + URLEncoder.encode(chequeInfo.getSerialNo(), "UTF-8") + "&" +
                        "BANKNO=" + URLEncoder.encode(chequeInfo.getBankNo(), "UTF-8") + "&" +
                        "BRANCHNO=" + URLEncoder.encode(chequeInfo.getBranchNo(), "UTF-8") + "&" +
                        "CHECKNO=" + URLEncoder.encode(chequeInfo.getChequeNo(), "UTF-8") + "&" +
                        "USESERIAL=" + URLEncoder.encode("0", "UTF-8");

                URL url = new URL(link);
                Log.e("link,3 ", serverLink + "   " + link + "   " + data);

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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            Log.e("TillerGetCheck", "JSONTaskpost");
            pdValidationDialog.dismissWithAnimation();

            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

//                    INFO":[{"ROWID":"AABX2UAAPAAAACDAA1","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390144;004;0099;1014569990011000","SERIALNO":"635088CD7E6D405B","CHECKISSUEDATE":"7\/2\/2020 12:51:57 PM","CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":"","TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":"","USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":""}]}


                    List<ChequeInfo> chequeInfoTilar = new ArrayList<>();
                    boolean foundIn = false,isCashed= false;

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        if (finalObject.getString("TOCUSTOMERMOB").equals(phoneNoUser) || finalObject.getString("TOCUSTOMERMOB").equals("")) {
                            //[{"ROWID":"AAAp0DAAuAAAAC0AAC","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968","CHECKISSUEDATE":"28\/06\/2020 10:33:57","CHECKDUEDATE":"21\/12\/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0","AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKWRITEDATE":"28\/06\/2020 10:33:57","CHECKPICPATH":"E:\\00400991014569990011000390144.png","TRANSSTATUS":""}]}

                            obj.setRowId(finalObject.getString("ROWID"));
                            obj.setBankNo(finalObject.getString("BANKNO"));


                            obj.setBankName(finalObject.getString("BANKNM"));
                            obj.setBranchNo(finalObject.getString("BRANCHNO"));

                            obj.setChequeNo(finalObject.getString("CHECKNO"));
                            obj.setAccCode(finalObject.getString("ACCCODE"));

                            obj.setIbanNo(finalObject.getString("IBANNO"));
                            obj.setCustName(finalObject.getString("CUSTOMERNM"));

                            obj.setQrCode(finalObject.getString("QRCODE"));
                            obj.setSerialNo(finalObject.getString("SERIALNO"));

                            obj.setCheckIsSueDate(finalObject.getString("CHECKISSUEDATE"));//?
                            obj.setCheckDueDate(finalObject.getString("CHECKDUEDATE"));//?

                            obj.setToCustomerName(finalObject.getString("TOCUSTOMERNM"));
                            obj.setMoneyInDinar(finalObject.getString("AMTJD"));

                            obj.setMoneyInFils(finalObject.getString("AMTFILS"));
                            obj.setMoneyInWord(finalObject.getString("AMTWORD"));

                            obj.setToCustomerMobel(finalObject.getString("TOCUSTOMERMOB"));

                            obj.setToCustomerNationalId(finalObject.getString("TOCUSTOMERNATID"));
                            obj.setCustomerWriteDate(finalObject.getString("CHECKWRITEDATE"));//?

//                        obj.setCheqPIc(finalObject.getString("CHECKPICPATH"));
//                            obj.setTransType(finalObject.getString("TRANSSTATUS"));
//                            obj.setStatus(finalObject.getString("STATUS"));
                            obj.setUserName(finalObject.getString("USERNO"));

                            obj.setISBF(finalObject.getString("ISFB"));
                            obj.setISCO(finalObject.getString("ISCO"));

                            obj.setISOpen("0");


                            chequeInfoTilar.add(obj);

                            foundIn = true;

                            if(finalObject.getString("TOCUSTOMERMOB").equals(finalObject.getString("OWNERMOBNO"))){

                                isCashed=true;

                            }

                        }
                    }

                    if (foundIn) {
                        if(!isCashed) {
                            if (intentReSend != null && intentReSend.equals("ReSend")) {
                                fillTheCheck(chequeInfoReSendEd);
                                linerEditing.setVisibility(View.VISIBLE);
                                linerBarcode.setVisibility(View.GONE);
                            } else {
                                linerEditing.setVisibility(View.VISIBLE);
                                linerBarcode.setVisibility(View.GONE);
                            }
                        }else{

                            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.cheque))
                                    .setContentText(EditerCheackActivity.this.getResources().getString(R.string.chequeCashed))
                                    .show();

                        }

                    } else {
                        if (intentReSend != null && intentReSend.equals("ReSend")) {
                            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.ResendCheque))
                                    .setContentText(EditerCheackActivity.this.getResources().getString(R.string.chequeCurrentlyNotYour))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            finish();
//
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        } else {
                            new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.sendCheque))
                                    .setContentText(EditerCheackActivity.this.getResources().getString(R.string.chequeCurrentlyNotYour))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            finish();
//
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }//

            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Check Data not found")) {
                Log.e("TAG_GetStor", "****Check Data not found");


                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(EditerCheackActivity.this.getResources().getString(R.string.cheque))
                        .setContentText("Check Data not found")
                        .show();

            }

        }
    }

    // ******************************************** Is Check Gero 4*************************************
    public class IsCheckGero extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        ChequeInfo chequeInfo;

        public IsCheckGero(ChequeInfo chequeInfo) {
            this.chequeInfo = chequeInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdValidationDialog.getProgressHelper().setBarColor(Color.parseColor("#F4511E"));

//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                String link = serverLink + "IsCheckGero";


//                ACCCODE=0014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390105
                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "ACCCODE=" + URLEncoder.encode(chequeInfo.getAccCode(), "UTF-8") + "&" +
                        "IBANNO=" + URLEncoder.encode(chequeInfo.getIbanNo(), "UTF-8") + "&" +
                        "SERIALNO=" + URLEncoder.encode(chequeInfo.getSerialNo(), "UTF-8") + "&" +
                        "BANKNO=" + URLEncoder.encode(chequeInfo.getBankNo(), "UTF-8") + "&" +
                        "BRANCHNO=" + URLEncoder.encode(chequeInfo.getBranchNo(), "UTF-8") + "&" +
                        "CHECKNO=" + URLEncoder.encode(chequeInfo.getChequeNo(), "UTF-8");


                URL url = new URL(link);
                Log.e("link,3 ", serverLink + "   " + link + "   " + data);

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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            Log.e("IsCheckGero", "JSONTaskpost");
            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

//                    INFO":[{"ROWID":"AABX2UAAPAAAACDAA1","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390144;004;0099;1014569990011000","SERIALNO":"635088CD7E6D405B","CHECKISSUEDATE":"7\/2\/2020 12:51:57 PM","CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":"","TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":"","USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":""}]}


                    List<ChequeInfo> chequeInfoGiro = new ArrayList<>();
                    boolean foundIn = false;

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        if (finalObject.getString("TOCUSTOMERMOB").equals(phoneNoUser)) {
                            if (!finalObject.getString("OWNERMOBNO").equals(phoneNoUser)) {
//                            "INFO":[{"ROWID":"AAAq3rAAuAAAADeAAB","BANKNO":"004","BANKNM":"Jordan Bank","BRANCHNO":"0099","CHECKNO":"390105","ACCCODE":"0014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"ADA2B3D052C54199","CHECKISSUEDATE":"01\/07\/2020 18:29:14","CHECKDUEDATE":"01\/07\/2020","TOCUSTOMERNM":"GggHhVhGg","AMTJD":"4444","AMTFILS":"44","AMTWORD":"اربعة آلاف و اربعمائة و اربعة و اربعون ديناراً و 440 فلساً","TOCUSTOMERMOB":"0772095887","TOCUSTOMERNATID":"0788588868","CHECKWRITEDATE":"01\/07\/2020 18:29:14","CHECKPICPATH":"Z:\\FS_Acc_Stk\\Web Services\\ChequesScan\\Win32\\Debug\\Images\\00400990014569990011000390105.txt","TRANSSTATUS":"1","USERNO":"0786812709","ISCO":"0","ISFB":"0","COMPANY":"","NOTE":"","TRANSTYPE":"1","RJCTREASON":""}]}


                                obj.setRowId(finalObject.getString("ROWID"));
                                obj.setBankNo(finalObject.getString("BANKNO"));


                                obj.setBankName(finalObject.getString("BANKNM"));
                                obj.setBranchNo(finalObject.getString("BRANCHNO"));

                                obj.setChequeNo(finalObject.getString("CHECKNO"));
                                obj.setAccCode(finalObject.getString("ACCCODE"));

                                obj.setIbanNo(finalObject.getString("IBANNO"));
                                obj.setCustName(finalObject.getString("CUSTOMERNM"));

                                obj.setQrCode(finalObject.getString("QRCODE"));
                                obj.setSerialNo(finalObject.getString("SERIALNO"));

                                obj.setCheckIsSueDate(finalObject.getString("CHECKISSUEDATE"));//?
                                obj.setCheckDueDate(finalObject.getString("CHECKDUEDATE"));//?

                                obj.setToCustomerName(finalObject.getString("TOCUSTOMERNM"));
                                obj.setMoneyInDinar(finalObject.getString("AMTJD"));

                                obj.setMoneyInFils(finalObject.getString("AMTFILS"));
                                obj.setMoneyInWord(finalObject.getString("AMTWORD"));

                                obj.setToCustomerMobel(finalObject.getString("TOCUSTOMERMOB"));

                                obj.setToCustomerNationalId(finalObject.getString("TOCUSTOMERNATID"));
                                obj.setCustomerWriteDate(finalObject.getString("CHECKWRITEDATE"));//?

//                        obj.setCheqPIc(finalObject.getString("CHECKPICPATH"));
//                            obj.setTransType(finalObject.getString("TRANSSTATUS"));
//                            obj.setStatus(finalObject.getString("STATUS"));
                                obj.setUserName(finalObject.getString("USERNO"));

                                obj.setISBF(finalObject.getString("ISFB"));
                                obj.setISCO(finalObject.getString("ISCO"));

                                obj.setISOpen("0");


                                chequeInfoGiro.add(obj);

                                foundIn = true;
                            }

                        }
                    }

                    if (!foundIn) {

                        Log.e("chequeGiro 2010", "not giro" + JsonResponse.toString());
//                        pdValidationDialog.dismissWithAnimation();
                        new TillerGetCheck(chequeInfo).execute();

                    } else {
//                        new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText(" Cheque ")
//                                .setContentText("This check is Giro can't Send !!!")
//                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @SuppressLint("WrongConstant")
//                                    @Override
//                                    public void onClick(SweetAlertDialog sDialog) {
//                                        finish();
//                                        sDialog.dismissWithAnimation();
//                                    }
//                                }).show();

                        new TillerGetCheck(chequeInfo).execute();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }//

            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Check Is not Gero")) {
                Log.e("CheckIsnotGero", "****Check Data not found");


//                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText(" Cheque ")
//                        .setContentText("Check Data not found")
//                        .show();

                new TillerGetCheck(chequeInfo).execute();


            }

        }
    }


    public class GetUserInfoByMobo extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        SweetAlertDialog pdaSweet;
        String ToPhoneNo = "";

        public GetUserInfoByMobo(String ToPhoneNo) {
            this.ToPhoneNo = ToPhoneNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));
            userFound = false;
            pdaSweet = new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pdaSweet.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pdaSweet.setTitleText("Process...");
            pdaSweet.setCancelable(false);
            pdaSweet.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                String link = serverLink + "GetUserInfoByMobo";


//                ACCCODE=0014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390105
                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "MOBNO=" + URLEncoder.encode(ToPhoneNo, "UTF-8");


                URL url = new URL(link);
                Log.e("phoneNoUser ,3 ", serverLink + "   " + link + "   " + data + " " + ToPhoneNo);

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

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("GetUserInfoByMobo", "tag -->" + stringBuffer.toString());

                return stringBuffer.toString();

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

//                    INFO":[{"ROWID":"AABX2UAAPAAAACDAA1","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390144;004;0099;1014569990011000","SERIALNO":"635088CD7E6D405B","CHECKISSUEDATE":"7\/2\/2020 12:51:57 PM","CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":"","TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":"","USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":""}]}


                    List<LoginINFO> chequePhone = new ArrayList<>();


                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        userSend = new LoginINFO();

//      [{"NATID":"4236828854","FIRSTNM":"alaa","FATHERNM":"t","GRANDNM":"yg","FAMILYNM":"ug","DOB":"22\/07\/2020","GENDER":"1","MOBILENO":"962798899716","ADDRESS":"amman","EMIAL":"alaa@gmail.com","PASSWORD":"AalaaA7$","INACTIVE":"0","INDATE":"22\/07\/2020 17:36:22","PASSKIND":"0"}]}


                        userSend.setNationalID(finalObject.getString("NATID"));
                        userSend.setFirstName(finalObject.getString("FIRSTNM"));


                        userSend.setSecondName(finalObject.getString("FATHERNM"));
                        userSend.setThirdName(finalObject.getString("GRANDNM"));

                        userSend.setFourthName(finalObject.getString("FAMILYNM"));
                        userSend.setBirthDate(finalObject.getString("DOB"));

                        userSend.setGender(finalObject.getString("GENDER"));
                        userSend.setUsername(finalObject.getString("MOBILENO"));

                        userSend.setAddress(finalObject.getString("ADDRESS"));
                        userSend.setEmail(finalObject.getString("EMIAL"));

                        userSend.setPassword(finalObject.getString("PASSWORD"));//?
                        userSend.setInactive(finalObject.getString("INACTIVE"));//?

                        userSend.setNationality(finalObject.getString("PASSKIND"));

                        chequePhone.add(userSend);

                    }

                    nationalNo.setText(userSend.getNationalID());
                    fName.setText(userSend.getFirstName());
                    sName.setText(userSend.getSecondName());
                    tName.setText(userSend.getThirdName());
                    fourthName.setText(userSend.getFourthName());

                    isInGetData = true;
                    userFound = true;
                    pdaSweet.dismissWithAnimation();

                } catch (JSONException e) {
                    e.printStackTrace();
                }//

            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"User Not found.")) {
                isInGetData = true;
                nationalNo.setText("");
                fName.setText("");
                sName.setText("");
                tName.setText("");
                fourthName.setText("");

                Log.e("StatusDescreption", "****User Not found.");
                pdaSweet.dismissWithAnimation();

                userFound = false;
                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.cannot_send))
                        .setContentText(getString(R.string.please_install_app))
                        .show();


            }

        }
    }


    void imageSend() {

        Bitmap bitmap = serverPicBitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String result = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("image", result));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.0.16/images/");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String the_string_response = convertResponseToString(response);
            Toast.makeText(EditerCheackActivity.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(EditerCheackActivity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("Error in http connection " + e.toString());
        }

    }


    public void showImageOfCheck(Bitmap bitmap) {
        final Dialog dialog = new Dialog(EditerCheackActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pic_show);
        dialog.show();

        final ImageView imageView = (ImageView) dialog.findViewById(R.id.image_check);
        imageView.setImageBitmap(bitmap);


    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        Toast.makeText(EditerCheackActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0) {
        } else {
            byte[] data = new byte[512];
            int len = 0;
            try {
                while (-1 != (len = inputStream.read(data))) {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close(); // closing the stream…..
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            Toast.makeText(EditerCheackActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    File creatFile(Bitmap finalBitmap) {

        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pngdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
            Log.i("Created", "Pdf Directory created");
        }

        //Create time stamp
//        Date date = new Date() ;
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        File myFile = new File(pdfFolder + "kk" + ".png");
        try {
            FileOutputStream out = new FileOutputStream(myFile);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(EditerCheackActivity.this, new String[]{myFile.getPath()}, new String[]{"image/png"}, null);


        return myFile;
    }

//void im(File myFile){
////    File myFile = new File(path);
//    RequestParams params = new RequestParams();
//    try {
//        params.put("images", myFile);
//    } catch(FileNotFoundException e) {}
//
//// send request
//    try {
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.post("//10.0.0.16/", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
//                // handle success response
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
//                // handle failure response
//            }
//        });
//
//    }catch (Exception e){
//        Log.e("Esss",""+e.toString());
//    }
//}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String addPicToGallery(Bitmap finalBitmap) {
        File root = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        File myDir = new File(String.valueOf(root));
        myDir.mkdirs();
        String fname = "Image_" + "jjjj" + ".png";
        File file = new File(myDir, fname);
        System.out.println(file.getAbsolutePath());
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(root);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 600, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(EditerCheackActivity.this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
        return file.getPath();
    }

    public void uploadMultipart(String filePath) {
        //getting name for the image
        String name = "imagess";

        //getting the actual path of the image
        String path = filePath;

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
//            new MultipartUploadRequest(this, uploadId, "file://10.0.0.16/images")
//                    .addFileToUpload(path, "image") //Adding file
//                    .addParameter("name", name) //Adding text parameter to the request
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(2)
//                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class Image extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = serverLink + "GetCheckPic?ACCCODE=4014569990011000&BANKNO=004&BRANCHNO=0099&CHECKNO=390092";


//                String data = "CHECKINFO=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
//
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

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
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Edit");

                    JSONObject jsonObject = null;


                    try {
                        jsonObject = new JSONObject(s);
//                        saveJSONData(EditerCheackActivity.this,jsonObject.getString("CHECKPIC"));
//
//                       serverPicBitmap= StringToBitMap(getJSONData(EditerCheackActivity.this));
//
//
                        serverPicBitmap = StringToBitMap(jsonObject.getString("CHECKPIC"));


                        Log.e("CHECKPIC1", "" + jsonObject.getString("CHECKPIC"));
//                        serverPicBitmap= StringToBitMap(  "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");


                        showImageOfCheck(serverPicBitmap);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
//                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText("Successful")
//                            .setContentText("Save Successful")
//                            .setConfirmText("Ok")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @SuppressLint("WrongConstant")
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    finish();
//                                    sDialog.dismissWithAnimation();
//                                }
//                            }).show();
                } else {
                    Log.e("tag", "****Failed to export data");
                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
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

//    public void uploadProfileImage(final String fileName){
//        byte[] imageBytes = getBytesImage(bitmap);
//       HttpClient httpclient = new DefaultHttpClient();
//        httpPost = new HttpPost(“URL to upload image to...“);
//        String boundary = "-------------" + System.currentTimeMillis();
//        httpPost.setHeader("Content-type", "multipart/form-data;
//                boundary="+boundary);
//                ByteArrayBody bab = new ByteArrayBody(imageBytes,fileName);
//        StringBody userId = new StringBody(mPrefs.getUser().getId(),
//                ContentType.TEXT_PLAIN);
//        StringBody type = new StringBody("baby",ContentType.TEXT_PLAIN);
//        HttpEntity entity = MultipartEntityBuilder.create()
//                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                .setBoundary(boundary)
//                .addPart("imageUpload",bab)
//                .addPart("userid",userId)
//                .addPart("type",type)
//                .build();
//        httpPost.setEntity(entity);
//        class UploadImage extends AsyncTask<Void,Void,String> {
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(),"Please
//                        wait...","uploading picture",false,false);
//            }
//            @Override
//            protected void onPostExecute(String s){
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void...param){
//                String res = "";
//                HttpResponse response;
//                try{
//                    response = httpclient.execute(httpPost);
//                    res = response.getStatusLine().toString();
//                    User user = mPrefs.getUser();
//                    user.setProfileImageUrl(PICTURE_URL+fileName);
//                    mPrefs.saveUser(user);
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//                return "Profile image upload successful"
//            }
//        }
//        UploadImage u = new UploadImage();
//        u.execute();
//    }

    public void saveJSONData(Context context, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/pic");
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    public String getJSONData(Context context) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/pic");
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

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
                request.setURI(new URI(serverLink + "SaveTempCheck?"));

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
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Edit");
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("")
                            .setContentText(EditerCheackActivity.this.getResources().getString(R.string.save_success))
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
                    new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("gg1", "Permission is granted");
                return true;
            } else {

                Log.e("gg2", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("gg3", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("jj4", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
//            if(flagINoUT==1){
//                ExportDbToExternal();
//            }else if (flagINoUT==2){
//                ImportDbToMyApp();
//            }

            cameraIntent();

        }
    }

    private void updateNotificationState() {
        new JSONUpdateNotificationTask().execute();
    }
    class JSONUpdateNotificationTask extends AsyncTask<String, String, String> {

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
                SharedPreferences loginPrefs1 = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
                String serverLink = loginPrefs1.getString("link", "");
//                http://localhost:8082/UpdateNotfication?ROWID=&STATUS=1
                request.setURI(new URI(serverLink+"UpdateNotfication?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("ROWID", chequeInfoReSendEd.getNOTFROWID()));
                nameValuePairs.add(new BasicNameValuePair("STATUS", "1"));

                Log.e("STATUS", "" +  chequeInfoReSendEd.getNOTFROWID());

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

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
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
                    Log.e("AdapteronPostExecute", "OK");

                    progressDialog.dismiss();

                } else {
                    Log.e("tagAdapter", "****Failed to Savedata");
                }
            } else {

                Log.e("tagAdapter", "****Failed  Please check internet connection");
            }
        }
    }
}
