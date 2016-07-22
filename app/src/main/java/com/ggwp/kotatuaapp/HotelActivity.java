package com.ggwp.kotatuaapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HotelActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        buildGoogleApiClient();

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
    public void performSearch() {
        // API use server API tested and worked
        final String URL_MAPS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-6.134841,106.813235&radius=10000&sensor=true&&types=lodging&key=AIzaSyA4isRf0pWofPg90-7bc48_BLukaMa4XYw";
        // pass second argument as "null" for GET requests

        JsonObjectRequest request = new JsonObjectRequest(URL_MAPS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s", response.toString());
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            // TODO lengkapi Picker
                            performPickerHelp(response);
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

                headers.put("key", "AIzaSyDNr5A9kgtWbZpr1--nFN9AIOUTOIVrL04");

                return headers;
            }
        };

        // add the request object to the queue to be executed
        mRequestQueue.add(request);
    }

    public void performPickerHelp(JSONObject object) {
        try {
            performPicker(object.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void performPicker(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject now = array.getJSONObject(i);
                String name = now.getString("name");
                double lat = now.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double lng = now.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                String place_id = now.getString("place_id");
                String vicinity = now.getString("vicinity");

                com.vistoworks.mediku.findhospital.activities.model.Places place = new com.vistoworks.mediku.findhospital.activities.model.Places(name,lat,lng,place_id,vicinity);
                mPlaces.put(name,place);

                performDetail(place.getPlace_id());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(lat, lng));
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place));
                Marker marker = mGoogleMap.addMarker(markerOptions);
                marker.setTitle(name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void performDetail(String place_id) {
        // API use server API tested and worked
        final String URL_MAPS = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyDNr5A9kgtWbZpr1--nFN9AIOUTOIVrL04&placeid=" + place_id;
        // pass second argument as "null" for GET requests

        JsonObjectRequest request = new JsonObjectRequest(URL_MAPS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s", response.toString());
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            // TODO lengkapi Picker
                            performDetailHelp(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AlertDialog.Builder b = new AlertDialog.Builder(FindHospitalActivity.this);
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

                headers.put("key", "AIzaSyDNr5A9kgtWbZpr1--nFN9AIOUTOIVrL04");

                return headers;
            }
        };

        // add the request object to the queue to be executed
        mRequestQueue.add(request);
    }

    public void performDetailHelp(JSONObject object) {
        try {
            performDetails(object.getJSONObject("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void performDetails(JSONObject object) {
        try {
            JSONObject now = object;
            String name = now.getString("name");

            String address = now.getString("formatted_address");
            String phone = now.getString("formatted_phone_number");

            com.vistoworks.mediku.findhospital.activities.model.Places place = mPlaces.get(name);
            place.setAddress(address);
            place.setPhone_number(phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
