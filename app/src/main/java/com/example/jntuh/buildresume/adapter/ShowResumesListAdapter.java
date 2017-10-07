package com.example.jntuh.buildresume.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.model.ResumeModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JNTUH on 03-10-2017.
 */

public class ShowResumesListAdapter extends BaseAdapter {
    private Context context;
    public Resources res;
    public String string;
    private LayoutInflater inflater;
    List<ResumeModel> mQuestionsSet = new ArrayList<>();
    public ResumeModel resumeModel;
    public ShowResumesListAdapter(Context context, List<ResumeModel> mQuestionsSet) {
        this.context = context;
        this.mQuestionsSet = mQuestionsSet;
    }
    @Override
    public int getCount() {
        return mQuestionsSet.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView;
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.resumelistrow, null);
        TextView textView = (TextView) rowView.findViewById(R.id.resumename);
        ImageView delete = (ImageView)rowView.findViewById(R.id.delete);
        ImageView share = (ImageView)rowView.findViewById(R.id.share);
        ResumeModel resumeModel = (ResumeModel)mQuestionsSet.get(i);
        textView.setText(resumeModel.getResumename());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard,"/BuildResume/"+((ResumeModel) mQuestionsSet.get(i)).getResumename()+".pdf");

                    if(file.exists())
                    {
                        file.delete();
                        mQuestionsSet.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context,"File Deleted",Toast.LENGTH_LONG).show();
                    }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdcard = Environment.getExternalStorageDirectory();
                String file = new File(sdcard,"/BuildResume/"+((ResumeModel) mQuestionsSet.get(i)).getResumename()+".pdf").getAbsolutePath();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" +file));
                context.startActivity(Intent.createChooser(share, "Share File"));
            }
        });

        return rowView;
    }
}
