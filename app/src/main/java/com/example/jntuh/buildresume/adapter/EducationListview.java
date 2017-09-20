package com.example.jntuh.buildresume.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.model.EducationModel;
import com.example.jntuh.buildresume.service.AlertDailogManager;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class EducationListview extends ArrayAdapter<EducationModel>{
    TextView qualification, institute, board, percentagetype,percentage,graduationtype, yearofpassing;
    public final Activity activity;
    public int currentposition;
    public ArrayList<EducationModel> educationmodel;
    RadioButton radioButton1,radioButton2;
    EducationModel educationModel;
    EducationQualification educationQualification;
    AlertDailogManager dailogManager = new AlertDailogManager();

    public EducationListview(Activity activity, ArrayList<EducationModel> peoplelist) {
        super(activity,R.layout.addeducationlistrow,peoplelist);
        this.activity = activity;
        this.educationmodel = peoplelist;

    }

    @Override
    public int getCount() {
        return educationmodel.size();
    }

    @Override
    public EducationModel getItem(int location) {
        return educationmodel.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        if (inflater == null)
            currentposition = position;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertview= inflater.inflate(R.layout.addeducationlistrow, null, true);
        ImageView edit = (ImageView)convertview.findViewById(R.id.editeducation);
        ImageView delete = (ImageView)convertview.findViewById(R.id.deleteeducation);
        qualification = (TextView) convertview.findViewById(R.id.qualificationlistrow);
        institute = (TextView)convertview.findViewById(R.id.institutelistrow);
        board = (TextView)convertview.findViewById(R.id.boardlistrow);
        yearofpassing = (TextView)convertview.findViewById(R.id.yearofpassinglistrow);
        percentagetype = (TextView)convertview.findViewById(R.id.percentagetypelistrow);
        percentage = (TextView)convertview.findViewById(R.id.percentagelistrow);
        graduationtype = (TextView)convertview.findViewById(R.id.graduationtypelistrow);

        educationModel =(EducationModel) getItem(position);

        qualification.setText(educationModel.getQualification());
        institute.setText(educationModel.getInstitute());
        board.setText(educationModel.getBoardUniversity());
        yearofpassing.setText(educationModel.getPassingYear());
        percentagetype.setText(educationModel.getGradingType());
        percentage.setText(educationModel.getPercentage());
        graduationtype.setText(educationModel.getGraduationType());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
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
                qualificatioN.getEditText().setText(((EducationModel) educationmodel.get(position)).getQualification());
                institute.getEditText().setText(((EducationModel) educationmodel.get(position)).getInstitute());
                boruni.getEditText().setText(((EducationModel) educationmodel.get(position)).getBoardUniversity());
                percentagecgpa.getEditText().setText(((EducationModel) educationmodel.get(position)).getPercentage());
                passingyear.getEditText().setText(((EducationModel) educationmodel.get(position)).getPassingYear());

                Button save = (Button)dialogView.findViewById(R.id.save_education);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_education);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedId = dialogRadioGroup .getCheckedRadioButtonId();
                        int selectedId1 = dialogRadioGroup1 .getCheckedRadioButtonId();
                        // find the radio button by returned id
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
//                                educationQualification.saveDetails(jobrole,institutE,borunI,percga,payear,percentageType,graduationType);
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
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
                builder.setTitle("Confirmation Delete !");
                builder.setMessage("Surely You Want Delete This Details");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"Deleted",Toast.LENGTH_LONG).show();
                        educationmodel.remove(educationmodel.get(currentposition));
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        return convertview;
    }


}