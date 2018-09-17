package com.websarva.wings.android.animationsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import static com.websarva.wings.android.animationsample.R.color.colorWhite;

public class Circle extends View{

    private final Paint paint;

    // Animation 開始地点をセット
    //private static final int radius2Target = 270;
    // 初期 radius2
    private float radius2 = 5;//radiusかぶってるので
    private float hollowRadius = 20;
    float canvasRadius;
    float x,y;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorWhite));
    }

    //ここが毎フレーム描画してる部分　アニメーションで変化していく部分
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(0, 255, 255, 255);//背景色
        // Canvas 中心点
        //float x = canvas.getWidth()/7;
        //float y = canvas.getHeight()/2;
        // 半径
        //float radius = canvas.getWidth()/4+strokeWidth;
        float radius = canvas.getWidth()/20;
        canvasRadius = radius;
        // 目
        Path clipPath = new Path();
        clipPath.addCircle(x, y, hollowRadius, Path.Direction.CW);
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE);
        System.out.print("hollowRadius "+hollowRadius+"\n");

        Path path = new Path();
        // 頭
        path.addCircle(x, y, radius2, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    public float getCanvasRadius() {
        return canvasRadius;
    }
    // Animationから新しいradius2が設定される
    public void setRadius2(float radius2) {
        this.radius2 = radius2;
    }
    // Animationから新しいradius2が設定される
    public void setHollowRadius(float hollowRadius) {
        this.hollowRadius = hollowRadius;
    }
    // Animationから新しいradius2が設定される
    public void setXY(float x,float y) {
        this.x = x;
        this.y = y;
    }
}