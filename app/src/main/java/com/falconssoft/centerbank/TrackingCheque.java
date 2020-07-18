package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.falconssoft.centerbank.databinding.ActivityTrackingChequeBinding;
import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.ArrayList;
import java.util.List;

public class TrackingCheque extends AppCompatActivity {

    private ActivityTrackingChequeBinding binding;
    private TrackingAdapter adapter;
    private List<ChequeInfoVM> list = new ArrayList<>();
    private DatabaseHandler handler;
    ChequeInfoVM chequeInfoVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tracking_cheque);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracking_cheque);
//        handler = new DatabaseHandler(this);
//        new Presenter(this).trackingCheque(this, handler.getActiveUserInfo().getUsername(), binding);

        /// get ntent



// To retrieve object in second Activity
         chequeInfoVM = (ChequeInfoVM) getIntent().getSerializableExtra("Tracking");
         getChequeData(chequeInfoVM);
        Log.e("getChequeData",""+chequeInfoVM.getAccCode());


    }

    public void getChequeData(ChequeInfoVM chequeInfoVM){
        new Presenter(this).trackingCheque(this, chequeInfoVM, binding);
    }

    public void fillAdapter(List<ChequeInfoVM> list, ActivityTrackingChequeBinding binding) {
        binding.trackingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrackingAdapter(this, list);
        binding.trackingRecyclerView.setAdapter(adapter);
    }

}