package com.example.saiful.contactbackup;

import android.*;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactAction extends AppCompatActivity {
    final int REQUEST_PHONE_CALL = 1;
    private static final boolean TODO = true;
    private TextView textViewTitle, textViewMobile, textViewDesk, textViewEmail;
    String id, title, mobile, desk, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_action);

        Toolbar toolbar = findViewById(R.id.toolbar_contactAction);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact View");

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewMobile = findViewById(R.id.textViewMobile);
        textViewDesk = findViewById(R.id.textViewDesk);
        textViewEmail = findViewById(R.id.textViewEmail);

        Intent intent = getIntent();
        id = intent.getStringExtra(ContactMain.CONTACT_ID);
        title = intent.getStringExtra(ContactMain.CONTACT_TITLE);
        mobile = intent.getStringExtra(ContactMain.CONTACT_MOBILE);
        desk = intent.getStringExtra(ContactMain.CONTACT_DESK);
        email = intent.getStringExtra(ContactMain.CONTACT_EMAIL);

        textViewTitle.setText(title);
        textViewMobile.setText(mobile);
        textViewDesk.setText(desk);
        textViewEmail.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1234567890"));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ContactAction.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactAction.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        callIntent.setData(Uri.parse("tel:"+mobile));
                        startActivity(callIntent);
                    }
                }
                return true;
            case R.id.edit:
//                Toast.makeText(this, "it's edit", Toast.LENGTH_SHORT).show();
                showUpdateDialog(id,title);
                return true;
            case R.id.message:
                Toast.makeText(this, "it's message", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+mobile));
                intent.putExtra("sms_body","Write your message");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showUpdateDialog(final String id,String title){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);

        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        final EditText editTextTitle=dialogView.findViewById(R.id.etTitle);
        final EditText editTextMobile=dialogView.findViewById(R.id.etMobile);
        final EditText editTextDesk=dialogView.findViewById(R.id.etDesk);
        final EditText editTextEmail=dialogView.findViewById(R.id.etEmail);
        TextView textViewUpdate=dialogView.findViewById(R.id.btnUpdate);

        editTextTitle.setText(title);
        editTextMobile.setText(mobile);
        editTextDesk.setText(desk);
        editTextEmail.setText(email);

        dialogBuilder.setTitle("Updating Contact " + title);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        textViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"update",Toast.LENGTH_SHORT).show();
                String title=editTextTitle.getText().toString().trim();
                String mobile=editTextMobile.getText().toString();
                String desk=editTextDesk.getText().toString();
                String email=editTextEmail.getText().toString();
                if(TextUtils.isEmpty(title)){
                     editTextTitle.setError("Name Required");
                    return;
                }
                updateContact(id,title,mobile,desk,email);
                alertDialog.dismiss();
            }
        });

    }
    public boolean updateContact(String id,String title,String mobile,String desk,String email){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("contactBackup").child(id);
        Contact contact=new Contact(id,title,mobile,desk,email);
        databaseReference.setValue(contact);
        Toast.makeText(getApplicationContext(),"Contact Update Success",Toast.LENGTH_SHORT).show();
        return true;
    }

    public void tvCall(View view) {
        switch (view.getId()) {
            case R.id.textViewMobile:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1234567890"));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ContactAction.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactAction.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        callIntent.setData(Uri.parse("tel:"+mobile));
                        startActivity(callIntent);
                    }
                }
                break;
            case R.id.textViewDesk:
                Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                callIntent2.setData(Uri.parse("tel:1234567890"));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ContactAction.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactAction.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        callIntent2.setData(Uri.parse("tel:"+desk));
                        startActivity(callIntent2);
                    }
                }
                break;
        }
    }
}
