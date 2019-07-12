package com.dev.sendit;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;

public class AccountActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 9637;
    List<AuthUI.IdpConfig> providers;

    String name, email, gender, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name = MainActivity.shared.getString("NAME", null);
        email = MainActivity.shared.getString("EMAIL", null);
        gender = MainActivity.shared.getString("GENDER", null);
        url = MainActivity.shared.getString("URL", null);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}