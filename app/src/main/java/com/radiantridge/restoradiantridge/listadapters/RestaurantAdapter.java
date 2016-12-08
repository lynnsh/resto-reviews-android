package com.radiantridge.restoradiantridge.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Restaurant;

/**
 * Custom adapter for the Restaurant list fragment.  Displays only the name
 * of the restaurant.
 *
 * @author Erika Bourque
 * @version 02/12/2016
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    public RestaurantAdapter(Context context, Restaurant[] restos)
    {
        super(context, 0, restos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Restaurant resto = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_restaurant, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.item_resto_name);
        // Populate the data into the template view using the data object
        tvName.setText(resto.getName());
        // Return the completed view to render on screen
        return convertView;

    }
}
