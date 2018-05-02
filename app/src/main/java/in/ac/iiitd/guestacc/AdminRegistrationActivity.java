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

public class AdminRegistrationActivity extends AppCompatActivity {

    EditText mNameF , mDOJ,mEmail;
    private DatabaseReference mMyFirebaseRef;
    String email;
    AdminRegistrationActivity.AdminRequest mJoinReq;
    ArrayAdapter mReqTypeAdapter;
    String mSelectedType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        mMyFirebaseRef = FirebaseDatabase.getInstance().getReference(MainActivity.ADMIN_DETAILS);

        mNameF = (EditText)findViewById(R.id.editTextNameAdmin);
        mEmail = (EditText) findViewById(R.id.editTextEmailAdmin);
        mDOJ = (EditText) findViewById(R.id.editYearOfJoinAdmin);

        mJoinReq = new AdminRegistrationActivity.AdminRequest();


        try {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }



        Button mSubmitRequest = (Button) findViewById(R.id.approveAdmin);



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
                    mJoinReq.year_of_joining = yoj;
                    //mJoinReq.type =
                    Log.i("Timestamp", mTimeStamp.toString());

                    //mMyFirebaseRef.push().setValue(mJoinReq);
                    mMyFirebaseRef.child(emailToSet.replace("@iiitd.ac.in","")).setValue(mJoinReq);
                    Toast.makeText(AdminRegistrationActivity.this,name + " added as Admin", Toast.LENGTH_SHORT).show();


                    finish();
                }

            }
        });

    }

    public static class AdminRequest implements Serializable {
        public String emailid;
        public String name;
        public String timestamp;

        public String year_of_joining;

        public AdminRequest()
        {

        }


    }
}
