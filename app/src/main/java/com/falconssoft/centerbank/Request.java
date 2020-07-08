package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Request extends AppCompatActivity {
    public  String FROMUSER_No="";
    public  String FROMUSER_Name="";
    public  String FROMUSER="";
    public  String TOUSER_no="";
    public  String TOUSER_Name="";
    public  String COMPNAME="";
    public  String NOTE="";
    public  String AMOUNT="";
    EditText edit_customerName,phoneNo,amountDinar,company,note;
    Button sendButton,cancelButton;
    boolean isFull=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        //   http://localhost:8081/AddRequest?REQINFO={"FROMUSER":"0798899716","FROMUSERNM":"ALAA",
        //   "TOUSER":"0798899702","TOUSERNM":"WAEL","COMPNAME":"XYZ","NOTE":"Any Thing","AMOUNT":"150.600"}
        initView();
    }

    private boolean validateRequired() {

        TOUSER_no = phoneNo.getText().toString();
        TOUSER_Name = edit_customerName.getText().toString();
        AMOUNT = amountDinar.getText().toString();
        if(!TextUtils.isEmpty(TOUSER_no)){}
        else
        {
            phoneNo.setError(getResources().getString(R.string.required));
            isFull=false;
        }

        if(!TextUtils.isEmpty(TOUSER_Name)){}
        else
        {
            edit_customerName.setError(getResources().getString(R.string.required));
            isFull=false;
        }

        if(!TextUtils.isEmpty(AMOUNT)){}
        else
        {
            amountDinar.setError(getResources().getString(R.string.required));
            isFull=false;
        }
        return isFull;

    }

    private void initView() {
        edit_customerName = findViewById(R.id.edit_customerName);
        phoneNo = findViewById(R.id.edit_phoneNo);
        amountDinar = findViewById(R.id.denier);
        company = findViewById(R.id.edit_company);
        note = findViewById(R.id.edit_notes);
        sendButton = findViewById(R.id.AcceptButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("sendButton",""+validateRequired());
               if( validateRequired())
               {
                   sendData();
               }
               else {
                   isFull=true;
               }


            }
        });
        cancelButton = findViewById(R.id.RejectButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent( Request.this,MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void sendData() {
    }
}
