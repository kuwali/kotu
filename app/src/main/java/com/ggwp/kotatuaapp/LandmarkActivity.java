package com.ggwp.kotatuaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ggwp.kotatuaapp.model.HotelAdapter;
import com.ggwp.kotatuaapp.model.LandmarkAdapter;
import com.ggwp.kotatuaapp.model.Places;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuwali on 7/25/16.
 */
public class LandmarkActivity extends AppCompatActivity {

    List<Places> landmarks = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        ListView landmarkListView = (ListView) findViewById(R.id.landmark_list_view);

        try {
            JSONObject placesJSON = new JSONObject(" {\"places\":[{\"name\":\"Museum Sejarah Jakarta\",\"lat\":-6.1352,\"lng\":106.8133,\"place_id\":\"ID00016\",\"address\":\"Jalan Taman Fatahillah No.1, Pinangsia, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6929101\",\"price\":\"Dewasa:Rp5.000/Orang,Mahasiswa:Rp3.000/Orang,Pelajar/Anak-anak:Rp2.000/Orang\",\"hours\":\"09.00-15.00(Selasa-Minggu),Senin dan Hari Libur Nasional Tutup\"},{\"name\":\"Museum Wayang\",\"lat\":-6.1348764,\"lng\":106.812582,\"place_id\":\"ID00017\",\"address\":\"Jl. Pintu Besar Utara No.27, Pinangsia, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6929560\",\"price\":\"Dewasa:Rp5.000/Orang, Mahasiswa:Rp3.000/Orang, Pelajar/Anak-anak:Rp2.000/Orang\",\"hours\":\"09.00-15.00(Selasa-Minggu),Senin dan Hari Libur Nasional Tutup\"},{\"name\":\"Museum Seni Rupa dan Keramik\",\"lat\":-6.134237,\"lng\":106.8143955,\"place_id\":\"ID00018\",\"address\":\"Jl. Pos Kota No.2, Pinangsia, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6926090\",\"price\":\"Dewasa:Rp5.000/Orang, Mahasiswa:Rp3.000/Orang, Pelajar/Anak-anak:Rp2.000/Orang\",\"hours\":\"09.00-15.00(Selasa-Minggu),Senin dan Hari Libur Nasional Tutup\"},{\"name\":\"Museum Bank Indonesia\",\"lat\":-6.1371972,\"lng\":106.8127084,\"place_id\":\"ID00019\",\"address\":\"Jalan Pintu Besar Utara No. 3, Pinangsia, Jakarta Barat, DKI Jakarta, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 2600158\",\"price\":\"Dewasa dan Anak-anak:Rp5.000, Mahasiswa/Pelajar Gratis\",\"hours\":\"08.00-15.30(Selasa-Jumat),08.00-16.00(Sabtu-Minggu)\"},{\"name\":\"Museum Bank Mandiri\",\"lat\":-6.1381567,\"lng\":106.8108539,\"place_id\":\"ID00020\",\"address\":\"Jl. Lapangan Stasiun No. 1, Tamansari, Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6902000\",\"price\":\"Dewasa:Rp2.000, Anak-anak:Gratis\",\"hours\":\"09.00-16.00(Selasa-Minggu), Senin dan Hari Libur Nasional Tutup\"}]} ");
            JSONArray places = placesJSON.getJSONArray("places");
            for (int i = 0; i < places.length(); i++) {
                String name = ((JSONObject) places.get(i)).getString("name").toString();
                double lat = Double.parseDouble(((JSONObject) places.get(i)).getString("lat").toString());
                double lng = Double.parseDouble(((JSONObject) places.get(i)).getString("lng").toString());
                String place_id = ((JSONObject) places.get(i)).getString("place_id").toString();
                String address = ((JSONObject) places.get(i)).getString("address").toString();
                String phone = ((JSONObject) places.get(i)).getString("phone").toString();
                String price = ((JSONObject) places.get(i)).getString("price").toString();
                String hours = ((JSONObject) places.get(i)).getString("hours").toString();

                landmarks.add(new Places(lat, lng, name, place_id, address, phone, price, hours));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LandmarkAdapter adapter = new LandmarkAdapter(this, R.layout.list_item_landmark, landmarks);

        landmarkListView.setAdapter(adapter);

        landmarkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Places place = landmarks.get(position);
                Intent intent = new Intent(LandmarkActivity.this, LandmarkMapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", place.getName());
                bundle.putString("address", place.getAddress());
                bundle.putString("lat", Double.toString(place.getLat()));
                bundle.putString("lng", Double.toString(place.getLng()));
                bundle.putString("phone", place.getPhone_number());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
