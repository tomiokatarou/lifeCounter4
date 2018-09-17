package com.websarva.wings.android.animationsample;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AnimationCircle extends Animation {
    private Circle circle;

    // 中心座標
    // アニメーション角度
    private float initRadius=0;
    private float time;
    private float canvasRadius;
    private float x,y;

    AnimationCircle(Circle circ,float x,float y,float time) {
        this.circle = circ;
        this.canvasRadius = circ.getCanvasRadius();//radius2の値を受け取っている
        this.time = time;
        this.x=x;
        this.y=y;
    }
    @Override
    protected void applyTransformation( float interpolatedTime, Transformation transformation) {
        float radius = (initRadius + (canvasRadius*60) * interpolatedTime);//interpolatedTimeは0.0～1.0に変化する値
//60 60.1
        circle.setXY(x,y);
        if( time > interpolatedTime){
            //前半
            circle.setRadius2(radius);
            circle.setHollowRadius(0);
        }else{
            //後半
            //circle.setRadius2(radius);60.1
            circle.setHollowRadius((0 + (canvasRadius*55) * (interpolatedTime-time)));
        }

        circle.requestLayout();
    }
}
