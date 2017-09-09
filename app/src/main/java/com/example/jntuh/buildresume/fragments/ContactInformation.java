package com.example.jntuh.buildresume.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;

import java.util.Calendar;


public class ContactInformation extends Fragment{
    TextInputLayout profile,name,email,mobile,address,dateofbirth,maritialstatus,city,state,country,pincode,gender;
    public ContactInformation() {
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
        View itemView = inflater.inflate(R.layout.fragment_contact_information, container, false);
        profile = (TextInputLayout)itemView.findViewById(R.id.profile);
        name = (TextInputLayout)itemView.findViewById(R.id.username);
        email = (TextInputLayout)itemView.findViewById(R.id.email);
        mobile = (TextInputLayout)itemView.findViewById(R.id.mobile);
        address = (TextInputLayout)itemView.findViewById(R.id.address);
        dateofbirth = (TextInputLayout)itemView.findViewById(R.id.dateofbirth);
        maritialstatus = (TextInputLayout)itemView.findViewById(R.id.martialstatus);
        city = (TextInputLayout)itemView.findViewById(R.id.city);
        state = (TextInputLayout)itemView.findViewById(R.id.state);
        country = (TextInputLayout)itemView.findViewById(R.id.country);
        pincode = (TextInputLayout)itemView.findViewById(R.id.pincode);
        gender = (TextInputLayout)itemView.findViewById(R.id.gender);
        dateofbirth.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    // Show your calender here
                    Toast.makeText(getContext(),"Hii",Toast.LENGTH_LONG).show();
                    showDatePicker();
                } else {
                    // Hide your calender here
                }
            }
        });
        gender.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    final CharSequence[] genderr = {"Male","Female"};
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Select Gender");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.setSingleChoiceItems(genderr,-1, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(genderr[which]=="Male")
                            {
                                gender.getEditText().setText("Male");
                            }
                            else if (genderr[which]=="Female")
                            {
                                gender.getEditText().setText("Female");
                            }
                        }
                    });
                    alert.show();
                } else {
                    // Hide your calender here
                }

            }
        });
        maritialstatus.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    final CharSequence[] genderr = {"Married","UnMarried"};
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Select Maritial Status");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.setSingleChoiceItems(genderr,-1, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(genderr[which]=="Married")
                            {
                                maritialstatus.getEditText().setText("Married");
                            }
                            else if (genderr[which]=="UnMarried")
                            {
                                maritialstatus.getEditText().setText("UnMarried");
                            }
                        }
                    });
                    alert.show();
                } else {
                    // Hide your calender here
                }

            }
        });

        return itemView;
    }
    private void showDatePicker() {
        DatepickerFragment date = new DatepickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateofbirth.getEditText().setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };

}
