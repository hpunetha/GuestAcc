package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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



public class Admin_ValidatePayment extends AppCompatActivity implements Admin_ValidatePayment_RecyclerAdapter.ValidateItemClickListener
{


    RecyclerView recyclerView ;
    Admin_ValidatePayment_RecyclerAdapter adapter ;
    DatabaseReference mFireBaseReference;
    List<Admin_Data_ValidatePayment> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_validate_payment);


        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...Please Wait ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        //progress.show();
       // progress.dismiss();

        data = new ArrayList<>();
        mFireBaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.PENDING_REQUESTS+"/"+MainActivity.VERIFY_PAYMENT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFireBaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Admin_Data_ValidatePayment mAdminData ;

                for (DataSnapshot ds2: dataSnapshot.getChildren())
                {
                    if (ds2!=null) {

                        mAdminData = new Admin_Data_ValidatePayment();
                        mAdminData.setReqId(ds2.getKey());
                        mAdminData.setPersons(ds2.child("no_of_persons").getValue().toString());
                        mAdminData.setRooms(ds2.child("no_of_rooms").getValue().toString());
                        mAdminData.setTotal(ds2.child("total_price").getValue().toString());
                        mAdminData.setFromDate(ds2.child("from_date").getValue().toString());
                        Log.i("madmindata", mAdminData.getPersons() + " " + mAdminData.getReqId() +" " + mAdminData.getRooms() + " " + mAdminData.getTotal());
                        data.add(mAdminData);
                    }
                }

                if(adapter!=null) { adapter.notifyDataSetChanged();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//
//        for(int i=0;i<20;i++)
//        {
//            data.add(new Admin_Data_ValidatePayment()) ;
//        }


        recyclerView = (RecyclerView)findViewById(R.id.validate_payment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_ValidatePayment_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View v,int position)
    {

    }

}
