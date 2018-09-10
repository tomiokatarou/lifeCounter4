package com.websarva.wings.android.animationsample;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AnimationCircle extends Animation {
    private Circle circle;

    // 中心座標
    private float centerX;
    private float centerY;

    // アニメーション角度
    private float initRadius=0;
    private float diff=50;
    private float oldRadius;
    private float newRadius;
    private int strokeWidth;
    private float radius;//半径
    private float radius2;//最終的な半径
    private float hollowRadius=5;//中空の半径
    private float time;
    private float canvasRadius;

    AnimationCircle(Circle circ,float time) {
        //this.radius=radius;
        //this.radius2=radius2;
        //this.oldAngle = circ.getAngle();
        //this.newAngle = newAngle;

        //this.newRadius = newRadius;
        //this.strokeWidth = circ.getStrokeWidth();

        this.oldRadius = circ.getRadius2();//radius2の値を受け取っている
        //this.diff = diff;
        //this.strokeWidth =strokeWidth;
        this.circle = circ;
        this.canvasRadius = circ.getCanvasRadius();
        //this.initRadius = initRadius;//ここで書かないと意味ない
        this.time = time;
        //System.out.print("AnimationCircle radius "+radius+"\n");
    }
/*
    AnimationCircle(Circle circ,float initRadius,float diff, int strokeWidth) {
        //this.radius=radius;
        //this.radius2=radius2;
        //this.oldAngle = circ.getAngle();
        //this.newAngle = newAngle;

        //this.newRadius = newRadius;
        //this.strokeWidth = circ.getStrokeWidth();

        this.oldRadius = circ.getRadius();//radius2の値を受け取っている
        this.diff = diff;
        this.strokeWidth =strokeWidth;
        this.circle = circ;
        this.initRadius = initRadius;//ここで書かないと意味ない
        //System.out.print("AnimationCircle radius "+radius+"\n");
    }
*/
    @Override
    protected void applyTransformation( float interpolatedTime, Transformation transformation) {
        //float radius = initRadius +oldRadius+ (diff) * interpolatedTime;
        float radius = (float) (initRadius + (canvasRadius*60) * interpolatedTime);//interpolatedTimeは0.0～1.0に変化する値
        float hollowRadius = initRadius + (80) * interpolatedTime;


        if( time > interpolatedTime){
            //前半
            circle.setRadius2(radius);
            circle.setHollowRadius(0);
        }else{
            //後半
            //circle.setRadius2(radius);
            circle.setHollowRadius((float) (0 + (canvasRadius*60.1) * (interpolatedTime-time)));
        }


        circle.requestLayout();

/*
        System.out.print("applyTransformation interpolatedTime "+interpolatedTime+"\n");
        System.out.print("applyTransformation radius "+radius+"\n");
        System.out.print("applyTransformation strokeWidth "+strokeWidth+"\n");
        */
        //float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
        //float radius = oldRadius + ((oldRadius - newRadius) * interpolatedTime);
        //System.out.print("applyTransformation oldRadius "+oldRadius+" newRadius "+newRadius+" radius "+radius+"\n");
    }
}
