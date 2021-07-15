package com.ayata.purvamart.ui.Fragment.account;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAccount;
import com.ayata.purvamart.data.network.interceptor.NetworkConnectionInterceptor;
import com.ayata.purvamart.data.permission.PermissionManager;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.ui.Adapter.AdapterAccount;
import com.ayata.purvamart.ui.Fragment.account.address.FragmentDeliveryAddress;
import com.ayata.purvamart.ui.Fragment.account.help.FragmentHelp;
import com.ayata.purvamart.ui.Fragment.account.privacypolicy.FragmentPrivacyPolicy;
import com.ayata.purvamart.ui.Fragment.account.profile.FragmentEditProfile;
import com.ayata.purvamart.ui.Fragment.account.promos.FragmentPromos;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.android.material.transition.platform.MaterialFadeThrough;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class FragmentAccount extends Fragment implements View.OnClickListener, AdapterAccount.OnLayoutClickListener {
    public static String TAG = "FragmentAccount";
    private View view;
    private RecyclerView recyclerView;
    private AdapterAccount adapterAccount;
    private List<ModelAccount> listitem;
    private LinearLayoutManager linearLayoutManager;

    private Button btn_logout;
    private ImageView imageBtn_edit;
    //main user
    private TextView acc_email, acc_name;
    //placeholder name
    TextView profile_name_placeholder;

    //bitmap
    private static final int REQUEST_MEDIA_READ_PERMISSION = 200;
    private static final int IMAGE_REQUEST = 100;
    Bitmap bitmap = null;
    ImageView db_photo;
    RelativeLayout layout_photo;

    //image permission
    private PermissionManager permissionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the pullRefreshLayout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        setEnterTransition(new MaterialFadeThrough());
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType3("Account");
        //bottom nav bar
        ((MainActivity) getActivity()).showBottomNavBar(true);
        acc_email = view.findViewById(R.id.acc_email);
        acc_name = view.findViewById(R.id.acc_name);
        imageBtn_edit = view.findViewById(R.id.acc_btn_edit);
        imageBtn_edit.setOnClickListener(this);
        btn_logout = view.findViewById(R.id.acc_btn_logout);
        btn_logout.setOnClickListener(this);
        initView();
        setDefaultProfileImage();
        newImageListener();
        return view;
    }

    private void initView() {
        profile_name_placeholder = view.findViewById(R.id.profile_name_placeholder);
        recyclerView = view.findViewById(R.id.acc_recycler);
        acc_email.setText(PreferenceHandler.getEmail(getContext()));
        String name = PreferenceHandler.getUsername(getContext());
        acc_name.setText(name);
        profile_name_placeholder.setText(getFirstLetterFromEachWordInSentence(name));
        db_photo = view.findViewById(R.id.ivProfileImage);
        layout_photo = view.findViewById(R.id.layout_pic);
        permissionManager = new PermissionManager(getContext());
        listitem = new ArrayList<>();
        prepareData();
        adapterAccount = new AdapterAccount(getContext(), listitem, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(adapterAccount);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void prepareData() {
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text11),
                getResources().getString(R.string.acc_rv_text12)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text21),
                getResources().getString(R.string.acc_rv_text22)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text31),
                getResources().getString(R.string.acc_rv_text32)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text41),
                getResources().getString(R.string.acc_rv_text42)));
        listitem.add(new ModelAccount(getResources().getString(R.string.acc_rv_text51),
                getResources().getString(R.string.acc_rv_text52)));
    }

    /**
     * Gets the first character of every word in the sentence.
     *
     * @param fullname
     * @return
     */
    public static String getFirstLetterFromEachWordInSentence(final String fullname) {
        if (fullname.equals("")) {
            return "";
        }
        StringBuilder initials = new StringBuilder();
        for (String s : fullname.split(" ")) {
            initials.append(s.charAt(0));
        }
        return initials.toString().trim();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.acc_btn_edit:
                //Intent
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(13, FragmentEditProfile.TAG, null, new FragmentEditProfile());
                break;

            case R.id.acc_btn_logout:
                if (!(new NetworkConnectionInterceptor(new WeakReference<>(getContext())).isConnected())) {
                    Toast.makeText(getContext(), "Please check your internet connection to continue", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "User logged out", Toast.LENGTH_SHORT).show();
                PreferenceHandler.logout(getContext());
                break;
        }

    }

    @Override
    public void onLayoutClick(int position) {
        switch (position) {
            case 0:
                //profile setting
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                ((MainActivity) getActivity()).changeFragment(13, FragmentEditProfile.TAG, null, new FragmentEditProfile());
                break;
            case 1:
                //promos
                Toast.makeText(getContext(), listitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).changeFragment(14, FragmentPromos.TAG, null, new FragmentPromos());
                break;
            case 2:
                //My Delivery Address
                //check token
                if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
                    Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), SignupActivity.class));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(FragmentAccount.TAG, "NOTCLICKABLE");
                ((MainActivity) getActivity()).changeFragment(18, FragmentDeliveryAddress.TAG, bundle, new FragmentDeliveryAddress());
                break;

            case 3:
                //Terms, Privacy & Policy
                ((MainActivity) getActivity()).changeFragment(14, FragmentPrivacyPolicy.TAG, null, new FragmentPrivacyPolicy());
                break;
            case 4:
                //Help & Support
                ((MainActivity) getActivity()).changeFragment(13, FragmentHelp.TAG, null, new FragmentHelp());
                break;
        }

    }

    private void setDefaultProfileImage() {
        bitmap = PreferenceHandler.getImage(getContext());
        if (bitmap != null) {
            profile_name_placeholder.setVisibility(View.GONE);
            db_photo.setImageBitmap(bitmap);
        }
    }

    private void newImageListener() {
        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionManager.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE, new PermissionManager.PermissionAskListener() {
                    @Override
                    public void onNeedPermission() {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_MEDIA_READ_PERMISSION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        new AlertDialog.Builder(getContext()).setTitle("Permission Denied").setMessage("Without this permission this app is unable to read photos. Are you sure you want to deny this permission.")
                                .setCancelable(false)
                                .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_MEDIA_READ_PERMISSION);
                                        dialog.dismiss();
                                    }
                                }).show();

                    }

                    @Override
                    public void onPermissionGranted() {
                        openPhotos();
                    }
                });

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case REQUEST_MEDIA_READ_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: ");
                    openPhotos();
                } else {
                    // Permission was denied.......
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                profile_name_placeholder.setVisibility(View.GONE);
                db_photo.setImageBitmap(bitmap);
                saveToPreference();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void openPhotos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void saveToPreference() {
        PreferenceHandler.saveImage(getContext(), bitmap);
    }
}