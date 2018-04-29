package in.ac.iiitd.guestacc;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;


public class BookingDetail extends AppCompatActivity implements FragmentPersonalGuestDetails.OnFragmentInteractionListener, FragmentOfficialGuestDetails.OnFragmentInteractionListener{
    Boolean flag = true;
    private Spinner spinner_purpose,spinner_official_funding,spinner_official_personal_funding_payedby;
    FragmentManager mFragMan;
    FragmentTransaction mFragTran;
    public static int mFragGuestCountTag=0;
    public static int mNoOfGuests = 2;
    public static HashMap<String,Guest> hm_guestDetails = new HashMap<String, Guest>();
    private DatabaseReference databaseReference_key_generator,databaseReference_bookings_final,databaseReference_pending_approval,databaseReference_user;
    Booking booking = null;


    //Request Stages
    String PENDING_APPROVAL = "pending_approval";
    String PENDING_PAYMENT= "pending_payment";
    String COMPLETED = "completed";
    String CANCELLED = "cancelled";


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        final Spinner spinner_purpose = findViewById(R.id.spinner_purpose);
        final Spinner spinner_personal_funding= findViewById(R.id.spinner_personal_funding);
        spinner_official_funding =findViewById(R.id.spinner_official_funding);
        spinner_official_personal_funding_payedby = findViewById(R.id.spinner_official_personal_funding_payedby);
        final EditText editText_PName = (EditText)findViewById(R.id.editText_PName);
        final EditText editText_PI = (EditText)findViewById(R.id.editText_PI);
        final EditText editText_InstituteDesc = (EditText)findViewById(R.id.editText_InstituteDesc);
        final EditText editText_ROV = (EditText)findViewById(R.id.editText_ROV);


        String[] items = new String[]{"Personal", "Official"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner_purpose.setAdapter(adapter);

        String[] items_Official_FundedBy = new String[]{"Project", "Institute","Self","Visitor"};
        ArrayAdapter<String> adapter_Official_FundedBy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_Official_FundedBy);
        spinner_official_funding.setAdapter(adapter_Official_FundedBy);

        String[] items_FundedBy = new String[]{"Self", "Visitor"};
        ArrayAdapter<String> adapter_fundedBy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_FundedBy);
        spinner_personal_funding.setAdapter(adapter_fundedBy);

//        String[] items_official_personal_FundedBy = new String[]{"Self", "Visitor"};
//        ArrayAdapter<String> adapter_official_personal_Funding_paidby = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_official_personal_FundedBy);
//        spinner_official_personal_funding_payedby.setAdapter(adapter_official_personal_Funding_paidby);


        Button btnBook = (Button)findViewById(R.id.btnBook);
        databaseReference_key_generator = FirebaseDatabase.getInstance().getReference("bookings_final_testing_");
        databaseReference_bookings_final = FirebaseDatabase.getInstance().getReference("bookings_final_testing_");
        databaseReference_pending_approval = FirebaseDatabase.getInstance().getReference("pending_requests/pending_approval_test");
        databaseReference_user = FirebaseDatabase.getInstance().getReference("user");


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    boolean flag_emptyEntry = false;


                if(editText_ROV.getText().toString().isEmpty())
                {
                    flag_emptyEntry=true;
//                    Snackbar.make(view, "Please fill the reason of visit", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();

                }
                else if (spinner_purpose.getSelectedItem().toString()=="Official")
                {
                    if(spinner_official_funding.getSelectedItem().toString() == "Project")
                    {
                        if(editText_PName.getText().toString().isEmpty())
                        {
//                            Snackbar.make(view, "Please fill the name of project ", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                            flag_emptyEntry=true;
                        }
                        else if(editText_PI.getText().toString().isEmpty())
                        {
                            flag_emptyEntry=true;
//                            Snackbar.make(view, "Please fill the name of the principal investigator", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                        }
                    }
                    if(spinner_official_funding.getSelectedItem().toString() == "Institute")
                    {
                        if(editText_InstituteDesc.getText().toString().isEmpty())
                        {
//                            Snackbar.make(view, "Please fill the description regrading the institute funding", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                            flag_emptyEntry=true;
                        }
                    }
                }

                if(!hm_guestDetails.isEmpty())
                {
                    Iterator<String> iterator = hm_guestDetails.keySet().iterator();
                    while (iterator.hasNext()) {
                        String firstName = "";
                        String age = "";
                        String key = iterator.next().toString();
                        if (hm_guestDetails.get(key).getName() != null) {
                            firstName = hm_guestDetails.get(key).getName().toString();
                        }
                        if (hm_guestDetails.get(key).getAge() != null) {
                            age = hm_guestDetails.get(key).getAge().toString();
                        }
                        if (firstName == "" || age == "") {
                            flag_emptyEntry = true;
                            //Snackbar.make(view, "Please fill all the names and ages of the guests", Snackbar.LENGTH_LONG)
                            //        .setAction("Action", null).show();
                            break;
                        }
                    }
                }
                if (flag_emptyEntry==false) {
                        System.out.println("Null entry found");
                        System.out.println(hm_guestDetails);
                        System.out.println("show =================>fragments ");
                        booking = new Booking();
                        booking.setBooking_status(PENDING_APPROVAL);

                        booking.setFrom_date(FacultyHomeActivity.mSendFromDate);
                        booking.setTo_date(FacultyHomeActivity.mSendToDate);


                        if (spinner_purpose.getSelectedItem().toString() == "Personal") {

                            if (spinner_personal_funding.getSelectedItem().toString() == "Self") {
                                booking.setFundedby_personalbooking("Self");
                            }
                            if (spinner_personal_funding.getSelectedItem().toString() == "Visitor") {
                                booking.setFundedby_personalbooking("Visitor");
                            }


                            booking.setFundedby_institute_details("");
                            booking.setFundedby_project_pinvestigator("");
                            booking.setFundedby_project_pname("");
                            booking.setFundedby_self_officialbooking("");
                            booking.setFundedby_visitor_officialbooking("");
                        }
                        if (spinner_purpose.getSelectedItem().toString() == "Official") {



                            if (spinner_official_funding.getSelectedItem().toString() == "Institute") {
                                booking.setFundedby_personalbooking("");
                                booking.setFundedby_project_pname("");
                                booking.setFundedby_project_pinvestigator("");
                                booking.setFundedby_self_officialbooking("");
                                booking.setFundedby_visitor_officialbooking("");
                                booking.setFundedby_institute_details(editText_InstituteDesc.getText().toString());
                            }

                            if (spinner_official_funding.getSelectedItem().toString() == "Project") {
                                booking.setFundedby_personalbooking("");
                                booking.setFundedby_self_officialbooking("");
                                booking.setFundedby_institute_details("");
                                booking.setFundedby_visitor_officialbooking("");
                                booking.setFundedby_project_pinvestigator(editText_PI.getText().toString());
                                booking.setFundedby_project_pname(editText_PName.getText().toString());
                            }


                            if (spinner_official_funding.getSelectedItem().toString() == "Self") {
                                booking.setFundedby_personalbooking("");
                                booking.setFundedby_institute_details("");
                                booking.setFundedby_project_pname("");
                                booking.setFundedby_project_pinvestigator("");
                                booking.setFundedby_visitor_officialbooking("");
                                booking.setFundedby_self_officialbooking("true");
                            }

                            if (spinner_official_funding.getSelectedItem().toString() == "Visitor") {
                                booking.setFundedby_personalbooking("");
                                booking.setFundedby_institute_details("");
                                booking.setFundedby_project_pname("");
                                booking.setFundedby_project_pinvestigator("");
                                booking.setFundedby_self_officialbooking("");
                                booking.setFundedby_visitor_officialbooking("true");
                            }
                        }

                        booking.setModification_reason("");

                        booking.setPurpose_of_visit(editText_ROV.getText().toString());
                        if (spinner_purpose.getSelectedItem().toString() == "Personal")
                            booking.setRequest_type_personal_or_official("Personal");
                        if (spinner_purpose.getSelectedItem().toString() == "Official")
                            booking.setRequest_type_personal_or_official("Official");

                        Date mFromDate, mToDate1;
                        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        try {
                            mFromDate = mDateFormat.parse(FacultyHomeActivity.mSendFromDate);
                            mToDate1 = mDateFormat.parse(FacultyHomeActivity.mSendToDate);
                            // Reference=>   https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java

                            ArrayList<Date> mDateList = new ArrayList<Date>();
                            Calendar mCalender = new GregorianCalendar();
                            mCalender.setTime(mFromDate);
                            while (mCalender.getTime().before(mToDate1)) {
                                Date result = mCalender.getTime();
                                mDateList.add(result);
                                mCalender.add(Calendar.DATE, 1);
                            }
                            System.out.println("mDateList=========>" + mDateList);
                            System.out.println("Number of Days=========>" + (mDateList.size()));
                            booking.setNo_of_days(String.valueOf(mDateList.size()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Long tsLong = System.currentTimeMillis() / 1000;
                        booking.setTimestamp(tsLong.toString());
                        booking.setTotal_booking_price(String.valueOf(FacultyHomeActivity.mTotalPrice));

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date todaydate = new Date();
                        booking.setRaised_on(formatter.format(todaydate));


                        System.out.println(hm_guestDetails);
                        Iterator<String> iterator_hm_guestDetails = hm_guestDetails.keySet().iterator();
                        while (iterator_hm_guestDetails.hasNext()) {
                            Guest guest = new Guest();
                            String key = iterator_hm_guestDetails.next().toString();
                            guest.setName(hm_guestDetails.get(key).getName().toString());
                            guest.setAge(hm_guestDetails.get(key).getAge().toString());
                            guest.setGender(hm_guestDetails.get(key).getGender().toString());
                            guest.setAssociated_room_id(hm_guestDetails.get(key).getAssociated_room_id().toString().replace("Room Id: ",""));
                            guest.setAllocated_room(hm_guestDetails.get(key).getAllocated_room().toString());
                            guest.setRoom_type(hm_guestDetails.get(key).getRoom_type().toString());
                            guest.setPrefered_location(hm_guestDetails.get(key).getPrefered_location().toString());
                            booking.guests.add(guest);
                        }

                        String mRequestId = databaseReference_bookings_final.child(FacultyHomeActivity.mSendFromDate).push().getKey();
                        databaseReference_bookings_final.child(FacultyHomeActivity.mSendFromDate).child(mRequestId).setValue(booking);
                        System.out.println("mRequestId=======>" + mRequestId);

                        //Insert in pending table correct.
                        Request request = new Request();
                        request.setFrom_date(FacultyHomeActivity.mSendFromDate);
                        databaseReference_pending_approval.child(mRequestId).setValue(request);


                        UserBookingDetails userBookingDetails = new UserBookingDetails();
                        userBookingDetails.setFrom_date(FacultyHomeActivity.mSendFromDate);
                        userBookingDetails.setNumber_of_persons(String.valueOf(FacultyHomeActivity.mTotalGuests));
                        userBookingDetails.setNumber_of_rooms(String.valueOf(FacultyHomeActivity.mTotalRooms));
                        userBookingDetails.setRaised_on(formatter.format(todaydate));
                        userBookingDetails.setStatus(PENDING_APPROVAL);
                        userBookingDetails.setTotal_amount(String.valueOf(FacultyHomeActivity.mTotalPrice));
                        String email="";
                        try {
                            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        }
                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }
                        databaseReference_user.child(email.replace("@iiitd.ac.in","")).child(mRequestId).setValue(userBookingDetails);

                        //System.out.println("Your Request is submitted sucessfully");
                        Toast.makeText(getApplicationContext(), "Your request is submitted sucessfully", Toast.LENGTH_LONG).show();
                        //finish();
                    System.out.println("============>>>>>Enter");
                    finish();
                    //Intent mFacultyHomeActivity = new Intent(BookingDetail.this, FacultyHomeActivity.class);
                    //startActivity(mFacultyHomeActivity);
                    System.out.println("================>>>>>>>>>Completed");

                    }
                    else
                    {
                        Snackbar.make(view, "All are mandatory fields. Please fill all of them", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
            }
        });

        spinner_official_funding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    spinner_official_personal_funding_payedby.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.GONE);
                    editText_PName.setVisibility(View.VISIBLE);
                    editText_PI.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    spinner_official_personal_funding_payedby.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.VISIBLE);
                    editText_PName.setVisibility(View.GONE);
                    editText_PI.setVisibility(View.GONE);

                }
                if (i == 2) {
                    spinner_official_personal_funding_payedby.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.GONE);
                    editText_PName.setVisibility(View.GONE);
                    editText_PI.setVisibility(View.GONE);
                }
                if (i == 3) {
                    spinner_official_personal_funding_payedby.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.GONE);
                    editText_PName.setVisibility(View.GONE);
                    editText_PI.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });












        spinner_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    hm_guestDetails.clear();
                    if (mFragMan!=null) {
                        for(Fragment fragment:getSupportFragmentManager().getFragments()){

                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                        }

                    LinearLayout linearLayout_Personal = (LinearLayout) findViewById(R.id.layout_personalBookings);
                    linearLayout_Personal.setVisibility(View.VISIBLE);
                    LinearLayout linearLayout_Official = (LinearLayout) findViewById(R.id.layout_officialBookings);
                    linearLayout_Official.setVisibility(View.INVISIBLE);

                        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
                        {
                            String roomTagKey = entry.getKey();
                            RoomItem roomDetails = entry.getValue();
                            int mFemaleCount = roomDetails.mFemaleCount;
                            int mMaleCount = roomDetails.mMaleCount;
                            String id = roomDetails.id;
                            int mRoomPrice = roomDetails.mRoomPrice;
                            int mRoomType = roomDetails.mRoomType;
                            String  roomPref  = roomDetails.roomPref;
                            for(int k=0;k<mMaleCount;k++)
                            {
                                mFragGuestCountTag++;
                                mFragMan = getSupportFragmentManager();
                                mFragTran = mFragMan.beginTransaction();
                                String tag = String.valueOf(mFragGuestCountTag);
                                FragmentPersonalGuestDetails mFacultyAddFrag = FragmentPersonalGuestDetails.newInstance(tag, "MALE","Room Id: "+String.valueOf(id),String.valueOf(mRoomType),roomPref);
                                mFragTran.add(R.id.allguestDetailfragmentsPlaceHolder, mFacultyAddFrag, tag);
                                mFragTran.commit();
                            }
                            for(int k=0;k<mFemaleCount;k++)
                            {
                                mFragGuestCountTag++;
                                mFragMan = getSupportFragmentManager();
                                mFragTran = mFragMan.beginTransaction();
                                String tag = String.valueOf(mFragGuestCountTag);
                                FragmentPersonalGuestDetails mFacultyAddFrag = FragmentPersonalGuestDetails.newInstance(tag, "FEMALE","Room Id: "+String.valueOf(id),String.valueOf(mRoomType),roomPref);
                                mFragTran.add(R.id.allguestDetailfragmentsPlaceHolder, mFacultyAddFrag, tag);
                                mFragTran.commit();
                            }
                        }

                }
                if(i==1)
                {
                    hm_guestDetails.clear();

                    if (mFragMan!=null) {
                        for(Fragment fragment:getSupportFragmentManager().getFragments()){

                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }

                    LinearLayout linearLayout_Personal = (LinearLayout) findViewById(R.id.layout_personalBookings);
                    linearLayout_Personal.setVisibility(View.GONE);
                    LinearLayout linearLayout_Official = (LinearLayout) findViewById(R.id.layout_officialBookings);
                    linearLayout_Official.setVisibility(View.VISIBLE);


                    for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
                    {
                        String roomTagKey = entry.getKey();
                        RoomItem roomDetails = entry.getValue();
                        int mFemaleCount = roomDetails.mFemaleCount;
                        int mMaleCount = roomDetails.mMaleCount;
                        String id = roomDetails.id;
                        int mRoomPrice = roomDetails.mRoomPrice;
                        int mRoomType = roomDetails.mRoomType;
                        String  roomPref  = roomDetails.roomPref;
                        for(int k=0;k<mMaleCount;k++)
                        {
                            mFragGuestCountTag++;
                            mFragMan = getSupportFragmentManager();
                            mFragTran = mFragMan.beginTransaction();
                            String tag = String.valueOf(mFragGuestCountTag);
                            FragmentOfficialGuestDetails mFacultyAddFrag = FragmentOfficialGuestDetails.newInstance(tag, "MALE","Room Id: "+String.valueOf(id),String.valueOf(mRoomType),roomPref);
                            mFragTran.add(R.id.allguestDetailOfficialFragmentsPlaceHolder, mFacultyAddFrag, tag);
                            mFragTran.commit();
                        }
                        for(int k=0;k<mFemaleCount;k++)
                        {
                            mFragGuestCountTag++;
                            mFragMan = getSupportFragmentManager();
                            mFragTran = mFragMan.beginTransaction();
                            String tag = String.valueOf(mFragGuestCountTag);
                            FragmentOfficialGuestDetails mFacultyAddFrag = FragmentOfficialGuestDetails.newInstance(tag, "FEMALE", "Room Id: "+ String.valueOf(id),String.valueOf(mRoomType),roomPref);
                            mFragTran.add(R.id.allguestDetailOfficialFragmentsPlaceHolder, mFacultyAddFrag, tag);
                            mFragTran.commit();
                        }
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        System.out.println("Activity Stopped====================>");
        super.onStop();
    }
}
