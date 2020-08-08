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
import java.security.Permission;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.falconssoft.centerbank.databinding.SingUpLayoutBinding;
import com.falconssoft.centerbank.viewmodel.SignupVM;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class SingUpActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_FLAG = 10;
    private TextView test;//date_text,
    private Date currentTimeAndDate;
    private SimpleDateFormat df;
    private Calendar myCalendar;
    private EditText phoneNo;//,natonalNo,  address, email, firstName, secondName, thirdName, fourthName;
    private String language, today, selectedAccount = "Individual", selectedGender = "Male", countryCode = "962", selectedNationality = "Jordan";
    private Animation animation;
    private LinearLayout linearLayout, coordinatorLayout, accountTypeLinear, genderLinear, phoneLinear, nationalLinear, linearDOB, passwordLinear, confirmPasswordLinear, addressLinear;
    //    private Button save, currentLocation;
//    private Spinner spinnerAccountType, spinnerGender;//, spinnerDocumentType;
    private List<String> genderList = new ArrayList<>();
    private ArrayAdapter arrayAdapter, genderArrayAdapter;//, documentAdapter;
    private DatabaseHandler databaseHandler;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    public static final String PAGE_NAME = "PAGE_NAME";
    private CountryCodePicker ccp, ccpNationality;
    private TextInputLayout emailLinear, idLinear;
    //    private ImageView seenPassword, seenConfirmPassword;
    //    private TextInputEditText password, confirmPassword;
    private int currentYear, birthYear;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private SignupVM signupVM;
    private SingUpLayoutBinding binding;
    private SharedClass sharedClass;
//    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LocaleAppUtils().changeLayot(SingUpActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.sing_up_layout);
//        setContentView(R.layout.sing_up_layout);//binding.getRoot()

        test = findViewById(R.id.signUp_textView4);
        signupVM = new SignupVM();

        init();

        binding.signUpCcp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();

            }
        });

        binding.signUpDocumentType.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
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
//        binding.date.setText(convertToEnglish(today));
        signupVM.setBirthDate(convertToEnglish(today));
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        birthYear = Calendar.getInstance().get(Calendar.YEAR);

        binding.signUpSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMethod();
            }
        });

        binding.signUpSeen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (binding.signUpPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) { //password
                    binding.signUpSeen.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    binding.signUpPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// password
                    binding.signUpPassword.setTransformationMethod(new PasswordTransformationMethod());// password
                } else {
                    binding.signUpSeen.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    binding.signUpPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);// password
                    binding.signUpPassword.setTransformationMethod(null);//password
                }
                return false;
            }
        });

        binding.signUpSeenConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (binding.signUpConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) { //password
                    binding.signUpSeenConfirm.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    binding.signUpConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// password
                    binding.signUpConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());// password
                } else {
                    binding.signUpSeenConfirm.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    binding.signUpConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);// password
                    binding.signUpConfirmPassword.setTransformationMethod(null);//password
                }
                return false;
            }
        });
        binding.setSignupModel(signupVM);

        Log.e("editing,signup ", language);
    }

    private void saveMethod() {
//        String localPhone = phoneNo.getText().toString();

        if (currentYear - birthYear > 17 ? true : false)
            if (!TextUtils.isEmpty("" + signupVM.getUsername()))
                if (signupVM.getUsername().length() == 9)
                    if (!String.valueOf(signupVM.getUsername().charAt(0)).equals("0"))
                        if (!TextUtils.isEmpty(signupVM.getFirstName()))
                            if (!TextUtils.isEmpty(signupVM.getSecondName()))
                                if (!TextUtils.isEmpty(signupVM.getThirdName()))
                                    if (!TextUtils.isEmpty(signupVM.getFourthName()))
                                        if (!TextUtils.isEmpty(signupVM.getNationalID()))
                                            if ((selectedNationality.equals("Jordan") && (signupVM.getNationalID().length() == 10))
                                                    || (!selectedNationality.equals("Jordan") && (signupVM.getNationalID().length() == 9)))
                                                if (!TextUtils.isEmpty(signupVM.getAddress()))
                                                    if (!TextUtils.isEmpty(signupVM.getEmail()))
                                                        if (Patterns.EMAIL_ADDRESS.matcher(signupVM.getEmail()).matches())
                                                            if (!TextUtils.isEmpty(signupVM.getPassword()))
                                                                if (isValidPassword(signupVM.getPassword()))
                                                                    if (!TextUtils.isEmpty(signupVM.getConfirmPassword()))
                                                                        if (signupVM.getPassword().equals(signupVM.getConfirmPassword())) {

                                                                            String mobile = signupVM.getUsername();
                                                                            signupVM.setUsername(countryCode + mobile);
//                                                                            signupVM.setBirthDate(signupVM.getBirthDate());
                                                                            signupVM.setNationality(selectedNationality);

                                                                            if (selectedGender.equals("Male") || selectedNationality.equals("ذكر"))
                                                                                signupVM.setGender("0");
                                                                            else
                                                                                signupVM.setGender("1");

                                                                            showDialog();
                                                                            new Presenter(SingUpActivity.this).saveSignUpInfo(this, signupVM);

                                                                        } else
                                                                            binding.signUpConfirmPassword.setError(getResources().getString(R.string.password_not_matched));
                                                                    else
                                                                        binding.signUpConfirmPassword.setError(getResources().getString(R.string.required));
                                                                else
                                                                    binding.signUpPassword.setError(getResources().getString(R.string.password_syntax));
                                                            else
                                                                binding.signUpPassword.setError(getResources().getString(R.string.required));
                                                        else
                                                            binding.signUpEmail.setError(getResources().getString(R.string.not_valid_email));
                                                    else
                                                        binding.signUpEmail.setError(getResources().getString(R.string.required));
                                                else
                                                    binding.signUpAddress.setError(getResources().getString(R.string.required));
                                            else
                                                binding.signUpIdNo.setError(getResources().getString(R.string.id_is_not_correct));
                                        else
                                            binding.signUpIdNo.setError(getResources().getString(R.string.required));
                                    else
                                        binding.signUpFourthName.setError(getResources().getString(R.string.required));
                                else
                                    binding.signUpThirdName.setError(getResources().getString(R.string.required));
                            else
                                binding.signUpSecondName.setError(getResources().getString(R.string.required));
                        else
                            binding.signUpFirstName.setError(getResources().getString(R.string.required));
                    else
                        phoneNo.setError(getResources().getString(R.string.remove_zero));
                else
                    phoneNo.setError(getResources().getString(R.string.phone_length));
            else
                phoneNo.setError(getResources().getString(R.string.required));
        else
            sharedClass.showSnackbar(coordinatorLayout, getString(R.string.age_constrict), false);

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
        sharedClass = new SharedClass(this);
        coordinatorLayout = findViewById(R.id.signup_coordinatorLayout);
//        save = findViewById(R.id.signUp_save);
//        firstName = findViewById(R.id.signUp_first_name);
//        secondName = findViewById(R.id.signUp_second_name);
//        thirdName = findViewById(R.id.signUp_third_name);
//        fourthName = findViewById(R.id.signUp_fourth_name);
//        natonalNo = findViewById(R.id.signUp_IdNo);
        phoneNo = findViewById(R.id.signUp_phone);
//        address = findViewById(R.id.signUp_address);
//        email = findViewById(R.id.signUp_email);
//        password = findViewById(R.id.signUp_password);
        linearLayout = findViewById(R.id.signup_nameLinear);
//        date_text = (TextView) findViewById(R.id.Date);
//        spinnerAccountType = findViewById(R.id.signUp_accountType);
//        spinnerGender = findViewById(R.id.signUp_gender);
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
//        seenPassword = findViewById(R.id.signUp_seen);
//        seenConfirmPassword = findViewById(R.id.signUp_seen_confirm);
//        confirmPassword = findViewById(R.id.signUp_confirm_password);
//        currentLocation = findViewById(R.id.signUp_current_location);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_waiting));

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.account_type, R.layout.spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        binding.signUpAccountType.setAdapter(arrayAdapter);
        binding.signUpAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAccount = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderArrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender_type, R.layout.spinner_layout);
        genderArrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        binding.signUpGender.setAdapter(genderArrayAdapter);
        binding.signUpGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SingUpActivity.this, openDatePickerDialog(binding.date), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.signUpCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    private void getLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    void gelLocationName(double longitude, double latitude) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String addressLocal = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        signupVM.setAddress(knownName);
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
//            date_text.setGravity(Gravity.RIGHT);
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
//            date_text.setGravity(Gravity.LEFT);
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
            sharedClass.showSnackbar(coordinatorLayout, getResources().getString(R.string.check_info_first), false);
        else if (message.contains("\"StatusCode\":14,\"StatusDescreption\":\"User Mobile alreay exisit.\""))
            sharedClass.showSnackbar(coordinatorLayout, getResources().getString(R.string.mobile_already_exist), false);
        else if (message.contains("\"StatusCode\":15,\"StatusDescreption\":\"User National ID alreay exisit.\""))
            sharedClass.showSnackbar(coordinatorLayout, getResources().getString(R.string.national_already_exist), false);
        else //(message.contains("{\"StatusCode\" : 4,\"StatusDescreption\":\"Error in Saving Check Temp.\" }") or error
            sharedClass.showSnackbar(coordinatorLayout, getResources().getString(R.string.info_not_saved), false);

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

//        editText.setText(sdf.format(myCalendar.getTime()));
        signupVM.setBirthDate(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_FLAG);
            return;
        } else {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                try {
                    gelLocationName(mLastLocation.getLongitude(), mLastLocation.getLatitude());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("locationis", "" + mLastLocation.getLatitude() + "****" + mLastLocation.getLongitude());
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    try {
                        gelLocationName(mLastLocation.getLongitude(), mLastLocation.getLatitude());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("locationisResult", "" + mLastLocation.getLatitude() + "****" + mLastLocation.getLongitude());
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
