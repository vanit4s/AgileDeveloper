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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddListingActivity extends AppCompatActivity {

    String facebook_id, name, firstName, email, url;

    ImageView imgBack, imgLocation;
    TextView txtLocation, txtPrice;
    Button btnSubmit;

    EditText listingDescription;

    Spinner spType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting);

        facebook_id = MainActivity.shared.getString("ID", null);
        name = MainActivity.shared.getString("NAME", null);
        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        url = MainActivity.shared.getString("URL", null);

        imgBack = findViewById(R.id.imgBack);
        imgLocation = findViewById(R.id.imgLocation);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        listingDescription = findViewById(R.id.addListingDesc);
        spType = findViewById(R.id.listingType);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(listingDescription.toString()) && TextUtils.isEmpty(txtLocation.toString()) &&
                    TextUtils.isEmpty(txtPrice.toString())) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Author", facebook_id);
                    hashMap.put("Name", firstName);
                    hashMap.put("Type", spType.getSelectedItem().toString());
                    hashMap.put("Description", listingDescription.getText().toString());
                    hashMap.put("Location", txtLocation.getText().toString());
                    hashMap.put("Price", txtLocation.getText().toString());

                    ref.child("Listings").push().setValue(hashMap);

                    Toast.makeText(AddListingActivity.this, "Successfully added a listing!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddListingActivity.this, BrowseActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddListingActivity.this, "You must specify the description, location and price!", Toast.LENGTH_SHORT).show();
                }

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

                        txtLocation.setText(city);
                        txtLocation.setEnabled(false);
                    } catch(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddListingActivity.this, "Location service unable to locate device!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                        txtLocation.setText(city);
                    } catch(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddListingActivity.this, "Location service unable to locate device!", Toast.LENGTH_SHORT).show();
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
