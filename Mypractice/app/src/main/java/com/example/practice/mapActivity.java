package com.example.practice;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Locale;

//주변 정류장 띄우는 클래스
/*수정중~~*/
public class mapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Double my_name,my_mobileNo,my_x,my_y;
    String str_name,str_mobileNo,str_x,str_y;

    private final String key = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";

    private GoogleMap gMap;
    public GoogleApiClient mGoogleApiClient = null;
    private FragmentActivity mActivity;
    public Marker marker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentlocation;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition;

    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
    String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        Log.d(tag, "순서 1 : onCreate");
        mActivity = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(tag,"순서 3 : 쓰레드 내부");

                getBusStationAroundList();

                //정류장 위도경도 조회
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(tag, "runOnUi쓰레드 내부");
                        makemarker(gMap);
                    }
                });
            }
        }).start();

        Log.d(tag, "순서 2 : 쓰레드 바깥");
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){

            @Override
            public boolean onMyLocationButtonClick() {

                Log.d(tag, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");
                mMoveMapByAPI = true;
                return true;
            }
        });
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(tag, "onMapClick :");
            }
        });

        gMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {
                if (mMoveMapByUser && mRequestingLocationUpdates){

                    Log.d(tag, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }
                mMoveMapByUser = true;
            }
        });

        gMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {}
        });
    }

    public String getBusStationAroundList() //주변 정류소 조회
    {
        Log.d(tag,"파싱");
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://openapi.gbis.go.kr/ws/rest/busstationservice/searcharound"//요청 URL
                + "?serviceKey=" + key
                + "x=" + my_x
                + "&y=" + my_y;

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            Log.d(tag, queryUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag= null;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(tag, "파싱시작");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("busStationAroundList")) ;// 첫번째 검색결과
                        else if (tag.equals("stationName")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_name = xpp.getText();
                            //my_name = Double.parseDouble(str_name);
                            //buffer.append("\n");
                        }
                        else if (tag.equals("mobileNo")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_mobileNo = xpp.getText();
                            my_mobileNo = Double.parseDouble(str_mobileNo);
                            //buffer.append("\n");
                        }

                        else if (tag.equals("x")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_x = xpp.getText();
                            my_y = Double.parseDouble(str_x);
                            //buffer.append("\n");
                        }
                        else if (tag.equals("y")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_y = xpp.getText();
                            my_x = Double.parseDouble(str_y);

                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("busStationList"));// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {Log.d(tag, "에러발생"); }
        Log.d(tag, "위도 : " + my_y + "\n경도 : " + my_x);
        Log.d(tag, str_name + "과 " + str_mobileNo);
        Log.d(tag, "파싱종료");
        return buffer.toString();
    }

    /*
    public String getStationLocationList() //정류장 위도 경도 파싱

    {
        Log.d(tag,"순서 4 : 파싱");
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://openapi.gbis.go.kr/ws/rest/busstationservice"//요청 URL
                + "?serviceKey=" + key
                + "&keyword=" + aaaa;

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            Log.d(tag, queryUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag= null;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(tag, "파싱시작");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("busStationList")) ;// 첫번째 검색결과
                        else if(tag.equals("mobileNo")) {
                            int a = xpp.next();
                            if (a != mytest_int) //mobileNo랑 다르면
                            {
                                break;
                            }
                        }
                        else if (tag.equals("x")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_x = xpp.getText();
                            double_y = Double.parseDouble(str_x);
                            //buffer.append("\n");
                        }
                        else if (tag.equals("y")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_y = xpp.getText();
                            double_x = Double.parseDouble(str_y);

                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("busStationList"));// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            Log.d(tag, "에러발생"); }
        Log.d(tag, "위도 : " + double_y + "\n경도 : " + double_x);
        Log.d(tag, "파싱종료");
        return buffer.toString();
    }
     */
    private void makemarker(GoogleMap googleMap) //마커찍기
    {
        gMap = googleMap;
        Log.d(tag,"정류장 마커찍기");
        Log.d(tag,"위도 : " +my_x + "\n경도 : " + my_y);

        LatLng myLocation = new LatLng(my_x, my_y);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myLocation)
                .title(str_name)
                .snippet(str_mobileNo);
        gMap.addMarker(markerOptions);
        marker = gMap.addMarker(markerOptions);
        marker.showInfoWindow();

    }

    @Override
    protected void onStart() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() == false){

            Log.d(tag, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onStop() {if (mRequestingLocationUpdates) {

        Log.d(tag, "onStop : call stopLocationUpdates");
        stopLocationUpdates();
    }

        if ( mGoogleApiClient.isConnected()) {

            Log.d(tag, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {

            Log.d(tag, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }


        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;

                checkPermissions();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.");
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(tag, "checkPermissions : 퍼미션 가지고 있음");

            if ( mGoogleApiClient.isConnected() == false) {

                Log.d(tag, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permsRequestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {

            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (permissionAccepted) {


                if ( mGoogleApiClient.isConnected() == false) {

                    Log.d(tag, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }
            } else {

                checkPermissions();
            }
        }
    }

    private void showDialogForPermissionSetting(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void startLocationUpdates() {
        if (!checkLocationServicesStatus()) {

            Log.d(tag, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(tag, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }

            //FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
            //이거 수정해야함
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            mRequestingLocationUpdates = true;

            gMap.setMyLocationEnabled(true);

        }
    }

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void stopLocationUpdates() {

        Log.d(tag,"stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        mRequestingLocationUpdates = false;
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        Log.d(tag, "setCurrentLocation안으로 들어옴");
        mMoveMapByUser = false;
        //if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title("내위치");
        marker = gMap.addMarker(markerOptions);
        marker.showInfoWindow();

        if ( mMoveMapByAPI ) {

            Log.d(tag, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            gMap.moveCamera(cameraUpdate);
        }
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentPosition
                = new LatLng( location.getLatitude(), location.getLongitude());


        Log.d(tag, "onLocationChanged : ");

        String markerTitle = getCurrentAddress(currentPosition);
        String markerSnippet = "위도:" + location.getLatitude()
                + " 경도:" + location.getLongitude();

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet);
        Log.d(tag, "setCurrentLocation : 내 위치 마커 생성하고 이동");

        mCurrentlocation = location;
    }

    private String getCurrentAddress(LatLng latlng) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(tag, "onActivityResult : 퍼미션 가지고 있음");


                        if ( mGoogleApiClient.isConnected() == false ) {

                            Log.d(tag, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }

                break;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }

    @Override
    public void onProviderEnabled(String provider) {    }

    @Override
    public void onProviderDisabled(String provider) {    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if ( mRequestingLocationUpdates == false ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                } else {

                    Log.d(tag, "onConnected : 퍼미션 가지고 있음");
                    Log.d(tag, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    gMap.setMyLocationEnabled(true);
                }

            }else{

                Log.d(tag, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                gMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(tag, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(tag, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(tag, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(tag, "onConnectionFailed");
        //setDefaultLocation();
    }

}



//자기위치 뜨게 바꿔야함




//package com.example.practice;
//
////https://webnautes.tistory.com/647
//
//public class Frag2 extends Fragment implements OnMapReadyCallback{
//    private MapView mapView = null;
//
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
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag2, container, false);
//
//        mapView = (MapView)view.findViewById(R.id.map);
//        mapView.getMapAsync(this);
//
//        return view;
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
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        markerOptions.position(SEOUL);
//
//        markerOptions.title("서울");
//
//        markerOptions.snippet("수도");
//
//        googleMap.addMarker(markerOptions);
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
//    }
//
//}