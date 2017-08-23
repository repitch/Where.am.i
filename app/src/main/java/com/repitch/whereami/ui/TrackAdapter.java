package com.repitch.whereami.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repitch.whereami.R;
import com.repitch.whereami.db.entity.LocationTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by repitch on 23.08.17.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<LocationTrack> tracks = new ArrayList<>();
    private ItemClickListener listener;

    public interface ItemClickListener {
        void onItemClicked(LocationTrack locationTrack);
    }

    public TrackAdapter(List<LocationTrack> tracks, ItemClickListener listener) {
        this.tracks.addAll(tracks);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location_track, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationTrack locationTrack = tracks.get(position);
        holder.txtLocation.setText(String.format("%f %f", locationTrack.getLatitude(), locationTrack.getLongitude()));
        holder.txtTime.setText(locationTrack.getTime().toString("dd MMM, HH:mm:ss"));
        holder.itemView.setOnClickListener(v -> listener.onItemClicked(locationTrack));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public void updateData(List<LocationTrack> tracks) {
        this.tracks.clear();
        this.tracks.addAll(tracks);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocation;
        TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.txt_location);
            txtTime = itemView.findViewById(R.id.txt_time);
        }
    }

}
