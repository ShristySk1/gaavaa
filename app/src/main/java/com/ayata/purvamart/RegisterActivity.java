package com.ayata.purvamart;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity{
    private static final int COUNT_MIN = 6;
    private static final String TAG = "registeractivity";
    Button btn_create_account;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    View toolbar;
    TextInputLayout textEmail, textPassword, textConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textEmail = findViewById(R.id.input_register_email);
        textPassword = findViewById(R.id.input_register_password);
        textConfirmPassword = findViewById(R.id.input_register_confirmPassword);
        toolbar = findViewById(R.id.toolbar_layout);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        btn_create_account = findViewById(R.id.btn_create_account);
        setToolbarType2("", false);
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!validateEmail() | !validatePassword()) {
//                    return;
//                }
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setToolbarType2(String title, Boolean shareIcon) {

        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.VISIBLE);
        toolbarType3.setVisibility(View.GONE);

        TextView text;
        ImageButton back;
        ImageView share;

        text = toolbar.findViewById(R.id.text_header);
        back = toolbar.findViewById(R.id.back);
        share = toolbar.findViewById(R.id.share);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (!shareIcon) {
            share.setVisibility(View.GONE);
        }

        text.setText(title);

    }
    //run time error checker
    public  boolean validateEmail() {
        String emailInput = textEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textEmail.setError(null);
            return true;
        }
    }


    public  boolean validatePassword() {
        String passwordInput = textPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textPassword.setError("Field can't be empty");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }
    public  boolean validateConfirmPassword() {
        String passwordInput = textConfirmPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textConfirmPassword.setError("Field can't be empty");
            return false;
        } else {
            textConfirmPassword.setError(null);
            return true;
        }
    }
}