package com.example.jntuh.buildresume.model;

import io.realm.RealmObject;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class ProjectDetailModel extends RealmObject {

    public ProjectDetailModel(String projecttitle, String projectdesc, String yourrole, String durationfrom,String durationto, String teammem) {
        this.projecttitle = projecttitle;
        this.projectdesc = projectdesc;
        this.yourrole = yourrole;
        this.durationfrom = durationfrom;
        this.durationto = durationto;
        this.teammem = teammem;
    }

    public String getProjecttitle() {
        return projecttitle;
    }

    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    public String getProjectdesc() {
        return projectdesc;
    }

    public void setProjectdesc(String projectdesc) {
        this.projectdesc = projectdesc;
    }

    public String getYourrole() {
        return yourrole;
    }

    public void setYourrole(String yourrole) {
        this.yourrole = yourrole;
    }

    public String getDurationfrom() {
        return durationfrom;
    }

    public void setDurationfrom(String durationfrom) {
        this.durationfrom = durationfrom;
    }

    public String getTeammem() {
        return teammem;
    }

    public void setTeammem(String teammem) {
        this.teammem = teammem;
    }

    private String projecttitle;
    private String projectdesc;
    private String yourrole;
    private String durationfrom;

    public String getDurationto() {
        return durationto;
    }

    public void setDurationto(String durationto) {
        this.durationto = durationto;
    }

    private String durationto;
    private String teammem;
    public ProjectDetailModel(){
    }


}
