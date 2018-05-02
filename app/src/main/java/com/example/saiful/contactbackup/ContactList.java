package com.example.saiful.contactbackup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ContactList extends ArrayAdapter<Contact> {
    private Activity context;
    private List<Contact> contactList;

    public ContactList(Activity context, List<Contact> contactList) {
        super(context, R.layout.layout_contact_list, contactList);
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_contact_list, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewMobile = listViewItem.findViewById(R.id.textViewMobile);
        TextView textViewDesk = listViewItem.findViewById(R.id.textViewDesk);
        TextView textViewEmail = listViewItem.findViewById(R.id.textViewEmail);

        Contact contact=contactList.get(position);
        textViewName.setText(contact.getTitle());
        textViewMobile.setText(contact.getMobile());
        textViewDesk.setText(contact.getDesk());
        textViewEmail.setText(contact.getEmail());
        return listViewItem;
    }
}
