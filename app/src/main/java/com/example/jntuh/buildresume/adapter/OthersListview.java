package com.example.jntuh.buildresume.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.model.OthersModel;

import java.util.ArrayList;

/**
 * Created by JNTUH on 13-09-2017.
 */

public class OthersListview extends ArrayAdapter<OthersModel>{
    TextView valueothers;
    public final Activity activity;
    public int currentposition;
    public ArrayList<OthersModel> othersModels;
    OthersModel referencesModel = null;


    public OthersListview(Activity activity, ArrayList<OthersModel> peoplelist ) {
        super(activity, R.layout.addreferenceslistrow,peoplelist);
        this.activity = activity;
        this.othersModels = peoplelist;
    }

    @Override
    public int getCount() {
        return othersModels.size();
    }

    @Override
    public OthersModel getItem(int location) {
        return othersModels.get(location);
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
        convertview= inflater.inflate(R.layout.addvaluelistrow, null, true);
        ImageView edit = (ImageView)convertview.findViewById(R.id.editvalue);
        ImageView delete = (ImageView)convertview.findViewById(R.id.deletevalue);
        valueothers = (TextView) convertview.findViewById(R.id.value);

        referencesModel =(OthersModel) getItem(position);

        valueothers.setText(referencesModel.getValue());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addvalue, null);
                builder.setTitle("Update Reference Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final TextInputLayout valueOthers = (TextInputLayout)dialogView.findViewById(R.id.valueother);


                valueOthers.getEditText().setText(((OthersModel) othersModels.get(position)).getValue());


                Button save = (Button)dialogView.findViewById(R.id.save_value);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_value);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{

                            String qualification = valueOthers.getEditText().getText().toString();

                            if(qualification==null||qualification==""){
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
                        othersModels.remove(othersModels.get(currentposition));
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
