package com.example.saiful.contactbackup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContact extends AppCompatActivity {
    private EditText etTitle, etMobile, etDesk, etEmail;
    private Button ok, reset;
    DatabaseReference databaseReference;
    List<Contact> contactList;
    ListView listView;

    String info;
    String strphoneType = "";
    static final int PERMISSION_READ_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = findViewById(R.id.toolbar_addcontact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Contact Page");

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyTelephonyManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(info);
        contactList = new ArrayList<>();
        listView = findViewById(R.id.listView);

        ok = findViewById(R.id.ok);
        reset = findViewById(R.id.reset);

        etTitle = findViewById(R.id.etTitle);
        etMobile = findViewById(R.id.etMobile);
        etDesk = findViewById(R.id.etDesk);
        etEmail = findViewById(R.id.etEmail);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyTelephonyManager();
                } else {
                    Toast.makeText(AddContact.this, "You dont have required to make the action", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void MyTelephonyManager() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = manager.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType = "NONE";
                break;
        }
        boolean isRoaming = manager.isNetworkRoaming();
        String IMEINUMBER = manager.getDeviceId();
        info = "IMEI " + IMEINUMBER;
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Contact contact = dataSnapshot1.getValue(Contact.class);
                    contactList.add(contact);
                }
//                it's listView Code
                ContactList adapter=new ContactList(AddContact.this,contactList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ok(View view) {
        String title = etTitle.getText().toString().trim();
        String mobile = etMobile.getText().toString();
        String desk = etDesk.getText().toString();
        String email = etEmail.getText().toString();
        Pattern patternEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcherEmail = patternEmail.matcher(etEmail.getText().toString());
        if(matcherEmail.matches()){
            if (!TextUtils.isEmpty(title)) {
                String id = databaseReference.push().getKey();
                Contact contact = new Contact(id, title, mobile, desk, email);
                databaseReference.child(id).setValue(contact);
                etTitle.setText("");
                etMobile.setText("");
                etDesk.setText("");
                etEmail.setText("");
                Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "you should enter the Title", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AddContact.this, "Email don't match", Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view) {
        etTitle.setText("");
        etMobile.setText("");
        etDesk.setText("");
        etEmail.setText("");
        Toast.makeText(getApplicationContext(), "Reset All Box", Toast.LENGTH_SHORT).show();
    }
}
