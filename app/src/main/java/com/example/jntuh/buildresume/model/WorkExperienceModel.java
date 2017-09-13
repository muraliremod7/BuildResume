package com.example.jntuh.buildresume.model;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class WorkExperienceModel{

    public WorkExperienceModel(String jobrole, String jobdescription, String companyname, String fromwork, String towork) {
        this.jobrole = jobrole;
        this.jobdescription = jobdescription;
        this.companyname = companyname;
        this.fromwork = fromwork;
        this.towork = towork;
    }

    public String getJobrole() {
        return jobrole;
    }

    public void setJobrole(String jobrole) {
        this.jobrole = jobrole;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getFromwork() {
        return fromwork;
    }

    public void setFromwork(String fromwork) {
        this.fromwork = fromwork;
    }

    public String getTowork() {
        return towork;
    }

    public void setTowork(String towork) {
        this.towork = towork;
    }

    String jobrole,jobdescription,companyname,fromwork,towork;
    public WorkExperienceModel(){
    }

}
