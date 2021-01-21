package com.ayata.purvamart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.utils.MyDialogFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "SignupActivity";
    Button btnSignUp, btnSignUpFacebook, btnSignUpGoogle;
    TextView text_login;
    //third-party
    private static final int GOOGLE_REQ_CODE_FIREBASE = 200;
    public static final String CLIENT_ID = "258477432568-fk19m79hnqcjmbk4lj0nodh5l2jchuqe.apps.googleusercontent.com";
    GoogleSignInClient googleSignInClient;
    //firebase
    private FirebaseAuth mAuth;
    DialogFragment dialogFragment;

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
        mAuth = FirebaseAuth.getInstance();
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
                createRequest();
                signInFireBase();
                break;
            case R.id.text_login:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
        }
    }

    void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInFireBase() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_REQ_CODE_FIREBASE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
            // ...
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        showDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideDialog();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("successlogin", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIFromFrirebase(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("faillogin", "signInWithCredential:failure", task.getException());
//                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUIFromFrirebase(null);
                        }
                    }
                });
    }

    private void updateUIFromFrirebase(FirebaseUser user) {
        Toast.makeText(this, "Email: " + user.getEmail().toString(), Toast.LENGTH_SHORT).show();
    }

    void showDialog() {
        dialogFragment = new MyDialogFragment();
        dialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DIALOG_MESSAGE, "Signing in...");
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