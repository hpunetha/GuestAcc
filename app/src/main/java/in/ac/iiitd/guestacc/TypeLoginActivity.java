package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TypeLoginActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    Firebase mFbClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_login);



        final RadioButton mRadioStudent = (RadioButton) findViewById(R.id.radioStudent);
        final RadioButton mRadioFaculty = (RadioButton) findViewById(R.id.radioFaculty);
        final RadioButton mRadioAdmin = (RadioButton) findViewById(R.id.radioAdmin);
        RadioGroup mRadioGroupLoginType= (RadioGroup) findViewById(R.id.radioGroupLoginType);

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

        //mFbClient = new Firebase("https://guestacc-226e6.firebaseio.com/faculty_staff");

        if (mRadioStudentChecked)
        {
//            FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
//            Toast.makeText(TypeLoginActivity.this,User.getDisplayName(), Toast.LENGTH_SHORT).show();

            Intent mUserHome = new Intent(this, UserHomeActivity.class);
            mUserHome.putExtra("UserType",0);
            startActivity(mUserHome);

        }
        else if (mRadioFacultyChecked)
        {
            boolean flag = false;

            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = mDatabase.getReference("faculty_staff/faculty");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();

                    try
                    {
                        if (val != null)
                        {
                            Log.w("Initial HashMap Val=",val.toString());

                            for (Map <String,String> abc:val)
                            {
                                if (abc!=null) {
                                    for (String ab: abc.values()) {
                                        Log.w("Last HashMap Val=", ab);

                                    }
                                }
                            }

                            //Iterator

//                                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
//                                if (email != null)
//                                {
//
//                                    for (Object hm : val.entrySet())
//                                    {
//                                        List<Map<String,Object>> vp =
//                                        Log.w("Last HashMap Val=",hm.toString());
//
////                                        for (Object pt : hm)
////                                        {
////
////                                        }
//
//                                    }
//
//                                }
//                                else
//                                {
//                                    Log.w("ERROR =>","No User Logged in");
//                                }


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


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            // flag=true;

            if (flag)
            {
                Intent mFacultyHome = new Intent(this, UserHomeActivity.class);
                mFacultyHome.putExtra("UserType",1);
                startActivity(mFacultyHome);
            }
            else
            {
//                FirebaseAuth.getInstance().signOut();
//                Intent mSignOut = new Intent(this, MainActivity.class);
//                mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(mSignOut);
                //this.finish();

            }
//

            //



        }
        else if (mRadioAdminChecked)
        {
            boolean flag = false;


            // flag=true;

            if (!flag)
            {
                Intent mAdminHome = new Intent(this, AdminHomeActivity.class);
                startActivity(mAdminHome);
            }
            else
            {
                FirebaseAuth.getInstance().signOut();
                Intent mSignOut = new Intent(this, MainActivity.class);
                mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mSignOut);
                //this.finish();

            }

        }
    }
}
