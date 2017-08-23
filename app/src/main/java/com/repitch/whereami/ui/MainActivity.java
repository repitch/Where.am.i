package com.repitch.whereami.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.repitch.whereami.LocationService;
import com.repitch.whereami.R;
import com.repitch.whereami.db.entity.LocationTrack;
import com.repitch.whereami.db.repository.LocationTrackRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_PERMISSIONS = 0;
    private LocationTrackRepository locationTrackRepository;

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private View empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_service).setOnClickListener(v -> checkPermissions());
        findViewById(R.id.btn_refresh).setOnClickListener(v -> refresh());
        recyclerView = findViewById(R.id.recycler);
        empty = findViewById(R.id.empty);

        locationTrackRepository = new LocationTrackRepository();
        trackAdapter = new TrackAdapter(
                locationTrackRepository.getAll(),
                locationTrack -> startActivity(MapActivity.createIntent(this, locationTrack.getId()))
        );
        recyclerView.setAdapter(trackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (trackAdapter.getItemCount() == 0) {
            showEmpty();
        } else {
            showContent();
        }
    }

    private void refresh() {
        List<LocationTrack> tracks = locationTrackRepository.getAll();
        trackAdapter.updateData(tracks);
    }

    private void showContent() {
        empty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        empty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void checkPermissions() {
        List<String> absentPermissions = new ArrayList<>();
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                absentPermissions.add(permission);
            }
        }
        if (absentPermissions.isEmpty()) {
            startService(LocationService.createIntent(this));
        } else {
            String[] permissions = absentPermissions.toArray(new String[absentPermissions.size()]);
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                checkPermissions();
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
