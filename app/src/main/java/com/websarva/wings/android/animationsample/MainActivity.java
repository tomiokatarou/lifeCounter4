package com.websarva.wings.android.animationsample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.PorterDuff;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

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
    TextView obj1;
    Button p1plus1,p1plus5,p1minus1,p1minus5;
    Button resetBtn;

    //counterFragment------------------------------------------------
    Button fragmentTestBtn;
    public ArrayList<Integer> p1CounterArr;


    int p1LifeInt = 20;

    //historyDriver
    int p1LifeDriver = 0;//-1
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
    String white      = "#FFFFFF";
    String blue     = "#2196F3";
    String yellow   = "#FFEB3B";

    //----------------------------------------------------------------------------------------------
    private Circle circ;
    private int endAngle = 0;
    private int animationPeriod = 2000;
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
        //ボタンの生成
        p1plus1 = (Button) this.findViewById(R.id.p1plus1);
        p1plus5 = (Button) this.findViewById(R.id.p1plus5);
        p1minus1 = (Button) this.findViewById(R.id.p1minus1);
        p1minus5 = (Button) this.findViewById(R.id.p1minus5);
        resetBtn = (Button) this.findViewById(R.id.resetBtn);

        //デバッグ用

        fragmentTestBtn = (Button) this.findViewById(R.id.button);

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

        scaleAnimatorToXY(container,0,0,1000);

        p1LifeList = new ArrayList<>();
        p1LifeListTmp = new ArrayList<>();

        //----------------------------------------------------------------------------------------------
        // 88%に角度を合わせる
        endAngle = 88*360/100;

        circ = (Circle) findViewById(R.id.circle);
        //----------------------------------------------------------------------------------------------

        if(savedInstanceState == null) {
            fragmentTestBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    //BackStackを設定
                    fragmentTransaction.addToBackStack("p1CntFragment");

                    //パラメータを設定
                    fragmentTransaction.replace(R.id.container, p1CounterFragment.newInstance(1));

                    //fragmentManager.popBackStack("p1CntFragment",0);
                    fragmentTransaction.commit();

                    translateAnimatorToXY(container,500,500,1000);

                    return false;
                }
            });
        }

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
                        p1txt.setText("p1txt ACTION_POINTER_DOWN \n" + " firstTouch " + firstTouch
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
                        p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouch " + firstTouch
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
            }
        };
        p1plus1.setOnTouchListener(p1plus1TouchListener);
        buttonListener p1plus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 5;
            }
        };
        p1plus5.setOnTouchListener(p1plus5TouchListener);
        buttonListener p1minus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -1;
            }
        };
        p1minus1.setOnTouchListener(p1minus1TouchListener);
        buttonListener p1minus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -5;
            }
        };
        p1minus5.setOnTouchListener(p1minus5TouchListener);

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

                                //指定したタグに戻る操作
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.popBackStack("p1CntFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

                        /*
                        間違えて触ってしまった場合　値が変化してない場合は何も起こさないようにしたいけど
                        タッチしただけなのか、タッチしたつもりだけどmove通ってしまったのかは判断がつかない
                        したがって、moveを通ってもとの値に戻そうとしても無駄　という仕様になる
                        つまり、どんな条件でもフェードは起こる
                        値が変化してない場合はマイナスされる

                        moveで値を変化させて離した時だけフェードが起こらないようにしないといけない
                         */
/*                                if(p1LifeDistInt > 0)plusminus=1;
                                else plusminus=-1;

                                fn_lifeDriver(p1GlobalTmp, plusminus);
                                p1GlobalTmp=0;
                                tmpState="";
                                System.out.println("p1plus1 p1GlobalTmp " + p1GlobalTmp);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        //これ意味ある？
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！


                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        System.out.println("move");
                        tmpState="move";
                        p1LifeDistance = firstTouch - motionEvent.getY(0);//距離
                        p1LifeDistInt = (int) p1LifeDistance / 100;//整数で
                        System.out.println("ACTION_MOVE p1LifeDistInt " + p1LifeDistInt);

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            distTmp = p1LifeDistInt;
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }
                        //配列の中身が一つ以上あり、distTmpに値が入っている状態
                        //tmpの値と違う時＝値が変化した時
                        if (p1LifeDistInt != distTmp) {
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            System.out.println("p1LifeDistInt " + p1LifeDistInt);
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
                        }
                    }
                    //値が違った場合、違い続けるってことか
                    if (p1LifeDistIntArr.size() > 2) {
                        //2より大きくなったらリムーブ
                        p1LifeDistIntArr.remove(0);
                    }

                    p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                    p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置

                    break;
                }
                return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
            */

/*
        p1plus5.setOnTouchListener(new View.OnTouchListener() {
            float firstTouch = 0;
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int p1LifeIntTemp2 = p1LifeInt;//最初の値
            int p1LifeDistInt;//距離を整数にしたもので、ライフの増減の値としても使っている
            float p1LifeDistance;//finalは変更を許可しない
            float s = 3f;
            int plusminus = 1;
            int distTmp=0;
            String tmpState="";

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("onTouch lifeDistInt " + p1LifeDistInt);
                p1LifeIntTemp2 = p1LifeIntTemp;

                //p1txt.setText("p1plus5 onTouch \n");//テキストの編集
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        //p1txt.setText("p1plus5 ACTION_POINTER_DOWN \n");//テキストの編集
                        //画面がタッチされた場合の処理
                        int pointerIndex = motionEvent.getActionIndex();
                        firstTouch = motionEvent.getY(pointerIndex);
                        p1LifeDistance = 0;
                        p1LifeDistInt = 0;//距離を整数にしたもので、ライフの増減の値としても使っている
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }

                        //もしDistIntが０（移動してない）場合は変わりに-5を入れる
                        if(p1LifeDistInt==0){
                            p1LifeIntTemp+=5;
                            p1LifeDistInt=+5;
                            p1GlobalTmp+=5;
                        }else{
                            p1GlobalTmp+=p1LifeDistInt;
                        }
                        p1LifeDistIntArr.add(p1LifeDistInt);
                        //省略できそうだけどめんどそう
                        //値が変わってるときだけ

                        //値が最初と変わってない限り
                        if(p1LifeIntTemp!=p1LifeIntTemp2){
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
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        //一定時間経ってから実行される　ライフの増減、ヒストリーのアニメーションなどを実行する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                if(p1LifeDistInt > 0)plusminus=1;
                                else plusminus=-1;

                                fn_lifeDriver(p1GlobalTmp, plusminus);
                                p1GlobalTmp=0;
                                tmpState="";
                                System.out.println("p1plus5 p1GlobalTmp " + p1GlobalTmp);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        //これ意味ある？
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        System.out.println("move");
                        tmpState="move";
                        p1LifeDistance = firstTouch - motionEvent.getY(0);//距離
                        p1LifeDistInt = (int) p1LifeDistance / 100;//整数で
                        System.out.println("ACTION_MOVE p1LifeDistInt " + p1LifeDistInt);

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            distTmp = p1LifeDistInt;
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }
                        //配列の中身が一つ以上あり、distTmpに値が入っている状態
                        //tmpの値と違う時＝値が変化した時
                        if (p1LifeDistInt != distTmp) {
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            System.out.println("p1LifeDistInt " + p1LifeDistInt);
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
                        }
                    }
                    //値が違った場合、違い続けるってことか
                    if (p1LifeDistIntArr.size() > 2) {
                        //2より大きくなったらリムーブ
                        p1LifeDistIntArr.remove(0);
                    }

                    p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                    p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置

                    break;
                }
                return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
        });
        p1minus1.setOnTouchListener(new View.OnTouchListener() {
            float firstTouch = 0;
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int p1LifeIntTemp2 = p1LifeInt;//最初の値
            int p1LifeDistInt;//距離を整数にしたもので、ライフの増減の値としても使っている
            float p1LifeDistance;//finalは変更を許可しない
            float s = 3f;
            int plusminus = 1;
            int distTmp=0;
            String tmpState="";

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("onTouch lifeDistInt " + p1LifeDistInt);
                p1LifeIntTemp2 = p1LifeIntTemp;
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        //p1txt.setText("p1minus1 ACTION_POINTER_DOWN \n");//テキストの編集
                        //画面がタッチされた場合の処理
                        int pointerIndex = motionEvent.getActionIndex();
                        firstTouch = motionEvent.getY(pointerIndex);
                        p1LifeDistance = 0;
                        p1LifeDistInt = 0;//距離を整数にしたもので、ライフの増減の値としても使っている
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }

                        //もしDistIntが０（移動してない）場合は変わりに-5を入れる
                        if(p1LifeDistInt==0){
                            p1LifeIntTemp-=1;
                            p1LifeDistInt=-1;
                            p1GlobalTmp-=1;
                        }else{
                            p1GlobalTmp+=p1LifeDistInt;
                        }
                        p1LifeDistIntArr.add(p1LifeDistInt);
                        //省略できそうだけどめんどそう
                        //値が変わってるときだけ

                        //値が最初と変わってない限り
                        if(p1LifeIntTemp!=p1LifeIntTemp2){
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
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        //一定時間経ってから実行される　ライフの増減、ヒストリーのアニメーションなどを実行する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                if(p1LifeDistInt > 0)plusminus=1;
                                else plusminus=-1;

                                fn_lifeDriver(p1GlobalTmp, plusminus);
                                p1GlobalTmp=0;
                                tmpState="";
                                System.out.println("p1minus1 p1GlobalTmp " + p1GlobalTmp);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        //これ意味ある？
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        System.out.println("move");
                        tmpState="move";
                        p1LifeDistance = firstTouch - motionEvent.getY(0);//距離
                        p1LifeDistInt = (int) p1LifeDistance / 100;//整数で
                        System.out.println("ACTION_MOVE p1LifeDistInt " + p1LifeDistInt);

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            distTmp = p1LifeDistInt;
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }
                        //配列の中身が一つ以上あり、distTmpに値が入っている状態
                        //tmpの値と違う時＝値が変化した時
                        if (p1LifeDistInt != distTmp) {
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            System.out.println("p1LifeDistInt " + p1LifeDistInt);
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
                        }
                    }
                    //値が違った場合、違い続けるってことか
                    if (p1LifeDistIntArr.size() > 2) {
                        //2より大きくなったらリムーブ
                        p1LifeDistIntArr.remove(0);
                    }

                    p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                    p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置

                    break;
                }
                return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
        });
        p1minus5.setOnTouchListener(new View.OnTouchListener() {
            float firstTouch = 0;
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int p1LifeIntTemp2 = p1LifeInt;//最初の値
            int p1LifeDistInt;//距離を整数にしたもので、ライフの増減の値としても使っている
            float p1LifeDistance;//finalは変更を許可しない
            float s = 10f;
            int plusminus = 1;
            int distTmp=0;
            String tmpState="";

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("onTouch lifeDistInt " + p1LifeDistInt);
                p1LifeIntTemp2 = p1LifeIntTemp;
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        //p1txt.setText("p1minus5 ACTION_POINTER_DOWN \n");//テキストの編集
                        //画面がタッチされた場合の処理
                        int pointerIndex = motionEvent.getActionIndex();
                        firstTouch = motionEvent.getY(pointerIndex);
                        p1LifeDistance = 0;
                        p1LifeDistInt = 0;//距離を整数にしたもので、ライフの増減の値としても使っている
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                        distTmp=0;
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        //タッチが離れた場合の処理
                        //int pointerIndex = motionEvent.getActionIndex();


                        //tmpの値と違う時＝値が変化した時
                        //値が同じ＝移動してないとき
                        //if (p1LifeDistInt == 0) {

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }

                        //もしDistIntが０（移動してない）場合は変わりに-5を入れる
                        if(p1LifeDistInt==0){
                            p1LifeIntTemp-=5;
                            p1LifeDistInt=-5;
                            p1GlobalTmp-=5;
                        }else{
                            p1GlobalTmp+=p1LifeDistInt;
                        }
                        p1LifeDistIntArr.add(p1LifeDistInt);
                        //省略できそうだけどめんどそう
                        //値が変わってるときだけ

                        //値が最初と変わってない限り
                        if(p1LifeIntTemp!=p1LifeIntTemp2){
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
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        //一定時間経ってから実行される　ライフの増減、ヒストリーのアニメーションなどを実行する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                if(p1LifeDistInt > 0)plusminus=1;
                                else plusminus=-1;

                                fn_lifeDriver(p1GlobalTmp, plusminus);
                                p1GlobalTmp=0;
                                tmpState="";
                                System.out.println("p1minus5 p1GlobalTmp " + p1GlobalTmp);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        //これ意味ある？
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        System.out.println("move");
                        tmpState="move";
                        p1LifeDistance = firstTouch - motionEvent.getY(0);//距離
                        p1LifeDistInt = (int) p1LifeDistance / 100;//整数で
                        System.out.println("ACTION_MOVE p1LifeDistInt " + p1LifeDistInt);

                        if (p1LifeDistIntArr.size() == 0) {
                            //初回
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            distTmp = p1LifeDistInt;
                            //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
                        }
                        //配列の中身が一つ以上あり、distTmpに値が入っている状態
                        //tmpの値と違う時＝値が変化した時
                        if (p1LifeDistInt != distTmp) {
                            p1LifeDistIntArr.add(p1LifeDistInt);
                            System.out.println("p1LifeDistInt " + p1LifeDistInt);
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
                        }
                    }
                    //値が違った場合、違い続けるってことか
                    if (p1LifeDistIntArr.size() > 2) {
                        //2より大きくなったらリムーブ
                        p1LifeDistIntArr.remove(0);
                    }

                    p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                    p1LifeIntTemp = p1LifeInt + p1LifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置

                    break;
                }
                return false ;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
            }
        });

*/
        /*
        p1plus1.setOnClickListener(new View.OnClickListener() {
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int plusminus = 1;
            int distTmp=0;
            float sx = 10f,sy=10f;

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            @Override
            public void onClick(View view) {
                System.out.println("p1plus1");

                p1LifeIntTemp = p1LifeInt;//値を渡す

                //最初にクリックされた時間を取得し、そこから何秒か以内に再びクリックされた場合は
                //その回数を数えておく

                p1LifeIntTemp = p1LifeInt + 1;//一時的なライフの数値。基本的にライフの数値と同じであるべきなはず
                p1Life_txt.setText(String.valueOf(p1LifeIntTemp));//テキストの編集
                //距離は測ってないのでif文は不要・・・のはず
                //if (p1BtnClickCnt != distTmp) {
                p1LifeDistIntArr.add(p1BtnClickCnt);
                if (p1LifeDriver < 4) {
                    p1LifeDriver++;
                } else {
                    p1LifeDriver = 0;
                }
                switch (p1LifeDriver) {
                    case 0:
                        fn_translation(p1Life_txt1, 0, 0, sx, sy, 800, "fadeout2");
                        p1Life_txt1.setText("" + (p1LifeIntTemp));//ここは関係ないはず
                        break;
                    case 1:
                        fn_translation(p1Life_txt2, 0, 0, sx, sy, 800, "fadeout2");
                        p1Life_txt2.setText("" + (p1LifeIntTemp));//p1LifeInt + p1LifeDistIntArr.get(0)
                        break;
                    case 2:
                        fn_translation(p1Life_txt3, 0, 0, sx, sy, 800, "fadeout2");
                        p1Life_txt3.setText("" + (p1LifeIntTemp));
                        break;
                    case 3:
                        fn_translation(p1Life_txt4, 0, 0, sx, sy, 800, "fadeout2");
                        p1Life_txt4.setText("" + (p1LifeIntTemp));
                        break;
                    case 4:
                        fn_translation(p1Life_txt5, 0, 0, sx, sy, 800, "fadeout2");
                        p1Life_txt5.setText("" + (p1LifeIntTemp));
                        break;
                }
                //動くようにはなったけど、今度リストが追加されなくなってしまった。
                //p1countの扱いと動作の整理しないといかんなこれは
                distTmp = p1BtnClickCnt;
                //}


                //タッチが離れた場合の処理
                p1LifeInt += 1;//値を渡し返す　ここに入れないと値が巻き戻る
                mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                delayedUpdate = new Runnable() {
                    public void run(){
                        fn_lifeDriver(p1BtnClickCnt,plusminus);
                        p1BtnClickCnt =0;
                    }
                };
                mHandler.postDelayed(delayedUpdate, 1000);

            }
        });
*/

                /*
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        //タッチが離れた場合の処理
                        p1LifeInt = p1LifeIntTemp+1;//値を渡し返す　ここに入れないと値が巻き戻るmHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        delayedUpdate = new Runnable() {
                            public void run(){
                                fn_lifeDriver(p1BtnClickCnt,plusminus);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                }
                return true;
            */

/*
        p1plus1.setOnTouchListener(new View.OnTouchListener() {
            int p1LifeIntTemp = 0;//指を動かしている間の値
            int plusminus = 1;
            int distTmp=0;
            int p1LifeDistInt=100;

            ArrayList<Integer> p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        p1LifeDistInt = 0;
                        p1LifeIntTemp = p1LifeInt;//値を渡す
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        //タッチが離れた場合の処理
                        p1LifeInt = p1LifeIntTemp+1;//値を渡し返す　ここに入れないと値が巻き戻るmHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        delayedUpdate = new Runnable() {
                            public void run(){
                                fn_lifeDriver(p1LifeDistInt,plusminus);
                            }
                        };
                        mHandler.postDelayed(delayedUpdate, 1000);
                        p1LifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                }
                return true;
            }
        });
        */

                        /*
                        p1LifeDistance
                        p1LifeDistInt
                        p1LifeIntTemp
                        p1LifeDistIntArr
                         */
                        /*
                        p1txt.setText("p1txt ACTION_POINTER_DOWN \n"+" firstTouch "+firstTouch
                                +"\n p1LifeInt:"+p1LifeInt+"  p1LifeIntTemp: " + p1LifeIntTemp
                                +" \n p1LifeDistance: "+p1LifeDistance
                                +" \n p1LifeDistInt: "+p1LifeDistInt
                                +" \n p1LifeList.size() "+p1LifeList.size()
                                +" \n p1LifeDistIntArr.size "+p1LifeDistIntArr.size());//テキストの編集
                                */
                        /*
                        p1LifeDistIntArr.add(p1LifeDistInt);
                        if(p1LifeDistIntArr.size()>4) {
                            //p1txt.setText("p1LifeDistInt "+p1LifeDistInt+" (0) "+p1LifeDistIntArr.get(0) +" (1) " +p1LifeDistIntArr.get(1)  +" (2): " +p1LifeDistIntArr.get(2)  +" (3): " +p1LifeDistIntArr.get(3) );//テキストの編集
                            p1LifeDistIntArr.remove(0);
                            if(p1LifeDistIntArr.get(0)!=p1LifeDistIntArr.get(1)){
                                if(p1LifeDistIntArr.get(0)<p1LifeDistIntArr.get(1)){plusminus = -1;}else{plusminus=1;}
                                if (p1countLife < 5) {
                                    p1countLife++;
                                } else {
                                    p1countLife = 0;
                                }
                                switch (p1countLife) {
                                    case 0:
                                        fn_translation(p1Life_txt1, 0, plusminus*800, sx, sy, 800, "fadeout2");
                                        p1Life_txt1.setText(""+(p1LifeInt));//ここは関係ないはず
                                        break;
                                    case 1:
                                        fn_translation(p1Life_txt2, 0, plusminus*800, sx, sy, 800, "fadeout2");
                                        p1Life_txt2.setText(""+(p1LifeInt));//p1LifeInt + p1LifeDistIntArr.get(0)
                                        break;
                                    case 2:
                                        fn_translation(p1Life_txt3, 0, plusminus*800, sx, sy, 800, "fadeout2");
                                        p1Life_txt3.setText(""+(p1LifeInt));
                                        break;
                                    case 3:
                                        fn_translation(p1Life_txt4, 0, plusminus*800, sx, sy, 800, "fadeout2");
                                        p1Life_txt4.setText(""+(p1LifeInt));
                                        break;
                                    case 4:
                                        fn_translation(p1Life_txt5, 0, plusminus*800, sx, sy, 800, "fadeout2");
                                        p1Life_txt5.setText(""+(p1LifeInt));
                                        break;
                                }
                                break;

                            }
                        }
                                */
                              /*
                                if(p1LifeDistInt==0){
                                }else if(plusminus==1){
                                    //p1LifeList.add(String.format(Locale.US, p1LifeInt + "", number + 1));
                                    p1LifeList.add( p1LifeInt + " (" + p1LifeDistInt + ")" );
                                    if (p1LifeHisDriver < 4) {
                                        p1LifeHisDriver++;
                                    } else {
                                        p1LifeHisDriver = 0;
                                    }
                                    //リストに突っ込んだ状態で動かす
                                    if (isP1LifeHisMax) {
                                        fn_p1LifeHisMove2(p1LifeList);
                                    } else {
                                        fn_p1LifeHisMove1(p1LifeList);
                                    }
                                }else{
                                    //p1LifeList.add(String.format(Locale.US, p1LifeInt + "", number + 1));
                                    p1LifeList.add( p1LifeInt + " (+" + p1LifeDistInt + ")" );
                                    if (p1LifeHisDriver < 4) {
                                        p1LifeHisDriver++;
                                    } else {
                                        p1LifeHisDriver = 0;
                                    }
                                    //リストに突っ込んだ状態で動かす
                                    if (isP1LifeHisMax) {
                                        fn_p1LifeHisMove2(p1LifeList);
                                    } else {
                                        fn_p1LifeHisMove1(p1LifeList);
                                    }
                                }*/
        //p1txt.setText("number " + number);//テキストの編集
        //p1txt.setText("arr.get(0) " + p1LifeList.get(0));//テキストの編集
                                /*
                                if (p1LifeHisDriver < 4) {
                                    p1LifeHisDriver++;
                                    p1txt.setText("p1LifeHisDriver "+p1LifeHisDriver
                                            +"\n isP1LifeHisMax " +isP1LifeHisMax
                                            +"\n p1LifeList.size() "+p1LifeList.size()
                                    );
                                } else {
                                    p1LifeHisDriver = 0;
                                    p1txt.setText("p1LifeHisDriver "+p1LifeHisDriver
                                            +"\n isP1LifeHisMax " +isP1LifeHisMax
                                            +"\n p1LifeList.size() "+p1LifeList.size()
                                    );
                                }*/
                                /*
                                if (!isP1LifeHisMax && p1LifeHisDriver == 4) {
                                    isP1LifeHisMax = true;
                                    p1txt.setText("p1LifeHisDriver "+p1LifeHisDriver
                                            +"\n isP1LifeHisMax " +isP1LifeHisMax
                                            +"\n p1LifeList.size() "+p1LifeList.size()
                                    );
                                }
                                */
        /*
        p1plus1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP: {
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                }
                return true;
            }
        });
        */
    }


/*
        mButton = (Button) this.findViewById(R.id.obj6);
        mButton.setText("open");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //最大数できるまでの動き

            //3のときにボタンをおすと、カウントが４になってopenMenuにはいり
            //４だったときカウントが０になってclosemenuにはいる
            //カウントが４のときのopenmenuの動作はない


            if(p1LifeHisDriver <4){
                p1LifeHisDriver++;
            }else{
                p1LifeHisDriver =0;
            }
            if(!isP1LifeHisMax&&p1LifeHisDriver==4){
                isP1LifeHisMax=true;
            }

            if(isP1LifeHisMax){
                fn_p1LifeHisMove2();
            }else{
                fn_p1LifeHisMove1();
            }

            if( p1countLife <4){
                p1countLife++;
            }else{
                p1countLife=0;
            }
      //  private ValueAnimator fn_translation(View target, float toTX, float toTY, float toSX, float toSY, long duration, String fade) {
            switch(p1countLife){
                case 0:fn_translation(p1Life_txt1,0,800,0.8f,1,800,"fadeout");
                    break;
                case 1:fn_translation(p1Life_txt2,100,800,0.8f,1,800,"fadeout");
                    break;
                case 2:fn_translation(p1Life_txt3,200,800,0.8f,1,800,"fadeout");
                    break;
                case 3:fn_translation(p1Life_txt4,300,800,0.8f,1,800,"fadeout");
                    break;
                case 4:fn_translation(p1Life_txt5,400,800,0.8f,1,800,"fadeout");
                    break;
            }

            }
        });

/*
        AnimationSampleFragment f = new AnimationSampleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,f).commit();
        */
/*
    // TextViewを追加するメソッド
    //リスナーの中ではthisは使えないので、クリックしたときにボタンを生成するのはできないのでは
    //リスナーの中でメソッドを使いたい場合は、その中にあるものしか使えない感 privateだからかな？
    //結局contextという謎の存在を解決できないのでどうしようもない
    public void addTextView(String message) {
        // TextViewを作成してテキストを設定
        TextView tv = new TextView(this);
        tv.setText(message);

        // 親のViewGroupを取得して、Viewを追加
        RelativeLayout lay = (RelativeLayout)this.findViewById(R.id.linearlayout01);
        lay.addView(tv);
    }
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
        float firstTouch = 0;
        int p1LifeIntTemp = 0;//指を動かしている間の値
        int p1LifeIntTemp2 = p1LifeInt;//最初の値
        int p1LifeDistInt;
        float p1LifeDistance;//finalは変更を許可しない
        float s = 3f;
        int plusminus = 1;
        int distTmp=0;
        String tmpState="";

        int creaseNum;

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
                case MotionEvent.ACTION_POINTER_UP: actionPointerUp(); break;
                case MotionEvent.ACTION_MOVE: actionMove(motionEvent); break;
                case MotionEvent.ACTION_CANCEL: System.out.println("ACTION_CANCEL "); break;
            }
            return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
        }
        public void actionPointerDown(MotionEvent motionEvent){
            //画面がタッチされた場合の処理
            int pointerIndex = motionEvent.getActionIndex();
            firstTouch = motionEvent.getY(pointerIndex);
            p1LifeDistance = 0;
            p1LifeDistInt = 0;
            p1LifeIntTemp = p1LifeInt;//値を渡す
            p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
            p1txt.setText("p1txt ACTION_POINTER_DOWN \n" + " firstTouch " + firstTouch
                    + "\n p1LifeInt:" + p1LifeInt + "  p1LifeIntTemp: " + p1LifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    + " \n p1LifeDistance: " + p1LifeDistance
                    + " \n p1LifeDistInt: " + p1LifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n p1LifeDistIntArr.size " + p1LifeDistIntArr.size());//テキストの編集
        }
        public void actionPointerUp(){
            System.out.println("actionPointerUp p1GlobalTmp " + p1GlobalTmp);
            //タッチが離れた場合の処理

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
            p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouch " + firstTouch
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
        }
        public void setVariables(){
            creaseNum = 1;
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
/*
    private void fn_p1LifeHisMove1(ArrayList<String> arr) {
        int baseNum=0;
        switch(p1LifeHisDriver){
            case 0:
                baseNum=8;
                fn_translation(p1lifeHistory1,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*0),lifeHisTransDuration,"fadein");
                p1lifeHistory1.setText( arr.get(0) );
                break;
            case 1:
                num=7;
                fn_translation(p1lifeHistory2,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory2.setText( arr.get(1) );
                break;
            case 2:
                num=6;
                fn_translation(p1lifeHistory3,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory3.setText( arr.get(2) );
                break;
            case 3:
                num=5;
                fn_translation(p1lifeHistory4,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory4.setText( arr.get(3) );
                break;
            case 4:
                num=4;
                fn_translation(p1lifeHistory5,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory5.setText( arr.get(4) );
                break;
            case 5:
                num=3;
                fn_translation(p1lifeHistory6,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory6.setText( arr.get(5) );
                break;
            case 6:
                num=2;
                fn_translation(p1lifeHistory7,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory7.setText( arr.get(6) );
                break;
            case 7:
                num=1;
                fn_translation(p1lifeHistory8,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory8.setText( arr.get(7) );
                break;
            case 8:
                num=0;
                fn_translation(p1lifeHistory9,hisTxtMove*(num+1),hisTxtMove*num,lifeHisTransDuration,"fadein");
                p1lifeHistory9.setText( arr.get(8) );
                break;
        }
        */
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
}
