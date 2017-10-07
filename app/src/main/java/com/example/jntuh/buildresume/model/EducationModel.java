package com.example.jntuh.buildresume.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmModule;

/**
 * Created by JNTUH on 10-09-2017.
 */
public class EducationModel extends RealmObject {

    private String Qualification,Institute,BoardUniversity,GradingType,Percentage,GraduationType,PassingYear;
    public EducationModel(){
    }
    public EducationModel(String qualification, String institute, String boardUniversity, String gradingType, String percentage, String graduationType, String passingYear) {
        Qualification = qualification;
        Institute = institute;
        BoardUniversity = boardUniversity;
        GradingType = gradingType;
        Percentage = percentage;
        GraduationType = graduationType;
        PassingYear = passingYear;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getInstitute() {
        return Institute;
    }

    public void setInstitute(String institute) {
        Institute = institute;
    }

    public String getBoardUniversity() {
        return BoardUniversity;
    }

    public void setBoardUniversity(String boardUniversity) {
        BoardUniversity = boardUniversity;
    }

    public String getGradingType() {
        return GradingType;
    }

    public void setGradingType(String gradingType) {
        GradingType = gradingType;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getGraduationType() {
        return GraduationType;
    }

    public void setGraduationType(String graduationType) {
        GraduationType = graduationType;
    }

    public String getPassingYear() {
        return PassingYear;
    }

    public void setPassingYear(String passingYear) {
        PassingYear = passingYear;
    }

}
