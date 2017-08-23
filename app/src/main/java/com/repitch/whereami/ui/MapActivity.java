package com.repitch.whereami.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.repitch.whereami.R;
import com.repitch.whereami.db.entity.LocationTrack;
import com.repitch.whereami.db.repository.LocationTrackRepository;

/**
 * Created by repitch on 24.08.17.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String EXTRA_TRACK_ID = "track_id";
    private GoogleMap googleMap;
    private LocationTrack locationTrack;
    private LocationTrackRepository locationTrackRepository;

    private Marker marker;
    private TextView txtLocation;
    private TextView txtTime;

    public static Intent createIntent(Context context, int trackId) {
        return new Intent(context, MapActivity.class)
                .putExtra(EXTRA_TRACK_ID, trackId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationTrackRepository = new LocationTrackRepository();
        int trackId = getIntent().getIntExtra(EXTRA_TRACK_ID, -1);
        locationTrack = locationTrackRepository.getById(trackId);

        txtLocation = findViewById(R.id.txt_location);
        txtTime = findViewById(R.id.txt_time);
        txtLocation.setText(String.format("%f %f", locationTrack.getLatitude(), locationTrack.getLongitude()));
        txtTime.setText(locationTrack.getTime().toString("dd MMM, HH:mm:ss"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(locationTrack.getLatitude(), locationTrack.getLongitude());
        float iconColor = BitmapDescriptorFactory.HUE_AZURE;
        marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(iconColor)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }
}
