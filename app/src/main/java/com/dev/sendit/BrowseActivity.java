package com.dev.sendit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dev.sendit.Adapters.ListViewAdapter;
import com.dev.sendit.Models.ChatModel;
import com.dev.sendit.Models.ListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    List<ListModel> listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        listView = findViewById(R.id.listView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffbd80")));
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Browse Listings</font>"));

        //[DATABASE DATA PULLING]
        readListings();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_browse);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(BrowseActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(BrowseActivity.this, MainActivity.class);
                        startActivity(home);
                        //finish();
                        break;
                    case R.id.navigation_browse:
                        Toast.makeText(BrowseActivity.this, "Browse", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(BrowseActivity.this, "Messages", Toast.LENGTH_SHORT).show();
                        Intent messages = new Intent(BrowseActivity.this, MessagesActivity.class);
                        startActivity(messages);
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(BrowseActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        Intent account = new Intent(BrowseActivity.this, AccountActivity.class);
                        startActivity(account);
                        break;
                }
                return true;
            }
        });
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browse_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readListings() {
        listings = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Listings");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listings.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListModel listing = snapshot.getValue(ListModel.class);

                    listings.add(listing);
                }

                adapter = new ListViewAdapter(BrowseActivity.this, listings, 0);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
