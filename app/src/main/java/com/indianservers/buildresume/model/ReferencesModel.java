package com.indianservers.buildresume.model;

import io.realm.RealmObject;

/**
 * Created by JNTUH on 10-09-2017.
 */
public class ReferencesModel extends RealmObject {

    public ReferencesModel(String refname, String refdes, String reforganization, String refemail, String refmobilenum) {
        this.refname = refname;
        this.refdes = refdes;
        this.reforganization = reforganization;
        this.refemail = refemail;
        this.refmobilenum = refmobilenum;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getRefdes() {
        return refdes;
    }

    public void setRefdes(String refdes) {
        this.refdes = refdes;
    }

    public String getReforganization() {
        return reforganization;
    }

    public void setReforganization(String reforganization) {
        this.reforganization = reforganization;
    }

    public String getRefemail() {
        return refemail;
    }

    public void setRefemail(String refemail) {
        this.refemail = refemail;
    }

    public String getRefmobilenum() {
        return refmobilenum;
    }

    public void setRefmobilenum(String refmobilenum) {
        this.refmobilenum = refmobilenum;
    }

    private String refname,refdes,reforganization,refemail,refmobilenum;
    public ReferencesModel(){
    }


}
