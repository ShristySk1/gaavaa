package com.ayata.purvamart.ui.Fragment.shop.product;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.node.DragTransformableNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformationSystem;

import androidx.appcompat.app.AppCompatActivity;

public class ThreeDActivity extends AppCompatActivity {
    private static final String TAG = "ThreeDActivity";
    //scene
    private SceneView mSceneView;
    String localModel = "PaperBag.sfb";
    ImageButton back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d);
        mSceneView = findViewById(R.id.sceneView);
        back = findViewById(R.id.back);
        title=findViewById(R.id.text_header);
        title.setText("");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        createScene();
    }

    private void createScene() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse(localModel))
                    .setRegistryId(localModel)
                    .build()
                    .thenAccept(modelRenderable -> onRenderableLoaded(modelRenderable))
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG);
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
//        try {
//            mSceneView.resume();
//        } catch (CameraNotAvailableException e) {
//        }
        createScene();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mSceneView.pause();
        try {
            Log.d(TAG, "onPause: ");
            mSceneView.destroy();
        } catch (Exception e) {
            Log.d(TAG, "onPause: exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
