package com.example.jntuh.buildresume.app;

import android.app.Application;

import com.example.jntuh.buildresume.fragments.ContactInformation;
import com.example.jntuh.buildresume.model.EducationModel;
import com.example.jntuh.buildresume.model.OthersModel;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.example.jntuh.buildresume.model.ReferencesModel;
import com.example.jntuh.buildresume.model.WorkExperienceModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
