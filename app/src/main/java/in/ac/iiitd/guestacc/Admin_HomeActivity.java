package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class Admin_HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GoogleApiClient mGoogleAPIClient;
    int mBackCount=0;
    FirebaseUser mFirebaseUser;
    String mCurrentUserName,mCurrentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        try {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            if (mFirebaseUser !=null) {
                mCurrentUserName = mFirebaseUser.getDisplayName();
                mCurrentUserEmail = mFirebaseUser.getEmail();

            }

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

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
        else {
            super.onBackPressed();


        mBackCount++;

//        if (mBackCount == 3) {
//            Toast.makeText(this, "Press back 2 more times to sign-out", Toast.LENGTH_SHORT).show();
//        }else if (mBackCount == 4) {
//                Toast.makeText(this, "Press back 1 more time to sign-out", Toast.LENGTH_SHORT).show();
//
//        } else if (mBackCount > 4) {

//            LoginClient_Singleton mClient = LoginClient_Singleton.getInstance(null);
//            GoogleSignInClient mGSClient = mClient.getClient();
//            mGSClient.signOut()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // Signing out Gmail as well
//                        }
//                    });
//            FirebaseAuth.getInstance().signOut();
//
//            Intent mSignOut = new Intent(this, MainActivity.class);
//            mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(mSignOut);
//        }
      }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.adminHome) {
            getFragmentManager().beginTransaction().replace(R.id.adminHomeFrame, new Admin_HomeFragment()).commit();
        } else if (id == R.id.adminRoomStatus) {
            getFragmentManager().beginTransaction().replace(R.id.adminHomeFrame, new Admin_RoomStatus_Fragment()).addToBackStack("roomstatus").commit();
        } else if (id == R.id.adminCancelBooking) {
            Intent adminCancelBookingIntent = new Intent(Admin_HomeActivity.this,Admin_CancelBookings.class);
            startActivity(adminCancelBookingIntent);

        } else if (id == R.id.adminAddRoom) {

        } else if (id == R.id.adminAddAdmin) {

        } else if (id == R.id.adminLogout) {

            LoginClient_Singleton mClient = LoginClient_Singleton.getInstance(null);
            GoogleSignInClient mGSClient = mClient.getClient();
            mGSClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Signing out Gmail as well
                        }
                    });
            FirebaseAuth.getInstance().signOut();

            Intent mSignOut = new Intent(this, MainActivity.class);
            mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mSignOut);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        ImageView mAvatarMenuImageView = (ImageView) findViewById(R.id.imageViewAdmin);
        TextView mTextViewMenuName =(TextView) findViewById(R.id.nameAdmin);
        TextView mTextViewMenuEmail = (TextView) findViewById(R.id.emailAdmin);


        if (mFirebaseUser!=null)
        {
            if (mCurrentUserName!=null) { mTextViewMenuName.setText(mCurrentUserName);}
            if (mCurrentUserEmail!=null) { mTextViewMenuEmail.setText(mCurrentUserEmail);}

            Uri ur =mFirebaseUser.getPhotoUrl();

            String abc = ur.toString().replace("/s96-c/","/s300-c/");

            Picasso.with(this).load(Uri.parse(abc)).into(mAvatarMenuImageView);

        }

        getMenuInflater().inflate(R.menu.faculty_home, menu);
        return true;
    }
}
