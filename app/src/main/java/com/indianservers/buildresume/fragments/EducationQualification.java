package com.indianservers.buildresume.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.indianservers.buildresume.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indianservers.buildresume.ScrollableTabsActivity;
import com.indianservers.buildresume.adapter.EducationListview;
import com.indianservers.buildresume.model.EducationModel;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.realm.RealmController;
import com.indianservers.buildresume.service.AlertDailogManager;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;


public class EducationQualification extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    RadioButton radioButton1,radioButton2;
    public static ArrayList<EducationModel> models = new ArrayList<>();
    public ListView listView;
    public EducationListview edulistview;
    public String itemId;
    private Realm realm;
    private static EducationQualification instance;
    public static ArrayList<EducationModel> models1;
    public EducationQualification() {
        // Required empty public constructor
    }
    public static EducationQualification newInstance(String text){
        EducationQualification qualification = new EducationQualification();
        Bundle b = new Bundle();
        b.putString("msg", text);

        qualification.setArguments(b);
        return qualification;
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_education_qualification, container, false);
        actionButton = (FloatingActionButton)itemView.findViewById(R.id.addeducation);
        actionButton.setOnClickListener(this);
        listView = (ListView)itemView.findViewById(R.id.edulistview);
        this.realm = RealmController.with(this).getRealm();
        itemId = ScrollableTabsActivity.itemid;

        if(itemId==null){
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("EduList", null);
                Type type = new TypeToken<ArrayList<EducationModel>>() {}.getType();
                models = gson.fromJson(json, type);

                if(models==null){
                    models = new ArrayList<>();
                }else{
                    edulistview = new EducationListview(getActivity(), models);
                    listView.setAdapter(edulistview);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else{
            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            models1 = new ArrayList<>(saveDataModels.getEducationModels());
            edulistview = new EducationListview(getActivity(), models1);
            listView.setAdapter(edulistview);
            edulistview.notifyDataSetInvalidated();
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("EduList", null);
                Type type = new TypeToken<ArrayList<EducationModel>>() {}.getType();
                models = gson.fromJson(json, type);
                if(models==null){
                    models = new ArrayList<>();
                    edulistview = new EducationListview(getActivity(), models1);
                    listView.setAdapter(edulistview);
                    edulistview.notifyDataSetChanged();
                }else{
                    models1.addAll(models);
                    edulistview = new EducationListview(getActivity(), models1);
                    listView.setAdapter(edulistview);
                    edulistview.notifyDataSetChanged();
                }

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return itemView;
    }
    public static EducationQualification getInstance() {
        return instance;
    }
    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.addeducation:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addeducation, null);
                builder.setTitle("Add Education Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                final RadioGroup dialogRadioGroup = (RadioGroup) dialogView.findViewById(R.id.percentype);
                final RadioGroup dialogRadioGroup1 = (RadioGroup) dialogView.findViewById(R.id.graduationtype);
                final TextInputLayout qualificatioN = (TextInputLayout)dialogView.findViewById(R.id.qualification);
                final TextInputLayout institute = (TextInputLayout)dialogView.findViewById(R.id.institute);
                final TextInputLayout boruni = (TextInputLayout)dialogView.findViewById(R.id.boarduniversity);
                final TextInputLayout percentagecgpa = (TextInputLayout)dialogView.findViewById(R.id.percentagecgpa);
                final TextInputLayout passingyear = (TextInputLayout)dialogView.findViewById(R.id.passingyear);
                passingyear.getEditText().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(MotionEvent.ACTION_UP == event.getAction()){
                            YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getContext(), new YearMonthPickerDialog.OnDateSetListener() {
                                @Override
                                public void onYearMonthSet(int year, int month) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.YEAR, year);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

                                    passingyear.getEditText().setText(dateFormat.format(calendar.getTime()));
                                }
                            });
                            yearMonthPickerDialog.show();
                        }
                        return false;
                    }
                });
                //2131624080
                //4086
                Button save = (Button)dialogView.findViewById(R.id.save_education);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_education);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedId = dialogRadioGroup .getCheckedRadioButtonId();
                        int selectedId1 = dialogRadioGroup1 .getCheckedRadioButtonId();
                        // find the radio button by returned itemid
                         radioButton1 = (RadioButton) dialogView.findViewById(selectedId);
                         radioButton2 = (RadioButton) dialogView.findViewById(selectedId1);
                        try{
                            String pv = "^\\d{1,9}(\\.\\d{1,2})$";
                            String percentageType = radioButton1.getText().toString();
                            String graduationType = radioButton2.getText().toString();

                            String qualification = qualificatioN.getEditText().getText().toString();
                            String institutE = institute.getEditText().getText().toString();
                            String borunI = boruni.getEditText().getText().toString();
                            String percga = percentagecgpa.getEditText().getText().toString();
                            String kept = null;
                            try{
                                kept = percga.substring( 0, percga.indexOf("."));
                            }catch (StringIndexOutOfBoundsException e){
                                e.printStackTrace();
                            }

                            String payear = passingyear.getEditText().getText().toString();
                            if(percentageType==null||percentageType==""||graduationType==null||graduationType==""||qualification==null||qualification==""||institutE==""||institutE==null||borunI==null||borunI==""||percga==null||percga==""||payear==null||payear==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else if(percentageType.equals("Percentage")){
                                if(!percga.matches(pv)){
                                    AlertDailogManager dailogManager = new AlertDailogManager();
                                    dailogManager.showAlertDialog(getContext(),"Enter Accurate Percentage",false);
                                }else if(kept.length()>2){
                                    AlertDailogManager dailogManager = new AlertDailogManager();
                                    dailogManager.showAlertDialog(getContext(),"Enter Accurate Percentage",false);
                                }else{
                                    saveDetails(qualification,institutE,borunI,percga,payear,percentageType,graduationType);
                                    alertDialog.dismiss();
                                }

                            }else if(percentageType.equals("CGPA")){
                                if(Float.parseFloat(percga)>10){
                                    AlertDailogManager dailogManager = new AlertDailogManager();
                                    dailogManager.showAlertDialog(getContext(),"Enter Accurate Percentage",false);
                                }else if(!percga.matches(pv)){
                                    AlertDailogManager dailogManager = new AlertDailogManager();
                                    dailogManager.showAlertDialog(getContext(),"Enter Accurate Percentage",false);
                                }else{
                                    saveDetails(qualification,institutE,borunI,percga,payear,percentageType,graduationType);
                                    alertDialog.dismiss();
                                }

                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    public void saveDetails(String qualification, String institutE, String borunI, String percga, String payear, String percentageType, String graduationType) {
        EducationModel educationModel = new EducationModel();
        educationModel.setQualification(qualification);
        educationModel.setInstitute(institutE);
        educationModel.setBoardUniversity(borunI);
        educationModel.setPercentage(percga);
        educationModel.setPassingYear(payear);
        educationModel.setGradingType(percentageType);
        educationModel.setGraduationType(graduationType);
        if(itemId==null){
            models.add(educationModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(models);
            prefsEditor.putString("EduList", json);
            prefsEditor.commit();
            edulistview = new EducationListview(getActivity(), models);
            listView.setAdapter(edulistview);
            edulistview.notifyDataSetInvalidated();
        }else{
            models.removeAll(models1);
            models.add(educationModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(models);
            prefsEditor.putString("EduList", json);
            prefsEditor.commit();
            models.addAll(models1);
            edulistview = new EducationListview(getActivity(), models);
            models1.add(educationModel);
            listView.setAdapter(edulistview);
            edulistview.notifyDataSetInvalidated();
        }

    }
 }
