package com.indianservers.buildresume.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.indianservers.buildresume.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indianservers.buildresume.ScrollableTabsActivity;
import com.indianservers.buildresume.adapter.ReferencesListview;
import com.indianservers.buildresume.model.ReferencesModel;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.realm.RealmController;
import com.indianservers.buildresume.service.AlertDailogManager;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.Realm;


public class References extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public static ArrayList<ReferencesModel> referencesModels = new ArrayList<>();
    public static ArrayList<ReferencesModel> referencesModelsup;
    public ListView listView;
    ReferencesListview referencesListview;
    private Realm realm;
    String itemId;
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
        listView = (ListView)itemView.findViewById(R.id.addreflistview);

        this.realm = RealmController.with(this).getRealm();
        itemId = ScrollableTabsActivity.itemid;
        if(itemId==null){
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("RefList", null);
                Type type = new TypeToken<ArrayList<ReferencesModel>>() {}.getType();
                referencesModels = gson.fromJson(json, type);

                if(referencesModels==null){
                    referencesModels = new ArrayList<>();
                }else{
                    referencesListview = new ReferencesListview(getActivity(), referencesModels);
                    listView.setAdapter(referencesListview);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {
            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            referencesModelsup = new ArrayList<>(saveDataModels.getReferencesModels());
            referencesListview = new ReferencesListview(getActivity(), referencesModelsup);
            listView.setAdapter(referencesListview);
            referencesListview.notifyDataSetInvalidated();
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("RefList", null);
                Type type = new TypeToken<ArrayList<ReferencesModel>>() {}.getType();
                referencesModels = gson.fromJson(json, type);

                if(referencesModels==null){
                    referencesModels = new ArrayList<>();
                    referencesListview = new ReferencesListview(getActivity(), referencesModelsup);
                    listView.setAdapter(referencesListview);
                    referencesListview.notifyDataSetInvalidated();
                }else{
                    referencesModelsup.addAll(referencesModels);
                    referencesListview = new ReferencesListview(getActivity(), referencesModelsup);
                    listView.setAdapter(referencesListview);
                    referencesListview.notifyDataSetChanged();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        };
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
                final String semailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String mobileval = "^[789]\\d{9}$";
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
                            }else if(!duratIon.matches(semailPattern)){
                                AlertDailogManager dailogManager = new AlertDailogManager();
                                dailogManager.showAlertDialog(getContext(),"Enter Correct Email",false);
                            }else if(!teamMem.matches(mobileval)){
                                AlertDailogManager dailogManager = new AlertDailogManager();
                                dailogManager.showAlertDialog(getContext(),"Enter Correct Mobile Number",false);
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
        if(itemId==null){
            referencesModels.add(projectDetailModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(referencesModels);
            prefsEditor.putString("RefList", json);
            prefsEditor.commit();
            referencesListview = new ReferencesListview(getActivity(),referencesModels);
            listView.setAdapter(referencesListview);
            referencesListview.notifyDataSetInvalidated();
        }else {
            referencesModels.removeAll(referencesModelsup);
            referencesModels.add(projectDetailModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(referencesModels);
            prefsEditor.putString("RefList", json);
            prefsEditor.commit();
            referencesModels.addAll(referencesModelsup);
            referencesListview = new ReferencesListview(getActivity(),referencesModels);
            referencesModelsup.addAll(referencesModels);
            listView.setAdapter(referencesListview);
            referencesListview.notifyDataSetInvalidated();
        }

    }
}
