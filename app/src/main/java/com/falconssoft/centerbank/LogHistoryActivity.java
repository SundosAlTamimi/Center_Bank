package com.falconssoft.centerbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;
import static com.falconssoft.centerbank.MainActivity.watch;

public class LogHistoryActivity extends AppCompatActivity {
    //    PieChart pieChart, piechart2;
    List<ChequeInfo> LogHistoryList;
    ListView listLogHistory;
    //    Spinner spinnerState, spinnerTranse;
    List<String> ListState, ListTrans;
    ArrayAdapter<String> arrayAdapterStautes, arrayAdapterTrans;
    List<ChequeInfo> ChequeInfoLogHistoryMain;
    DatabaseHandler dbHandler;
    List<String> parametwrForGetLog;
    TextView help, AccAccount;
    LinearLayout helpDialog;
    String AccountNo, phoneNo, serverLink;
    TextView customName,dateText,cheqNo,SendCheque;
    public static ChequeInfo chequeInfoReSend;
    SwipeRefreshLayout swipeRefresh;
    boolean isRefresh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_history_report);

//        pieChart = findViewById(R.id.piechart);
//        piechart2 = findViewById(R.id.piechart2);
//        spinnerState = findViewById(R.id.spinnerState);
//        spinnerTranse = findViewById(R.id.spinnerTranse);
        SharedPreferences loginPrefs1 = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs1.getString("link", "");

        listLogHistory = findViewById(R.id.listLogHistory);
        AccAccount = findViewById(R.id.AccAccount);
        help = findViewById(R.id.help);
        customName=findViewById(R.id.customName);
        dateText=findViewById(R.id.date);
        cheqNo=findViewById(R.id.chNo);
        SendCheque=findViewById(R.id.SendCheque);
        SendCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogHistoryActivity.this, EditerCheackActivity.class);
                startActivity(intent);
            }
        });
        ChequeInfoLogHistoryMain = new ArrayList<>();
        parametwrForGetLog = new ArrayList<>();
        helpDialog = findViewById(R.id.helpDialog);
        helpDialog.setVisibility(View.GONE);
        dbHandler = new DatabaseHandler(LogHistoryActivity.this);
//        pieChart(pieChart);
//        pieChart(piechart2);

        LogHistoryList = new ArrayList<>();
        ListState = new ArrayList<>();
        ListTrans = new ArrayList<>();

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               isRefresh=true;
                new GetAllTransaction().execute();

            }
        });


        ListState.add("All");
        ListState.add("Accept");
        ListState.add("Reject");
//        new GetAllTransaction().execute();

        ListTrans.add("All");
        ListTrans.add("Send");
        ListTrans.add("Receive");


        cheqNo.setTag("1");
        cheqNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogHistoryActivity.this, "sort", Toast.LENGTH_SHORT).show();
                Collections.sort(ChequeInfoLogHistoryMain, new CheqNoSorter());
//                Log.e("Sort2",""+sortAlpha());
                if (cheqNo.getTag().toString().equals("1")) {
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    cheqNo.setTag("0");
                    cheqNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
                } else if (cheqNo.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoLogHistoryMain);
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    cheqNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);

                    cheqNo.setTag("1");
                }
            }
        });


        dateText.setTag("1");
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogHistoryActivity.this, "SortDate", Toast.LENGTH_SHORT).show();
                sortDate();
//                Log.e("Sort2",""+sortAlpha());
                if (dateText.getTag().toString().equals("1")) {
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    dateText.setTag("0");
                    dateText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
                } else if (dateText.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoLogHistoryMain);
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    dateText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);

                    dateText.setTag("1");
                }
            }
        });

        customName.setTag("0");
        customName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(LogHistoryActivity.this, "Sort", Toast.LENGTH_SHORT).show();
                sortAlpha();
//                Log.e("Sort2",""+sortAlpha());
                if (customName.getTag().toString().equals("1")) {
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    customName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
                    customName.setTag("0");
                } else if (customName.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoLogHistoryMain);
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);
                    customName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
                    customName.setTag("1");
                }

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (help.getText().toString().equals("0")) {
                    helpDialog.setVisibility(View.VISIBLE);
                    help.setText("1");
                } else if (help.getText().toString().equals("1")) {
                    helpDialog.setVisibility(View.GONE);
                    help.setText("0");
                }

            }
        });

//
//        arrayAdapterStautes = new ArrayAdapter<String>(LogHistoryActivity.this, R.layout.spinner_row, ListState);
//        spinnerState.setAdapter(arrayAdapterStautes);

//        arrayAdapterTrans = new ArrayAdapter<String>(LogHistoryActivity.this, R.layout.spinner_row, ListTrans);
//        spinnerTranse.setAdapter(arrayAdapterTrans);

//        ACCCODE=4014569990011000&MOBNO=&WHICH=0

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        AccountNo = getIntent().getStringExtra("AccountNo");
        phoneNo = loginPrefs.getString("mobile", "");

        parametwrForGetLog.add(AccountNo);
        parametwrForGetLog.add(phoneNo);
        parametwrForGetLog.add(watch);
        Log.e("parametser", "acc = " + AccountNo + "  " + parametwrForGetLog.get(0) + "    phone = " + parametwrForGetLog.get(1) + "      " + phoneNo + "  watch " + watch + "  " + parametwrForGetLog.get(2));

        if (watch.equals("0")) {
            AccAccount.setText(" This Account (" + AccountNo.substring(1) + ")");

        } else {
            AccAccount.setText(" For ALL Account");
        }

        new GetAllTransaction().execute();

    }

    void pieChart(PieChart pieChart) {
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new PieEntry(945f, 0));
        NoOfEmp.add(new PieEntry(1040f, 1));
//        NoOfEmp.add(new PieEntry(1133f, 2));
//        NoOfEmp.add(new PieEntry(1240f, 3));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Send Status");


        ArrayList year2 = new ArrayList();

        year2.add("2008");
        year2.add("2009");
//        year2.add("2010");
//        year2.add("2011");

        PieData data2 = new PieData(dataSet);
        pieChart.setData(data2);
        pieChart.setDescription(null);
//        pieChart.setNoDataTextColor(R.color.colorGold);
        pieChart.setHoleColor(R.color.colorGold);
//        pieChart.setCOL(R.color.colorGold);
//        pieChart.setTransparentCircleColor(R.color.Nocolor);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        dataSet.setValueTextColor(R.color.white);
//        dataSet.setValueLineColor(R.color.white);
        pieChart.animateXY(1500, 1500);
    }


    public void startEditerForReSend(ChequeInfo chequeInfo){
        chequeInfoReSend=chequeInfo;
        Intent reSendIntent=new Intent(LogHistoryActivity.this,EditerCheackActivity.class);
        reSendIntent.putExtra("ReSend","ReSend");
        startActivity(reSendIntent);

    }

    private class GetAllTransaction extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

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

                String link = serverLink + "GetLog";

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "ACCCODE=" + URLEncoder.encode(parametwrForGetLog.get(0), "UTF-8") + "&" +
                        "MOBNO=" + URLEncoder.encode(parametwrForGetLog.get(1), "UTF-8") + "&" +
                        "WHICH=" + URLEncoder.encode(parametwrForGetLog.get(2), "UTF-8");

                URL url = new URL(link);
                Log.e("link,3 ", serverLink+"   "+link+ "   "+data);

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

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

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
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

                    ChequeInfoLogHistoryMain = new ArrayList<>();

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        //[{"ROWID":"AAAp0DAAuAAAAC0AAC","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968","CHECKISSUEDATE":"28\/06\/2020 10:33:57","CHECKDUEDATE":"21\/12\/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0","AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKWRITEDATE":"28\/06\/2020 10:33:57","CHECKPICPATH":"E:\\00400991014569990011000390144.png","TRANSSTATUS":""}]}

                        obj.setRowId(finalObject.getString("ROWID1"));
                        obj.setBankNo(finalObject.getString("BANKNO"));


                        obj.setBankName(finalObject.getString("BANKNM"));
                        obj.setBranchNo(finalObject.getString("BRANCHNO"));

                        obj.setChequeNo(finalObject.getString("CHECKNO"));
                        obj.setAccCode(finalObject.getString("ACCCODE"));

                        obj.setIbanNo(finalObject.getString("IBANNO"));
                        obj.setCustName(finalObject.getString("CUSTOMERNM"));

                        obj.setQrCode(finalObject.getString("QRCODE"));
                        obj.setSerialNo(finalObject.getString("SERIALNO"));

                        obj.setCheckIsSueDate(finalObject.getString("CHECKISSUEDATE"));//?
                        obj.setCheckDueDate(finalObject.getString("CHECKDUEDATE"));//?

                        obj.setToCustomerName(finalObject.getString("TOCUSTOMERNM"));
                        obj.setMoneyInDinar(finalObject.getString("AMTJD"));

                        obj.setMoneyInFils(finalObject.getString("AMTFILS"));
                        obj.setMoneyInWord(finalObject.getString("AMTWORD"));

                        obj.setToCustomerMobel(finalObject.getString("TOCUSTOMERMOB"));

                        obj.setToCustomerNationalId(finalObject.getString("TOCUSTOMERNATID"));
                        obj.setCustomerWriteDate(finalObject.getString("CHECKWRITEDATE"));//?

//                        obj.setCheqPIc(finalObject.getString("CHECKPICPATH"));
                        obj.setTransType(finalObject.getString("TRANSSTATUS"));
                        obj.setStatus(finalObject.getString("STATUS"));
                        obj.setUserName(finalObject.getString("USERNO"));

                        obj.setISBF(finalObject.getString("ISFB"));
                        obj.setISCO(finalObject.getString("ISCO"));
                        obj.setCompanyName(finalObject.getString("COMPANY"));
                        obj.setNoteCheck(finalObject.getString("NOTE"));
                        obj.setISOpen("0");
                        ChequeInfoLogHistoryMain.add(obj);
                    }


//                  ChequeInfoLogHistoryMain.get(0).setCustName("مها");
//                    ChequeInfoLogHistoryMain.get(1).setCustName("تهاني");
//                    ChequeInfoLogHistoryMain.get(2).setCustName("عبير");
//                    ChequeInfoLogHistoryMain.get(3).setCustName("احمد");
                    sortAlpha();
                    ListAdapterLogHistory listAdapterLogHistory = new ListAdapterLogHistory(LogHistoryActivity.this, ChequeInfoLogHistoryMain);
                    listLogHistory.setAdapter(listAdapterLogHistory);

                    if (isRefresh) {
                        swipeRefresh.setRefreshing(false);
                        isRefresh = false;
                    } else {
                        isRefresh = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("TAG_GetStor", "****Failed to export data");
//                if(!isAssetsIn.equals("1")) {
//                    if (pd != null) {
//                        pd.dismiss();
//                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.ops))
//                                .setContentText(context.getResources().getString(R.string.faildstore))
//                                .show();
//                    }
//                }else{
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                    new SyncGetAssest().execute();
//                }
            }
//            progressDialog.dismiss();

        }
    }

    void sortAlpha() {
        Locale arabic = new Locale("ar");
        final Collator arabicCollator = Collator.getInstance(arabic);


        Collections.sort(ChequeInfoLogHistoryMain, new Comparator<ChequeInfo>() {


            @Override
            public int compare(ChequeInfo one, ChequeInfo two) {
                // TODO Auto-generated method stub

                Log.e("Sort",""+arabicCollator.compare(one.getCustName(), two.getCustName()));

                return arabicCollator.compare(one.getCustName(), two.getCustName());
            }

        });
    }


    private void sortDate() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //your own date format
//        if (reports != null) {
            Collections.sort(ChequeInfoLogHistoryMain, new Comparator<ChequeInfo>() {
                @Override
                public int compare(ChequeInfo o1, ChequeInfo o2) {
                    try {
                        return simpleDateFormat.parse(o2.getCheckDueDate()).compareTo(simpleDateFormat.parse(o1.getCheckDueDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
//        }
    }



    class CheqNoSorter implements Comparator<ChequeInfo> {
        @Override
        public int compare(ChequeInfo one, ChequeInfo another) {
            int returnVal = 0;

            if (Integer.parseInt(one.getChequeNo()) < Integer.parseInt(another.getChequeNo())) {
                returnVal = -1;
            } else if (Integer.parseInt(one.getChequeNo()) > Integer.parseInt(another.getChequeNo())) {
                returnVal = 1;
            } else if (one.getChequeNo() == another.getChequeNo()) {
                returnVal = 0;
            }
            return returnVal;
        }

    }

}
