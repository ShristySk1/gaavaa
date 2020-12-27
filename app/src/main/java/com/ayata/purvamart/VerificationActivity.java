package com.ayata.purvamart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.Constants.Constants;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.VerificationResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "VerificationFragment";
    private String phoneNo;
    DialogFragment dialogFragment;

    //view
    Button btn_next;
    OtpView otpEditText;
    CardView progressBar;
    TextView phone_no;
    //toolbar
    RelativeLayout toolbarType1, toolbarType2, toolbarType3;
    View toolbar;
    //token
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        btn_next = findViewById(R.id.btn_next);
        otpEditText = findViewById(R.id.otp_view);
        progressBar = findViewById(R.id.progressBar);
        phone_no = findViewById(R.id.tv_phone_no);
        btn_next.setOnClickListener(this);
        otpEditText.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar_layout);
        toolbarType1 = toolbar.findViewById(R.id.appbar1);
        toolbarType2 = toolbar.findViewById(R.id.appbar2);
        toolbarType3 = toolbar.findViewById(R.id.appbar3);
        setToolbarType2("", false, false);
        //getIntent
        getIntentArguments();
        otpEditText.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                // do Stuff
                showDialog();
                verifyOtp(otp);
                closeKeyboard();
                Log.d("onOtpCompleted=>", otp);
            }
        });
    }

    private void getIntentArguments() {
        Intent intent = getIntent();
        token = "bearer" + intent.getStringExtra(Constants.AUTH_TOKEN);
    }

    private void verifyOtp(String otp) {
        ApiService restloginapiinterface = ApiClient.getClient().create(ApiService.class);
        try {

            restloginapiinterface.verifyOtp(token, otp).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideDialog();
                    if (response.isSuccessful() && response != null) {
                        JsonObject jsonObject = response.body();
                        Log.d(TAG, "onResponse: " + jsonObject);
                        JsonElement code = jsonObject.get("code");
                        if (code.getAsString().equals("200")) {
                            //do process with data
                            Log.d(TAG, "onResponse: success");
                            Gson gson = new GsonBuilder().create();
                            VerificationResponse verificationResponse = gson.fromJson(gson.toJson(jsonObject), VerificationResponse.class);
                            Toast.makeText(VerificationActivity.this, "Otp successfully verified", Toast.LENGTH_SHORT).show();
                            saveUser(token);
                            Log.d(TAG, "onResponse: " + verificationResponse.getDetails());
                            Intent intent = (new Intent(VerificationActivity.this, MainActivity.class));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            return;
                        } else {
                            Log.d(TAG, "onResponse: " + jsonObject.get("message"));
                            Log.d(TAG, "onResponse: " + jsonObject.get("code"));
                            Toast.makeText(VerificationActivity.this, jsonObject.get("details").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(VerificationActivity.this, "Otp verfication failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    hideDialog();
                    Toast.makeText(VerificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //close keyboard after pin view complete
    private void closeKeyboard() {
        View view = VerificationActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) VerificationActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //back button
            case R.id.imageButton:
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.btn_next://no action

        }
    }

    void showDialog() {
        dialogFragment = new MyDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DIALOG_MESSAGE, "Verifying OTP...");
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

    private void saveUser(String token) {
        PreferenceHandler.saveUser(token, this);
    }
}