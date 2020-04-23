package com.Team25.intoapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    boolean nightView = false;
    String currentMapType = "com.Team25.intoapp:id/btnnormal";
    double[] pointerPosX;
    double[] pointerPosY;
    String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_manager);
        Bundle extras = getIntent().getExtras();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try{
            pointerPosX = extras.getDoubleArray("POINTER_POSX");
            pointerPosY = extras.getDoubleArray("POINTER_POSY");
            names = extras.getStringArray("POINTER_NAMES");
        }catch(Exception e){
            Log.e(TAG, "onCreate: Error passing map pointer data");
        }
        View.OnClickListener mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = v.getResources().getResourceName(v.getId());
                Log.d(TAG, "onClick: " + buttonName);
                if (buttonName != currentMapType){
                    switch (buttonName){
                        case "com.Team25.intoapp:id/btnnormal":
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            break;
                        case "com.Team25.intoapp:id/btnhybrid":
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            break;
                        case "com.Team25.intoapp:id/btnterrain":
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            break;
                        default:
                            break;
                    }currentMapType = buttonName;
                }
            }
        };
        Button btnNormal = findViewById(R.id.btnnormal);
        Button btnHybrid = findViewById(R.id.btnhybrid);
        Button btnTerrain = findViewById(R.id.btnterrain);

        btnNormal.setOnClickListener(mapButtonListener);
        btnHybrid.setOnClickListener(mapButtonListener);
        btnTerrain.setOnClickListener(mapButtonListener);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSetup(pointerPosX,pointerPosY,names);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        setMapLongClick(mMap);
    }

    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("title")
                        .snippet(snippet));
            }
        });
    }

    private void mapSetup(double[] mapPosX,double[] mapPosY,String[] names){
        Log.e(TAG, "mapSetup: maps setup attempted");
        for (int i = 0; i<mapPosX.length;i++){
            LatLng pointerPos = new LatLng(mapPosX[i],mapPosY[i]);
            String snippet = String.format(Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    pointerPos.latitude,
                    pointerPos.longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(pointerPos)
                    .title(names[i])
                    .snippet(snippet));
        }
    }


}
