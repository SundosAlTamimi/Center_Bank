package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.Models.Setting;
import com.falconssoft.centerbank.databinding.LogInBinding;
import com.falconssoft.centerbank.mail.LongOperation;
import com.falconssoft.centerbank.viewmodel.SignupVM;
import com.google.android.material.snackbar.Snackbar;
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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.centerbank.LocaleAppUtils.language;
import static com.falconssoft.centerbank.SingUpActivity.PAGE_NAME;

public class LogInActivity extends AppCompatActivity {

    //    private EditText userName, password;
//    private Button singIn, singUp;
//    private String language = "";
    private ImageView SettingImage, close;//, seen;
    private DatabaseHandler databaseHandler;
    private Animation animation;
    public static final String LANGUAGE_FLAG = "LANGUAGE_FLAG";
    public static final String LOGIN_INFO = "LOGIN_INFO";
    private String[] array;
    private String checkNo = "", accountCode = "", ibanNo = "", customerName = "", qrCode = "", serialNo = "", bankNo = "", branchNo = "", countryCode = "962", countryCodeForgetPassword = "962";
    private TextView bankNameTV, chequeWriterTV, chequeNoTV, accountNoTV, okTV, cancelTV;
    private Dialog barcodeDialog;
    private SharedPreferences.Editor editor, edit;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    private LinearLayout coordinatorLayout;
    private boolean flag = false;
    private LinearLayout phoneLinear, emailLinear, passwordLinear;
    private LogInBinding binding;
    private SignupVM signupVM = new SignupVM();
    private ButtonsClickHandler buttonsClickHandler;
    private ArrayList<String> spinnerList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int checkedRemember = -1;// -1 => mean not checked , 0 => not checked, 1=> checked
    public static String ROW_ID_PREFERENCE = "ROW_ID_PREFERENCE";
    private CountryCodePicker ccp;
    String serverLink = "";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = LogInBinding.inflate(getLayoutInflater());

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");
        editor = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE).edit();
//        editor.putString("link", "http://10.0.0.16:8082/");
        editor.putString("link", "http://falconssoft.net/ScanChecks/APIMethods.dll/");
        editor.apply();

        new LocaleAppUtils().changeLayot(LogInActivity.this);

        binding = DataBindingUtil.setContentView(this, R.layout.log_in);
//        setContentView(R.layout.log_in);//binding.getRoot()
        if (language.equals("ar"))
            binding.LogInPassword.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        init();
//        databaseHandler.deleteLoginInfo();

        buttonsClickHandler = new ButtonsClickHandler(this);
        binding.setClickHandler(buttonsClickHandler);

        Log.e("editing,login ", language);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

//        SettingImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addSettingButton();
//            }
//        });

        SharedPreferences loginPrefs1 = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs1.getString("link", "");

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        binding.loginPhoneLinear.startAnimation(animation);//userName

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        passwordLinear.startAnimation(animation);

        binding.LogInUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                checkIfIsRemember(String.valueOf(charSequence));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                checkIfIsRemember(String.valueOf(charSequence));
//                Log.e("tracking", countryCode + charSequence);
                if (!TextUtils.isEmpty(String.valueOf(charSequence)) && String.valueOf(charSequence).length() > 1) {
                    if (!TextUtils.isEmpty(databaseHandler.getLoginInfo(countryCode + charSequence).getUsername())) {
                        binding.loginSearch.setVisibility(View.VISIBLE);
                        binding.loginSearch.setText(databaseHandler.getLoginInfo(countryCode + charSequence).getUsername());
                    }
                } else {
                    binding.loginSearch.setVisibility(View.GONE);
                    binding.loginSearch.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.loginCcp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = binding.loginCcp.getSelectedCountryCode();

            }
        });

        binding.setLoginModel(signupVM);

    }


    public class ButtonsClickHandler implements CompoundButton.OnCheckedChangeListener {
        Context context;

        public ButtonsClickHandler(Context context) {
            this.context = context;
        }

        public void onClickLogin(View view) {
//            Toast.makeText(context, signupVM.getUsername(), Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(binding.LogInUserName.getText().toString()))//userName.getText().toString()))// if (signupVM.getUsername().length() == 10)//userName.length() == 10)
                if (!TextUtils.isEmpty(binding.LogInPassword.getText().toString())) {//password.getText().toString())) {
//                            userName.setError(null);
//                            password.setError(null);
//                        binding.LogInUserName.setError(null);
//                        binding.LogInPassword.setError(null);
                    binding.setError(null);

                    LoginINFO user = new LoginINFO();
                    user.setUsername(countryCode + binding.LogInUserName.getText().toString());
                    user.setPassword(binding.LogInPassword.getText().toString());
                    user.setIsRemember(checkedRemember);
                    Log.e("fromlogin", user.getUsername() + "******************" + user.getPassword());
                    user.setIsNowActive(0);
                    showDialog();
                    new Presenter(LogInActivity.this).loginInfoCheck(LogInActivity.this, user);
                } else {
                    binding.setError(getResources().getString(R.string.required));
                }
//                else {
//                    binding.setError("Phone number not correct!!");
//                }
            else {
                binding.setError("Required!");
            }
        }

        public void onClickSignup(View view) {
            Intent mainActivityIntent = new Intent(LogInActivity.this, SingUpActivity.class);
            startActivity(mainActivityIntent);
        }

        public void onClickCheckValidation(View view) {

            barcodeDialog = new Dialog(LogInActivity.this);
            barcodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            barcodeDialog.setContentView(R.layout.check_validation_layout);

            TextView scan = barcodeDialog.findViewById(R.id.checkValidation_scanBarcode);
            ImageView close = barcodeDialog.findViewById(R.id.checkValidation_close);
            LinearLayout headerLinear = barcodeDialog.findViewById(R.id.checkValidation_headerLinear);
            TextView haveAProblem = barcodeDialog.findViewById(R.id.checkValidation_help);
            TextView scanTV = barcodeDialog.findViewById(R.id.checkValidation_scanLinear);

            final LinearLayout serialLinear = barcodeDialog.findViewById(R.id.checkValidation_serial_linear);
            final TextInputEditText serial = barcodeDialog.findViewById(R.id.checkValidation_serial);
            TextView check = barcodeDialog.findViewById(R.id.checkValidation_check);
            serialLinear.setVisibility(View.GONE);

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
                        new Presenter(LogInActivity.this).checkBySerial(serial.getText().toString().toUpperCase(), LogInActivity.this, null, null);
                    } else {
                        serial.setError("Required");
                    }

                }
            });

            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(LogInActivity.this);
                    intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setPrompt("SCAN");
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barcodeDialog.dismiss();
                }
            });

            Log.e("checkLang", language);
            if (language.equals("ar")) {
                headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                check.setGravity(Gravity.RIGHT);

                haveAProblem.setCompoundDrawablesWithIntrinsicBounds(null, null
                        , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_help), null);
                scanTV.setCompoundDrawablesWithIntrinsicBounds(null, null
                        , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_phone), null);

            } else {
                headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                check.setGravity(Gravity.LEFT);

                haveAProblem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_help), null
                        , null, null);
                scanTV.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_phone), null
                        , null, null);

            }

            barcodeDialog.show();
        }

        public void onClickEnglish(View view) {
            language = "en";
            editor = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE).edit();
            editor.putString("language", "en");
            editor.apply();

            LocaleAppUtils.setLocale(new Locale("en"));
            LocaleAppUtils.setConfigChange(LogInActivity.this);
            finish();
            startActivity(getIntent());
        }

        public void onClickArabic(View view) {
            language = "ar";
            editor = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE).edit();
            editor.putString("language", "ar");
            editor.apply();

            LocaleAppUtils.setLocale(new Locale("ar"));
            LocaleAppUtils.setConfigChange(LogInActivity.this);
            finish();
            startActivity(getIntent());
        }

        public void onClickForgetPassword(View view) {
            Dialog dialog = new Dialog(LogInActivity.this);
            dialog.setContentView(R.layout.dialog_forget_password);

            phoneLinear = dialog.findViewById(R.id.forgetPassword_phone_linear);
            final EditText phone = dialog.findViewById(R.id.forgetPassword_phone);
            final Button phoneSend = dialog.findViewById(R.id.forgetPassword_phone_send);
            RadioButton phoneRB = dialog.findViewById(R.id.forgetPassword_phone_rb);
            emailLinear = dialog.findViewById(R.id.forgetPassword_email_linear);
            final EditText email = dialog.findViewById(R.id.forgetPassword_email);
            final Button emailSend = dialog.findViewById(R.id.forgetPassword_email_send);
            RadioButton emailRB = dialog.findViewById(R.id.forgetPassword_email_rb);
            RadioGroup radioGroup = dialog.findViewById(R.id.forgetPassword_rg);
            TextView textView = dialog.findViewById(R.id.forgetPassword_tv);
            ccp = dialog.findViewById(R.id.forgetPassword_ccp);

            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (language.equals("ar")) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null
                        , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_touch), null);
                LinearLayout.LayoutParams tvBoxParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvBoxParam.gravity = Gravity.RIGHT;
                textView.setLayoutParams(tvBoxParam);

                RadioGroup.LayoutParams phoneParam = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                phoneParam.gravity = Gravity.RIGHT;
                radioGroup.setLayoutParams(phoneParam);

//                emailRB.setGravity(Gravity.RIGHT);
//                emailRB.setButtonDrawable(android.R.drawable.btn_radio);
//                LinearLayout.LayoutParams emailParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                emailParam.gravity = Gravity.RIGHT;
//                emailSend.setLayoutParams(emailParam);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    if (id == R.id.forgetPassword_email_rb) {
                        phoneLinear.setVisibility(View.GONE);
                        emailLinear.setVisibility(View.VISIBLE);

                        emailSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(email.getText().toString())) {
                                    try {

                                        String emailSender = "hiary.abeer96@gmail.com", password = "000", emailReceiver = "hiary.abeer@yahoo.com", userName = "Cheque App";

                                        LongOperation l = new LongOperation(emailSender
                                                , password
                                                , emailReceiver
                                                , userName);
                                        l.execute();  //sends the email in background
                                        Toast.makeText(LogInActivity.this, l.get(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Log.e("SendMail", e.getMessage(), e);
                                    }

                                } else
                                    Toast.makeText(LogInActivity.this, "Please fill email first!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        phoneLinear.setVisibility(View.VISIBLE);
                        emailLinear.setVisibility(View.GONE);

                        phoneSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(phone.getText().toString())) {

                                } else
                                    Toast.makeText(LogInActivity.this, "Please fill phone first!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });

            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected() {
                    countryCodeForgetPassword = ccp.getSelectedCountryCode();

                }
            });

            dialog.show();
//            Window window = dialog.getWindow();
//            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        public void onClickSearchPhone(View view) {
            String splitString = binding.loginSearch.getText().toString().substring(binding.loginSearch.getText().toString().length() - 9);
            binding.LogInUserName.setText(splitString);
            binding.LogInPassword.setText(databaseHandler.getUserInfo(binding.loginSearch.getText().toString()).getPassword());
//            Log.e("split",binding.loginSearch.getText().toString().substring(binding.loginSearch.getText().toString().length() - 9));
            binding.loginRememberMe.setChecked(true);
            binding.loginSearch.setVisibility(View.GONE);


//            signupVM.setUsername(signupVM.getSearchPhone());
//            signupVM.setPassword(databaseHandler.getUserInfo(signupVM.getSearchPhone()).getPassword());
//            binding.loginRememberMe.setChecked(true);
//            binding.loginSearch.setVisibility(View.GONE);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                checkedRemember = 1;
            else
                checkedRemember = 0;

        }
    }


    public String convertToArabic(String value) {
        String newValue = (((((((((((value + "").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
        Log.e("convertToArabic", value + "      " + newValue);
        return newValue;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
            } else {

                Log.e("MainActivity", "" + Result.getContents());
//                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
//                openEditerCheck();

                String ST = Result.getContents();
                array = ST.split(";");

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
    }

    public void showValidationDialog(boolean check, String customerName, String BankNo, String accountNo, String chequeNo) {
        if (check) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_after_validation);
            dialog.setCancelable(false);

            bankNameTV = dialog.findViewById(R.id.dialog_validation_bankName);
            chequeWriterTV = dialog.findViewById(R.id.dialog_validation_chequeWriter);
            chequeNoTV = dialog.findViewById(R.id.dialog_validation_chequeNo);
            accountNoTV = dialog.findViewById(R.id.dialog_validation_accountNo);
            okTV = dialog.findViewById(R.id.dialog_validation_ok);
            cancelTV = dialog.findViewById(R.id.dialog_validation_cancel);
            cancelTV.setVisibility(View.GONE);


            if (language.trim().equals("ar")) {
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(LogInActivity.this);
                chequeWriterTV.setText(customerName);
                accountNoTV.setText(convertToArabic(accountNo));
                chequeNoTV.setText(convertToArabic(chequeNo));
            } else {
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(LogInActivity.this);
                chequeWriterTV.setText(customerName);
                accountNoTV.setText(accountNo);
                chequeNoTV.setText(chequeNo);
            }
            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    new IsCheckPinding().execute();
                    barcodeDialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WARNING")
                    .setContentText("Invalidate cheque!")
                    .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                }
            }).show();
        }

    }


    void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.dismiss();
    }

    public void goToTheMainPage(String message, LoginINFO user) {
        hideDialog();

        if (message != null && message.contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\",\"INFO\"")) {//"StatusCode":10,"StatusDescreption":"User not found."
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            LoginINFO currentUser = databaseHandler.getUserInfo(user.getUsername());
            Log.e("Username", "" + currentUser.getUsername());
            Log.e("remember/Active", "" + user.getIsRemember() + user.getIsNowActive());

            if (TextUtils.isEmpty(currentUser.getUsername())) {
                databaseHandler.addLoginInfo(user);
                Log.e("remember/Active2", "" + user.getIsRemember() + user.getIsNowActive());
            } else if (!TextUtils.isEmpty(currentUser.getUsername()) && user.getIsRemember() != -1)
                databaseHandler.updateRememberState(user.getIsRemember(), user.getUsername());


            Log.e("fromlogin22", user.getUsername() + checkedRemember);

            databaseHandler.updateLoginActive(user.getUsername());
//            checkIfIsRemember();

            editor = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE).edit();
            editor.putString("mobile", user.getUsername());
            editor.putString("password", user.getPassword());
            editor.putString("name", user.getFirstName());

//          editor.putString("link", "http://10.0.0.16:8081/");
            editor.apply();

            Intent MainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(MainActivityIntent);
        } else if (message != null && message.contains("\"StatusCode\":10,\"StatusDescreption\":\"User not found.\""))
            showSnackbar(getResources().getString(R.string.user_not_found), false);
        else
            showSnackbar(getResources().getString(R.string.check_internet_connection), false);

    }

    void addSettingButton() {
        final Dialog dialog = new Dialog(LogInActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_);
        dialog.setCancelable(false);

        final TextInputEditText inputEditText = dialog.findViewById(R.id.dialog_addSetting_Setting);
        TextView close = dialog.findViewById(R.id.dialog_add_close);
        TextView add = dialog.findViewById(R.id.dialog_addSetting_Save);

        Setting setting = databaseHandler.getAllSetting();
        if (!TextUtils.isEmpty(setting.getIp())) {
            inputEditText.setText(setting.getIp());
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(inputEditText.getText().toString())) {
                    // TODO add account

                    databaseHandler.deleteAllSetting();
                    databaseHandler.addSetting(new Setting(inputEditText.getText().toString()));

                } else
                    Toast.makeText(LogInActivity.this, "Please add Ip first !", Toast.LENGTH_SHORT).show();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //TODO add dialog function
        dialog.show();
    }

    void init() {

        databaseHandler = new DatabaseHandler(LogInActivity.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_waiting));
//        userName = findViewById(R.id.LogInUserName);
//        password = findViewById(R.id.LogInPassword);
//        singIn = findViewById(R.id.LogInSingIn);
//        singUp = findViewById(R.id.LogInSingUp);
//        arabic = findViewById(R.id.login_arabic);
//        english = findViewById(R.id.login_english);
//        checkValidation = findViewById(R.id.login_checkValidation);
//        signupVM.setUsername("0790790791");
//        signupVM.setPassword("tahaniA1$");

//        binding.LogInUserName.setText("0790790791");//userName.getText().toString());
//        binding.LogInPassword.setText("tahaniA1$");
        SettingImage = findViewById(R.id.Setting);
        coordinatorLayout = findViewById(R.id.login_coordinatorLayout);
//        seen = findViewById(R.id.login_seen);
//        forgetPassword = findViewById(R.id.login_forgetPassword);
        passwordLinear = findViewById(R.id.login_password_linear);

        if (getIntent().getIntExtra(PAGE_NAME, 0) == 10)
            showSnackbar(getResources().getString(R.string.new_account_saved_successfully), true);

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

    void checkLanguage() {

        if (language.equals("ar")) {
//            binding.LogInUserName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
//            binding.loginPhoneLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.LogInPassword.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            LinearLayout.LayoutParams checkBoxParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkBoxParam.gravity = Gravity.RIGHT;
            binding.loginRememberMe.setLayoutParams(checkBoxParam);

//            userName.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_person_black_24dp), null);
//            password.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_https_black_24dp), null);
        } else {

//            binding.loginPhoneLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//            userName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_person_black_24dp), null
//                    , null, null);
//            password.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_https_black_24dp), null
//                    , null, null);
        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        checkIfIsRemember();
//    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
////            case R.id.LogInSingIn: {
////                if (!TextUtils.isEmpty(binding.LogInUserName.getText()))//userName.getText().toString()))
////                    if (binding.LogInUserName.length() == 10)//userName.length() == 10)
////                        if (!TextUtils.isEmpty(binding.LogInPassword.getText())) {//password.getText().toString())) {
//////                            userName.setError(null);
//////                            password.setError(null);
////                            binding.LogInUserName.setError(null);
////                            binding.LogInPassword.setError(null);
////
////                            LoginINFO user = new LoginINFO();
////                            user.setUsername(binding.LogInUserName.getText().toString());//userName.getText().toString());
////                            user.setPassword(binding.LogInPassword.getText().toString());//password.getText().toString());
////
////                            showDialog();
////                            new Presenter(LogInActivity.this).loginInfoCheck(LogInActivity.this, user);
////                        } else {
////                            binding.LogInPassword.setError("Required!");//password
////                        }
////                    else {
////                        binding.LogInUserName.setError("Phone number not correct!");//userName
////                    }
////                else {
////                    binding.LogInUserName.setError("Required!");//userName
////                }
////            }
////            break;
//            case R.id.login_checkValidation: {
//                barcodeDialog = new Dialog(LogInActivity.this);
//                barcodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                barcodeDialog.setContentView(R.layout.check_validation_layout);
//
//                TextView scan = barcodeDialog.findViewById(R.id.checkValidation_scanBarcode);
//                ImageView close = barcodeDialog.findViewById(R.id.checkValidation_close);
//                LinearLayout headerLinear = barcodeDialog.findViewById(R.id.checkValidation_headerLinear);
//                TextView haveAProblem = barcodeDialog.findViewById(R.id.checkValidation_help);
//                TextView scanTV = barcodeDialog.findViewById(R.id.checkValidation_scanLinear);
//
//                final LinearLayout serialLinear = barcodeDialog.findViewById(R.id.checkValidation_serial_linear);
//                final TextInputEditText serial = barcodeDialog.findViewById(R.id.checkValidation_serial);
//                TextView check = barcodeDialog.findViewById(R.id.checkValidation_check);
//                serialLinear.setVisibility(View.GONE);
//
//                haveAProblem.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (serialLinear.getVisibility() == View.VISIBLE) {
//                            serialLinear.setVisibility(View.GONE);
//
//                        } else {
//                            serialLinear.setVisibility(View.VISIBLE);
//                            serial.setError(null);
//                        }
//                    }
//                });
//
//                check.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!TextUtils.isEmpty(serial.getText().toString())) {
//                            serial.setError(null);
//                        } else {
//                            serial.setError("Required");
//                        }
//
//                    }
//                });
//
//                scan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        IntentIntegrator intentIntegrator = new IntentIntegrator(LogInActivity.this);
//                        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
//                        intentIntegrator.setBeepEnabled(false);
//                        intentIntegrator.setCameraId(0);
//                        intentIntegrator.setPrompt("SCAN");
//                        intentIntegrator.setBarcodeImageEnabled(false);
//                        intentIntegrator.initiateScan();
//                    }
//                });
//
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        barcodeDialog.dismiss();
//                    }
//                });
//
//                Log.e("checkLang", language);
//                if (language.equals("ar")) {
//                    headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//                    check.setGravity(Gravity.RIGHT);
//
//                    haveAProblem.setCompoundDrawablesWithIntrinsicBounds(null, null
//                            , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_help), null);
//                    scanTV.setCompoundDrawablesWithIntrinsicBounds(null, null
//                            , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_phone), null);
//
//                } else {
//                    headerLinear.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//                    check.setGravity(Gravity.LEFT);
//
//                    haveAProblem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_help), null
//                            , null, null);
//                    scanTV.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_phone), null
//                            , null, null);
//
//                }
//
//                barcodeDialog.show();
//            }
//            break;
//            case R.id.login_arabic: {
//                language = "ar";
//                editor = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE).edit();
//                editor.putString("language", "ar");
//                editor.apply();
//
//                LocaleAppUtils.setLocale(new Locale("ar"));
//                LocaleAppUtils.setConfigChange(LogInActivity.this);
//                finish();
//                startActivity(getIntent());
//            }
//            break;
//            case R.id.login_english: {
//                language = "en";
//                editor = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE).edit();
//                editor.putString("language", "en");
//                editor.apply();
//
//                LocaleAppUtils.setLocale(new Locale("en"));
//                LocaleAppUtils.setConfigChange(LogInActivity.this);
//                finish();
//                startActivity(getIntent());
//            }
//            break;
//            case R.id.login_forgetPassword: {
//                Dialog dialog = new Dialog(this);
//                dialog.setContentView(R.layout.dialog_forget_password);
//
//                phoneLinear = dialog.findViewById(R.id.forgetPassword_phone_linear);
//                final EditText phone = dialog.findViewById(R.id.forgetPassword_phone);
//                final Button phoneSend = dialog.findViewById(R.id.forgetPassword_phone_send);
//                RadioButton phoneRB = dialog.findViewById(R.id.forgetPassword_phone_rb);
//                emailLinear = dialog.findViewById(R.id.forgetPassword_email_linear);
//                final EditText email = dialog.findViewById(R.id.forgetPassword_email);
//                final Button emailSend = dialog.findViewById(R.id.forgetPassword_email_send);
//                RadioButton emailRB = dialog.findViewById(R.id.forgetPassword_email_rb);
//                RadioGroup radioGroup = dialog.findViewById(R.id.forgetPassword_rg);
//
//
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int id) {
//                        if (id == R.id.forgetPassword_email_rb) {
//                            phoneLinear.setVisibility(View.GONE);
//                            emailLinear.setVisibility(View.VISIBLE);
//
//                            emailSend.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if (!TextUtils.isEmpty(email.getText().toString())) {
//                                        try {
//
//                                            String emailSender = "hiary.abeer96@gmail.com", password = "000", emailReceiver = "hiary.abeer@yahoo.com", userName = "Cheque App";
//
//                                            LongOperation l = new LongOperation(emailSender
//                                                    , password
//                                                    , emailReceiver
//                                                    , userName);
//                                            l.execute();  //sends the email in background
//                                            Toast.makeText(LogInActivity.this, l.get(), Toast.LENGTH_SHORT).show();
//                                        } catch (Exception e) {
//                                            Log.e("SendMail", e.getMessage(), e);
//                                        }
//
//                                    } else
//                                        Toast.makeText(LogInActivity.this, "Please fill email first!", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        } else {
//                            phoneLinear.setVisibility(View.VISIBLE);
//                            emailLinear.setVisibility(View.GONE);
//
//                            phoneSend.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if (!TextUtils.isEmpty(phone.getText().toString())) {
//
//                                    } else
//                                        Toast.makeText(LogInActivity.this, "Please fill phone first!", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                    }
//                });
//
//
//                dialog.show();
//                Window window = dialog.getWindow();
//                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            }
//            break;
//        }
//    }

    // ******************************************** CHECK QR VALIDATION *************************************
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

                request.setURI(new URI(serverLink + "VerifyCheck?"));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("CHECKNO", array[0]));
                nameValuePairs.add(new BasicNameValuePair("BANKNO", array[1]));
                nameValuePairs.add(new BasicNameValuePair("BTANCHNO", array[2]));
                nameValuePairs.add(new BasicNameValuePair("ACCCODE", array[3]));
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
                    Log.e("login/checkValidation/", "Success/" + s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);


                        checkNo = jsonObject.get("CHECKNO").toString();
                        accountCode = jsonObject.get("ACCCODE").toString();
                        ibanNo = jsonObject.get("IBANNO").toString();
                        customerName = jsonObject.get("CUSTOMERNM").toString();
                        qrCode = jsonObject.get("QRCODE").toString();
                        serialNo = jsonObject.get("SERIALNO").toString();
                        bankNo = jsonObject.get("BANKNO").toString();
                        branchNo = jsonObject.get("BRANCHNO").toString();

                        showValidationDialog(true, customerName, bankNo, accountCode, checkNo);

//                        showSweetDialog(true, jsonObject.get("CUSTOMERNM").toString(), jsonObject.get("BANKNO").toString(), jsonObject.get("ACCCODE").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    showValidationDialog(false, "", "", "", "");

                    Log.e("tagLogIn", "****Failed to export data");
                }
            } else {
                Toast.makeText(LogInActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

    // ******************************************** CHECK QR VALIDATION *************************************
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
                String data = "ACCCODE=" + URLEncoder.encode(accountCode, "UTF-8") + "&"
                        + "IBANNO=" + URLEncoder.encode(ibanNo, "UTF-8") + "&"
                        + "SERIALNO=" + URLEncoder.encode(serialNo, "UTF-8") + "&"
                        + "BANKNO=" + URLEncoder.encode(bankNo, "UTF-8") + "&"
                        + "BRANCHNO=" + URLEncoder.encode(branchNo, "UTF-8") + "&"
                        + "CHECKNO=" + URLEncoder.encode(checkNo, "UTF-8");
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
                inputStream.close();// obj.seto(finalObject.getString("OWNERMOBNO"));
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

            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(LogInActivity.this.getResources().getString(R.string.pending_))
                            .setContentText(LogInActivity.this.getResources().getString(R.string.cantsendchech))
                            .setConfirmText(LogInActivity.this.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();

                } else if (s.contains("\"StatusDescreption\":\"Check not pindding.\"")) {


                    new TillerGetCheck().execute();

                }
            } else {
                Log.e("tag", "****Failed to export data");
                new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(LogInActivity.this.getResources().getString(R.string.warning))
                        .setContentText(LogInActivity.this.getResources().getString(R.string.failtoSend))
                        .setCancelText(LogInActivity.this.getResources().getString(R.string.close)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                    }
                })
                        .show();


            }

        }
    }


    public class TillerGetCheck extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        ChequeInfo chequeInfo;

//        public TillerGetCheck(ChequeInfo chequeInfo) {
//            this.chequeInfo = chequeInfo;
//        }

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

                String link = serverLink + "TillerGetCheck";


//                ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144&USESERIAL=0

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "ACCCODE=" + URLEncoder.encode(accountCode, "UTF-8") + "&" +
                        "IBANNO=" + URLEncoder.encode(ibanNo, "UTF-8") + "&" +
                        "SERIALNO=" + URLEncoder.encode(serialNo, "UTF-8") + "&" +
                        "BANKNO=" + URLEncoder.encode(bankNo, "UTF-8") + "&" +
                        "BRANCHNO=" + URLEncoder.encode(branchNo, "UTF-8") + "&" +
                        "CHECKNO=" + URLEncoder.encode(checkNo, "UTF-8") + "&" +
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


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

                    List<ChequeInfo> chequeInfoTilar = new ArrayList<>();
                    boolean foundIn = false;

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        if (finalObject.getString("TOCUSTOMERMOB").equals(finalObject.getString("OWNERMOBNO"))) {

                            new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(" Cheque ")
                                    .setContentText("Cheque Cashed")
                                    .show();

                        } else {

                            new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(" Cheque ")
                                    .setContentText("*** Can Receive The Cheque ***")
                                    .show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }//

            } else if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Check Data not found")) {
                Log.e("TAG_GetStor", "****Check Data not found");


                new SweetAlertDialog(LogInActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(" Cheque ")
                        .setContentText("Check Data not found")
                        .show();

            }

        }
    }

}
