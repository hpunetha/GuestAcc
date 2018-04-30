package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class Admin_HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int mBackCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction().add(R.id.adminHomeFrame, new Admin_HomeFragment()).commit();
        //getFragmentManager().beginTransaction().replace(R.id.adminHomeFrame,new Admin_HomeFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
// else {
//            super.onBackPressed();
//        }

        mBackCount++;

        if (mBackCount == 1) {
            Toast.makeText(this, "Press again to sign-out", Toast.LENGTH_SHORT).show();


        } else if (mBackCount > 1) {
            FirebaseAuth.getInstance().signOut();
            Intent mSignOut = new Intent(Admin_HomeActivity.this, MainActivity.class);
            mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mSignOut);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.adminHome) {
            getFragmentManager().beginTransaction().replace(R.id.adminHomeFrame, new Admin_HomeFragment()).commit();
        } else if (id == R.id.adminRoomStatus) {
            getFragmentManager().beginTransaction().replace(R.id.adminHomeFrame, new Admin_RoomStatus_Fragment()).commit();
        } else if (id == R.id.adminCancelBooking) {
            Intent adminCancelBookingIntent = new Intent(Admin_HomeActivity.this,Admin_CancelBookings.class);
            startActivity(adminCancelBookingIntent);

        } else if (id == R.id.adminAddRoom) {

        } else if (id == R.id.adminAddAdmin) {

        } else if (id == R.id.adminLogout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
