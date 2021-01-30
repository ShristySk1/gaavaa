package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.Model.ModelRegister;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.RegisterDetail;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "registeractivity";
    Button btn_create_account, btn_google, btn_facebook;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    View toolbar;
    TextInputLayout textEmail, textPassword, textConfirmPassword, textMobileNumber;
    DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new ApiClient(new WeakReference<>(getApplicationContext()));

        textEmail = findViewById(R.id.input_register_email);
        textPassword = findViewById(R.id.input_register_password);
        textConfirmPassword = findViewById(R.id.input_register_confirmPassword);

        textMobileNumber = findViewById(R.id.input_register_mobileno);
        toolbar = findViewById(R.id.toolbar_layout);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        btn_create_account = findViewById(R.id.btn_create_account);
        setToolbarType2("", false, false);
        btn_create_account.setOnClickListener(this);

        btn_google = findViewById(R.id.btn_create_google);
        btn_facebook = findViewById(R.id.btn_create_facebook);

        btn_google.setOnClickListener(this);
        btn_facebook.setOnClickListener(this);
    }

    public void setToolbarType2(String title, Boolean likeIcon, Boolean filterIcon) {

        toolbarType1.setVisibility(View.GONE);
        toolbarType2.setVisibility(View.VISIBLE);
        toolbarType3.setVisibility(View.GONE);

        TextView text;
        ImageButton back;
        ImageView like, filter;

        text = toolbar.findViewById(R.id.text_header);
        back = toolbar.findViewById(R.id.back);
        like = toolbar.findViewById(R.id.like);
        filter = toolbar.findViewById(R.id.filter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (!likeIcon) {
            like.setVisibility(View.GONE);
        } else {
            like.setVisibility(View.VISIBLE);
        }

        if (!filterIcon) {
            filter.setVisibility(View.GONE);
        } else {
            filter.setVisibility(View.VISIBLE);
        }

        text.setText(title);

    }

    //run time error checker
    public boolean validateEmail() {
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


    public boolean validatePassword() {
        String passwordInput = textPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textPassword.setError("Field can't be empty");
            return false;
        } else {
            textPassword.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPassword() {
        String passwordInput = textConfirmPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (!textConfirmPassword.getEditText().getText().toString().trim().equals(textPassword.getEditText().getText().toString().trim())) {
            textConfirmPassword.setError("Password do not match");
            return false;
        } else {
            textConfirmPassword.setError(null);
            return true;
        }
    }

    public boolean validateMobileNumber() {
        String mobileInput = textMobileNumber.getEditText().getText().toString().trim();
        Log.d(TAG, "validateMobileNumber: " + !android.util.Patterns.PHONE.matcher(mobileInput).matches());
        Log.d(TAG, "validateMobileNumber: " + isValidNumber(mobileInput));
        if (mobileInput.isEmpty()) {
            textMobileNumber.setError("Field can't be empty");
            return false;
        } else if (!isValidNumber(mobileInput)) {
            textMobileNumber.setError("Invalid Phone number");
            return false;
        } else {
            textMobileNumber.setError(null);
            return true;
        }
    }

    private boolean isValidNumber(String phonenumber) {
        String PHONE_PATTERN = "^(9|9)\\d{9}$";
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phonenumber);
        return matcher.matches();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_account:
                if (!validateEmail() | !validatePassword() | !validateMobileNumber() | !validateConfirmPassword()) {
                    return;
                }
                ModelRegister details = new ModelRegister();
                details.setEmail(textEmail.getEditText().getText().toString().trim());
                details.setPassword(textPassword.getEditText().getText().toString().trim());
                details.setConfirmPassword(textConfirmPassword.getEditText().getText().toString().trim());
                details.setMobileNumber(textMobileNumber.getEditText().getText().toString().trim());
                registerUser(details);

                break;

            case R.id.btn_create_facebook:
                Toast.makeText(this, "Facebook Register Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_create_google:
                Toast.makeText(this, "Google Register Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void registerUser(ModelRegister details) {
        showDialog();
        Log.d(TAG, "registerUser: " + details.getEmail());
        new Repository(new NetworkResponseListener<BaseResponse<RegisterDetail>>() {
            @Override
            public void onResponseReceived(BaseResponse<RegisterDetail> response) {
                hideDialog();
                if (response.getCode().toString().equals("200")) {
                    RegisterDetail detail = response.getDetails();
                    // passing Bearer token
                    Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                    String token = detail.getToken();
                    String otp = detail.getOtpCode();
                    String username = detail.getUsername();
                    String email = detail.getEmail().get(0);
                    String number = detail.getMobileNumber().get(0);
                    intent.putExtra(Constants.AUTH_TOKEN, token);
                    intent.putExtra(Constants.OTP, otp);
                    intent.putExtra(Constants.USER_NAME, username);
                    intent.putExtra(Constants.USER_EMAIL, email);
                    intent.putExtra(Constants.USER_PHONE_NUMBER, number);
                    Log.d(TAG, "onResponse: " + token + " " + otp + "phone: " + number);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, response.getDetails().toString() + "", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onError(String message) {
                hideDialog();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, ApiClient.getApiService()).requestRegister(details);

    }

    void showDialog() {
        dialogFragment = new MyDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DIALOG_MESSAGE, "Please wait...");
        dialogFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");
    }

    void hideDialog() {
        dialogFragment.dismiss();
    }
}