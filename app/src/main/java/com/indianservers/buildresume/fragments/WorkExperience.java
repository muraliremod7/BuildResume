package com.indianservers.buildresume.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.indianservers.buildresume.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indianservers.buildresume.ScrollableTabsActivity;
import com.indianservers.buildresume.adapter.WorkExperienceListview;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.model.WorkExperienceModel;
import com.indianservers.buildresume.realm.RealmController;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;


public class WorkExperience extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public static ArrayList<WorkExperienceModel> experienceModels = new ArrayList<>();
    public ListView listView;
    public WorkExperienceListview workExperienceListview;
    String toworkk = null;
    public TextInputLayout fromWork,toWork;
    private Realm realm;
    public static ArrayList<WorkExperienceModel> wemodels;
    public String itemId;
    public WorkExperience() {
        // Required empty public constructor
    }
    public static WorkExperience newInstance(){
        return new WorkExperience();
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
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
        listView = (ListView)itemview.findViewById(R.id.worklistview);
        actionButton = (FloatingActionButton)itemview.findViewById(R.id.addworkexperience);
        actionButton.setOnClickListener(this);
        this.realm = RealmController.with(this).getRealm();

        itemId = ScrollableTabsActivity.itemid;
        if(itemId==null){
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("ExpList", null);
                Type type = new TypeToken<ArrayList<WorkExperienceModel>>() {}.getType();
                experienceModels = gson.fromJson(json, type);

                if(experienceModels==null){
                    experienceModels = new ArrayList<>();
                }else{
                    workExperienceListview = new WorkExperienceListview(getActivity(), experienceModels);
                    listView.setAdapter(workExperienceListview);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        else{

            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            wemodels = new ArrayList<>(saveDataModels.getExperienceModels());
            workExperienceListview = new WorkExperienceListview(getActivity(), wemodels);
            listView.setAdapter(workExperienceListview);
            workExperienceListview.notifyDataSetChanged();
            try{
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                String json = sharedPrefs.getString("ExpList", null);
                Type type = new TypeToken<ArrayList<WorkExperienceModel>>() {}.getType();
                experienceModels = gson.fromJson(json, type);

                if(experienceModels==null){
                    experienceModels = new ArrayList<>();
                    workExperienceListview = new WorkExperienceListview(getActivity(), wemodels);
                    listView.setAdapter(workExperienceListview);
                    workExperienceListview.notifyDataSetChanged();
                }else{
                    wemodels.addAll(experienceModels);
                    workExperienceListview = new WorkExperienceListview(getActivity(), wemodels);
                    listView.setAdapter(workExperienceListview);
                    workExperienceListview.notifyDataSetChanged();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
        return itemview;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addworkexperience:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.addworkexperience, null);
                builder.setTitle("Add Experience Details");
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

                Button save = (Button)dialogView.findViewById(R.id.save_work);
                Button cancel = (Button)dialogView.findViewById(R.id.cancel_work);
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
                                workexperiencesaveDetails(jobtitle,jobdes,comname,fromwork,towork);
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

    public void workexperiencesaveDetails(String jobtitle, String jobdes, String comname, String fromwork, String toworkk) {
        WorkExperienceModel experienceModel = new WorkExperienceModel();
        experienceModel.setJobtitle(jobtitle);
        experienceModel.setJobrole(jobtitle);
        experienceModel.setJobdescription(jobdes);
        experienceModel.setCompanyname(comname);
        experienceModel.setFromwork(fromwork);
        experienceModel.setTowork(toworkk);
        if(itemId==null){
            experienceModels.add(experienceModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(experienceModels);
            prefsEditor.putString("ExpList", json);
            prefsEditor.commit();
            workExperienceListview = new WorkExperienceListview(getActivity(),experienceModels);
            listView.setAdapter(workExperienceListview);
            workExperienceListview.notifyDataSetChanged();
        }else{
            experienceModels.removeAll(wemodels);
            experienceModels.add(experienceModel);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(experienceModels);
            prefsEditor.putString("ExpList", json);
            prefsEditor.commit();
            experienceModels.addAll(wemodels);
            workExperienceListview = new WorkExperienceListview(getActivity(),experienceModels);
            wemodels.add(experienceModel);
            listView.setAdapter(workExperienceListview);
            workExperienceListview.notifyDataSetChanged();
        }

    }

}
