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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** The global variables and tags required to make the map work as intended and allow the passing of information between methods.
     */
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    String currentMapType = "com.Team25.intoapp:id/btnnormal";
    double[] pointerPosX;
    double[] pointerPosY;
    String[] names;

    /** On create the map first initalises the fragment and loads it onto the map manager activity.
     *  The extras is then obtained and checked to see if marker positions have been passd to be added to the map after it has been created.
     *  @param savedInstanceState - The information and any extras needed by the oncreate method to launch the activity.
     *  After the information has been check the buttons on the mapManager are linked to code objects and attached to a listener allowing the map to be
     *      switched to differn't views.
     */
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
     * Also automates the generation of map markers based on data passed from the database query by the bundle.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSetup(pointerPosX,pointerPosY,names);
        LatLng into = new LatLng(54.9778428, -1.6166137);
        mMap.addMarker(new MarkerOptions().position(into).title("Into Newcastle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(into));
        setMapLongClick(mMap);
    }

    /** if a user presses and holds on the map it will create a marker so they can set there own potential way points.
     * @param map - The map being manipulated.
     */
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

    /** A method to place markers on the map from the database.
     * @param mapPosX - A list of the markers x coordinates.
     * @param mapPosY - A list of the markers y coordinates.
     * @param names - A list of the markers names.
     */

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
