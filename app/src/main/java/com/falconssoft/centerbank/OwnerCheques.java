package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.falconssoft.centerbank.databinding.ActivityOwnerChequesBinding;
import com.falconssoft.centerbank.databinding.ActivityTrackingChequeBinding;
import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.ArrayList;
import java.util.List;

public class OwnerCheques extends AppCompatActivity {

    private ActivityOwnerChequesBinding binding;
    private OwnerChequesAdapter adapter;
    private List<ChequeInfoVM> list = new ArrayList<>();
    private DatabaseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tracking_cheque);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_cheques);
        handler = new DatabaseHandler(this);
        new Presenter(this).ownerCheque(this, handler.getActiveUserInfo().getUsername(), binding);

    }

    public void fillAdapter(List<ChequeInfoVM> list, ActivityOwnerChequesBinding binding) {
        binding.ownerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OwnerChequesAdapter(this, list);
        binding.ownerRecyclerView.setAdapter(adapter);
    }

}