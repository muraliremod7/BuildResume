package com.example.jntuh.buildresume.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.adapter.WorkExperienceListview;
import com.example.jntuh.buildresume.model.WorkExperienceModel;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WorkExperience extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public ArrayList<WorkExperienceModel> experienceModels;
    public ListView listView;
    WorkExperienceListview workExperienceListview;
    String toworkk = null;
    public TextInputLayout fromWork,toWork;
    public WorkExperience() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview = inflater.inflate(R.layout.fragment_work_experience, container, false);
        actionButton = (FloatingActionButton)itemview.findViewById(R.id.addworkexperience);
        actionButton.setOnClickListener(this);
        experienceModels = new ArrayList<WorkExperienceModel>();
        workExperienceListview = new WorkExperienceListview(getActivity(), experienceModels);
        listView = (ListView)itemview.findViewById(R.id.worklistview);
        return itemview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addworkexperience:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addworkexperience, null);
                builder.setTitle("Add Education Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final CheckBox checkBox = (CheckBox)dialogView.findViewById(R.id.checkBoxwe);
                final TextInputLayout jobRole = (TextInputLayout)dialogView.findViewById(R.id.jobtitle);
                final TextInputLayout jobDescription = (TextInputLayout)dialogView.findViewById(R.id.jobdescription);
                final TextInputLayout comapnyName = (TextInputLayout)dialogView.findViewById(R.id.companyname);
                fromWork = (TextInputLayout)dialogView.findViewById(R.id.workfrom);
                toWork = (TextInputLayout)dialogView.findViewById(R.id.workto);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            Toast.makeText(getContext(), "checked",
                                    Toast.LENGTH_SHORT).show();
                            toWork.getEditText().setText("Present");

                        }else{
                            toWork.getEditText().setText(" ");

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

                Button save = (Button)dialogView.findViewById(R.id.save_education);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_education);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{
                            String jobtitle = jobRole.getEditText().getText().toString();
                            String jobdes = jobDescription.getEditText().getText().toString();
                            String comname = comapnyName.getEditText().getText().toString();
                            String fromwork = fromWork.getEditText().getText().toString();
                            String towork = toWork.getEditText().getText().toString();
                            if(jobtitle==null||jobtitle==""||jobdes==""||jobdes==null||comname==null||comname==""||fromwork==null||fromwork==""||towork==null||towork==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
                                workexperiencesaveDetails(jobtitle,jobdes,comname,fromwork,toworkk);
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

    private void workexperiencesaveDetails(String jobtitle, String jobdes, String comname, String fromwork, String toworkk) {
        WorkExperienceModel experienceModel = new WorkExperienceModel(jobtitle,jobdes,comname,fromwork,toworkk);
        experienceModels.add(experienceModel);
        listView.setAdapter(workExperienceListview);
        workExperienceListview.notifyDataSetInvalidated();
    }
}
