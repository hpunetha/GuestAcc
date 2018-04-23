package in.ac.iiitd.guestacc;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class BookingDetail extends AppCompatActivity implements FragmentPersonalGuestHeader.OnFragmentInteractionListener{
    Boolean flag = true;
    private Spinner spinner_purpose,spinner_official_funding,spinner_official_personal_funding;
    FragmentManager mFragMan;
    FragmentTransaction mFragTran;
    public static int mFragGuestCountTag=0;
    public static int mNoOfGuests = 2;
    public static HashMap<String,Guest> hm_guestDetails = new HashMap<String, Guest>();
    private DatabaseReference databaseReference;
    Booking booking = null;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        if (savedInstanceState == null)
        {
            System.out.println("savedInstanceState value is null");
            if (mFragMan!=null) {
                for(Fragment fragment:getSupportFragmentManager().getFragments()){

                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }
        Spinner spinner_purpose = findViewById(R.id.spinner_purpose);
        Spinner spinner_personal_funding= findViewById(R.id.spinner_personal_funding);
        spinner_official_funding =findViewById(R.id.spinner_official_funding);
        spinner_official_personal_funding = findViewById(R.id.spinner_official_personal_funding);
        final EditText editText_PName = (EditText)findViewById(R.id.editText_PName);
        final EditText editText_PI = (EditText)findViewById(R.id.editText_PI);
        final EditText editText_InstituteDesc = (EditText)findViewById(R.id.editText_InstituteDesc);



        String[] items = new String[]{"Personal", "Official"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner_purpose.setAdapter(adapter);

        String[] items_Official_FundedBy = new String[]{"Project", "Institute","Personal"};
        ArrayAdapter<String> adapter_Official_FundedBy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_Official_FundedBy);
        spinner_official_funding.setAdapter(adapter_Official_FundedBy);

        String[] items_FundedBy = new String[]{"Self", "Visitor"};
        ArrayAdapter<String> adapter_fundedBy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_FundedBy);
        spinner_personal_funding.setAdapter(adapter_fundedBy);

        String[] items_official_personal_FundedBy = new String[]{"Self", "Visitor"};
        ArrayAdapter<String> adapter_official_personal_FundedBy = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_official_personal_FundedBy);
        spinner_official_personal_funding.setAdapter(adapter_official_personal_FundedBy);




        Button btnBook = (Button)findViewById(R.id.btnBook);
        databaseReference = FirebaseDatabase.getInstance().getReference("bookings_final/");
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println(hm_guestDetails);
//                Iterator<String> iterator = hm_guestDetails.keySet().iterator();
//                while (iterator.hasNext())
//                {
//                    String key = iterator.next().toString();
//                    String firstName = hm_guestDetails.get(key).getName().toString();
//                   // String lastName = hm_guestDetails.get(key).getLast_Name().toString();
//                    String age = hm_guestDetails.get(key).getAge().toString();
//
//                   // System.out.println(firstName+" "+lastName+" "+age);
//                }


                System.out.println("show =================>fragments ");
                booking = new Booking();
                booking.setBooking_status("pending approval");
                booking.setFrom_date("25-02-2009");
                booking.setTo_date("31-02-2009"); ;
                booking.setFundedby_institute_details("");
                booking.setFundedby_project_pinvestigator("p singh");
                booking.setFundedby_project_pname("Mobile computing ");
                booking.setFundedby_self("");
                booking.setFundedby_visitor("");
                booking.setModification_reason("");
                booking.setNo_of_days("1");
                booking.setPurpose_of_visit("Project requirment analysis");
                booking.setRequest_type_personal_or_official("Official");
                Long tsLong = System.currentTimeMillis()/1000;
                booking.setTimestamp(tsLong.toString());
                booking.setTotal_booking_price("1500");
                Guest guest1 = new Guest();
                guest1.setName("Pramod Mallick");
                guest1.setItemtag("1");
                guest1.setAge("54");
                guest1.setGender("Male");
                guest1.setAssociated_room_id("1");
                guest1.setAllocated_room("");
                guest1.setPrefered_location("BH1");
                booking.guests.add(guest1);
                Guest guest2 = new Guest();
                guest2.setName("Kalyani Swain");
                guest2.setItemtag("1");
                guest2.setAge("54");
                guest2.setGender("Female");
                guest2.setAssociated_room_id("1");
                guest2.setAllocated_room("");
                guest2.setPrefered_location("BH1");
                booking.guests.add(guest2);

                databaseReference.child("2017-03-20").push().setValue(booking);




            }
        });

        spinner_official_funding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    spinner_official_personal_funding.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.GONE);
                    editText_PName.setVisibility(View.VISIBLE);
                    editText_PI.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    spinner_official_personal_funding.setVisibility(View.GONE);
                    editText_InstituteDesc.setVisibility(View.VISIBLE);
                    editText_PName.setVisibility(View.GONE);
                    editText_PI.setVisibility(View.GONE);
                }
                if (i == 2) {
                    spinner_official_personal_funding.setVisibility(View.VISIBLE);
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
                    if (mFragMan!=null) {
                        for(Fragment fragment:getSupportFragmentManager().getFragments()){

                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }
                        LinearLayout linearLayout_Personal = (LinearLayout) findViewById(R.id.layout_personalBookings);
                        linearLayout_Personal.setVisibility(View.VISIBLE);
                        LinearLayout linearLayout_Official = (LinearLayout) findViewById(R.id.layout_officialBookings);
                        linearLayout_Official.setVisibility(View.INVISIBLE);
                        for (int j = 0; j < mNoOfGuests; j++) {
                            mFragGuestCountTag++;
                            mFragMan = getSupportFragmentManager();
                            mFragTran = mFragMan.beginTransaction();
                            String tag = String.valueOf(mFragGuestCountTag);
                            FragmentPersonalGuestHeader mFacultyAddFrag = FragmentPersonalGuestHeader.newInstance(tag, "MALE", "Room ID: 1");
                            mFragTran.add(R.id.allguestDetailfragmentsPlaceHolder, mFacultyAddFrag, tag);
                            mFragTran.commit();
                        }
                        for (int j = 0; j < mNoOfGuests; j++) {
                            mFragGuestCountTag++;
                            mFragMan = getSupportFragmentManager();
                            mFragTran = mFragMan.beginTransaction();
                            String tag = String.valueOf(mFragGuestCountTag);
                            FragmentPersonalGuestHeader mFacultyAddFrag = FragmentPersonalGuestHeader.newInstance(tag, "FEMALE", "Room ID: 2");
                            mFragTran.add(R.id.allguestDetailfragmentsPlaceHolder, mFacultyAddFrag, tag);
                            mFragTran.commit();
                        }
//                        flag=false;

                    //}
//                    else
//                    {
//                        System.out.println("Show ===========>fragments without adding any new");
//                    }

                }
                if(i==1)
                {
                    LinearLayout linearLayout_Personal = (LinearLayout) findViewById(R.id.layout_personalBookings);
                    linearLayout_Personal.setVisibility(View.GONE);
                    LinearLayout linearLayout_Official = (LinearLayout) findViewById(R.id.layout_officialBookings);
                    linearLayout_Official.setVisibility(View.VISIBLE);
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
}
