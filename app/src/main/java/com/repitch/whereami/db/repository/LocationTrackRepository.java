package com.repitch.whereami.db.repository;

import android.support.annotation.Nullable;

import com.repitch.whereami.db.dao.SingleDaoService;
import com.repitch.whereami.db.entity.LocationTrack;

import java.util.List;

/**
 * Created by repitch on 23.08.17.
 */
public class LocationTrackRepository extends SingleDaoService<LocationTrack, Integer> {
    public LocationTrackRepository() {
        super(LocationTrack.class);
    }

    public void addLocationTrack(LocationTrack locationTrack) {
        getDao().create(locationTrack);
    }

    @Nullable
    public LocationTrack getById(int id){
        return getDao().queryForId(id);
    }

    public List<LocationTrack> getAll() {
        return getDao().queryForAll();
    }
}
