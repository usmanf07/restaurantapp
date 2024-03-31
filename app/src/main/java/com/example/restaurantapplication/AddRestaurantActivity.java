package com.example.restaurantapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText nameEditText, imageUrlEditText, descriptionEditText, phoneEditText, ratingEditText, locationEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        nameEditText = findViewById(R.id.edit_text_name);
        imageUrlEditText = findViewById(R.id.edit_text_image_url);
        descriptionEditText = findViewById(R.id.edit_text_description);
        phoneEditText = findViewById(R.id.edit_text_phone);
        ratingEditText = findViewById(R.id.edit_text_rating);
        locationEditText = findViewById(R.id.edit_text_location);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestaurant();
            }
        });
    }

    private void saveRestaurant() {
        String name = nameEditText.getText().toString().trim();
        String imageUrl = imageUrlEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String rating = ratingEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        Restaurant restaurant = new Restaurant(name, imageUrl, description, phone, rating, location);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newRestaurant", restaurant);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
