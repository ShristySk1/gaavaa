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

import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.LoginResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        textEmail = findViewById(R.id.input_register_email);
        textPassword = findViewById(R.id.input_register_password);
        textMobileNumber = findViewById(R.id.input_register_mobileno);
        toolbar = findViewById(R.id.toolbar_layout);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        setToolbarType2("", false, false);
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
        if (mobileInput.isEmpty()) {
            textMobileNumber.setError("Field can't be empty");
            return false;
        } else {
            textMobileNumber.setError(null);
            return true;
        }
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
                showDialog();
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
        ApiService restloginapiinterface = ApiClient.getClient().create(ApiService.class);
        try {
            restloginapiinterface.loginUser(phone_no, password).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideDialog();
                    if (response.isSuccessful() && response != null) {
                        JsonObject jsonObject = response.body();
                        Log.d(TAG, "onResponse: " + jsonObject);
                        try {
                            JsonElement code = jsonObject.get("code");
                            String s = code.getAsString();
                            if (s.equals("200")) {
                                Toast.makeText(LoginActivity.this, jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                                Gson gson = new GsonBuilder().create();
                                LoginResponse loginResponse = gson.fromJson(gson.toJson(jsonObject), LoginResponse.class);
                                Toast.makeText(LoginActivity.this, "User is logged in", Toast.LENGTH_SHORT).show();
                                String token = loginResponse.getDetails().getToken();
                                //TODO RESPONSE
                                String username = loginResponse.getDetails().getUsername();
                                String email = loginResponse.getDetails().getEmail();
                                String phoneno = loginResponse.getDetails().getMobileNumber().toString();
                                saveUser(token, email, username, phoneno);
                                Log.d(TAG, "onResponse: " + token);
                                Intent intent = (new Intent(LoginActivity.this, MainActivity.class));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            } else {
                                //do process with data
                                Log.d(TAG, "onResponse: " + jsonObject.get("message"));
                                Log.d(TAG, "onResponse: " + jsonObject.get("status"));
                                Toast.makeText(LoginActivity.this, jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    hideDialog();
                    Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            hideDialog();
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void saveUser(String token, String email, String username, String phone) {
        PreferenceHandler.saveUser(token, email, phone, username, this);
    }
}