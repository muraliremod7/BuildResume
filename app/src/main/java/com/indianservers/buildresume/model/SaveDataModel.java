package com.indianservers.buildresume.model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SaveDataModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String profilename;
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String dateofbirth;
    private String marriagestatus;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String gender;
    private String careerobjective;
    private String declaration;
    private RealmList<WorkExperienceModel> experienceModels;
    private RealmList<ProjectDetailModel> projectDetailModels;
    private RealmList<ReferencesModel> referencesModels;
    private RealmList<OthersModel> othersModelsskills;

    private RealmList<OthersModel> othersModelsache;
    private RealmList<OthersModel> othersModelshobbys;
    private RealmList<OthersModel> othersModelslan;
    public SaveDataModel() {
    }
    public SaveDataModel(int id, String profilename, String name, String email, String mobile, String address, String dateofbirth, String marriagestatus, String city, String state, String country, String pincode, String gender, String careerobjective, String declaration, RealmList<EducationModel> educationModels, RealmList<WorkExperienceModel> experienceModels, RealmList<ProjectDetailModel> projectDetailModels, RealmList<ReferencesModel> referencesModels, RealmList<OthersModel> othersModelsskills, RealmList<OthersModel> othersModelsache, RealmList<OthersModel> othersModelshobbys, RealmList<OthersModel> othersModelslan, byte[] personpic, byte[] signaturepic) {
        this.id = id;
        this.profilename = profilename;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.dateofbirth = dateofbirth;
        this.marriagestatus = marriagestatus;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.gender = gender;
        this.careerobjective = careerobjective;
        this.declaration = declaration;
        this.educationModels = educationModels;
        this.experienceModels = experienceModels;
        this.projectDetailModels = projectDetailModels;
        this.referencesModels = referencesModels;
        this.othersModelsskills = othersModelsskills;
        this.othersModelsache = othersModelsache;
        this.othersModelshobbys = othersModelshobbys;
        this.othersModelslan = othersModelslan;
        this.personpic = personpic;
        this.signaturepic = signaturepic;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getMarriagestatus() {
        return marriagestatus;
    }

    public void setMarriagestatus(String marriagestatus) {
        this.marriagestatus = marriagestatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCareerobjective() {
        return careerobjective;
    }

    public void setCareerobjective(String careerobjective) {
        this.careerobjective = careerobjective;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }




    public RealmList<EducationModel> getEducationModels() {
        return educationModels;
    }

    public void setEducationModels(RealmList<EducationModel> educationModels) {
        this.educationModels = educationModels;
    }

    private RealmList<EducationModel> educationModels;

    public RealmList<WorkExperienceModel> getExperienceModels() {
        return experienceModels;
    }

    public void setExperienceModels(RealmList<WorkExperienceModel> experienceModels) {
        this.experienceModels = experienceModels;
    }

    public RealmList<ProjectDetailModel> getProjectDetailModels() {
        return projectDetailModels;
    }

    public void setProjectDetailModels(RealmList<ProjectDetailModel> projectDetailModels) {
        this.projectDetailModels = projectDetailModels;
    }

    public RealmList<ReferencesModel> getReferencesModels() {
        return referencesModels;
    }

    public void setReferencesModels(RealmList<ReferencesModel> referencesModels) {
        this.referencesModels = referencesModels;
    }

    public RealmList<OthersModel> getOthersModelsskills() {
        return othersModelsskills;
    }

    public void setOthersModelsskills(RealmList<OthersModel> othersModelsskills) {
        this.othersModelsskills = othersModelsskills;
    }


    public RealmList<OthersModel> getOthersModelsache() {
        return othersModelsache;
    }

    public void setOthersModelsache(RealmList<OthersModel> othersModelsache) {
        this.othersModelsache = othersModelsache;
    }

    public RealmList<OthersModel> getOthersModelshobbys() {
        return othersModelshobbys;
    }

    public void setOthersModelshobbys(RealmList<OthersModel> othersModelshobbys) {
        this.othersModelshobbys = othersModelshobbys;
    }

    public RealmList<OthersModel> getOthersModelslan() {
        return othersModelslan;
    }

    public void setOthersModelslan(RealmList<OthersModel> othersModelslan) {
        this.othersModelslan = othersModelslan;
    }

    public byte[] getPersonpic() {
        return personpic;
    }

    public void setPersonpic(byte[] personpic) {
        this.personpic = personpic;
    }

    public byte[] getSignaturepic() {
        return signaturepic;
    }

    public void setSignaturepic(byte[] signaturepic) {
        this.signaturepic = signaturepic;
    }

    private byte[] personpic,signaturepic;

}

