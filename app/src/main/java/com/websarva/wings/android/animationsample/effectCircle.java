package com.websarva.wings.android.animationsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

public class effectCircle extends View{

    private final Paint paint;
    private RectF rect;

    // Animation 開始地点をセット
    // 初期 radius2
    private float radius2 = 5;//radiusかぶってるので
    float canvasRadius;
    float clipRadius;
    RectF clipRect = new RectF(0,0,0,0);
    float xx,yy;


    // Arcの幅
    int strokeWidth;

    public effectCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // Arcの色
        paint.setColor(Color.argb(255, 0, 0, 255));
        // Arcの範囲
        rect = new RectF(0,0,0,0);
    }

    //ここが毎フレーム描画してる部分　アニメーションで変化していく部分
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setColor(Color.argb(255, 164, 199, 57));
        paint.setColor(getResources().getColor(R.color.colorWhite));

        canvas.drawARGB(0, 255, 255, 255);//背景色
        // Canvas 中心点
        //float x = canvas.getWidth()/9;
        //float y = canvas.getHeight()/2;
        // 半径
        float radius = canvas.getWidth()/20;
        canvasRadius = radius;

        // 円弧の領域設定

        //System.out.print("onDraw canvasRadius "+canvasRadius+ " radius2 "+radius2+"\n");
        //rect.set(x - radius, y - radius, x + radius, y + radius);
        Path path = new Path();
        // 頭

        System.out.println("x:" + xx + " y:"+yy);
        path.addCircle(xx, yy, radius2, Path.Direction.CCW);
        // 目

        Path clipPath = new Path();
        clipPath.addRect(clipRect, Path.Direction.CCW);
        canvas.clipPath(clipPath, Region.Op.INTERSECT);

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
    public int getStrokeWidth() {
        return strokeWidth;
    }
    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
    }

    public void setRect(RectF rectf){
        this.clipRect =rectf;
    }
    public RectF getRect() {
        return rect;
    }
    /*
    public float getXX(){
        return xx;
    }
    public float getYY(){
        return yy;
    }
    */
    public void setXX(float xx){
        this.xx = xx;
    }
    public float getXX() {
        return xx;
    }
    public void setYY(float yy){
        this.yy = yy;
    }
    public float getYY() {
        return yy;
    }
}