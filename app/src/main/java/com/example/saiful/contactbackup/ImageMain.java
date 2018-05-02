package com.example.saiful.contactbackup;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import com.bumptech.glide.Glide;

public class ImageMain extends AppCompatActivity {
    final int REQUEST_IMAGE_CALL = 1;
    GridView gridView;
    private ArrayList<File> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_main);

        Toolbar toolbar = findViewById(R.id.toolbar_imagemain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Image Page");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ImageMain.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ImageMain.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_CALL);
            } else {
//                list = imageReader(Environment.getExternalStorageDirectory());
                list = imageReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
            }
        } else {
//            list = imageReader(Environment.getExternalStorageDirectory());
            list = imageReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));

        }

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ImageMain.this, ViewImages.class).putExtra("img", list.get(i).toString()));
            }
        });

    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.grid_layout, viewGroup, false);
            ImageView imageView = view.findViewById(R.id.imageView2);
            imageView.setImageURI(Uri.parse(getItem(i).toString()));

            return view;
        }
    }

    ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            } else {
                if (files[i].getName().endsWith(".jpg")) {
                    a.add(files[i]);
                }
            }
        }
        return a;
    }
}