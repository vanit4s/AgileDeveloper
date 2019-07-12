package com.dev.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.sendit.Models.ListModel;

import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] location;
    String[] description;
    int[] icon;
    ArrayList<ListModel> arrayList = new ArrayList<ListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        //[DATABASE DATA PULLING]
        title = new String[]{"Collection","Delivery","Delivery","Collection"};
        location = new String[]{"York","Leeds","York","Durham"};
        description = new String[]{"Furniture needs collecting ASAP","Need garden plants delivered","Looking for someone to deliver fridge","Collection of scrap needed"};
        icon = new int[]{R.drawable.com_facebook_profile_picture_blank_square,
                        R.drawable.com_facebook_profile_picture_blank_square,
                        R.drawable.com_facebook_profile_picture_blank_square,
                        R.drawable.com_facebook_profile_picture_blank_square};

        listView = findViewById(R.id.listView);

        for (int i = 0; i < title.length; i++) {
            ListModel model = new ListModel(title[i], location[i], description[i], icon[i]);
            arrayList.add(model);
        }

        adapter = new ListViewAdapter(this, arrayList);
        listView.setAdapter(adapter);

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
}
