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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.fragments.Projects;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class ProjectDetailsListview extends ArrayAdapter<ProjectDetailModel>{
    TextView projecttitle, projectdesc, yourrole, durationfrom, durationto, teammem;
    public final Activity activity;
    public int currentposition;
    public ArrayList<ProjectDetailModel> detailModels;
    String toworkk;
    ProjectDetailModel projectDetailModel;

    public ProjectDetailsListview(Activity activity, ArrayList<ProjectDetailModel> peoplelist) {
        super(activity,R.layout.addprojectslistrow,peoplelist);
        this.activity = activity;
        this.detailModels = peoplelist;

    }

    @Override
    public int getCount() {
        return detailModels.size();
    }

    @Override
    public ProjectDetailModel getItem(int location) {
        return detailModels.get(location);
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
        convertview= inflater.inflate(R.layout.addprojectslistrow, null, true);
        ImageView edit = (ImageView)convertview.findViewById(R.id.editproject);
        ImageView delete = (ImageView)convertview.findViewById(R.id.deleteproject);
        projecttitle = (TextView) convertview.findViewById(R.id.projecttitlelistrow);
        projectdesc = (TextView)convertview.findViewById(R.id.projectdetailslistrow);
        yourrole = (TextView)convertview.findViewById(R.id.yourolelistrow);
        durationfrom = (TextView)convertview.findViewById(R.id.durationfromlistrow);
        durationto = (TextView)convertview.findViewById(R.id.durationtolistrow);
        teammem = (TextView)convertview.findViewById(R.id.noofteammemlistrow);

        projectDetailModel =(ProjectDetailModel) getItem(position);

        projecttitle.setText(projectDetailModel.getProjecttitle());
        projectdesc.setText(projectDetailModel.getProjectdesc());
        yourrole.setText(projectDetailModel.getYourrole());
        durationfrom.setText(projectDetailModel.getDurationfrom());
        durationto.setText(projectDetailModel.getDurationto());
        teammem.setText(projectDetailModel.getTeammem());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addprojects, null);
                builder.setTitle("Add Project Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final TextInputLayout projecttitle = (TextInputLayout)dialogView.findViewById(R.id.projectname);
                final TextInputLayout projectdesc = (TextInputLayout)dialogView.findViewById(R.id.projectdetails);
                final TextInputLayout yourrole = (TextInputLayout)dialogView.findViewById(R.id.yourrole);
                final TextInputLayout durationfrom = (TextInputLayout)dialogView.findViewById(R.id.durationfrom);
                final TextInputLayout durationto = (TextInputLayout)dialogView.findViewById(R.id.durationto);
                final TextInputLayout teammem = (TextInputLayout)dialogView.findViewById(R.id.teammembers);
                final CheckBox checkBox = (CheckBox)dialogView.findViewById(R.id.checkBoxpd);

                projecttitle.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getProjecttitle());
                projectdesc.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getProjectdesc());
                yourrole.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getYourrole());
                durationfrom.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getDurationfrom());
                durationto.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getDurationto());
                teammem.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getTeammem());

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            Toast.makeText(getContext(), "checked",
                                    Toast.LENGTH_SHORT).show();
                            durationto.getEditText().setText("Present");

                        }else{
                            durationto.getEditText().setText(" ");

                            Toast.makeText(getContext(), "unchecked",
                                    Toast.LENGTH_SHORT).show();
                            durationto.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                                                durationto.getEditText().setText(dateFormat.format(calendar.getTime()));
                                            }
                                        });
                                        yearMonthPickerDialog.show();
                                    } else {
                                        // Hide your calender here
                                    }
                                }
                            });
                            toworkk = durationto.getEditText().getText().toString();
                        }
                    }
                });
                //work from and work to functionalities ===========================================================
                durationfrom.getEditText().setOnTouchListener(new View.OnTouchListener() {
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

                                    durationfrom.getEditText().setText(dateFormat.format(calendar.getTime()));
                                }
                            });
                            yearMonthPickerDialog.show();
                        }
                        return false;
                    }
                });

                durationto.getEditText().setOnTouchListener(new View.OnTouchListener() {
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

                                    durationto.getEditText().setText(dateFormat.format(calendar.getTime()));
                                }
                            });
                            yearMonthPickerDialog.show();
                        }
                        return false;
                    }
                });
                Button save = (Button)dialogView.findViewById(R.id.save_project);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_project);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{
                            String projectTitle = projecttitle.getEditText().getText().toString();
                            String projectDesc = projectdesc.getEditText().getText().toString();
                            String yourRole = yourrole.getEditText().getText().toString();
                            String percga = durationfrom.getEditText().getText().toString();
                            String durationTo = durationto.getEditText().getText().toString();
                            String teamMem = teammem.getEditText().getText().toString();
                            if(projectTitle==null||projectTitle==""||projectDesc==""||projectDesc==null||teamMem==null||teamMem==""||yourRole==null||yourRole==""||percga==null||percga==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
                                ProjectDetailModel projectDetailModel = new ProjectDetailModel();
                                projectDetailModel.setProjecttitle(projectTitle);
                                projectDetailModel.setProjectdesc(projectDesc);
                                projectDetailModel.setYourrole(yourRole);
                                projectDetailModel.setDurationfrom(percga);
                                projectDetailModel.setDurationto(durationTo);
                                projectDetailModel.setTeammem(teamMem);
                                Projects.detailModels.set(position,projectDetailModel);
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
                        detailModels.remove(detailModels.get(currentposition));
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