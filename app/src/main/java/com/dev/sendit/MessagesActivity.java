package com.dev.sendit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.sendit.Fragments.ChatsFragment;
import com.dev.sendit.Fragments.UsersFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesActivity  extends AppCompatActivity {

    private DatabaseReference database;

    TextView messages;
    ImageView send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        send = findViewById(R.id.imgSend);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
        viewPagerAdapter.addFragment(new UsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.getMenu().findItem(R.id.navigation_account).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.navigation_messages);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(MessagesActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(MessagesActivity.this, MainActivity.class);
                        startActivity(home);
                        //finish();
                        break;
                    case R.id.navigation_browse:
                        Toast.makeText(MessagesActivity.this, "Browse", Toast.LENGTH_SHORT).show();
                        Intent browse = new Intent(MessagesActivity.this, BrowseActivity.class);
                        startActivity(browse);
                        break;
                    case R.id.navigation_messages:
                        Toast.makeText(MessagesActivity.this, "Messages", Toast.LENGTH_SHORT).show();
                        //finish();
                        break;
                    case R.id.navigation_account:
                        Toast.makeText(MessagesActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        Intent account = new Intent(MessagesActivity.this, AccountActivity.class);
                        startActivity(account);
                        break;
                }
                return true;
            }
        });

        overridePendingTransition(0, 0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int pos) {
            return fragments.get(pos);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
