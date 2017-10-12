package com.example.jntuh.buildresume.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.adapter.OthersListview;
import com.example.jntuh.buildresume.model.OthersModel;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.Realm;


public class Other extends Fragment implements View.OnClickListener{
    public Button addskills,addach,addhobbys,addlan;
    public ListView addSkillslv,addAchlv,addHobbyslv,addLanlv;
    public  static ArrayList<OthersModel> detailModels = new ArrayList<>();
    public  static ArrayList<OthersModel> detailModels1 = new ArrayList<>();
    public  static ArrayList<OthersModel> detailModels2 = new ArrayList<>();
    public  static ArrayList<OthersModel> detailModels3 = new ArrayList<>();
    public  static ArrayList<OthersModel> detailModelsup;
    public  static ArrayList<OthersModel> detailModels1up;
    public  static ArrayList<OthersModel> detailModels2up;
    public  static ArrayList<OthersModel> detailModels3up;
    public OthersListview othersListview;
    public OthersListview othersListview1;
    public OthersListview othersListview2;
    public OthersListview othersListview3;
    String itemId;
    private Realm realm;
    public Other() {
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
        View itemView = inflater.inflate(R.layout.fragment_other, container, false);

        addSkillslv = (ListView)itemView.findViewById(R.id.addskillslistview);
        addAchlv = (ListView)itemView.findViewById(R.id.addachivementslistview);
        addHobbyslv = (ListView)itemView.findViewById(R.id.addhobbyslistview);
        addLanlv = (ListView)itemView.findViewById(R.id.addlanlistview);

         itemId = ScrollableTabsActivity.itemid;

        addskills = (Button)itemView.findViewById(R.id.addskills);
        addskills.setOnClickListener(this);
        addach = (Button)itemView.findViewById(R.id.addachivements);
        addach.setOnClickListener(this);
        addhobbys = (Button)itemView.findViewById(R.id.addhobbys);
        addhobbys.setOnClickListener(this);
        addlan = (Button)itemView.findViewById(R.id.addlan);
        addlan.setOnClickListener(this);
        this.realm = RealmController.with(this).getRealm();

        if(itemId==null){
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Lanlist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels = gson.fromJson(json, type);

                if(detailModels==null){
                    detailModels = new ArrayList<>();
                }else{
                    othersListview = new OthersListview(getActivity(), detailModels);
                    addSkillslv.setAdapter(othersListview);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Hobbyslist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels1 = gson.fromJson(json, type);

                if(detailModels1==null){
                    detailModels1 = new ArrayList<>();
                }else{
                    othersListview1 = new OthersListview(getActivity(), detailModels1);
                    addAchlv.setAdapter(othersListview1);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Achlist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels2 = gson.fromJson(json, type);

                if(detailModels2==null){
                    detailModels2 = new ArrayList<>();
                }else{
                    othersListview2 = new OthersListview(getActivity(), detailModels2);
                    addHobbyslv.setAdapter(othersListview2);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Skillslist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels3 = gson.fromJson(json, type);

                if(detailModels3==null){
                    detailModels3 = new ArrayList<>();
                }else{
                    othersListview3 = new OthersListview(getActivity(), detailModels3);
                    addLanlv.setAdapter(othersListview3);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {

            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            detailModelsup = new ArrayList<>(saveDataModels.getOthersModelsskills());
            othersListview = new OthersListview(getActivity(), detailModelsup);
            addLanlv.setAdapter(othersListview);
            othersListview.notifyDataSetChanged();

            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Lanlist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels = gson.fromJson(json, type);

                if(detailModels==null){
                    detailModels = new ArrayList<>();
                    othersListview = new OthersListview(getActivity(), detailModelsup);
                    addLanlv.setAdapter(othersListview);
                    othersListview.notifyDataSetChanged();
                }else{
                    detailModelsup.addAll(detailModels);
                    othersListview = new OthersListview(getActivity(), detailModelsup);
                    addLanlv.setAdapter(othersListview);
                    othersListview.notifyDataSetChanged();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            detailModels1up = new ArrayList<>(saveDataModels.getOthersModelsache());
            othersListview1 = new OthersListview(getActivity(), detailModels1up);
            addHobbyslv.setAdapter(othersListview1);
            othersListview1.notifyDataSetInvalidated();

            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Hobbyslist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels1 = gson.fromJson(json, type);

                if(detailModels1==null){
                    detailModels1 = new ArrayList<>();
                    othersListview1 = new OthersListview(getActivity(), detailModels1up);
                    addHobbyslv.setAdapter(othersListview1);
                    othersListview1.notifyDataSetInvalidated();
                }else{
                    detailModels1up.addAll(detailModels1);
                    othersListview1 = new OthersListview(getActivity(), detailModels1up);
                    addHobbyslv.setAdapter(othersListview1);
                    othersListview1.notifyDataSetChanged();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            detailModels2up = new ArrayList<>(saveDataModels.getOthersModelshobbys());
            othersListview2 = new OthersListview(getActivity(), detailModels2up);
            addAchlv.setAdapter(othersListview2);
            othersListview2.notifyDataSetInvalidated();
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Achlist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels2 = gson.fromJson(json, type);

                if(detailModels2==null){
                    detailModels2 = new ArrayList<>();
                    othersListview2 = new OthersListview(getActivity(), detailModels2up);
                    addAchlv.setAdapter(othersListview2);
                    othersListview2.notifyDataSetInvalidated();
                }else{
                    detailModels2up.addAll(detailModels2);
                    othersListview2 = new OthersListview(getActivity(), detailModels2up);
                    addAchlv.setAdapter(othersListview2);
                    othersListview2.notifyDataSetChanged();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            detailModels3up = new ArrayList<>(saveDataModels.getOthersModelslan());
            othersListview3 = new OthersListview(getActivity(), detailModels3up);
            addSkillslv.setAdapter(othersListview3);
            othersListview.notifyDataSetInvalidated();
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("Skillslist", null);
                Type type = new TypeToken<ArrayList<OthersModel>>() {}.getType();
                detailModels3 = gson.fromJson(json, type);

                if(detailModels3==null){
                    detailModels3 = new ArrayList<>();
                    othersListview3 = new OthersListview(getActivity(), detailModels3up);
                    addSkillslv.setAdapter(othersListview3);
                    othersListview3.notifyDataSetInvalidated();
                }else{
                    detailModels3up.addAll(detailModels3);
                    othersListview3 = new OthersListview(getActivity(), detailModels3up);
                    addSkillslv.setAdapter(othersListview3);
                    othersListview3.notifyDataSetChanged();
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
            case R.id.addskills:
                addOthers("Add Skills","Skill");
                break;
            case R.id.addachivements:
                addOthers("Add Achivements","Achivement");
                break;
            case R.id.addhobbys:
                addOthers("Add Hobbys","Hobby");
                break;
            case R.id.addlan:
                addOthers("Add Language","Language");
                break;
        }
    }

    private void addOthers(String s, final String s1) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addvalue, null);
        builder.setTitle(s);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        final TextInputLayout valueOthers = (TextInputLayout)dialogView.findViewById(R.id.valueother);
        valueOthers.getEditText().setHint(s1);

        Button save = (Button)dialogView.findViewById(R.id.save_value);
        Button cancel = (Button)dialogView.findViewById(R.id.cancel_value);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String qualification = valueOthers.getEditText().getText().toString();

                    if(qualification==null||qualification==""){
                        Toast.makeText(getContext(),"Should Be Fill All Fields",Toast.LENGTH_LONG).show();
                    }else if(s1.equals("Skill")){
                        otherssavelan(qualification);
                        alertDialog.dismiss();

                    }
                    else if(s1.equals("Achivement")){
                        otherssavehobbys(qualification);
                        alertDialog.dismiss();

                    }
                    else if(s1.equals("Hobby")){
                        otherssaveache(qualification);
                        alertDialog.dismiss();
                    }
                    else if(s1.equals("Language")){
                        otherssaveskills(qualification);
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

    private void otherssaveskills(String qualification) {
        OthersModel othersModel = new OthersModel();
        othersModel.setValue(qualification);
        if(itemId==null){
            detailModels.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels);
            prefsEditor.putString("Lanlist", json);
            prefsEditor.commit();
            othersListview = new OthersListview(getActivity(),detailModels);
            addLanlv.setAdapter(othersListview);
            othersListview.notifyDataSetInvalidated();
        }else{
            detailModels.removeAll(detailModelsup);
            detailModels.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels);
            prefsEditor.putString("Lanlist", json);
            prefsEditor.commit();
            detailModels.addAll(detailModelsup);
            othersListview = new OthersListview(getActivity(),detailModels);
            detailModelsup.add(othersModel);
            addLanlv.setAdapter(othersListview);
            othersListview.notifyDataSetInvalidated();
        }

    }

    private void otherssaveache(String qualification) {
        OthersModel othersModel = new OthersModel();
        othersModel.setValue(qualification);
        if(itemId==null){
            detailModels1.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels1);
            prefsEditor.putString("Hobbyslist", json);
            prefsEditor.commit();
            othersListview1 = new OthersListview(getActivity(),detailModels1);
            addHobbyslv.setAdapter(othersListview1);
            othersListview1.notifyDataSetInvalidated();
        }else {
            detailModels1.removeAll(detailModels1up);
            detailModels1.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels1);
            prefsEditor.putString("Hobbyslist", json);
            prefsEditor.commit();
            detailModels1.addAll(detailModels1up);
            othersListview1 = new OthersListview(getActivity(),detailModels1);
            detailModels1up.add(othersModel);
            addHobbyslv.setAdapter(othersListview1);
            othersListview1.notifyDataSetInvalidated();
        }

    }

    private void otherssavehobbys(String qualification) {
        OthersModel othersModel = new OthersModel();
        othersModel.setValue(qualification);
        if(itemId==null){
            detailModels2.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels2);
            prefsEditor.putString("Achlist", json);
            prefsEditor.commit();
            othersListview2 = new OthersListview(getActivity(),detailModels2);
            addAchlv.setAdapter(othersListview2);
            othersListview2.notifyDataSetInvalidated();
        }else {
            detailModels2.removeAll(detailModels2up);
            detailModels2.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels2);
            prefsEditor.putString("Achlist", json);
            prefsEditor.commit();
            detailModels2.addAll(detailModels2up);
            othersListview2 = new OthersListview(getActivity(),detailModels2);
            detailModels2up.add(othersModel);
            addAchlv.setAdapter(othersListview2);
            othersListview2.notifyDataSetInvalidated();
        }

    }

    private void otherssavelan(String qualification) {
        OthersModel othersModel = new OthersModel();
        othersModel.setValue(qualification);
        if(itemId==null){
            detailModels3.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels3);
            prefsEditor.putString("Skillslist", json);
            prefsEditor.commit();
            othersListview3 = new OthersListview(getActivity(),detailModels3);
            addSkillslv.setAdapter(othersListview3);
            othersListview3.notifyDataSetInvalidated();
        }else {
            detailModels3.removeAll(detailModels3up);
            detailModels3.add(othersModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(detailModels3);
            prefsEditor.putString("Skillslist", json);
            prefsEditor.commit();
            detailModels3.addAll(detailModels3up);
            othersListview3 = new OthersListview(getActivity(),detailModels3);
            detailModels3up.add(othersModel);
            addSkillslv.setAdapter(othersListview3);
            othersListview3.notifyDataSetInvalidated();
        }
    }
}
