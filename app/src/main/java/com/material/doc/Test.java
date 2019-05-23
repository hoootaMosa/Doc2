package com.material.doc;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alamkanak.weekview.WeekView;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

public class Test extends AppCompatActivity {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;


    DonutProgress male,female,accepted,rejected,unresponded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        male=(DonutProgress) findViewById(R.id.male_progress);
        female=(DonutProgress) findViewById(R.id.female_progress);
        accepted=(DonutProgress) findViewById(R.id.accepted_progress);
        rejected=(DonutProgress) findViewById(R.id.rejected_progress);
        unresponded=(DonutProgress) findViewById(R.id.unresponded_progress);

        mWeekView = (WeekView) findViewById(R.id.weekView);

        male.setProgress(70);
        female.setProgress(30);
        accepted.setProgress(60);
        rejected.setProgress(30);
        unresponded.setProgress(10);

        mWeekViewType = TYPE_WEEK_VIEW;
        mWeekView.setNumberOfVisibleDays(7);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));




    }
}