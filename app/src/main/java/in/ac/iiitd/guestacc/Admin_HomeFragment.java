package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
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

    static List<Admin_Data_PendingApproval> mAdminPendingApprovalData;
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
        mAdminPendingApprovalData = new ArrayList<>();
        Log.i("Checknull",mTextViewAvailableRooms.getText().toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.... Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();


        new getStatusForToday().execute("");
        new getPendingApproval().execute("");
        new getVerifyPayment().execute("");
        new getFacultyRequest().execute("");
        new getProgressLoading().execute("");

        progressDialog.dismiss();

        //Threads for receiving data - admin home screen
        //if (mTextViewAvailableRooms.getText().toString())

        //Status for Today
        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView1);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminStatusForTodayIntent = new Intent(getActivity(),Admin_StatusForToday_MainScreen.class);
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

    private class getProgressLoading extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }
    }

    //**************************************Pending Approval****************************************************

    @SuppressLint("StaticFieldLeak")
    private class getPendingApproval extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            mFireBaseReference = FirebaseDatabase.getInstance().getReference("pending_requests/pending_approval");

            //TODO pending approval data
            mFireBaseReference.addValueEventListener(new ValueEventListener() {
                String fromDate;
                String requestId;
                DatabaseReference mFireBaseReferencePendingApproval;

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mTextViewPendingApproval.setText(String.valueOf(dataSnapshot.getChildrenCount()) + " Requests");
                    //To clear Pending data
                    mAdminPendingApprovalData.clear();
                    for (DataSnapshot val: dataSnapshot.getChildren()){
                        Log.i("request", val.getKey());
                        Log.i("request",val.child("from_date").getValue().toString());
                        //requestId = val.child("2018-04-27").getValue().toString();
                        //Log.i("request",val.child())

                        requestId = val.getKey();
                        fromDate = val.child("from_date").getValue().toString();

                        mFireBaseReferencePendingApproval = FirebaseDatabase.getInstance().getReference("bookings_final/"+fromDate+"/"+requestId);
                        mFireBaseReferencePendingApproval.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String reqID ;
                                String date ;
                                String type ;
                                String fundedBy ;
                                String reason ;
                                String males ;
                                String females ;
                                String projectName ;

                                //Log.i("requestIdkey",dataSnapshot.getKey());
                                //Log.i("requestIdcount", String.valueOf(dataSnapshot.getChildrenCount()));
                                reqID = requestId;
                                date = fromDate;
                                fundedBy = dataSnapshot.child("funded_by").getValue().toString();
                                reason = dataSnapshot.child("purpose_of_visit").getValue().toString();
                                projectName = dataSnapshot.child("fundedby_project_pname").getValue().toString();

                                //mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID,date,type,fundedBy,reason,males,females,projectName));
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

    @SuppressLint("StaticFieldLeak")
    private class getStatusForToday extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            date = "2018-03-18";
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
                        Log.i("Checknull",mTextViewAvailableRooms.getText().toString());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}