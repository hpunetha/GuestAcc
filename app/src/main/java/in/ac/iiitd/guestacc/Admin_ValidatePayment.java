package in.ac.iiitd.guestacc;

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

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_ValidatePayment extends AppCompatActivity implements Admin_ValidatePayment_RecyclerAdapter.ValidateItemClickListener
{


    RecyclerView recyclerView ;
    Admin_ValidatePayment_RecyclerAdapter adapter ;
    private DatabaseReference mDatabase;
    ValueEventListener mListener ;
    List<Admin_Data_ValidatePayment> data ;


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

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_ValidatePayment()) ;
        }




        //*********************************************************//

        mDatabase = FirebaseDatabase.getInstance().getReference("user");


        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //  Log.e("DATA",dataSnapshot.getValue()) ;
                    for(DataSnapshot db : dataSnapshot.getChildren())
                    {
                        // Log.e("DATA",db.child("email_id"));

                       // db.child()

                        //db.child("email_id").toString();
                       // String key  = db.getKey().toString();
                      //  String Name = db.child("name").getValue().toString() ;
                     //   String email =  db.child("email_id").getValue().toString();
                     //   String type  = db.child("type").getValue().toString();

                       // db.getValue().
                       // Log.e("DATA2--",db.getKey().toString()) ;

                       // data.add(new Admin_Data_Faculty_Registration(Name,type,email,key)) ;
//
//
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



        mDatabase.addValueEventListener(mListener) ;

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
