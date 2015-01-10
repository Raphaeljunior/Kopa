package com.example.KopaSmartest;

import android.os.Bundle;
import android.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by CODING_MOVAT on 8/16/2014.
 */
public class Borrow extends Fragment {
View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.loan,container,false);
        TextView phone = (TextView) rootView.findViewById(R.id.PhoneNumber);
        TextView amount = (TextView) rootView.findViewById(R.id.amount);
        TextView dead = (TextView) rootView.findViewById(R.id.deadline);
        phone.setText(getArguments().getString("phoneNo"));
        amount.setText(getArguments().getString("amount"));
        dead.setText(getArguments().getString("deadline"));
        String message= "You owe me ksh "+ amount.getText().toString()+ " payable by " + dead.getText().toString() + " . Please pay up";
       SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone.getText().toString(),null,message,null,null);
        return  rootView;
    }
}
