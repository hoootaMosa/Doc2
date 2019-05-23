package com.material.doc;

import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

public class MultiScreens extends AppCompatActivity {
    private UltraViewPager ultraViewPager;
    private PagerAdapter adapter;
    private UltraViewPager.Orientation gravity_indicator;
    TextView go_to_tabs_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_screen);

        //intialaize the button
        go_to_tabs_button=(TextView) findViewById(R.id.getstarted);

        // handle the click event
        go_to_tabs_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MultiScreens.this, BottomTabActivity.class));
            }
        });

        // intialaize multi screens viewer
        ultraViewPager = (UltraViewPager) findViewById(R.id.ultra_viewpager);

//        defaultUltraViewPager();

        // multi screens viewer settings
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new MultiScreenPagerAdapter(true);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.setMultiScreen(0.6f);
        ultraViewPager.setAutoMeasureHeight(true);
        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
    }
}
