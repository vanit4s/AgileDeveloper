package com.dev.sendit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 9637;
    List<AuthUI.IdpConfig> providers;

    Button btn_fb;
    Button btn_google;
    Button btn_email;

    public static SharedPreferences shared;
    private static final String PROFILE = "profileprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(0, 0);

        //[VIEW CONTROLS]
        btn_fb = findViewById(R.id.btn_fb);
        btn_google = findViewById(R.id.btn_google);
        btn_email = findViewById(R.id.btn_email);

        //Variable used for sharing profile data (name, email, facebook profile picture)
        shared = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_browse:
                        Toast.makeText(MainActivity.this, "Browse", Toast.LENGTH_SHORT).show();
                        Intent browse = new Intent(MainActivity.this, BrowseActivity.class);
                        startActivity(browse);
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(MainActivity.this, "Messages", Toast.LENGTH_SHORT).show();
                        Intent messages = new Intent(MainActivity.this, MessagesActivity.class);
                        startActivity(messages);
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        Intent account = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(account);
                        break;
                }
                return true;
            }
        });


        //[SESSIONS]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            for (UserInfo userInfo : user.getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    //Toast.makeText(this, "Welcome back, " + user.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            AuthUI.IdpConfig facebookIdpConfig = new AuthUI.IdpConfig.FacebookBuilder()
                    .setPermissions(Arrays.asList("email","public_profile"))
                    .build();

            //Initialise the providers
            providers = Arrays.asList(
                    facebookIdpConfig, //Facebook
                    new AuthUI.IdpConfig.GoogleBuilder().build(), //Google
                    new AuthUI.IdpConfig.EmailBuilder().build() //Email
                    //new AuthUI.IdpConfig.PhoneBuilder().build() //Phone
            );

            showSignInOptions();
        }
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            loadUserProfile(currentAccessToken);
            if (currentAccessToken == null) {
                //LOGGED OUT
            } else {
                //LOGGED IN
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        final GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if (object != null) {
                        String id = object.getString("id");
                        String first_name = object.getString("first_name");
                        String last_name = object.getString("last_name");
                        String name = first_name + " " + last_name;
                        String email = object.getString("email");
                        //String gender = object.getString("gender");
                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                        //gender = gender.substring(0,1).toUpperCase() + gender.substring(1);

                        //[DATABASE]
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Name");
                        database.setValue(name);
                        database = FirebaseDatabase.getInstance().getReference("Users").child(id).child("First_Name");
                        database.setValue(first_name);
                        database = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Last_Name");
                        database.setValue(last_name);
                        /*database = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Gender");
                        database.setValue(gender);*/
                        database = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Email");
                        database.setValue(email);

                        Toast.makeText(MainActivity.this, "DATABASE: " + email + "!", Toast.LENGTH_SHORT).show();

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.dontAnimate();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle para = new Bundle();
        para.putString("fields","first_name,last_name,email,id");
        request.setParameters(para);
        request.executeAsync();
    }
}
