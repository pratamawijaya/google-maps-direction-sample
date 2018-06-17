package com.pratamawijaya.googlemaphitungjarak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RC_BTN_A = 0;
    private static final int RC_BTN_B = 1;
    public static final String KEY_LAT = "key_lat";
    public static final String KEY_LNG = "key_lng";
    public static final String KEY_LAT_START = "key_lat_start";
    public static final String KEY_LNG_START = "key_lng_start";
    public static final String KEY_LAT_END = "key_lat_end";
    public static final String KEY_LNG_END = "key_lng_end";

    @BindView(R.id.tvLokasiA)
    TextView tvLokasiA;
    @BindView(R.id.tvLokasiB)
    TextView tvLokasiB;

    private double latStart, lngStart, latEnd, lngEnd;

    private Intent intentMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind butterknife
        ButterKnife.bind(this);

        intentMaps = new Intent(this, MapsActivity.class);
    }

    @OnClick(R.id.btnInputA)
    void onBtnAClick() {
        startActivityForResult(intentMaps, RC_BTN_A);
    }

    @OnClick(R.id.btnInputB)
    void onBtnBClick() {
        startActivityForResult(intentMaps, RC_BTN_B);
    }

    @OnClick(R.id.btnHitung)
    void onBtnHitungClick() {
        Intent intent = new Intent(this, DirectionActivity.class);
        intent.putExtra(MainActivity.KEY_LAT_START, latStart);
        intent.putExtra(MainActivity.KEY_LNG_START, lngStart);
        intent.putExtra(MainActivity.KEY_LAT_END, latEnd);
        intent.putExtra(MainActivity.KEY_LNG_END, lngEnd);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final double lat = data.getDoubleExtra(KEY_LAT, 0);
            final double lng = data.getDoubleExtra(KEY_LNG, 0);

            switch (requestCode) {
                case RC_BTN_A:
                    tvLokasiA.setText("lat: " + lat + " lng: " + lng);
                    latStart = lat;
                    lngStart = lng;
                    break;
                case RC_BTN_B:
                    tvLokasiB.setText("lat: " + lat + " lng: " + lng);
                    latEnd = lat;
                    lngEnd = lng;
                    break;
            }
        }
    }
}
