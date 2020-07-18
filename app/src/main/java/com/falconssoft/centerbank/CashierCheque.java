package com.falconssoft.centerbank;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CashierCheque extends AppCompatActivity {

    EditText cashier_IdNo, cashier_phone, casher_first_name, cashier_second_name, cashier_third_name, cashier_fourth_name, cashier_address, cashier_reson, cashier_customerName, denier, Phils, cashier_BN1, cashier_BN2, cashier_BN3, cashier_CUR1, cashier_CUR2, cashier_CUR3, cashier_ACC1, cashier_ACC2, cashier_ACC3, cashier_CUS1, cashier_CUS2, cashier_CUS3, cashier_CUS4, cashier_CUS5, cashier_CUS6, cashier_SE1, cashier_SE2, cashier_CH1;
    TextView AmouWord, Date;
    RadioButton radioButton_PER, radioButton_MASTER;
    LinearLayout toMasterLiner;
    Spinner bankNameSpinner, bankNoSpinner;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    String today;
    List<String> bankName;
    List<String> branchName;
    ArrayAdapter<String> arrayAdapterBank,arrayAdapterBranch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashier_check_layout);

        init();

        bankName.add("Jordan Bank");

        branchName.add("Abdoun Branch");
        branchName.add("Abu Alanda Branch");
        branchName.add("Abu Nusair Branch");
        branchName.add("Airport Branch");
        branchName.add("Ajlun Branch");

        arrayAdapterBank = new ArrayAdapter(this, R.layout.spinner_layout, bankName);
        arrayAdapterBank.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        bankNameSpinner.setAdapter(arrayAdapterBank);

        arrayAdapterBranch = new ArrayAdapter(this, R.layout.spinner_layout, branchName);
        arrayAdapterBranch.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        bankNoSpinner.setAdapter(arrayAdapterBranch);


        radioButton_PER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMasterLiner.setVisibility(View.GONE);
            }
        });


        radioButton_MASTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMasterLiner.setVisibility(View.VISIBLE);
            }
        });

        Phils.addTextChangedListener(textWatcher);

        denier.addTextChangedListener(textWatcher);

    }



    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = "";
            if (!denier.getText().toString().equals("")) {

                if (!Phils.getText().toString().equals("")) {
                    amount = denier.getText().toString() + "." + Phils.getText().toString();
                } else {
                    amount = denier.getText().toString() + "." + "00";
                }
            }

            if (!Phils.getText().toString().equals("")) {

                if (!denier.getText().toString().equals("")) {
                    amount = denier.getText().toString() + "." + Phils.getText().toString();
                } else {
                    amount = "00" + "." + Phils.getText().toString();
                }
            }


            NumberToArabic numberToArabic = new NumberToArabic();
            String amountWord = numberToArabic.getArabicString(amount);

            Log.e("Ammount", "Jd +" + amountWord);
            AmouWord.setText(amountWord + " فقط لا غير");


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void init() {

        cashier_IdNo = findViewById(R.id.cashier_IdNo);
        cashier_phone = findViewById(R.id.cashier_phone);
        casher_first_name = findViewById(R.id.casher_first_name);
        cashier_second_name = findViewById(R.id.cashier_second_name);
        cashier_third_name = findViewById(R.id.cashier_third_name);
        cashier_fourth_name = findViewById(R.id.cashier_fourth_name);
        cashier_address = findViewById(R.id.cashier_address);
        cashier_reson = findViewById(R.id.cashier_reson);
        cashier_customerName = findViewById(R.id.cashier_customerName);
        denier = findViewById(R.id.denier);
        Phils = findViewById(R.id.Phils);
        cashier_BN1 = findViewById(R.id.cashier_BN1);
        cashier_BN2 = findViewById(R.id.cashier_BN2);
        cashier_BN3 = findViewById(R.id.cashier_BN3);
        cashier_CUR1 = findViewById(R.id.cashier_CUR1);
        cashier_CUR2 = findViewById(R.id.cashier_CUR2);
        cashier_CUR3 = findViewById(R.id.cashier_CUR3);
        cashier_ACC1 = findViewById(R.id.cashier_ACC1);
        cashier_ACC2 = findViewById(R.id.cashier_ACC2);
        cashier_ACC3 = findViewById(R.id.cashier_ACC3);
        cashier_CUS1 = findViewById(R.id.cashier_CUS1);
        cashier_CUS2 = findViewById(R.id.cashier_CUS2);
        cashier_CUS3 = findViewById(R.id.cashier_CUS3);
        cashier_CUS4 = findViewById(R.id.cashier_CUS4);
        cashier_CUS5 = findViewById(R.id.cashier_CUS5);
        cashier_CUS6 = findViewById(R.id.cashier_CUS6);
        cashier_SE1 = findViewById(R.id.cashier_SE1);
        cashier_SE2 = findViewById(R.id.cashier_SE2);
        cashier_CH1 = findViewById(R.id.cashier_CH1);
        AmouWord = findViewById(R.id.AmouWord);
        Date = findViewById(R.id.Date);
        radioButton_PER = findViewById(R.id.radioButton_PER);
        radioButton_MASTER = findViewById(R.id.radioButton_MASTER);
        toMasterLiner = findViewById(R.id.toMasterLiner);
        bankNameSpinner = findViewById(R.id.bankNameSpinner);
        bankNoSpinner = findViewById(R.id.bankNoSpinner);
        toMasterLiner.setVisibility(View.GONE);

        bankName=new ArrayList<>();
        branchName=new ArrayList<>();


        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        Date.setText(convertToEnglish(today));

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }
}
