package com.falconssoft.centerbank;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogHistoryActivity extends AppCompatActivity {
    PieChart pieChart,piechart2;
    List<ChequeInfo> LogHistoryList;
    ListView listLogHistory;
    Spinner spinnerState,spinnerTranse;
    List<String>ListState,ListTrans;
    ArrayAdapter<String>arrayAdapterStautes,arrayAdapterTrans;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_history_report);

        pieChart = findViewById(R.id.piechart);
        piechart2= findViewById(R.id.piechart2);
        spinnerState=findViewById(R.id.spinnerState);
        spinnerTranse=findViewById(R.id.spinnerTranse);
        listLogHistory=findViewById(R.id.listLogHistory);
        pieChart(pieChart);
        pieChart(piechart2);

        LogHistoryList=new ArrayList<>();
        ListState=new ArrayList<>();
        ListTrans=new ArrayList<>();
        ListState.add("All");
        ListState.add("Accept");
        ListState.add("Reject");
//        new GetAllTransaction().execute();

        ListTrans.add("All");
        ListTrans.add("Send");
        ListTrans.add("Receive");


        arrayAdapterStautes = new ArrayAdapter<String>(LogHistoryActivity.this, R.layout.spinner_row, ListState);
        spinnerState.setAdapter(arrayAdapterStautes);

        arrayAdapterTrans = new ArrayAdapter<String>(LogHistoryActivity.this, R.layout.spinner_row, ListTrans);
        spinnerTranse.setAdapter(arrayAdapterTrans);

//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//              "", "", "", "", "ايلا ",
//              "", "", "", "",
//                "", "", "", "1", "1", "10/10/2020"));
//
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "1", "0", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "1", "0", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "0", "0", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "0", "1", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "0", "1", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "0", "1", "10/10/2020"));
//
//        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
//                "", "", "", "", "ايلا ",
//                "", "", "", "",
//                "", "", "", "1", "0", "10/10/2020"));

        ListAdapterLogHistory listAdapterLogHistory=new ListAdapterLogHistory(LogHistoryActivity.this,LogHistoryList);
        listLogHistory.setAdapter(listAdapterLogHistory);
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
                String link = "http://"+"10.0.0.16:8081" + "/GetSore";

                //
//                String data = "compno=" + URLEncoder.encode("736", "UTF-8") + "&" +
//                        "compyear=" + URLEncoder.encode("2019", "UTF-8") ;
////
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();

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

            if (JsonResponse != null && JsonResponse.contains("STORENO")) {
                Log.e("TAG_GetStor", "****Success");

//                pd.getProgressHelper().setBarColor(Color.parseColor("#1F6381"));
//                pd.setTitleText(context.getResources().getString(R.string.storesave));
//                try {

//                    JSONArray parentArray = new JSONArray(JsonResponse);
//
//
//                    List<Stk> stks=new ArrayList<>();
//
//                    for (int i = 0; i < parentArray.length(); i++) {
//                        JSONObject finalObject = parentArray.getJSONObject(i);
//
//                        Stk obj = new Stk();
//
//                        obj.setStkNo(finalObject.getString("STORENO"));
//                        obj.setStkName(finalObject.getString("STORENAME"));
//
//
//                        stks.add(obj);
//
//                    }
//
////
//                    dbHandler.deleteAllItem("STK");
//                    for (int i = 0; i < stks.size(); i++) {
//                        dbHandler.addStory(stks.get(i));
//                    }
//
//                    Log.e("TAG_GetStor", "****SaveSuccess");
//                    pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pd.setTitleText(context.getResources().getString(R.string.storeSave));
//
//
//                    if(!isAssetsIn.equals("1")) {
//                        if(pd!=null) {
//                            pd.dismiss();
//
//                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                    .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
//                                    .setContentText(context.getResources().getString(R.string.importSuc))
//                                    .show();
//                        }
//
//                    }else{
//                        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                        pd.setTitleText(context.getResources().getString(R.string.storeSave));
//                        new SyncGetAssest().execute();
//                    }

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

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

}
