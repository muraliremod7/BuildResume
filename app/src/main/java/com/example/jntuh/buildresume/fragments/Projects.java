package com.example.jntuh.buildresume.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.adapter.ProjectDetailsListview;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.example.jntuh.buildresume.model.WorkExperienceModel;

import java.util.ArrayList;


public class Projects extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    ArrayList<ProjectDetailModel> detailModels;
    public ListView listView;
    ProjectDetailsListview detailsListview;
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
                final TextInputLayout duration = (TextInputLayout)dialogView.findViewById(R.id.durationofproject);
                final TextInputLayout teammem = (TextInputLayout)dialogView.findViewById(R.id.teammembers);

                Button save = (Button)dialogView.findViewById(R.id.save_project);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_project);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{
                            String projectTitle = projecttitle.getEditText().getText().toString();
                            String projectDesc = projectdesc.getEditText().getText().toString();
                            String yourRole = yourrole.getEditText().getText().toString();
                            String duratIon = duration.getEditText().getText().toString();
                            String teamMem = teammem.getEditText().getText().toString();
                            if(projectTitle==null||projectTitle==""||projectDesc==""||projectDesc==null||teamMem==null||teamMem==""||yourRole==null||yourRole==""||duratIon==null||duratIon==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
                                projectdetailssaveDetails(projectTitle,projectDesc,yourRole,duratIon,teamMem);
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

    private void projectdetailssaveDetails(String projectTitle, String projectDesc, String yourRole, String duratIon, String teamMem) {
        ProjectDetailModel projectDetailModel = new ProjectDetailModel(projectTitle,projectDesc,yourRole,duratIon,teamMem);
        detailModels.add(projectDetailModel);
        listView.setAdapter(detailsListview);
        detailsListview.notifyDataSetInvalidated();
    }
}
