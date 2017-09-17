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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.model.ReferencesModel;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JNTUH on 10-09-2017.
 */

public class ReferencesListview extends ArrayAdapter<ReferencesModel>{
    TextView refname, refdesignation, reforganization, refemail, refmobile;
    public final Activity activity;
    public int currentposition;
    public ArrayList<ReferencesModel> referencesModels;
    ReferencesModel referencesModel = null;


    public ReferencesListview(Activity activity, ArrayList<ReferencesModel> peoplelist ) {
        super(activity,R.layout.addreferenceslistrow,peoplelist);
        this.activity = activity;
        this.referencesModels = peoplelist;
    }

    @Override
    public int getCount() {
        return referencesModels.size();
    }

    @Override
    public ReferencesModel getItem(int location) {
        return referencesModels.get(location);
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
        convertview= inflater.inflate(R.layout.addreferenceslistrow, null, true);
        ImageView edit = (ImageView)convertview.findViewById(R.id.editref);
        ImageView delete = (ImageView)convertview.findViewById(R.id.deleteref);
        refname = (TextView) convertview.findViewById(R.id.refnamelistrow);
        refdesignation = (TextView)convertview.findViewById(R.id.refdesglslistrow);
        reforganization = (TextView)convertview.findViewById(R.id.reforganizationlistrow);
        refemail = (TextView)convertview.findViewById(R.id.refemaillistrow);
        refmobile = (TextView)convertview.findViewById(R.id.refmobilelistrow);

        referencesModel =(ReferencesModel) getItem(position);

        refname.setText(referencesModel.getRefname());
        refdesignation.setText(referencesModel.getRefdes());
        reforganization.setText(referencesModel.getReforganization());
        refemail.setText(referencesModel.getRefemail());
        refmobile.setText(referencesModel.getRefmobilenum());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addreferences, null);
                builder.setTitle("Update Reference Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final TextInputLayout refName = (TextInputLayout)dialogView.findViewById(R.id.refname);
                final TextInputLayout refDesi = (TextInputLayout)dialogView.findViewById(R.id.refdesignation);
                final TextInputLayout reforg = (TextInputLayout)dialogView.findViewById(R.id.reforganization);
                final TextInputLayout refemail = (TextInputLayout)dialogView.findViewById(R.id.refemail);
                final TextInputLayout refmobile = (TextInputLayout)dialogView.findViewById(R.id.refmobile);

                refName.getEditText().setText(((ReferencesModel) referencesModels.get(position)).getRefname());
                refDesi.getEditText().setText(((ReferencesModel) referencesModels.get(position)).getRefdes());
                reforg.getEditText().setText(((ReferencesModel) referencesModels.get(position)).getReforganization());
                refemail.getEditText().setText(((ReferencesModel) referencesModels.get(position)).getRefemail());
                refmobile.getEditText().setText(((ReferencesModel) referencesModels.get(position)).getRefmobilenum());

                Button save = (Button)dialogView.findViewById(R.id.save_reference);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_reference);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{

                            String qualification = refName.getEditText().getText().toString();
                            String institutE = refDesi.getEditText().getText().toString();
                            String borunI = reforg.getEditText().getText().toString();
                            String percga = refemail.getEditText().getText().toString();
                            String payear = refmobile.getEditText().getText().toString();
                            if(qualification==null||qualification==""||institutE==""||institutE==null||borunI==null||borunI==""||percga==null||percga==""||payear==null||payear==""){
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
                        referencesModels.remove(referencesModels.get(currentposition));
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