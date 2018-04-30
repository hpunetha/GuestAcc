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


        new getStatusForToday().execute();
        new getPendingApproval().execute();
        new getVerifyPayment().execute();
        new getFacultyRequest().execute();

        progressDialog.dismiss();

        //Status for Today
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView1);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminStatusForTodayIntent = new Intent(getActivity(), Admin_StatusForToday_MainScreen.class);
                startActivity(adminStatusForTodayIntent);
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
        @Override
        protected String doInBackground(String... strings) {
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("pending_requests/pending_approval");
            mFireBaseReference.addValueEventListener(new ValueEventListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mTextViewPendingApproval.setText(dataSnapshot.getChildrenCount() + " Requests");
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

    @SuppressLint("StaticFieldLeak")
    private class getStatusForToday extends AsyncTask<String, String, String> {
        DatabaseReference mDatabaseReference, mDatabaseStatusForToday;
        HashSet<String> mRoomName = new HashSet<>();
        HashSet<String> mRoomNameNotAvailable = new HashSet<>();
        //        Booking mStatusForTodayBooking;
        int mRoomSize;
        String PENDING_APPROVAL = "pending_approval";
        String PENDING_PAYMENT = "pending_payment";
        String COMPLETED = "completed";
        String CANCELLED = "cancelled";

        @Override
        protected String doInBackground(String... strings) {

            //https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("room_details");


            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mRoomName.clear();
                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                        //Store list of all rooms
                        mRoomName.add(String.valueOf(val.child("id").getValue()));
                        Log.i("status", String.valueOf(val.child("id").getValue()));
                    }
                    mRoomSize = mRoomName.size();
                    Log.i("Total Rooms", String.valueOf(mRoomSize));
                    Log.i("Total Rooms", mRoomName.toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Log.i("Date", date);
            mDatabaseStatusForToday = FirebaseDatabase.getInstance().getReference("bookings_final/" + date);
            mDatabaseStatusForToday.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mRoomNameNotAvailable.clear();
                    Log.i("Date", String.valueOf(dataSnapshot.getChildrenCount()));

                    for (DataSnapshot val : dataSnapshot.getChildren()) {
                        if (val.getValue() != null) {
                            Log.i("Snapshot", val.toString());

                            Booking mStatusForTodayBooking = val.getValue(Booking.class);

                            if (mStatusForTodayBooking != null) {
                                Log.i("Status", mStatusForTodayBooking.getBooking_status());
                                if (mStatusForTodayBooking.getBooking_status().equals(COMPLETED)) {
                                    for (int i = 0; i < mStatusForTodayBooking.getGuests().size(); i++) {
                                        if (mStatusForTodayBooking.getGuests().get(i).getAllocated_room() != null) {
                                            Log.i("Check", "reached till here");
                                            Log.i("Data", mRoomName.toString());
                                            //mRoomName.remove(mStatusForTodayBooking.getGuests().get(i).getAllocated_room());
                                            mRoomNameNotAvailable.add(mStatusForTodayBooking.getGuests().get(i).getAllocated_room());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Log.i("Checked", "Checking");
                    Log.i("TodayData", mRoomSize + "\t" + mRoomName.size());
                    mTextViewBookedRooms.setText("Booked Rooms: " + mRoomNameNotAvailable.size());
                    mTextViewAvailableRooms.setText("Available Rooms: " + (mRoomSize - mRoomNameNotAvailable.size()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}
