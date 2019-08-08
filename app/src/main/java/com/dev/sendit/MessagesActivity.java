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
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dev.sendit.Fragments.ChatsFragment;
import com.dev.sendit.Fragments.UsersFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesActivity  extends AppCompatActivity {

    private DatabaseReference database;

    CircleImageView profilePicture;
    TextView titleName;
    String firstName, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        profilePicture = findViewById(R.id.profileIcon);
        titleName = findViewById(R.id.firstname);

        firstName = MainActivity.shared.getString("FIRST_NAME", null);
        url = MainActivity.shared.getString("URL", null);

        titleName.setText(firstName);
        Glide.with(getApplicationContext()).load(url).into(profilePicture);

        Toolbar toolbar = findViewById(R.id.messagesToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

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
