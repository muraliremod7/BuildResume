package com.indianservers.buildresume.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
