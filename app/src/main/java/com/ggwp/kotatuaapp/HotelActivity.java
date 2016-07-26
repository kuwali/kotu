package com.ggwp.kotatuaapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.ViewTransformer;
import com.ggwp.kotatuaapp.model.HotelAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private RequestQueue mRequestQueue;
    private BottomSheetLayout bottomSheetLayout;
    protected HashMap<String, com.ggwp.kotatuaapp.model.Places> mPlaces = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet_hotel);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        buildGoogleApiClient();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_hotel);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapsInitializer.initialize(getApplicationContext());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                        LatLng(-6.134841,106.813235), 15));
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(new LatLng(-6.134841,106.813235));
////                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_search_white_36dp));
//                Marker marker = googleMap.addMarker(markerOptions);
//                marker.setTitle("Kota Tua");

                performSearch(googleMap);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        View v = LayoutInflater.from(HotelActivity.this).inflate(R.layout.bottom_sheet_hospital, null);
                        TextView addressTextView = (TextView) v.findViewById(R.id.address_text_view);
                        TextView phoneTextView = (TextView) v.findViewById(R.id.phone_number_text_view);
                        TextView placeNameTextView = (TextView) v.findViewById(R.id.place_name_text_view);
                        final com.ggwp.kotatuaapp.model.Places place = mPlaces.get(marker.getId());
                        if (place != null) {
                            if (place.getAddress().isEmpty() || place.getAddress() == null || place.getAddress().equals("")) {
                                addressTextView.setText(place.getVicinity());
                            } else {
                                addressTextView.setText(place.getAddress());
                            }
                            if (place.getPhone_number().equals("")) {
                                phoneTextView.setText("-");
                                v.findViewById(R.id.call_fab).setVisibility(View.INVISIBLE);
                            } else {
                                phoneTextView.setText(place.getPhone_number());
                            }
                            placeNameTextView.setText(place.getName());
                            bottomSheetLayout.setShouldDimContentView(false);
                        }

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
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place.getLat() + "," + place.getLng());
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }
                        });
                        v.findViewById(R.id.call_fab).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (place.getPhone_number().equals("")) {
                                    Toast.makeText(getApplicationContext(), "No Phone Number", Toast.LENGTH_LONG);
                                } else {
                                    String number = "tel:" + place.getPhone_number()
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
            }
        });



//        count = 0;
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setProgress(0);
//        progressDialog.show();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this,this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    public void performSearch(final GoogleMap googleMap) {
        // API use server API tested and worked
        final String URL_MAPS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-6.134841,106.813235&radius=1500&sensor=true&&types=lodging&key=AIzaSyAsVc8QhVvfIuSQAQAKR18QwoITPeegocE";
        // pass second argument as "null" for GET requests

        JsonObjectRequest request = new JsonObjectRequest(URL_MAPS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s", response.toString());
                            // TODO lengkapi Picker
                            performPickerHelp(response, googleMap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AlertDialog.Builder b = new AlertDialog.Builder(HotelActivity.this);
                b.setTitle("Network error")
                        .setMessage("Periksa kembali jaringan Anda.")
                        .setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                onBackPressed();
                            }
                        });
                b.create().show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("key", "AIzaSyAsVc8QhVvfIuSQAQAKR18QwoITPeegocE");

                return headers;
            }
        };
        // add the request object to the queue to be executed
        mRequestQueue.add(request);
    }

    public void performPickerHelp(JSONObject object, GoogleMap googleMap) {
        try {
            performPicker(object.getJSONArray("results"), googleMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void performPicker(JSONArray array, GoogleMap googleMap) {
        for (int i = 0; i < array.length() && i < 20; i++) {
            try {
                JSONObject now = array.getJSONObject(i);
                String name = now.getString("name");
                String place_id = now.getString("place_id");
                double lat = now.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double lng = now.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                String vicinity = now.getString("vicinity");
                performDetail(place_id, googleMap);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void performDetail(String place_id, final GoogleMap googleMap) {
        // API use server API tested and worked
        final String URL_MAPS = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyAsVc8QhVvfIuSQAQAKR18QwoITPeegocE&placeid=" + place_id;
        // pass second argument as "null" for GET requests


        JsonObjectRequest request = new JsonObjectRequest(URL_MAPS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s", response.toString());
//                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            // TODO lengkapi Picker
                            performDetailHelp(response, googleMap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AlertDialog.Builder b = new AlertDialog.Builder(HotelActivity.this);
                b.setTitle("Network error")
                        .setMessage("Periksa kembali jaringan Anda.")
                        .setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                onBackPressed();
                            }
                        });
                b.create().show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("key", "AIzaSyAsVc8QhVvfIuSQAQAKR18QwoITPeegocE");

                return headers;
            }
        };

        // add the request object to the queue to be executed
        mRequestQueue.add(request);
    }

    public void performDetailHelp(JSONObject object, GoogleMap googleMap) {
        try {
            performDetails(object.getJSONObject("result"), googleMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void performDetails(JSONObject object, GoogleMap googleMap) {
        try {
            JSONObject now = object;
            String placeId = now.getString("place_id");
            String name = now.getString("name");
            double lat = now.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            double lng = now.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            String vicinity = now.getString("vicinity");
            String address = now.getString("formatted_address");
            String phone = now.getString("formatted_phone_number");
            String rating = now.getString("rating");
//            String price = now.getString("price_level");

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, lng));
//                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_search_white_36dp));
            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTitle(name);

            mPlaces.put(marker.getId(), new com.ggwp.kotatuaapp.model.Places(name, lat, lng, placeId, vicinity, address, phone, "", rating));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
