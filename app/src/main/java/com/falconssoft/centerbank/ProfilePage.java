package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falconssoft.centerbank.Models.LoginINFO;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class ProfilePage extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private LoginINFO loginINFO;
    private EditText firstName, secondName, thirdName, fourthName, address, email, password, phoneNo;
    private TextView nationalID, accountType, gender, date;
    private String language;
    private Animation animation;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LocaleAppUtils().changeLayot(ProfilePage.this);
        setContentView(R.layout.activity_profile_page);

        databaseHandler = new DatabaseHandler(this);
        loginINFO = databaseHandler.getActiveUserInfo();

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");

        firstName = findViewById(R.id.profile_first_name);
        secondName = findViewById(R.id.profile_second_name);
        thirdName = findViewById(R.id.profile_third_name);
        fourthName = findViewById(R.id.profile_fourth_name);
        address = findViewById(R.id.profile_address);
        email = findViewById(R.id.profile_email);
        password = findViewById(R.id.profile_password);
        nationalID = findViewById(R.id.profile_national_No);
        phoneNo = findViewById(R.id.profile_phone);
        accountType = findViewById(R.id.profile_account_type);
        gender = findViewById(R.id.profile_gender);
        date = findViewById(R.id.profile_date);
        linearLayout = findViewById(R.id.profile_nameLinear);

        firstName.setText(loginINFO.getFirstName());
        secondName.setText(loginINFO.getSecondName());
        thirdName.setText(loginINFO.getThirdName());
        fourthName.setText(loginINFO.getFourthName());
        address.setText(loginINFO.getAddress());
        email.setText(loginINFO.getEmail());
        password.setText(loginINFO.getPassword());
        nationalID.setText(loginINFO.getNationalID());
        phoneNo.setText(loginINFO.getUsername());
        accountType.setText("Individual");
        if (loginINFO.getGender().equals("0"))
            gender.setText("Male");
        else
            gender.setText("Female");
        date.setText(loginINFO.getBirthDate());

        phoneNo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new SharedClass(ProfilePage.this).showPhoneOptions(phoneNo.getText().toString());

//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                String mobile = "tel:+" + phoneNo.getText().toString();
//                Log.e("mobile", mobile);
//                intent.setData(Uri.parse(mobile));
//                try {
//                    startActivity(intent);
//
//                } catch (Exception e) {
//                    Toast.makeText(ProfilePage.this, "No Dialer Found!", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });

//        checkLanguage();
    }

    void checkLanguage() {
        if (language.equals("ar")) {
            nationalID.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_person_black_24dp), null);
            phoneNo.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_local_phone_black_24dp), null);
            address.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_location_on_black_24dp), null);
            email.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_email_black_24dp), null);
            password.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_https_black_24dp), null);
            date.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_date_range_black_24dp), null);
            accountType.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_credit_card), null);
            gender.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(ProfilePage.this, R.drawable.gender), null);
//            date.setGravity(Gravity.RIGHT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            nationalID.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_person_black_24dp), null
                    , null, null);
            phoneNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_local_phone_black_24dp), null
                    , null, null);
            address.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_location_on_black_24dp), null
                    , null, null);
            email.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_email_black_24dp), null
                    , null, null);
            password.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_https_black_24dp), null
                    , null, null);
            date.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_date_range_black_24dp), null
                    , null, null);
            accountType.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.ic_credit_card), null
                    , null, null);
            gender.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ProfilePage.this, R.drawable.gender), null
                    , null, null);
//            date_text.setGravity(Gravity.LEFT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        accountType.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        gender.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        date.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearLayout.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        nationalID.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        phoneNo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        address.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        email.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        password.startAnimation(animation);
    }

}