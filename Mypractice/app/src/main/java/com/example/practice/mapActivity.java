//package com.example.practice;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Looper;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;
//
////주변정류장을 띄우는 코드
//
//public class mapActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    MapView mapView;
//
//    @Override
//    public void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mapview);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//        Dexter.withContext(getApplicationContext())
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    private ViewGroup container;
//
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//
//                        buildLocationRequest();
//                        buildLocationCallback();
//                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplication());
//
//                        mapView = (MapView) findViewById(R.id.map);
//                        mapView.onCreate(savedInstanceState);
//                        mapView.onResume();
//                        mapView.getMapAsync(mapActivity.this);
//
//                        initArea();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.toast_layout,container,false);
//                        TextView text = layout.findViewById(R.id.text);
//                        Toast toast = new Toast(getApplication());
//                        text.setText("위치권한이 필요합니다.");
//                        text.setTextSize(15);
//                        text.setTextColor(Color.WHITE);
//                        toast.setGravity(Gravity.BOTTOM,0,0);
//                        toast.setDuration(Toast.LENGTH_SHORT);
//                        toast.setView(layout);
//                        toast.show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//
//                    }
//
//                }).check();
//    }
//
//    private void initArea() {
//    }
//
//    private void buildLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(500);
//        locationRequest.setSmallestDisplacement(10f);
//    }
//
//    private void buildLocationCallback() {
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(final LocationResult locationResult) {
//            }
//        };
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        mMap.addMarker(markerOptions);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//
//        if (fusedLocationProviderClient != null) {
//
//            if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//                return;
//            }
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//        }
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//        super.onStop();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//        if ((ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//
//            return;
//        }
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//        if (mMap != null) mMap.setMyLocationEnabled(true);
//    }
//}