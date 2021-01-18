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
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.RegisterDetail;
import com.ayata.purvamart.data.network.response.RegisterDetailError;
import com.ayata.purvamart.data.network.response.RegisterResponse;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                RegisterDetail details = new RegisterDetail();
                details.setEmail(textEmail.getEditText().getText().toString().trim());
                details.setPassword(textPassword.getEditText().getText().toString().trim());
                details.setConfirmPassword(textConfirmPassword.getEditText().getText().toString().trim());
                details.setMobileNumber(textMobileNumber.getEditText().getText().toString().trim());


//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
                showDialog();
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

    private void registerUser(RegisterDetail details) {
        Log.d(TAG, "registerUser: " + details.getEmail());
        ApiService restloginapiinterface = ApiClient.getClient().create(ApiService.class);
        try {
            restloginapiinterface.registerUser(details).enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        RegisterResponse defaultResponse = response.body();
                        Gson gson = new GsonBuilder().create();
                        if (defaultResponse.getCode() == 200) {
                            TypeToken<List<RegisterDetail>> responseTypeToken = new TypeToken<List<RegisterDetail>>() {
                            };
                            List<RegisterDetail> detail = gson.fromJson(gson.toJson(defaultResponse.getDetails()), responseTypeToken.getType());
                            // passing Bearer token
                            Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                            String token = detail.get(0).getToken();
                            String otp = detail.get(0).getOtpCode();
                            String username = detail.get(0).getUsername();
                            String email = detail.get(0).getEmail();
                            String number = detail.get(0).getMobileNumber();
                            intent.putExtra(Constants.AUTH_TOKEN, token);
                            intent.putExtra(Constants.OTP, otp);
                            intent.putExtra(Constants.USER_NAME, username);
                            intent.putExtra(Constants.USER_EMAIL, email);
                            intent.putExtra(Constants.USER_PHONE_NUMBER, number);
                            Log.d(TAG, "onResponse: " + token + " " + otp + "phone: " + number);
                            startActivity(intent);
                        } else {
                            //If for everyOther Status the response is Object of ResponseError which contains msg.
                            RegisterDetailError responseError = gson.fromJson(gson.toJson(defaultResponse.getDetails()), RegisterDetailError.class);
                            Toast.makeText(RegisterActivity.this, responseError.toString() + "", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, response.message() + "", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    hideDialog();
                    Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            hideDialog();
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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