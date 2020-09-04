package com.ayata.purvamart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {
Button btnSignUp,btnSignUpFacebook,btnSignUpGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSignUp=findViewById(R.id.btn_signUp);
        btnSignUpFacebook=findViewById(R.id.btn_signUp_facebook);
        btnSignUpGoogle=findViewById(R.id.btn_signUp_google);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}