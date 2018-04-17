package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by kd on 15/4/18.
 */

public class AdminHomeFragment extends Fragment {

    CardView adminHomeCardView;
    TextView mTextViewBookedRooms, mTextViewAvailableRooms;
    DatabaseReference mFireBaseReference;

    //Database

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminHomeView = inflater.inflate(R.layout.fragment_admin_home,container,false);

        mTextViewBookedRooms = (TextView)adminHomeView.findViewById(R.id.adminBookedRooms);
        mTextViewAvailableRooms = (TextView)adminHomeView.findViewById(R.id.adminRoomsAvailable);

        //Thread for getting status for today - admin home screen
        new getStatusForToday().execute("");

        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView1);

        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView2);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView3);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView4);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        return adminHomeView;
    }

    @SuppressLint("StaticFieldLeak")
    private class getStatusForToday extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            mFireBaseReference = FirebaseDatabase.getInstance().getReference("bookings");
            //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            HashSet<String> bookingsRooms = new HashSet<>(Arrays.asList("bh1","bh2","gh1","gh2",
                    "fr1","fr2"));
            //Log.i("HashsetSize", String.valueOf(bookingsRooms.size()));

            //Log.i("data",date);
            mFireBaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("DataSnapshot", String.valueOf(dataSnapshot.getChildrenCount()));
                    for (DataSnapshot bookingData: dataSnapshot.getChildren()){
                        if (bookingData.getKey().equals("2018-03-17")){
                            //for (bookingData.getKey().)
                            //bookingData.getValue("rooms");
                            Log.i("Data", String.valueOf(bookingData.getChildrenCount()));
                            mTextViewBookedRooms.setText("Booked Rooms : "+String.valueOf
                                    (bookingData.getChildrenCount()));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

/*
    @SuppressLint("StaticFieldLeak")
    class getStatusForToday extends AsyncTask<void,void,void>{


        @Override
        protected void doInBackground(void... voids) {

        }
    }*/
}
