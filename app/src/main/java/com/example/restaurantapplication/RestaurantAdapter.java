package com.example.restaurantapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Restaurant> originalRestaurantList;
    private List<Restaurant> filteredRestaurantList;
    private OnItemClickListener listener;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.originalRestaurantList = restaurantList;
        this.filteredRestaurantList = new ArrayList<>(restaurantList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = filteredRestaurantList.get(position);
        holder.bind(restaurant, position);
    }

    @Override
    public int getItemCount() {
        return filteredRestaurantList.size();
    }
    public List<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurantList;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString().toLowerCase().trim();
                List<Restaurant> filteredList = new ArrayList<>();

                if (searchString.isEmpty()) {
                    filteredList.addAll(originalRestaurantList);
                } else {
                    for (Restaurant restaurant : originalRestaurantList) {
                        if (restaurant.getName().toLowerCase().contains(searchString)) {
                            filteredList.add(restaurant);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredRestaurantList.clear();
                filteredRestaurantList.addAll((List<Restaurant>) results.values);
                notifyDataSetChanged();
            }
        };
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nameTextView;
        private RatingBar ratingTextView;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            ratingTextView = itemView.findViewById(R.id.restaurant_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Restaurant restaurant, int position) {
            this.position = position;
            Picasso.get().load(restaurant.getImageUrl()).into(imageView);
            nameTextView.setText(restaurant.getName());
            ratingTextView.setRating(Float.parseFloat(restaurant.getRating()));
        }
    }

}
