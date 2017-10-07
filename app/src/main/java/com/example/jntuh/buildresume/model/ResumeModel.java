package com.example.jntuh.buildresume.model;

/**
 * Created by JNTUH on 03-10-2017.
 */

public class ResumeModel {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResumename() {
        return resumename;
    }

    public void setResumename(String resumename) {
        this.resumename = resumename;
    }

    private String resumename;
    public ResumeModel(){

    }
    public ResumeModel(String id, String resumename) {
        this.id = id;
        this.resumename = resumename;
    }
}
