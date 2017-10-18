package com.example.jntuh.buildresume.adapters;

import android.content.Context;

import com.example.jntuh.buildresume.model.SaveDataModel;

import io.realm.RealmResults;



public class RealmBooksAdapter extends RealmModelAdapter<SaveDataModel> {

    public RealmBooksAdapter(Context context, RealmResults<SaveDataModel> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}