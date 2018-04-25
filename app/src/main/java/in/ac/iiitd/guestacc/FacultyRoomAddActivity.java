package in.ac.iiitd.guestacc;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyRoomAddActivity extends AppCompatActivity implements FacultyRoomAddFragment.OnFragmentInteractionListener {

    public final String NEW="0",OLD="1";   //Whether there are old rooms
    public int mFragTag=0;
    //public static int mFragCount=0;
    FragmentManager mFragMan;
    FragmentTransaction mFragTran;
    public static int mTotalPrice;
    public static int mTotalRooms;
    public static int mTotalGuests,mTotalMale,mTotalFemale;

    public static int mFlagApply;

    TextView mTotalRoomsTextView;
    TextView mTotalPriceTextView;


    Button mApplyButton;

    //public static ArrayList<FacultyRoomAddFragment> mAllRoomFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_room_add);
        //mAllRoomFragments = new ArrayList<>();


        mTotalRoomsTextView = (TextView) findViewById(R.id.textViewTotalRooms);
        mTotalPriceTextView = (TextView) findViewById(R.id.textViewTotalPrice);
        mApplyButton =(Button) findViewById(R.id.btnApply);
        mTotalRooms=0;
        mTotalPrice=0;

        mFragMan = getSupportFragmentManager();
        Log.i("Fragments",String.valueOf(mFragMan.getFragments().size()));

        if (savedInstanceState==null) {
            if (mFragMan.getFragments().size() == 0) {

                if (FacultyHomeActivity.mAllRoomsDetails!=null)
                {
                    if (FacultyHomeActivity.mAllRoomsDetails.size()==0) {
                        mFragTag = 0;

                        mFragTran = mFragMan.beginTransaction();
                        mFragTag++;

                        String tag = String.valueOf(mFragTag);
                        String room_name = getResources().getString(R.string.room_details) + " " + tag;
                        FacultyRoomAddFragment mFacultyAddFrag = FacultyRoomAddFragment.newInstance(tag, room_name,NEW);
                        mFragTran.add(R.id.allfragmentsContainer, mFacultyAddFrag, tag);
                        //mAllRoomFragments.add(mFacultyAddFrag);
                        mFragTran.commit();
                    }
                    else
                    {
                        addExistingFragments();
                    }

                }

            }
        }
        else
        {

        }

        Button mAddButton = (Button) findViewById(R.id.btnAdd);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getSupportFragmentManager().getFragments().size()<5) {
                    mFragMan = getSupportFragmentManager();
                    mFragTran = mFragMan.beginTransaction();
                    mFragTag++;

                    String tag = String.valueOf(mFragTag);
                    String room_name = getResources().getString(R.string.room_details) + " " + tag;
                    FacultyRoomAddFragment mNewFacultyAddFrag = FacultyRoomAddFragment.newInstance(tag, room_name,NEW);
                    mFragTran.add(R.id.allfragmentsContainer, mNewFacultyAddFrag, tag);
                   // mAllRoomFragments.add(mNewFacultyAddFrag);
                    mFragTran.commit();


                }
                else
                {
                    Snackbar.make(view, "More than 5 rooms cannot be booked at once", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });


            mApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set result
                    updateRoomsAndPrice(view);

                    if (mFlagApply==0) {
                        Intent resultInt = new Intent();
                        resultInt.putExtra(FacultyHomeActivity.TOTALPRICE, mTotalPrice);
                        resultInt.putExtra(FacultyHomeActivity.TOTALROOMS, mTotalRooms);
                        resultInt.putExtra(FacultyHomeActivity.TOTALMALES, mTotalMale);
                        resultInt.putExtra(FacultyHomeActivity.TOTALFEMALES, mTotalFemale);
                        resultInt.putExtra(FacultyHomeActivity.TOTALGUESTS, mTotalGuests);


                        Log.i("totalprice", String.valueOf(mTotalPrice));
                        Log.i("totalrooms", String.valueOf(mTotalRooms));
                        setResult(Activity.RESULT_OK, resultInt);
                        finish();
                    }
                    else
                    {
                        Snackbar.make(view, "At least one guest must be there in each room", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });



    }

    private void addExistingFragments() {
        Log.i("AddExisting","Fragments Added");
        mFragTag=0;
        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
        {
            String roomTagKey = entry.getKey();
            Log.i("RoomTagKey",roomTagKey);
            RoomItem roomDetails = entry.getValue();
            mFragTran = mFragMan.beginTransaction();
            mFragTag++;
            String tag = roomTagKey;
            String room_name = getResources().getString(R.string.room_details) + " " + tag;
            FacultyRoomAddFragment mFacultyAddFrag = FacultyRoomAddFragment.newInstance(tag, room_name,OLD);
            mFragTran.add(R.id.allfragmentsContainer, mFacultyAddFrag, tag);
            //mAllRoomFragments.add(mFacultyAddFrag);
            mFragTran.commit();
            //mFacultyAddFrag.UpdateOldFragments(roomTagKey);
//            FacultyRoomAddFragment tempFrag = (FacultyRoomAddFragment) getSupportFragmentManager().findFragmentByTag(roomTagKey);
//            tempFrag.();

        }
        updateRoomsAndPrice();
    }

    public void updateRoomsAndPrice()
    {
        mTotalPrice=0;
        mTotalRooms=0;
        mTotalGuests=0;
        mTotalMale=0;
        mTotalFemale=0;

        Log.w("Size allFragments",String.valueOf(getSupportFragmentManager().getFragments().size()));
        //Log.w("Size mAllArrayListFrag",String.valueOf(mAllRoomFragments.size()));

        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
        {
            String roomTagKey = entry.getKey();
            RoomItem roomDetails = entry.getValue();

            mTotalPrice = mTotalPrice + roomDetails.mRoomPrice;
            mTotalRooms = mTotalRooms+1;
            mTotalMale = mTotalMale + roomDetails.mMaleCount;
            mTotalFemale = mTotalFemale + roomDetails.mFemaleCount;

        }
        mTotalGuests = mTotalFemale + mTotalMale;
//
//            mTotalRooms = mAllRoomFragments.size();
//
//            for (FacultyRoomAddFragment frg: mAllRoomFragments)
//            {
//                mTotalPrice = mTotalPrice + frg.mRoomPrice;
//
//            }
//
//
            String totalprice = getResources().getString(R.string.total_price) + " " + String.valueOf(mTotalPrice);
            String totalroom = getResources().getString(R.string.total_rooms) + " " + String.valueOf(mTotalRooms);
            if(totalprice!=null && totalroom!=null) {
                mTotalPriceTextView.setText(totalprice);
                mTotalRoomsTextView.setText(totalroom);
            }





    }

    public void updateRoomsAndPrice(View view)
    {
        mTotalPrice=0;
        mTotalRooms=0;
        mTotalGuests=0;
        mTotalMale=0;
        mTotalFemale=0;
        mFlagApply=0;
        Log.w("Size allFragments",String.valueOf(getSupportFragmentManager().getFragments().size()));
        //Log.w("Size mAllArrayListFrag",String.valueOf(mAllRoomFragments.size()));

        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
        {
            int roomMale=0,roomfemale=0;
            String roomTagKey = entry.getKey();
            RoomItem roomDetails = entry.getValue();

            mTotalPrice = mTotalPrice + roomDetails.mRoomPrice;
            mTotalRooms = mTotalRooms+1;
            mTotalMale = mTotalMale + roomDetails.mMaleCount;
            mTotalFemale = mTotalFemale + roomDetails.mFemaleCount;

            roomMale=roomDetails.mMaleCount;
            roomfemale=roomDetails.mFemaleCount;

            if (roomMale + roomfemale ==0)
            {
                mFlagApply=1;

            }
        }
        mTotalGuests = mTotalFemale + mTotalMale;

        String totalprice = getResources().getString(R.string.total_price) + " " + String.valueOf(mTotalPrice);
        String totalroom = getResources().getString(R.string.total_rooms) + " " + String.valueOf(mTotalRooms);
        mTotalPriceTextView.setText(totalprice);
        mTotalRoomsTextView.setText(totalroom);





    }


    @Override
    public void onBackPressed() {
        HashMap<String,RoomItem> mTempHashMap = new HashMap<>();
        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
        {
            int roomMale=0,roomfemale=0;
            String roomTagKey = entry.getKey();
            RoomItem roomDetails = entry.getValue();


            roomMale=roomDetails.mMaleCount;
            roomfemale=roomDetails.mFemaleCount;

            if (roomMale + roomfemale ==0)
            {
                mTempHashMap.put(roomTagKey,roomDetails);

            }
        }

        for (Map.Entry<String,RoomItem> entry : mTempHashMap.entrySet()) {
            FacultyHomeActivity.mAllRoomsDetails.remove(entry.getKey());
        }

        updateRoomsAndPrice();

        if (FacultyHomeActivity.mAllRoomsDetails.size()>0) {

            Intent resultInt = new Intent();
            resultInt.putExtra(FacultyHomeActivity.TOTALPRICE, mTotalPrice);
            resultInt.putExtra(FacultyHomeActivity.TOTALROOMS, mTotalRooms);
            resultInt.putExtra(FacultyHomeActivity.TOTALMALES, mTotalMale);
            resultInt.putExtra(FacultyHomeActivity.TOTALFEMALES, mTotalFemale);
            resultInt.putExtra(FacultyHomeActivity.TOTALGUESTS, mTotalGuests);


            Log.i("totalprice", String.valueOf(mTotalPrice));
            Log.i("totalrooms", String.valueOf(mTotalRooms));
            setResult(Activity.RESULT_OK, resultInt);
            finish();

        }
        else
        {
            Intent resultInt = new Intent();
            setResult(Activity.RESULT_CANCELED, resultInt);

        }


        super.onBackPressed();
    }

    @Override
    public void onFragmentDelete(String roomTag) {
//        for (FacultyRoomAddFragment frg: mAllRoomFragments)
//        {
//
//            if (frg.mRoomTag.equals(roomTag))
//            {
//
//
//                mAllRoomFragments.remove(mAllRoomFragments.indexOf(frg));
//                break;
//            }
//        }
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(roomTag)).commit();
        FacultyHomeActivity.mAllRoomsDetails.remove(roomTag);
        //initAllIds();         Not working Correctly , hence commented.




        updateRoomsAndPrice();

    }

    private void initAllIds() {
        int index=1;
        for (Map.Entry<String,RoomItem> entry : FacultyHomeActivity.mAllRoomsDetails.entrySet())
        {
            String roomTagKey = entry.getKey();
            RoomItem roomDetails = entry.getValue();
            FacultyHomeActivity.mAllRoomsDetails.get(roomTagKey).id= String.valueOf(index);
            FacultyRoomAddFragment tempFrag = (FacultyRoomAddFragment) getSupportFragmentManager().findFragmentByTag(roomTagKey);
            tempFrag.updateRoomIdsTextView();
            index++;
        }

    }

    @Override
    public void onFragmentDataRequest(String roomTag, String pref, int malecount, int femalecount, int roomtype, int price) {

    }
}
