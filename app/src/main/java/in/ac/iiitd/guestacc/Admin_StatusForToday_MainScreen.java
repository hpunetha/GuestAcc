package in.ac.iiitd.guestacc;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Admin_StatusForToday_MainScreen extends AppCompatActivity {

    List<Admin_StatusForToday_Data> mData = new ArrayList<>();
    DatabaseReference mDatabaseReference, mDataBaseBookingReference;
    ArrayList<String> mRoomName = new ArrayList<>();
    ArrayList<String> mRoomNameNotAvailable = new ArrayList<>();
    Booking mStatusForTodayBooking;
    Admin_StatusForToday_RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statusfortoday_activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mRoomNameNotAvailable.clear();

        //ArrayList<String> mRoomName = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.ROOM_DETAILS);
        mRoomName.clear();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot val : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getValue() != null) {
                        //Store list of all rooms
                        mRoomName.add(String.valueOf(val.child("id").getValue()));
                        Log.i("status", String.valueOf(val.child("id").getValue()));
                    }
                }
                if (mAdapter != null) { mAdapter.notifyDataSetChanged(); }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("Date", date);
        mDataBaseBookingReference = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL+"/" + date);
        mDataBaseBookingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Date", String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot val : dataSnapshot.getChildren()) {
                    if (val.getValue() != null) {
                        mStatusForTodayBooking = val.getValue(Booking.class);
                        if (mStatusForTodayBooking != null && mStatusForTodayBooking.getBooking_status().equals(MainActivity.COMPLETED)) {
                            for (int i = 0; i < mStatusForTodayBooking.getGuests().size(); i++) {
                                if (mStatusForTodayBooking.getGuests().get(i).getAllocated_room() != null)
                                    mRoomNameNotAvailable.add(mStatusForTodayBooking.getGuests().get(i).getAllocated_room());
                            }
                        }
                    }
                }
                for (int i = 0; i < mRoomName.size(); i++) {

                    if (mRoomNameNotAvailable.contains(mRoomName.get(i)))
                        mData.add(new Admin_StatusForToday_Data(mRoomName.get(i), Color.RED));
                    else
                        mData.add(new Admin_StatusForToday_Data(mRoomName.get(i), Color.GREEN));
                }//Add values to Rooms
                if (mAdapter != null) { mAdapter.notifyDataSetChanged(); }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Log.i("Date", mData.toString());
        RecyclerView myrv = (RecyclerView) findViewById(R.id.admin_statusfortoday_recyclerView);
        mAdapter = new Admin_StatusForToday_RecyclerViewAdapter(this, mData);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(mAdapter);
    }
}
