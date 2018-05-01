package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FacultyMyBookings extends AppCompatActivity implements FacultyMyBookings_RecyclerAdapter.ValidateItemClickListener
{

    FirebaseUser mFirebaseUser;
    String mCurrentUserName;
    String mCurrentUserEmail;
    RecyclerView recyclerView ;
    FacultyMyBookings_RecyclerAdapter adapter ;
    DatabaseReference mFireBaseReference;
    List<FacultyMyBookings_Data> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_my_bookings_recyclerview);
        try {
            getSupportActionBar().setTitle("My Bookings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e)
        {

        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...Please Wait ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        //progress.show();
       // progress.dismiss();

        data = new ArrayList<>();

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

        String email;
        if (mCurrentUserEmail!=null) {
           email= mCurrentUserEmail.replace("@iiitd.ac.in", "");
            mFireBaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.USER+"/"+ email);
        }


        if (mFireBaseReference!=null) {
            mFireBaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FacultyMyBookings_Data mFacultyMyBookingsData;

                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                        if (ds2 != null) {

                            mFacultyMyBookingsData = new FacultyMyBookings_Data();
                            mFacultyMyBookingsData.setReqId(ds2.getKey());
                            mFacultyMyBookingsData.setPersons(ds2.child("number_of_persons").getValue().toString());
                            mFacultyMyBookingsData.setRooms(ds2.child("number_of_rooms").getValue().toString());
                            mFacultyMyBookingsData.setTotal(ds2.child("total_amount").getValue().toString());
                            mFacultyMyBookingsData.setRoomStatus(ds2.child("status").getValue().toString());
                            Log.i("madmindata", mFacultyMyBookingsData.getPersons() + " " + mFacultyMyBookingsData.getReqId() + " " + mFacultyMyBookingsData.getRooms() + " " + mFacultyMyBookingsData.getTotal());
                            data.add(mFacultyMyBookingsData);
                        }
                    }

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


//
//        for(int i=0;i<20;i++)
//        {
//            data.add(new Admin_Data_ValidatePayment()) ;
//        }


        recyclerView = (RecyclerView)findViewById(R.id.mybookingsrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FacultyMyBookings_RecyclerAdapter(FacultyMyBookings.this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View v,int position)
    {

    }

}
