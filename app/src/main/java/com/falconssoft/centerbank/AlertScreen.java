package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.notification;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class AlertScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList <notification> notificationArrayList;
    LinearLayoutManager layoutManager;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_main_screen);
        recyclerView = findViewById(R.id.recycler);
        notificationArrayList=new ArrayList<>();
        fillListNotification();
        fillListNotification();
        fillListNotification();
        layoutManager = new LinearLayoutManager(AlertScreen.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView,0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
        recyclerView.setAdapter(notificationAdapter);
    }

    private void fillListNotification() {

        notification one=new notification();
        one.setAmount_check("1500 JD");
        one.setDate("21-6-2020");
        one.setSource("Companey Source");
        notificationArrayList.add(one);
        notificationArrayList.add(one);
        notificationArrayList.add(one);


    }
    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
        {
            controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_filldown);
            NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }
}
