package com.example.saiful.contactbackup;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewImages extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        imageView=findViewById(R.id.imageView3);

        String images=getIntent().getStringExtra("img");
        imageView.setImageURI(Uri.parse(images));
    }
}
