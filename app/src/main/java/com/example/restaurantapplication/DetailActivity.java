package com.example.restaurantapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        ImageView imageView = findViewById(R.id.restaurant_detail_image);
        TextView nameTextView = findViewById(R.id.restaurant_detail_name);
        TextView descriptionTextView = findViewById(R.id.restaurant_detail_description);
        Button locationButton = findViewById(R.id.restaurant_detail_location_button);
        Button phoneButton = findViewById(R.id.restaurant_detail_phone_button);

        nameTextView.setText(restaurant.getName());

        descriptionTextView.setText(restaurant.getDescription());


        Picasso.get().load(restaurant.getImageUrl()).into(imageView);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open location in maps
                String locationUrl = restaurant.getLocation();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationUrl));
                startActivity(intent);
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dial phone number
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurant.getPhone()));
                startActivity(phoneIntent);
            }
        });
    }
}
