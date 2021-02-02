package com.ayata.purvamart.node;

import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class DragTransformableNode extends TransformableNode {

   // private  Draf

    @Override
    public TransformationSystem getTransformationSystem() {
        return transformationSystem;
    }

    public void setTransformationSystem(TransformationSystem transformationSystem) {
        this.transformationSystem = transformationSystem;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    TransformationSystem transformationSystem;
    float radius;

    public DragTransformableNode(float radius, TransformationSystem transformationSystem) {

        super(transformationSystem);
        DragRotationController dragRotationController=new DragRotationController(this,transformationSystem.getDragRecognizer());
        this.radius=radius;
        this.transformationSystem=transformationSystem;

    }

//    public  DragTransformableNode(TransformableNode transformableNode)
//    {
//        transformableNode.super(new DragRotation,this);
//
//    }


}
