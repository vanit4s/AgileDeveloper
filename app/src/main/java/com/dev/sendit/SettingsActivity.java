package com.dev.sendit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev.sendit.Models.ListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity  extends AppCompatActivity {

    String facebook_id, name, firstName, email, url;

    TextView userFirstName, userName, userEmail, txtNewLocation;
    CircleImageView userImage, userIcon;

    Button btnSubmit;

    ImageView imgBack, imgLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        facebook_id = MainActivity.shared.getString("ID", null);
        name = MainActivity.shared.getString("NAME", null);
        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        url = MainActivity.shared.getString("URL", null);

        userName = findViewById(R.id.profileName);
        userFirstName = findViewById(R.id.messageUser);
        userEmail = findViewById(R.id.profileEmail);
        userIcon = findViewById(R.id.profileIcon);
        userImage = findViewById(R.id.profileImage);
        txtNewLocation = findViewById(R.id.txtNewLocation);
        imgLocation = findViewById(R.id.imgLocation);
        btnSubmit = findViewById(R.id.btnSubmit);

        imgBack = findViewById(R.id.imgBack);

        userName.setText(name);
        userFirstName.setText(firstName);
        userEmail.setText(email);

        Glide.with(getApplicationContext()).load(url).into(userImage);
        Glide.with(getApplicationContext()).load(url).into(userIcon);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                } else {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    try {
                        String city = getLocation(location.getLatitude(), location.getLongitude());

                        txtNewLocation.setText(city);
                        txtNewLocation.setEnabled(false);
                    } catch(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SettingsActivity.this, "Location service unable to locate device!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtNewLocation.toString())) {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ListModel listing = snapshot.getValue(ListModel.class);

                                if (listing.getAuthor().equals(facebook_id)) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("Location", txtNewLocation.getText().toString());

                                    ref.child(snapshot.getKey()).updateChildren(hashMap);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    Toast.makeText(SettingsActivity.this, "Successfully edited the profile!", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(SettingsActivity.this, "You must specify the location!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    try {
                        String city = getLocation(location.getLatitude(), location.getLongitude());

                        txtNewLocation.setText(city);
                    } catch(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SettingsActivity.this, "Location service unable to locate device!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Location service unable to locate device!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String getLocation(double lat, double lon) {
        String city = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);

            if (addresses.size() > 0) {
                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        city = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return city;
    }
}
