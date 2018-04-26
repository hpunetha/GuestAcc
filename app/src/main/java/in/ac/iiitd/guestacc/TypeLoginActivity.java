package in.ac.iiitd.guestacc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class TypeLoginActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    Firebase mFbClient;
    String email ;
    ProgressDialog mProgDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_login);

        mProgDiag = new ProgressDialog(this);
        mProgDiag.setMessage("Loading.... Please Wait");
        mProgDiag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDiag.setIndeterminate(true);




        final RadioButton mRadioStudent = (RadioButton) findViewById(R.id.radioStudent);
        final RadioButton mRadioFaculty = (RadioButton) findViewById(R.id.radioFaculty);
        final RadioButton mRadioAdmin = (RadioButton) findViewById(R.id.radioAdmin);
        RadioGroup mRadioGroupLoginType= (RadioGroup) findViewById(R.id.radioGroupLoginType);


        try {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            mDatabase = FirebaseDatabase.getInstance();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }


        mRadioGroupLoginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                loginCheck(mRadioStudent,mRadioFaculty,mRadioAdmin);

            }
        });

    }

    private void loginCheck(RadioButton mRadioStudent, RadioButton mRadioFaculty, RadioButton mRadioAdmin)
    {
        Boolean mRadioStudentChecked = mRadioStudent.isChecked();
        Boolean mRadioFacultyChecked = mRadioFaculty.isChecked();
        Boolean mRadioAdminChecked = mRadioAdmin.isChecked();
        mProgDiag.show();

        //mFbClient = new Firebase("https://guestacc-226e6.firebaseio.com/faculty_staff");

        if (mRadioStudentChecked)
        {
            mProgDiag.dismiss();
//            FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
//            Toast.makeText(TypeLoginActivity.this,User.getDisplayName(), Toast.LENGTH_SHORT).show();

            Intent mUserHome = new Intent(this, UserHomeActivity.class);
            mUserHome.putExtra("UserType",0);
            startActivity(mUserHome);

        }
        else if (mRadioFacultyChecked)
        {



            DatabaseReference myRef = mDatabase.getReference("faculty_staff/faculty");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean flag = false;
                    ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();

                    try
                    {
                        if (val != null)
                        {
                            Log.w("Initial HashMap Val=",val.toString());

                            for (Map <String,String> abc:val)
                            {
                                if (abc!=null) {
//                                    for (String ab: abc.values()) {
//                                        Log.w("Last HashMap Val=", ab);
//
//                                    }
                                    for (Map.Entry<String,String> ab: abc.entrySet()) {

                                        if (ab!=null)
                                        {
                                            if (ab.getKey().equalsIgnoreCase("emailid"))
                                            {
                                                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                                if (email!=null)
                                                {
                                                    if (ab.getValue().equalsIgnoreCase(email))
                                                    {
                                                        Log.w("Faculty Found ", ab.getValue());
                                                        mProgDiag.dismiss();
                                                        Intent mFacultyHome = new Intent(TypeLoginActivity.this, FacultyHomeActivity.class);
                                                        mFacultyHome.putExtra("UserType",1);
                                                        startActivity(mFacultyHome);

                                                        flag=true;
                                                    }

                                                }
                                                Log.w("Last HashMap Val=", ab.getValue());
                                            }

                                        }



                                    }
                                }
                            }



                            }
                        else
                        {
                            Log.w("HashMap Value is => ", "NULL");
                        }



                    }
                    catch(NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                    if (!flag)
                    {
                        mProgDiag.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Faculty", Toast.LENGTH_SHORT).show();
                        Intent mSignOut = new Intent(TypeLoginActivity.this, MainActivity.class);
                        mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mSignOut);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            // flag=true;


//

            //



        }
        else if (mRadioAdminChecked)
        {
            boolean flag = false;

            //mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = mDatabase.getReference("admin_details");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean flag = false;
                    ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();

                    try
                    {
                        if (val != null)
                        {
                            Log.w("Admin Initial HashMap=",val.toString());

                            for (Map <String,String> abc:val)
                            {
                                if (abc!=null) {
//                                    for (String ab: abc.values()) {
//                                        Log.w("Last HashMap Val=", ab);
//
//                                    }
                                    for (Map.Entry<String,String> ab: abc.entrySet()) {

                                        if (ab!=null)
                                        {
                                            if (ab.getKey().equalsIgnoreCase("emailid"))
                                            {


                                                if (email!=null)
                                                {
                                                    if (ab.getValue().equalsIgnoreCase(email))
                                                    {
                                                        Log.w("Admin Found ", ab.getValue());
                                                        mProgDiag.dismiss();
                                                        Toast.makeText(TypeLoginActivity.this,"Authenticated as Admin", Toast.LENGTH_SHORT).show();


                                                        flag=true;
                                                    }

                                                }
                                                else
                                                {
                                                    Log.w("NULL EMAIL Found ", " NULL ");
                                                }
                                                Log.w("Last HashMap Val=", ab.getValue());
                                            }

                                        }



                                    }
                                }
                            }



                        }
                        else
                        {
                            Log.w("HashMap Value is => ", "NULL");
                        }



                    }
                    catch(NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                    if (!flag)
                    {
                        mProgDiag.dismiss();
                        FirebaseAuth.getInstance().signOut();

                        Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Admin", Toast.LENGTH_SHORT).show();
                        Intent mSignOut = new Intent(TypeLoginActivity.this, MainActivity.class);
                        mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mSignOut);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
    }
}
