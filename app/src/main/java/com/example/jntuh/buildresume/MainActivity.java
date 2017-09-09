package com.example.jntuh.buildresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button create,save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create = (Button)findViewById(R.id.createResume);
        create.setOnClickListener(this);
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
            case R.id.savedResumes:
                break;
        }

    }
}
