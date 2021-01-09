package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.R;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp, btnSignUpFacebook, btnSignUpGoogle;
    TextView text_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSignUp = findViewById(R.id.btn_signUp);
        btnSignUpFacebook = findViewById(R.id.btn_signUp_facebook);
        btnSignUpGoogle = findViewById(R.id.btn_signUp_google);
        text_login = findViewById(R.id.text_login);
        btnSignUp.setOnClickListener(this);
        btnSignUpFacebook.setOnClickListener(this);
        btnSignUpGoogle.setOnClickListener(this);
        text_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_signUp:
                Intent intent = new Intent(SignupActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_signUp_facebook:
                Toast.makeText(this, "Facebook SignUp Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_signUp_google:
                Toast.makeText(this, "Google SignUp Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_login:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
        }
    }
}