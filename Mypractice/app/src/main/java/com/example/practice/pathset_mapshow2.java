package com.example.practice;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//이거안되면 걍 위도경도도 수작업하려고함

//pathSetting_end와 이어짐
//리스트 클릭 시 그 특정 정류장을 보여주는 클래스
/*https://coding-factory.tistory.com/39*/

public class pathset_mapshow2 extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    private pathSetting_end pathSetting_end;
    int a, b;
    int colTotal;
    String str_a;
    String str_b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.map_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_search);
        mapFragment.getMapAsync(this);
        Excel();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng POINT = new LatLng(a, b);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POINT);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POINT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        Toast.makeText(this,          // 현재 화면의 제어권자
                a+"와"+b, // 보여줄 메시지
                Toast.LENGTH_LONG)    // 보여줄 기간 (길게, 짧게)
                .show();

    }


    private void Excel() {
        Workbook workbook=null;
        Sheet sheet=null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("station_2020_August.xlsx");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);
            colTotal = sheet.getColumns();   //전체 열(세로) 수

            for(int colstart=0; colstart<colTotal+2; colstart++){

                String excelload = sheet.getCell(colstart, 0).getContents();
                int excelload_2 =  Integer.parseInt(excelload);
                int int_mytest = Integer.parseInt(String.valueOf(pathSetting_end.tv));
                if(excelload == String.valueOf(pathSetting_end.tv)){
                    int save_col = colstart;
                    str_a = sheet.getCell(save_col-3,0).getContents();
                    str_b = sheet.getCell(save_col-2,0).getContents();
                    a =  Integer.parseInt(str_a);
                    b =  Integer.parseInt(str_b);
                }break;
            }

        }
        catch (IOException e) {e.printStackTrace();}
        catch (BiffException e) {e.printStackTrace();}
        finally {
            if(workbook != null) {
                workbook.close();
            }
        }
    }
}