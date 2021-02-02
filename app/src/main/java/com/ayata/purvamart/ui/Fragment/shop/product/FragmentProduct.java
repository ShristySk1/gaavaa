package com.ayata.purvamart.ui.Fragment.shop.product;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.network.ApiClient;
import com.ayata.purvamart.data.network.ApiService;
import com.ayata.purvamart.data.network.response.ProductDetail;
import com.ayata.purvamart.data.preference.PreferenceHandler;
import com.ayata.purvamart.node.DragTransformableNode;
import com.ayata.purvamart.ui.login.SignupActivity;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformationSystem;
import com.google.gson.JsonObject;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is single product page
 */
public class FragmentProduct extends Fragment implements View.OnClickListener {
    public static String TAG = "FragmentProduct";
    public static final String MODEL_ITEM = "param1";
    private ProductDetail modelItem;
    //viewpager
    ViewPager2 mViewPager;
    PageIndicatorView pageIndicatorView;
    // images array
//    int[] images = {R.drawable.signup, R.drawable.signup, R.drawable.signup};
    List<String> images;
    // Creating Object of ViewPagerAdapter
    ViewPagerAdapterProduct mViewPagerAdapter;
    //scene
    private SceneView mSceneView;
    String localModel = "ProductTextured.sfb";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelItem = (ProductDetail) getArguments().getSerializable(MODEL_ITEM);
        }
    }

    Button btnAddToCart;
    private static int quantity = 0;
    TextView textQuantity, textProductTitle, textProductNewPrice, textProductOldPrice, textWeight, textDiscount, textProductDescription, text_product_from;
    ImageButton btn_add, btn_minus;
    ImageView image_product;

    //like
    ImageView thumb_image;
    TextView thumb_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("", true, false);

        //bottom nav
        ((MainActivity) getActivity()).showBottomNavBar(false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
        text_product_from = view.findViewById(R.id.text_product_from);
//        image_product = view.findViewById(R.id.image_product);
        mSceneView = view.findViewById(R.id.sceneView);
        thumb_image = view.findViewById(R.id.thumb_image);
        thumb_text = view.findViewById(R.id.thumb_text);
        textProductNewPrice = view.findViewById(R.id.text_product_newPrice);
        textProductDescription = view.findViewById(R.id.text_product_description);
        textProductTitle = view.findViewById(R.id.text_product_name);
        textWeight = view.findViewById(R.id.text_product_weight);
        btnAddToCart.setOnClickListener(this);
        //setData-----
        //viewpager image
        images = new ArrayList<>();
        createScene();
//        mViewPager = view.findViewById(R.id.viewPager2);
//        pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
//        // Initializing the ViewPagerAdapter
//        mViewPagerAdapter = new ViewPagerAdapterProduct(images);
//        // Adding the Adapter to the ViewPager
//        mViewPager.setAdapter(mViewPagerAdapter);
//        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                pageIndicatorView.setSelection(position);
//            }
//        });
//        images.addAll(modelItem.getProductImage());
//        pageIndicatorView.setCount(images.size());
//        mViewPagerAdapter.notifyDataSetChanged();
//        Log.d(TAG, "initView: " + images.size());
        //rest of data
        textProductTitle.setText(modelItem.getName());
        textProductNewPrice.setText(modelItem.getProductPrice().toString());
        textWeight.setText(modelItem.getUnit());
        textProductDescription.setText(modelItem.getDescription());
        thumb_text.setText(modelItem.getProductLikes().toString());
        text_product_from.setText(modelItem.getFrom());
        Log.d(TAG, "initView: " + modelItem.getFrom());
        thumb_image.setOnClickListener(this);
    }

    private void handleDiscount() {
        if (modelItem.getDiscounted()) {
            showDiscount();
        } else {
            hideDiscount();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                nextFragment();
                break;
            case R.id.thumb_image:
                break;
        }
    }

    /**
     * go to cart fragment
     */
    private void nextFragment() {
        //check if user is logged in then only we hit api otherwise return
        if (!PreferenceHandler.isUserAlreadyLoggedIn(getContext())) {
            Toast.makeText(getContext(), "Please Login to continue", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), SignupActivity.class));
            return;
        }
        //api
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.addToCart(modelItem.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().get("message"));
                    if (response.body().get("code").getAsString().equals("200")) {

                        Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getContext(), response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    private void showDiscount() {
        textProductOldPrice.setVisibility(View.VISIBLE);
        textDiscount.setVisibility(View.VISIBLE);
        textDiscount.setText(modelItem.getProductDiscount());
        textProductOldPrice.setText(modelItem.getOldPrice().toString());
        textProductOldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void hideDiscount() {
        textProductOldPrice.setVisibility(View.GONE);
        textDiscount.setVisibility(View.GONE);
    }

    private void createScene() {
        ModelRenderable.builder()
                .setSource(getContext(), Uri.parse(localModel))
                .setRegistryId(localModel)
                .build()
                .thenAccept(modelRenderable -> onRenderableLoaded(modelRenderable))
                .exceptionally(throwable -> {
                    Toast toast =
                            Toast.makeText(getContext(), "Unable to load model", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });
    }

    void onRenderableLoaded(ModelRenderable model) {


        if (mSceneView != null) {
//            Node modelNode = new Node();
//            modelNode.setRenderable(model);
//            modelNode.setParent(mSceneView.getScene());
//            modelNode.setLocalPosition(new Vector3(0, -0.1f, -1));
//            modelNode.setLocalScale(new Vector3(2,2,2));
//            mSceneView.getScene().addChild(modelNode);

            TransformationSystem transformationSystem = makeTransformationSystem();
            DragTransformableNode dragTransformableNode = new DragTransformableNode(1f, transformationSystem);
            dragTransformableNode.setLocalScale(new Vector3(50f, 50f, 50f));

            if (dragTransformableNode != null) {
                dragTransformableNode.setRenderable(model);
                mSceneView.getScene().addChild(dragTransformableNode);

                //   mSceneView.getScene().aa
                dragTransformableNode.select();
                mSceneView.getScene().addOnPeekTouchListener(new Scene.OnPeekTouchListener() {
                    @Override
                    public void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                        Log.d("touch", motionEvent.toString());

                        try {
                            transformationSystem.onTouch(hitTestResult, motionEvent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
//                mSceneView.getScene().setOnTouchListener(new Scene.OnTouchListener() {
//                    @Override
//                    public boolean onSceneTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
//                      //  transformationSystem.onTouch(hitTestResult,motionEvent);
//                        return false;
//                    }});h


            }


        }


    }

    private TransformationSystem makeTransformationSystem() {

        FootprintSelectionVisualizer footprintSelectionVisualizer = new FootprintSelectionVisualizer();
        return new TransformationSystem(getResources().getDisplayMetrics(), footprintSelectionVisualizer);
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            mSceneView.resume();
        } catch (CameraNotAvailableException e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSceneView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mSceneView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}