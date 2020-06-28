package com.falconssoft.centerbank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.Setting;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {

    EditText userName, password;
    Button singIn, singUp;
    private ImageView arabic, english;
    public static String language = "en";
    ImageView SettingImage;
    DatabaseHandler databaseHandler;
    private Animation animation;
    public static final String LANGUAGE_FLAG = "LANGUAGE_FLAG";

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
                Intent mainActivityIntent = new Intent(LogInActivity.this, SingUpActivity.class);
                mainActivityIntent.putExtra(LANGUAGE_FLAG, language);
                startActivity(mainActivityIntent);
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

        SettingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSettingButton();
            }
        });

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        userName.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        password.startAnimation(animation);

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


    void addSettingButton() {
        final Dialog dialog = new Dialog(LogInActivity.this,R.style.Theme_Dialog);
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

        databaseHandler=new DatabaseHandler(LogInActivity.this);
        userName = findViewById(R.id.LogInUserName);
        password = findViewById(R.id.LogInPassword);
        singIn = findViewById(R.id.LogInSingIn);
        singUp = findViewById(R.id.LogInSingUp);
        arabic = findViewById(R.id.login_arabic);
        english = findViewById(R.id.login_english);
        SettingImage=findViewById(R.id.Setting);
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
