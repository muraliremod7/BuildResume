package com.example.jntuh.buildresume.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.fragments.WorkExperience;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.model.WorkExperienceModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class WorkExperienceListview extends ArrayAdapter<WorkExperienceModel>{
    public TextView jobtitle, jobdescription, companyname, fromwork, towork;
    public final Activity activity;
    public int currentposition;
    public ArrayList<WorkExperienceModel> experienceModels;
    public WorkExperienceModel workExperienceModel;
    public Realm realm;
    String toworkk;

    public WorkExperienceListview(Activity activity, ArrayList<WorkExperienceModel> models) {
        super(activity,R.layout.addworkexperiencelistrow,models);
        this.activity = activity;
        this.experienceModels = models;

    }

    @Override
    public int getCount() {
        return experienceModels.size();
    }

    @Override
    public WorkExperienceModel getItem(int location) {
        return experienceModels.get(location);
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
        convertview= inflater.inflate(R.layout.addworkexperiencelistrow, null,true);
        final ImageView edit = (ImageView)convertview.findViewById(R.id.editworkexperience);
        ImageView delete = (ImageView)convertview.findViewById(R.id.deleteworkexperience);
        jobtitle = (TextView)convertview.findViewById(R.id.jobtitlelistrow);
        jobdescription = (TextView)convertview.findViewById(R.id.jobdescriptionlistrow);
        companyname = (TextView)convertview.findViewById(R.id.companynamelistrow);
        fromwork = (TextView)convertview.findViewById(R.id.fromworklistrow);
        towork = (TextView)convertview.findViewById(R.id.toworklistrow);

        workExperienceModel =(WorkExperienceModel) getItem(position);

        jobtitle.setText(workExperienceModel.getJobtitle());
        jobdescription.setText(workExperienceModel.getJobdescription());
        companyname.setText(workExperienceModel.getCompanyname());
        fromwork.setText(workExperienceModel.getFromwork());
        towork.setText(workExperienceModel.getTowork());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addworkexperience, null);
                builder.setTitle("Add Experience Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final CheckBox checkBox = (CheckBox)dialogView.findViewById(R.id.checkBoxwe);
                final TextInputLayout jobRole = (TextInputLayout)dialogView.findViewById(R.id.jobtitle);
                final TextInputLayout jobDescription = (TextInputLayout)dialogView.findViewById(R.id.jobdescription);
                final TextInputLayout comapnyName = (TextInputLayout)dialogView.findViewById(R.id.companyname);
                final TextInputLayout fromWork = (TextInputLayout)dialogView.findViewById(R.id.workfrom);
                final TextInputLayout toWork = (TextInputLayout)dialogView.findViewById(R.id.workto);

                jobRole.getEditText().setText(((WorkExperienceModel) experienceModels.get(position)).getJobtitle());
                jobDescription.getEditText().setText(((WorkExperienceModel) experienceModels.get(position)).getJobdescription());
                comapnyName.getEditText().setText(((WorkExperienceModel) experienceModels.get(position)).getCompanyname());
                fromWork.getEditText().setText(((WorkExperienceModel) experienceModels.get(position)).getFromwork());
                toWork.getEditText().setText(((WorkExperienceModel) experienceModels.get(position)).getTowork());
               //check box functionality===========================================================================
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            Toast.makeText(getContext(), "checked",
                                    Toast.LENGTH_SHORT).show();
                            toWork.getEditText().setText("Present");

                        }else{
                            Toast.makeText(getContext(), "unchecked",
                                    Toast.LENGTH_SHORT).show();
                            toWork.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(hasFocus) {
                                        // Show your calender here
                                        YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getContext(), new YearMonthPickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onYearMonthSet(int year, int month) {
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.set(Calendar.YEAR, year);
                                                calendar.set(Calendar.MONTH, month);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                                                toWork.getEditText().setText(dateFormat.format(calendar.getTime()));
                                            }
                                        });
                                        yearMonthPickerDialog.show();
                                    } else {
                                        // Hide your calender here
                                    }
                                }
                            });
                            toworkk = toWork.getEditText().getText().toString();
                        }
                    }
                });
                //work from and work to functionalities ===========================================================
                fromWork.getEditText().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(MotionEvent.ACTION_UP == event.getAction()){
                            YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getContext(), new YearMonthPickerDialog.OnDateSetListener() {
                                @Override
                                public void onYearMonthSet(int year, int month) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.YEAR, year);
                                    calendar.set(Calendar.MONTH, month);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                                    fromWork.getEditText().setText(dateFormat.format(calendar.getTime()));
                                }
                            });
                            yearMonthPickerDialog.show();
                        }
                        return false;
                    }
                });

                toWork.getEditText().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(MotionEvent.ACTION_UP == event.getAction()){
                            YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getContext(), new YearMonthPickerDialog.OnDateSetListener() {
                                @Override
                                public void onYearMonthSet(int year, int month) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.YEAR, year);
                                    calendar.set(Calendar.MONTH, month);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                                    toWork.getEditText().setText(dateFormat.format(calendar.getTime()));
                                }
                            });
                            yearMonthPickerDialog.show();
                        }
                        return false;
                    }
                });
                Button save = (Button)dialogView.findViewById(R.id.save_work);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_work);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{
                            final TextInputLayout jobRole = (TextInputLayout)dialogView.findViewById(R.id.jobtitle);
                            final TextInputLayout jobDescription = (TextInputLayout)dialogView.findViewById(R.id.jobdescription);
                            final TextInputLayout comapnyName = (TextInputLayout)dialogView.findViewById(R.id.companyname);
                            final TextInputLayout fromWork = (TextInputLayout)dialogView.findViewById(R.id.workfrom);
                            final TextInputLayout toWork = (TextInputLayout)dialogView.findViewById(R.id.workto);

                            String jobtitlee = jobRole.getEditText().getText().toString();
                            String jobdes = jobDescription.getEditText().getText().toString();
                            String comname = comapnyName.getEditText().getText().toString();
                            String fromworkk = fromWork.getEditText().getText().toString();
                            String toworkk = toWork.getEditText().getText().toString();

                            if(jobtitlee==null||jobtitlee==""||jobdes==""||jobdes==null||comname==null||comname==""||fromworkk==null||fromworkk==""||toworkk==null||toworkk==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
                                WorkExperienceModel experienceModel = new WorkExperienceModel();
                                experienceModel.setJobtitle(jobtitlee);
                                experienceModel.setCompanyname(comname);
                                experienceModel.setJobdescription(jobdes);
                                experienceModel.setFromwork(fromworkk);
                                experienceModel.setTowork(toworkk);
                                WorkExperience.experienceModels.set(position,experienceModel);
                                notifyDataSetChanged();
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
                        experienceModels.remove((WorkExperienceModel) getItem(position));
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