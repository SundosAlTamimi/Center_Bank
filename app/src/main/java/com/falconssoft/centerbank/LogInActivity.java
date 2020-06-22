package com.falconssoft.centerbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    EditText userName,Password;
    Button singIn,singUp;

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
                Intent MainActivityIntent=new Intent(LogInActivity.this,MainActivity.class);
                startActivity(MainActivityIntent);
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainActivityIntent=new Intent(LogInActivity.this,SingUpActivity.class);
                startActivity(MainActivityIntent);
            }
        });


    }

    void Authintication(){
        if(!userName.getText().toString().equals("")&&!Password.getText().toString().equals("")){
            if(userName.getText().toString().equals("1234")&&Password.getText().toString().equals("1234")){

                Intent MainActivityIntent=new Intent(LogInActivity.this,MainActivity.class);
                startActivity(MainActivityIntent);

            }else{
                Toast.makeText(this, " UserName Or Password Not Correct", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Please Add UserName Or Password", Toast.LENGTH_SHORT).show();
        }


    }

    void init(){

        userName =findViewById(R.id.LogInUserName);
        Password =findViewById(R.id.LogInPassword);
        singIn=findViewById(R.id.LogInSingIn);
        singUp=findViewById(R.id.LogInSingUp);

    }
}
