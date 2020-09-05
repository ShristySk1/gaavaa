package com.ayata.purvamart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;

public class FragmentMyOrder extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout option1,option2,option3;
    private TextView textView1,textView2,textView3;
    private View line1,line2,line3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_order, container, false);

        //toolbar
        ((MainActivity)getActivity()).showToolbar();
        ((MainActivity)getActivity()).setToolbarType3("My Order");
        //bottom nav bar
        ((MainActivity)getActivity()).showBottomNavBar(true);

        initView();

        if(view.findViewById(R.id.fragment_order)!=null){

            if(savedInstanceState!=null){
                return null;
            }

            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.fragment_order, new FragmentEmptyOrder())
                    .commit();

        }

        return view;
    }

    private void initView(){
        option1=view.findViewById(R.id.layout_option1);
        option2=view.findViewById(R.id.layout_option2);
        option3=view.findViewById(R.id.layout_option3);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);

        textView1=view.findViewById(R.id.text1);
        textView2=view.findViewById(R.id.text2);
        textView3=view.findViewById(R.id.text3);
        line1= view.findViewById(R.id.line1);
        line2=view.findViewById(R.id.line2);
        line3= view.findViewById(R.id.line3);

        selectOption1();

    }

    @Override
    public void onClick(View view) {

        Fragment fragment= null;

        switch (view.getId()){
            case R.id.layout_option1:
                selectOption1();
                fragment= new FragmentEmptyOrder();
                break;

            case R.id.layout_option2:
                selectOption2();
                fragment= new FragmentEmptyOrder();
                break;

            case R.id.layout_option3:
                selectOption3();
                fragment= new FragmentEmptyOrder();
                break;
        }

        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.fragment_order, fragment)
                .addToBackStack(null).commit();
    }

    private void selectOption1(){

        textView1.setTextColor(getResources().getColor(R.color.colorGreen));
        textView2.setTextColor(getResources().getColor(R.color.colorGray));
        textView3.setTextColor(getResources().getColor(R.color.colorGray));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void selectOption2(){

        textView1.setTextColor(getResources().getColor(R.color.colorGray));
        textView2.setTextColor(getResources().getColor(R.color.colorGreen));
        textView3.setTextColor(getResources().getColor(R.color.colorGray));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void selectOption3(){

        textView1.setTextColor(getResources().getColor(R.color.colorGray));
        textView2.setTextColor(getResources().getColor(R.color.colorGray));
        textView3.setTextColor(getResources().getColor(R.color.colorGreen));

        line1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        line3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
    }
}