package com.ayata.purvamart.node;

import android.os.Handler;

import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.BaseGestureRecognizer;
import com.google.ar.sceneform.ux.BaseTransformableNode;
import com.google.ar.sceneform.ux.BaseTransformationController;
import com.google.ar.sceneform.ux.DragGesture;
import com.google.ar.sceneform.ux.DragGestureRecognizer;

public class DragRotationController extends BaseTransformationController<DragGesture> {
    @Override
    public void onActivated(Node node) {
                super.onActivated(node);
        //   final Handler handler=new Handler(Looper.getMainLooper()).

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transformcamera(initialLat, initialLong);

            }
        }, 0);
    }
    private DragTransformableNode transformableNode;
    private DragGestureRecognizer dragGestureRecognizer;


    private static double initialLat = 26.15444376319647;
    private static double initialLong = 18.995950736105442;
    private float rotationRateDegrees = 0.5f;


    public DragRotationController(BaseTransformableNode transformableNode, BaseGestureRecognizer<DragGesture> gestureRecognizer) {
        super(transformableNode, gestureRecognizer);
    }

    public DragRotationController(DragTransformableNode dragTransformableNode, DragGestureRecognizer dragGestureRecognizer) {

        super(dragTransformableNode, dragGestureRecognizer);
        this.transformableNode = dragTransformableNode;
        this.dragGestureRecognizer = dragGestureRecognizer;
    }

    @Override
    protected boolean canStartTransformation(DragGesture gesture) {
        return transformableNode.isSelected();
    }

    @Override
    protected void onContinueTransformation(DragGesture gesture) {

        double rotationAmountY = gesture.getDelta().y * rotationRateDegrees;
        double rotationAmountX = gesture.getDelta().x * rotationRateDegrees;
//        double deltaAngleY = rotationAmountY.toDouble()
//        double deltaAngleX = rotationAmountX.toDouble()

        initialLong -= rotationAmountX;
        initialLat += rotationAmountY;

        transformcamera(initialLat, initialLong);
    }

    private void transformcamera(double initialLat, double initialLong) {

        if (transformableNode.getScene() != null) {
            Camera camera = transformableNode.getScene().getCamera();

            Quaternion rot = Quaternion.eulerAngles(new Vector3(0F, 0F, 0F));
            Vector3 pos = new Vector3(getX(initialLat, initialLong), getY(initialLat, initialLong), getZ(initialLat, initialLong));
            rot = Quaternion.multiply(rot, new Quaternion(Vector3.up(), (float) (initialLong)));
            rot = Quaternion.multiply(rot, new Quaternion(Vector3.right(), (float) (-initialLat)));
//            camera?.localRotation = rot
//            camera?.localPosition = pos

            camera.setLocalRotation(rot);
            camera.setLocalPosition(pos);
        }
    }


    @Override
    protected void onEndTransformation(DragGesture gesture) {

    }

    private float getX(Double lat, Double long_) {
        return (float) (transformableNode.radius * Math.cos(Math.toRadians(lat)) * Math.sin(Math.toRadians(long_)));
    }


    private float getY(Double lat, Double long_) {
        return (float) ((float) transformableNode.radius * Math.sin(Math.toRadians(lat)));
    }

    private float getZ(Double lat, Double long_) {
        return (float) (transformableNode.radius * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(long_)));
    }

//    @Override
//    public void onActivate(Node node) {
//        super.onActivated(node);
//        //   final Handler handler=new Handler(Looper.getMainLooper()).
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                transformcamera(initialLat, initialLong);
//
//            }
//        }, 0);
//    }


}
