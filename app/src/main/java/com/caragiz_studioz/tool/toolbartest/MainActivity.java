package com.caragiz_studioz.tool.toolbartest;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.florent37.arclayout.ArcLayout;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
ArcLayout testing;
    AppBarLayout appBar;
    int scrollRange = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testing = (ArcLayout)findViewById(R.id.testthis);

//        appBar = (AppBarLayout)findViewById(R.id.appbar);
//
//        appBar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(scrollRange == -1)
            scrollRange = appBarLayout.getTotalScrollRange();

        float scale = 1 + verticalOffset / Float.valueOf(scrollRange);

//        toolbarArcBackground.setScale(scale);

        if(scale<=0 )
            appBarLayout.setElevation(5f);
        else
            appBarLayout.setElevation(0f);
    }
}
