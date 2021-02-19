package com.ayata.purvamart.ui.Fragment.shop.product;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayata.purvamart.MainActivity;
import com.ayata.purvamart.R;
import com.ayata.purvamart.data.node.DragTransformableNode;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformationSystem;

import androidx.fragment.app.Fragment;

public class ThreeDFragment extends Fragment {
    public static final String TAG = "ThreeDFragment";
    //scene
    private SceneView mSceneView;
    String localModel = "PaperBag.sfb";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three_d, container, false);
        mSceneView = view.findViewById(R.id.sceneView);
        //toolbar
        ((MainActivity) getActivity()).showToolbar();
        ((MainActivity) getActivity()).setToolbarType2("", false, false);
        Log.d(TAG, "onCreateView: ");
        createScene();
        return view;
    }

    private void createScene() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
            // Maxscale must be greater than minscale

            if (dragTransformableNode != null) {
                dragTransformableNode.getScaleController().setMaxScale(0.25f);
                dragTransformableNode.getScaleController().setMinScale(0.10f);
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