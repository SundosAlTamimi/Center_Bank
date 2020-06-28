package com.falconssoft.centerbank;

import android.os.Bundle;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class LogHistoryActivity extends AppCompatActivity {
    PieChart pieChart,piechart2;
    List<ChequeInfo> LogHistoryList;
    ListView listLogHistory;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_history_report);

        pieChart = findViewById(R.id.piechart);
        piechart2= findViewById(R.id.piechart2);
        listLogHistory=findViewById(R.id.listLogHistory);
        pieChart(pieChart);
        pieChart(piechart2);

        LogHistoryList=new ArrayList<>();


        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
              "", "", "", "", "ايلا ",
              "", "", "", "",
                "", "", "", "", "send", "("));


        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "send", "Acc", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "send", "Acc", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "send", "send", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "", "Rec", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "", "send", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "", "Rec", "("));

        LogHistoryList.add(new ChequeInfo("1454", "1265", "25515", "1213", "235353", "الخزنه للاستثمار",
                "", "", "", "", "ايلا ",
                "", "", "", "",
                "", "", "", "", "Rec", "("));

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


}
