package com.ayata.purvamart.ui.Fragment.shop.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayata.purvamart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity implements AdapterNotification.OnNotificationClickListener {
    RecyclerView rvNotification;
    List<ModelNotification> listitem;
    AdapterNotification adapterNotification;
    //back buuton
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rvNotification = findViewById(R.id.rvNotification);
        ivBack = findViewById(R.id.back);
        setUpRecyclerView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpRecyclerView() {
        listitem = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapterNotification = new AdapterNotification(this, listitem, this);
        rvNotification.setLayoutManager(layoutManager);
        rvNotification.setAdapter(adapterNotification);
        dataPrepare();
    }

    private void dataPrepare() {
        listitem.add(new ModelNotification(R.drawable.button_green, "Gaavaa",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        listitem.add(new ModelNotification(R.drawable.button_green, "Gaavaa",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        listitem.add(new ModelNotification(R.drawable.button_green, "Gaavaa",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        listitem.add(new ModelNotification(R.drawable.button_green, "Gaavaa",
                "This is the description of each notification.This is the description of each notification.",
                "2020-08-29"));
        adapterNotification.notifyDataSetChanged();
    }

    @Override
    public void onNotificationClick(int position, ModelNotification modelNotification) {
        Toast.makeText(this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}