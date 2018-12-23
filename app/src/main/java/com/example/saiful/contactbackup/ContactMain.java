package com.example.saiful.contactbackup;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactMain extends AppCompatActivity {
    EditText editText;
    FloatingActionButton fabAdd;
    ListView listView2;
    DatabaseReference databaseReference2;
    List<Contact> contactList2;
    ContactList adapter2;
    public static final String CONTACT_ID = "contactid";
    public static final String CONTACT_TITLE = "contacttitle";
    public static final String CONTACT_MOBILE = "contactmobile";
    public static final String CONTACT_DESK = "contactdesk";
    public static final String CONTACT_EMAIL = "contactemail";
    //                https://stackoverflow.com/questions/10355971/how-to-filter-listview-through-edittext
    String info;
    String strphoneType = "";
    static final int PERMISSION_READ_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_main);

        Toolbar toolbar = findViewById(R.id.toolbar_contactmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Page");
//        toolbar.setTitleTextColor(Color.parseColor("#C51162"));

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyTelephonyManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }

        editText = findViewById(R.id.editTextSearch);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == keyEvent.ACTION_DOWN) && (i == keyEvent.KEYCODE_ENTER)) {
                    String name = editText.getText().toString();
                    for (Contact contact : contactList2) {
                        String title = contact.getTitle();
                        String mobile = contact.getMobile();
                        String desk = contact.getDesk();
                        String email = contact.getEmail();
                        if (title.equals(name)) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContactMain.this);
                            alertDialogBuilder.setTitle(title);
                            alertDialogBuilder.setMessage(mobile);
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

//                            Toast.makeText(getApplicationContext(),title+" "+mobile, Toast.LENGTH_LONG).show();
                            break;
                        }
                        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                    }
//                    Toast.makeText(getApplicationContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        /*editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_SHORT).show();
                ContactMain.this.adapter2.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setBackgroundColor(Color.parseColor("#C8E6C9"));
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactMain.this, AddContact.class));
            }
        });

        listView2 = findViewById(R.id.listView2);
        listView2.setTextFilterEnabled(true);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contactList2.get(i);
                Intent intent = new Intent(getApplicationContext(), ContactAction.class);
                intent.putExtra(CONTACT_ID, contact.getId()).putExtra(CONTACT_TITLE, contact.getTitle()).putExtra(CONTACT_MOBILE, contact.getMobile()).putExtra(CONTACT_DESK, contact.getDesk()).putExtra(CONTACT_EMAIL, contact.getEmail());
                startActivity(intent);
            }
        });

        databaseReference2 = FirebaseDatabase.getInstance().getReference(info);
        contactList2 = new ArrayList<>();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyTelephonyManager();
                } else {
                    Toast.makeText(ContactMain.this, "You dont have required to make the action", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList2.clear();
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    Contact contact = dataSnapshot2.getValue(Contact.class);
                    if (contact.getTitle().toString() == "saiful") {
                        Toast.makeText(ContactMain.this, "hi saiful", Toast.LENGTH_SHORT).show();
                    }
                    contactList2.add(contact);
                }
                adapter2 = new ContactList(ContactMain.this, contactList2);
                listView2.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

}
