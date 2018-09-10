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
    private RectF rect;

    // Animation 開始地点をセット
    //private static final int radius2Target = 270;
    // 初期 radius2
    private float radius2 = 5;//radiusかぶってるので
    private float hollowRadius = 20;
    float canvasRadius;

    // Arcの幅
    int strokeWidth;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        // Arcの色
        paint.setColor(Color.argb(255, 0, 0, 255));
        // Arcの範囲
        rect = new RectF();
    }

    //ここが毎フレーム描画してる部分　アニメーションで変化していく部分
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
/*
        // 背景、半透明
        canvas.drawColor(Color.argb(64, 0, 0, 255));
*/

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setColor(Color.argb(255, 164, 199, 57));
        paint.setColor(getResources().getColor(R.color.colorWhite));

        canvas.drawARGB(0, 255, 255, 255);//背景色
        // Canvas 中心点
        float x = canvas.getWidth()/9;
        float y = canvas.getHeight()/2;
        // 半径
        //float radius = canvas.getWidth()/4+strokeWidth;
        float radius = canvas.getWidth()/20;
        canvasRadius = radius;

        // 円弧の領域設定

        System.out.print("onDraw canvasRadius "+canvasRadius+ " radius2 "+radius2+"\n");
        /*
        System.out.print("onDraw radius "+radius+"\n");
        System.out.print("onDraw strokeWidth "+strokeWidth+"\n");
        */
        rect.set(x - radius, y - radius, x + radius, y + radius);
        //paint.setStrokeWidth(strokeWidth);
        //System.out.print("onDraw strokeWidth "+strokeWidth+"\n");
        // 円弧の描画
        //canvas.drawArc(rect, radius2Target, radius2, false, paint);
        //canvas.drawCircle(x, y, radius2, paint);
        // 目
        Path clipPath = new Path();
        clipPath.addCircle(x, y, hollowRadius, Path.Direction.CW);
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE);

        Path path = new Path();

        // 頭
        path.addCircle(x, y, radius2, Path.Direction.CW);

        canvas.drawPath(path, paint);

    }

    public float getCanvasRadius() {
        return canvasRadius;
    }
    // AnimationAへ現在のradius2を返す
    public float getRadius2() {
        return radius2;
    }
    // AnimationAから新しいradius2が設定される
    public void setRadius2(float radius2) {
        this.radius2 = radius2;
    }
    // AnimationAへ現在のradius2を返す
    public float getHollowRadius() {
        return hollowRadius;
    }
    // AnimationAから新しいradius2が設定される
    public void setHollowRadius(float hollowRadius) {
        this.hollowRadius = hollowRadius;
    }
    public int getStrokeWidth() {
        return strokeWidth;
    }
    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
    }
}