package com.ayata.purvamart.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Constants.Constants;

import java.lang.ref.WeakReference;

public class MyError {
    ProgressBar progress_error;
    TextView text_error;
    ImageView noWifi, connectionTimeout;

    public void inflateLayout(View root, WeakReference<Context> mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext.get());
        //Avoid pass null in the root it ignores spaces in the child pullRefreshLayout
        View inflatedLayout = inflater.inflate(R.layout.error_layout, (ViewGroup) root, false);
//         int id=view.findViewById(android.R.id.content).getRootView().getId();
        ViewGroup viewGroup = root.findViewById(R.id.root_main);
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) view.findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.addView(inflatedLayout);
        text_error = root.findViewById(R.id.text_error);
        progress_error = root.findViewById(R.id.progress_error);
        noWifi = root.findViewById(R.id.ivNoWifi);
        connectionTimeout = root.findViewById(R.id.ivTimeout);

    }

    public void showProgress() {
        progress_error.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progress_error.setVisibility(View.GONE);
    }

    public void showError(String message) {
        progress_error.setVisibility(View.GONE);
        text_error.setText(message);
        if (message.equals(Constants.NO_INTERNET_CONNECTION)) {
            noWifi.setVisibility(View.VISIBLE);
            connectionTimeout.setVisibility(View.GONE);
        } else {
            noWifi.setVisibility(View.GONE);
            connectionTimeout.setVisibility(View.VISIBLE);
        }
    }
}
