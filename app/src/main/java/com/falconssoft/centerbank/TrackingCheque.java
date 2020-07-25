package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.falconssoft.centerbank.databinding.ActivityTrackingChequeBinding;
import com.falconssoft.centerbank.viewmodel.ChequeInfoVM;

import java.util.ArrayList;
import java.util.List;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class TrackingCheque extends AppCompatActivity {

    private ActivityTrackingChequeBinding binding;
    private TrackingAdapter adapter;
    private List<ChequeInfoVM> list = new ArrayList<>();
    private DatabaseHandler handler;
    private ChequeInfoVM chequeInfoVM;
    public String language = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tracking_cheque);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracking_cheque);

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");
//        handler = new DatabaseHandler(this);
//        new Presenter(this).trackingCheque(this, handler.getActiveUserInfo().getUsername(), binding);

        /// get ntent


// To retrieve object in second Activity
        chequeInfoVM = (ChequeInfoVM) getIntent().getSerializableExtra("Tracking");
        getChequeData(chequeInfoVM);
        binding.trackingAccountNo.setText(chequeInfoVM.getAccCode());
        binding.trackingChequeNo.setText(chequeInfoVM.getChequeNo());

        checkLanguage();
        Log.e("getChequeData", "" + chequeInfoVM.getChequeNo());


    }

    public void getChequeData(ChequeInfoVM chequeInfoVM) {
        new Presenter(this).trackingCheque(this, chequeInfoVM, binding);
    }

    public void fillAdapter(List<ChequeInfoVM> list, ActivityTrackingChequeBinding binding) {
        binding.trackingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrackingAdapter(this, list, language);
        binding.trackingRecyclerView.setAdapter(adapter);
    }

    void checkLanguage() {

        if (language.equals("ar")) {
            binding.trackingLinearAccountNo.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.trackingLinearChequeNo.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {

            binding.trackingLinearAccountNo.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            binding.trackingLinearChequeNo.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);        }

    }


}