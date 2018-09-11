package com.websarva.wings.android.animationsample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class p1CounterFragment extends android.support.v4.app.Fragment {
    Button button2;


    public static p1CounterFragment newInstance(int counterNum) {
        //p1LifeList = new ArrayList<>();
        //    public ArrayList<String> p1LifeList;
        //インスタンス生成
        p1CounterFragment fragment = new p1CounterFragment();
        //Bundleにパラメータを設定
        Bundle barg = new Bundle();
        barg.putInt("counterNumKey", counterNum);
        fragment.setArguments(barg);

        return fragment;
    }

    //FragmentのViewを生成して返す
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.counterfragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);

        Bundle args = getArguments();
        if(args != null){
            int num = args.getInt("counterNumKey");
            TextView poisonTxtView = (TextView) view.findViewById(R.id.poisonCnt_txt);
            poisonTxtView.setText("poisonCnt "+num);
            TextView energyTxtView = (TextView) view.findViewById(R.id.energyCnt_txt);
            energyTxtView.setText("energynt "+num);
        }

    }

}
