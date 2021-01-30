package com.ayata.purvamart.ui.login;

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

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.generic.NetworkResponseListener;
import com.ayata.purvamart.data.network.response.BaseResponse;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.data.repository.Repository;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "VerificationFragment";
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
    String tokenwithBearer, tokenWithoutBearer;
    String username;
    String email, phoenno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        new ApiClient(new WeakReference<>(getApplicationContext()));
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
        tokenwithBearer = "bearer" + intent.getStringExtra(Constants.AUTH_TOKEN);
        tokenWithoutBearer = intent.getStringExtra(Constants.AUTH_TOKEN);
        username = intent.getStringExtra(Constants.USER_NAME);
        email = intent.getStringExtra(Constants.USER_EMAIL);
        phoenno = intent.getStringExtra(Constants.USER_PHONE_NUMBER);
        phone_no.setText("We sent it to the number +977 " + phoenno);
        PreferenceHandler.saveTokenTemp(this, tokenwithBearer);
    }

    private void verifyOtp(String otp) {
        new Repository(new NetworkResponseListener<BaseResponse<String>>() {
            @Override
            public void onResponseReceived(BaseResponse<String> verificationResponse) {
                hideDialog();
                //do process with data
                if (verificationResponse.getCode().toString().equals("200")) {
                    saveUser(tokenWithoutBearer, email, username, phoenno);
                    Log.d(TAG, "onResponse: " + verificationResponse.getDetails());
                    Intent intent = (new Intent(VerificationActivity.this, MainActivity.class));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(VerificationActivity.this, "Otp successfully verified", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return;
                } else {
                    Toast.makeText(VerificationActivity.this, verificationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoading() {
            }

            @Override
            public void onError(String message) {
                hideDialog();
                Toast.makeText(VerificationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, ApiClient.getApiService()).requestVerification(otp);
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

    private void saveUser(String token, String email, String username, String phone) {
        PreferenceHandler.saveUser(token, email, phone, username, this);
    }
}