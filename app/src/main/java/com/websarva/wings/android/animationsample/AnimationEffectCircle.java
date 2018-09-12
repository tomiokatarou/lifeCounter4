package com.websarva.wings.android.animationsample;

import android.graphics.RectF;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;

public class AnimationEffectCircle extends Animation {
    private effectCircle circle;

    // アニメーション角度
    private float canvasRadius;
    private float xx,yy;

    AnimationEffectCircle(effectCircle circ,float xx,float yy,RectF clipRect) {
        this.circle = circ;
        /*
        this.xx=circ.getXX();
        this.yy=circ.getYY();
        */
        //this.oldRadius = circ.getRadius2();//radius2の値を受け取っている
        this.canvasRadius = circ.getCanvasRadius();//canvasRadiusの値を受け取っている
        //this.clipRect = circ.getRect();

        circ.setXX(xx);
        circ.setYY(yy);
        circ.setRect(clipRect);
    }

    @Override
    protected void applyTransformation( float interpolatedTime, Transformation transformation) {
        float radius = (float) ((canvasRadius*30) * interpolatedTime);//interpolatedTimeは0.0～1.0に変化する値

        circle.setRadius2(radius);
        //circle.setRect(clipRect);//left top right bottom
        circle.requestLayout();
    }
}
