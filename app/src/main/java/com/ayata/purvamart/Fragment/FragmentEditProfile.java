package com.ayata.purvamart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.SignupActivity;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProfileDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditProfile extends Fragment {

    public static final String TAG = "FragmentEditProfile";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentEditProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditProfile newInstance(String param1, String param2) {
        FragmentEditProfile fragment = new FragmentEditProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextInputLayout textEmail, textPassword, textConfirmPassword, textMobileNumber, textUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button save;

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
                String username = textUsername.getEditText().getText().toString();
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                ProfileDetail profileDetail = new ProfileDetail();
                profileDetail.setAddressLine1("address");
                profileDetail.setContactNo1(phone);
                profileDetail.setFirstName(username);
                profileDetail.setLastName("lastsname");
                profileDetail.setShippingAddr(test);
                profileDetail.setGender("Male");
                String token = PreferenceHandler.getToken(getContext());
                apiService.updateProfile(token, profileDetail).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject jsonObject = response.body();
                            if (jsonObject.get("code").toString().equals("200")) {
                                saveUser(token, "test@gmail.com", "testusername", profileDetail.getContactNo1());
                                Toast.makeText(getContext(), "" + "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStackImmediate();
                            } else {
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
        textUsername.getEditText().setText(username);
        textMobileNumber.getEditText().setText(phone);
    }

    private void initView(View view) {
        save = view.findViewById(R.id.btn_save);
        textEmail = view.findViewById(R.id.input_register_email);
        textUsername = view.findViewById(R.id.input_register_username);
        textMobileNumber = view.findViewById(R.id.input_register_phone);
    }
}