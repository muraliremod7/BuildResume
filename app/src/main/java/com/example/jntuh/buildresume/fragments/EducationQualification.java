package com.example.jntuh.buildresume.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.adapter.EducationListview;
import com.example.jntuh.buildresume.model.EducationModel;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class EducationQualification extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    RadioButton radioButton1,radioButton2;
    public static ArrayList<EducationModel> models;
    public ListView listView;
    public static EducationListview edulistview;
    public String itemId;
    private Realm realm;
    private static EducationQualification instance;
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
        models = new ArrayList<EducationModel>();
        edulistview = new EducationListview(getActivity(),models);
        itemId = ScrollableTabsActivity.itemid;
        if(itemId==null){

        }else{

            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            models = new ArrayList<>(saveDataModels.getEducationModels());
            edulistview = new EducationListview(getActivity(), models);
            listView.setAdapter(edulistview);
            edulistview.notifyDataSetInvalidated();
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
                            String percentageType = radioButton1.getText().toString();
                            String graduationType = radioButton2.getText().toString();
                            String qualification = qualificatioN.getEditText().getText().toString();
                            String institutE = institute.getEditText().getText().toString();
                            String borunI = boruni.getEditText().getText().toString();
                            String percga = percentagecgpa.getEditText().getText().toString();
                            String payear = passingyear.getEditText().getText().toString();
                            if(percentageType==null||percentageType==""||graduationType==null||graduationType==""||qualification==null||qualification==""||institutE==""||institutE==null||borunI==null||borunI==""||percga==null||percga==""||payear==null||payear==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
                                saveDetails(qualification,institutE,borunI,percga,payear,percentageType,graduationType);
                                alertDialog.dismiss();
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
        models.add(educationModel);
        edulistview = new EducationListview(getActivity(), models);
        listView.setAdapter(edulistview);
        edulistview.notifyDataSetInvalidated();

    }
 }
