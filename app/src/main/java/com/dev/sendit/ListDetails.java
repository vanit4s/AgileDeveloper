package com.dev.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListDetails extends AppCompatActivity {

    TextView listingName, userFirstName, listingLocation, listingDescription, currentBid;

    String name, firstName, email, url, receiverid;
    CircleImageView userIcon, detailsImage;
    ImageView imgBack, imgContact;

    Button btnSendRequest;

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
        userFirstName = findViewById(R.id.messageUser);
        userIcon = findViewById(R.id.profileIcon);
        imgBack = findViewById(R.id.imgBack);
        imgContact = findViewById(R.id.imgContact);
        detailsImage = findViewById(R.id.detailsImage);
        btnSendRequest = findViewById(R.id.btnSubmit);
        currentBid = findViewById(R.id.detailCurrentBid);

        //Get the data from the listed item
        Intent gintent = getIntent();
        Bundle b = gintent.getExtras();

        final String authorid = (String) b.get("Author");

        if (b != null) {
            String title = (String) b.get("Type");
            String name = (String) b.get("Name");
            String loc = (String) b.get("Location");
            String desc = (String) b.get("Description");
            String price = (String) b.get("Price");
            String image_url = (String) b.get("Image_URL");

            listingName.setText(name); //for now, without a database
            listingLocation.setText(loc);
            listingDescription.setText(desc);
            currentBid.setText(price);
            Glide.with(getApplicationContext()).load(image_url).into(detailsImage);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDetails.this, MessageActivity.class);
                intent.putExtra("RECEIVERID", authorid);
                ListDetails.this.startActivity(intent);
            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDetails.this, MessageActivity.class);
                intent.putExtra("RECEIVERID", authorid);

                String id = MainActivity.shared.getString("ID", null);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Sender", id);
                hashMap.put("Receiver", authorid);
                hashMap.put("Request", true);

                ref.child("Requests").push().setValue(hashMap);

                ListDetails.this.startActivity(intent);
            }
        });

        userFirstName.setText(firstName);
        Glide.with(getApplicationContext()).load(url).into(userIcon);

        overridePendingTransition(0, 0);
    }
}
