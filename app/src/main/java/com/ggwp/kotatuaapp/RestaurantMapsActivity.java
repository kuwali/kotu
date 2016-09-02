package com.ggwp.kotatuaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.ViewTransformer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class RestaurantMapsActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        DirectionCallback {

    private GoogleMap mMap;
    private BottomSheetLayout bottomSheetLayout;

    protected LatLng start = new LatLng(-6.134841,106.813235);
    protected LatLng end;

    private String name;
    private String address;
    private String phone;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_place);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_place);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map_place, mapFragment).commit();
        }
        MapsInitializer.initialize(this);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lat = Double.parseDouble(bundle.getString("lat"));
        lng = Double.parseDouble(bundle.getString("lng"));

        name = bundle.getString("name");
        address = bundle.getString("address");
        phone = bundle.getString("phone");

        end = new LatLng(lat, lng);

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View v = LayoutInflater.from(RestaurantMapsActivity.this).inflate(R.layout.bottom_sheet_place, null);
                TextView addressTextView = (TextView) v.findViewById(R.id.address_text_view);
                TextView phoneTextView = (TextView) v.findViewById(R.id.phone_number_text_view);
                TextView placeNameTextView = (TextView) v.findViewById(R.id.place_name_text_view);
                if (!marker.getTitle().equalsIgnoreCase("Kota Tua")) {
                    addressTextView.setText(address);
                    if (phone.equals("")) {
                        phoneTextView.setText("-");
                        v.findViewById(R.id.call_fab).setVisibility(View.INVISIBLE);
                    } else {
                        phoneTextView.setText(phone);
                    }
                    placeNameTextView.setText(name);
                } else {
                    addressTextView.setText("Jakarta Barat");
                    phoneTextView.setText("-");
                    v.findViewById(R.id.call_fab).setVisibility(View.INVISIBLE);
                    v.findViewById(R.id.navigate_button).setVisibility(View.INVISIBLE);
                    placeNameTextView.setText("Kota Tua");
                }

                bottomSheetLayout.setShouldDimContentView(false);

                bottomSheetLayout.showWithSheetView(v, new ViewTransformer() {
                    @Override
                    public void transformView(float translation, float maxTranslation, float peekedTranslation, BottomSheetLayout parent, View view) {
                        float progress = Math.min(translation / peekedTranslation, 1);
                        float scale = (1 - progress) + progress * 0.9f;
//                        view.setScaleX(scale);
//                        view.setScaleY(scale);

                        if (translation == 0 || translation == parent.getHeight()) {
                            parent.setBackgroundColor(0);
                            ensureLayer(view, View.LAYER_TYPE_NONE);
                        } else {
                            parent.setBackgroundColor(0);
                            ensureLayer(view, View.LAYER_TYPE_HARDWARE);
                        }

                        float translationToTop = -(view.getHeight() * (1 - scale)) / 2;
                        view.setTranslationY(translationToTop + progress * 20 * view.getContext().getResources().getDisplayMetrics().density);
                    }

                    private void ensureLayer(View view, int layerType) {
                        if (view.getLayerType() != layerType) {
                            view.setLayerType(layerType, null);
                        }
                    }

                    @Override
                    public float getDimAlpha(float v, float v1, float v2, BottomSheetLayout bottomSheetLayout, View view) {
                        return 0;
                    }
                });

                v.findViewById(R.id.navigate_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
                v.findViewById(R.id.call_fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (phone.equals("")) {
                            Toast.makeText(getApplicationContext(), "No Phone Number", Toast.LENGTH_LONG);
                        } else {
                            String number = "tel:" + phone
                                    .replaceAll("\\(", "")
                                    .replaceAll("\\)", "")
                                    .replaceAll(" ", "")
                                    .trim();
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                            startActivity(callIntent);
                        }
                    }
                });
                return false;
            }
        });

        updateUI();
    }


    public void updateUI() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 16));
        requestDirection();
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            mMap.addMarker(new MarkerOptions().position(start))
                .setTitle("Kota Tua");
            mMap.addMarker(new MarkerOptions().position(end))
                .setTitle(name);

            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void requestDirection() {
        GoogleDirection.withServerKey("AIzaSyDebE23pIg9PQMaejIufwtU-WaQU1b5M_c")
                .from(start)
                .to(end)
                .transportMode(TransportMode.WALKING)
                .execute(this);
    }
}
