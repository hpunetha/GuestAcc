package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FacultyRegistrationActivity extends AppCompatActivity {

    EditText mNameF , mDOJ,mEmail;
    Spinner spinFacultyStaff;
    private DatabaseReference mMyFirebaseRef;
    String email;
    Request mJoinReq;
    ArrayAdapter mReqTypeAdapter;
    String mSelectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);


        mMyFirebaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.JOIN_REQUESTS);

        mNameF = (EditText)findViewById(R.id.editTextName);
        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mDOJ = (EditText) findViewById(R.id.editYearOfJoin);
        spinFacultyStaff = (Spinner) findViewById(R.id.spinFacultyStaff);

        mJoinReq = new Request();


        List<String> reqTypes = new ArrayList<String>();
        reqTypes.add("Faculty");
        reqTypes.add("Staff");
        mReqTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reqTypes);
        mReqTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFacultyStaff.setAdapter(mReqTypeAdapter);


        try {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }


        if(email!=null)
        {
            mJoinReq.emailid=email;
            mEmail.setText(email);
        }


        Button mSubmitRequest = (Button) findViewById(R.id.buttonSubmitRequest);

        spinFacultyStaff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedType = adapterView.getItemAtPosition(i).toString();
                Log.i("Selected Type",mSelectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailToSet =mEmail.getText().toString();
                String name = mNameF.getText().toString();
                String yoj =mDOJ.getText().toString();
                //if (emailToSet!=null)

                if (emailToSet.trim().equalsIgnoreCase(""))
                {

                    Snackbar.make(view, "Email cannot be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if(name.trim().equalsIgnoreCase(""))
                {

                    Snackbar.make(view, "Name cannot be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if(yoj.trim().equalsIgnoreCase(""))
                {
                    Snackbar.make(view, "Year of Joining cannot be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if(!emailToSet.trim().contains("@iiitd.ac.in"))
                {
                    Snackbar.make(view, "Only IIITD email ids are allowed. Please type the complete email id", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {

                    Timestamp mTimeStamp = new Timestamp(System.currentTimeMillis());
                    mJoinReq.timestamp = mTimeStamp.toString();
                    mJoinReq.emailid = emailToSet;

                    mJoinReq.name = name;
                    mJoinReq.type = mSelectedType.toLowerCase();
                    mJoinReq.year_of_joining = yoj;
                    //mJoinReq.type =
                    Log.i("Timestamp", mTimeStamp.toString());

                    //mMyFirebaseRef.push().setValue(mJoinReq);
                    mMyFirebaseRef.child(emailToSet.replace("@iiitd.ac.in","")).setValue(mJoinReq);
                    Toast.makeText(FacultyRegistrationActivity.this,"Registration request submitted successfully", Toast.LENGTH_SHORT).show();

                    LoginClient_Singleton mClient = LoginClient_Singleton.getInstance(null);
                    GoogleSignInClient mGSClient = mClient.getClient();
                    mGSClient.signOut()
                            .addOnCompleteListener(FacultyRegistrationActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // Signing out Gmail as well
                                }
                            });
                    FirebaseAuth.getInstance().signOut();

                    Intent mSignOut = new Intent(FacultyRegistrationActivity.this, MainActivity.class);
                    mSignOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mSignOut);
                    finish();
                }

            }
        });


    }

    public static class Request implements Serializable{
        public String emailid;
        public String name;
        public String timestamp;
        public String type;
        public String year_of_joining;

        public Request()
        {

        }


    }



    public static boolean checkIIITDMailId(String email)
    {
        if (email.trim().contains("@iiitd.ac.in"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
