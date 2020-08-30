package com.example.practice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Map;

public class Frag2 extends Fragment implements OnMapReadyCallback {


    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Button button;
    MapView mapView;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag2, container,false);

        button = view.findViewById(R.id.frag_button);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        buildLocationRequest();
                        buildLocationCallback();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

                        mapView = (MapView) view.findViewById(R.id.map);
                        mapView.onCreate(savedInstanceState);
                        mapView.onResume();
                        mapView.getMapAsync(Frag2.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,container,false);
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getActivity());
                        text.setText("위치권한이 필요합니다.");
                        text.setTextSize(15);
                        text.setTextColor(Color.WHITE);
                        toast.setGravity(Gravity.BOTTOM,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }

                }).check();


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initArea(mMap);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (fusedLocationProviderClient != null) {

            if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        button.setOnClickListener(new View.OnClickListener() //조회 버튼 누르면
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), pathSetting_start.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setSmallestDisplacement(10f);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {

            }
        };
    }

    private void initArea(GoogleMap googleMap) {
        LatLng DEFAULT_LOCATION = new LatLng(37.498095, 127.027610);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 10);
        mMap.moveCamera(cameraUpdate);
    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        if (mMap != null) mMap.setMyLocationEnabled(true);
    }

//
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//    Button button;
//    MapView mapView;
//    private GoogleMap mMap;
//    public Frag2()
//    {
//        // required
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.frag2, container, false);
//
//        button = view.findViewById(R.id.frag_button);
//        mapView = (MapView)view.findViewById(R.id.map);
//        mapView.getMapAsync(this);
//
//        Dexter.withActivity(getActivity())
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//
//                        buildLocationRequest();
//                        buildLocationCallback();
//                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//                        mapView = (MapView) view.findViewById(R.id.map);
//                        mapView.onCreate(savedInstanceState);
//                        mapView.onResume();
//                        mapView.getMapAsync(Frag2.this);
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.toast_layout,container,false);
//                        TextView text = layout.findViewById(R.id.text);
//                        Toast toast = new Toast(getActivity());
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
//
//
//
//        return view;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        //mMap.getUiSettings().setZoomControlsEnabled(true);
//        initArea(mMap);
//
//        if (fusedLocationProviderClient != null) {
//
//            if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//                return;
//            }
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//        }
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        button.setOnClickListener(new View.OnClickListener() //조회 버튼 누르면
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), pathSetting_start.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initArea(GoogleMap mMap) {
//        GoogleMap mMap = null;
//        LatLng DEFAULT_LOCATION = new LatLng(37.498095, 127.027610);
//        Marker marker = mMap.addMarker(new MarkerOptions()
//                .position(DEFAULT_LOCATION)
//                .title("위치 서비스 비활성화")
//                .snippet("확인바랍니다."));
//        marker.showInfoWindow();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
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
//
//            }
//        };
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            return;
//        }
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//        if (mMap != null) mMap.setMyLocationEnabled(true);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onLowMemory();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        //액티비티가 처음 생성될 때 실행되는 함수
//
//        if(mapView != null)
//        {
//            mapView.onCreate(savedInstanceState);
//        }
//    }
//
//

}
