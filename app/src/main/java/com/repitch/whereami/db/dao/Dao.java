package com.repitch.whereami.db.dao;


import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.repitch.whereami.db.helper.DbHelper;

import java.sql.SQLException;

/**
 * Created by repitch on 23.07.17.
 */
public class Dao<T, ID> extends RuntimeExceptionDao<T, ID> {

    private final Class<T> entityClass;
    private final DbHelper dbHelper;

    public Dao(DbHelper dbHelper, Class<T> entityClass) throws SQLException {
        super(dbHelper.getDao(entityClass));
        this.entityClass = entityClass;
        this.dbHelper = dbHelper;
    }
}

