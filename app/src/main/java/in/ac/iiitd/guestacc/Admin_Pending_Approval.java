package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Admin_Pending_Approval extends AppCompatActivity implements Admin_Pending_Approval_RecyclerAdapter.ItemClickListener
        , Admin_DialogSelect_PendingDetails.DialogClickListener {

    DatabaseReference mFireBaseReference, mFireBaseReferencePendingApproval;
    ProgressDialog progressDialog;
    List<Admin_Data_PendingApproval> mAdminPendingApprovalData = new ArrayList<>(); //Store pending approval data
    HashMap<String, Booking> mAdminUpdateData = new HashMap<>();
    HashMap<String,Boolean> mAllDateRoomsAvailabilityCount ;
    ArrayList<String> mRoomName ;

    Boolean flag = false;

    // Context context ;
    RecyclerView recyclerView;
    Admin_Pending_Approval_RecyclerAdapter adapter;
    Admin_Pending_Approval context;
    String fromDate;
    String requestId;
    int count = 0;
    int countcount = 0;
    List<String> mDateList ;
    Boolean exitFlag = true ;
    String from_date;
    String to_date;
    Date mToDate;
    ArrayList mFirebaseDateList ;

    FirebaseDatabase mDatabase ;
    Spinner spinnerOne ;
    Spinner spinnerTwo ;
    public List<String> spinnerItemsList ;

    public List<String> spinnerItemsList2 ;
    ArrayAdapter<String> spinnerArrayAdapter2 ;
    ArrayAdapter<String> spinnerArrayAdapter ;


    String allocatedRoom1 ;
    String allocatedRoom2 ;
    int i ;


    public  String MapRoom(String t)
    {
        String type = "";

        if (t.contains("bh")) type = "Boys' Hostel";
        if (t.contains("gh")) type = "Girls' Hostel";
        if (t.contains("frr")) type = "Faculty Rooms";
        if (t.contains("frf")) type = "Faculty Flat";


        return type ;

    }


    public  String MapPrice(String t)
    {
        String price = "";

        if (t.contains("bh")) price = "1500";
        if (t.contains("gh")) price = "1500";
        if (t.contains("frr")) price = "1500";
        if (t.contains("frf")) price = "2500";


        return price ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_pending_approval);

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        //new getPendingApproval().execute();

        try
        {
            mDatabase = FirebaseDatabase.getInstance() ;
        }catch (Exception e)
        {
            e.printStackTrace();
        }




//****************************************************************************************************


        // TODO : Change reference here
        mFireBaseReference = FirebaseDatabase.getInstance().getReference("/");
        //mFireBaseReference = FirebaseDatabase.getInstance().getReference("/");
        final ArrayList<Integer> mGuestRooms = new ArrayList<>(); //Store rooms


// ***************************************Value listener in pending_requests/pending_Approval ****************************************

        mFireBaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                HashMap<String, String> mBookingIds = new HashMap<>();

                //mTextViewPendingApproval.setText(dataSnapshot.getChildrenCount()+" Requests");
                //To clear Pending data
                //mAdminPendingApprovalData.clear();
                Log.i("DBSnapshot Value", dataSnapshot.child("pending_requests").getValue().toString());

                for (DataSnapshot val : dataSnapshot.getChildren()) {

                    if (val.getKey().equalsIgnoreCase("pending_requests")) {
                        for (DataSnapshot pend_approvalsnapshot : val.getChildren()) {
                            if (pend_approvalsnapshot.getKey().equalsIgnoreCase("pending_approval")) {
                                for (DataSnapshot allreq : pend_approvalsnapshot.getChildren()) {
                                    count++;
                                    Log.i("count", "pending approval" + count);

                                    Log.i("FinalClassDataAdapter", "Children Count " + String.valueOf(dataSnapshot.getChildrenCount()));
                                    Log.i("request", allreq.getKey());
                                    Log.i("request", allreq.child("from_date").getValue().toString());
                                    //requestId = val.child("2018-04-27").getValue().toString();
                                    //Log.i("request",val.child())

                                    requestId = allreq.getKey();
                                    fromDate = allreq.child("from_date").getValue().toString();
                                    mBookingIds.put(requestId, fromDate);

                                    Log.i("FromDate", "Before Listener" + requestId + " " + fromDate);
                                }
                            }


                        }

                    }
                }

                Log.i("Hashmap", mBookingIds.toString());

                Booking mAdminBooking;
                for (DataSnapshot val2 : dataSnapshot.getChildren()) {
                    if (val2.getKey().equalsIgnoreCase("bookings_final")) {
                        Log.i("enter1", "Bookings Final Entered");
                        for (DataSnapshot snapshot1 : val2.getChildren()) {
                            Log.i("enter2", snapshot1.getChildren().toString());
                            for (DataSnapshot snp2 : snapshot1.getChildren()) {
//                                    for (Map.Entry<String, String> entry : mBookingIds.entrySet())
//                                    {
                                //Log.i("dates", entry.getKey() + " " + entry.getValue());
                                Log.i("enter2", snp2.getKey() + "  ====? " + mBookingIds.toString());
                                if (mBookingIds.keySet().contains(snp2.getKey())) {
                                    Log.i("True Check", snp2.getKey() + "  ====? " + mBookingIds.toString());
                                    //mAdminPendingApprovalDataRoomData.clear();
                                    mBookingIds.get(snp2.getKey());


                                    countcount++;
                                    Log.i("count", "booking's final" + countcount);

                                    Character gender;
                                    int intMales = 0;
                                    int intFemales = 0;
                                    String reqID; //Done
                                    String date; //Done
                                    String type; //Done
                                    String fundedBy = null; //Done
                                    String purpose_of_visit; //Done
                                    String males;  //Done
                                    String females;  //Done
                                    String projectName; //Done
                                    String TotalPrice;
                                    String guest1 = null;
                                    String guest2 = null;
                                    String preference = null;
                                    boolean flagRoomtest = true;
                                    //Entry found
                                    //Log.i("reqId",dataSnapshot.child(entry.getValue()).child(entry.getKey()).getKey());

                                    mAdminBooking = snp2.getValue(Booking.class);
                                    if (mAdminBooking != null) {
                                        Log.i("datestemp", mAdminBooking.timestamp + "  " + mAdminBooking.total_booking_price); //Working


                                        //Assigning AdminDataPendingApproval********************************************
                                        reqID = (snp2.getKey());
                                        date = mAdminBooking.getFrom_date() + " " + mAdminBooking.getTo_date();
                                        type = mAdminBooking.getRequest_type_personal_or_official();
                                        TotalPrice = mAdminBooking.getTotal_booking_price();

                                        if (type.equals("Personal"))
                                            fundedBy = type;
                                        else {
                                            if (mAdminBooking.getFundedby_project_pname() != null)
                                                fundedBy = mAdminBooking.getFundedby_project_pname();
                                            else if (mAdminBooking.getFundedby_institute_details() != null)
                                                fundedBy = mAdminBooking.getFundedby_institute_details();
                                        }

                                        purpose_of_visit = mAdminBooking.getPurpose_of_visit();
                                        projectName = mAdminBooking.getFundedby_project_pname();
                                        // mAdminPendingApprovalDataRoomData.clear();
                                        mGuestRooms.clear();

                                        List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData = new ArrayList<>(); // Store pending approval roomdata

                                        //Boolean flagRoomtest = true;
                                        if (mAdminBooking.getGuests().size() > 0) {

                                            for (int i = 0; i < mAdminBooking.guests.size(); i++) {
                                                Admin_Data_PendingApproval_RoomData check;
                                                if (mGuestRooms.contains(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id))) {

                                                    flagRoomtest = false;
                                                    gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                                    if (gender == 'F') intFemales += 1;
                                                    else intMales += 1;
                                                    guest2 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                                    Log.i("Tag1", guest1 + " " + guest2 + " " + preference);
                                                    check = new Admin_Data_PendingApproval_RoomData(guest1, guest2, preference);
                                                    mAdminPendingApprovalDataRoomData.add(check);
                                                    guest2 = null;
                                                    //Log.i("insideTag","i = "+i+" inside same room");
                                                } else {
                                                    if (i > 0 && i < mAdminBooking.getGuests().size() - 1 && flagRoomtest == true) {
                                                        //Log.i("insideTag","i = "+i+" inside middle loop");
                                                        Log.i("Tag2", guest1 + " null " + preference);
                                                        check = new Admin_Data_PendingApproval_RoomData(guest1, null, preference);
                                                        mAdminPendingApprovalDataRoomData.add(check);
                                                    }
                                                    //Log.i("insideTag", "i = " + i + " inside else loop");
                                                    mGuestRooms.add(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id));

                                                    gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                                    if (gender == 'F') intFemales += 1;
                                                    else intMales += 1;
                                                    guest1 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                                    preference = mAdminBooking.getGuests().get(i).getPrefered_location();
                                                    flagRoomtest = true;
                                                    if (i == mAdminBooking.getGuests().size() - 1) {
                                                        Log.i("Tag3", guest1 + " null " + preference);
                                                        check = new Admin_Data_PendingApproval_RoomData(guest1, null, preference);
                                                        mAdminPendingApprovalDataRoomData.add(check);
                                                    }
                                                }
                                            }//End of for loop iterating on guest size
                                        }//End of if loop checking guest size > 0

                                        males = String.valueOf(intMales);
                                        females = String.valueOf(intFemales);
                                        Log.i("Tag4", males + " " + females);
                                        Log.i("Tag5 Final Class Data", reqID);

                                        for (int i = 0; i < mAdminPendingApprovalDataRoomData.size(); i++) {
                                            Log.i("Tag6 RoomData", "requestr id :" + reqID + "\n" + mAdminPendingApprovalDataRoomData.get(i).guest1 + "\n"
                                                    + mAdminPendingApprovalDataRoomData.get(i).guest2 + "\n" +
                                                    mAdminPendingApprovalDataRoomData.get(i).preference);
                                        }

                                        Log.i("Tag 7 FinalClassData", reqID + "\n" + date + "\n" + type + "\n" + fundedBy + "\n" + purpose_of_visit + "\n" + males + "\n" + females);
                                        Log.i("Tag 8 FinalClassData", "Pending approval" + requestId + " " + mAdminPendingApprovalDataRoomData.toString());
                                        mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID, date, type, fundedBy, purpose_of_visit, males, females, projectName, TotalPrice, mAdminPendingApprovalDataRoomData));

                                    }

                            /*Log.i("faculty", "Institute: " + mAdminBooking.getFundedby_institute_details() + "\n" +
                                    "Investigator: " + mAdminBooking.getFundedby_project_pinvestigator() + "\n" +
                                    "Project Name: " + mAdminBooking.getFundedby_project_pname());*/


                                }//End of pending approval id in dataSnapshot
//                                    } //End of for loop iterating in mBookingsId

                            }
                        }

                    }
                }

                Log.i("ListsPendingAppData", mAdminPendingApprovalData.toString());
                if (adapter != null) {
                    Log.i("FinalClassDataAdapter", "SizeMain" + mAdminPendingApprovalData.size());
                    for (int i = 0; i < mAdminPendingApprovalData.size(); i++) {
                        Log.i("FinalClassDataAdapter", "Approval List data" +
                                mAdminPendingApprovalData.get(i).getReqID() + "\n"
                                + mAdminPendingApprovalData.get(i).getDate() + "\n"
                                + mAdminPendingApprovalData.get(i).getType() + "\n"
                                + mAdminPendingApprovalData.get(i).getFundedBy() + "\n"
                                + mAdminPendingApprovalData.get(i).getReason() + "\n"
                                + mAdminPendingApprovalData.get(i).getMales() + "\n"
                                + mAdminPendingApprovalData.get(i).getFemales() + "\n"
                                + mAdminPendingApprovalData.get(i).getProjectName() + "\n");

                        int size = mAdminPendingApprovalData.get(i).getRoomsData().size();
                        Log.i("FinalClassDataAdapter", "SizeMainInner" + String.valueOf(size));
                        for (int j = 0; j < size; j++) {
                            Log.i("FinalClassDataAdapter", mAdminPendingApprovalData.get(i).getRoomsData().get(j).guest1 + "\n" +
                                    mAdminPendingApprovalData.get(i).getRoomsData().get(j).guest2 + "\n" +
                                    mAdminPendingApprovalData.get(i).getRoomsData().get(j).preference);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }


            }

            private void checkPending(DataSnapshot snapshot1, HashMap<String, String> mBookingIds, List<Admin_Data_PendingApproval> mAdminPendingApprovalData, List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//****************************************************************************************************************************


//******************************Bookings_final reference*******************************************************************

        mFireBaseReferencePendingApproval = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL);

        mFireBaseReferencePendingApproval.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Iterate over all booking dates

                //*****************************



                /*Log.i("request", "inside bookings final" + dataSnapshot.getKey());*/


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


/*
                if (dataSnapshot.getValue() != null) {
                    Log.i("request", "inside bookings final datasnapshot" + dataSnapshot.getKey());
                    boolean flagRoomtest = true;
                    mAdminBooking = dataSnapshot.getValue(Booking.class);

                    Log.i("faculty", "Institute: " + mAdminBooking.getFundedby_institute_details() + "\n" +
                            "Investigator: " + mAdminBooking.getFundedby_project_pinvestigator() + "\n" +
                            "Project Name: " + mAdminBooking.getFundedby_project_pname());
                    reqID = dataSnapshot.getKey();
                    date = mAdminBooking.getFrom_date() + "-" + mAdminBooking.getTo_date();

                    Log.i("FromDate", dataSnapshot.getKey() + fromDate);
                    Log.i("FromDate", dataSnapshot.getKey() + date);
                    type = mAdminBooking.getRequest_type_personal_or_official();
                    if (type.equals("Personal"))
                        fundedBy = type;
                    else {
                        if (mAdminBooking.getFundedby_project_pname() != null)
                            fundedBy = mAdminBooking.getFundedby_project_pname();
                        else if (mAdminBooking.getFundedby_institute_details() != null)
                            fundedBy = mAdminBooking.getFundedby_institute_details();
                    }

                    reason = mAdminBooking.getPurpose_of_visit();
                    projectName = mAdminBooking.getFundedby_project_pname();
                    mAdminPendingApprovalDataRoomData.clear();

                    if (mAdminBooking.getGuests().size() > 0) {
                        for (int i = 0; i < mAdminBooking.guests.size(); i++) {
                            if (mGuestRooms.contains(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id))) {
                                flagRoomtest = false;
                                gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                if (gender == 'F') intFemales += 1;
                                else intMales += 1;
                                guest2 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                Log.i("insideTag", guest1 + " " + guest2 + " " + preference);
                                mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, guest2, preference));
                                guest2 = null;
                                //Log.i("insideTag","i = "+i+" inside same room");
                            } else {
                                if (i > 0 && i < mAdminBooking.getGuests().size() - 1 && flagRoomtest == true) {
                                    //Log.i("insideTag","i = "+i+" inside middle loop");
                                    Log.i("insideTag", guest1 + " null " + preference);
                                    mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, null, preference));
                                }
                                //Log.i("insideTag", "i = " + i + " inside else loop");
                                mGuestRooms.add(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id));

                                gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                if (gender == 'F') intFemales += 1;
                                else intMales += 1;
                                guest1 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                preference = mAdminBooking.getGuests().get(i).getPrefered_location();
                                flagRoomtest = true;
                                if (i == mAdminBooking.getGuests().size() - 1) {
                                    Log.i("insideTag", guest1 + " null " + preference);
                                    mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, null, preference));
                                }
                            }
                        }
                    }
                    males = String.valueOf(intMales);
                    females = String.valueOf(intFemales);
                    Log.i("insideTag", males + " " + females);
                    Log.i("Final Class Data", reqID);
                    for (int i = 0; i < mAdminPendingApprovalDataRoomData.size(); i++) {
                        Log.i("RoomData", "requestr id :" + reqID + "\n" + mAdminPendingApprovalDataRoomData.get(i).guest1 + "\n"
                                + mAdminPendingApprovalDataRoomData.get(i).guest2 + "\n" +
                                mAdminPendingApprovalDataRoomData.get(i).preference);
                    }

                    Log.i("FinalClassData", reqID + "\n" + date + "\n" + type + "\n" + fundedBy + "\n" + reason + "\n" + males + "\n" + females);
                    Log.i("FinalClassData", "Pending approval" + requestId + " " + mAdminPendingApprovalDataRoomData.toString());
                    mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID, date, type, fundedBy, reason, males, females, projectName, mAdminPendingApprovalDataRoomData));

                    //mAdminPendingApprovalDataRoomData.clear();
                }*/


//******************************* Database(Kd) **************************************************

        //**********************************************************************************************

        /*
        List<Admin_Data_PendingApproval> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_PendingApproval()) ;
        }*/


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.i("CheckData", mAdminPendingApprovalData.toString());
        adapter = new Admin_Pending_Approval_RecyclerAdapter(this, mAdminPendingApprovalData);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);

        //progressDialog.dismiss();
    }
    //********************************Async task**********************************************

    @SuppressLint("StaticFieldLeak")
    /*private class getPendingApproval extends AsyncTask<String, String, String> {

        List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData = new ArrayList<>(); // Store pending approval roomdata

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);

            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
adapter = new Admin_Pending_Approval_RecyclerAdapter(context, mAdminPendingApprovalData);

            adapter.setClickListener(context);

            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            // flag=true ;
        }

        @Override
        protected String doInBackground(String... strings) {
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("pending_requests/pending_approval");
            final ArrayList<Integer> mGuestRooms = new ArrayList<>(); //Store rooms


            mFireBaseReference.addValueEventListener(new ValueEventListener() {
                String fromDate;
                String requestId;
                DatabaseReference mFireBaseReferencePendingApproval;

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //mTextViewPendingApproval.setText(dataSnapshot.getChildrenCount()+" Requests");

                    //To clear Pending data
                    mAdminPendingApprovalData.clear();
                    mAdminPendingApprovalDataRoomData.clear();
                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                        Log.i("request", val.getKey());
                        Log.i("request", val.child("from_date").getValue().toString());
                        //requestId = val.child("2018-04-27").getValue().toString();
                        //Log.i("request",val.child())

                        requestId = val.getKey();
                        fromDate = val.child("from_date").getValue().toString();

                        //mFireBaseReferencePendingApproval = FirebaseDatabase.getInstance().getReference("bookings_final/" + fromDate + "/" + requestId);
                        mFireBaseReferencePendingApproval = FirebaseDatabase.getInstance().getReference("bookings_final/" + fromDate + "/" + requestId);

                        mFireBaseReferencePendingApproval.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                mGuestRooms.clear();
                                Character gender;
                                int intMales = 0;
                                int intFemales = 0;
                                String reqID; //Done
                                String date; //Done
                                String type; //Done
                                String fundedBy = null; //Done
                                String reason; //Done
                                String males;  //Done
                                String females;  //Done
                                String projectName; //Done
                                String guest1 = null;
                                String guest2 = null;
                                String preference = null;

                                if (dataSnapshot.getValue() != null) {
                                    boolean flagRoomtest = true;
                                    Booking mAdminBooking = dataSnapshot.getValue(Booking.class);

                                    Log.i("faculty", "Institute: " + mAdminBooking.getFundedby_institute_details() + "\n" +
                                            "Investigator: " + mAdminBooking.getFundedby_project_pinvestigator() + "\n" +
                                            "Project Name: " + mAdminBooking.getFundedby_project_pname());
                                    reqID = requestId;
                                    date = fromDate;
                                    type = mAdminBooking.getRequest_type_personal_or_official();
                                    if (type.equals("Personal"))
                                        fundedBy = type;
                                    else {
                                        if (mAdminBooking.getFundedby_project_pname() != null)
                                            fundedBy = mAdminBooking.getFundedby_project_pname();
                                        else if (mAdminBooking.getFundedby_institute_details() != null)
                                            fundedBy = mAdminBooking.getFundedby_institute_details();
                                    }

                                    reason = mAdminBooking.getPurpose_of_visit();
                                    projectName = mAdminBooking.getFundedby_project_pname();

                                    if (mAdminBooking.getGuests().size() > 0) {
                                        for (int i = 0; i < mAdminBooking.guests.size(); i++) {
                                            if (mGuestRooms.contains(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id))) {
                                                flagRoomtest = false;
                                                gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                                if (gender == 'F') intFemales += 1;
                                                else intMales += 1;
                                                guest2 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                                Log.i("insideTag", guest1 + " " + guest2 + " " + preference);
                                                mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, guest2, preference));
                                                guest2 = null;
                                                //Log.i("insideTag","i = "+i+" inside same room");
                                            } else {
                                                if (i > 0 && i < mAdminBooking.getGuests().size() - 1 && flagRoomtest == true) {
                                                    //Log.i("insideTag","i = "+i+" inside middle loop");
                                                    Log.i("insideTag", guest1 + " null " + preference);
                                                    mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, null, preference));
                                                }
                                                //Log.i("insideTag", "i = " + i + " inside else loop");
                                                mGuestRooms.add(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id));

                                                gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                                if (gender == 'F') intFemales += 1;
                                                else intMales += 1;
                                                guest1 = mAdminBooking.getGuests().get(i).getName() + "(" + gender + ")";
                                                preference = mAdminBooking.getGuests().get(i).getPrefered_location();
                                                flagRoomtest = true;
                                                if (i == mAdminBooking.getGuests().size() - 1) {
                                                    Log.i("insideTag", guest1 + " null " + preference);
                                                    mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1, null, preference));
                                                }
                                            }
                                        }
                                    }
                                    males = String.valueOf(intMales);
                                    females = String.valueOf(intFemales);
                                    //Log.i("insideTag",males + " " +females);
                                    Log.i("FinalClassData", "reqid: " + reqID + "\n" +
                                            "from_date: " + fromDate + "\n" +
                                            "");
                                    Log.i("RoomData", "Pending approval" + mAdminPendingApprovalDataRoomData.toString());
                                    mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID, date, type, fundedBy, reason, males, females, projectName, mAdminPendingApprovalDataRoomData));
                                    // flag=true ;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    //progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //progressDialog.dismiss();
            //adapter.notifyDataSetChanged();
            return null;
        }
    }
*/

    //**************************************************************************************************

    @Override
    public void onItemClick(View view, final int position, List<Admin_Data_PendingApproval_RoomData> adminDataPendingApprovalRoomData) {


        View row = recyclerView.getLayoutManager().findViewByPosition(position);

        LinearLayout midLinearLayout = (LinearLayout) row.findViewById(R.id.midlayout);

        // if the view is already clicked, then hide it and remove all the views attached to it
          if (midLinearLayout.getVisibility() == View.VISIBLE) {
        midLinearLayout.setVisibility(View.GONE);
        midLinearLayout.removeAllViews();
             return;
          }


        final View[] cardview = new View[adminDataPendingApprovalRoomData.size()];

        for (i = 0; i < adminDataPendingApprovalRoomData.size(); i++)
        {
            Admin_Data_PendingApproval_RoomData roomDetails = adminDataPendingApprovalRoomData.get(i);

            cardview[i] = (View) getLayoutInflater().inflate(R.layout.admin_pending_approval_card_view, null);

            //set TextView from from data

            ((TextView) cardview[i].findViewById(R.id.Room)).setText("Room " + String.valueOf(i));
            ((TextView) cardview[i].findViewById(R.id.GuestType1)).setText(roomDetails.guest1);
            ((TextView) cardview[i].findViewById(R.id.GuestType2)).setText(roomDetails.guest2);
            ((TextView) cardview[i].findViewById(R.id.preference)).setText(roomDetails.preference);
            Button allocateButton = (Button) cardview[i].findViewById(R.id.allocate);
            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            /*********************     listen to the allocate button  *********************/



            final Integer index = i;

            allocateButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Admin_DialogSelect_PendingDetails dialogSelect = new Admin_DialogSelect_PendingDetails();//new Admin_DialogSelect_PendingDetails(cardview[index]) ;

                    // send reference to dialogselect
                    dialogSelect.setCardview(cardview[index], dialogSelect,position , i);
                    // set clicklistener using context of mainactivity
                    dialogSelect.setClickListener(context);
                    // get context from calling activity
                    dialogSelect.show(getSupportFragmentManager(), "123");

                }
            });

            // cardView[i].setLayoutParams(layoutparams);
            // cardView[i].addView(cardview);
            midLinearLayout.addView(cardview[i]);
        }

         midLinearLayout.setVisibility(View.VISIBLE);
        //midLinearLayout.animate().translationY(midLinearLayout.getHeight());
        //rel.setVisibility(View.VISIBLE);
        // notifyDatasetChanged() ;

    }

//*****************************************************  Accept button Click ******************************************************

    @Override
    public void onAcceptButtonClick(View v, int position) {

        DatabaseReference pending = FirebaseDatabase.getInstance().getReference(MainActivity.PENDING_REQUESTS + "/" + MainActivity.PENDING_APPROVAL);
        // Log.i("Pending_id",mAdminPendingApprovalData.get(position).getReqID());



        //Key removed from pending_approval
        pending.child(mAdminPendingApprovalData.get(position).getReqID()).getRef().removeValue();
        //Add females and males
        int no_of_persons = 0;
        no_of_persons = Integer.parseInt(mAdminPendingApprovalData.get(position).getMales()) + Integer.parseInt(mAdminPendingApprovalData.get(position).getFemales());

        Log.i("Pending_id", String.valueOf(no_of_persons));
        Log.i("Pending_id", mAdminPendingApprovalData.get(position).getTotalPrice());
        Log.i("Pending_id", String.valueOf(mAdminPendingApprovalData.get(position).getRoomsData().size()));
        Log.i("Pending_id", mAdminPendingApprovalData.get(position).getDate().split(" ")[0]);

        UpdateVerifyPayment updatePayment = new UpdateVerifyPayment(String.valueOf(no_of_persons), String.valueOf(mAdminPendingApprovalData.get(position).getRoomsData().size()),
                mAdminPendingApprovalData.get(position).getDate().split(" ")[0], mAdminPendingApprovalData.get(position).getTotalPrice());

        pending = FirebaseDatabase.getInstance().getReference(MainActivity.PENDING_REQUESTS + "/" + MainActivity.VERIFY_PAYMENT);

        pending.child(mAdminPendingApprovalData.get(position).getReqID()).setValue(updatePayment);
        //  TODO Hide pane

        FirebaseUser mFirebaseUser;
        String userEmail = null;
        try {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mFirebaseUser != null) {
                userEmail = mFirebaseUser.getEmail();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //Log.i("Email",);
        String email = userEmail.replace("@iiitd.ac.in", "");
        Log.i("Data", email);
        pending = FirebaseDatabase.getInstance().getReference(MainActivity.USER + "/" + email);
        try {
            pending.child(mAdminPendingApprovalData.get(position).getReqID()).child("status").setValue("pending_payment");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String date = mAdminPendingApprovalData.get(position).getDate().split(" ")[0];
        pending = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL + "/" + date + "/" + mAdminPendingApprovalData.get(position).getReqID());

        try {
            pending.child("booking_status").setValue("pending_payment");
        } catch (Exception e) {
        }
        //if (pending.child(mAdminPendingApprovalData.get(position).getReqID()));


        mAdminPendingApprovalData.remove(position) ;
        adapter.notifyDataSetChanged();

    }



//***********************************************  Accept button click end  *******************************************************

//****************************************************  Reject button click ************************************************************

    @Override
    public void onRejectButtonClick(View v, int position) {

        DatabaseReference pending = FirebaseDatabase.getInstance().getReference(MainActivity.PENDING_REQUESTS + "/" + MainActivity.PENDING_APPROVAL);
        try {
            pending.child(mAdminPendingApprovalData.get(position).getReqID()).getRef().removeValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        //No verify payment
        //  TODO Hide pane

        FirebaseUser mFirebaseUser;
        String userEmail = null;
        try {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mFirebaseUser != null) {
                userEmail = mFirebaseUser.getEmail();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //Log.i("Email",);
        String email = userEmail.replace("@iiitd.ac.in", "");
        Log.i("Data", email);
        pending = FirebaseDatabase.getInstance().getReference(MainActivity.USER + "/" + email);
        try {
            pending.child(mAdminPendingApprovalData.get(position).getReqID()).child("status").setValue("rejected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String date = mAdminPendingApprovalData.get(position).getDate().split(" ")[0];
        pending = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL + "/" + date + "/" + mAdminPendingApprovalData.get(position).getReqID());

        try {
            pending.child("booking_status").setValue("rejected");
        } catch (Exception e) {
        }
        //if (pending.child(mAdminPendingApprovalData.get(position).getReqID()));
    }

//**********************************************   Reject button click end   ********************************************************SSS
    /***********       DIALOG BOX OPERATIONS   ************************/

    @Override

    public void setSpinner(RadioGroup r , RadioButton one , RadioButton two , Spinner spinner1 , Spinner spinner2 , final int position , View v)
    {




        // TODO Receive rooms with price in hashmap
        // TODO range query between dates
        // TODO write operations in database


        this.spinnerOne = v.findViewById(R.id.spinner1);
        this.spinnerTwo = v.findViewById(R.id.spinner2);
        if (one.isChecked())
        {

            /*********** HIDE LOWER VIEWS      ********************/
            spinnerTwo.setVisibility(View.INVISIBLE);
            v.findViewById(R.id.guest2_dialog).setVisibility(View.INVISIBLE);
            from_date = mAdminPendingApprovalData.get(position).getDate().split(" ")[0];
            to_date = mAdminPendingApprovalData.get(position).getDate().split(" ")[1];

            //***********************************   Range Query inside One room Dialog group *****************************************************



 /*================================   KULDEEP' CODE ==========================================*/


/*

 DatabaseReference mDatabaseReference ;
            final ArrayList<String> mRoomName = new ArrayList<>();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("room_details");
            mRoomName.clear();

            mDatabaseReference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                        if (dataSnapshot.getValue() != null) {
                            //Store list of all rooms
                            mRoomName.add(String.valueOf(val.child("id").getValue()));
                            Log.i("status", String.valueOf(val.child("id").getValue()));
                        }
                    }
*/


            //*************************************Range Query*********************************************


            mAllDateRoomsAvailabilityCount = new HashMap<>();
            mAllDateRoomsAvailabilityCount.put("bh1", true);
            mAllDateRoomsAvailabilityCount.put("bh2", true);
            mAllDateRoomsAvailabilityCount.put("gh1", true);
            mAllDateRoomsAvailabilityCount.put("gh2", true);
            mAllDateRoomsAvailabilityCount.put("frr1", true);
            mAllDateRoomsAvailabilityCount.put("frr2", true);
            mAllDateRoomsAvailabilityCount.put("frr3", true);
            mAllDateRoomsAvailabilityCount.put("frf1", true);
            mAllDateRoomsAvailabilityCount.put("frf2", true);


/*

                    for(int i=0;i<mRoomName.size();i++)
                    {
                        mAllDateRoomsAvailabilityCount.put(mRoomName.get(i),true) ;

                        Log.e("INC",mRoomName.get(i)) ;
                    }
*/

            Date mFromDate, mToDate1;

            List<String> mBookedRoomList;

            mDateList = new ArrayList<>();
            Calendar mCalender;
            mFromDate = mToDate;

            mFirebaseDateList = new ArrayList<>();
//        Log.i("sendToDate",mSendToDate);
//        Log.i("fromDate",mSendFromDate);

            //Reference=> https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
            DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                if (mDatabase != null)
                {
                    mFromDate = mDateFormat.parse(from_date);
                    mToDate1 = mDateFormat.parse(to_date);
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
                    String basetable = "bookings_final";

                    //
                    String strDBAccess = basetable;
                    Log.i("date access", strDBAccess);
                    myRef = mDatabase.getReference(strDBAccess);

                    myRef.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
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


                                                    if (mAdminBooking.guests.size() > 0)
                                                    {
                                                        for (Guest guest1 : mAdminBooking.guests)
                                                        {

                                                            Log.i("Check Keys", mAllDateRoomsAvailabilityCount.keySet().toString() + "  =? " + guest1.allocated_room);

                                                            if (mAllDateRoomsAvailabilityCount.keySet().contains(guest1.allocated_room))
                                                            {
                                                                mAllDateRoomsAvailabilityCount.put(guest1.allocated_room, false);

                                                                Log.i("FINAL HASH LOOP",mAllDateRoomsAvailabilityCount.toString());
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

                            int count = 0;
                            for (Map.Entry<String, Boolean> entry : mAllDateRoomsAvailabilityCount.entrySet())
                            {
                                Boolean val = entry.getValue();

                                if (val)
                                {
                                    count += 1;
                                }

                            }
                            spinnerItemsList = new ArrayList<>();

                            spinnerItemsList2 = new ArrayList<>();
                            Log.i("NEW FINAL HASHMAP", mAllDateRoomsAvailabilityCount.toString());

                            for (String key : mAllDateRoomsAvailabilityCount.keySet()) {
                                if (mAllDateRoomsAvailabilityCount.get(key)) {
                                    String roomNo = "Room " + Character.toString(key.charAt(key.length() - 1));
                                    String roomType = MapRoom(key);
                                    String price = MapPrice(key);

                                    spinnerItemsList.add(roomType + " " + roomNo);
                                    spinnerItemsList2.add(roomType + " " + roomNo) ;
                                    Log.i("INCOMING_DATA", key);
                                    //data.add(new Admin_Data_RoomStatus(MapRoom(key),roomNo,color));
                                }
                            }


                            // define spinner adapter

                            spinnerArrayAdapter = new ArrayAdapter<String>(Admin_Pending_Approval.this, android.R.layout.simple_spinner_item, spinnerItemsList);

                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinnerOne.setAdapter(spinnerArrayAdapter);




                            spinnerArrayAdapter2 = new ArrayAdapter<String>(Admin_Pending_Approval.this, android.R.layout.simple_spinner_item, spinnerItemsList2);

                            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinnerTwo.setAdapter(spinnerArrayAdapter2);

/*
                                    if (count > mTotalRooms) {
                                        Log.i("YIPPIE", " ================Rooms can be booked now=======================");

                                    } else {

                                        Toast.makeText(getActivity(), "Specified number of rooms not available for given dates.", Toast.LENGTH_SHORT).show();

                                    }*/

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            }





            spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {

                    /**************     ArrayList copy  ******************/

                    spinnerItemsList2 = new ArrayList<>(spinnerItemsList);


                    /**************     end ArrayList copy  ******************/

                    allocatedRoom1 = spinnerItemsList.get(position);
                    spinnerItemsList2.remove(position) ;

                    spinnerArrayAdapter2 = new ArrayAdapter<String>(Admin_Pending_Approval.this, android.R.layout.simple_spinner_item, spinnerItemsList2);

                    spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerTwo.setAdapter(spinnerArrayAdapter2);
                    spinnerArrayAdapter2.notifyDataSetChanged();


                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });


            spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                     allocatedRoom2 = spinnerItemsList2.get((spinnerTwo.getSelectedItemPosition())) ;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }

        /*==========================  END KULDEEP'S CODE= ==============================================*/

            //********************************* Range query end inside One room Dialog Group ****************************************************


        else {

            //**********************************   Range Query inside TWO   ROOM  Group  *****************************************************
            this.spinnerTwo.setVisibility(View.VISIBLE);
            v.findViewById(R.id.guest2_dialog).setVisibility(View.VISIBLE);











            //*********************************  Range query inside Two room group end  ****************************************************

        }
    }

    @Override
    public void onRadioGroupClicked()
    {
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Admin_Pending_Approval.this,android.R.layout.simple_spinner_item,spinnerItemsList);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerOne.setAdapter(spinnerArrayAdapter);

    }


    @Override
    public void onDialogClick(View cardview, int position , int roomIndex)
    {
        // use this method on Dialog clicks
        // Log.e("TOAST",cardview.toString(),null);


        Log.i("DIALOG DATA : ",allocatedRoom1+" || "+allocatedRoom2) ;
        mAdminPendingApprovalData.get(position)
                .getRoomsData().get(roomIndex-1).setGuest1AllocatedRoom(allocatedRoom1);

        mAdminPendingApprovalData.get(position)
                .getRoomsData().get(roomIndex-1).setGuest2AllocatedRoom(allocatedRoom2);





        // Do when return from dialog

    }

    public static class UpdateVerifyPayment implements Serializable
    {
        String no_of_persons;
        String no_of_rooms;
        String raised_on;
        String total_price;

        public UpdateVerifyPayment(String no_of_persons, String no_of_rooms, String raised_on, String total_price) {
            this.no_of_persons = no_of_persons;
            this.no_of_rooms = no_of_rooms;
            this.raised_on = raised_on;
            this.total_price = total_price;
        }
    }

}
