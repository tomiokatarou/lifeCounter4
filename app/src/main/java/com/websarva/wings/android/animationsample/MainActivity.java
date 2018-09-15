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
import android.media.AudioAttributes;
import android.media.SoundPool;
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
    Button mButton;
    //p1
    TextView p1lifeHistory1,p1lifeHistory2,p1lifeHistory3,p1lifeHistory4,p1lifeHistory5,p1lifeHistory6,p1lifeHistory7,p1lifeHistory8,p1lifeHistory9;
    TextView p1Life_txt,p1Life_txt1,p1Life_txt2,p1Life_txt3,p1Life_txt4,p1Life_txt5;
    TextView p1Psn_txt,p1Psn_txt1,p1Psn_txt2,p1Psn_txt3;
    TextView p1Eng_txt,p1Eng_txt1,p1Eng_txt2,p1Eng_txt3;
    Button p1plus1,p1plus5,p1minus1,p1minus5,p1PsnPlus,p1PsnMinus,p1EngPlus,p1EngMinus;
    //p2
    TextView p2lifeHistory1,p2lifeHistory2,p2lifeHistory3,p2lifeHistory4,p2lifeHistory5,p2lifeHistory6,p2lifeHistory7,p2lifeHistory8,p2lifeHistory9;
    TextView p2Life_txt,p2Life_txt1,p2Life_txt2,p2Life_txt3,p2Life_txt4,p2Life_txt5;
    TextView p2Psn_txt,p2Psn_txt1,p2Psn_txt2,p2Psn_txt3;
    TextView p2Eng_txt,p2Eng_txt1,p2Eng_txt2,p2Eng_txt3;
    Button p2plus1,p2plus5,p2minus1,p2minus5,p2PsnPlus,p2PsnMinus,p2EngPlus,p2EngMinus;
    Button resetBtn;

    //p1
    int p1LifeInt = 20,p1PsnInt = 0,p1EngInt = 0;
    //p2
    int p2LifeInt = 20,p2PsnInt = 0,p2EngInt = 0;

    //lifeDriver
    int p1LifeDriver = 0,p1PsnDriver = 0,p1EngDriver = 0;//-1
    int p1LifeHisDriver = -1;//どのテキストを動かすかというためだけの循環する変数　にしたい
    boolean isP1LifeHisMax = false;//ヒストリーがマックスになってるかどうか
    int p2LifeDriver = 0,p2PsnDriver = 0,p2EngDriver = 0;//-1
    int p2LifeHisDriver = -1;//どのテキストを動かすかというためだけの循環する変数　にしたい
    boolean isp2LifeHisMax = false;//ヒストリーがマックスになってるかどうか

    public ArrayList<String> p1LifeList;
    public ArrayList<String> p1LifeListTmp;
    public ArrayList<String> p2LifeList;
    public ArrayList<String> p2LifeListTmp;

    //遅らせるやつ
    private Handler mHandler = new Handler();
    private Runnable delayedUpdate;

    //フォントサイズ style.xmlで定義してる
    final int hisTxtMove = 90;//移動量 110
    float fadeFontScale = 1.2f;//3
    float fadeCounterFontScale = 2.0f;//カウンターの数字の1フェードのスケール

    //グローバルな増減値
    int p1GlobalTmp=0;
    int p2GlobalTmp=0;

    long lifeScaleDuration=200;
    long lifeHisTransDuration=700;

    //se
    private SoundPool soundPool;
    private int btnSe;
    //----------------------------------------------------------------------------------------------
    private Circle circ;
    private effectCircle circP1Plus1;
    private int animationPeriod = 2000;

    ConstraintLayout p2Layout;
    public RectF rectP1Plus1,rectP1Plus5,rectP1Minus1,rectP1Minus5,rectP1PsnPlus,rectP1PsnMinus,rectP1EngPlus,rectP1EngMinus;
    public RectF rectP2Plus1,rectP2Plus5,rectP2Minus1,rectP2Minus5,rectP2PsnPlus,rectP2PsnMinus,rectP2EngPlus,rectP2EngMinus;
    //ボタンサイズを取得
    float btnSizeX,btnSizeY;
    //debug----------------------------------------------------------------------------------------------

    TextView p1txt;
    TextView p2Life_txt_debug;
    //----------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_animation_sample);//先においておかないとボタンがないのでリスナーも使えない

        //final ConstraintLayout relativeLayout_o = (ConstraintLayout) findViewById(R.id.fragment_animation_sample);
        final ConstraintLayout p1Layout_o = (ConstraintLayout) findViewById(R.id.p1Layout);
        final ConstraintLayout p2Layout_o = (ConstraintLayout) findViewById(R.id.p2Layout);
        p2Layout = (ConstraintLayout) findViewById(R.id.p2Layout);

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

        //カウンターのテキストとフェードテキスト
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
        //カウンターのボタン
        p1PsnPlus = (Button) this.findViewById(R.id.p1PsnPlus);
        p1PsnMinus = (Button) this.findViewById(R.id.p1PsnMinus);
        p1EngPlus = (Button) this.findViewById(R.id.p1EngPlus);
        p1EngMinus = (Button) this.findViewById(R.id.p1EngMinus);

        //エフェクトクリップ用矩形
        rectP1Plus1 = new RectF(0,0,0,0);
        rectP1Plus5 = new RectF(0,0,0,0);
        rectP1Minus1 = new RectF(0,0,0,0);
        rectP1Minus5 = new RectF(0,0,0,0);
        rectP1PsnPlus = new RectF(0,0,0,0);
        rectP1PsnMinus = new RectF(0,0,0,0);
        rectP1EngPlus = new RectF(0,0,0,0);
        rectP1EngMinus = new RectF(0,0,0,0);

        //p2オブジェクトの生成
        p2Life_txt = (TextView) this.findViewById(R.id.p2Life_txt);
        p2Life_txt.setText(p2LifeInt+"");
        p2Life_txt1 = (TextView) this.findViewById(R.id.p2Life_txt1);
        p2Life_txt2 = (TextView) this.findViewById(R.id.p2Life_txt2);
        p2Life_txt3 = (TextView) this.findViewById(R.id.p2Life_txt3);
        p2Life_txt4 = (TextView) this.findViewById(R.id.p2Life_txt4);
        p2Life_txt5 = (TextView) this.findViewById(R.id.p2Life_txt5);
        //オブジェクトの生成
        p2lifeHistory1 = (TextView) this.findViewById(R.id.p2lifeHistory1);
        p2lifeHistory2 = (TextView) this.findViewById(R.id.p2lifeHistory2);
        p2lifeHistory3 = (TextView) this.findViewById(R.id.p2lifeHistory3);
        p2lifeHistory4 = (TextView) this.findViewById(R.id.p2lifeHistory4);
        p2lifeHistory5 = (TextView) this.findViewById(R.id.p2lifeHistory5);
        p2lifeHistory6 = (TextView) this.findViewById(R.id.p2lifeHistory6);
        p2lifeHistory7 = (TextView) this.findViewById(R.id.p2lifeHistory7);
        p2lifeHistory8 = (TextView) this.findViewById(R.id.p2lifeHistory8);
        p2lifeHistory9 = (TextView) this.findViewById(R.id.p2lifeHistory9);

        //カウンターのテキストとフェードテキスト
        p2Psn_txt = (TextView) this.findViewById(R.id.p2Psn_txt);
        p2Psn_txt.setText(p2PsnInt+"");
        p2Psn_txt1 = (TextView) this.findViewById(R.id.p2Psn_txt1);
        p2Psn_txt2 = (TextView) this.findViewById(R.id.p2Psn_txt2);
        p2Psn_txt3 = (TextView) this.findViewById(R.id.p2Psn_txt3);

        p2Eng_txt = (TextView) this.findViewById(R.id.p2Eng_txt);
        p2Eng_txt.setText(p2EngInt+"");
        p2Eng_txt1 = (TextView) this.findViewById(R.id.p2Eng_txt1);
        p2Eng_txt2 = (TextView) this.findViewById(R.id.p2Eng_txt2);
        p2Eng_txt3 = (TextView) this.findViewById(R.id.p2Eng_txt3);

        //ボタンの生成
        p2plus1 = (Button) this.findViewById(R.id.p2plus1);
        p2plus5 = (Button) this.findViewById(R.id.p2plus5);
        p2minus1 = (Button) this.findViewById(R.id.p2minus1);
        p2minus5 = (Button) this.findViewById(R.id.p2minus5);
        //カウンターのボタン
        p2PsnPlus = (Button) this.findViewById(R.id.p2PsnPlus);
        p2PsnMinus = (Button) this.findViewById(R.id.p2PsnMinus);
        p2EngPlus = (Button) this.findViewById(R.id.p2EngPlus);
        p2EngMinus = (Button) this.findViewById(R.id.p2EngMinus);

        //エフェクトクリップ用矩形
        rectP1Plus1 = new RectF(0,0,0,0);
        rectP1Plus5 = new RectF(0,0,0,0);
        rectP1Minus1 = new RectF(0,0,0,0);
        rectP1Minus5 = new RectF(0,0,0,0);
        rectP1PsnPlus = new RectF(0,0,0,0);
        rectP1PsnMinus = new RectF(0,0,0,0);
        rectP1EngPlus = new RectF(0,0,0,0);
        rectP1EngMinus = new RectF(0,0,0,0);
        rectP2Plus1 = new RectF(0,0,0,0);
        rectP2Plus5 = new RectF(0,0,0,0);
        rectP2Minus1 = new RectF(0,0,0,0);
        rectP2Minus5 = new RectF(0,0,0,0);
        rectP2PsnPlus = new RectF(0,0,0,0);
        rectP2PsnMinus = new RectF(0,0,0,0);
        rectP2EngPlus = new RectF(0,0,0,0);
        rectP2EngMinus = new RectF(0,0,0,0);
        //リセット
        resetBtn = (Button) this.findViewById(R.id.resetBtn);

        //se
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build();
        //ロードしておく
        btnSe = soundPool.load(this,R.raw.btn_se,1);
        //デバッグ用
        p2Life_txt_debug = (TextView) this.findViewById(R.id.p2Life_txt_debug);

        //着色
        //ボタンの矢印
        int color = getResources().getColor(R.color.colorGray);
        //p1
        ImageView p1plus1UpArrow = (ImageView) findViewById(R.id.p1plus1UpArrow);
        p1plus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1plus5UpArrow = (ImageView) findViewById(R.id.p1plus5UpArrow);
        p1plus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1minus1UpArrow = (ImageView) findViewById(R.id.p1minus1UpArrow);
        p1minus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1minus5UpArrow = (ImageView) findViewById(R.id.p1minus5UpArrow);
        p1minus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        ImageView p1PsnUpArrow = (ImageView) findViewById(R.id.p1PsnUpArrow);
        p1PsnUpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1PsnDownArrow = (ImageView) findViewById(R.id.p1PsnDownArrow);
        p1PsnDownArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1EngUpArrow = (ImageView) findViewById(R.id.p1EngUpArrow);
        p1EngUpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p1EngDownArrow = (ImageView) findViewById(R.id.p1EngDownArrow);
        p1EngDownArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        //p2
        ImageView p2plus1UpArrow = (ImageView) findViewById(R.id.p2plus1UpArrow);
        p2plus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2plus5UpArrow = (ImageView) findViewById(R.id.p2plus5UpArrow);
        p2plus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2minus1UpArrow = (ImageView) findViewById(R.id.p2minus1UpArrow);
        p2minus1UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2minus5UpArrow = (ImageView) findViewById(R.id.p2minus5UpArrow);
        p2minus5UpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        ImageView p2PsnUpArrow = (ImageView) findViewById(R.id.p2PsnUpArrow);
        p2PsnUpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2PsnDownArrow = (ImageView) findViewById(R.id.p2PsnDownArrow);
        p2PsnDownArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2EngUpArrow = (ImageView) findViewById(R.id.p2EngUpArrow);
        p2EngUpArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        ImageView p2EngDownArrow = (ImageView) findViewById(R.id.p2EngDownArrow);
        p2EngDownArrow.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        //counter
        int colorWhite = getResources().getColor(R.color.colorWhite);
        ImageView p1EngImg = (ImageView) findViewById(R.id.p1EngImg);
        p1EngImg.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN);
        ImageView p1PsnImg = (ImageView) findViewById(R.id.p1PsnImg);
        p1PsnImg.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN);
        ImageView p2EngImg = (ImageView) findViewById(R.id.p2EngImg);
        p2EngImg.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN);
        ImageView p2PsnImg = (ImageView) findViewById(R.id.p2PsnImg);
        p2PsnImg.setColorFilter(colorWhite, PorterDuff.Mode.SRC_IN);
        //リセットボタン
        int colorPink = getResources().getColor(R.color.colorPink);
        ImageView resetBtnImg = (ImageView) findViewById(R.id.resetBtnImg);
        resetBtnImg.setColorFilter(colorPink, PorterDuff.Mode.SRC_IN);

        p1txt = (TextView) this.findViewById(R.id.p1textView);
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
        //p2初期配置
        fn_translation(p2Life_txt1, 0, 0, 1, "invisible");
        fn_translation(p2Life_txt2, 0, 0, 1, "invisible");
        fn_translation(p2Life_txt3, 0, 0, 1, "invisible");
        fn_translation(p2Life_txt4, 0, 0, 1, "invisible");
        fn_translation(p2Life_txt5, 0, 0, 1, "invisible");

        fn_translation(p2lifeHistory1, 0, 0, 0, "invisible");//visible
        fn_translation(p2lifeHistory2, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory3, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory4, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory5, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory6, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory7, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory8, 0, 0, 0, "invisible");
        fn_translation(p2lifeHistory9, 0, 0, 0, "invisible");

        fn_translation(p2Psn_txt1, 0, 0, 1, "invisible");
        fn_translation(p2Psn_txt2, 0, 0, 1, "invisible");
        fn_translation(p2Psn_txt3, 0, 0, 1, "invisible");
        fn_translation(p2Eng_txt1, 0, 0, 1, "invisible");
        fn_translation(p2Eng_txt2, 0, 0, 1, "invisible");
        fn_translation(p2Eng_txt3, 0, 0, 1, "invisible");

        p1LifeList = new ArrayList<>();
        p1LifeListTmp = new ArrayList<>();
        p2LifeList = new ArrayList<>();
        p2LifeListTmp = new ArrayList<>();

        //----------------------------------------------------------------------------------------------

        circ = (Circle) findViewById(R.id.circle);
        circP1Plus1 = (effectCircle) this.findViewById(R.id.circP1Plus1);
        /*
        Animator alpha = alphaAnimator(circP1Plus1,1,"Visible");
        alpha.start();
        */
        //----------------------------------------------------------------------------------------------


        //----------------------------------------------------------------------------------------------

        buttonListener p1plus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 1;
                clipRect = rectP1Plus1;
                lifeInt = p1LifeInt;
                p="p1";
            }
        };
        p1plus1.setOnTouchListener(p1plus1TouchListener);
        buttonListener p1plus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 5;
                clipRect = rectP1Plus5;
                lifeInt = p1LifeInt;
                p="p1";
            }
        };
        p1plus5.setOnTouchListener(p1plus5TouchListener);
        buttonListener p1minus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -1;
                clipRect = rectP1Minus1;
                lifeInt = p1LifeInt;
                p="p1";
            }
        };
        p1minus1.setOnTouchListener(p1minus1TouchListener);
        buttonListener p1minus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -5;
                clipRect = rectP1Minus5;
                lifeInt = p1LifeInt;
                p="p1";
            }
        };
        p1minus5.setOnTouchListener(p1minus5TouchListener);
        //poison
        buttonCounterListener p1PsnPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 1;creaseEngNum = 0;
                clipRect = rectP1PsnPlus;
                p="p1";
            }
        };
        p1PsnPlus.setOnTouchListener(p1PsnPlusTouchListener);
        buttonCounterListener p1PsnMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = -1;creaseEngNum = 0;
                clipRect = rectP1PsnMinus;
                p="p1";
            }
        };
        p1PsnMinus.setOnTouchListener(p1PsnMinusTouchListener);
        //energy
        buttonCounterListener p1EngPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = 1;
                clipRect = rectP1EngPlus;
                p="p1";
            }
        };
        p1EngPlus.setOnTouchListener(p1EngPlusTouchListener);
        buttonCounterListener p1EngMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = -1;
                clipRect = rectP1EngMinus;
                p="p1";
            }
        };
        p1EngMinus.setOnTouchListener(p1EngMinusTouchListener);

        //-----------------------------------
        buttonListener p2plus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 1;
                clipRect = rectP2Plus1;
                lifeInt = p2LifeInt;
                p="p2";
            }
        };
        p2plus1.setOnTouchListener(p2plus1TouchListener);
        buttonListener p2plus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = 5;
                clipRect = rectP2Plus5;
                lifeInt = p2LifeInt;
                p="p2";
            }
        };
        p2plus5.setOnTouchListener(p2plus5TouchListener);
        buttonListener p2minus1TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -1;
                clipRect = rectP2Minus1;
                lifeInt = p2LifeInt;
                p="p2";
            }
        };
        p2minus1.setOnTouchListener(p2minus1TouchListener);
        buttonListener p2minus5TouchListener = new buttonListener() {
            @Override
            public void setVariables(){
                creaseNum = -5;
                clipRect = rectP2Minus5;
                lifeInt = p2LifeInt;
                p="p2";
            }
        };
        p2minus5.setOnTouchListener(p2minus5TouchListener);
        //poison
        buttonCounterListener p2PsnPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 1;creaseEngNum = 0;
                clipRect = rectP2PsnPlus;
                p="p2";
            }
        };
        p2PsnPlus.setOnTouchListener(p2PsnPlusTouchListener);
        buttonCounterListener p2PsnMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = -1;creaseEngNum = 0;
                clipRect = rectP2PsnMinus;
                p="p2";
            }
        };
        p2PsnMinus.setOnTouchListener(p2PsnMinusTouchListener);
        //energy
        buttonCounterListener p2EngPlusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = 1;
                clipRect = rectP2EngPlus;
                p="p2";
            }
        };
        p2EngPlus.setOnTouchListener(p2EngPlusTouchListener);
        buttonCounterListener p2EngMinusTouchListener = new buttonCounterListener() {
            @Override
            public void setVariables(){
                creasePsnNum = 0;creaseEngNum = -1;
                clipRect = rectP2EngMinus;
                p="p2";
            }
        };
        p2EngMinus.setOnTouchListener(p2EngMinusTouchListener);

        resetBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AnimationCircle animation = new AnimationCircle(circ, (float) 0.5);
                // アニメーションの起動期間を設定
                animation.setInterpolator(new DecelerateInterpolator());//OvershootInterpolator DecelerateInterpolator
                animationPeriod = 800;
                animation.setDuration(animationPeriod);

                System.out.print("resetBtn \n");
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:{
                        circ.startAnimation(animation);
                        mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
                        delayedUpdate = new Runnable() {
                            public void run() {
                                p1LifeInt = 20;
                                p1LifeDriver = 0;
                                p1LifeHisDriver = -1;
                                isP1LifeHisMax = false;
                                p1LifeList = new ArrayList<>();
                                p1PsnInt=0;p1EngInt=0;
                                p1Psn_txt.setText(String.valueOf(p1PsnInt));//ライフ
                                p1Eng_txt.setText(String.valueOf(p1EngInt));//ライフ
                                p1PsnDriver=0;p1EngDriver=0;

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
                                
                                p2LifeInt = 20;
                                p2LifeDriver = 0;
                                p2LifeHisDriver = -1;
                                isp2LifeHisMax = false;
                                p2LifeList = new ArrayList<>();
                                p2PsnInt=0;p2EngInt=0;
                                p2Psn_txt.setText(String.valueOf(p2PsnInt));//ライフ
                                p2Eng_txt.setText(String.valueOf(p2EngInt));//ライフ
                                p2PsnDriver=0;p2EngDriver=0;

                                p2Life_txt.setText(String.valueOf(p2LifeInt));//ライフ
                                p2lifeHistory1.setText( "" );
                                p2lifeHistory2.setText( "" );
                                p2lifeHistory3.setText( "" );
                                p2lifeHistory4.setText( "" );
                                p2lifeHistory5.setText( "" );
                                p2lifeHistory6.setText( "" );
                                p2lifeHistory7.setText( "" );
                                p2lifeHistory8.setText( "" );
                                p2lifeHistory9.setText( "" );
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

    class buttonListener implements View.OnTouchListener {
        /*
        p1LifeInt p1Life_txt p1GlobalTmp p1LifeDriver
         */
        int lifeInt;

        float firstTouchX=0,firstTouchY = 0,releaseX,releaseY;
        int lifeIntTemp = 0;//指を動かしている間の値
        int lifeIntTemp2 = lifeInt;//最初の値
        int lifeDistInt;//差分用の数字（移動距離）
        float s = 3f;
        int distTmp=0;
        String tmpState="";
        String p;
        //場所確認

        int creaseNum;
        RectF clipRect;

        ArrayList<Integer> lifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！

        TextView p1txt = (TextView) findViewById(R.id.p1textView);//debug用
        TextView p1txt2 = (TextView) findViewById(R.id.p1textView2);//debug用

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            btnSizeX = p1plus1.getRight();
            btnSizeY = p1plus1.getBottom() - p1plus1.getTop();
            lifeIntTemp2 = lifeIntTemp;
            setVariables();
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: actionPointerDown(motionEvent); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP: actionPointerUp(motionEvent,p); break;
                case MotionEvent.ACTION_MOVE: actionMove(motionEvent,p); break;
                case MotionEvent.ACTION_CANCEL: System.out.println("ACTION_CANCEL "); break;
            }
            return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
        }
        public void actionPointerDown(MotionEvent motionEvent){
            //画面がタッチされた場合の処理
            int pointerIndex = motionEvent.getActionIndex();
            firstTouchY = motionEvent.getY(pointerIndex);
            firstTouchX = motionEvent.getX(pointerIndex);
            lifeDistInt = 0;
            lifeIntTemp = lifeInt;//値を渡す
            if(p=="p1") p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
            else p2Life_txt.setText(String.valueOf(p2LifeInt));//ライフ
            p1txt.setText("p1txt ACTION_POINTER_DOWN \n" + " firstTouchY " + firstTouchY
                    + "\n p1LifeInt:" + p1LifeInt + "  lifeIntTemp: " + lifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    + " \n lifeDistInt: " + lifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n lifeDistIntArr.size " + lifeDistIntArr.size());//テキストの編集
        }
        public void actionPointerUp(MotionEvent motionEvent, final String p){
            releaseX = motionEvent.getX();
            releaseY = motionEvent.getY();
            //System.out.println("actionPointerUp p1GlobalTmp " + p1GlobalTmp);
            System.out.println("releaseX:" + releaseX + " releaseY:"+releaseY);
            System.out.println("p2Layout.getTop():" + p2Layout.getTop());
            System.out.println("clipRect.left:" + clipRect.left + " top:"+clipRect.top);

            //if(releaseX>clipRect.left && releaseX<clipRect.right && releaseY>clipRect.top && releaseY<clipRect.bottom) {
            if(releaseX > 0 && releaseX < btnSizeX && releaseY > 0 && releaseY < btnSizeY ) {
                AnimationEffectCircle animation = new AnimationEffectCircle(circP1Plus1, releaseX + clipRect.left, releaseY + clipRect.top, clipRect);
                // アニメーションの起動期間を設定
                animation.setInterpolator(new DecelerateInterpolator());//OvershootInterpolator DecelerateInterpolator
                animationPeriod = 800;
                animation.setDuration(animationPeriod);
                circP1Plus1.startAnimation(animation);
                //fadeアニメーションはApplyChangesで初期化されてないっぽいか、初期化がうまくできてないか
                Animator alpha = alphaAnimator(circP1Plus1, 400, "fadeout2");
                alpha.start();
            }
            /*
            p2Life_txt_debug.setText(""
                    +"clipRect left:"+clipRect.left +" right:"+clipRect.right +" top:"+clipRect.top +" bottom:"+clipRect.bottom + "\n"
                    +"p1plus1 left:"+p1plus1.getLeft() +" right:"+p1plus1.getRight() +" top:"+p1plus1.getTop() +" bottom:"+p1plus1.getBottom() + "\n"
                    +"p1plus5 left:"+p1plus5.getLeft() +" right:"+p1plus5.getRight() +" top:"+p1plus5.getTop() +" bottom:"+p1plus5.getBottom() + "\n"
                    +"p1minus1 left:"+p1minus1.getLeft() +" right:"+p1minus1.getRight() +" top:"+p1minus1.getTop() +" bottom:"+p1minus1.getBottom() + "\n"
                    +"p1minus5 left:"+p1minus5.getLeft() +" right:"+p1minus5.getRight() +" top:"+p1minus5.getTop() +" bottom:"+p1minus5.getBottom() + "\n"
                    +"p2plus1 left:"+p2plus1.getLeft() +" right:"+p2plus1.getRight() +" top:"+p2plus1.getTop() +" bottom:"+p2plus1.getBottom() + "\n"
                    +"p2plus5 left:"+p2plus5.getLeft() +" right:"+p2plus5.getRight() +" top:"+p2plus5.getTop() +" bottom:"+p2plus5.getBottom() + "\n"
                    +"p2minus1 left:"+p2minus1.getLeft() +" right:"+p2minus1.getRight() +" top:"+p2minus1.getTop() +" bottom:"+p2minus1.getBottom() + "\n"
                    +"p2minus5 left:"+p2minus5.getLeft() +" right:"+p2minus5.getRight() +" top:"+p2minus5.getTop() +" bottom:"+p2minus5.getBottom() + "\n"
                    +"btnSizeX: "+btnSizeX+" btnSizeY: "+btnSizeY + "\n"
                    +"releaseX "+releaseX + "\n"
                    +"releaseY "+releaseY + "\n"
            );
*/
            mHandler.postDelayed(delayedUpdate, 10);

            if (lifeDistIntArr.size() == 0) {
                //初回
                lifeDistIntArr.add(lifeDistInt);
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }
            //もしDistIntが０（移動してない）場合は変わりに-5を入れる
            if(p=="p1") {
                if (lifeDistInt == 0) {
                    lifeIntTemp += creaseNum;
                    lifeDistInt = +creaseNum;
                    p1GlobalTmp += creaseNum;
                } else {
                    p1GlobalTmp += lifeDistInt;
                }
            }else{
                if (lifeDistInt == 0) {
                    lifeIntTemp += creaseNum;
                    lifeDistInt = +creaseNum;
                    p2GlobalTmp += creaseNum;
                } else {
                    p2GlobalTmp += lifeDistInt;
                }
            }
            lifeDistIntArr.add(lifeDistInt);

            if(p=="p1") {
                //値が最初と変わってない限り
                if (lifeIntTemp != lifeIntTemp2) {
                    fn_p1LifeFade(lifeIntTemp);
                }
                p1LifeInt = lifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る
                p1Life_txt.setText(String.valueOf(p1LifeInt));//ライフ
                if (p1LifeDriver < 3) {
                    p1LifeDriver++;
                } else {
                    p1LifeDriver = 0;
                }
            }else{
                //値が最初と変わってない限り
                if (lifeIntTemp != lifeIntTemp2) {
                    fn_p2LifeFade(lifeIntTemp);
                }
                p2LifeInt = lifeIntTemp;//値を渡し返す　ここに入れないと値が巻き戻る
                p2Life_txt.setText(String.valueOf(p2LifeInt));//ライフ
                if (p2LifeDriver < 3) {
                    p2LifeDriver++;
                } else {
                    p2LifeDriver = 0;
                }
            }
            //動くようにはなったけど、今度リストが追加されなくなってしまった。
            //p1countの扱いと動作の整理しないといかんなこれは
            distTmp = lifeDistInt;

            System.out.println("iffff     p1GlobalTmp " + p1GlobalTmp);
            System.out.println("iffff     p2GlobalTmp " + p2GlobalTmp);
            p1txt.setText("p1txt ACTION_POINTER_UP \n" + " firstTouchY " + firstTouchY
                    + "\n p1LifeInt:" + p1LifeInt + "  lifeIntTemp: " + lifeIntTemp
                    + " \n p1LifeHisDriver: " + p1LifeHisDriver
                    //+ " \n lifeDistance: " + lifeDistance
                    + " \n lifeDistInt: " + lifeDistInt
                    + " \n p1LifeList.size() " + p1LifeList.size()
                    + " \n p1GlobalTmp " + p1GlobalTmp
                    + " \n lifeDistIntArr.size " + lifeDistIntArr.size());//テキストの編集
            mHandler.removeCallbacks(delayedUpdate);//一回実行してた場合それを破棄する
            delayedUpdate = new Runnable() {
                public void run() {
                    //if(p=="p1") {
                        if (p1GlobalTmp != 0) {
                            fn_p1LifeDriver(p1GlobalTmp);
                            p1GlobalTmp = 0;
                            tmpState = "";
                        }
                    //}else{
                        if (p2GlobalTmp != 0) {
                            fn_p2LifeDriver(p2GlobalTmp);
                            p2GlobalTmp = 0;
                            tmpState = "";
                        }
                    //}
                }
            };
            mHandler.postDelayed(delayedUpdate, 1000);
            //これ意味ある？
            lifeDistIntArr = new ArrayList<>();//finalつけると逆に使えないぞ。理由は不明だ！
        }
        public void actionMove(MotionEvent motionEvent,final String p){
            System.out.println("actionMove \n");
            lifeDistInt = (int) (firstTouchY - motionEvent.getY(0)) / 100;//整数で

            if (lifeDistIntArr.size() == 0) {
                //初回
                lifeDistIntArr.add(lifeDistInt);

                p1txt2.setText("初回 \n distTmp " + distTmp);
                p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                        + " \n lifeDistInt: " + lifeDistInt
                        + " \n distTmp: " + distTmp
                        + " \n lifeDistIntArr.size() " + lifeDistIntArr.size()
                );
                distTmp = lifeDistInt;
                //この中に入ったあと弄っててもフェードアウトしてるやつが出ない
            }

            //配列の中身が一つ以上あり、distTmpに値が入っている状態
            p1txt.setText("p1LifeDriver \n" + p1LifeDriver
                    + " \n lifeDistInt: " + lifeDistInt
                    + " \n distTmp: " + distTmp
                    + " \n lifeDistIntArr.size() " + lifeDistIntArr.size()
            );
            if (lifeDistInt != distTmp) {
                lifeDistIntArr.add(lifeDistInt);
                if(p=="p1") {
                    if (p1LifeDriver < 3) {
                        p1LifeDriver++;
                    } else {
                        p1LifeDriver = 0;
                    }
                    //省略できそうだけどめんどそう
                    fn_p1LifeFade(lifeIntTemp);
                }else{
                    if (p2LifeDriver < 3) {
                        p2LifeDriver++;
                    } else {
                        p2LifeDriver = 0;
                    }
                    //省略できそうだけどめんどそう
                    fn_p2LifeFade(lifeIntTemp);
                }
                //動くようにはなったけど、今度リストが追加されなくなってしまった。
                //p1countの扱いと動作の整理しないといかんなこれは
                distTmp = lifeDistInt;
                p1txt2.setText("lifeDistInt!=distTmp \n distTmp " + distTmp);
            }
            //値が違った場合、違い続けるってことか
            if (lifeDistIntArr.size() > 2) {
                //2より大きくなったらリムーブ
                lifeDistIntArr.remove(0);
            }

            if(p=="p1"){
                p1Life_txt.setText(String.valueOf(lifeIntTemp));//ライフ
                //life_txt.setText(String.valueOf(lifeIntTemp));//テキストの編集
                lifeIntTemp = p1LifeInt + lifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置
            }
            else{
                p2Life_txt.setText(String.valueOf(lifeIntTemp));//ライフ
                //life_txt.setText(String.valueOf(lifeIntTemp));//テキストの編集
                lifeIntTemp = p2LifeInt + lifeDistInt;//動かすたびに毎フレーム追加されてる 離した瞬間にもう一度足されてしまうのでこの位置
            }
        }
        public void setVariables(){
            creaseNum = 1;
            clipRect = rectP1Plus1;
            p="p1";
        }
    }

    //カウンター用のボタンリスナー
    class buttonCounterListener implements View.OnTouchListener {
        float releaseX,releaseY;
        float s = 3f;

        int creasePsnNum,creaseEngNum;
        RectF clipRect;
        String p;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            setVariables();
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: actionPointerDown(); break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP: actionPointerUp(motionEvent,p); break;
                case MotionEvent.ACTION_MOVE: break;
                case MotionEvent.ACTION_CANCEL: System.out.println("ACTION_CANCEL "); break;
            }
            return false;//trueの場合は処理を親要素に渡さない。falseの場合は処理を親要素に渡す。
        }
        public void actionPointerDown(){
            //画面がタッチされた場合の処理
            p1Psn_txt.setText(String.valueOf(p1PsnInt));//ライフ
            p1Eng_txt.setText(String.valueOf(p1EngInt));//ライフ
        }
        public void actionPointerUp(MotionEvent motionEvent,String p){
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

            if(p=="p1") {
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
                if (creasePsnNum != 0) {
                    fn_p1PsnFade(p1PsnInt);
                } else {
                    fn_p1EngFade(p1EngInt);
                }
            }else{
                //ライフ変動
                p2PsnInt += creasePsnNum;
                p2EngInt += creaseEngNum;
                p2Psn_txt.setText(String.valueOf(p2PsnInt));//ライフ
                p2Eng_txt.setText(String.valueOf(p2EngInt));//ライフ
                if (p2PsnDriver < 2) {
                    p2PsnDriver++;
                } else {
                    p2PsnDriver = 0;
                }
                if (p2EngDriver < 2) {
                    p2EngDriver++;
                } else {
                    p2EngDriver = 0;
                }
                if (creasePsnNum != 0) {
                    fn_p2PsnFade(p2PsnInt);
                } else {
                    fn_p2EngFade(p2EngInt);
                }                
            }

            p1txt.setText("p1PsnDriver \n" + p1PsnDriver
                    + " \n p1Psn1txt1: " + p1Psn_txt1.getText()
                    + " \n p1Psn1txt2: " + p1Psn_txt2.getText()
                    + " \n p1Psn1txt3: " + p1Psn_txt3.getText()
            );

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
                p1Life_txt2.setText(""+lifeIntTemp);//p1LifeInt + lifeDistIntArr.get(0)
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
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //毒カウンターが変動した時の半透明な文字
    public void fn_p1PsnFade(int lifeIntTemp){
        float toTY = -5;
        switch (p1PsnDriver) {
            case 0:
                fn_translation(p1Psn_txt1, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p1Psn_txt2, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt2.setText(""+lifeIntTemp);//p1LifeInt + lifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p1Psn_txt3, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Psn_txt3.setText(""+lifeIntTemp);
                break;
        }
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //エネルギーカウンターが変動した時の半透明な文字
    public void fn_p1EngFade(int lifeIntTemp){
        float toTY = -5;
        switch (p1EngDriver) {
            case 0:
                fn_translation(p1Eng_txt1, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Eng_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p1Eng_txt2, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Eng_txt2.setText(""+lifeIntTemp);//p1LifeInt + lifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p1Eng_txt3, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p1Eng_txt3.setText(""+lifeIntTemp);
                break;
        }
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //ライフが変動した時の半透明な文字
    public void fn_p2LifeFade(int lifeIntTemp){
        float toTY = -30;
        switch (p2LifeDriver) {
            case 0:
                fn_translation(p2Life_txt1, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p2Life_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p2Life_txt2, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p2Life_txt2.setText(""+lifeIntTemp);//p2LifeInt + p2LifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p2Life_txt3, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p2Life_txt3.setText(""+lifeIntTemp);
                break;
            case 3:
                fn_translation(p2Life_txt4, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p2Life_txt4.setText(""+lifeIntTemp);
                break;
                /*
            case 4:
                fn_translation(p2Life_txt5, 0, toTY, fadeFontScale, fadeFontScale, lifeScaleDuration, "fadeout2");
                p2Life_txt5.setText("" + (lifeIntTemp));
                break;
                */
        }
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //毒カウンターが変動した時の半透明な文字
    public void fn_p2PsnFade(int lifeIntTemp){
        float toTY = -5;
        switch (p2PsnDriver) {
            case 0:
                fn_translation(p2Psn_txt1, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Psn_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p2Psn_txt2, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Psn_txt2.setText(""+lifeIntTemp);//p2LifeInt + p2LifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p2Psn_txt3, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Psn_txt3.setText(""+lifeIntTemp);
                break;
        }
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //エネルギーカウンターが変動した時の半透明な文字
    public void fn_p2EngFade(int lifeIntTemp){
        float toTY = -5;
        switch (p2EngDriver) {
            case 0:
                fn_translation(p2Eng_txt1, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Eng_txt1.setText(""+lifeIntTemp);//ここは関係ないはず
                break;
            case 1:
                fn_translation(p2Eng_txt2, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Eng_txt2.setText(""+lifeIntTemp);//p2LifeInt + p2LifeDistIntArr.get(0)
                break;
            case 2:
                fn_translation(p2Eng_txt3, 0, toTY, fadeCounterFontScale, fadeCounterFontScale, lifeScaleDuration, "fadeout2");
                p2Eng_txt3.setText(""+lifeIntTemp);
                break;
        }
        //se
        soundPool.play(btnSe,1.0f,1.0f,0,0,1);
    }
    //p1Life_txt p1LifeInt lifeDistInt plusminus p1LifeHisDriver isP1LifeHisMax
    public void fn_p1LifeDriver(int lifeDistInt) {
        mHandler.removeCallbacks(delayedUpdate);
        //lifeDistIntが０のときは何も起きてはいけない
        //ただ、ボタンから来た場合はDistIntが０でもOK
        if(lifeDistInt > 0){
            p1LifeList.add(p1LifeInt + " (+" + lifeDistInt + ")");
        } else {
            p1LifeList.add(p1LifeInt + " (" + lifeDistInt + ")");//マイナスは自動でつく
        }
        System.out.println("fn_p1LifeDriver lifeDistInt " + lifeDistInt);
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
        }
        //9だったとき
        //リストに突っ込んだ状態で動かす
        if (isP1LifeHisMax) {
            fn_p1LifeHisMove2(p1LifeList);
        } else {
            fn_p1LifeHisMove1(p1LifeList);
        }
    }
    public void fn_p2LifeDriver(int lifeDistInt) {
        mHandler.removeCallbacks(delayedUpdate);
        //p2LifeDistIntが０のときは何も起きてはいけない
        //ただ、ボタンから来た場合はDistIntが０でもOK
        if(lifeDistInt > 0){
            p2LifeList.add(p2LifeInt + " (+" + lifeDistInt + ")");
        } else {
            p2LifeList.add(p2LifeInt + " (" + lifeDistInt + ")");//マイナスは自動でつく
        }
        System.out.println("fn_p2LifeDriver lifeDistInt " + lifeDistInt);
        //p2Life_txt4の場合<4
        if (p2LifeHisDriver < 9) {
            p2LifeHisDriver++;
        } else {
            p2LifeHisDriver = 0;
        }
        //p2Life_txt4の場合>5
        if (p2LifeList.size() > 10) {
            p2LifeList.remove(0);
        }
        //p2Life_txt4の場合==4
        if (!isp2LifeHisMax && (p2LifeList.size() - 1) == 9) {
            isp2LifeHisMax = true;
        }
        //9だったとき
        //リストに突っ込んだ状態で動かす
        if (isp2LifeHisMax) {
            fn_p2LifeHisMove2(p2LifeList);
        } else {
            fn_p2LifeHisMove1(p2LifeList);
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
    private void fn_p1LifeHisMove1(ArrayList<String> arr) {
        int baseNum=80;//780
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
        int baseNum=80;//780
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

    private void fn_p2LifeHisMove1(ArrayList<String> arr) {
        int baseNum=80;//780
        switch(p2LifeHisDriver){
            case 0:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory1.setText( arr.get(0) );
                break;
            case 1:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory2.setText( arr.get(1) );
                break;
            case 2:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory3.setText( arr.get(2) );
                break;
            case 3:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory4.setText( arr.get(3) );
                break;
            case 4:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory5.setText( arr.get(4) );
                break;
            case 5:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory6.setText( arr.get(5) );
                break;
            case 6:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory7.setText( arr.get(6) );
                break;
            case 7:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory8.setText( arr.get(7) );
                break;
            case 8:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory9.setText( arr.get(8) );
                break;
        }

        Animator animator = rotateAnimator(mButton);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void fn_p2LifeHisMove2(ArrayList<String> arr) {
        int baseNum=80;//780
        switch(p2LifeHisDriver){
            case 9:
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory2.setText( arr.get(1) );
                p2lifeHistory3.setText( arr.get(2) );
                p2lifeHistory4.setText( arr.get(3) );
                p2lifeHistory5.setText( arr.get(4) );
                p2lifeHistory6.setText( arr.get(5) );
                p2lifeHistory7.setText( arr.get(6) );
                p2lifeHistory8.setText( arr.get(7) );
                p2lifeHistory9.setText( arr.get(8) );
                p2lifeHistory1.setText( arr.get(9) );
                break;
            case 0:
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory3.setText( arr.get(1) );
                p2lifeHistory4.setText( arr.get(2) );
                p2lifeHistory5.setText( arr.get(3) );
                p2lifeHistory6.setText( arr.get(4) );
                p2lifeHistory7.setText( arr.get(5) );
                p2lifeHistory8.setText( arr.get(6) );
                p2lifeHistory9.setText( arr.get(7) );
                p2lifeHistory1.setText( arr.get(8) );
                p2lifeHistory2.setText( arr.get(9) );
                break;
            case 1:
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory4.setText( arr.get(1) );
                p2lifeHistory5.setText( arr.get(2) );
                p2lifeHistory6.setText( arr.get(3) );
                p2lifeHistory7.setText( arr.get(4) );
                p2lifeHistory8.setText( arr.get(5) );
                p2lifeHistory9.setText( arr.get(6) );
                p2lifeHistory1.setText( arr.get(7) );
                p2lifeHistory2.setText( arr.get(8) );
                p2lifeHistory3.setText( arr.get(9) );
                break;
            case 2:
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory5.setText( arr.get(1) );
                p2lifeHistory6.setText( arr.get(2) );
                p2lifeHistory7.setText( arr.get(3) );
                p2lifeHistory8.setText( arr.get(4) );
                p2lifeHistory9.setText( arr.get(5) );
                p2lifeHistory1.setText( arr.get(6) );
                p2lifeHistory2.setText( arr.get(7) );
                p2lifeHistory3.setText( arr.get(8) );
                p2lifeHistory4.setText( arr.get(9) );
                break;
            case 3:
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");
                p2lifeHistory6.setText( arr.get(1) );
                p2lifeHistory7.setText( arr.get(2) );
                p2lifeHistory8.setText( arr.get(3) );
                p2lifeHistory9.setText( arr.get(4) );
                p2lifeHistory1.setText( arr.get(5) );
                p2lifeHistory2.setText( arr.get(6) );
                p2lifeHistory3.setText( arr.get(7) );
                p2lifeHistory4.setText( arr.get(8) );
                p2lifeHistory5.setText( arr.get(9) );
                break;
            case 4:
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory7.setText( arr.get(1) );
                p2lifeHistory8.setText( arr.get(2) );
                p2lifeHistory9.setText( arr.get(3) );
                p2lifeHistory1.setText( arr.get(4) );
                p2lifeHistory2.setText( arr.get(5) );
                p2lifeHistory3.setText( arr.get(6) );
                p2lifeHistory4.setText( arr.get(7) );
                p2lifeHistory5.setText( arr.get(8) );
                p2lifeHistory6.setText( arr.get(9) );
                break;
            case 5:
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory8.setText( arr.get(1) );
                p2lifeHistory9.setText( arr.get(2) );
                p2lifeHistory1.setText( arr.get(3) );
                p2lifeHistory2.setText( arr.get(4) );
                p2lifeHistory3.setText( arr.get(5) );
                p2lifeHistory4.setText( arr.get(6) );
                p2lifeHistory5.setText( arr.get(7) );
                p2lifeHistory6.setText( arr.get(8) );
                p2lifeHistory7.setText( arr.get(9) );
                break;
            case 6:
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory9.setText( arr.get(1) );
                p2lifeHistory1.setText( arr.get(2) );
                p2lifeHistory2.setText( arr.get(3) );
                p2lifeHistory3.setText( arr.get(4) );
                p2lifeHistory4.setText( arr.get(5) );
                p2lifeHistory5.setText( arr.get(6) );
                p2lifeHistory6.setText( arr.get(7) );
                p2lifeHistory7.setText( arr.get(8) );
                p2lifeHistory8.setText( arr.get(9) );
                break;
            case 7:
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory1.setText( arr.get(1) );
                p2lifeHistory2.setText( arr.get(2) );
                p2lifeHistory3.setText( arr.get(3) );
                p2lifeHistory4.setText( arr.get(4) );
                p2lifeHistory5.setText( arr.get(5) );
                p2lifeHistory6.setText( arr.get(6) );
                p2lifeHistory7.setText( arr.get(7) );
                p2lifeHistory8.setText( arr.get(8) );
                p2lifeHistory9.setText( arr.get(9) );
                break;
            case 8:
                fn_translation(p2lifeHistory2,baseNum-(hisTxtMove*8),baseNum-(hisTxtMove*9),lifeHisTransDuration,"fadeout");
                fn_translation(p2lifeHistory3,baseNum-(hisTxtMove*7),baseNum-(hisTxtMove*8),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory4,baseNum-(hisTxtMove*6),baseNum-(hisTxtMove*7),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory5,baseNum-(hisTxtMove*5),baseNum-(hisTxtMove*6),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory6,baseNum-(hisTxtMove*4),baseNum-(hisTxtMove*5),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory7,baseNum-(hisTxtMove*3),baseNum-(hisTxtMove*4),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory8,baseNum-(hisTxtMove*2),baseNum-(hisTxtMove*3),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory9,baseNum-(hisTxtMove*1),baseNum-(hisTxtMove*2),lifeHisTransDuration,"visible");
                fn_translation(p2lifeHistory1,baseNum-(hisTxtMove*0),baseNum-(hisTxtMove*1),lifeHisTransDuration,"fadein");

                p2lifeHistory2.setText( arr.get(1) );
                p2lifeHistory3.setText( arr.get(2) );
                p2lifeHistory4.setText( arr.get(3) );
                p2lifeHistory5.setText( arr.get(4) );
                p2lifeHistory6.setText( arr.get(5) );
                p2lifeHistory7.setText( arr.get(6) );
                p2lifeHistory8.setText( arr.get(7) );
                p2lifeHistory9.setText( arr.get(8) );
                p2lifeHistory1.setText( arr.get(9) );
                break;
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

        btnSizeX = p1plus1.getRight();
        btnSizeY = p1plus1.getBottom() - p1plus1.getTop();
        rectP1Plus1 = new RectF(p1plus1.getLeft(),p1plus1.getTop(),p1plus1.getRight(),p1plus1.getBottom());
        rectP1Plus5 = new RectF(p1plus5.getLeft(),p1plus5.getTop(),p1plus5.getRight(),p1plus5.getBottom());
        rectP1Minus1 = new RectF(p1minus1.getLeft(),p1minus1.getTop(),p1minus1.getRight(),p1minus1.getBottom());
        rectP1Minus5 = new RectF(p1minus5.getLeft(),p1minus5.getTop(),p1minus5.getRight(),p1minus5.getBottom());

        rectP1PsnPlus = new RectF(p1PsnPlus.getLeft(),p1PsnPlus.getTop(),p1PsnPlus.getRight(),p1PsnPlus.getBottom());
        rectP1PsnMinus = new RectF(p1PsnMinus.getLeft(),p1PsnMinus.getTop(),p1PsnMinus.getRight(),p1PsnMinus.getBottom());
        rectP1EngPlus = new RectF(p1EngPlus.getLeft(),p1EngPlus.getTop(),p1EngPlus.getRight(),p1EngPlus.getBottom());
        rectP1EngMinus = new RectF(p1EngMinus.getLeft(),p1EngMinus.getTop(),p1EngMinus.getRight(),p1EngMinus.getBottom());

        rectP2Plus1 = new RectF(p2plus1.getLeft(),p2plus1.getTop()+p2Layout.getTop(),p2plus1.getRight(),p2plus1.getBottom()+p2Layout.getTop());
        rectP2Plus5 = new RectF(p2plus5.getLeft(),p2plus5.getTop()+p2Layout.getTop(),p2plus5.getRight(),p2plus5.getBottom()+p2Layout.getTop());
        rectP2Minus1 = new RectF(p2minus1.getLeft(),p2minus1.getTop()+p2Layout.getTop(),p2minus1.getRight(),p2minus1.getBottom()+p2Layout.getTop());
        rectP2Minus5 = new RectF(p2minus5.getLeft(),p2minus5.getTop()+p2Layout.getTop(),p2minus5.getRight(),p2minus5.getBottom()+p2Layout.getTop());

        rectP2PsnPlus = new RectF(p2PsnPlus.getLeft(),p2PsnPlus.getTop()+p2Layout.getTop(),p2PsnPlus.getRight(),p2PsnPlus.getBottom()+p2Layout.getTop());
        rectP2PsnMinus = new RectF(p2PsnMinus.getLeft(),p2PsnMinus.getTop()+p2Layout.getTop(),p2PsnMinus.getRight(),p2PsnMinus.getBottom()+p2Layout.getTop());
        rectP2EngPlus = new RectF(p2EngPlus.getLeft(),p2EngPlus.getTop()+p2Layout.getTop(),p2EngPlus.getRight(),p2EngPlus.getBottom()+p2Layout.getTop());
        rectP2EngMinus = new RectF(p2EngMinus.getLeft(),p2EngMinus.getTop()+p2Layout.getTop(),p2EngMinus.getRight(),p2EngMinus.getBottom()+p2Layout.getTop());
    }
}
