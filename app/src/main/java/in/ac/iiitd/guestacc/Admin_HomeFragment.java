package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by kd on 15/4/18.
 */

public class Admin_HomeFragment extends Fragment {

    CardView adminHomeCardView;
    TextView mTextViewBookedRooms, mTextViewAvailableRooms, mTextViewFacultyRequest, mTextViewVerifyPayment, mTextViewPendingApproval;
    ProgressDialog progressDialog;



    DatabaseReference mFireBaseReference;
    //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminHomeView = inflater.inflate(R.layout.fragment_admin_home, container, false);

        mTextViewBookedRooms = (TextView) adminHomeView.findViewById(R.id.adminHomeBookedRooms);
        mTextViewAvailableRooms = (TextView) adminHomeView.findViewById(R.id.adminRoomsAvailable);
        mTextViewFacultyRequest = (TextView) adminHomeView.findViewById(R.id.adminHomeFacultyRequest);
        mTextViewVerifyPayment = (TextView) adminHomeView.findViewById(R.id.adminHomeVerifyPayment);
        mTextViewPendingApproval = (TextView) adminHomeView.findViewById(R.id.adminHomePendingApproval);
        //Log.i("Checknull", mTextViewAvailableRooms.getText().toString());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.... Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();


        //new getStatusForToday().execute();
        new getPendingApproval().execute();
        new getVerifyPayment().execute();
        new getFacultyRequest().execute();

        progressDialog.dismiss();
        //TODO GetstatusforToday not working

        //Status for Today
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView1);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent adminStatusForTodayIntent = new Intent(getActivity(), Admin_StatusForToday_MainScreen.class);
                //startActivity(adminStatusForTodayIntent);
            }
        });

        //Pending Approval
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView2);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminPendingApprovalIntent = new Intent(getActivity(), Admin_Pending_Approval.class);
                startActivity(adminPendingApprovalIntent);
            }
        });

        //Verify Payment
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView3);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminValidatePaymentIntent = new Intent(getActivity(), Admin_ValidatePayment.class);
                startActivity(adminValidatePaymentIntent);
            }
        });

        //Faculty Registration Requests
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView4);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Admin_HomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });
        return adminHomeView;
    }

    //**************************************Pending Approval****************************************************

    @SuppressLint("StaticFieldLeak")
    private class getPendingApproval extends AsyncTask<String, String, String> {
        List<Admin_Data_PendingApproval> mAdminPendingApprovalData = new ArrayList<>(); //Store pending approval data
        List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData = new ArrayList<>(); // Store pending approval roomdata

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

                    mTextViewPendingApproval.setText(dataSnapshot.getChildrenCount()+" Requests");

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
                                int intMales=0;
                                int intFemales=0;
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

                                    Log.i("faculty","Institute: " +mAdminBooking.getFundedby_institute_details()+"\n"+
                                                    "Investigator: "+mAdminBooking.getFundedby_project_pinvestigator()+"\n"+
                                                    "Project Name: "+mAdminBooking.getFundedby_project_pname());
                                    reqID = requestId;
                                    date = fromDate;
                                    type = mAdminBooking.getRequest_type_personal_or_official();
                                    if(type.equals("Personal"))
                                        fundedBy = type;
                                    else{
                                        if (mAdminBooking.getFundedby_project_pname()!=null)
                                            fundedBy = mAdminBooking.getFundedby_project_pname();
                                        else if(mAdminBooking.getFundedby_institute_details()!=null)
                                            fundedBy = mAdminBooking.getFundedby_institute_details();
                                    }

                                    reason = mAdminBooking.getPurpose_of_visit();
                                    projectName = mAdminBooking.getFundedby_project_pname();

                                    if (mAdminBooking.getGuests().size() > 0) {
                                        for (int i = 0; i < mAdminBooking.guests.size(); i++) {
                                            if (mGuestRooms.contains(Integer.parseInt(mAdminBooking.guests.get(i).associated_room_id))) {
                                                flagRoomtest = false;
                                                gender = mAdminBooking.getGuests().get(i).getGender().charAt(0);
                                                if (gender=='F') intFemales += 1;
                                                else intMales += 1;
                                                guest2 = mAdminBooking.getGuests().get(i).getName()+"("+gender+")";
                                                Log.i("insideTag", guest1 + " " + guest2 + " " + preference);
                                                mAdminPendingApprovalDataRoomData.add(new Admin_Data_PendingApproval_RoomData(guest1,guest2,preference));
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
                                                if (gender=='F') intFemales += 1;
                                                else intMales += 1;
                                                guest1 = mAdminBooking.getGuests().get(i).getName()+"("+gender+")";
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
                                    //Log.i("RoomData",mAdminPendingApprovalDataRoomData.toString());
                                    mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID,fromDate,type,fundedBy,reason,males,females,projectName,mAdminPendingApprovalDataRoomData));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
    //**************************************Verify Payment****************************************************

    @SuppressLint("StaticFieldLeak")
    private class getVerifyPayment extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("pending_requests/verify_payment");
            mFireBaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mTextViewVerifyPayment.setText(String.valueOf(dataSnapshot.getChildrenCount()) + " Requests");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    //**************************************Faculty Requests****************************************************

    @SuppressLint("StaticFieldLeak")
    private class getFacultyRequest extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("join_requests");
            mFireBaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mTextViewFacultyRequest.setText(String.valueOf(dataSnapshot.getChildrenCount()) + " Requests");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    //************************************Get status for today****************************************************
    /*

//Before getStatusForToday

    @SuppressLint("StaticFieldLeak")
    private class getStatusForToday extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            //mFireBaseReference = FirebaseDatabase.getInstance().getReference("room_details");
            //date = "2018-03-18";
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("bookings/" + date);

            final HashSet<String> bookingsRooms = new HashSet<>(Arrays.asList("bh1", "bh2", "gh1", "gh2", "fr1", "fr2", "ff1", "ff2"));
            final int mRoomSize = bookingsRooms.size();


            mFireBaseReference.addValueEventListener(new ValueEventListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Log.i("DataSnapshot", String.valueOf(dataSnapshot.getChildrenCount()));

                    Log.i("Database", dataSnapshot.getKey());
                    Log.i("Database", String.valueOf(dataSnapshot.getChildrenCount()));
                    if (dataSnapshot.getChildrenCount() != 0) {
                        for (DataSnapshot bookingData : dataSnapshot.getChildren()) {
                            for (DataSnapshot roomBookedClient : bookingData.child("rooms").getChildren()) {
                                bookingsRooms.remove(roomBookedClient.getKey());
                            }
                        }
                        //Log.i("Checknull",mTextViewAvailableRooms.getText().toString());

                        mTextViewBookedRooms.setText("Booked Rooms: " + String.valueOf(mRoomSize - bookingsRooms.size()));
                        mTextViewAvailableRooms.setText("Available Rooms: " + String.valueOf(bookingsRooms.size()));
                        //Log.i("Checknull",mTextViewAvailableRooms.getText().toString());

                    } else {
                        //Log.i("Checknull",mTextViewAvailableRooms.getText().toString());

                        mTextViewBookedRooms.setText("Booked Rooms: " + String.valueOf(mRoomSize - bookingsRooms.size()));
                        mTextViewAvailableRooms.setText("Available Rooms: " + String.valueOf(bookingsRooms.size()));
                        Log.i("Checknull", mTextViewAvailableRooms.getText().toString());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

*/

    private class getStatusForToday extends AsyncTask<String, String, String>{
        DatabaseReference mDatabaseReference;
        ArrayList<String> mRoomName = new ArrayList<>();
        ArrayList<String> mRoomNameNotAvailable = new ArrayList<>();
        Booking mStatusForTodayBooking;
        int mRoomSize;
        String PENDING_APPROVAL = "pending_approval";
        String PENDING_PAYMENT= "pending_payment";
        String COMPLETED = "completed";
        String CANCELLED = "cancelled";

        @Override
        protected String doInBackground(String... strings) {

            //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("room_details");
            mRoomNameNotAvailable.clear();
            mRoomName.clear();


            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot val:dataSnapshot.getChildren()){
                        //Store list of all rooms
                        mRoomName.add(String.valueOf(val.child("id").getValue()));
                        Log.i("status",String.valueOf(val.child("id").getValue()));
                    }
                    mRoomSize = mRoomName.size();
                    Log.i("Total Rooms", String.valueOf(mRoomSize));
                    Log.i("Total Rooms",mRoomName.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Log.i("Date",date);
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("bookings_final/"+date);
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("Date", String.valueOf(dataSnapshot.getChildrenCount()));
                    if (dataSnapshot.getValue() != null) {
                        mStatusForTodayBooking = dataSnapshot.getValue(Booking.class);

                        if ( mStatusForTodayBooking != null && mStatusForTodayBooking.getBooking_status().equals(COMPLETED)) {
                            for (int i = 0; i < mStatusForTodayBooking.getGuests().size(); i++) {
                                if (mStatusForTodayBooking.getGuests().get(i).getAllocated_room() != null) {
                                    Log.i("Check","reached till here");
                                    mRoomName.remove(mStatusForTodayBooking.getGuests().get(i).getAllocated_room());
                                    //mRoomNameNotAvailable.add(mStatusForTodayBooking.getGuests().get(i).getAssociated_room_id());
                                }
                            }
                        }
                    }
                    mTextViewBookedRooms.setText("Booked Rooms: " + (mRoomSize-mRoomName.size()));
                    mTextViewAvailableRooms.setText("Available Rooms: " + mRoomName.size());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}