package com.falconssoft.centerbank;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class SingUpActivity extends AppCompatActivity {
    private TextView date_text, test;
    private Date currentTimeAndDate;
    private SimpleDateFormat df;
    private Calendar myCalendar;
    private EditText natonalNo, phoneNo, address, email, firstName, secondName, thirdName, fourthName;
    private String language, today, selectedAccount = "Individual", selectedGender = "Male", countryCode = "962", selectedNationality = "Jordan";
    private Animation animation;
    private LinearLayout linearLayout, coordinatorLayout, accountTypeLinear, genderLinear, phoneLinear, nationalLinear, linearDOB, passwordLinear, confirmPasswordLinear;
    private Button save;
    private Spinner spinnerAccountType, spinnerGender;//, spinnerDocumentType;
    private List<String> genderList = new ArrayList<>();
    private ArrayAdapter arrayAdapter, genderArrayAdapter;//, documentAdapter;
    private DatabaseHandler databaseHandler;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    public static final String PAGE_NAME = "PAGE_NAME";
    private CountryCodePicker ccp, ccpNationality;
    private TextInputLayout emailLinear, addressLinear, idLinear;
    private ImageView seenPassword, seenConfirmPassword;
    private TextInputEditText password, confirmPassword;
    private int currentYear, birthYear;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
//    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = SingUpLayoutBinding.inflate(getLayoutInflater());
        new LocaleAppUtils().changeLayot(SingUpActivity.this);
        setContentView(R.layout.sing_up_layout);//binding.getRoot()

//        language = getIntent().getStringExtra(LANGUAGE_FLAG);
        test = findViewById(R.id.signUp_textView4);
        init();
//        appLocationService = new AppLocationService(
//                SingUpActivity.this);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Location location = appLocationService
//                        .getLocation(LocationManager.GPS_PROVIDER);
//
//                //you can hard-code the lat & long if you have issues with getting it
//                //remove the below if-condition and use the following couple of lines
//                //double latitude = 37.422005;
//                //double longitude = -122.084095
//
//                if (location != null) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    LocationAddress locationAddress = new LocationAddress();
//                    locationAddress.getAddressFromLocation(latitude, longitude,
//                            getApplicationContext(), new GeocoderHandler());
//                } else {
//
//                }
//            }
//        });

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();

            }
        });

        ccpNationality.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedNationality = ccpNationality.getSelectedCountryName();
                Log.e("countryName", selectedNationality);

            }
        });
        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");

        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        date_text.setText(convertToEnglish(today));
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        birthYear = Calendar.getInstance().get(Calendar.YEAR);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMethod();
            }
        });

        seenPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) { //password
                    seenPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// password
                    password.setTransformationMethod(new PasswordTransformationMethod());// password
                } else {
                    seenPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);// password
                    password.setTransformationMethod(null);//password
                }
                return false;
            }
        });

        seenConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (confirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) { //password
                    seenConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// password
                    confirmPassword.setTransformationMethod(new PasswordTransformationMethod());// password
                } else {
                    seenConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);// password
                    confirmPassword.setTransformationMethod(null);//password
                }
                return false;
            }
        });

        Log.e("editing,signup ", language);
    }

    private void saveMethod() {
        String localNationalID = natonalNo.getText().toString();
        String localFirstName = firstName.getText().toString();
        String localSecondName = secondName.getText().toString();
        String localThirdName = thirdName.getText().toString();
        String localFourthName = fourthName.getText().toString();
        String localPhone = phoneNo.getText().toString();
        String localAddress = address.getText().toString();
        String localEmail = email.getText().toString();
        String localPassword = password.getText().toString();
        String localBirthDate = date_text.getText().toString();
        String localConfirmPassword = confirmPassword.getText().toString();

//        Log.e("phone", "" + localPhone.charAt(0) + "            " + (String.valueOf(localPhone.charAt(0))).equals("0"));

//        if (!selectedAccount.equals(getResources().getString(R.string.account_type)))
//            if (!selectedGender.equals(getResources().getString(R.string.gender)))        String localPassword = password.getText().toString();

        if (currentYear - birthYear > 17 ? true : false)
            if (!TextUtils.isEmpty("" + localPhone))
                if (localPhone.length() == 9)
                    if (!String.valueOf(localPhone.charAt(0)).equals("0"))
                        if (!TextUtils.isEmpty(localFirstName))
                            if (!TextUtils.isEmpty(localSecondName))
                                if (!TextUtils.isEmpty(localThirdName))
                                    if (!TextUtils.isEmpty(localFourthName))
                                        if (!TextUtils.isEmpty(localNationalID))
                                            if ((selectedNationality.equals("Jordan") && (localNationalID.length() == 10))
                                                    || (!selectedNationality.equals("Jordan") && (localNationalID.length() == 9)))
                                                if (!TextUtils.isEmpty(localAddress))
                                                    if (!TextUtils.isEmpty(localEmail))
                                                        if (Patterns.EMAIL_ADDRESS.matcher(localEmail).matches())
                                                            if (!TextUtils.isEmpty(localPassword))
                                                                if (isValidPassword(localPassword))
                                                                    if (!TextUtils.isEmpty(localConfirmPassword))
                                                                        if (localPassword.equals(localConfirmPassword)) {

                                                                            LoginINFO loginINFO = new LoginINFO();
                                                                            loginINFO.setNationalID(localNationalID);
                                                                            loginINFO.setFirstName(localFirstName);
                                                                            loginINFO.setSecondName(localSecondName);
                                                                            loginINFO.setThirdName(localThirdName);
                                                                            loginINFO.setFourthName(localFourthName);
                                                                            loginINFO.setUsername(countryCode + localPhone);
                                                                            loginINFO.setAddress(localAddress);
                                                                            loginINFO.setEmail(localEmail);
                                                                            loginINFO.setPassword(localPassword);
                                                                            loginINFO.setBirthDate(localBirthDate);
                                                                            loginINFO.setNationality(selectedNationality);

                                                                            if (selectedGender.equals("Male") || selectedNationality.equals("ذكر"))
                                                                                loginINFO.setGender("0");
                                                                            else
                                                                                loginINFO.setGender("1");

                                                                            showDialog();
                                                                            new Presenter(SingUpActivity.this).saveSignUpInfo(this, loginINFO);

                                                                        } else
                                                                            confirmPassword.setError(getResources().getString(R.string.password_not_matched));
                                                                    else
                                                                        confirmPassword.setError(getResources().getString(R.string.required));
                                                                else
                                                                    password.setError(getResources().getString(R.string.password_syntax));
                                                            else
                                                                password.setError(getResources().getString(R.string.required));
                                                        else
                                                            email.setError(getResources().getString(R.string.not_valid_email));
                                                    else
                                                        email.setError(getResources().getString(R.string.required));
                                                else
                                                    address.setError(getResources().getString(R.string.required));
                                            else
                                                natonalNo.setError(getResources().getString(R.string.id_is_not_correct));
                                        else
                                            natonalNo.setError(getResources().getString(R.string.required));
                                    else
                                        fourthName.setError(getResources().getString(R.string.required));
                                else
                                    thirdName.setError(getResources().getString(R.string.required));
                            else
                                secondName.setError(getResources().getString(R.string.required));
                        else
                            firstName.setError(getResources().getString(R.string.required));
                    else
                        phoneNo.setError(getResources().getString(R.string.remove_zero));
                else
                    phoneNo.setError(getResources().getString(R.string.phone_length));
            else
                phoneNo.setError(getResources().getString(R.string.required));
        else
            showSnackbar(getString(R.string.age_constrict), false);

//            else
//                showSnackbar("Please choose gender!", false);
//        else
//            showSnackbar("Please choose account type!", false);

    }

    public static boolean isValidPassword(String password) {
        if (password.length() > 5) {

            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();
        } else
            return false;
    }

    private void init() {

        databaseHandler = new DatabaseHandler(this);
        coordinatorLayout = findViewById(R.id.signup_coordinatorLayout);
        save = findViewById(R.id.signUp_save);
        firstName = findViewById(R.id.signUp_first_name);
        secondName = findViewById(R.id.signUp_second_name);
        thirdName = findViewById(R.id.signUp_third_name);
        fourthName = findViewById(R.id.signUp_fourth_name);
        natonalNo = findViewById(R.id.signUp_IdNo);
        phoneNo = findViewById(R.id.signUp_phone);
        address = findViewById(R.id.signUp_address);
        email = findViewById(R.id.signUp_email);
        password = findViewById(R.id.signUp_password);
        linearLayout = findViewById(R.id.signup_nameLinear);
        date_text = (TextView) findViewById(R.id.Date);
        spinnerAccountType = findViewById(R.id.signUp_accountType);
        spinnerGender = findViewById(R.id.signUp_gender);
//        spinnerDocumentType = findViewById(R.id.signUp_document_type);
        ccpNationality = findViewById(R.id.signUp_document_type);
        accountTypeLinear = findViewById(R.id.signUp_accountType_linear);
        genderLinear = findViewById(R.id.signUp_gender_linear);
        phoneLinear = findViewById(R.id.signUp_phone_linear);
        nationalLinear = findViewById(R.id.signUp_documentType_linear);
        ccp = findViewById(R.id.signUp_ccp);
        linearDOB = findViewById(R.id.signUp_dateOfBirth_linear);
        idLinear = findViewById(R.id.signUp_id_linear);
        emailLinear = findViewById(R.id.signUp_email_linear);
        addressLinear = findViewById(R.id.signUp_address_linear);
        passwordLinear = findViewById(R.id.signup_passwordLinear);
        confirmPasswordLinear = findViewById(R.id.signup_confirm_passwordLinear);
        seenPassword = findViewById(R.id.signUp_seen);
        seenConfirmPassword = findViewById(R.id.signUp_seen_confirm);
        confirmPassword = findViewById(R.id.signUp_confirm_password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_waiting));

//        accountTypeList.clear();
//        accountTypeList.add(getResources().getResourceName(R.string.account_type));
//        accountTypeList.add(getResources().getResourceName(R.string.individual));
//        accountTypeList.add(getResources().getResourceName(R.string.corporate));
//        accountTypeList.add(getResources().getResourceName(R.string.join_account));
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.account_type, R.layout.spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinnerAccountType.setAdapter(arrayAdapter);
        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAccount = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        genderList.clear();
//        genderList.add(getResources().getResourceName(R.string.gender));
//        genderList.add(getResources().getResourceName(R.string.male));
//        genderList.add(getResources().getResourceName(R.string.female));
        genderArrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender_type, R.layout.spinner_layout);
        genderArrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinnerGender.setAdapter(genderArrayAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedGender = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        documentAdapter = ArrayAdapter.createFromResource(this, R.array.document_type, R.layout.spinner_layout);
//        documentAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
//        spinnerDocumentType.setAdapter(documentAdapter);
//        spinnerDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                selectedDocument = adapterView.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        myCalendar = Calendar.getInstance();
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SingUpActivity.this, openDatePickerDialog(date_text), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void checkLanguage() {
        if (language.equals("ar")) {
            selectedAccount = "شخصي";
            selectedGender = "ذكر";
//            natonalNo.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null);
//            phoneNo.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null);
//            address.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null);
//            email.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_email_black_24dp), null);
//            password.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_https_black_24dp), null);
//            date_text.setCompoundDrawablesWithIntrinsicBounds(null, null
//                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_date_range_black_24dp), null);
            date_text.setGravity(Gravity.RIGHT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            accountTypeLinear.setGravity(Gravity.RIGHT);
            genderLinear.setGravity(Gravity.RIGHT);

        } else {
            selectedAccount = "Individual";
            selectedGender = "Male";
//            natonalNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null
//                    , null, null);
//            phoneNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null
//                    , null, null);
//            address.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null
//                    , null, null);
//            email.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_email_black_24dp), null
//                    , null, null);
//            password.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_https_black_24dp), null
//                    , null, null);
//            date_text.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_date_range_black_24dp), null
//                    , null, null);
            date_text.setGravity(Gravity.LEFT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            accountTypeLinear.setGravity(Gravity.LEFT);
            genderLinear.setGravity(Gravity.LEFT);

        }

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        accountTypeLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        genderLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        nationalLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearDOB.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        phoneLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearLayout.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        idLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        addressLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        emailLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        passwordLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        confirmPasswordLinear.startAnimation(animation);
    }

    public void goToLoginPage(String message) {
        hideDialog();
        if (message.contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\"")) {
            Intent intent = new Intent(SingUpActivity.this, LogInActivity.class);
            intent.putExtra(PAGE_NAME, 10);
            startActivity(intent);
            finish();
        } else if (message.contains("{\"StatusCode\" : 9,\"StatusDescreption\":\"Error in saving User.\" }"))
            showSnackbar(getResources().getString(R.string.check_info_first), false);
        else if (message.contains("\"StatusCode\":14,\"StatusDescreption\":\"User Mobile alreay exisit.\""))
            showSnackbar(getResources().getString(R.string.mobile_already_exist), false);
        else if (message.contains("\"StatusCode\":15,\"StatusDescreption\":\"User National ID alreay exisit.\""))
            showSnackbar(getResources().getString(R.string.national_already_exist), false);
        else //(message.contains("{\"StatusCode\" : 4,\"StatusDescreption\":\"Error in Saving Check Temp.\" }") or error
            showSnackbar(getResources().getString(R.string.info_not_saved), false);


    }

    void showSnackbar(String text, boolean showImage) {

        if (showImage) {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#3167F0\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_24dp, 0, 0, 0);
            textViewSnackbar.setCompoundDrawablePadding(5);
        } else {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#D11616\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error, 0, 0, 0);
            textViewSnackbar.setCompoundDrawablePadding(5);

        }
        snackbar.show();
    }

    void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.dismiss();
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
                birthYear = year;
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

    public class AppLocationService extends Service implements LocationListener {

        protected LocationManager locationManager;
        Location location;

        private static final long MIN_DISTANCE_FOR_UPDATE = 10;
        private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

        public AppLocationService(Context context) {
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);
        }

        public Location getLocation(String provider) {
            if (locationManager.isProviderEnabled(provider)) {
                if (ActivityCompat.checkSelfPermission(SingUpActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }
                locationManager.requestLocationUpdates(provider,
                        MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider);
                    return location;
                }
            }
            return null;
        }

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

    }

    public class LocationAddress {
        private static final String TAG = "LocationAddress";

        public void getAddressFromLocation(final double latitude, final double longitude,
                                                  final Context context, final Handler handler) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    String result = null;
                    try {
                        List<Address> addressList = geocoder.getFromLocation(
                                latitude, longitude, 1);
                        if (addressList != null && addressList.size() > 0) {
                            Address address = addressList.get(0);
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                sb.append(address.getAddressLine(i)).append("\n");
                            }
                            sb.append(address.getLocality()).append("\n");
                            sb.append(address.getPostalCode()).append("\n");
                            sb.append(address.getCountryName());
                            result = sb.toString();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Unable connect to Geocoder", e);
                    } finally {
                        Message message = Message.obtain();
                        message.setTarget(handler);
                        if (result != null) {
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            result = "Latitude: " + latitude + " Longitude: " + longitude +
                                    "\n\nAddress:\n" + result;
                            bundle.putString("address", result);
                            message.setData(bundle);
                        } else {
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            result = "Latitude: " + latitude + " Longitude: " + longitude +
                                    "\n Unable to get address for this lat-long.";
                            bundle.putString("address", result);
                            message.setData(bundle);
                        }
                        message.sendToTarget();
                    }
                }
            };
            thread.start();
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            test.setText(locationAddress);
        }
    }
}
