package com.example.saiful.contactbackup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SmsList extends ArrayAdapter<Sms> {

    private List<Sms> smsList;
    private Activity context;

    public SmsList(Activity context, List<Sms> smsList) {
        super(context, R.layout.sms_list, smsList);
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LayoutInflater inflater = context.getLayoutInflater();
        View smsViewItem = inflater.inflate(R.layout.sms_list, null, true);

        TextView number = smsViewItem.findViewById(R.id.number);
        TextView body = smsViewItem.findViewById(R.id.body);

        Sms sms = smsList.get(position);
        number.setText(sms.getNumber());
        body.setText(sms.getBody());
        return smsViewItem;

    }
}
