package com.websarva.wings.android.animationsample;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

//calligraphyの初期化
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
