package com.indianservers.buildresume.model;

import io.realm.RealmObject;

/**
 * Created by JNTUH on 13-09-2017.
 */
public class OthersModel extends RealmObject {
    public OthersModel(){
    }

    public OthersModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
