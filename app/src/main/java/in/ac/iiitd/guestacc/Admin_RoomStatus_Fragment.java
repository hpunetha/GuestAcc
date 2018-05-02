package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by kd on 22/4/18.
 */

public class Admin_RoomStatus_Fragment extends Fragment implements Admin_RoomStatus_RecyclerAdapter.ItemClickListener {
    Date mToDate;
    int mDateVal, mTotalRooms;
    String mSendFromDate, mSendToDate;
    Button mAdminRoomAvailability;
    EditText mEditTextFromDate, mEditTextToDate;
    ListView mListViewAdminRoomStatus;
    Spinner mSpinnerAvailabilityStatus;
    ArrayAdapter<CharSequence> spinnerStatusAdapter;

    Boolean exitFlag;

    List<String> mDateList, mFirebaseDateList;
    FirebaseDatabase mDatabase;
    List<Admin_Data_RoomStatus> data;
    HashMap<String, Boolean> mAllDateRoomsAvailabilityCount;


    RecyclerView recyclerView;
    Admin_RoomStatus_RecyclerAdapter adapter;




    public  String MapRoom(String t)
    {
        String type = "";

        if (t.contains("bh")) type = "Boys' Hostel";
        if (t.contains("gh")) type = "Girls' Hostel";
        if (t.contains("frr")) type = "Faculty Rooms";
        if (t.contains("frf")) type = "Faculty Flat";


        return type ;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminRoomStatus = inflater.inflate(R.layout.fragment_admin_room_status, container, false);

        data = new ArrayList<>();

      //  mDatabase = FirebaseDatabase.getInstance() ;
     /*#################################### RETRIEVE DATA END ############################################*/

       // mListViewAdminRoomStatus = (ListView) adminRoomStatus.findViewById(R.id.list_adminRoomStatus);
        mEditTextFromDate = (EditText) adminRoomStatus.findViewById(R.id.editTextAdminRoomFrom);
        TextView mAdminRoomStatus = (TextView) adminRoomStatus.findViewById(R.id.adminRoomStatus) ;
        mAdminRoomStatus.setVisibility(View.GONE);
        mEditTextToDate = (EditText) adminRoomStatus.findViewById(R.id.editTextAdminRoomTo);
        mAdminRoomAvailability = (Button) adminRoomStatus.findViewById(R.id.button_adminRoomCheckAvailability);
        //Reference : https://www.youtube.com/watch?v=28jA5-mO8K8
        mSpinnerAvailabilityStatus = (Spinner) adminRoomStatus.findViewById(R.id.spinner_adminAvailabilityStatus);
        mSpinnerAvailabilityStatus.setVisibility(View.GONE);
        spinnerStatusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.adminRoomStatusSpinner, android.R.layout.simple_spinner_item);
        spinnerStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAvailabilityStatus.setAdapter(spinnerStatusAdapter);

        //***************************************************************************************************************************
        final String mFormat = "E, MMM d";
        SimpleDateFormat mPreSDFormat = new SimpleDateFormat(mFormat, Locale.ENGLISH);
        Date mPreDate = new Date();
        mToDate = mPreDate;
        String mPreTextCheckin = getString(R.string.check_in) + mPreSDFormat.format(mPreDate);
        mEditTextFromDate.setText(mPreTextCheckin);
        mSendFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(mPreDate.getTime());

        Calendar mPreCal = Calendar.getInstance();
        mPreCal.setTime(mPreDate);
        mPreCal.add(Calendar.DATE, 1);
        Date mPreNextDate = mPreCal.getTime();

        String mPreTextCheckout = getString(R.string.check_out) + mPreSDFormat.format(mPreNextDate);
        mEditTextToDate.setText(mPreTextCheckout);
        mSendToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(mPreNextDate.getTime());


        final Calendar mCal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            //DatePickerDialog.

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCal.set(Calendar.YEAR, year);
                mCal.set(Calendar.MONTH, monthOfYear);
                mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//              String mFormat = "E, MMM d";
                SimpleDateFormat mSimpleDF = new SimpleDateFormat(mFormat, Locale.ENGLISH);

                if (mDateVal == 1) {
                    String mTextIn = getString(R.string.check_in) + mSimpleDF.format(mCal.getTime());

                    mSendFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(mCal.getTime());

                    mToDate = mCal.getTime();
                    mEditTextFromDate.setText(mTextIn);
                    Calendar mChkCal = Calendar.getInstance();
                    mChkCal.setTime(mToDate);
                    mChkCal.add(Calendar.DATE, 1);
                    String mTextOut = getString(R.string.check_out) + mSimpleDF.format(mChkCal.getTime());
                    mEditTextToDate.setText(mTextOut);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(mChkCal.getTime());

                } else if (mDateVal == 2) {
                    String text = getString(R.string.check_out) + mSimpleDF.format(mCal.getTime());
                    mEditTextToDate.setText(text);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(mCal.getTime());
                }
            }

        };

        mEditTextFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(getActivity(), date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));

                mDateVal = 1;
                //mDatePickDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                mDatePickDialog.show();
            }
        });

        mEditTextToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(getActivity(), date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));
                mDateVal = 2;

                Calendar mChkCal = Calendar.getInstance();
                mChkCal.setTime(mToDate);
                mChkCal.add(Calendar.DATE, 1);
                //Date mPreNextDate = mChkCal.getTime();

                mDatePickDialog.getDatePicker().setMinDate(mChkCal.getTimeInMillis());
                mDatePickDialog.show();
            }
        });

//                mAdminRoomAvailability.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        new checkRoomAvailabilityStatus().execute("");
//                    }
//                });



        //*********************************************************************************************************************


        /*################################    RETRIEVE DATA #####################################################*/




        try
        {
            mDatabase = FirebaseDatabase.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        mAdminRoomAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();
                showRooms();
            }
        });







/*************************   RECYCLER VIEW  ***************************************/


/*   mAllDateRoomsAvailabilityCount.put("bh1",true);
        mAllDateRoomsAvailabilityCount.put("bh2",true);
        mAllDateRoomsAvailabilityCount.put("gh1",true);
        mAllDateRoomsAvailabilityCount.put("gh2",true);
        mAllDateRoomsAvailabilityCount.put("frr1",true);
        mAllDateRoomsAvailabilityCount.put("frr2",true);
        mAllDateRoomsAvailabilityCount.put("frr3",true);
        mAllDateRoomsAvailabilityCount.put("frf1",true);
        mAllDateRoomsAvailabilityCount.put("frf2",true);*/





        recyclerView = (RecyclerView) adminRoomStatus.findViewById(R.id.admin_room_status_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new Admin_RoomStatus_RecyclerAdapter(this.getActivity(), data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


/****************************END RECYCLERVIEW******************************************/
        return adminRoomStatus;
    }


    public void showRooms()
    {

        /***************************************************************************************/

          /*================================   KULDEEP' CODE ==========================================*/
        DatabaseReference mDatabaseReference ;
        final ArrayList<String> mRoomName = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("room_details");
        mRoomName.clear();



        Log.i("statusSerach", mRoomName.toString());
        /*==========================  END KULDEEP'S CODE= ==============================================*/

        mAllDateRoomsAvailabilityCount = new HashMap<>();

        mAllDateRoomsAvailabilityCount.put("bh1",true);
        mAllDateRoomsAvailabilityCount.put("bh2",true);
        mAllDateRoomsAvailabilityCount.put("gh1",true);
        mAllDateRoomsAvailabilityCount.put("gh2",true);
        mAllDateRoomsAvailabilityCount.put("frr1",true);
        mAllDateRoomsAvailabilityCount.put("frr2",true);
        mAllDateRoomsAvailabilityCount.put("frr3",true);
        mAllDateRoomsAvailabilityCount.put("frf1",true);
        mAllDateRoomsAvailabilityCount.put("frf2",true);



        for(int i=0;i<mRoomName.size();i++)
        {
            mAllDateRoomsAvailabilityCount.put(mRoomName.get(i),true) ;

            Log.e("INC",mRoomName.get(i)) ;
        }

        Date mFromDate,mToDate1;

        final List<String> mBookedRoomList;

        mDateList= new ArrayList<>();
        Calendar mCalender;
        mFromDate=mToDate;

        mFirebaseDateList = new ArrayList<>();
//        Log.i("sendToDate",mSendToDate);
//        Log.i("fromDate",mSendFromDate);

        //Reference=> https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
        DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        try
        {
            if (mDatabase!=null) {
                mFromDate = mDateFormat.parse(mSendFromDate);
                mToDate1 = mDateFormat.parse(mSendToDate);
                // Reference=>   https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java


                mCalender = new GregorianCalendar();
                mBookedRoomList = new ArrayList<String>();
                mCalender.setTime(mFromDate);
                while (mCalender.getTime().before(mToDate1)) {
                    Date result = mCalender.getTime();
                    String resultString = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(result);

                    mDateList.add(resultString);
                    mCalender.add(Calendar.DATE, 1);
                }

                Log.i("Date List", mDateList.toString());

                final DatabaseReference myRef;
                // String basetable ="bookings_final";
              /*  String basetable = "bookings_final";

                //
                String strDBAccess = basetable;
                Log.i("date access", strDBAccess);*/


                Log.e("KULDEEP",mAllDateRoomsAvailabilityCount.toString());
                myRef = mDatabase.getReference(MainActivity.BOOKING_FINAL);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //                        for(DataSnapshot child : dataSnapshot.getChildren() )
                        //                        {
                        //
                        //
                        //                        }

                        Log.i("DATA SNAP START", dataSnapshot.toString());


                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            exitFlag = false;

                            Log.i("Retrieving child", " child key => " + child.getKey() + " and mDateList" + mDateList);
                            if (mDateList.contains(child.getKey())) {
                                final String tempDateString = child.getKey();

                                if (child.getChildrenCount() != 0) {
                                    Log.i("Bookings", "Bookings for " + tempDateString + " children " + dataSnapshot.getChildren().toString());

                                    for (DataSnapshot dbS : child.getChildren()) {
                                        try {

                                            Log.i("id booking", "id ->" + dbS.getKey() + " booking status->" + dbS.child("booking_status").getValue());

                                            if (dbS.getValue() != null) {
                                            }

                                            Booking mAdminBooking = dbS.getValue(Booking.class);
                                            // if (mAdminBooking.guests.size()>0)
                                            Log.i("INSIDETAG", mAdminBooking.booking_status);

                                            // pending_approval change to completed
                                            if (mAdminBooking.booking_status.equalsIgnoreCase("completed")) {


                                                if (mAdminBooking.guests.size() > 0) {
                                                    for (Guest guest1 : mAdminBooking.guests) {

                                                        Log.i("Check Keys", mAllDateRoomsAvailabilityCount.keySet().toString() + "  =? " + guest1.allocated_room);

                                                        if (mAllDateRoomsAvailabilityCount.keySet().contains(guest1.allocated_room)) {
                                                            mAllDateRoomsAvailabilityCount.put(guest1.allocated_room, false);


                                                        }
                                                    }

                                                    Log.i("Final Hashmap", mAllDateRoomsAvailabilityCount.toString());


                                                }


                                                //                                                    Log.i("INSIDETAG", mAdminBooking.guests.get(0).associated_room_id);

                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }
                                } else {
                                    Log.i("No Bookings ", "No Bookings for date " + tempDateString);


                                }

                            }


                        }

                        // **********************n Modification 8****************************8



                        for ( String key : mAllDateRoomsAvailabilityCount.keySet() )
                        {
                            String roomNo = "Room "+Character.toString(key.charAt(key.length()-1)) ;
                            String t = key.substring(0,key.length()-2) ;
                            String color ;


                            Boolean  c = mAllDateRoomsAvailabilityCount.get(key) ;


                            if(c==true)         color = "A" ;
                            else                color = "NA" ;
                            Log.i("INCOMING_DATA",t);
                            data.add(new Admin_Data_RoomStatus(MapRoom(t),roomNo,color));

                        }

                        if(adapter!=null)      adapter.notifyDataSetChanged();

                        //**********************************************************************
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }

        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }



        /***************************************************************************************/
    }

//    @SuppressLint("StaticFieldLeak")
//    private class checkRoomAvailabilityStatus extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Date mFromDate, mToDate1;
//            List<Date> mDateList;
//            Calendar mCalender;
//            mFromDate = mToDate;
//
//            Log.i("sendToDate", mSendToDate);
//            Log.i("fromDate", mSendFromDate);
//
//            //Reference=> https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
//            DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//            try {
//                mFromDate = mDateFormat.parse(mSendFromDate);
//                mToDate1 = mDateFormat.parse(mSendToDate);
//                // Reference=>   https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java
//
//                mDateList = new ArrayList<Date>();
//                mCalender = new GregorianCalendar();
//                mCalender.setTime(mFromDate);
//                while (mCalender.getTime().before(mToDate1)) {
//                    Date result = mCalender.getTime();
//                    mDateList.add(result);
//                    mCalender.add(Calendar.DATE, 1);
//                }
//
//                Log.i("Date List", mDateList.toString());
//                Log.i("Date list", String.valueOf(mDateList.size()));
//
//                DatabaseReference myRef;
//                String basetable = "bookings_final";
//
//                for (Date tempdate : mDateList) {
//                    final String tempDateString = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tempdate);
//                    String strDBAccess = basetable + '/' + tempDateString;
//                    Log.i("date access", strDBAccess);
//                    myRef = FirebaseDatabase.getInstance().getReference(strDBAccess);
//
//                    myRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.getChildrenCount() != 0) {
//                                Log.i("Bookings", "Bookings for " + tempDateString + " children " + dataSnapshot.getChildren().toString());
//                                for (DataSnapshot dbS : dataSnapshot.getChildren()) {
//                                    Log.i("id booking", "id ->" + dbS.getKey() + " booking status->" + dbS.child("booking_status").getValue());
//                                }
//                            } else {
//                                Log.i("No Bookings ", "No Bookings for date " + tempDateString);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override

    public void onItemClick(View v, int position) {

    }
}
