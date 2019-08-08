package com.dev.sendit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.sendit.Adapters.ListViewAdapter;
import com.dev.sendit.Models.ListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    List<ListModel> listings;

    String facebook_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);

        listView = findViewById(R.id.listView);

        facebook_id = MainActivity.shared.getString("ID", null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffbd80")));
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Edit listings</font>"));

        readListings();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(EditListActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        //Intent home = new Intent(EditListActivity.this, MainActivity.class);
                        //startActivity(home);
                        finish();
                        break;
                    case R.id.navigation_browse:
                        Toast.makeText(EditListActivity.this, "Browse", Toast.LENGTH_SHORT).show();
                        Intent browse = new Intent(EditListActivity.this, BrowseActivity.class);
                        startActivity(browse);
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(EditListActivity.this, "Messages", Toast.LENGTH_SHORT).show();
                        Intent messages = new Intent(EditListActivity.this, MessagesActivity.class);
                        startActivity(messages);
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(EditListActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        Intent account = new Intent(EditListActivity.this, AccountActivity.class);
                        startActivity(account);
                        break;
                }
                return true;
            }
        });
        overridePendingTransition(0, 0);
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

                    if (listing.getAuthor().equals(facebook_id)) {
                        listings.add(listing);
                    }
                }

                adapter = new ListViewAdapter(EditListActivity.this, listings, 1);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
