package com.indianservers.buildresume;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.Window;
import android.widget.Toast;

import com.indianservers.buildresume.fragments.CareerObjective;
import com.indianservers.buildresume.fragments.ContactInformation;
import com.indianservers.buildresume.fragments.Declaration;
import com.indianservers.buildresume.fragments.EducationQualification;
import com.indianservers.buildresume.fragments.Other;
import com.indianservers.buildresume.fragments.Projects;
import com.indianservers.buildresume.fragments.References;
import com.indianservers.buildresume.fragments.WorkExperience;
import com.indianservers.buildresume.model.EducationModel;
import com.indianservers.buildresume.model.OthersModel;
import com.indianservers.buildresume.model.ProjectDetailModel;
import com.indianservers.buildresume.model.ReferencesModel;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.model.WorkExperienceModel;
import com.indianservers.buildresume.realm.RealmController;
import com.indianservers.buildresume.service.AlertDailogManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class ScrollableTabsActivity extends AppCompatActivity {
    public static String itemid = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Realm realm;
    ContactInformation information = new ContactInformation();
    EducationQualification qualification = new EducationQualification();
    WorkExperience experience = new WorkExperience();
    Projects projects = new Projects();
    Other other = new Other();
    References references = new References();
    CareerObjective careerObjective = new CareerObjective();
    Declaration declaration = new Declaration();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scrollable_tabs);
        this.realm = RealmController.with(this).getRealm();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        itemid = intent.getStringExtra("itemid");

        if(itemid==null){
            getSupportActionBar().setTitle("Create Profile");
        }else{
            getSupportActionBar().setTitle("Update Profile");
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


        if(itemid ==null){
            String semailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String semail = information.email.getEditText().getText().toString();
            String mobile = information.mobile.getEditText().getText().toString();
            String mobileval = "^[789]\\d{9}$";
                try{
                    SaveDataModel book = new SaveDataModel();
                    book.setId((int) (1+System.currentTimeMillis()));
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
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bStream1);
                    byte[] mByteArray1 = bStream1.toByteArray();
                    book.setSignaturepic(mByteArray1);
                        realm.beginTransaction();
                        if(information.profile.getEditText().getText().toString().equals("")){
                            AlertDailogManager dailogManager = new AlertDailogManager();
                            dailogManager.showAlertDialog(this,"Enter Profile",false);
                        }
                        else if(!semail.matches(semailPattern)||semail.equals("")){
                        AlertDailogManager dailogManager = new AlertDailogManager();
                        dailogManager.showAlertDialog(ScrollableTabsActivity.this,"Enter Correct Email Address",false);
                    }

                    else if(!mobile.matches(mobileval)||mobile.equals("")){
                        AlertDailogManager dailogManager = new AlertDailogManager();
                        dailogManager.showAlertDialog(ScrollableTabsActivity.this,"Enter Correct Mobile Number",false);
                    }
                    else{
                            realm.copyToRealm(book);
                            Toast.makeText(ScrollableTabsActivity.this,"Data Saved",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.remove("EduList");
                            editor.remove("ExpList");
                            editor.remove("RefList");
                            editor.remove("PrjList");
                            editor.remove("Lanlist");
                            editor.remove("Hobbyslist");
                            editor.remove("Achlist");
                            editor.remove("Skillslist");
                            editor.remove("imagePreferanc");
                            editor.remove("imagePreferance");
                            editor.commit();
                            startActivity(intent);
                            this.finish();
                            realm.commitTransaction();
                        }

                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
        }else{
            realm.beginTransaction();
            SaveDataModel book1 = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemid)).findFirst();
            book1.removeFromRealm();
            SaveDataModel book = new SaveDataModel();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String email = information.email.getEditText().getText().toString();
            if(email.matches(emailPattern)){
                try{
                    book.setId((int) (RealmController.getInstance().getBooks().size() + System.currentTimeMillis()));
                    book.setProfilename(information.profile.getEditText().getText().toString());
                    book.setName(information.name.getEditText().getText().toString());
                    book.setEmail(email);
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
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                if(careerObjective.textInputLayout==null){

                }else{

                    book.setCareerobjective(careerObjective.textInputLayout.getEditText().getText().toString());

                }
                if(qualification.models1==null){

                }else{
                    RealmList<EducationModel> realmListFriends = new RealmList<EducationModel>(qualification.models1.toArray(new EducationModel[qualification.models1.size()]));
                    book.setEducationModels(realmListFriends);
                }
                if(experience.wemodels==null){

                }else{
                    book.setExperienceModels(new RealmList<WorkExperienceModel>(experience.wemodels.toArray(new WorkExperienceModel[experience.wemodels.size()])));

                }
                if(projects.pdmodels==null){

                }else{
                    book.setProjectDetailModels(new RealmList<ProjectDetailModel>(projects.pdmodels.toArray(new ProjectDetailModel[projects.pdmodels.size()])));

                }
                if(other.detailModelsup==null||other.detailModels1up==null||other.detailModels2up==null||other.detailModels3up==null){

                }else{
                    book.setOthersModelsskills(new RealmList<OthersModel>(other.detailModelsup.toArray(new OthersModel[other.detailModelsup.size()])));
                    book.setOthersModelsache(new RealmList<OthersModel>(other.detailModels1up.toArray(new OthersModel[other.detailModels1up.size()])));
                    book.setOthersModelshobbys(new RealmList<OthersModel>(other.detailModels2up.toArray(new OthersModel[other.detailModels2up.size()])));
                    book.setOthersModelslan(new RealmList<OthersModel>(other.detailModels3up.toArray(new OthersModel[other.detailModels3up.size()])));
                }
                if(references.referencesModelsup==null){

                }else{
                    book.setReferencesModels(new RealmList<ReferencesModel>(references.referencesModelsup.toArray(new ReferencesModel[references.referencesModelsup.size()])));
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
                bitmap1.compress(Bitmap.CompressFormat.PNG, 0, bStream1);
                byte[] mByteArray1 = bStream1.toByteArray();
                book.setSignaturepic(mByteArray1);
                if(ContactInformation.profile.getEditText().getText().equals("")){
                    AlertDailogManager dailogManager = new AlertDailogManager();
                    dailogManager.showAlertDialog(this,"Enter Profile Name",false);
                }else{
                    realm.copyToRealm(book);
                    realm.commitTransaction();
                    Toast.makeText(ScrollableTabsActivity.this,"Data Saved",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.remove("EduList");
                    editor.remove("ExpList");
                    editor.remove("RefList");
                    editor.remove("PrjList");
                    editor.remove("Lanlist");
                    editor.remove("Hobbyslist");
                    editor.remove("Achlist");
                    editor.remove("Skillslist");
                    editor.remove("imagePreferanc");
                    editor.remove("imagePreferance");
                    editor.commit();
                    startActivity(intent);
                    this.finish();
                }
            }else{
                AlertDailogManager dailogManager = new AlertDailogManager();
                dailogManager.showAlertDialog(ScrollableTabsActivity.this,"Enter Correct Email Address",false);
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
                .setMessage("If you want save your data click Yes else click on No")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        saveData();
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.remove("EduList");
                        editor.remove("ExpList");
                        editor.remove("RefList");
                        editor.remove("PrjList");
                        editor.remove("Lanlist");
                        editor.remove("Hobbyslist");
                        editor.remove("Achlist");
                        editor.remove("Skillslist");
                        editor.remove("imagePreferanc");
                        editor.remove("imagePreferance");
                        editor.commit();
                        String semailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        String semail = information.email.getEditText().getText().toString();
                        if(!semail.matches(semailPattern)){
                            AlertDailogManager dailogManager = new AlertDailogManager();
                            dailogManager.showAlertDialog(ScrollableTabsActivity.this,"Empty Data Can't Be saved",false);
                        }else{
                            finish();
                        }
                        //close();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(ScrollableTabsActivity.this,ShowDataActivity.class);
                        startActivity(intent);
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.remove("EduList");
                        editor.remove("ExpList");
                        editor.remove("RefList");
                        editor.remove("PrjList");
                        editor.remove("Lanlist");
                        editor.remove("Hobbyslist");
                        editor.remove("Achlist");
                        editor.remove("Skillslist");
                        editor.remove("imagePreferanc");
                        editor.remove("imagePreferance");
                        editor.commit();
                    finish();
                    }
                })
                .show();

    }
}
