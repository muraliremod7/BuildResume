package com.indianservers.buildresume.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    public RealmModelAdapter(OrderedRealmCollection<T> realmResults) {

        super( realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}