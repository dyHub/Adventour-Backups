package com.cs110.lit.adventour;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs110.lit.adventour.model.Checkpoint;

import java.util.ArrayList;


/**
 * Created by achen on 5/19/16.
 */
public class CheckpointListAdapter extends RecyclerView.Adapter<CheckpointListAdapter.ViewHolder> {

    private final Activity context;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public View mView;
        public int mCheckpointID;
        public final ImageView mImageView;
        public String title;
        public String summary;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.tour_metadata_checkpoint_image);
            nameTextView = (TextView) itemView.findViewById(R.id.tour_metadata_checkpoint_title);

        }
    }

    // Store a member variable for the contacts
    private ArrayList<Checkpoint> mCheckpoints;

    // Pass in the contact array into the constructor
    public CheckpointListAdapter(Activity context, ArrayList<Checkpoint> checkpoints) {
        mCheckpoints = checkpoints;
        this.context = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public CheckpointListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.card_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final CheckpointListAdapter.ViewHolder vHolder, int position) {
        // Get the data model based on position
        Checkpoint checkpoint = mCheckpoints.get(position);

        // Set item views based on the data model
        TextView textView = vHolder.nameTextView;
        textView.setText(checkpoint.getTitle());

        String photo = checkpoint.getPhoto();
        if (photo == null || photo.compareTo("http://placehold.it/250x250") == 0) {
            photo = ("https://maps.googleapis.com/maps/api/streetview?size=600x500&location=" +
                    Double.toString(checkpoint.getLatitude()) +"," + Double.toString(checkpoint.getLongitude()) +
                    "&heading=15&pitch=10&key=" + context.getString(R.string.google_street_view));
        }
        System.out.println(photo);
        Glide.with(vHolder.mImageView.getContext()).load(photo).centerCrop().into(vHolder.mImageView);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mCheckpoints.size();
    }
}
