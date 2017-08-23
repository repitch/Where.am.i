package com.repitch.whereami.db.entity;

import android.location.Location;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by repitch on 23.08.17.
 */
@DatabaseTable
public class LocationTrack implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private DateTime time;

    @DatabaseField
    private double longitude;

    @DatabaseField
    private double latitude;

    public LocationTrack(){}

    public LocationTrack(DateTime time, Location location) {
        this.time = time;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public int getId() {
        return id;
    }

    public DateTime getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

}
