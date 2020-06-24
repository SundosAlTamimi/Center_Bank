package com.falconssoft.centerbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {

    EditText userName, password;
    Button singIn, singUp;
    private ImageView arabic, english;
    private static String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        init();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Authintication();
                Intent MainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(MainActivityIntent);
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainActivityIntent = new Intent(LogInActivity.this, SingUpActivity.class);
                startActivity(MainActivityIntent);
            }
        });

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "ar";
                LocaleAppUtils.setLocale(new Locale("ar"));
                LocaleAppUtils.setConfigChange(LogInActivity.this);
                finish();
                startActivity(getIntent());

            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "en";
                LocaleAppUtils.setLocale(new Locale("en"));
                LocaleAppUtils.setConfigChange(LogInActivity.this);
                finish();
                startActivity(getIntent());
            }
        });
    }

    void Authintication() {
        if (!userName.getText().toString().equals("") && !password.getText().toString().equals("")) {
            if (userName.getText().toString().equals("1234") && password.getText().toString().equals("1234")) {

                Intent MainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(MainActivityIntent);

            } else {
                Toast.makeText(this, " UserName Or Password Not Correct", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please Add UserName Or Password", Toast.LENGTH_SHORT).show();
        }


    }

    void init() {

        userName = findViewById(R.id.LogInUserName);
        password = findViewById(R.id.LogInPassword);
        singIn = findViewById(R.id.LogInSingIn);
        singUp = findViewById(R.id.LogInSingUp);
        arabic = findViewById(R.id.login_arabic);
        english = findViewById(R.id.login_english);

        if (language.equals("ar")) {
            userName.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_person_black_24dp), null);
            password.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_https_black_24dp), null);
        } else {
            userName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_person_black_24dp), null
                    , null, null);
            password.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(LogInActivity.this, R.drawable.ic_https_black_24dp), null
                    , null, null);
        }
    }
}
