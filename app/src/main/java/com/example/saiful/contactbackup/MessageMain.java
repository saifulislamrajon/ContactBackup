package com.example.saiful.contactbackup;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class MessageMain extends AppCompatActivity {
    FragmentManager fragmentManager;
    FloatingActionButton sendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messag_main);
        Toolbar toolbar = findViewById(R.id.toolbar_messagemain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Message Page");
        fragmentManager = getFragmentManager();

        sendSMS=findViewById(R.id.sendSMS);
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MessageMain.this,"it's floatingactionbutton",Toast.LENGTH_SHORT).show();
                FragmentSendSms fragmentSendSms=new FragmentSendSms();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.group,fragmentSendSms,"C");
                transaction.commit();


            }
        });
    }

    public void inbox(View view) {
        FragmentInbox inbox = new FragmentInbox();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.group, inbox, "A");
//        transaction.addToBackStack("replaceWithA");
        transaction.commit();
    }

    public void sent(View view) {
        FragmentSent sent = new FragmentSent();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.group, sent, "B");
//        transaction.addToBackStack("replaceWithB");
        transaction.commit();
    }
}
