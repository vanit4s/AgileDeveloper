package com.dev.sendit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity  extends AppCompatActivity {

    String name, firstName, email, url;

    TextView userFirstName, userName, userEmail;
    CircleImageView userImage, userIcon;

    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = MainActivity.shared.getString("NAME", null);
        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        url = MainActivity.shared.getString("URL", null);

        userName = findViewById(R.id.profileName);
        userFirstName = findViewById(R.id.messageUser);
        userEmail = findViewById(R.id.profileEmail);
        userIcon = findViewById(R.id.profileIcon);
        userImage = findViewById(R.id.profileImage);

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

        overridePendingTransition(0, 0);
    }
}
