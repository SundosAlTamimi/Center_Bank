package com.falconssoft.centerbank;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.falconssoft.centerbank.Models.LoginINFO;
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
    private TextView date_text;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = SingUpLayoutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.sing_up_layout);//binding.getRoot()

//        language = getIntent().getStringExtra(LANGUAGE_FLAG);
        init();
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
        checkLanguage();

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
        } else {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#D11616\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error, 0, 0, 0);
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
}
