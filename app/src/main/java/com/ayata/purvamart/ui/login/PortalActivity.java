package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;

import androidx.appcompat.app.AppCompatActivity;

public class PortalActivity extends AppCompatActivity {
    Button btnLogin, btn_LoginGuest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        btnLogin = findViewById(R.id.btn_login);
        btn_LoginGuest = findViewById(R.id.btn_login_guest);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortalActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_LoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}