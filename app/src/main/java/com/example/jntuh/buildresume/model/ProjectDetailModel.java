package com.example.jntuh.buildresume.model;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class ProjectDetailModel{

    public ProjectDetailModel(String projecttitle, String projectdesc, String yourrole, String duration, String teammem) {
        this.projecttitle = projecttitle;
        this.projectdesc = projectdesc;
        this.yourrole = yourrole;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTeammem() {
        return teammem;
    }

    public void setTeammem(String teammem) {
        this.teammem = teammem;
    }

    public String projecttitle,projectdesc,yourrole,duration,teammem;
    public ProjectDetailModel(){
    }


}
