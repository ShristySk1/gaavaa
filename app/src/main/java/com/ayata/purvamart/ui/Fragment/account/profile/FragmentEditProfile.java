package com.ayata.purvamart.ui.Fragment.account.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProfileDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEditProfile extends Fragment {


    public static final String TAG = "FragmentEditProfile";
    Button save;
    TextInputLayout textEmail, textFirstname, textMobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("Edit Profile", false, false);
        initView(view);
        getDataFromPreference();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //profile-edit
                updateProfile();

            }

            private void updateProfile() {
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                boolean test = false;
                String email = textEmail.getEditText().getText().toString();
                String phone = textMobileNumber.getEditText().getText().toString();
                String username = textFirstname.getEditText().getText().toString();

                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                ProfileDetail profileDetail = new ProfileDetail();
                profileDetail.setEmail(email);
                profileDetail.setContactNo1(phone);
                profileDetail.setFirstName(username);
                String token = PreferenceHandler.getToken(getContext());
                apiService.updateProfile(profileDetail).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject jsonObject = response.body();
                            if (jsonObject.get("code").toString().equals("200")) {
                                saveUser(token, profileDetail.getEmail(), profileDetail.getFirstName(), profileDetail.getContactNo1());
                                if (isAdded())
                                    Toast.makeText(getContext(), "" + "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStackImmediate();
                            } else {
                                if (isAdded())
                                    Toast.makeText(getContext(), "" + jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
//                                Toast.makeText(getContext(), "" + "Please login to continue", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void saveUser(String token, String email, String username, String phone) {
        PreferenceHandler.updateUser(token, email, phone, username, getContext());
    }

    private void getDataFromPreference() {
        String email = PreferenceHandler.getEmail(getContext());
        String username = PreferenceHandler.getUsername(getContext());
        String phone = PreferenceHandler.getPhone(getContext());
        textEmail.getEditText().setText(email);
        textFirstname.getEditText().setText(username);
        textMobileNumber.getEditText().setText(phone);
    }

    private void initView(View view) {
        save = view.findViewById(R.id.btn_save);
        textEmail = view.findViewById(R.id.input_register_email);
        textFirstname = view.findViewById(R.id.input_register_username);
        textMobileNumber = view.findViewById(R.id.input_register_phone);
    }
}