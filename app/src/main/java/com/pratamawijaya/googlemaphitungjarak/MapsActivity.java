package com.pratamawijaya.googlemaphitungjarak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.0129198, 107.3557821);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Jakarta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));
        mMap.setOnCameraMoveListener(this);
    }

    @OnClick(R.id.btnPilih)
    void onBtnPilihClick() {
        final LatLng currentLocation = mMap.getCameraPosition().target;
        Log.d(MapsActivity.class.getSimpleName(), "current location " + currentLocation.latitude);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.KEY_LAT, currentLocation.latitude);
        returnIntent.putExtra(MainActivity.KEY_LNG, currentLocation.longitude);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onCameraMove() {
        final LatLng currentLocation = mMap.getCameraPosition().target;
        Log.d(MapsActivity.class.getSimpleName(), "current location " + currentLocation.latitude + " " + currentLocation.longitude);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
