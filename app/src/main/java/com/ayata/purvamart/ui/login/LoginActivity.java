package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.network.response.LoginDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "loginactivity";
    Button btn_google, btn_facebook, btn_login;
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    View toolbar;
    TextInputLayout textEmail, textPassword, textMobileNumber;
    DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new ApiClient(new WeakReference<>(getApplicationContext()));
        initView();
        setToolbarType2("", false, false);
    }

    void initView() {
        textEmail = findViewById(R.id.input_register_email);
        textPassword = findViewById(R.id.input_register_password);
        textMobileNumber = findViewById(R.id.input_register_mobileno);
        toolbar = findViewById(R.id.toolbar_layout);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        btn_google = findViewById(R.id.btn_create_google);
        btn_facebook = findViewById(R.id.btn_create_facebook);
        btn_login = findViewById(R.id.btn_create_account);
        btn_google.setOnClickListener(this);
        btn_facebook.setOnClickListener(this);
        btn_login.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_account:
                if (!validatePassword() | !validateMobileNumber()) {
                    return;
                }
                String password = textPassword.getEditText().getText().toString().trim();
                String phone = textMobileNumber.getEditText().getText().toString().trim();
                loginUser(phone, password);

                break;

            case R.id.btn_create_facebook:
                Toast.makeText(this, "Facebook Register Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_create_google:
                Toast.makeText(this, "Google Register Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loginUser(String phone_no, String password) {
        Log.d(TAG, "registerUser: " + phone_no);
        new Repository(new NetworkResponseListener<BaseResponse<LoginDetail>>() {
            @Override
            public void onResponseReceived(BaseResponse<LoginDetail> detailBaseResponse) {
                hideDialog();
                LoginDetail loginDetail = detailBaseResponse.getDetails();
                if (loginDetail != null) {
                    String token = loginDetail.getToken();
                    //TODO RESPONSE
                    String username = loginDetail.getUsername();
                    String email = loginDetail.getEmail();
                    String phoneno = loginDetail.getMobileNumber().toString();
                    saveUser(token, email, username, phoneno);
                    Log.d(TAG, "onResponse: " + token);
                    Intent intent = (new Intent(LoginActivity.this, MainActivity.class));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(LoginActivity.this, "User is logged in", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return;
                } else {
                    Toast.makeText(LoginActivity.this, detailBaseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoading() {
                showDialog();

            }


            @Override
            public void onError(String message) {
                hideDialog();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, ApiClient.getApiService()).requestLogin(phone_no, password);
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

    private void saveUser(String token, String email, String username, String phone) {
        PreferenceHandler.saveUser(token, email, phone, username, this);
    }
}