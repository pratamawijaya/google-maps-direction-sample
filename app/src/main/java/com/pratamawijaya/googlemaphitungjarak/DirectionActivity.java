package com.pratamawijaya.googlemaphitungjarak;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DirectionActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private GoogleMap mMap;

    private double latStart, lngStart, latEnd, lngEnd;

    @BindView(R.id.tvInfo)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        ButterKnife.bind(this);

        latStart = getIntent().getDoubleExtra(MainActivity.KEY_LAT_START, 0);
        lngStart = getIntent().getDoubleExtra(MainActivity.KEY_LNG_START, 0);
        latEnd = getIntent().getDoubleExtra(MainActivity.KEY_LAT_END, 0);
        lngEnd = getIntent().getDoubleExtra(MainActivity.KEY_LNG_END, 0);

        LatLng start = new LatLng(latStart, lngStart);
        LatLng end = new LatLng(latEnd, lngEnd);

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .key("key-direction-api")
                .waypoints(start, end)
                .withListener(this)
                .build();
        routing.execute();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(latStart, lngStart);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.e("tag", "error " + e.getMessage());
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> routes, int i) {
        Log.d("tag", "routing success " + routes.size());

        mMap.addMarker(new MarkerOptions().position(new LatLng(latStart, lngStart)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latEnd, lngEnd)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latStart, lngStart), 14));

        String txtInfo = "";

        for (Route data : routes) {
            // draw polyline
            Log.d("tag", "write polyline " + data.getDistanceText());
            txtInfo += data.getDistanceText() + " " + data.getDurationText();

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10);
            polylineOptions.color(Color.RED);
            polylineOptions.addAll(data.getPoints());
            mMap.addPolyline(polylineOptions);
        }

        tvInfo.setText(txtInfo);

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
