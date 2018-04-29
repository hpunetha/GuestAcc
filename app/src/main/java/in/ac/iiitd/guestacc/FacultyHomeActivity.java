package in.ac.iiitd.guestacc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.snapshot.BooleanNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FacultyHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //public static HashMap<String,RoomItem> mAllRoomsDetails;
    public static HashMap<String,RoomItem> mAllRoomsDetails;

    private int mTotalRooms=0,mTotalMales=0,mTotalFemales=0,mTotalGuests=0;
    private int mTotalPrice=0;

    public static final String TOTALPRICE = "totalprice";
    public static final String TOTALROOMS = "totalrooms";
    public static final String TOTALMALES = "totalmales";
    public static final String TOTALFEMALES = "totalfemales";
    public static final String TOTALGUESTS = "totalguests";


    FirebaseDatabase mDatabase;
    CardView mContractedCardView,mExpandedCardView;
    TextView mTotalPriceTextView;
    TextView mTotalRoomsTextView;
    EditText mEditTextRoomDetailsFaculty;
    EditText mEditTextFromDate;
    EditText mEditTextToDate;
    TextView mAgreeTextView;
    CheckBox mAgreeTermsCheckBox;
    RelativeLayout mAgreeTermsRelativeLayout;
    public static String mSendToDate,mSendFromDate;
    CheckAvailabilityTask mCheckAvailTask;
    Button btnCheckAvailFaculty;
    Date mToDate;
    int mDateVal =0;
    ProgressDialog mProgDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAllRoomsDetails =new HashMap<>();



        try
        {
            mDatabase = FirebaseDatabase.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        btnCheckAvailFaculty = (Button) findViewById(R.id.btnCheckAvailFaculty);
        btnCheckAvailFaculty.setEnabled(false);

        final RelativeLayout.LayoutParams mExpandedParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mExpandedParameters.addRule(RelativeLayout.BELOW, R.id.expandedCardView);

        final RelativeLayout.LayoutParams mContractedParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mContractedParameters.addRule(RelativeLayout.BELOW, R.id.contractedCardView);


        mAgreeTermsRelativeLayout = (RelativeLayout) findViewById(R.id.agreeTermsRelativeLayout);
        mAgreeTermsCheckBox =(CheckBox) findViewById(R.id.agreeTermsCheckBox);
        mAgreeTextView= (TextView) findViewById(R.id.agreeTermsTextView);

        mAgreeTermsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAgreeTermsCheckBox.isChecked())
                {
                    btnCheckAvailFaculty.setEnabled(true);

                }
                else
                {
                    btnCheckAvailFaculty.setEnabled(false);
                }

            }
        });

        mAgreeTermsRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAgreeTermsCheckBox.isChecked())
                {
                    btnCheckAvailFaculty.setEnabled(false);
                    mAgreeTermsCheckBox.setChecked(false);
                }
                else
                {
                    btnCheckAvailFaculty.setEnabled(true);
                    mAgreeTermsCheckBox.setChecked(true);
                }
            }
        });


        btnCheckAvailFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAllRoomsDetails.size()>0)
                {

                    mCheckAvailTask = new CheckAvailabilityTask(FacultyHomeActivity.this);
                    mCheckAvailTask.execute();

//                Intent mBookingDetail = new Intent(FacultyHomeActivity.this, BookingDetail.class);
//                startActivity(mBookingDetail);

                }
                else
                {

                    Snackbar.make(view, "No Rooms are added. Please add rooms first", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }






            }
        });


        mTotalPriceTextView = (TextView)findViewById(R.id.totalpriceID);
        mTotalRoomsTextView = (TextView)findViewById(R.id.totalroomID);

        mTotalPriceTextView.setVisibility(View.GONE);
        mTotalRoomsTextView.setVisibility(View.GONE);

        mContractedCardView = (CardView)findViewById(R.id.contractedCardView);
        mExpandedCardView = ( CardView) findViewById(R.id.expandedCardView);
        mExpandedCardView.setAlpha(0f);
        mExpandedCardView.setVisibility(View.GONE);

        mContractedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedCardView.setVisibility(View.VISIBLE);
                mExpandedCardView.animate().alpha(1f).setDuration(400);
                mContractedCardView.setAlpha(0f);
                mContractedCardView.setVisibility(View.GONE);
                mAgreeTermsRelativeLayout.setLayoutParams(mExpandedParameters);

            }
        });

        mExpandedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContractedCardView.setVisibility(View.VISIBLE);
                mContractedCardView.animate().alpha(1f).setDuration(400);
                mExpandedCardView.setVisibility(View.GONE);
                mExpandedCardView.setAlpha(0f);
                mAgreeTermsRelativeLayout.setLayoutParams(mContractedParameters);

            }
        });

        mEditTextFromDate= (EditText) findViewById(R.id.editTextFacultyFrom);
        mEditTextToDate= (EditText) findViewById(R.id.editTextFacultyTo);
        mEditTextRoomDetailsFaculty =(EditText) findViewById(R.id.editTextRoomDetailsFaculty);

        mEditTextRoomDetailsFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mFacultyAdd = new Intent(FacultyHomeActivity.this, FacultyRoomAddActivity.class);
                startActivityForResult(mFacultyAdd,11);

            }
        });

        final String mFormat = "E, MMM d";
        SimpleDateFormat mPreSDFormat = new SimpleDateFormat(mFormat, Locale.ENGLISH);
        Date mPreDate = new Date();
        mToDate =mPreDate;
        String mPreTextCheckin = getString(R.string.check_in)+  mPreSDFormat.format(mPreDate);
        mEditTextFromDate.setText(mPreTextCheckin);
        mSendFromDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mPreDate.getTime());

        Calendar mPreCal = Calendar.getInstance();
        mPreCal.setTime(mPreDate);
        mPreCal.add(Calendar.DATE,1);
        Date mPreNextDate = mPreCal.getTime();
        // final Calendar mPreCalFinal = mPreCal;
        String mPreTextCheckout = getString(R.string.check_out)+  mPreSDFormat.format(mPreNextDate);
        mEditTextToDate.setText(mPreTextCheckout);
        mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mPreNextDate.getTime());


        final Calendar mCal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            //DatePickerDialog.

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCal.set(Calendar.YEAR, year);
                mCal.set(Calendar.MONTH, monthOfYear);
                mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                String mFormat = "E, MMM d";
                SimpleDateFormat mSimpleDF = new SimpleDateFormat(mFormat, Locale.ENGLISH);
                if (mDateVal ==1)
                {
                    String mTextIn = getString(R.string.check_in)+ mSimpleDF.format(mCal.getTime());

                    mSendFromDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mCal.getTime());

                    mToDate =mCal.getTime();
                    mEditTextFromDate.setText(mTextIn);
                    Calendar mChkCal = Calendar.getInstance();
                    mChkCal.setTime(mToDate);
                    mChkCal.add(Calendar.DATE,1);
                    String mTextOut = getString(R.string.check_out) + mSimpleDF.format(mChkCal.getTime());
                    mEditTextToDate.setText(mTextOut);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mChkCal.getTime());

                }
                else if (mDateVal ==2)
                {
                    String text = getString(R.string.check_out) + mSimpleDF.format(mCal.getTime());
                    mEditTextToDate.setText(text);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mCal.getTime());
                }
            }

        };

        mEditTextFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(FacultyHomeActivity.this, date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));

                mDateVal =1;
                mDatePickDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                mDatePickDialog.show();
            }
        });

        mEditTextToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(FacultyHomeActivity.this, date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));
                mDateVal =2;

                Calendar mChkCal = Calendar.getInstance();
                mChkCal.setTime(mToDate);
                mChkCal.add(Calendar.DATE,1);
                //Date mPreNextDate = mChkCal.getTime();


                mDatePickDialog.getDatePicker().setMinDate(mChkCal.getTimeInMillis());
                mDatePickDialog.show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent mFacultyAdd = new Intent(FacultyHomeActivity.this, FacultyRoomAddActivity.class);

                startActivityForResult(mFacultyAdd,11);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 11) {
            if(resultCode == Activity.RESULT_OK){

                mTotalPrice=data.getIntExtra(TOTALPRICE,-1);
                mTotalRooms=data.getIntExtra(TOTALROOMS,-1);
                mTotalMales=data.getIntExtra(TOTALMALES,-1);
                mTotalFemales=data.getIntExtra(TOTALFEMALES,-1);
                mTotalGuests=data.getIntExtra(TOTALGUESTS,-1);
                Log.i("HomeActivity totalprice",String.valueOf(mTotalPrice));
                Log.i("HomeActivity totalrooms",String.valueOf(mTotalRooms));
                if (mTotalPrice!=-1 && mTotalRooms!=-1 && mTotalMales!=-1 && mTotalFemales!=-1 && mTotalGuests!=-1)
                {
                    String textToset = String.valueOf(mTotalRooms) +" " + getResources().getString(R.string.rooms) + ", "
                            + String.valueOf(mTotalGuests) + " Guests" +", " + getResources().getString(R.string.only_rs)  + String.valueOf(mTotalPrice);

                    mEditTextRoomDetailsFaculty.setText(textToset);
                    mTotalRoomsTextView.setText(String.valueOf(mTotalRooms));
                    mTotalPriceTextView.setText(String.valueOf(mTotalPrice));
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                    mEditTextRoomDetailsFaculty.setText(getResources().getString(R.string.add_room));
            }


        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.faculty_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class CheckAvailabilityTask extends AsyncTask<String,Boolean,Boolean>
    {
        Boolean exitFlag ;
        private FacultyHomeActivity mFacHomeAct;
        List<String> mDateList;

        public CheckAvailabilityTask(FacultyHomeActivity activ)
        {
            this.mFacHomeAct = activ;
            this.exitFlag=false;
            mDateList= new ArrayList<>();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgDiag = new ProgressDialog(mFacHomeAct);
            mProgDiag.setMessage("Loading.... Please Wait");
            mProgDiag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgDiag.setIndeterminate(true);
            mProgDiag.show();
        }

         @Override
        protected Boolean doInBackground(String... strings)
        {
            Date mFromDate,mToDate1;

            final List<String> mBookedRoomList;
            final List<String> mAllPossibleRoomsList = new ArrayList<>();
            mAllPossibleRoomsList.add("bh1");
            mAllPossibleRoomsList.add("gh1");
            mAllPossibleRoomsList.add("bh2");
            mAllPossibleRoomsList.add("gh2");
            mAllPossibleRoomsList.add("frr1");
            mAllPossibleRoomsList.add("frr2");
            mAllPossibleRoomsList.add("frr3");
            mAllPossibleRoomsList.add("frf1");
            mAllPossibleRoomsList.add("frf2");

            Calendar mCalender;
            mFromDate=mToDate;

            Log.i("sendToDate",mSendToDate);
            Log.i("fromDate",mSendFromDate);

            //Reference=> https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
            DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
            try {
                mFromDate = mDateFormat.parse(mSendFromDate);
                mToDate1 = mDateFormat.parse(mSendToDate);
                // Reference=>   https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java


                mCalender= new GregorianCalendar();
                mBookedRoomList = new ArrayList<String>();
                mCalender.setTime(mFromDate);
                while (mCalender.getTime().before(mToDate1))
                {
                    Date result = mCalender.getTime();
                    String resultString = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(result);

                    mDateList.add(resultString);
                    mCalender.add(Calendar.DATE, 1);
                }

                Log.i("Date List",mDateList.toString());

                final DatabaseReference myRef;
                String basetable ="bookings_final";

                final HashMap<String,Integer> mAllDateRoomsAvailabilityCount = new HashMap<>();




                //
                String strDBAccess = basetable;
                Log.i("date access",strDBAccess);
                myRef = mDatabase.getReference(strDBAccess);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        for(DataSnapshot child : dataSnapshot.getChildren() )
                        {
                            List<String> mDateAvailableRooms = new ArrayList<>();

                            mDateAvailableRooms = mAllPossibleRoomsList;

                            if (mDateList.contains(child.getKey()))
                            {
                                final String tempDateString = child.getKey();

                                if (dataSnapshot.getChildrenCount() != 0)
                                {
                                    Log.i("Bookings", "Bookings for " + tempDateString + " children " + dataSnapshot.getChildren().toString());

                                    for (DataSnapshot dbS : child.getChildren())
                                    {
                                        try
                                        {

                                            Log.i("id booking", "id ->" + dbS.getKey() + " booking status->" + dbS.child("booking_status").getValue());

                                            //String check ="completed";
                                            String check = "pending approval";  //to be commented
                                            if (dbS.child("booking_status").getValue().toString().equalsIgnoreCase(check)) {
                                                //Check in completed bookings

                                                Log.i("booking status check", "Condition true");

                                                //DataSnapshot guestSnapshot = dataSnapshot.child("guests");
                                                for (DataSnapshot guests : dbS.getChildren()) {
                                                    //                                                Log.i("Loop Entered", "entered");
                                                    if (guests.getKey().equalsIgnoreCase("guests")) {


                                                        Log.i("Alloc check=>", guests.child("allocated_room").toString());
                                                        if (mDateAvailableRooms.contains(guests.child("allocated_room").toString())) {
                                                            //Log.i("Room already exists", " in Booked Rooms List => " + mBookedRoomList.toString() + " room type=>" + guests.child("allocated_room").toString());
                                                            mDateAvailableRooms.remove(guests.child("allocated_room").toString());
                                                        } else {
                                                            //mBookedRoomList.add(guests.child("allocated_room").toString());

                                                            // Log.i("Room Added", " to Booked Rooms List => " + mBookedRoomList.toString());


                                                        }
                                                    } else {
                                                        //                                                    Log.i("Loop Entered Else", "Else Condition Invoked");

                                                    }
                                                }

                                            }

                                            mAllDateRoomsAvailabilityCount.put(tempDateString, mDateAvailableRooms.size());
                                            Log.i("Size of Rooms list", String.valueOf(mAllDateRoomsAvailabilityCount.toString()));


                                            for (Map.Entry<String, Integer> entry : mAllDateRoomsAvailabilityCount.entrySet()) {
                                                String d = entry.getKey();
                                                Integer val = entry.getValue();
                                                //if (mTotalRooms > val) {
                                                //disable below line and enable above one
                                                if (mTotalRooms > 2) {
                                                    Log.i("Total ROoms", String.valueOf(mTotalRooms));
                                                    exitFlag = true;
                                                    mProgDiag.dismiss();
                                                    Snackbar.make(mFacHomeAct.btnCheckAvailFaculty, "Specified number of rooms not available for given period.", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
//                                                    myRef.removeEventListener(this);
//
                                                   break;

                                                }
                                            }

                                            if (exitFlag) {
                                                Log.i("exitflag1", String.valueOf(exitFlag));
                                                //
                                                break;
                                            }


                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                            continue;
                                        }
                                    }
                                } else {
                                    Log.i("No Bookings ", "No Bookings for date " + tempDateString);


                                }
                                Log.i("exitflag2", String.valueOf(exitFlag));
                            }



                            if (exitFlag) {
                                Log.i("exitflag3", String.valueOf(exitFlag));
                                //
                                break;
                            }


                        }


                        if(!exitFlag)
                        {
                            mProgDiag.dismiss();

                            Intent mBookingDetail = new Intent(FacultyHomeActivity.this, BookingDetail.class);
                            startActivity(mBookingDetail);

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.i("exitflagfinal",String.valueOf(exitFlag));




            return exitFlag;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            Log.i("exitFlagPOST",String.valueOf(exitFlag));





        }

    }


}
