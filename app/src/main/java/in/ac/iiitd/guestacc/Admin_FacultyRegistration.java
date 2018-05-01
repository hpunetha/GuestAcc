package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.os.Message;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by rakesh on 28/4/18.
 */

public class Admin_FacultyRegistration extends AppCompatActivity implements Admin_FacultyRegistration_RecyclerAdapter.FacultyRegistrationItemClickListener

{

    RecyclerView recyclerView ;
    Admin_FacultyRegistration_RecyclerAdapter adapter ;

    private DatabaseReference mDatabase,existingFaculties;
    ValueEventListener mListener,mExistingFacultyListener;
    HashSet<String> registeredEmails ;
    List<Admin_Data_Faculty_Registration> data ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_faculty_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*------------------------   Database Access ---------------------------------*/


        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.JOIN_REQUESTS);

        existingFaculties = FirebaseDatabase.getInstance().getReference(MainActivity.FACULTY_STAFF+"/"+"faculty");

         //mDatabase
        data = new ArrayList<>() ;
        registeredEmails = new HashSet<String>();
    // create valuelistener variable

       mExistingFacultyListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot db:dataSnapshot.getChildren())
                    {

                       // Log.e("DATA1",db.child("emailid").getValue().toString()) ;
                        registeredEmails.add(db.child("emailid").getValue().toString());
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
            }

        };

        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                  //  Log.e("DATA",dataSnapshot.getValue()) ;
                  for(DataSnapshot db : dataSnapshot.getChildren())
                  {
                     // Log.e("DATA",db.child("email_id"));

                      //db.child("email_id").toString();
                      String key  = db.getKey().toString();
                      String Name = db.child("name").getValue().toString() ;
                      String email =  db.child("email_id").getValue().toString();
                      String type  = db.child("type").getValue().toString();

                        Log.e("DATA2",email) ;

                        data.add(new Admin_Data_Faculty_Registration(Name,type,email,key)) ;
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

        // add value listener to reference

        mDatabase.addValueEventListener(mListener) ;
        existingFaculties.addValueEventListener(mExistingFacultyListener);

        /*--------------------------------------------------------------------------------*/

      /*  List<Admin_Data_Faculty_Registration> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_Faculty_Registration("AAAA","Faculty","123@iiitd.ac.in")) ;
        }*/



        recyclerView = (RecyclerView)findViewById(R.id.faculty_registration_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_FacultyRegistration_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View v, int position)
    {

    }

    @Override

    public void onButtonClick(View v,int position)
    {
        Log.e("DATA4",String.valueOf(position)) ;

            String email = data.get(position).getEmail() ;
            String key = data.get(position).getKey() ;

        if(!registeredEmails.contains(email))
        {
            // remove from request list
            // add to
            //Log.e("DATA4",mDatabase.child());

            DatabaseReference add = FirebaseDatabase.getInstance().getReference(MainActivity.FACULTY_STAFF+"/"+"faculty") ;

            //add.

           DatabaseReference delete = FirebaseDatabase.getInstance().getReference(MainActivity.JOIN_REQUESTS);
           delete.child(key).removeValue() ;

           // mDatabase.child().setValue() ;
        }
    }

}
