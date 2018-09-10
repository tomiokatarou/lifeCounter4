package com.websarva.wings.android.animationsample;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationSampleFragment extends Fragment {

    TextView mObj1;
    TextView mObj2;
    TextView mObj3;
    TextView mObj4;
    TextView mObj5;
    Button mButton;

    public AnimationSampleFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_animation_sample, container, false);
        mObj1 = (TextView) view.findViewById(R.id.obj1);
        mObj2 = (TextView) view.findViewById(R.id.obj2);
        mObj3 = (TextView) view.findViewById(R.id.obj3);
        mObj4 = (TextView) view.findViewById(R.id.obj4);
        mObj5 = (TextView) view.findViewById(R.id.obj5);

       // mButton = (Button) view.findViewById(R.id.p1plus5);
        mButton.setText("open");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButton.getText().toString().equals("close")) {
                    closeMenu();
                    mButton.setText("open");
                } else {
                    openMenu();
                    mButton.setText("close");
                }
            }
        });
        return view;
    }

    private Animator rotateAnimator(View target) {
        ObjectAnimator  oa = ObjectAnimator.ofFloat(target, "rotation", 0f,360f);
        oa.setDuration(1000);
        return oa;
    }

    private Animator translateAnimator(View target, float distance, float degree, boolean open) {
        float fromX;
        float fromY;
        float toX;
        float toY;

        if (open) {
            toX = (float) (distance * Math.cos(Math.toRadians(degree)));
            toY = (float) (distance * Math.sin(Math.toRadians(degree)));
            fromX = 0f;
            fromY = 0f;
        } else {
            toX = 0f;
            toY = 0f;
            fromX = (float) (distance * Math.cos(Math.toRadians(degree)));
            fromY = (float) (distance * Math.sin(Math.toRadians(degree)));
        }

        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX",fromX,toX);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY",fromY,toY);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(1000);
        return oa;
    }

    private Animator alphaAnimator(View target, boolean open) {
        float fromAlpha;
        float toAlpha;
        if (open) {
            fromAlpha = 0f;
            toAlpha = 1f;
        } else {
            fromAlpha = 1f;
            toAlpha = 0f;
        }
        ObjectAnimator oa = ObjectAnimator.ofFloat(target,"alpha",fromAlpha,toAlpha);
        oa.setDuration(1000);
        return oa;
    }

    private void alphaTranslateRotateAnimation(View target, float distance, float degree, long delay, boolean open) {

        Animator alpha = alphaAnimator(target,open);
        alpha.setInterpolator(new DecelerateInterpolator());

        Animator translate = translateAnimator(target,distance,degree,open);
        if (open) {
            translate.setInterpolator(new BounceInterpolator());
        } else {
            translate.setInterpolator(new OvershootInterpolator());
        }

        Animator rotate = rotateAnimator(target);
        rotate.setInterpolator(new DecelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.setStartDelay(delay);
        set.playTogether(alpha,translate,rotate);

        set.start();
    }

    private void openMenu() {
        alphaTranslateRotateAnimation(mObj1,-300,0,200,true);
        alphaTranslateRotateAnimation(mObj2,-300,22,150,true);
        alphaTranslateRotateAnimation(mObj3,-300,45,100,true);
        alphaTranslateRotateAnimation(mObj4,-300,66,50,true);
        alphaTranslateRotateAnimation(mObj5,-300,90,0,true);

        Animator animator = rotateAnimator(mButton);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void closeMenu() {
        alphaTranslateRotateAnimation(mObj1,-300,0,200,false);
        alphaTranslateRotateAnimation(mObj2,-300,22,150,false);
        alphaTranslateRotateAnimation(mObj3,-300,45,100,false);
        alphaTranslateRotateAnimation(mObj4,-300,66,50,false);
        alphaTranslateRotateAnimation(mObj5,-300,90,0,false);

        Animator animator = rotateAnimator(mButton);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
