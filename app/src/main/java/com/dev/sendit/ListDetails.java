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

public class ListDetails extends AppCompatActivity {

    TextView name, location, description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.detailsName);
        location = findViewById(R.id.detailsLocation);
        description = findViewById(R.id.detailsDescription);

        //Get the data from the listed item
        Intent gintent = getIntent();
        Bundle b = gintent.getExtras();

        ActionBar ab = getSupportActionBar();


        if (b != null) {
            String title = (String) b.get("title");
            String loc = (String) b.get("location");
            String desc = (String) b.get("description");

            ab.setTitle(title);
            name.setText("Name"); //for now, without a database
            location.setText(loc);
            description.setText(desc);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_browse);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_browse:
                        Toast.makeText(ListDetails.this, "Browse", Toast.LENGTH_SHORT).show();
                        Intent browse = new Intent(ListDetails.this, BrowseActivity.class);
                        startActivity(browse);
                        break;
                    case R.id.navigation_home:
                        Toast.makeText(ListDetails.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(ListDetails.this, MainActivity.class);
                        startActivity(home);
                        //finish();
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(ListDetails.this, "Messages", Toast.LENGTH_SHORT).show();
                        Intent messages = new Intent(ListDetails.this, MessagesActivity.class);
                        startActivity(messages);
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(ListDetails.this, "Account", Toast.LENGTH_SHORT).show();
                        Intent account = new Intent(ListDetails.this, AccountActivity.class);
                        startActivity(account);
                        break;
                }
                return true;
            }
        });
        overridePendingTransition(0, 0);
    }
}
