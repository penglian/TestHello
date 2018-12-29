package com.example.pl.testhello.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.basehello.location.SimpleLocation;
import com.example.pl.testhello.R;
import com.example.pl.testhello.base.BaseActivity;

/**
 * desc:
 * created by pl at 2018/12/29
 */
public class TestLocatioActitity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_test_location;
    }


    private SimpleLocation mLocation;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        // construct a new instance
        mLocation = new SimpleLocation(this);

        // reduce the precision to 5,000m for privacy reasons
        mLocation.setBlurRadius(5000);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final double latitude = mLocation.getLatitude();
                final double longitude = mLocation.getLongitude();

                Toast.makeText(TestLocatioActitity.this, "Latitude: "+latitude, Toast.LENGTH_SHORT).show();
                Toast.makeText(TestLocatioActitity.this, "Longitude: "+longitude, Toast.LENGTH_SHORT).show();
            }

        });
        // if we can't access the location yet
        if (!mLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        mLocation.beginUpdates();
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        mLocation.endUpdates();

        super.onPause();
    }

}
