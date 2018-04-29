package in.ac.iiitd.guestacc;

/**
 * Created by kd on 24/4/18.
 */

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
import java.util.List;
import java.util.Locale;

public class Admin_StatusForToday_MainScreen extends AppCompatActivity {

    List<Admin_StatusForToday_Data> mData = new ArrayList<>();
    DatabaseReference mDatabaseReference;
    ArrayList<String> mRoomName = new ArrayList<>();
    ArrayList<String> mRoomNameNotAvailable = new ArrayList<>();
    Booking mStatusForTodayBooking;
    Admin_StatusForToday_RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statusfortoday_activity_main);

        //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("room_details");
        mRoomNameNotAvailable.clear();
        mRoomName.clear();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot val:dataSnapshot.getChildren()){
                    //Store list of all rooms
                    mRoomName.add(String.valueOf(val.child("id").getValue()));
                    Log.i("status",String.valueOf(val.child("id").getValue()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("Date",date);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("bookings_final/"+date);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Date", String.valueOf(dataSnapshot.getChildrenCount()));
                if (dataSnapshot.getValue() != null) {
                    mStatusForTodayBooking = dataSnapshot.getValue(Booking.class);
                    if (mStatusForTodayBooking.getGuests().size() > 0 && mStatusForTodayBooking != null) {
                        for (int i = 0; i < mStatusForTodayBooking.getGuests().size(); i++) {
                            if (mStatusForTodayBooking.getGuests().get(i).getAssociated_room_id() != null)
                                mRoomNameNotAvailable.add(mStatusForTodayBooking.getGuests().get(i).getAssociated_room_id());
                        }
                    }
                }
                //Add values to Rooms
                for (int i=0;i<mRoomName.size();i++){
                    if (mRoomNameNotAvailable.contains(mRoomName.get(i)))
                        mData.add(new Admin_StatusForToday_Data(mRoomName.get(i),Color.RED));
                    else
                        mData.add(new Admin_StatusForToday_Data(mRoomName.get(i),Color.GREEN));
                }
                if (mAdapter!=null){mAdapter.notifyDataSetChanged();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        int color = Color.GREEN;
        mData.add(new Admin_StatusForToday_Data("BH1",color));
        mData.add(new Admin_StatusForToday_Data("BH2",color));
        mData.add(new Admin_StatusForToday_Data("GH1",color));
        mData.add(new Admin_StatusForToday_Data("GH2",color));
        mData.add(new Admin_StatusForToday_Data("FR1",color));
        mData.add(new Admin_StatusForToday_Data("FR2",color));
        mData.add(new Admin_StatusForToday_Data("FF1",color));

        */

        Log.i("Date",mData.toString());
        RecyclerView myrv = (RecyclerView)findViewById(R.id.admin_statusfortoday_recyclerView);
        mAdapter = new Admin_StatusForToday_RecyclerViewAdapter(this,mData);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(mAdapter);
    }
}
