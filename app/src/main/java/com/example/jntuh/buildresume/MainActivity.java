package com.example.jntuh.buildresume;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button create,save,created;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create = (Button)findViewById(R.id.createResume);
        create.setOnClickListener(this);
        created = (Button)findViewById(R.id.createdResumes);
        created.setOnClickListener(this);
        save = (Button)findViewById(R.id.savedResumes);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.createResume:
                Intent cr = new Intent(MainActivity.this,ScrollableTabsActivity.class);
                startActivity(cr);
                break;
            case R.id.createdResumes:
                Intent cdrs = new Intent(MainActivity.this,ShowDataActivity.class);
                startActivity(cdrs);
                break;
            case R.id.savedResumes:
                Intent saved = new Intent(MainActivity.this,SavedResumesActivity.class);
                startActivity(saved);
                break;
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                })
                .show();

    }
}
