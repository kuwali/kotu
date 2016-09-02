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
import com.ggwp.kotatuaapp.model.RestaurantAdapter;
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
public class RestaurantActivity extends AppCompatActivity {

    List<Places> restaurants = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        ListView restaurantListView = (ListView) findViewById(R.id.restaurant_list_view);

        try {
            JSONObject placesJSON = new JSONObject(" {\"places\":[{\"name\":\"Cafe Batavia\",\"lat\":-6.1344541,\"lng\":106.8126772,\"place_id\":\"ID00021\",\"address\":\"Taman Fatahillah, Jl. Pintu Besar Utara No. 14, Kota, Jakarta\",\"phone\":\"(021) 6915531\",\"price\":\"Rp300.000\",\"hours\":\"08.00-00.00(Senin-Kamis,Minggu),08.00-01.00\",\"cuisines\":\"Indonesia,Chinese,Dimsum\"},{\"name\":\"Historia Food & Bar\",\"lat\":-6.1357772,\"lng\":106.8128948,\"place_id\":\"ID00022\",\"address\":\"Taman Fatahillah, Jl. Pintu Besar Utara No 11, Kota, Jakarta\",\"phone\":\"(021) 6904188\",\"price\":\"Rp250.000\",\"hours\":\"10.00-22.00(Senin-Jumat),08.00-22.00(Sabtu-Minggu)\",\"cuisines\":\"Cafe\"},{\"name\":\"Kopi Es Tak Kie\",\"lat\":-6.1411799,\"lng\":106.8137921,\"place_id\":\"ID00023\",\"address\":\"Jl. Pintu Besar Selatan 3 No. 4-6, Kota, Jakarta\",\"phone\":\"(021) 6928296\",\"price\":\"Rp80.000\",\"hours\":\"06.30-02.00\",\"cuisines\":\"Chinese, Kopi dan Teh\"},{\"name\":\"Kedai Seni Djakarte\",\"lat\":-6.1356227,\"lng\":106.8128519,\"place_id\":\"ID00024\",\"address\":\"Taman Fatahillah, Jl. Pintu Besar Utara No. 17, Kota, Jakarta\",\"phone\":\"0818 08374431\",\"price\":\"Rp80.000\",\"hours\":\"09:00-21:00(Senin-Jumat,Minggu),09:00-22:00(Sabtu)\",\"cuisines\":\"Cafe\"},{\"name\":\"Bangi Kopi Kota Tua\",\"lat\":-6.135356,\"lng\":106.6727253,\"place_id\":\"ID00025\",\"address\":\"Jalan Kali Besar Timur No. 21A, Tamansari, Pinangsia, Jakarta Barat, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta\",\"phone\":\"(021) 6903442\",\"price\":\"Rp200.000\",\"hours\":\"08.00-00.00\",\"cuisines\":\"Peranakan, Indonesia, Kopi dan Tea\"},{\"name\":\"Bakmi Orpa\",\"lat\":-6.1360278,\"lng\":106.8057937,\"place_id\":\"ID00026\",\"address\":\"Jl. Malaka 2 No. 25, Kota, Jakarta\",\"phone\":\"(021) 6912450\",\"price\":\"Rp80.000\",\"hours\":\"10:00-20:00\",\"cuisines\":\"Bakmi\"},{\"name\":\"Pinang Sari\",\"lat\":-6.1399927,\"lng\":106.8169131,\"place_id\":\"ID00027\",\"address\":\"Jl. Pinangsia Timur No. 1, Kota, Jakarta\",\"phone\":\"(021) 6251025\",\"price\":\"Rp50.000\",\"hours\":\"06.00-16.30\",\"cuisines\":\"Chinese\"},{\"name\":\"Starcbucks Coffee\",\"lat\":-6.1389753,\"lng\":106.8076117,\"place_id\":\"ID00028\",\"address\":\"Stasiun Kota, Jl. Stasiun Kota, Kota, Jakarta\",\"phone\":\"\",\"price\":\"Rp120.000\",\"hours\":\"10:00-22:00\",\"cuisines\":\"Cafe, Kopi dan Teh\"},{\"name\":\"Keukenhof Bistro\",\"lat\":-6.1333294,\"lng\":106.8131591,\"place_id\":\"ID00029\",\"address\":\"Jl. Kunir No. 5, Kota, Jakarta\",\"phone\":\"(021) 6908856\",\"price\":\"Rp150.000\",\"hours\":\"10:00-22:00\",\"cuisines\":\"Indonesia, Dimsum, Chinese\"},{\"name\":\"Santung Kuo Tieh & Sui Kiaw 68 \",\"lat\":-6.1404324,\"lng\":106.8116569,\"place_id\":\"ID00030\",\"address\":\"Jl. Pancoran Raya, Kota, Jakarta\",\"phone\":\"(021) 6924716\",\"price\":\"Rp80.000\",\"hours\":\"09:30 - 21:30 \",\"cuisines\":\"Chinese\"},{\"name\":\"Batavia Market\",\"lat\":-6.13413,\"lng\":106.81345,\"place_id\":\"ID00031\",\"address\":\"Gedung Kantor Pos, Lantai 2, Jl. Taman Fatahillah No. 3\",\"phone\":\"(021) 26073990\",\"price\":\"Rp200.000\",\"hours\":\"10:00-22:00(Senin-Kamis,Minggu),10.00-00.00(Jumat-Sabtu)\",\"cuisines\":\"Indonesia\"},{\"name\":\"J.CO Donuts & Coffee\",\"lat\":-6.1389753,\"lng\":106.8076117,\"place_id\":\"ID00032\",\"address\":\"Stasiun Kota, Jl. Stasiun Kota, Kota, Jakarta\",\"phone\":\"(021) 7996060\",\"price\":\"Rp100.000\",\"hours\":\"10:00 - 22:00\",\"cuisines\":\"Cafe,Bakery,Desserts, Kopi dan Teh\"},{\"name\":\"Cahaya Mentari\",\"lat\":-6.1318136,\"lng\":106.809479,\"place_id\":\"ID00033\",\"address\":\"Jl. Roa Malaka Utara No. 49, Kota, Jakarta\",\"phone\":\"(021) 6922339\",\"price\":\"Rp200.000\",\"hours\":\" 11.00–15.00,17.30–22.00\",\"cuisines\":\"Chinese\"},{\"name\":\"Kantin Nikmat\",\"lat\":-6.143195,\"lng\":106.81428,\"place_id\":\"ID00034\",\"address\":\"Jl. Pasar Glodok Selatan No. 13, Kota, Jakarta\",\"phone\":\"(021) 6344387\",\"price\":\"Rp70.000\",\"hours\":\" 10.00-22.00\",\"cuisines\":\"Bakmi,Chinese,Indonesia\"},{\"name\":\"A&W\",\"lat\":-6.1389753,\"lng\":106.8076117,\"place_id\":\"ID00035\",\"address\":\"Stasiun Kota, Jl. Stasiun Kota, Kota, Jakarta\",\"phone\":\"(021) 6907245\",\"price\":\"Rp120.000\",\"hours\":\"07.00-20.00\",\"cuisines\":\"Fast Food, Amerika\"},{\"name\":\"KFC\",\"lat\":-6.1389753,\"lng\":106.8076117,\"place_id\":\"ID00036\",\"address\":\"Stasiun Kota, Jl. Stasiun Kota, Kota, Jakarta\",\"phone\":\"(021) 14022\",\"price\":\"Rp100.000\",\"hours\":\"10.00-22.00\",\"cuisines\":\"Fast Food, Amerika\"},{\"name\":\"Grand Hwa Yen\",\"lat\":-6.133244,\"lng\":106.8097516,\"place_id\":\"ID00037\",\"address\":\"Jl. Roa Malaka Utara No. 30, Kota, Jakarta\",\"phone\":\"(021) 6912755\",\"price\":\"Rp260.000\",\"hours\":\"11:00-15:00, 17:00-22:00\",\"cuisines\":\"Seafood, Chinese\"},{\"name\":\"CFC\",\"lat\":-6.1389753,\"lng\":106.8076117,\"place_id\":\"ID00038\",\"address\":\"Stasiun Kota, Jl. Stasiun Kota, Kota, Jakarta\",\"phone\":\"\",\"price\":\"Rp100.000\",\"hours\":\"07.00-20.00\",\"cuisines\":\"Fast Food, Amerika, Indonesia\"},{\"name\":\"Sedap Wangi\",\"lat\":-6.1394902,\"lng\":106.8168293,\"place_id\":\"ID00039\",\"address\":\"Jl. Pinangsia Timur No. 27, Kota, Jakarta\",\"phone\":\"(021) 6499469\",\"price\":\"Rp50.000\",\"hours\":\"10.00-21.00\",\"cuisines\":\"Chinese\"},{\"name\":\"Bakmi Ahiung khas Jambi\",\"lat\":-6.1396367,\"lng\":106.8168993,\"place_id\":\"ID00040\",\"address\":\"Jl. Pinangsia Timur No. 15, Kota, Jakarta\",\"phone\":\"0818 962881\",\"price\":\"Rp80.000\",\"hours\":\"06.00-13.00(Selasa-Minggu),Senin Tutup\",\"cuisines\":\"Sumatera, Bakmi\"}]} ");
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
                String cuisines = ((JSONObject) places.get(i)).getString("cuisines").toString();

                restaurants.add(new Places(lat, lng, name, place_id, address, phone, price, hours, cuisines));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RestaurantAdapter adapter = new RestaurantAdapter(this, R.layout.list_item_restaurant, restaurants);

        restaurantListView.setAdapter(adapter);

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Places place = restaurants.get(position);
                Intent intent = new Intent(RestaurantActivity.this, RestaurantMapsActivity.class);
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
