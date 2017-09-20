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


public class CareerObjective extends Fragment implements View.OnClickListener{
    public FloatingActionButton actionButton;
    public static TextInputLayout textInputLayout =null;
    public CareerObjective() {
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
        View itemView = inflater.inflate(R.layout.fragment_career_objjective, container, false);
        actionButton = (FloatingActionButton)itemView.findViewById(R.id.adddcareerobjective);
        actionButton.setOnClickListener(this);
        textInputLayout = (TextInputLayout)itemView.findViewById(R.id.careerobjective);
        String itemId = ScrollableTabsActivity.id;
        if(itemId==null){

        }else{
            RealmController controller = new RealmController(getActivity().getApplication());
            SaveDataModel saveDataModels = controller.getBook(itemId);
            textInputLayout.getEditText().setText(saveDataModels.getCareerobjective().toString());
        }
        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adddcareerobjective:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());

                builderSingle.setTitle("Choose..");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item);
                arrayAdapter.add("To enhance my working capacities professional skills business effiencies and to serve my organization in best possible way with sheer determination and commitment."+"\n");
                arrayAdapter.add("To make contribution to the organization to the best of my ability and to develop new skills and share my knowledge while interacting with others and achieve new hight.."+"\n");
                arrayAdapter.add("To reach the highest echelons in an organization with hardwork, dedication constant endavor to perform better and give results."+"\n");
                arrayAdapter.add("To be a professional and to utilize my skill and knowledge to fullfill the requirment of the organization in customer service."+"\n");
                arrayAdapter.add("As I am the fresher  in this field, I will know about the nature of my work. Moreover, I will attempt to know about the various processes which form my job. By doing so, I will be able to do my job more proficiently. On the other hand, I shall implement my knowledge into the practical world.."+"\n");
                arrayAdapter.add("To obtain an entry-level position with in an organization that offers security and professional growth which requires strong analytical and technical skills."+"\n");
                arrayAdapter.add("To Create value and recognition on work place by producing the best result for the organization through synchronize and hordwork."+"\n");
                arrayAdapter.add("To work in tandem with a team in    a challenging and competative environment where i would improve my knowledge, capabilities and put them to use for the development of the organization."+"\n");
                arrayAdapter.add("To be a part of organization that provides an atmosphere of mutual growth and benefites, where i can show my talent and potential."+"\n");
                arrayAdapter.add("To serve  organization as a hardworker in this competitive environment discharging all my professional skills."+"\n");
                arrayAdapter.add("To work hard with full dedication for the achievement of organization objective under satisfying job.Contact , hence enhancing my skill and knowledge and ready to learn new things."+"\n");
                arrayAdapter.add("To be involved in work where I can utilize skill and creatively involved with system That effectively contributes to the growth of organization"+"\n");
                arrayAdapter.add("To pursue a highly rewarding career, seeking for a job in challenging and healthy work environment where I can utilize my skills and knowledge efficiently for organizational growth."+"\n");
                arrayAdapter.add("To be an astute learner and the best performer in your organization. So that I can build an innovative career in your esteemed organization by using my skills and other significant talents"+"\n");
                arrayAdapter.add("To excel in my field through hard work, research, skills and perseverance. To serve my parents, and my country with the best of my abilities"+"\n");
                arrayAdapter.add("I will always try to use my skills like honesty, devotion towards my job, punctuality etc. I will discuss my ideology with my superiors."+"\n");
                arrayAdapter.add("To work in a progressive organization which can expand all my knowledge and provided me exciting opportunities to utilize my skills and qualification to produce result fidelity."+"\n");
                arrayAdapter.add("To work to my optimum level for the betterment of the company/organization and to make a mark as a distinguished professional in an organization."+"\n");
                arrayAdapter.add("To work in rapidly growing organization with a dynamic environment and achieve organizational goal with my best efforts."+"\n");
                arrayAdapter.add("To render my sincere effects in to your esteemed organization this can develop and brush up my knowledge."+"\n");

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
