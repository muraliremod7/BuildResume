package com.example.jntuh.buildresume.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.model.ProjectDetailModel;

import java.util.ArrayList;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class ProjectDetailsListview extends ArrayAdapter<ProjectDetailModel>{
    TextView projecttitle, projectdesc, yourrole, duration, teammem;
    public final Activity activity;
    public int currentposition;
    public ArrayList<ProjectDetailModel> detailModels;
    RadioButton radioButton1,radioButton2;
    ProjectDetailModel projectDetailModel = null;
    EducationQualification educationQualification;

    public ProjectDetailsListview(Activity activity, ArrayList<ProjectDetailModel> peoplelist) {
        super(activity,R.layout.addprojectslistrow,peoplelist);
        this.activity = activity;
        this.detailModels = peoplelist;
        this.educationQualification = educationQualification;
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
        duration = (TextView)convertview.findViewById(R.id.durationlistrow);
        teammem = (TextView)convertview.findViewById(R.id.noofteammemlistrow);

        projectDetailModel =(ProjectDetailModel) getItem(position);

        projecttitle.setText(projectDetailModel.getProjecttitle());
        projectdesc.setText(projectDetailModel.getProjectdesc());
        yourrole.setText(projectDetailModel.getYourrole());
        duration.setText(projectDetailModel.getDuration());
        teammem.setText(projectDetailModel.getTeammem());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addprojects, null);
                builder.setTitle("Add Education Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final TextInputLayout projectTitle = (TextInputLayout)dialogView.findViewById(R.id.projectname);
                final TextInputLayout projectDesc = (TextInputLayout)dialogView.findViewById(R.id.projectdetails);
                final TextInputLayout yourRole = (TextInputLayout)dialogView.findViewById(R.id.yourrole);
                final TextInputLayout duratIon = (TextInputLayout)dialogView.findViewById(R.id.durationofproject);
                final TextInputLayout teamMem = (TextInputLayout)dialogView.findViewById(R.id.teammembers);

                projectTitle.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getProjecttitle());
                projectDesc.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getProjectdesc());
                yourRole.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getYourrole());
                duratIon.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getDuration());
                teamMem.getEditText().setText(((ProjectDetailModel) detailModels.get(position)).getTeammem());

                Button save = (Button)dialogView.findViewById(R.id.save_project);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_project);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{
                            String qualification = projectTitle.getEditText().getText().toString();
                            String institutE = projectDesc.getEditText().getText().toString();
                            String borunI = yourRole.getEditText().getText().toString();
                            String percga = duratIon.getEditText().getText().toString();
                            String payear = teamMem.getEditText().getText().toString();
                            if(qualification==null||qualification==""||institutE==""||institutE==null||borunI==null||borunI==""||percga==null||percga==""||payear==null||payear==""){
                                Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                            }else{
//                                educationQualification.saveDetails(projecttitle,institutE,borunI,percga,payear,percentageType,graduationType);
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
                Toast.makeText(getContext(),"Deleted",Toast.LENGTH_LONG).show();
                detailModels.remove(detailModels.get(currentposition));
                notifyDataSetChanged();
            }
        });
        return convertview;
    }


}