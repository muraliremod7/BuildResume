package com.example.jntuh.buildresume.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.realm.RealmController;


public class Declaration extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public static TextInputLayout textInputLayout = null;
    public Declaration() {
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
        View itemView = inflater.inflate(R.layout.fragment_declaration, container, false);
        actionButton = (FloatingActionButton)itemView.findViewById(R.id.adddeclaration);
        actionButton.setOnClickListener(this);
        textInputLayout = (TextInputLayout)itemView.findViewById(R.id.declaration);
        String itemId = ScrollableTabsActivity.id;
        if(itemId==null){

        }else{
            RealmController controller = new RealmController(getActivity().getApplication());
            SaveDataModel saveDataModels = controller.getBook(itemId);
            textInputLayout.getEditText().setText(saveDataModels.getDeclaration().toString());
        }
        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adddeclaration:
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());

            builderSingle.setTitle("Choose..");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item);
            arrayAdapter.add("I hereby declare that the above cited information is true to the best of my knowledge and belief if given a chance, I can prove myself."+"\n");
            arrayAdapter.add("I do hereby declare that the above given statements are true and correct to the best of my knowledge."+"\n");
            arrayAdapter.add("I hereby declare that the above written particulars are true to the best of my knowledge and belief."+"\n");
            arrayAdapter.add("I hereby declare that all the above facts are true to best of my knowledge."+"\n");
            arrayAdapter.add("I hereby certify that the above information is true and correct to the best of my knowledge and belief."+"\n");
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    textInputLayout.getEditText().setText(strName);
                }
            });
            builderSingle.show();
                break;
        }
    }
}
