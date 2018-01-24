package com.indianservers.buildresume;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.indianservers.buildresume.adapters.BooksAdapter;
import com.indianservers.buildresume.adapters.RealmBooksAdapter;
import com.indianservers.buildresume.app.Prefs;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.realm.RealmController;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowDataActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private BooksAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profiles");
        recyclerView = (RecyclerView)findViewById(R.id.showrecycler);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getBooks());
    }

    private void setRealmData() {
        SaveDataModel b = new SaveDataModel();
       if(b.getName()==null){

       }else{
           realm.beginTransaction();
           realm.copyToRealm(b);
           realm.commitTransaction();
       }
    }

    public void setRealmAdapter(RealmResults<SaveDataModel> books) {

        RealmBooksAdapter realmAdapter = new RealmBooksAdapter(books);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new BooksAdapter(this);
        recyclerView.setAdapter(adapter);
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
