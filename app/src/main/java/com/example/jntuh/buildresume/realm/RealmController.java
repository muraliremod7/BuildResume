package com.example.jntuh.buildresume.realm;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.jntuh.buildresume.model.SaveDataModel;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Doctor.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(SaveDataModel.class);
        realm.commitTransaction();
    }

    //find all objects in the Doctor.class
    public RealmResults<SaveDataModel> getBooks() {

        return realm.where(SaveDataModel.class).findAll();
    }

    //query a single item with the given id
    public SaveDataModel getBook(String id) {

        return realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
    }

    //check if Doctor.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(SaveDataModel.class).isEmpty();
    }

    //query example
    public RealmResults<SaveDataModel> queryedBooks() {

        return realm.where(SaveDataModel.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
