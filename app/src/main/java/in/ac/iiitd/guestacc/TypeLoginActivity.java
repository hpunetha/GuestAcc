package in.ac.iiitd.guestacc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    public static final String USERTYPE = "user";
    public static final int STUDENT=0;
    public static final int FACULTY=1;
    public static final int ADMIN=2;

    boolean mStaffFlag=false;
    boolean mFacultyFlag=false;
    boolean mBothFlag=false;

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


            SharedPreferences mSharedPref = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor mEdit = mSharedPref.edit();
            mEdit.putInt(USERTYPE,STUDENT);
            mEdit.apply();

            Intent mUserHome = new Intent(this, FacultyHomeActivity.class);
            mUserHome.putExtra(USERTYPE,STUDENT);
            startActivity(mUserHome);




        }
        else if (mRadioFacultyChecked)
        {



            DatabaseReference myRef = mDatabase.getReference(MainActivity.FACULTY_STAFF+"/faculty");
//********************OLD CODE COMMENTED BELOW************************
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    boolean flag = false;
//                    ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();
//
//                    try
//                    {
//                        if (val != null)
//                        {
//                            Log.w("Initial HashMap Val=",val.toString());
//
//                            for (Map <String,String> abc:val)
//                            {
//                                if (abc!=null) {
////                                    for (String ab: abc.values()) {
////                                        Log.w("Last HashMap Val=", ab);
////
////                                    }
//                                    for (Map.Entry<String,String> ab: abc.entrySet()) {
//
//                                        if (ab!=null)
//                                        {
//                                            if (ab.getKey().equalsIgnoreCase("emailid"))
//                                            {
//                                                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
//                                                if (email!=null)
//                                                {
//                                                    if (ab.getValue().equalsIgnoreCase(email))
//                                                    {
//                                                        Log.w("Faculty Found ", ab.getValue());
//                                                        mProgDiag.dismiss();
//                                                        Intent mFacultyHome = new Intent(TypeLoginActivity.this, FacultyHomeActivity.class);
//                                                        mFacultyHome.putExtra("UserType",1);
//                                                        startActivity(mFacultyHome);
//
//                                                        flag=true;
//                                                    }
//
//                                                }
//                                                Log.w("Last HashMap Val=", ab.getValue());
//                                            }
//
//                                        }
//
//
//
//                                    }
//                                }
//                            }
//
//
//
//                            }
//                        else
//                        {
//                            Log.w("HashMap Value is => ", "NULL");
//                        }
//
//
//
//                    }
//                    catch(NullPointerException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                    if (!flag)
//                    {
//                        mProgDiag.dismiss();
//                        FirebaseAuth.getInstance().signOut();
//                        Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Faculty", Toast.LENGTH_SHORT).show();
//                        Intent mSignOut = new Intent(TypeLoginActivity.this, MainActivity.class);
//                        mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(mSignOut);
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
// ********************END OLD CODE************************

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mFacultyFlag = false;
                    //ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();
                    //Log.i("")
                    try
                    {
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        Log.i("snapshot",dataSnapshot.getChildren().toString());

                        for (DataSnapshot db1 : dataSnapshot.getChildren()) {

                            //  Log.i("Email Values",db1.child("emailid").getValue().toString() +"   =? "+ email);


                            String mToCheckEmail = db1.child("emailid").getValue().toString();
                            if (mToCheckEmail != null)
                            {
                                if (mToCheckEmail.equalsIgnoreCase(email)){

                                    if (email != null)
                                    {


                                        Log.i("Email Found", mToCheckEmail + "   =? " + email);


                                        Log.w("Faculty Found ", mToCheckEmail);
                                        mProgDiag.dismiss();

                                        SharedPreferences mSharedPref = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor mEdit = mSharedPref.edit();
                                        mEdit.putInt(USERTYPE,FACULTY);
                                        mEdit.apply();

                                        Intent mFacultyHome = new Intent(TypeLoginActivity.this, FacultyHomeActivity.class);
                                        mFacultyHome.putExtra(USERTYPE, FACULTY);
                                        startActivity(mFacultyHome);

                                        mFacultyFlag = true;


                                        Log.w("Last HashMap Val=", mToCheckEmail);
                                        break;
                                    }
                                }

                            }
                        }



                    }
                    catch(NullPointerException e)
                    {
                        e.printStackTrace();
                    }

                    if (!mFacultyFlag)
                    {
//                        mProgDiag.dismiss();

                       // Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Faculty", Toast.LENGTH_SHORT).show();


                        checkStaff();


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if (mRadioAdminChecked)
        {
            boolean flag = false;

            //mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = mDatabase.getReference(MainActivity.ADMIN_DETAILS);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean flag = false;
                    //ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();
                    try {
                        for (DataSnapshot val: dataSnapshot.getChildren()){
                            //Log.i ("Admin",val.child("emailid").getValue().toString());
                            if (val.child("emailid").getValue().equals(email)){
                                //Log.w("Admin Found ", ab.getValue());


                                SharedPreferences mSharedPref = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor mEdit = mSharedPref.edit();
                                mEdit.putInt(USERTYPE,ADMIN);
                                mEdit.apply();

                                Toast.makeText(TypeLoginActivity.this,"Authenticated as Admin", Toast.LENGTH_SHORT).show();
                                Intent mAdminHome = new Intent(TypeLoginActivity.this,Admin_HomeActivity.class);
                                startActivity(mAdminHome);
                                finish();
                                flag=true;
                                break;
                            }
                        }
                    }
                    catch(NullPointerException e) {
                        e.printStackTrace();
                    }
/*
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
                                                        Intent mAdminHome = new Intent(TypeLoginActivity.this,Admin_HomeActivity.class);
                                                        startActivity(mAdminHome);
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
                    }*/

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


    private void checkStaff()
    {
        DatabaseReference myRef = mDatabase.getReference(MainActivity.FACULTY_STAFF+"/staff");
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mStaffFlag = false;
                //ArrayList<Map<String,String>> val = (ArrayList<Map<String, String>>) dataSnapshot.getValue();
                //Log.i("")
                try
                {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    Log.i("snapshot",dataSnapshot.getChildren().toString());

                    for (DataSnapshot db1 : dataSnapshot.getChildren())
                    {

                        //  Log.i("Email Values",db1.child("emailid").getValue().toString() +"   =? "+ email);


                        String mToCheckEmail = db1.child("emailid").getValue().toString();
                        if (mToCheckEmail != null)
                        {
                            if (mToCheckEmail.equalsIgnoreCase(email)){

                                if (email != null)
                                {


                                    Log.i("Email Found", mToCheckEmail + "   =? " + email);


                                    Log.w("Staff Found ", mToCheckEmail);
                                    mProgDiag.dismiss();


                                    SharedPreferences mSharedPref = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor mEdit = mSharedPref.edit();
                                    mEdit.putInt(USERTYPE,FACULTY);
                                    mEdit.apply();

                                    Intent mFacultyHome = new Intent(TypeLoginActivity.this, FacultyHomeActivity.class);
                                    mFacultyHome.putExtra(USERTYPE, FACULTY);
                                    startActivity(mFacultyHome);

                                    mStaffFlag = true;



                                    Log.w("Last HashMap Val=", mToCheckEmail);
                                    break;
                                }
                            }

                        }
                    }



                }
                catch(NullPointerException e)
                {
                    e.printStackTrace();
                }

                if (!mStaffFlag)
                {
                    mProgDiag.dismiss();
                    //FirebaseAuth.getInstance().signOut();
                    //Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Staff", Toast.LENGTH_SHORT).show();
                    openRegPromptDialog();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void openRegPromptDialog()
    {
        AlertDialog.Builder mBuild = new AlertDialog.Builder(TypeLoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_faculty_registration_prompt_dialog,null);


        final Button mYes = (Button) mView.findViewById(R.id.btnYesRegister);
        final Button mNo = (Button) mView.findViewById(R.id.btnNoRegister);


        mBuild.setView(mView);
        final AlertDialog mAlertDialog = mBuild.create();
        mAlertDialog.show();

        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.hide();
                Intent mRegIntent = new Intent(TypeLoginActivity.this, FacultyRegistrationActivity.class);

                startActivity(mRegIntent);

            }
        });

        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.hide();

                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(TypeLoginActivity.this,"Not Permitted to Sign-in as Faculty", Toast.LENGTH_SHORT).show();
                Intent mSignOut = new Intent(TypeLoginActivity.this, MainActivity.class);
                mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mSignOut);

            }
        });


    }


}
