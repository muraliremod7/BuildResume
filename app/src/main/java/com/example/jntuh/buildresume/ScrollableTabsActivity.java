package com.example.jntuh.buildresume;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jntuh.buildresume.app.Prefs;
import com.example.jntuh.buildresume.fragments.Declaration;
import com.example.jntuh.buildresume.fragments.Projects;
import com.example.jntuh.buildresume.fragments.WorkExperience;
import com.example.jntuh.buildresume.fragments.ContactInformation;
import com.example.jntuh.buildresume.fragments.References;
import com.example.jntuh.buildresume.fragments.Other;
import com.example.jntuh.buildresume.fragments.EducationQualification;
import com.example.jntuh.buildresume.fragments.CareerObjective;
import com.example.jntuh.buildresume.model.EducationModel;
import com.example.jntuh.buildresume.model.OthersModel;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.example.jntuh.buildresume.model.ReferencesModel;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.model.WorkExperienceModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.example.jntuh.buildresume.service.AlertDailogManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class ScrollableTabsActivity extends AppCompatActivity {
    public static String id;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ContactInformation information = new ContactInformation();
    EducationQualification qualification = new EducationQualification();
    WorkExperience experience = new WorkExperience();
    Projects projects = new Projects();
    Other other = new Other();
    References references = new References();
    CareerObjective careerObjective = new CareerObjective();
    Declaration declaration = new Declaration();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);
        realm = RealmController.with(this).getRealm();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
                   if(id==null){

        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ContactInformation(), "CONTACT INFORMATION");
        adapter.addFrag(new CareerObjective(), "CAREER OBJECTIVE");
        adapter.addFrag(new EducationQualification(), "EDUCATION QUALIFICATION");
        adapter.addFrag(new WorkExperience(), "WORK EXPERIENCE");
        adapter.addFrag(new Projects(), "PROJECTS");
        adapter.addFrag(new Other(), "OTHER");
        adapter.addFrag(new References(), "REFERENCES");
        adapter.addFrag(new Declaration(), "DECLARATION");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_resume, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                exitByBackKey();
                break;
            case R.id.saveddata:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        if(id==null){
            try{
                SaveDataModel book = new SaveDataModel();
                long i= 1 + System.currentTimeMillis();//L is the suffix for long
                String id =String.valueOf(i);//Now it will return "9993939399"
                book.setId(id);
                book.setProfilename(information.profile.getEditText().getText().toString());
                book.setName(information.name.getEditText().getText().toString());
                book.setEmail(information.email.getEditText().getText().toString());
                book.setMobile(information.mobile.getEditText().getText().toString());
                book.setDateofbirth(information.dateofbirth.getEditText().getText().toString());
                book.setGender(information.gender.getEditText().getText().toString());
                book.setMarriagestatus(information.maritialstatus.getEditText().getText().toString());
                book.setAddress(information.address.getEditText().getText().toString());
                book.setCity(information.city.getEditText().getText().toString());
                book.setState(information.state.getEditText().getText().toString());
                book.setCountry(information.country.getEditText().getText().toString());
                book.setPincode(information.pincode.getEditText().getText().toString());
                book.setCountry(information.country.getEditText().getText().toString());
                if(careerObjective.textInputLayout==null){

                }else{
                    book.setCareerobjective(careerObjective.textInputLayout.getEditText().getText().toString());

                }
                if(qualification.models==null){

                }else{
                    book.setEducationModels(new RealmList<EducationModel>(qualification.models.toArray(new EducationModel[qualification.models.size()])));
                }
                if(experience.experienceModels==null){

                }else{
                    book.setExperienceModels(new RealmList<WorkExperienceModel>(experience.experienceModels.toArray(new WorkExperienceModel[experience.experienceModels.size()])));

                }
                if(projects.detailModels==null){

                }else{
                    book.setProjectDetailModels(new RealmList<ProjectDetailModel>(projects.detailModels.toArray(new ProjectDetailModel[projects.detailModels.size()])));

                }
                if(other.detailModels==null||other.detailModels1==null||other.detailModels2==null||other.detailModels3==null){

                }else{
                    book.setOthersModelsskills(new RealmList<OthersModel>(other.detailModels.toArray(new OthersModel[other.detailModels.size()])));
                    book.setOthersModelsache(new RealmList<OthersModel>(other.detailModels1.toArray(new OthersModel[other.detailModels1.size()])));
                    book.setOthersModelshobbys(new RealmList<OthersModel>(other.detailModels2.toArray(new OthersModel[other.detailModels2.size()])));
                    book.setOthersModelslan(new RealmList<OthersModel>(other.detailModels3.toArray(new OthersModel[other.detailModels3.size()])));
                }
                if(references.referencesModels==null){

                }else{
                    book.setReferencesModels(new RealmList<ReferencesModel>(references.referencesModels.toArray(new ReferencesModel[references.referencesModels.size()])));
                }
                if(declaration.textInputLayout==null){

                }else {
                    book.setDeclaration(declaration.textInputLayout.getEditText().getText().toString());
                }

                Bitmap bitmap = ((BitmapDrawable)information.uploadphoto.getDrawable()).getBitmap();
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bStream);
                byte[] mByteArray = bStream.toByteArray();
                book.setPersonpic(mByteArray);

                Bitmap bitmap1 = ((BitmapDrawable)information.uploadsign.getDrawable()).getBitmap();
                ByteArrayOutputStream bStream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bStream);
                byte[] mByteArray1 = bStream1.toByteArray();
                book.setSignaturepic(mByteArray1);
                Realm realm = RealmController.with(this).getRealm();
                if(ContactInformation.profile.getEditText().getText().equals("")){
                    AlertDailogManager dailogManager = new AlertDailogManager();
                    dailogManager.showAlertDialog(this,"Enter Profile Name",false);
                }else{
                    realm.beginTransaction();
                    realm.copyToRealm(book);
                    realm.commitTransaction();
                    Toast.makeText(ScrollableTabsActivity.this,"Data Saved",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                    startActivity(intent);
                    this.finish();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else{
           RealmController controller = new RealmController(this.getApplication());
            SaveDataModel saveDataModels = controller.getBook(id);
            realm = RealmController.with(this).getRealm();
            realm.beginTransaction();
            saveDataModels.removeFromRealm();
            realm.commitTransaction();
            try{
                SaveDataModel book = new SaveDataModel();
                long i= 1 + System.currentTimeMillis();//L is the suffix for long
                String id =String.valueOf(i);//Now it will return "9993939399"
                book.setId(id);
                book.setProfilename(information.profile.getEditText().getText().toString());
                book.setName(information.name.getEditText().getText().toString());
                book.setEmail(information.email.getEditText().getText().toString());
                book.setMobile(information.mobile.getEditText().getText().toString());
                book.setDateofbirth(information.dateofbirth.getEditText().getText().toString());
                book.setGender(information.gender.getEditText().getText().toString());
                book.setMarriagestatus(information.maritialstatus.getEditText().getText().toString());
                book.setAddress(information.address.getEditText().getText().toString());
                book.setCity(information.city.getEditText().getText().toString());
                book.setState(information.state.getEditText().getText().toString());
                book.setCountry(information.country.getEditText().getText().toString());
                book.setPincode(information.pincode.getEditText().getText().toString());
                book.setCountry(information.country.getEditText().getText().toString());
                if(careerObjective.textInputLayout==null){

                }else{
                    book.setCareerobjective(careerObjective.textInputLayout.getEditText().getText().toString());

                }
                if(qualification.models==null){

                }else{
                    book.setEducationModels(new RealmList<EducationModel>(qualification.models.toArray(new EducationModel[qualification.models.size()])));
                }
                if(experience.experienceModels==null){

                }else{
                    book.setExperienceModels(new RealmList<WorkExperienceModel>(experience.experienceModels.toArray(new WorkExperienceModel[experience.experienceModels.size()])));

                }
                if(projects.detailModels==null){

                }else{
                    book.setProjectDetailModels(new RealmList<ProjectDetailModel>(projects.detailModels.toArray(new ProjectDetailModel[projects.detailModels.size()])));

                }
                if(other.detailModels==null||other.detailModels1==null||other.detailModels2==null||other.detailModels3==null){

                }else{
                    book.setOthersModelsskills(new RealmList<OthersModel>(other.detailModels.toArray(new OthersModel[other.detailModels.size()])));
                    book.setOthersModelsache(new RealmList<OthersModel>(other.detailModels1.toArray(new OthersModel[other.detailModels1.size()])));
                    book.setOthersModelshobbys(new RealmList<OthersModel>(other.detailModels2.toArray(new OthersModel[other.detailModels2.size()])));
                    book.setOthersModelslan(new RealmList<OthersModel>(other.detailModels3.toArray(new OthersModel[other.detailModels3.size()])));
                }
                if(references.referencesModels==null){

                }else{
                    book.setReferencesModels(new RealmList<ReferencesModel>(references.referencesModels.toArray(new ReferencesModel[references.referencesModels.size()])));
                }
                if(declaration.textInputLayout==null){

                }else {
                    book.setDeclaration(declaration.textInputLayout.getEditText().getText().toString());
                }

                Bitmap bitmap = ((BitmapDrawable)information.uploadphoto.getDrawable()).getBitmap();
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bStream);
                byte[] mByteArray = bStream.toByteArray();
                book.setPersonpic(mByteArray);

                Bitmap bitmap1 = ((BitmapDrawable)information.uploadsign.getDrawable()).getBitmap();
                ByteArrayOutputStream bStream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bStream);
                byte[] mByteArray1 = bStream1.toByteArray();
                book.setSignaturepic(mByteArray1);
                if(ContactInformation.profile.getEditText().getText().equals("")){
                    AlertDailogManager dailogManager = new AlertDailogManager();
                    dailogManager.showAlertDialog(this,"Enter Profile Name",false);
                }else{
                    realm.beginTransaction();
                    realm.copyToRealm(book);
                    realm.commitTransaction();
                    Toast.makeText(ScrollableTabsActivity.this,"Data Saved",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                    startActivity(intent);
                    this.finish();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle)
                .setMessage("Do you want to Save Data or else you loose your data what you have entered !")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        saveData();
                        Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                        startActivity(intent);
                        finish();
                        //close();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface dialog, int arg1) {
                    finish();                    }
                })
                .show();

    }
}
