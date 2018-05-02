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
        mDbRefBookings = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL_);//+"/"+"2018-05-03");


        mDbRefBookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    for(DataSnapshot db : dataSnapshot.getChildren())
                    {
                        Log.d("date====>:", db.getKey().toString());
                        for(DataSnapshot db_id : db.getChildren()) {

                            if(db_id.child("booking_status").getValue().toString().equals("completed"))
                            {
                                String requestId = db_id.getKey().toString();

                                Log.d("req id:", db_id.getKey().toString());
                                String total_guests = db_id.child("total_guests").getValue().toString();
                                String total_rooms = db_id.child("total_rooms").getValue().toString();
                                String from_date = db_id.child("from_date").getValue().toString();
                                String to_date = db_id.child("to_date").getValue().toString();

                                Log.d("total_guests:", total_guests);
                                Log.d("total_rooms:", total_rooms);
                                Log.d("from_date:", from_date);

                                ArrayList<String> list_allocatedRooms = new ArrayList<String>();
                                list_allocatedRooms.add("bh1");

                                Log.e("DATA2", from_date);

                                mlist_dataCancelBooking.add(new Admin_Data_CancelBookings(requestId, "", total_guests, total_rooms, from_date,to_date));//.substring(from_date.length() - 5), to_date.substring(to_date.length() - 5)));
                                Log.d("mlist_==>" , mlist_dataCancelBooking.toString());
                            }
                        }
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
        Log.d("mlist_==>" , mlist_dataCancelBooking.toString());
        adapter = new Admin_CancelBookings_RecyclerAdapter(this, mlist_dataCancelBooking);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View v, int position)
    {

    }
}
