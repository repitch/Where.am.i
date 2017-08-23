package com.repitch.whereami;

import android.app.Application;

import com.repitch.whereami.db.helper.DbHelper;

/**
 * Created by repitch on 23.08.17.
 */
public class App extends Application {

    private static App appInstance;

    public static App getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        DbHelper.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DbHelper.releaseHelper();
    }
}
