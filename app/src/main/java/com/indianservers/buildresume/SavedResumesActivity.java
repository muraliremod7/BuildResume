package com.indianservers.buildresume;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.indianservers.buildresume.adapter.ShowResumesListAdapter;
import com.indianservers.buildresume.model.ResumeModel;

import java.io.File;
import java.util.ArrayList;

public class SavedResumesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ResumeModel> resumeModels;
    File sdcard = Environment.getExternalStorageDirectory();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_resumes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Saved Resumes");
        listView = (ListView)findViewById(R.id.resumeslist);
        resumeModels = new ArrayList<>();
        ShowResumesListAdapter listAdapter = new ShowResumesListAdapter(this,resumeModels);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        getresumes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resumetitle = ((TextView)view.findViewById(R.id.resumename)).getText().toString();
                File file = new File(sdcard,"/BuildResume/"+resumetitle+".pdf");

                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        Toast.makeText(SavedResumesActivity.this,
                                "No Application Available to View PDF",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getresumes() {
        File yourDir = new File(sdcard, "/BuildResume/");
        if(yourDir==null){

        }else{
            if(yourDir.listFiles()==null){

            }else{
                for (File f : yourDir.listFiles()) {

                    ResumeModel resumeModel = new ResumeModel();

                    if (f.isFile()) {
                        String name = f.getName();
                        if (name.indexOf(".") > 0)
                            name = name.substring(0, name.lastIndexOf("."));
                        resumeModel.setResumename(name);
                        resumeModels.add(resumeModel);
                        ShowResumesListAdapter listAdapter = new ShowResumesListAdapter(this,resumeModels);
                        try{
                            listView.setAdapter(listAdapter);
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    } else {

                    }
                }
            }

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
