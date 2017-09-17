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
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.adapter.ReferencesListview;
import com.example.jntuh.buildresume.model.ReferencesModel;

import java.util.ArrayList;


public class References extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public ArrayList<ReferencesModel> referencesModels;
    public ListView listView;
    ReferencesListview detailsListview;
    public References() {
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
        View itemView = inflater.inflate(R.layout.fragment_references, container, false);
        actionButton = (FloatingActionButton)itemView.findViewById(R.id.addrefe);
        actionButton.setOnClickListener(this);
        referencesModels = new ArrayList<ReferencesModel>();
        detailsListview = new ReferencesListview(getActivity(), referencesModels);
        listView = (ListView)itemView.findViewById(R.id.addreflistview);
        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addrefe:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addreferences, null);
                builder.setTitle("Add Reference Details");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                final TextInputLayout refName = (TextInputLayout)dialogView.findViewById(R.id.refname);
                final TextInputLayout refDesi = (TextInputLayout)dialogView.findViewById(R.id.refdesignation);
                final TextInputLayout reforg = (TextInputLayout)dialogView.findViewById(R.id.reforganization);
                final TextInputLayout refemail = (TextInputLayout)dialogView.findViewById(R.id.refemail);
                final TextInputLayout refmobile = (TextInputLayout)dialogView.findViewById(R.id.refmobile);

                Button save = (Button)dialogView.findViewById(R.id.save_reference);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_reference);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{

                            String projectTitle = refName.getEditText().getText().toString();
                            String projectDesc = refDesi.getEditText().getText().toString();
                            String yourRole = reforg.getEditText().getText().toString();
                            String duratIon = refemail.getEditText().getText().toString();
                            String teamMem = refmobile.getEditText().getText().toString();
                            if(projectDesc==""||projectDesc==null||yourRole==null||yourRole==""||duratIon==null||duratIon==""||teamMem==null||teamMem==""){
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
        ReferencesModel projectDetailModel = new ReferencesModel(projectTitle,projectDesc,yourRole,duratIon,teamMem);
        referencesModels.add(projectDetailModel);
        listView.setAdapter(detailsListview);
        detailsListview.notifyDataSetInvalidated();
    }
}
