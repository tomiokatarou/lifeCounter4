package com.websarva.wings.android.animationsample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    static final String TAG = "MainActivity";

    private void myLog(String tag, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(tag, msg);
    }

    TextView mObj1;
    TextView mObj2;
    TextView mObj3;
    TextView mObj4;
    TextView mObj5;
    Button mButton;
    TextView p1lifeHistory1;
    TextView p1lifeHistory2;
    TextView p1lifeHistory3;
    TextView p1lifeHistory4;
    TextView p1lifeHistory5;
    TextView p1lifeHistory6;
    TextView p1lifeHistory7;
    TextView p1lifeHistory8;
    TextView p1lifeHistory9;
    TextView p1Life_txt;
    TextView p1Life_txt1;
    TextView p1Life_txt2;
    TextView p1Life_txt3;
    TextView p1Life_txt4;
    TextView p1Life_txt5;
    TextView p1txt;
    TextView p1Psn_txt,p1Psn_txt1,p1Psn_txt2,p1Psn_txt3;
    TextView p1Eng_txt,p1Eng_txt1,p1Eng_txt2,p1Eng_txt3;
    TextView obj1;
    Button p1plus1,p1plus5,p1minus1,p1minus5,p1PsnPlus,p1PsnMinus,p1EngPlus,p1EngMinus;
    Button resetBtn;

    int p1LifeInt = 20,p1PsnInt = 0,p1EngInt = 0;

    //historyDriver
    int p1LifeDriver = 0,p1PsnDriver = 0,p1EngDriver = 0;//-1
    int p1LifeHisDriver = -1;//どのテキストを動かすかというためだけの循環する変数　にしたい
    boolean isP1LifeHisMax = false;//ヒストリーがマックスになってるかどうか

    public ArrayList<String> p1LifeList;
    public ArrayList<String> p1LifeListTmp;

    //遅らせるやつ
    private Handler mHandler = new Handler();
    private Runnable delayedUpdate;

    //フォントサイズ style.xmlで定義してる
    final int hisTxtMove = 110;//移動量 110
    float fadeFontScale = 1.2f;//3
    int lifeFontSize = 250;

    //グローバルな増減値
    int p1GlobalTmp;

    long lifeScaleDuration=200;
    long lifeHisTransDuration=700;
    //----------------------------------------------------------------------------------------------
    private Circle circ;
    private effectCircle circP1Plus1;
    private int endAngle = 0;
    private int animationPeriod = 2000;

    public RectF rectP1Plus1,rectP1Plus5,rectP1Minus1,rectP1Minus5,rectP1PsnPlus,rectP1PsnMinus,rectP1EngPlus,rectP1EngMinus;
    //----------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_animation_sample);//先においておかないとボタンがないのでリスナーも使えない

        //final ConstraintLayout relativeLayout_o = (ConstraintLayout) findViewById(R.id.fragment_animation_sample);
        final ConstraintLayout p1Layout_o = (ConstraintLayout) findViewById(R.id.p1Layout);
        final ConstraintLayout p2Layout_o = (ConstraintLayout) findViewById(R.id.p2Layout);

        mObj1 = (TextView) this.findViewById(R.id.obj1);
        mObj2 = (TextView) this.findViewById(R.id.obj2);
        mObj3 = (TextView) this.findViewById(R.id.obj3);
        mObj4 = (TextView) this.findViewById(R.id.obj4);
        mObj5 = (TextView) this.findViewById(R.id.obj5);
        //オブジェクトの生成
        p1Life_txt = (TextView) this.findViewById(R.id.p1Life_txt);
        p1Life_txt.setText(p1LifeInt+"");
        p1Life_txt1 = (TextView) this.findViewById(R.id.p1Life_txt1);
        p1Life_txt2 = (TextView) this.findViewById(R.id.p1Life_txt2);
        p1Life_txt3 = (TextView) this.findViewById(R.id.p1Life_txt3);
        p1Life_txt4 = (TextView) this.findViewById(R.id.p1Life_txt4);
        p1Life_txt5 = (TextView) this.findViewById(R.id.p1Life_txt5);
        //オブジェクトの生成
        p1lifeHistory1 = (TextView) this.findViewById(R.id.p1lifeHistory1);
        p1lifeHistory2 = (TextView) this.findViewById(R.id.p1lifeHistory2);
        p1lifeHistory3 = (TextView) this.findViewById(R.id.p1lifeHistory3);
        p1lifeHistory4 = (TextView) this.findViewById(R.id.p1lifeHistory4);
        p1lifeHistory5 = (TextView) this.findViewById(R.id.p1lifeHistory5);
        p1lifeHistory6 = (TextView) this.findViewById(R.id.p1lifeHistory6);
        p1lifeHistory7 = (TextView) this.findViewById(R.id.p1lifeHistory7);
        p1lifeHistory8 = (TextView) this.findViewById(R.id.p1lifeHistory8);
        p1lifeHistory9 = (TextView) this.findViewById(R.id.p1lifeHistory9);

        p1Psn_txt = (TextView) this.findViewById(R.id.p1Psn_txt);
        p1Psn_txt.setText(p1PsnInt+"");
        p1Psn_txt1 = (TextView) this.findViewById(R.id.p1Psn_txt1);
        p1Psn_txt2 = (TextView) this.findViewById(R.id.p1Psn_txt2);
        p1Psn_txt3 = (TextView) this.findViewById(R.id.p1Psn_txt3);

        p1Eng_txt = (TextView) this.findViewById(R.id.p1Eng_txt);
        p1Eng_txt.setText(p1EngInt+"");
        p1Eng_txt1 = (TextView) this.findViewById(R.id.p1Eng_txt1);
        p1Eng_txt2 = (TextView) this.findViewById(R.id.p1Eng_txt2);
        p1Eng_txt3 = (TextView) this.findViewById(R.id.p1Eng_txt3);

        //ボタンの生成
        p1plus1 = (Button) this.findViewById(R.id.p1plus1);
        p1plus5 = (Button) this.findViewById(R.id.p1plus5);
        p1minus1 = (Button) this.findViewById(R.id.p1minus1);
        p1minus5 = (Button) this.findViewById(R.id.p1minus5);
        p1PsnPlus = (Button) this.findViewById(R.id.p1PsnPlus);
        p1PsnMinus = (Button) this.findViewById(R.id.p1PsnMinus);
        p1EngPlus = (Button) this.findViewById(R.id.p1EngPlus);
        p1EngMinus = (Button) this.findViewById(R.id.p1EngMinus);
        resetBtn = (Button) this.findViewById(R.id.resetBtn);

        //rect
        rectP1Plus1 = new RectF(0,0,0,0);
        rectP1Plus5 = new RectF(0,0,0,0);
        rectP1Minus1 = new RectF(0,0,0,0);
        rectP1Minus5 = new RectF(0,0,0,0);
        rectP1PsnPlus = new RectF(0,0,0,0);
        rectP1PsnMinus = new RectF(0,0,0,0);
        rectP1EngPlus = new RectF(0,0,0,0);
        rectP1EngMinus = new RectF(0,0,0,0);

        //デバッグ用

        //counterFragment-----------------------------------------------
        final ConstraintLayout container = (ConstraintLayout) findViewById(R.id.container);


        //ボタンの矢印
        int color = getResources().getColor(R.color.colorGray);
        ImageView p1plus1UpArrow = (ImageView) findViewById(R.id.p1plus1UpArrow);
        p1plus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1plus5UpArrow = (ImageView) findViewById(R.id.p1plus5UpArrow);
        p1plus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1minus1UpArrow = (ImageView) findViewById(R.id.p1minus1UpArrow);
        p1minus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1minus5UpArrow = (ImageView) findViewById(R.id.p1minus5UpArrow);
        p1minus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //リセットボタン
        int colorPink = getResources().getColor(R.color.colorPink);
        ImageView resetBtnImg = (ImageView) findViewById(R.id.resetBtnImg);
        resetBtnImg.setColorFilter(colorPink, PorterDuff.Mode.SRC_IN);

        p1txt = (TextView) this.findViewById(R.id.p1textView);
        //p1txt.setText("p1LifeHisDriver :" + p1LifeHisDriver);
        //初期配置
        fn_translation(p1Life_txt1, 0, 0, 1, "invisible");
        fn_translation(p1Life_txt2, 0, 0, 1, "invisible");
        fn_translation(p1Life_txt3, 0, 0, 1, "invisible");
        fn_translation(p1Life_txt4, 0, 0, 1, "invisible");
        fn_translation(p1Life_txt5, 0, 0, 1, "invisible");

        fn_translation(p1lifeHistory1, 0, 0, 0, "invisible");//visible
        fn_translation(p1lifeHistory2, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory3, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory4, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory5, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory6, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory7, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory8, 0, 0, 0, "invisible");
        fn_translation(p1lifeHistory9, 0, 0, 0, "invisible");

        fn_translation(p1Psn_txt1, 0, 0, 1, "invisible");
        fn_translation(p1Psn_txt2, 0, 0, 1, "invisible");
        fn_translation(p1Psn_txt3, 0, 0, 1, "invisible");
        fn_translation(p1Eng_txt1, 0, 0, 1, "invisible");
        fn_translation(p1Eng_txt2, 0, 0, 1, "invisible");
        fn_translation(p1Eng_txt3, 0, 0, 1, "invisible");
        scaleAnimatorToXY(container,0,0,1000);

        p1LifeList = new ArrayList<>();
        p1LifeListTmp = new ArrayList<>();

        //----------------------------------------------------------------------------------------------

        circ = (Circle) findViewById(R.id.circle);
        circP1Plus1 = (effectCircle) findViewById(R.id.circP1Plus1);
        //----------------------------------------------------------------------------------------------


        //----------------------------------------------------------------------------------------------
        p1Layout_o.setOnTouchListener(new View.OnTouchListener() {
            float firstTouch = 0;
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int p1LifeDistInt;
            float p1LifeDistance;//finalは変更を許可しない
            float s = 3f;
            int plusminus = 1;
            int distTmp=0;

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

            TextView p1txt = (TextView) findViewById(R.id.p1textView);//debug用
            TextView p1txt2 = (TextView) findViewById(R.id.p1textView2);//debug用

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        //画面がタッチされた場合の処理
                        int pointerIndex = motionEvent.getActionIndex();
                        firstTouch = motionEvent.getY(pointerIndex);
                        p1LifeDistance = 0;
                        p1LifeDistInt = 0;
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                        p1txt.setText("p1txt ACTION_POINTER_DOWN \n" + " firstTouchY " + firstTouch
                                + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                                + " \n p1LifeHisDriver: " + p1LifeHisDriver
                                + " \n p1LifeDistance: " + p1LifeDistance
                                + " \n p1LifeDistInt: " + p1LifeDistInt
                                + " \n p1LifeList.size() " + p1LifeList.size()
                                + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        //タッチが離れた場合の処理
                        //int pointerIndex = motionEvent.getActionIndex();
                        p1LifeInt = p1LifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る
                        p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouchY " + firstTouch
                                + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                                + " \n p1LifeHisDriver: " + p1LifeHisDriver
                                + " \n p1LifeDistance: " + p1LifeDistance
                                + " \n p1LifeDistInt: " + p1LifeDistInt
                                + " \n p1LifeList.size() " + p1LifeList.size()
                                + " \n p1GlobalTmp "+p1GlobalTmp
                                + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                if (p1LifeDistInt != 0) {
                                    fn_lifeDriver(p1GlobalTmp, plusminus);
                                    System.out.println("p1Layout_o p1GlobalTmp " + p1GlobalTmp);
                                    p1GlobalTmp=0;
                                }
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        //これ意味ある？
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        p1LifeDistance = firstTouch - motionEvent.getY(0);//距離
                        p1LifeDistInt = (int) p1LifeDistance / 100;//整数で

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);

                            p1txt2.setText("初回 \n distTmp " + distTmp);
                            p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                                    + " \n p1LifeDistInt: " + p1LifeDistInt
                                    + " \n distTmp: " + distTmp
                                    + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
                            );
                            distTmp = p1LifeDistInt;
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }

                        //配列の中身が一つ以上あり、distTmpに値が入っている状態
                        p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                                + " \n p1LifeDistInt: " + p1LifeDistInt
                                + " \n distTmp: " + distTmp
                                + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
                        );
                        if (p1LifeDistInt != distTmp) {
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            if (p1LifeDriver < 3) {
                                p1LifeDriver++;
                            } else {
                                p1LifeDriver = 0;
                            }
                            //省略できそうだけどめんどそう
                            fn_p1LifeFade(p1LifeIntTemp);
                            //動くようにはなったけど、今度リストが追加されなくなってしまった。
                            //p1countの扱いと動作の整理しないといかんなこれは
                            distTmp = p1LifeDistInt;
                            p1txt2.setText("p1LifeDistInt!=distTmp \n distTmp " + distTmp);
                        }
                        //値が違った場合、違い続けるってことか
                        if (p1LifeDistIntArr.size() > 2) {
                            //2より大きくなったらリムーブ
                            p1LifeDistIntArr.remove(0);
                            p1txt2.setText("2より大きくなったらリムーブ \n distTmp " + distTmp);
                        }

                        p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                        p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置

                        break;
                    }
                }
                return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
        });

        buttonListener p1plus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 1;
                clipRect = rectP1Plus1;

            }
        };
        p1plus1.setOnTouchListener(p1plus1TouchListener);
        buttonListener p1plus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 5;
                clipRect = rectP1Plus5;

            }
        };
        p1plus5.setOnTouchListener(p1plus5TouchListener);
        buttonListener p1minus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -1;
                clipRect = rectP1Minus1;
            }
        };
        p1minus1.setOnTouchListener(p1minus1TouchListener);
        buttonListener p1minus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -5;
                clipRect = rectP1Minus5;
            }
        };
        p1minus5.setOnTouchListener(p1minus5TouchListener);
        //poison
        buttonCounterListener p1PsnPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 1;creaseEngNum = 0;
                clipRect = rectP1PsnPlus;

            }
        };
        p1PsnPlus.setOnTouchListener(p1PsnPlusTouchListener);
        buttonCounterListener p1PsnMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = -1;creaseEngNum = 0;
                clipRect = rectP1PsnMinus;

            }
        };
        p1PsnMinus.setOnTouchListener(p1PsnMinusTouchListener);
        //energy
        buttonCounterListener p1EngPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = 1;
                clipRect = rectP1EngPlus;

            }
        };
        p1EngPlus.setOnTouchListener(p1EngPlusTouchListener);
        buttonCounterListener p1EngMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = -1;
                clipRect = rectP1EngMinus;

            }
        };
        p1EngMinus.setOnTouchListener(p1EngMinusTouchListener);

        resetBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.print("resetBtn \n");
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:{
                        System.out.print("ACTION_POINTER_UP \n");
                        AnimationCircle animation = new AnimationCircle(circ, (float) 0.5);
                        // アニメーションの起動期間を設定
                        animation.setInterpolator(new DecelerateInterpolator());//OvershootInterpolator DecelerateInterpolator
                        animationPeriod = 800;
                        animation.setDuration(animationPeriod);
                        circ.startAnimation(animation);
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                p1LifeInt = 20;
                                p1LifeDriver = 0;
                                p1LifeHisDriver = -1;
                                isP1LifeHisMax = false;
                                p1LifeList = new ArrayList<>();

                                p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                                p1lifeHistory1.setText( "" );
                                p1lifeHistory2.setText( "" );
                                p1lifeHistory3.setText( "" );
                                p1lifeHistory4.setText( "" );
                                p1lifeHistory5.setText( "" );
                                p1lifeHistory6.setText( "" );
                                p1lifeHistory7.setText( "" );
                                p1lifeHistory8.setText( "" );
                                p1lifeHistory9.setText( "" );
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, (long) (animationPeriod*0.3));
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
        });
    }

                        /*
                        間違えて触ってしまった場合　値が変化してない場合は何も起こさないようにしたいけど
                        タッチしただけなのか、タッチしたつもりだけどmove通ってしまったのかは判断がつかない
                        したがって、moveを通ってもとの値に戻そうとしても無駄　という仕様になる
                        つまり、どんな条件でもフェードは起こる
                        値が変化してない場合はマイナスされる

                        moveで値を変化させて離した時だけフェードが起こらないようにしないといけない
                         */

    //p1Life_txt p1LifeInt lifeDistInt plusminus p1LifeHisDriver isP1LifeHisMax
    public void fn_lifeDriver(int lifeDistInt,int plusminus) {
        mHandler.removeCallbacks(delayedUpdate);
        //p1LifeDistIntが０のときは何も起きてはいけない
        //ただ、ボタンから来た場合はDistIntが０でもOK

        //p1txt.setText("fn_lifeDriver \n");//テキストの編集

        if(lifeDistInt > 0){
            p1LifeList.add(p1LifeInt + " (+" + lifeDistInt + ")");
        } else {
            p1LifeList.add(p1LifeInt + " (" + lifeDistInt + ")");//マイナスは自動でつく
        }
        System.out.println("fn_lifeDriver lifeDistInt " + lifeDistInt);
        //p1Life_txt4の場合<4
        if (p1LifeHisDriver < 9) {
            p1LifeHisDriver++;
        } else {
            p1LifeHisDriver = 0;
        }
        //p1Life_txt4の場合>5
        if (p1LifeList.size() > 10) {
            p1LifeList.remove(0);
        }
        //p1Life_txt4の場合==4
        if (!isP1LifeHisMax && (p1LifeList.size() - 1) == 9) {
            isP1LifeHisMax = true;
            /*
            p1txt.setText("p1LifeHisDriver " + p1LifeHisDriver
                    + "\n isP1LifeHisMax " + isP1LifeHisMax
                    + "\n p1LifeList.size() " + p1LifeList.size()
            );
            */
        }
        //9だったとき
        //リストに突っ込んだ状態で動かす
        if (isP1LifeHisMax) {
            fn_p1LifeHisMove2(p1LifeList);
        } else {
            fn_p1LifeHisMove1(p1LifeList);
        }
    }
    private ValueAnimator fn_translation(View target, float toTX, float toTY, float toSX, float toSY, long duration, String fade) {

        Animator alpha = alphaAnimator(target,duration,fade);
        alpha.setInterpolator(new DecelerateInterpolator());

        Animator translate = translateAnimatorToXY(target,toTX,toTY,duration);
        translate.setInterpolator(new DecelerateInterpolator());

        Animator scale = scaleAnimatorToXY(target,toSX,toSY,duration);
        scale.setInterpolator(new DecelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        //set.setStartDelay(delay);
        set.playTogether(alpha,translate,scale);
        set.start();

        ValueAnimator anim = ValueAnimator.ofFloat(0.f, 100.f);
// アニメーションの時間(3秒)を設定する
        anim.setDuration(duration);
        anim.start();

        return anim;
    }


    class buttonListener implements View.OnTouchListener {
        //View.OnTouchListener buttonListener = new View.OnTouchListener(){
        float firstTouchX=0,firstTouchY = 0,releaseX,releaseY;
        int p1LifeIntTemp = 0;//指を動かしている間の値
        int p1LifeIntTemp2 = p1LifeInt;//最初の値
        int p1LifeDistInt;
        float p1LifeDistance;//finalは変更を許可しない
        float s = 3f;
        int plusminus = 1;
        int distTmp=0;
        String tmpState="";

        int creaseNum;
        RectF clipRect;

        ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

        TextView p1txt = (TextView) findViewById(R.id.p1textView);//debug用
        TextView p1txt2 = (TextView) findViewById(R.id.p1textView2);//debug用

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            p1LifeIntTemp2 = p1LifeIntTemp;
            setVariables();
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: actionPointerDown(motionEvent); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP: actionPointerUp(motionEvent); break;
                case MotionEvent.ACTION_MOVE: actionMove(motionEvent); break;
                case MotionEvent.ACTION_CANCEL: System.out.println("ACTION_CANCEL "); break;
            }
            return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
        }
        public void actionPointerDown(MotionEvent motionEvent){
            //画面がタッチされた場合の処理
            int pointerIndex = motionEvent.getActionIndex();
            firstTouchY = motionEvent.getY(pointerIndex);
            firstTouchX = motionEvent.getX(pointerIndex);
            p1LifeDistance = 0;
            p1LifeDistInt = 0;
            p1LifeIntTemp = p1LifeInt;//値を渡す
            p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
            p1txt.setText("p1txt ACTION_POINTER_DOWN \n" + " firstTouchY " + firstTouchY
                    + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    + " \n p1LifeDistance: " + p1LifeDistance
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
        }
        public void actionPointerUp(MotionEvent motionEvent){
            releaseX = motionEvent.getX();
            releaseY = motionEvent.getY();
            //System.out.println("actionPointerUp p1GlobalTmp " + p1GlobalTmp);
            System.out.println("releaseX:" + releaseX + " releaseY:"+releaseY);
            System.out.println("clipRect.left:" + clipRect.left + " clipRect.top:"+clipRect.top);
            //タッチが離れた場合の処理
            AnimationEffectCircle animation = new AnimationEffectCircle(circP1Plus1,releaseX+clipRect.left,releaseY+clipRect.top, clipRect);
            // アニメーションの起動期間を設定
            animation.setInterpolator(new DecelerateInterpolator());//OvershootInterpolator DecelerateInterpolator
            animationPeriod = 800;
            animation.setDuration(animationPeriod);
            circP1Plus1.startAnimation(animation);
            Animator alpha = alphaAnimator(circP1Plus1,200,"fadeout2");
            alpha.start();

            if (p1LifeDistIntArr.size() == 0) {
                //初回
                p1LifeDistIntArr.add(p1LifeDistInt);
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }
            //もしDistIntが０（移動してない）場合は変わりに-5を入れる
            if (p1LifeDistInt == 0) {
                p1LifeIntTemp += creaseNum;
                p1LifeDistInt = +creaseNum;
                p1GlobalTmp += creaseNum;
            } else {
                p1GlobalTmp += p1LifeDistInt;
            }
            p1LifeDistIntArr.add(p1LifeDistInt);
            //値が最初と変わってない限り
            if (p1LifeIntTemp != p1LifeIntTemp2) {
                fn_p1LifeFade(p1LifeIntTemp);
            }

            p1LifeInt = p1LifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る
            //ライフ変動
            p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
            if (p1LifeDriver < 3) {
                p1LifeDriver++;
            } else {
                p1LifeDriver = 0;
            }
            //動くようにはなったけど、今度リストが追加されなくなってしまった。
            //p1countの扱いと動作の整理しないといかんなこれは
            distTmp = p1LifeDistInt;

            p1LifeInt = p1LifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る

            System.out.println("iffff     p1GlobalTmp " + p1GlobalTmp);
            p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouchY " + firstTouchY
                    + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    + " \n p1LifeDistance: " + p1LifeDistance
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n p1GlobalTmp " + p1GlobalTmp
                    + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
            mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
            delayedUpdate = new Runnable() {
                public void run() {
                    if(p1GlobalTmp!=0) {
                        fn_lifeDriver(p1GlobalTmp, plusminus);
                        p1GlobalTmp = 0;
                        tmpState = "";
                    }
                }
            };
            mHandler.postDelayed(delayedUpdate, 1000);
            //これ意味ある？
            p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
        }
        public void actionMove(MotionEvent motionEvent){
            System.out.println("actionMove \n");
            p1LifeDistance = firstTouchY - motionEvent.getY(0);//距離
            p1LifeDistInt = (int) p1LifeDistance / 100;//整数で

            if (p1LifeDistIntArr.size() == 0) {
                //初回
                p1LifeDistIntArr.add(p1LifeDistInt);

                p1txt2.setText("初回 \n distTmp " + distTmp);
                p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                        + " \n p1LifeDistInt: " + p1LifeDistInt
                        + " \n distTmp: " + distTmp
                        + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
                );
                distTmp = p1LifeDistInt;
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }

            //配列の中身が一つ以上あり、distTmpに値が入っている状態
            p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n distTmp: " + distTmp
                    + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
            );
            if (p1LifeDistInt != distTmp) {
                p1LifeDistIntArr.add(p1LifeDistInt);
                if (p1LifeDriver < 3) {
                    p1LifeDriver++;
                } else {
                    p1LifeDriver = 0;
                }
                //省略できそうだけどめんどそう
                fn_p1LifeFade(p1LifeIntTemp);
                //動くようにはなったけど、今度リストが追加されなくなってしまった。
                //p1countの扱いと動作の整理しないといかんなこれは
                distTmp = p1LifeDistInt;
                p1txt2.setText("p1LifeDistInt!=distTmp \n distTmp " + distTmp);
            }
            //値が違った場合、違い続けるってことか
            if (p1LifeDistIntArr.size() > 2) {
                //2より大きくなったらリムーブ
                p1LifeDistIntArr.remove(0);
                p1txt2.setText("2より大きくなったらリムーブ \n distTmp " + distTmp);
            }

            p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
            p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置
        }
        public void setVariables(){
            creaseNum = 1;
        }
    }
    //カウンター用のボタンリスナー
    class buttonCounterListener implements View.OnTouchListener {
        float firstTouchX=0,firstTouchY = 0,releaseX,releaseY;
        int p1LifeIntTemp = 0;//指を動かしている間の値
        int p1LifeIntTemp2 = p1LifeInt;//最初の値
        int p1LifeDistInt;
        float p1LifeDistance;//finalは変更を許可しない
        float s = 3f;
        int plusminus = 1;
        int distTmp=0;
        String tmpState="";

        int creasePsnNum,creaseEngNum;
        RectF clipRect;

        ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

        TextView p1txt = (TextView) findViewById(R.id.p1textView);//debug用
        TextView p1txt2 = (TextView) findViewById(R.id.p1textView2);//debug用

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            setVariables();
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: actionPointerDown(motionEvent); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP: actionPointerUp(motionEvent); break;
                case MotionEvent.ACTION_MOVE: actionMove(motionEvent); break;
                case MotionEvent.ACTION_CANCEL: System.out.println("ACTION_CANCEL "); break;
            }
            return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
        }
        public void actionPointerDown(MotionEvent motionEvent){
            //画面がタッチされた場合の処理
            int pointerIndex = motionEvent.getActionIndex();
            firstTouchY = motionEvent.getY(pointerIndex);
            firstTouchX = motionEvent.getX(pointerIndex);
            p1LifeDistance = 0;
            p1LifeDistInt = 0;
            p1LifeIntTemp = p1LifeInt;//値を渡す
            p1Psn_txt.setText(String.valueOf(p1PsnInt));//ライフ
            p1Eng_txt.setText(String.valueOf(p1EngInt));//ライフ
        }
        public void actionPointerUp(MotionEvent motionEvent){
            releaseX = motionEvent.getX();
            releaseY = motionEvent.getY();
            //System.out.println("actionPointerUp p1GlobalTmp " + p1GlobalTmp);
            System.out.println("releaseX:" + releaseX + " releaseY:"+releaseY);
            System.out.println("clipRect.left:" + clipRect.left + " clipRect.top:"+clipRect.top);
            //タッチが離れた場合の処理
            AnimationEffectCircle animation = new AnimationEffectCircle(circP1Plus1,releaseX+clipRect.left,releaseY+clipRect.top, clipRect);
            // アニメーションの起動期間を設定
            animation.setInterpolator(new DecelerateInterpolator());//OvershootInterpolator DecelerateInterpolator
            animationPeriod = 800;
            animation.setDuration(animationPeriod);
            circP1Plus1.startAnimation(animation);
            Animator alpha = alphaAnimator(circP1Plus1,200,"fadeout2");
            alpha.start();

            //ライフ変動
            p1PsnInt += creasePsnNum;
            p1EngInt += creaseEngNum;
            p1Psn_txt.setText(String.valueOf(p1PsnInt));//ライフ
            p1Eng_txt.setText(String.valueOf(p1EngInt));//ライフ
            if (p1PsnDriver < 2) {
                p1PsnDriver++;
            } else {
                p1PsnDriver = 0;
            }
            if (p1EngDriver < 2) {
                p1EngDriver++;
            } else {
                p1EngDriver = 0;
            }
            fn_p1PsnFade(p1PsnInt);
            //fn_p1LifeFade(p1LifeIntTemp);
            System.out.println("p1PsnInt:" + p1PsnInt);

            /*
            if (p1LifeDistIntArr.size() == 0) {
                //初回
                p1LifeDistIntArr.add(p1LifeDistInt);
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }
            //もしDistIntが０（移動してない）場合は変わりに-5を入れる
            if (p1LifeDistInt == 0) {
                p1LifeIntTemp += creaseNum;
                p1LifeDistInt = +creaseNum;
                p1GlobalTmp += creaseNum;
            } else {
                p1GlobalTmp += p1LifeDistInt;
            }
            p1LifeDistIntArr.add(p1LifeDistInt);
            //値が最初と変わってない限り
            if (p1LifeIntTemp != p1LifeIntTemp2) {
                fn_p1LifeFade(p1LifeIntTemp);
            }

            p1LifeInt = p1LifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る
            //ライフ変動
            p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
            if (p1LifeDriver < 3) {
                p1LifeDriver++;
            } else {
                p1LifeDriver = 0;
            }
            //動くようにはなったけど、今度リストが追加されなくなってしまった。
            //p1countの扱いと動作の整理しないといかんなこれは
            distTmp = p1LifeDistInt;

            p1LifeInt = p1LifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る

            System.out.println("iffff     p1GlobalTmp " + p1GlobalTmp);
            p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouchY " + firstTouchY
                    + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    + " \n p1LifeDistance: " + p1LifeDistance
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n p1GlobalTmp " + p1GlobalTmp
                    + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
            mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
            delayedUpdate = new Runnable() {
                public void run() {
                    if(p1GlobalTmp!=0) {
                        fn_lifeDriver(p1GlobalTmp, plusminus);
                        p1GlobalTmp = 0;
                        tmpState = "";
                    }
                }
            };
            mHandler.postDelayed(delayedUpdate, 1000);
            //これ意味ある？
            p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            */
        }
        public void actionMove(MotionEvent motionEvent){
            System.out.println("actionMove \n");
            p1LifeDistance = firstTouchY - motionEvent.getY(0);//距離
            p1LifeDistInt = (int) p1LifeDistance / 100;//整数で

            if (p1LifeDistIntArr.size() == 0) {
                //初回
                p1LifeDistIntArr.add(p1LifeDistInt);

                p1txt2.setText("初回 \n distTmp " + distTmp);
                p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                        + " \n p1LifeDistInt: " + p1LifeDistInt
                        + " \n distTmp: " + distTmp
                        + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
                );
                distTmp = p1LifeDistInt;
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }

            //配列の中身が一つ以上あり、distTmpに値が入っている状態
            p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n distTmp: " + distTmp
                    + " \n p1LifeDistIntArr.size() " + p1LifeDistIntArr.size()
            );
            if (p1LifeDistInt != distTmp) {
                p1LifeDistIntArr.add(p1LifeDistInt);
                if (p1LifeDriver < 3) {
                    p1LifeDriver++;
                } else {
                    p1LifeDriver = 0;
                }
                //省略できそうだけどめんどそう
                fn_p1LifeFade(p1LifeIntTemp);
                //動くようにはなったけど、今度リストが追加されなくなってしまった。
                //p1countの扱いと動作の整理しないといかんなこれは
                distTmp = p1LifeDistInt;
                p1txt2.setText("p1LifeDistInt!=distTmp \n distTmp " + distTmp);
            }
            //値が違った場合、違い続けるってことか
            if (p1LifeDistIntArr.size() > 2) {
                //2より大きくなったらリムーブ
                p1LifeDistIntArr.remove(0);
                p1txt2.setText("2より大きくなったらリムーブ \n distTmp " + distTmp);
            }

            p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
            p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置
        }
        public void setVariables(){
            creasePsnNum = 1;creaseEngNum = 0;
        }
    }
    //ライフが変動した時の半透明な文字
    public void fn_p1LifeFade(int lifeIntTemp){
        float toTY = -30;
        switch (p1LifeDriver) {
            case 0:
                fn_translation(p1Life_txt1, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Life_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p1Life_txt2, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Life_txt2.setText(""+lifeIntTemp);//p1LifeInt + p1LifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p1Life_txt3, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Life_txt3.setText(""+lifeIntTemp);
                break;
            case 3:
                fn_translation(p1Life_txt4, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Life_txt4.setText(""+lifeIntTemp);
                break;
                /*
            case 4:
                fn_translation(p1Life_txt5, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Life_txt5.setText("" + (lifeIntTemp));
                break;
                */
        }
    }
    //ライフが変動した時の半透明な文字
    public void fn_p1PsnFade(int lifeIntTemp){
        float toTY = -30;
        switch (p1PsnDriver) {
            case 0:
                fn_translation(p1Psn_txt1, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p1Psn_txt2, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt2.setText(""+lifeIntTemp);//p1LifeInt + p1LifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p1Psn_txt3, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt3.setText(""+lifeIntTemp);
                break;
        }
    }
    private void fn_p1LifeHisMove1(ArrayList<String> arr) {
        int baseNum=+780;
        switch(p1LifeHisDriver){
            case 0:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory1.setText( arr.get(0) );
                break;
            case 1:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory2.setText( arr.get(1) );
                break;
            case 2:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory3.setText( arr.get(2) );
                break;
            case 3:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory4.setText( arr.get(3) );
                break;
            case 4:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory5.setText( arr.get(4) );
                break;
            case 5:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory6.setText( arr.get(5) );
                break;
            case 6:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory7.setText( arr.get(6) );
                break;
            case 7:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory8.setText( arr.get(7) );
                break;
            case 8:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory9.setText( arr.get(8) );
                break;
        }

        Animator animator = rotateAnimator(mButton);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void fn_p1LifeHisMove2(ArrayList<String> arr) {
        int baseNum=+780;
        switch(p1LifeHisDriver){
            case 9:
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory2.setText( arr.get(1) );
                p1lifeHistory3.setText( arr.get(2) );
                p1lifeHistory4.setText( arr.get(3) );
                p1lifeHistory5.setText( arr.get(4) );
                p1lifeHistory6.setText( arr.get(5) );
                p1lifeHistory7.setText( arr.get(6) );
                p1lifeHistory8.setText( arr.get(7) );
                p1lifeHistory9.setText( arr.get(8) );
                p1lifeHistory1.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 0:
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory3.setText( arr.get(1) );
                p1lifeHistory4.setText( arr.get(2) );
                p1lifeHistory5.setText( arr.get(3) );
                p1lifeHistory6.setText( arr.get(4) );
                p1lifeHistory7.setText( arr.get(5) );
                p1lifeHistory8.setText( arr.get(6) );
                p1lifeHistory9.setText( arr.get(7) );
                p1lifeHistory1.setText( arr.get(8) );
                p1lifeHistory2.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 1:
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory4.setText( arr.get(1) );
                p1lifeHistory5.setText( arr.get(2) );
                p1lifeHistory6.setText( arr.get(3) );
                p1lifeHistory7.setText( arr.get(4) );
                p1lifeHistory8.setText( arr.get(5) );
                p1lifeHistory9.setText( arr.get(6) );
                p1lifeHistory1.setText( arr.get(7) );
                p1lifeHistory2.setText( arr.get(8) );
                p1lifeHistory3.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 2:
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory5.setText( arr.get(1) );
                p1lifeHistory6.setText( arr.get(2) );
                p1lifeHistory7.setText( arr.get(3) );
                p1lifeHistory8.setText( arr.get(4) );
                p1lifeHistory9.setText( arr.get(5) );
                p1lifeHistory1.setText( arr.get(6) );
                p1lifeHistory2.setText( arr.get(7) );
                p1lifeHistory3.setText( arr.get(8) );
                p1lifeHistory4.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 3:
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p1lifeHistory6.setText( arr.get(1) );
                p1lifeHistory7.setText( arr.get(2) );
                p1lifeHistory8.setText( arr.get(3) );
                p1lifeHistory9.setText( arr.get(4) );
                p1lifeHistory1.setText( arr.get(5) );
                p1lifeHistory2.setText( arr.get(6) );
                p1lifeHistory3.setText( arr.get(7) );
                p1lifeHistory4.setText( arr.get(8) );
                p1lifeHistory5.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 4:
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory7.setText( arr.get(1) );
                p1lifeHistory8.setText( arr.get(2) );
                p1lifeHistory9.setText( arr.get(3) );
                p1lifeHistory1.setText( arr.get(4) );
                p1lifeHistory2.setText( arr.get(5) );
                p1lifeHistory3.setText( arr.get(6) );
                p1lifeHistory4.setText( arr.get(7) );
                p1lifeHistory5.setText( arr.get(8) );
                p1lifeHistory6.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 5:
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory8.setText( arr.get(1) );
                p1lifeHistory9.setText( arr.get(2) );
                p1lifeHistory1.setText( arr.get(3) );
                p1lifeHistory2.setText( arr.get(4) );
                p1lifeHistory3.setText( arr.get(5) );
                p1lifeHistory4.setText( arr.get(6) );
                p1lifeHistory5.setText( arr.get(7) );
                p1lifeHistory6.setText( arr.get(8) );
                p1lifeHistory7.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 6:
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory9.setText( arr.get(1) );
                p1lifeHistory1.setText( arr.get(2) );
                p1lifeHistory2.setText( arr.get(3) );
                p1lifeHistory3.setText( arr.get(4) );
                p1lifeHistory4.setText( arr.get(5) );
                p1lifeHistory5.setText( arr.get(6) );
                p1lifeHistory6.setText( arr.get(7) );
                p1lifeHistory7.setText( arr.get(8) );
                p1lifeHistory8.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 7:
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory1.setText( arr.get(1) );
                p1lifeHistory2.setText( arr.get(2) );
                p1lifeHistory3.setText( arr.get(3) );
                p1lifeHistory4.setText( arr.get(4) );
                p1lifeHistory5.setText( arr.get(5) );
                p1lifeHistory6.setText( arr.get(6) );
                p1lifeHistory7.setText( arr.get(7) );
                p1lifeHistory8.setText( arr.get(8) );
                p1lifeHistory9.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;
            case 8:
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p1lifeHistory2.setText( arr.get(1) );
                p1lifeHistory3.setText( arr.get(2) );
                p1lifeHistory4.setText( arr.get(3) );
                p1lifeHistory5.setText( arr.get(4) );
                p1lifeHistory6.setText( arr.get(5) );
                p1lifeHistory7.setText( arr.get(6) );
                p1lifeHistory8.setText( arr.get(7) );
                p1lifeHistory9.setText( arr.get(8) );
                p1lifeHistory1.setText( arr.get(9) );
                p1txt.setText("p1lifeHistory1 " + p1lifeHistory1.getText()
                        + "\n p1lifeHistory2 " + p1lifeHistory2.getText()
                        + "\n p1lifeHistory3 " + p1lifeHistory3.getText()
                        + "\n p1lifeHistory4 " + p1lifeHistory4.getText()
                        + "\n p1lifeHistory5 " + p1lifeHistory5.getText()
                        + "\n p1lifeHistory6 " + p1lifeHistory6.getText()
                        + "\n p1lifeHistory7 " + p1lifeHistory7.getText()
                        + "\n p1lifeHistory8 " + p1lifeHistory8.getText()
                        + "\n p1lifeHistory9 " + p1lifeHistory9.getText()
                );
                break;

                /*

                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadeout");
                fn_translation(p1lifeHistory2,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory3,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory4,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory5,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory6,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory7,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory8,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p1lifeHistory9,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadein");
                 */
        }

        Animator animator = rotateAnimator(mButton);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
    private Animator fn_translation(View target, float fromTY, float toTY,long duration) {
/*
        float fromY;//開始地点
        float toY;//到達地点

        if (appear) {
            toY = fromTY;
            fromY = toTY;
        } else {
            toY = toTY;
            fromY = fromTY;
        }*/

        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX",0,0);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY",fromTY,toTY);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(duration);
        return oa;
    }
    private Animator translateAnimator(View target, float fromTX, float toTX, float fromTY, float toTY,long duration) {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX",fromTX,toTX);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY",fromTY,toTY);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(duration);
        return oa;
    }

    private Animator translateAnimatorToXY(View target, float toTX, float toTY,long duration) {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX",0,toTX);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY",0,toTY);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(duration);
        return oa;
    }
    private Animator rotateAnimator(View target) {
        ObjectAnimator  oa = ObjectAnimator.ofFloat(target, "rotation", 0f,360f);
        oa.setDuration(1000);
        return oa;
    }
    private Animator scaleAnimator(View target,float from,float to,long duration) {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("scaleX",from,to);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("scaleY",from,to);
        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(duration);
        return oa;
    }
    private Animator scaleAnimatorToXY(View target, float toX, float toY,long duration) {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("ScaleX",1,toX);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("ScaleY",1,toY);
        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(target,xHolder,yHolder);
        oa.setDuration(duration);
        return oa;
    }

    private Animator alphaAnimator(View target,long duration, String fade) {
        float fromAlpha=0.1f;
        float toAlpha=0.1f;
        if (fade=="fadein") {
            fromAlpha = 0.0f;
            toAlpha = 1f;
        } else if(fade=="fadeout") {
            fromAlpha = 1f;
            toAlpha = 0.0f;
        }else if(fade=="visible"){
            fromAlpha = 1f;
            toAlpha = 1f;
        }else if(fade=="invisible"){
            fromAlpha = 0f;
            toAlpha = 0f;
        }else if(fade=="fadein2"){
            fromAlpha = 0.0f;
            toAlpha = 0.6f;
        }else if(fade=="fadeout2"){
            fromAlpha = 0.6f;
            toAlpha = 0.0f;
        }
        ObjectAnimator oa = ObjectAnimator.ofFloat(target,"alpha",fromAlpha,toAlpha);
        oa.setDuration(duration);
        return oa;
    }

    //appearで出現 0から1
    //移動Yだけ
    private void fn_translation(View target, float formTY, float toTY, long duration, String fade) {
        Animator alpha = alphaAnimator(target,duration,fade);
        alpha.setInterpolator(new DecelerateInterpolator());

        Animator translate = fn_translation(target,formTY,toTY,duration);
        translate.setInterpolator(new OvershootInterpolator());

        AnimatorSet set = new AnimatorSet();
        //set.setStartDelay(delay);
        set.playTogether(alpha,translate);
        set.start();
    }
/*

                AnimationCircle animation = new AnimationCircle(circ, 20,0);
                // アニメーションの起動期間を設定
                animation.setDuration(animationPeriod);
                circ.startAnimation(animation);
 */
        /*
    private void fn_animationCircle(Circle target, float formTY, float toTY,float radius,long duration, String fade) {
        AnimationCircle animationCircle = new AnimationCircle(target, radius,0);
        animationCircle.setInterpolator(new DecelerateInterpolator());
        animationCircle.setDuration(duration);
        target.startAnimation(animationCircle);

        animation.setDuration(animationPeriod);
        circ.startAnimation(animation);


        Animator alpha = alphaAnimator(target,duration,fade);
        alpha.setInterpolator(new DecelerateInterpolator());

        Animator translate = fn_translation(target,formTY,toTY,duration);
        translate.setInterpolator(new OvershootInterpolator());


        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("ScaleX",1,toX);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("ScaleY",1,toY);

        AnimatorSet set = new AnimatorSet();
        //set.setStartDelay(delay);
        set.playTogether(alpha,translate);
        set.start();
    }
        */

    //
    /*
+ AccelerateDecelerateInterpolator
なにも設定しなかった場合のデフォルトの挙動 加速したのちに減速してアニメーションを終える
+ DecelerateInterpolator
最初に加速してだんだん減速する
+ AccelerateInterpolator
だんだん加速する
+ BounceInterpolator
バウンドするように加速減速逆方向移動を繰り返して目標の値に到達する
+ OvershootInterpolator
目標の値を一旦オーバーしてから目的の値に到達する
 */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        rectP1Plus1 = new RectF(p1plus1.getLeft(),p1plus1.getTop(),p1plus1.getRight(),p1plus1.getBottom());
        rectP1Plus5 = new RectF(p1plus5.getLeft(),p1plus5.getTop(),p1plus5.getRight(),p1plus5.getBottom());
        rectP1Minus1 = new RectF(p1minus1.getLeft(),p1minus1.getTop(),p1minus1.getRight(),p1minus1.getBottom());
        rectP1Minus5 = new RectF(p1minus5.getLeft(),p1minus5.getTop(),p1minus5.getRight(),p1minus5.getBottom());

        rectP1PsnPlus = new RectF(p1PsnPlus.getLeft(),p1PsnPlus.getTop(),p1PsnPlus.getRight(),p1PsnPlus.getBottom());
        rectP1PsnMinus = new RectF(p1PsnMinus.getLeft(),p1PsnMinus.getTop(),p1PsnMinus.getRight(),p1PsnMinus.getBottom());
        rectP1EngPlus = new RectF(p1EngPlus.getLeft(),p1EngPlus.getTop(),p1EngPlus.getRight(),p1EngPlus.getBottom());
        rectP1EngMinus = new RectF(p1EngMinus.getLeft(),p1EngMinus.getTop(),p1EngMinus.getRight(),p1EngMinus.getBottom());
    }
}
