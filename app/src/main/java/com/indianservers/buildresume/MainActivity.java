package com.indianservers.buildresume;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button create,save,created;
    private InterstitialAd mInterstitialAd;


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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7132200294304234/7905799702");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // requestNewInterstitial();
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.createResume:
                Intent cr = new Intent(MainActivity.this,ScrollableTabsActivity.class);
                startActivity(cr);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else {

                    Log.d("sai","Ad not loaded ");
                }

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
