package com.example.saiful.contactbackup;

import android.*;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FragmentInbox extends Fragment {
    final int REQUEST_MESSAGE_INBOX = 1;
    ListView listView;
    ArrayList<String> smsList;
    List<Sms> smsList2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getActivity().findViewById(R.id.listView3);
        smsList2 = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_SMS}, REQUEST_MESSAGE_INBOX);
            } else {
                showSms();
            }
        }
    }

    public void showSms() {
        Uri inboxURI = Uri.parse("content://sms/inbox");
        smsList = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor c = cr.query(inboxURI, null, null, null, null);
        while (c.moveToNext()) {
            String Number = c.getString(c.getColumnIndexOrThrow("address")).toString();
            String Body = c.getString(c.getColumnIndexOrThrow("body")).toString();
//            smsList.add("Number: " + Number + "\n" + "Body: " + Body);
            Sms sms = new Sms(Number, Body);
            smsList2.add(sms);
        }
        c.close();
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, smsList);
        listView.setAdapter(adapter);*/
        SmsList adapter = new SmsList(getActivity(), smsList2);
        listView.setAdapter(adapter);
    }
}
