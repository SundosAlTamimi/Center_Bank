package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.falconssoft.centerbank.Models.CashierChequeModel;
import com.falconssoft.centerbank.Models.ChequeInfo;
import com.falconssoft.centerbank.Models.LoginINFO;
import com.hbb20.CountryCodePicker;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class CashierCheque extends AppCompatActivity {

    EditText cashier_IdNo, cashier_phone, casher_first_name, cashier_second_name, cashier_third_name, cashier_fourth_name, cashier_address, cashier_reson, denier, Phils, cashier_BN1, cashier_BN2, cashier_BN3, cashier_CUR1, cashier_CUR2, cashier_CUR3, cashier_ACC1, cashier_ACC2, cashier_ACC3, cashier_CUS1, cashier_CUS2, cashier_CUS3, cashier_CUS4, cashier_CUS5, cashier_CUS6, cashier_SE1, cashier_SE2, cashier_CH1,
            first_name_, second_name_, thered_name_, fourth_name_;
    TextView AmouWord, Date;
    RadioButton radioButton_PER, radioButton_MASTER;
    LinearLayout toMasterLiner;
    Spinner bankNameSpinner, bankNoSpinner, relationSpinner;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    String today, countryCode = "962";
    List<String> bankName, relationList;
    List<String> branchName;
    ArrayAdapter<String> arrayAdapterBank, arrayAdapterBranch, arrayAdapterRelation;
    Button cashierCheck_send;
    SweetAlertDialog pd;
    String serverLink = "http://falconssoft.net/ScanChecks/APIMethods.dll/", phoneNoUser;
    private JSONObject jsonObject;
    Calendar myCalendar;
    String bankNameString = "Jordan Bank", branchNameString = "Abdoun Branch", relationString = "Consanguinity";
    LoginINFO infoUser;
    DatabaseHandler databaseHandler;
    private CountryCodePicker ccp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashier_check_layout);

        init();


        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs.getString("link", "");
        phoneNoUser = loginPrefs.getString("mobile", "");

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

        relationList.add(getResources().getString(R.string.consanguinity));
        relationList.add(getResources().getString(R.string.Business));
        relationList.add(getResources().getString(R.string.other));
        arrayAdapterRelation = new ArrayAdapter(this, R.layout.spinner_layout, relationList);
        arrayAdapterRelation.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        relationSpinner.setAdapter(arrayAdapterRelation);


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

        cashierCheck_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendCashierCheque();

            }
        });


        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CashierCheque.this, openDatePickerDialog(Date), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bankNameString = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bankNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                branchNameString = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        relationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (getResources().getString(R.string.consanguinity).equals(adapterView.getItemAtPosition(position).toString())) {
                    relationString = "0";
                } else if (getResources().getString(R.string.Business).equals(adapterView.getItemAtPosition(position).toString())) {
                    relationString = "1";
                } else if (getResources().getString(R.string.other).equals(adapterView.getItemAtPosition(position).toString())) {
                    relationString = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();

            }
        });

    }


    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final TextView editText) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String dateSelected = sdf.format(myCalendar.getTime());

                editText.setText(dateSelected);
            }

        };
        return date;
    }


    void SendCashierCheque() {


        if (!TextUtils.isEmpty(cashier_IdNo.getText().toString()) && cashier_IdNo.getText().toString().length() == 10) {
            if (!TextUtils.isEmpty(cashier_phone.getText().toString()) && cashier_phone.getText().toString().length() == 10) {
                if (!String.valueOf(cashier_phone.getText().toString().charAt(0)).equals("0"))
                    if (!TextUtils.isEmpty(casher_first_name.getText().toString())) {
                        if (!TextUtils.isEmpty(cashier_second_name.getText().toString())) {

                            if (!TextUtils.isEmpty(cashier_third_name.getText().toString())) {

                                if (!TextUtils.isEmpty(cashier_fourth_name.getText().toString())) {

                                    if (!TextUtils.isEmpty(cashier_address.getText().toString())) {

                                        if (!TextUtils.isEmpty(cashier_reson.getText().toString())) {


                                            if (!TextUtils.isEmpty(denier.getText().toString())) {

                                                if (!TextUtils.isEmpty(Phils.getText().toString())) {

                                                    if (!TextUtils.isEmpty(cashier_BN1.getText().toString())) {

                                                        if (!TextUtils.isEmpty(cashier_BN2.getText().toString())) {

                                                            if (!TextUtils.isEmpty(cashier_BN3.getText().toString())) {

                                                                if (!TextUtils.isEmpty(cashier_CUR1.getText().toString())) {

                                                                    if (!TextUtils.isEmpty(cashier_CUR1.getText().toString())) {

                                                                        if (!TextUtils.isEmpty(cashier_CUR2.getText().toString())) {

                                                                            if (!TextUtils.isEmpty(cashier_CUR3.getText().toString())) {

                                                                                if (!TextUtils.isEmpty(cashier_CUS4.getText().toString())) {

                                                                                    if (!TextUtils.isEmpty(cashier_CUS5.getText().toString())) {

                                                                                        if (!TextUtils.isEmpty(cashier_CUS6.getText().toString())) {

                                                                                            if (!TextUtils.isEmpty(cashier_SE1.getText().toString())) {


                                                                                                if (!TextUtils.isEmpty(cashier_SE2.getText().toString())) {

                                                                                                    if (!TextUtils.isEmpty(cashier_CH1.getText().toString())) {

                                                                                                        if (!TextUtils.isEmpty(AmouWord.getText().toString())) {

                                                                                                            if (!TextUtils.isEmpty(Date.getText().toString())) {

                                                                                                                if (radioButton_MASTER.isChecked()) {
                                                                                                                    if (!TextUtils.isEmpty(first_name_.getText().toString())) {
                                                                                                                        if (!TextUtils.isEmpty(second_name_.getText().toString())) {
                                                                                                                            if (!TextUtils.isEmpty(thered_name_.getText().toString())) {
                                                                                                                                if (!TextUtils.isEmpty(fourth_name_.getText().toString())) {

                                                                                                                                    CashierChequeModel cashierCheque = new CashierChequeModel();

                                                                                                                                    cashierCheque.setBANKNO("004");
                                                                                                                                    cashierCheque.setBANKNM(bankNameString);
                                                                                                                                    cashierCheque.setBRANCHNM(branchNameString);
                                                                                                                                    cashierCheque.setTOCUSTMOB(countryCode + cashier_phone.getText().toString());
                                                                                                                                    cashierCheque.setTOCUSTNATID(cashier_IdNo.getText().toString());
                                                                                                                                    cashierCheque.setTOCUSTNAME(casher_first_name.getText().toString());

                                                                                                                                    String AccNo = cashier_BN1.getText().toString() +
                                                                                                                                            cashier_BN2.getText().toString() +
                                                                                                                                            cashier_BN3.getText().toString() +
                                                                                                                                            cashier_CUR1.getText().toString() +
                                                                                                                                            cashier_CUR2.getText().toString() +
                                                                                                                                            cashier_CUR3.getText().toString() +
                                                                                                                                            cashier_ACC1.getText().toString() +
                                                                                                                                            cashier_ACC2.getText().toString() +
                                                                                                                                            cashier_ACC3.getText().toString() +
                                                                                                                                            cashier_CUS1.getText().toString() +
                                                                                                                                            cashier_CUS2.getText().toString() +
                                                                                                                                            cashier_CUS3.getText().toString() +
                                                                                                                                            cashier_CUS4.getText().toString() +
                                                                                                                                            cashier_CUS5.getText().toString() +
                                                                                                                                            cashier_CUS6.getText().toString() +
                                                                                                                                            cashier_SE1.getText().toString() +
                                                                                                                                            cashier_SE2.getText().toString() +
                                                                                                                                            cashier_CH1.getText().toString();

                                                                                                                                    cashierCheque.setTOCUSTFNAME(cashier_second_name.getText().toString());
                                                                                                                                    cashierCheque.setTOCUSTGNAME(cashier_third_name.getText().toString());
                                                                                                                                    cashierCheque.setTOCUSTFAMNAME(cashier_fourth_name.getText().toString());
                                                                                                                                    cashierCheque.setADDRESS(cashier_address.getText().toString());
                                                                                                                                    String custName = casher_first_name.getText().toString() + " " + cashier_second_name.getText().toString() + " " + cashier_third_name.getText().toString() + " " + cashier_fourth_name.getText().toString();
                                                                                                                                    cashierCheque.setCUSTNAME(custName);
                                                                                                                                    cashierCheque.setCUSTMOBNO(phoneNoUser);
                                                                                                                                    cashierCheque.setAMTJD(denier.getText().toString());
                                                                                                                                    cashierCheque.setAMTFILS(Phils.getText().toString());
                                                                                                                                    cashierCheque.setAMTWORD(AmouWord.getText().toString());
                                                                                                                                    cashierCheque.setREQDATE(Date.getText().toString());
                                                                                                                                    cashierCheque.setPURPOSE(cashier_reson.getText().toString());
                                                                                                                                    cashierCheque.setRELATIONSHIP(relationString);
                                                                                                                                    cashierCheque.setACCFORCUST(AccNo);
                                                                                                                                    cashierCheque.setRECIPTNAME(first_name_.getText().toString());
                                                                                                                                    cashierCheque.setRECIPTFNAME(second_name_.getText().toString());
                                                                                                                                    cashierCheque.setRECIPTGNAME(thered_name_.getText().toString());
                                                                                                                                    cashierCheque.setRECIPTFAMNAME(fourth_name_.getText().toString());

                                                                                                                                    jsonObject = new JSONObject();
                                                                                                                                    jsonObject = cashierCheque.getJSONObject();
                                                                                                                                    new SaveCashierCheque().execute();

                                                                                                                                } else {
                                                                                                                                    fourth_name_.setError("Required!");
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                thered_name_.setError("Required!");
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            second_name_.setError("Required!");
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        first_name_.setError("Required!");
                                                                                                                    }

                                                                                                                } else {
                                                                                                                    CashierChequeModel cashierCheque = new CashierChequeModel();

                                                                                                                    cashierCheque.setBANKNO("004");
                                                                                                                    cashierCheque.setBANKNM(bankNameString);
                                                                                                                    cashierCheque.setBRANCHNM(branchNameString);
                                                                                                                    cashierCheque.setTOCUSTMOB(cashier_phone.getText().toString());
                                                                                                                    cashierCheque.setTOCUSTNATID(cashier_IdNo.getText().toString());
                                                                                                                    cashierCheque.setTOCUSTNAME(casher_first_name.getText().toString());

                                                                                                                    String AccNo = cashier_BN1.getText().toString() +
                                                                                                                            cashier_BN2.getText().toString() +
                                                                                                                            cashier_BN3.getText().toString() +
                                                                                                                            cashier_CUR1.getText().toString() +
                                                                                                                            cashier_CUR2.getText().toString() +
                                                                                                                            cashier_CUR3.getText().toString() +
                                                                                                                            cashier_ACC1.getText().toString() +
                                                                                                                            cashier_ACC2.getText().toString() +
                                                                                                                            cashier_ACC3.getText().toString() +
                                                                                                                            cashier_CUS1.getText().toString() +
                                                                                                                            cashier_CUS2.getText().toString() +
                                                                                                                            cashier_CUS3.getText().toString() +
                                                                                                                            cashier_CUS4.getText().toString() +
                                                                                                                            cashier_CUS5.getText().toString() +
                                                                                                                            cashier_CUS6.getText().toString() +
                                                                                                                            cashier_SE1.getText().toString() +
                                                                                                                            cashier_SE2.getText().toString() +
                                                                                                                            cashier_CH1.getText().toString();

                                                                                                                    infoUser = databaseHandler.getActiveUserInfo();
                                                                                                                    cashierCheque.setTOCUSTFNAME(cashier_second_name.getText().toString());
                                                                                                                    cashierCheque.setTOCUSTGNAME(cashier_third_name.getText().toString());
                                                                                                                    cashierCheque.setTOCUSTFAMNAME(cashier_fourth_name.getText().toString());
                                                                                                                    cashierCheque.setADDRESS(cashier_address.getText().toString());
                                                                                                                    String custName = casher_first_name.getText().toString() + " " + cashier_second_name.getText().toString() + " " + cashier_third_name.getText().toString() + " " + cashier_fourth_name.getText().toString();
                                                                                                                    cashierCheque.setCUSTNAME(custName);
                                                                                                                    cashierCheque.setCUSTMOBNO(phoneNoUser);
                                                                                                                    cashierCheque.setAMTJD(denier.getText().toString());
                                                                                                                    cashierCheque.setAMTFILS(Phils.getText().toString());
                                                                                                                    cashierCheque.setAMTWORD(AmouWord.getText().toString());
                                                                                                                    cashierCheque.setREQDATE(Date.getText().toString());
                                                                                                                    cashierCheque.setPURPOSE(cashier_reson.getText().toString());
                                                                                                                    cashierCheque.setRELATIONSHIP(relationString);
                                                                                                                    cashierCheque.setACCFORCUST(AccNo);
                                                                                                                    cashierCheque.setRECIPTNAME(infoUser.getFirstName());//sinup
                                                                                                                    cashierCheque.setRECIPTFNAME(infoUser.getSecondName());
                                                                                                                    cashierCheque.setRECIPTGNAME(infoUser.getThirdName());
                                                                                                                    cashierCheque.setRECIPTFAMNAME(infoUser.getFourthName());

                                                                                                                    jsonObject = new JSONObject();
                                                                                                                    jsonObject = cashierCheque.getJSONObject();
                                                                                                                    new SaveCashierCheque().execute();

                                                                                                                }


                                                                                                            } else {
                                                                                                                Date.setError("Required!");
                                                                                                            }

                                                                                                        } else {
                                                                                                            AmouWord.setError("Required!");
                                                                                                        }

                                                                                                    } else {
                                                                                                        cashier_CH1.setError("Required!");
                                                                                                    }

                                                                                                } else {
                                                                                                    cashier_SE2.setError("Required!");
                                                                                                }

                                                                                            } else {
                                                                                                cashier_SE1.setError("Required!");
                                                                                            }

                                                                                        } else {
                                                                                            cashier_CUS6.setError("Required!");
                                                                                        }

                                                                                    } else {
                                                                                        cashier_CUS5.setError("Required!");
                                                                                    }

                                                                                } else {
                                                                                    cashier_CUS4.setError("Required!");
                                                                                }

                                                                            } else {
                                                                                cashier_CUR3.setError("Required!");
                                                                            }

                                                                        } else {
                                                                            cashier_CUR2.setError("Required!");
                                                                        }

                                                                    } else {
                                                                        cashier_CUR1.setError("Required!");
                                                                    }

                                                                } else {
                                                                    cashier_CUR1.setError("Required!");
                                                                }

                                                            } else {
                                                                cashier_BN3.setError("Required!");
                                                            }

                                                        } else {
                                                            cashier_BN2.setError("Required!");
                                                        }

                                                    } else {
                                                        cashier_BN1.setError("Required!");
                                                    }

                                                } else {
                                                    Phils.setError("Required!");
                                                }

                                            } else {
                                                denier.setError("Required!");
                                            }


                                        } else {
                                            cashier_reson.setError("Required!");
                                        }

                                    } else {
                                        cashier_address.setError("Required!");
                                    }

                                } else {
                                    cashier_fourth_name.setError("Required!");
                                }

                            } else {
                                cashier_third_name.setError("Required!");
                            }

                        } else {
                            cashier_second_name.setError("Required!");
                        }
                    } else {
                        casher_first_name.setError("Required!");
                    }
                else
                    cashier_phone.setError(getResources().getString(R.string.zero_digit));
            } else {
                cashier_phone.setError("Required!");
            }
        } else {
            cashier_IdNo.setError("Required!");
        }


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
            if (Phils.getText().toString().equals("") && denier.getText().toString().equals("")) {
                AmouWord.setText(amountWord + "");
            }


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void init() {

        databaseHandler = new DatabaseHandler(CashierCheque.this);
        myCalendar = Calendar.getInstance();
        cashier_IdNo = findViewById(R.id.cashier_IdNo);
        cashier_phone = findViewById(R.id.cashier_phone);
        casher_first_name = findViewById(R.id.casher_first_name);
        cashier_second_name = findViewById(R.id.cashier_second_name);
        cashier_third_name = findViewById(R.id.cashier_third_name);
        cashier_fourth_name = findViewById(R.id.cashier_fourth_name);
        cashier_address = findViewById(R.id.cashier_address);
        cashier_reson = findViewById(R.id.cashier_reson);
//        cashier_customerName = findViewById(R.id.cashier_customerName);
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
        relationSpinner = findViewById(R.id.relationSpinner);
        toMasterLiner.setVisibility(View.GONE);
        cashierCheck_send = findViewById(R.id.cashierCheck_send);
        first_name_ = findViewById(R.id.first_name_);
        second_name_ = findViewById(R.id.second_name_);
        thered_name_ = findViewById(R.id.thered_name_);
        fourth_name_ = findViewById(R.id.fourth_name_);
        ccp = findViewById(R.id.cashierCheck_ccp);

        bankName = new ArrayList<>();
        branchName = new ArrayList<>();
        relationList = new ArrayList<>();


        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        Date.setText(convertToEnglish(today));

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }


    private class SaveCashierCheque extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
            pd = new SweetAlertDialog(CashierCheque.this, SweetAlertDialog.PROGRESS_TYPE);
            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            pd.setTitleText("Save...");
            pd.setCancelable(false);
            pd.show();

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
                String link = serverLink + "SaveCashierCheck";


                String data = "INFO=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
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
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("Save", "cashierCheque -->" + stringBuffer.toString());
                Log.e("jsonObject.toString()", "save cashierCheque -->" + jsonObject.toString());

                Log.e("tag", "cashierCheque   -->" + data);

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

            if (s != null && s.contains("\"StatusDescreption\":\"OK\"")) {
                Log.e("tag", "****saved Success cashierCheque");
                pd.dismissWithAnimation();
                SweetAlertDialog sweet = new SweetAlertDialog(CashierCheque.this, SweetAlertDialog.SUCCESS_TYPE);
                sweet.setTitleText("Successful");
                sweet.setContentText("Processing ...");
                sweet.setCanceledOnTouchOutside(false);
                sweet.setConfirmText("Ok");
                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                        sDialog.dismissWithAnimation();
                    }
                });
                sweet.show();
//                    pushCheque.setEnabled(true);
            }
            if (s != null && s.contains("\"StatusDescreption\":\"Error in Saving Cashier Check.\"")) {
                Log.e("tag", "****Failed to export data");

                SweetAlertDialog sweet = new SweetAlertDialog(CashierCheque.this, SweetAlertDialog.ERROR_TYPE);
                sweet.setTitleText("WARNING");
                sweet.setContentText("Error in Processing Cashier Check.!");
                sweet.setCanceledOnTouchOutside(false);
                sweet.setConfirmText("Close");
                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                sweet.show();

                pd.dismissWithAnimation();

//                    pushCheque.setEnabled(true);
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");


                SweetAlertDialog sweet = new SweetAlertDialog(CashierCheque.this, SweetAlertDialog.ERROR_TYPE);
                sweet.setTitleText("WARNING");
                sweet.setContentText("Error in Processing Cashier Check.! \n" + s);
                sweet.setCanceledOnTouchOutside(false);
                sweet.setConfirmText("Close");
                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                sweet.show();

                pd.dismissWithAnimation();
//                pushCheque.setEnabled(true);
            }
        }
    }
}
