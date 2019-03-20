package com.example.mh1535547.anndaan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton don;
    ImageButton ben;
    private FirebaseAuth firebaseAuth;
    private SwitchCompat switcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        don=(ImageButton) findViewById(R.id.donatebutton);
        ben= (ImageButton) findViewById(R.id.procurebutton);




        firebaseAuth = FirebaseAuth.getInstance();
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(dashboard.this,donation.class);
                startActivity(intent);*/
            }
        });
        ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(dashboard.this,benefit.class);
                startActivity(intent);*/
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//switch button implementation for navigation menu in side drawer menu
        Menu menu = navigationView.getMenu();

        MenuItem menuItem = menu.findItem(R.id.notifi);

        View actionView = MenuItemCompat.getActionView(menuItem);


        switcher = (SwitchCompat) actionView.findViewById(R.id.switcher);

        switcher.setChecked(false);

        switcher.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

               Toast.makeText(getApplicationContext(),"Notification enabled",Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {

           Intent intent = new Intent(getApplicationContext(),profile.class);
        } else if (id == R.id.notifi) {
            Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_LONG).show();

        } else if (id == R.id.history) {
            Toast.makeText(getApplicationContext(), "hitory", Toast.LENGTH_LONG).show();

        } else if (id == R.id.share) {
            Toast.makeText(getApplicationContext(), "shre!!", Toast.LENGTH_LONG).show();

        } else if (id == R.id.about) {
            Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG).show();

        } else if (id == R.id.exit) {
            Toast.makeText(dashboard.this,"Logout",Toast.LENGTH_LONG).show();
               logoutuser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //logout
    private void logoutuser(){

        firebaseAuth.signOut();

        finish();

        Intent intent = new Intent(dashboard.this,loginpage.class);
        startActivity(intent);
    }
}
