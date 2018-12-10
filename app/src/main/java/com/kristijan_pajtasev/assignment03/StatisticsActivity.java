package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class StatisticsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        GPXHandlerUtil.decodeGPX(this, "myfile.gpx");
    }
}
