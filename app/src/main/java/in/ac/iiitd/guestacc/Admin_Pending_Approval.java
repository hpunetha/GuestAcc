package in.ac.iiitd.guestacc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_Pending_Approval extends AppCompatActivity implements Admin_Pending_Approval_RecyclerAdapter.ItemClickListener
        , Admin_DialogSelect_PendingDetails.DialogClickListener {

    DatabaseReference mFireBaseReference;
    ProgressDialog progressDialog;
    List<Admin_Data_PendingApproval> mAdminPendingApprovalData = new ArrayList<>(); //Store pending approval data
    List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData = new ArrayList<>(); // Store pending approval roomdata

    Boolean flag = false;
    // Context context ;
    RecyclerView recyclerView;
    Admin_Pending_Approval_RecyclerAdapter adapter;
    Admin_Pending_Approval context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_pending_approval);

        context = this;

/*
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();*/
        //new getPendingApproval().execute();
//****************************************************************************************************


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
                //mAdminPendingApprovalData.clear();
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
                            Log.i("request","inside bookings final"+dataSnapshot.getKey());


                            mGuestRooms.clear();
                            Character gender;
                            int intMales = 0;
                            int intFemales = 0;
                            String reqID; //Done
                            String date; //Done
                            String type; //Done
                            String fundedBy = ""; //Done
                            String reason; //Done
                            String males;  //Done
                            String females;  //Done
                            String projectName; //Done
                            String guest1 = "";
                            String guest2 = "";
                            String preference = "";

                            if (dataSnapshot.getValue() != null) {
                                Log.i("request","inside bookings final datasnapshot"+dataSnapshot.getKey());
                                boolean flagRoomtest = true;
                                Booking mAdminBooking = dataSnapshot.getValue(Booking.class);

                                Log.i("faculty", "Institute: " + mAdminBooking.getFundedby_institute_details() + "\n" +
                                        "Investigator: " + mAdminBooking.getFundedby_project_pinvestigator() + "\n" +
                                        "Project Name: " + mAdminBooking.getFundedby_project_pname());
                                reqID = dataSnapshot.getKey();
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
                                //Log.i("insideTag",males + " " +females);
                                Log.i("Final Class Data",reqID);
                                for (int i=0;i<mAdminPendingApprovalDataRoomData.size();i++){
                                    Log.i("Final Class Data","requestr id :"+reqID+"\n"+mAdminPendingApprovalDataRoomData.get(i).guest1+"\n"
                                    +mAdminPendingApprovalDataRoomData.get(i).guest2+"\n"+
                                    mAdminPendingApprovalDataRoomData.get(i).preference);
                                }

                                Log.i("FinalClassData",reqID + "\n" + date + "\n" + type+"\n"+fundedBy+"\n"+reason+"\n"+males+"\n"+females);
                                Log.i("FinalClassData", "Pending approval"+requestId +" "+ mAdminPendingApprovalDataRoomData.toString());
                                mAdminPendingApprovalData.add(new Admin_Data_PendingApproval(reqID, date, type, fundedBy, reason, males, females, projectName, mAdminPendingApprovalDataRoomData));

                                //mAdminPendingApprovalDataRoomData.clear();
                            }
                            if(adapter!=null) {
                                for (int i=0;i<mAdminPendingApprovalData.size();i++){
                                    Log.i("FinalClassDataAdapter","Approval List data"+
                                            mAdminPendingApprovalData.get(i).getReqID()+"\n"
                                            +mAdminPendingApprovalData.get(i).getDate()+"\n"
                                            +mAdminPendingApprovalData.get(i).getType()+"\n"
                                            +mAdminPendingApprovalData.get(i).getFundedBy()+"\n"
                                            +mAdminPendingApprovalData.get(i).getReason()+"\n"
                                            +mAdminPendingApprovalData.get(i).getMales()+"\n"
                                            +mAdminPendingApprovalData.get(i).getFemales()+"\n"
                                            +mAdminPendingApprovalData.get(i).getProjectName());
                                }
                                adapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }
    //********************************Async task**********************************************

    @SuppressLint("StaticFieldLeak")
    private class getPendingApproval extends AsyncTask<String, String, String> {

        List<Admin_Data_PendingApproval_RoomData> mAdminPendingApprovalDataRoomData = new ArrayList<>(); // Store pending approval roomdata

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);

            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            /*adapter = new Admin_Pending_Approval_RecyclerAdapter(context, mAdminPendingApprovalData);

            adapter.setClickListener(context);

            recyclerView.setAdapter(adapter);*/
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


    //**************************************************************************************************

    @Override
    public void onItemClick(View view, int position, List<Admin_Data_PendingApproval_RoomData> adminDataPendingApprovalRoomData) {


        View row = recyclerView.getLayoutManager().findViewByPosition(position);

        LinearLayout midLinearLayout = (LinearLayout) row.findViewById(R.id.midlayout);

        // if the view is already clicked, then hide it and remove all the views attached to it
        if (midLinearLayout.getVisibility() == View.VISIBLE) {
            midLinearLayout.setVisibility(View.GONE);
            midLinearLayout.removeAllViews();
            return;
        }


        final View[] cardview = new View[adminDataPendingApprovalRoomData.size()];

        for (int i = 0; i < adminDataPendingApprovalRoomData.size(); i++) {
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
                    dialogSelect.setCardview(cardview[index], dialogSelect);
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

    @Override
    public void onDialogClick(View cardview, String type) {
        // use this method on Dialog clicks
        // Log.e("TOAST",cardview.toString(),null);


        // Do when return from dialog

    }

    @Override
    public void onButtonClick(View v,int position)
    {
        Log.e("DATABASE",String.valueOf(position)) ;
    }
}
