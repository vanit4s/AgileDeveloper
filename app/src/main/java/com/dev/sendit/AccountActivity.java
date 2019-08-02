package com.dev.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev.sendit.Adapters.RatingsAdapter;
import com.dev.sendit.Models.RatingModel;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 9637;
    List<AuthUI.IdpConfig> providers;

    String name, firstName, email, url;
    TextView userName, userFirstName, userEmail, userLocation;
    CircleImageView userImage, userIcon;

    ImageView imgSettings, imgLogout;

    ListView listRatings;
    RatingsAdapter adapter;
    String[] ratingName;
    int[] rating;
    String[] ratingContent;
    int[] icon;
    ArrayList<RatingModel> arrayList = new ArrayList<RatingModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name = MainActivity.shared.getString("NAME", null);
        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        url = MainActivity.shared.getString("URL", null);

        userName = findViewById(R.id.profileName);
        userFirstName = findViewById(R.id.messageUser);
        userEmail = findViewById(R.id.profileEmail);
        userLocation = findViewById(R.id.profileLocation);
        userImage = findViewById(R.id.profileImage);
        userIcon = findViewById(R.id.profileIcon);

        imgSettings = findViewById(R.id.imgSettings);
        imgLogout = findViewById(R.id.imgLogout);

        userName.setText(name);
        userFirstName.setText(firstName);
        userEmail.setText(email);
        userLocation.setText("Update your location!");

        Glide.with(getApplicationContext()).load(url).into(userImage);
        Glide.with(getApplicationContext()).load(url).into(userIcon);

        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(AccountActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AccountActivity.this, "You have logged out!", Toast.LENGTH_SHORT).show();

                                LoginManager.getInstance().logOut();

                                AuthUI.IdpConfig facebookIdpConfig = new AuthUI.IdpConfig.FacebookBuilder()
                                        .setPermissions(Arrays.asList("email","public_profile"))
                                        .build();

                                //Initialise the providers
                                providers = Arrays.asList(
                                        //new AuthUI.IdpConfig.EmailBuilder().build(), //Email
                                        facebookIdpConfig, //Facebook
                                        //new AuthUI.IdpConfig.PhoneBuilder().build() //Phone
                                        new AuthUI.IdpConfig.GoogleBuilder().build() //Google
                                );

                                showSignInOptions();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //[DATABASE DATA PULLING]
        ratingName = new String[]{"John","Michelle","Anna","Michael"};
        rating = new int[]{5, 4, 3, 4};
        ratingContent = new String[]{"Great job, couldn't ask for anyone better","Szymon did amazing, would recommend","Slightly late, but happy with the job", "Would recommend!"};
        icon = new int[]{R.drawable.com_facebook_profile_picture_blank_square,
                R.drawable.com_facebook_profile_picture_blank_square,
                R.drawable.com_facebook_profile_picture_blank_square,
                R.drawable.com_facebook_profile_picture_blank_square};

        listRatings = findViewById(R.id.listRatings);

        for (int i = 0; i < ratingName.length; i++) {
            RatingModel model = new RatingModel(ratingName[i], rating[i], ratingContent[i], icon[i]);
            arrayList.add(model);
        }

        adapter = new RatingsAdapter(this, arrayList);
        listRatings.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(AccountActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(home);
                        //finish();
                        break;
                    case R.id.navigation_browse:
                        Toast.makeText(AccountActivity.this, "Browse", Toast.LENGTH_SHORT).show();
                        Intent browse = new Intent(AccountActivity.this, BrowseActivity.class);
                        startActivity(browse);
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(AccountActivity.this, "Messages", Toast.LENGTH_SHORT).show();
                        Intent messages = new Intent(AccountActivity.this, MessagesActivity.class);
                        startActivity(messages);
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(AccountActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        overridePendingTransition(0, 0);
    }

    public void showSignInOptions() {
        AuthMethodPickerLayout custom = new AuthMethodPickerLayout
                .Builder(R.layout.activity_login)
                .setFacebookButtonId(R.id.btn_fb)
                .setGoogleButtonId(R.id.btn_google)
                .setEmailButtonId(R.id.btn_email)
                .build();

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MainTheme)
                        .setTosAndPrivacyPolicyUrls(
                                "test",
                                "test")
                        .setAuthMethodPickerLayout(custom)
                        .build(), REQUEST_CODE
        );

        Intent home = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(home);
    }
}