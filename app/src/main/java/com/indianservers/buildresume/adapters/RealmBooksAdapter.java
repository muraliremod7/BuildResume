package com.indianservers.buildresume.adapters;

import android.content.Context;

import com.indianservers.buildresume.model.SaveDataModel;

import io.realm.RealmResults;


public class RealmBooksAdapter extends RealmModelAdapter<SaveDataModel> {

    public RealmBooksAdapter(RealmResults<SaveDataModel> realmResults) {

        super(realmResults);
    }
}