package com.dev.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListDetails extends AppCompatActivity {

    TextView listingName, userFirstName, listingLocation, listingDescription;

    String name, firstName, email, url;
    CircleImageView userIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = MainActivity.shared.getString("NAME", null);
        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        url = MainActivity.shared.getString("URL", null);

        listingName = findViewById(R.id.detailsName);
        listingLocation = findViewById(R.id.detailsLocation);
        listingDescription = findViewById(R.id.detailsDescription);
        userFirstName = findViewById(R.id.profileFirstName);
        userIcon = findViewById(R.id.profileIcon);

        userFirstName.setText(firstName);
        Glide.with(getApplicationContext()).load(url).into(userIcon);

        //Get the data from the listed item
        Intent gintent = getIntent();
        Bundle b = gintent.getExtras();

        if (b != null) {
            String title = (String) b.get("title");
            String loc = (String) b.get("location");
            String desc = (String) b.get("description");

            listingName.setText("Name"); //for now, without a database
            listingLocation.setText(loc);
            listingDescription.setText(desc);
        }

        overridePendingTransition(0, 0);
    }
}
