package in.ac.iiitd.guestacc;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
    String mSendToDate,mSendFromDate;
    CheckAvailabilityTask mCheckAvailTask;
    Button btnCheckAvailFaculty;
    Date mToDate;
    int mDateVal =0;

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

                Intent mBookingDetail = new Intent(FacultyHomeActivity.this, BookingDetail.class);

                startActivity(mBookingDetail);



                mCheckAvailTask = new CheckAvailabilityTask(FacultyHomeActivity.this);
                mCheckAvailTask.execute();

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


//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
        }
    }//onActivityResult



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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class CheckAvailabilityTask extends AsyncTask<String,String,String>
    {
        private FacultyHomeActivity mFacHomeAct;

        public CheckAvailabilityTask(FacultyHomeActivity activ)
        {
            this.mFacHomeAct = activ;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
                Log.i("sendToDate",mSendToDate);
                Log.i("fromDate",mSendFromDate);
                DatabaseReference myRef = mDatabase.getReference("bookings_final");



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }


}
