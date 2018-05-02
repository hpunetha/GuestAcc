package in.ac.iiitd.guestacc;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Admin_RoomAddActivity extends AppCompatActivity {
    EditText mEditTextRoomLocation,mEditTextRoomPrice;
    CheckBox checkBoxMale,checkBoxFemale,checkBoxStudentCanBook,checkBoxFacultyCanBook;
    String email;
    DatabaseReference mMyFirebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__room_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextRoomLocation =(EditText)findViewById(R.id.editTextRoomLocation);
        mEditTextRoomPrice =(EditText) findViewById(R.id.editTextRoomPrice);
        checkBoxMale =(CheckBox) findViewById(R.id.checkBoxMale);
        checkBoxFemale =(CheckBox) findViewById(R.id.checkBoxFemale);
        checkBoxStudentCanBook =(CheckBox) findViewById(R.id.checkBoxStudentCanBook);
        checkBoxFacultyCanBook =(CheckBox) findViewById(R.id.checkBoxFacultyCanBook);

        Button mAddRoomButton =(Button) findViewById(R.id.addRoomButton);

        mMyFirebaseRef = FirebaseDatabase.getInstance().getReference("newrooms");


        try {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        mAddRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewRoom mNewRoom = new NewRoom();

                mNewRoom.location = mEditTextRoomLocation.getText().toString();
                mNewRoom.price = mEditTextRoomPrice.getText().toString();
                if (mNewRoom.location == null || mNewRoom.location.trim().equalsIgnoreCase("")) {

                    Snackbar.make(view, "Email cannot be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (mNewRoom.price == null || mNewRoom.price.trim().equalsIgnoreCase("")) {
                    Snackbar.make(view, "Price cannot be blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    if (checkBoxMale.isChecked()) {
                        mNewRoom.whocanstay.add("m");
                    }
                    if (checkBoxFemale.isChecked()) {
                        mNewRoom.whocanstay.add("f");
                    }
                    if (checkBoxFacultyCanBook.isChecked()) {
                        mNewRoom.whocanbook.add("f");

                    }
                    if (checkBoxStudentCanBook.isChecked()) {
                        mNewRoom.whocanbook.add("s");
                    }




                    //mMyFirebaseRef.push().setValue(mJoinReq);
                    mMyFirebaseRef.push().setValue(mNewRoom);

                    finish();

                }


            }
        });



    }

    public static class NewRoom implements Serializable {
        public String price;
        public String location;
        public String type;
        public ArrayList<String> whocanstay;
        public ArrayList<String> whocanbook;

        public NewRoom()
        {
            whocanbook = new ArrayList<>();
            whocanstay = new ArrayList<>();

        }


    }



}

