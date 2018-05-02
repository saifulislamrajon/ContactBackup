package com.example.saiful.contactbackup;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentSendSms extends Fragment {
    final int REQUEST_MESSAGE_SEND = 1;
    EditText etNumber;
    EditText etText;
    Button btnSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_send_sms, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etNumber = getActivity().findViewById(R.id.etNumber);
        etText = getActivity().findViewById(R.id.etText);
        btnSend = getActivity().findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number= Integer.parseInt(etNumber.getText().toString());
                String message=etText.getText().toString();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+number));
                intent.putExtra("sms_body",message);
                startActivity(intent);
            }
        });

    }


    /*public void send(View view) {
        *//*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_SMS}, REQUEST_MESSAGE_SEND);
            } else {
                String number = etNumber.getText().toString().trim();
                String text = etText.getText().toString().trim();
                if (number == null || number.equals("") || text == null || text.equals("")) {
                    Toast.makeText(getActivity(), "Field Cant be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isDigitsOnly(number)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, text, null, null);
                        Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please Enter Integer Only", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }*//*
        *//*int number= Integer.parseInt(etNumber.getText().toString());
        String message=etText.getText().toString();
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+number));
        intent.putExtra("sms_body",message);
        startActivity(intent);*//*
    }*/
}
