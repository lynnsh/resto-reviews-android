package com.radiantridge.restoradiantridge.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Review;

/**
 * Created by Panda on 07/12/2016.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    public ReviewAdapter(Context context, Review[] reviews)
    {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Review review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_review, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.item_review_title);
        TextView tvRating = (TextView) convertView.findViewById(R.id.item_review_rating);
        TextView tvContent = (TextView) convertView.findViewById(R.id.item_review_content);

        // Populate the data into the template view using the data object
        tvTitle.setText(review.getTitle());
        tvRating.setText(review.getRating() + "");
        tvContent.setText(review.getContent());

        // Return the completed view to render on screen
        return convertView;
    }
}
