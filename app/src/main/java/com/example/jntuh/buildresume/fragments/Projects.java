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
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.adapter.ProjectDetailsListview;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;


public class Projects extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public static ArrayList<ProjectDetailModel> detailModels=null;
    public ListView listView;
    ProjectDetailsListview detailsListview;
    private Realm realm;
    String toworkk = null;
    public Projects() {
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
        View itemView =  inflater.inflate(R.layout.fragment_projects, container, false);
        actionButton = (FloatingActionButton)itemView.findViewById(R.id.addprojects);
        actionButton.setOnClickListener(this);
        detailModels = new ArrayList<ProjectDetailModel>();
        detailsListview = new ProjectDetailsListview(getActivity(),detailModels);
        listView = (ListView)itemView.findViewById(R.id.projectlistview);
        String itemId = ScrollableTabsActivity.itemid;
        this.realm = RealmController.with(this).getRealm();
        if(itemId==null){

        }else {
            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            detailModels = new ArrayList<>(saveDataModels.getProjectDetailModels());
            detailsListview = new ProjectDetailsListview(getActivity(), detailModels);
            listView.setAdapter(detailsListview);
            detailsListview.notifyDataSetInvalidated();
        };
        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addprojects:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
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
                                projectdetailssaveDetails(projectTitle,projectDesc,yourRole,percga,durationTo,teamMem);
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

    private void projectdetailssaveDetails(String projectTitle, String projectDesc, String yourRole, String duratIon,String durationto, String teamMem) {
        ProjectDetailModel projectDetailModel = new ProjectDetailModel(projectTitle,projectDesc,yourRole,duratIon,durationto,teamMem);
        detailModels.add(projectDetailModel);
        detailsListview = new ProjectDetailsListview(getActivity(),detailModels);
        listView.setAdapter(detailsListview);
        detailsListview.notifyDataSetInvalidated();
    }
}
