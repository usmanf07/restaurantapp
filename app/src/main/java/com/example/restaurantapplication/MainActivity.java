package com.example.restaurantapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_RESTAURANT_REQUEST = 1;

    private RecyclerView restaurantRecyclerView;
    private EditText searchEditText;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //copyRawFileToInternalStorage(R.raw.restaurants, "restaurants.txt");

        restaurantRecyclerView = findViewById(R.id.restaurant_recycler_view);
        searchEditText = findViewById(R.id.search_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        restaurantRecyclerView.setLayoutManager(layoutManager);

        restaurantList = loadRestaurantData();

        restaurantAdapter = new RestaurantAdapter(this, restaurantList);
        restaurantRecyclerView.setAdapter(restaurantAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                if (restaurantAdapter != null && restaurantAdapter.getFilter() != null) {
                    restaurantAdapter.getFilter().filter(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        restaurantAdapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Open detail activity with corresponding restaurant
                Restaurant selectedRestaurant = restaurantAdapter.getFilteredRestaurantList().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("restaurant", selectedRestaurant);
                startActivity(intent);
            }
        });

        ImageButton addRestaurantButton = findViewById(R.id.add_restaurant_button);
        addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
                startActivityForResult(intent, ADD_RESTAURANT_REQUEST);
            }
        });
    }
//    private void copyRawFileToInternalStorage(int resourceId, String fileName) {
//        Resources resources = getResources();
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//
//        try {
//            inputStream = resources.openRawResource(resourceId);
//
//            File outFile = new File(getFilesDir(), fileName);
//            outputStream = new FileOutputStream(outFile);
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, length);
//            }
//
//            Log.d("MainActivity", "File copied successfully to internal storage");
//        } catch (Resources.NotFoundException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (inputStream != null) inputStream.close();
//                if (outputStream != null) outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RESTAURANT_REQUEST && resultCode == RESULT_OK) {

            Restaurant newRestaurant = (Restaurant) data.getSerializableExtra("newRestaurant");

            restaurantList.add(newRestaurant);

            saveRestaurantData(restaurantList);

            restaurantAdapter.notifyDataSetChanged();
        }
    }
    private List<Restaurant> loadRestaurantData() {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("restaurants.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String imageUrl = parts[1];
                String description = parts[2];
                String phone = parts[3];
                String rating = parts[4];
                String location = parts[5];
                restaurants.add(new Restaurant(name, imageUrl, description, phone, rating, location));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurants;
    }


    private void saveRestaurantData(List<Restaurant> restaurants) {
        File file = new File(getFilesDir(), "restaurants.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Restaurant restaurant : restaurants) {
                bufferedWriter.write(restaurant.getName() + "," +
                        restaurant.getImageUrl() + "," +
                        restaurant.getDescription() + "," +
                        restaurant.getPhone() + "," +
                        restaurant.getRating() + "," +
                        restaurant.getLocation());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
