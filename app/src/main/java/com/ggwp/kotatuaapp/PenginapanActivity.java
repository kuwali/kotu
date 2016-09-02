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
public class PenginapanActivity extends AppCompatActivity {

    List<Places> hotels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penginapan);

        ListView hotelListView = (ListView) findViewById(R.id.penginapan_list_view);

        try {
            JSONObject placesJSON = new JSONObject(" {\"places\" :[{\"name\":\"Hotel ibis Jakarta Mangga Dua\",\"lat\":-6.1381736,\"lng\":106.8203376,\"place_id\":\"ID00001\",\"address\":\"Jalan Pangeran Jayakarta No. 73, Mangga Dua Selatan, Mangga Dua Sel., Jakarta Pusat, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6250101\",\"price\":\"Rp457.300\"},{\"name\":\"Best Western Mangga Dua Hotel & Residence\",\"lat\":-6.1398837,\"lng\":106.8250013,\"place_id\":\"ID00002\",\"address\":\"Jl. Mangga Dua Abdad No.111, RT.3/RW.7, Mangga Dua Sel., Sawah Besar, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6122999\",\"price\":\"Rp639.800\"},{\"name\":\"Le Grandeur Mangga Dua\",\"lat\":-6.137135,\"lng\":106.825362,\"place_id\":\"ID00003\",\"address\":\"Jl. Mangga Dua Raya, Mangga Dua Selatan, Sawah Besar, Mangga Dua Sel., Jakarta Pusat, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6128811\",\"price\":\"Rp835.000\"},{\"name\":\"Novotel Jakarta Mangga Dua Square\",\"lat\":-6.1382322,\"lng\":106.8316681,\"place_id\":\"ID00004\",\"address\":\"Jl. Gunung Sahari Raya No. 1, Mangga Dua Square, Ancol, Jakarta Utara, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 62312800\",\"price\":\"Rp844.580\"},{\"name\":\"Sparks Hotel\",\"lat\":-6.1497605,\"lng\":106.8219835,\"place_id\":\"ID00005\",\"address\":\"Jl. Mangga Besar Raya No.42, RT.2/RW.2, Taman Sari, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6252534\",\"price\":\"Rp496.000\"},{\"name\":\"Travellers Hotel Jakarta\",\"lat\":-6.1467403,\"lng\":106.831571,\"place_id\":\"ID00006\",\"address\":\"Jl. Pangeran Jayakarta No.70, Mangga Dua Sel., Sawah Besar, DKI Jakarta, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6013888\",\"price\":\"Rp510.400\"},{\"name\":\"d'primahotel Mangga Dua 2\",\"lat\":-6.138884,\"lng\":106.8189573,\"place_id\":\"ID00007\",\"address\":\"Komp. Rukan Pangeran Jayakarta Center No. 73, RT.3/RW.6, Mangga Dua Selatan, Sawah Besar, Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6120159\",\"price\":\"Rp432.000\"},{\"name\":\"Zuri Express Hotel Jakarta - Mangga Dua\",\"lat\":-6.1391119,\"lng\":106.8227429,\"place_id\":\"ID00008\",\"address\":\"Jalan Mangga Dua Dalam No. 55-56, Mangga Dua Selatan, Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 22620008\",\"price\":\"Rp338.000\"},{\"name\":\"Mercure Jakarta Kota\",\"lat\":-6.1448043,\"lng\":106.8120066,\"place_id\":\"ID00009\",\"address\":\"Jl. Hayam Wuruk No. 123, Glodok, Mangga Besar, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6248680\",\"price\":\"Rp642.510\"},{\"name\":\"Swiss - Belhotel\",\"lat\":-6.1475153,\"lng\":106.8260155,\"place_id\":\"ID00010\",\"address\":\"Jl. Kartini Raya No. 57, Pasar Baru, Sawah Besar, Kartini, Jakarta Pusat, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6393888\",\"price\":\"Rp800.000\"},{\"name\":\"Triniti Hotel\",\"lat\":-6.1581838,\"lng\":106.8148561,\"place_id\":\"ID00011\",\"address\":\"Jl. Pembangunan III No.4, RT.14/RW.1, Petojo Utara, Gambir, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6331111\",\"price\":\"Rp299.000\"},{\"name\":\"Merlynn Park Hotel\",\"lat\":-6.1672369,\"lng\":106.818282,\"place_id\":\"ID00012\",\"address\":\"Jl. KH. Hasyim Azhari No. 29 - 31, Jakarta Pusat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 30026888\",\"price\":\"Rp854.100\"},{\"name\":\"Grand Paragon\",\"lat\":-6.1503007,\"lng\":106.8142914,\"place_id\":\"ID00013\",\"address\":\"Jl. Gajah Mada No.126, RT.13/RW.8, Keagungan, Tamansari, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 29073333\",\"price\":\"Rp645.000\"},{\"name\":\"Grand Asia Hotel\",\"lat\":-6.13755,\"lng\":106.7949225,\"place_id\":\"ID00014\",\"address\":\"Jalan Bandengan Selatan No.88, RT.1, Pejagalan, Penjaringan, Pejagalan, Jakarta Utara, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 66606969\",\"price\":\"Rp400.000\"},{\"name\":\"Sanno Hotel\",\"lat\":-6.1299445,\"lng\":106.8018547,\"place_id\":\"ID00015\",\"address\":\"Jl. Pluit Raya Selatan No. 2, Jl. Pluit Selatan Raya, Pluit, Penjaringan, Kota Jkt Utara, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6606060\",\"price\":\"Rp359.311\"}]} ");
            JSONArray places = placesJSON.getJSONArray("places");
            for (int i = 0; i < places.length(); i++) {
                String name = ((JSONObject) places.get(i)).getString("name").toString();
                double lat = Double.parseDouble(((JSONObject) places.get(i)).getString("lat").toString());
                double lng = Double.parseDouble(((JSONObject) places.get(i)).getString("lng").toString());
                String place_id = ((JSONObject) places.get(i)).getString("place_id").toString();
                String address = ((JSONObject) places.get(i)).getString("address").toString();
                String phone = ((JSONObject) places.get(i)).getString("phone").toString();
                String price = ((JSONObject) places.get(i)).getString("price").toString();

                hotels.add(new Places(name, lat, lng, place_id, address, phone, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HotelAdapter adapter = new HotelAdapter(this, R.layout.list_item_hotel, hotels);

        hotelListView.setAdapter(adapter);

        hotelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Places place = hotels.get(position);
                Intent intent = new Intent(PenginapanActivity.this, PenginapanMapsActivity.class);
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
