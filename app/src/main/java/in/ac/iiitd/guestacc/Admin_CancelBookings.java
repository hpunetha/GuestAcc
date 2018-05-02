package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_CancelBookings extends AppCompatActivity implements Admin_CancelBookings_RecyclerAdapter.CancelItemClickListener
{
    Admin_CancelBookings_RecyclerAdapter adapter ;
    RecyclerView recyclerView ;
    private DatabaseReference mDbRefBookings;
    ValueEventListener mDbRefBookingsListener;
    List<Admin_Data_CancelBookings> mlist_dataCancelBooking = new ArrayList<>() ;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cancel_bookings);


        // TODO : make layout expandable
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Admin_Data_CancelBookings> data = new ArrayList<>();
        mDbRefBookings = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL+"/"+"2018-04-30");


        mDbRefBookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //  Log.e("DATA",dataSnapshot.getValue()) ;
                    for(DataSnapshot db : dataSnapshot.getChildren())
                    {
                        // Log.e("DATA",db.child("email_id"));

                        //db.child("email_id").toString();
                        String requestId  = db.getKey().toString();
                        System.out.println(requestId);
                        Log.d("req id:",requestId);
                        String total_guests = db.child("total_guests").getValue().toString() ;
                        String total_rooms =  db.child("total_rooms").getValue().toString();
                        String from_date  = db.child("from_date").getValue().toString();
                        String to_date  = db.child("to_date").getValue().toString();

                        Log.d("total_guests:",total_guests);
                        Log.d("total_rooms:",total_rooms);
                        Log.d("from_date:",from_date);

                        ArrayList<String> list_allocatedRooms = new ArrayList<String>();
                        list_allocatedRooms.add("bh1");

                        Log.e("DATA2",from_date) ;

                        mlist_dataCancelBooking.add(new Admin_Data_CancelBookings(requestId,"bh1",total_guests,total_rooms,from_date.substring(from_date.length() - 5),to_date.substring(to_date.length() - 5)));
                 }
                }

                if(adapter!=null)
                {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }
        };

        mDbRefBookings.addValueEventListener(mDbRefBookingsListener);
        recyclerView = (RecyclerView)findViewById(R.id.cancel_bookings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_CancelBookings_RecyclerAdapter(this, mlist_dataCancelBooking);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View v, int position)
    {

    }
}
