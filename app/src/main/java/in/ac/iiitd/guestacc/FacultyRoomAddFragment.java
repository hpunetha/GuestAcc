package in.ac.iiitd.guestacc;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FacultyRoomAddFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    public String mRoomTag;
    public String mRoomName;
    public String mSelectedPreference=null;
    public int mMaleCount=0;
    public int mFemaleCount=0;
    public int mRoomType;       // 0-> Room , 1-> Flat
    public int mRoomPrice;
    ArrayAdapter<String> mPrefAdapter;

    public boolean mFacultyFlag=true;
    public String mWhatCanBeBooked;

    private final String FACULTYRESIDENCE ="Faculty Residence";
    private final String BOYSHOSTEL="Boys Hostel";
    private final String GIRLSHOSTEL="Girls Hostel";

    public final String CANBOOKALLLOCATIONS = "fbg";
    public final String CANBOOKBOYSGIRLS = "bg";
    public final String CANBOOKONLYGIRLS = "g";
    public final String CANBOOKONLYBOYS = "b";
    public final String CANBOOKFACULTYBOYS = "fb";
    public final String CANBOOKFACULTYGIRLS = "fg";

    private String mStatusFragment;
    private  TextView mCounterMale;
    private TextView mCounterFemale;
    private TextView mRoomPriceTextView;
    private OnFragmentInteractionListener mListener;
    private TextView mRoomDet;
    private Spinner mPref;
    List<String> mCat;
    public FacultyRoomAddFragment() {
        // Required empty public constructor
    }


    public static FacultyRoomAddFragment newInstance(String param1, String param2,String param3) {
        FacultyRoomAddFragment fragment = new FacultyRoomAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCat = new ArrayList<>();
        if (getArguments() != null) {

            mStatusFragment =(getArguments().getString(ARG_PARAM3));
            mRoomTag = getArguments().getString(ARG_PARAM1);
            mRoomName = getArguments().getString(ARG_PARAM2);

            if (mStatusFragment.equals("0")) {
                RoomItem mNewRoom = new RoomItem();

                mNewRoom.roomTag = mRoomTag = getArguments().getString(ARG_PARAM1);
                mNewRoom.roomName = mRoomName = getArguments().getString(ARG_PARAM2);
                mNewRoom.mRoomType = mRoomType = 0; //default Room
                mNewRoom.mRoomPrice = mRoomPrice = 1500; //default price of room
                mNewRoom.id = mNewRoom.roomTag;
                FacultyHomeActivity.mAllRoomsDetails.put(mNewRoom.roomTag, mNewRoom);
                ((FacultyRoomAddActivity) getActivity()).updateRoomsAndPrice();
            }
            else
            {
                mRoomType = 0;
                mRoomPrice = 1500;

            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vu1 = inflater.inflate(R.layout.fragment_faculty_room_add, container, false);

        mRoomDet = (TextView) vu1.findViewById(R.id.textViewRoomDetails);
        mRoomPriceTextView = (TextView) vu1.findViewById(R.id.textViewRoomPriceDisplay);
        ImageButton mCloseBtn = (ImageButton)vu1.findViewById(R.id.buttonCloseFragment);

        ImageButton mMinusFemale= (ImageButton) vu1.findViewById(R.id.minusFemale);
        ImageButton mPlusFemale = (ImageButton) vu1.findViewById(R.id.plusFemale);
        ImageButton mMinusMale= (ImageButton) vu1.findViewById(R.id.minusMale);
        ImageButton mPlusMale = (ImageButton) vu1.findViewById(R.id.plusMale);

        mCounterMale =(TextView)vu1.findViewById(R.id.countertextMale);
        mCounterFemale = (TextView) vu1.findViewById(R.id.countertextFemale);

        if (mStatusFragment.equals("0")) {
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount = mMaleCount = 0;
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount = mFemaleCount = 0;
        }
        else
        {
            mMaleCount = 0;
            mFemaleCount = 0;
        }

        String mPdisplay = getResources().getString(R.string.price_rs) +" "+ String.valueOf(mRoomPrice);
        mRoomPriceTextView.setText(mPdisplay);

        mCounterFemale.setText(String.valueOf(mFemaleCount));
        mCounterMale.setText(String.valueOf(mMaleCount));

        mMinusFemale.setOnClickListener(this);
        mPlusFemale.setOnClickListener(this);
        mMinusMale.setOnClickListener(this);
        mPlusMale.setOnClickListener(this);


        mPref = (Spinner) vu1.findViewById(R.id.spinnerPreferences);

        Log.i("FragmentType",mStatusFragment);

        if (mStatusFragment.equals("0")) {
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook = mWhatCanBeBooked=  CANBOOKALLLOCATIONS;
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount = mMaleCount = 0;
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount = mFemaleCount = 0;

            addAll(CANBOOKALLLOCATIONS);

//            List<String> categories = new ArrayList<String>();
//            if (mFacultyFlag){categories.add(FACULTYRESIDENCE);}
//            categories.add(BOYSHOSTEL);
//            categories.add(GIRLSHOSTEL);
//
//            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCategories = mCat = categories;
//            mPrefAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//            mPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            mPref.setAdapter(mPrefAdapter);
        }
        else if  (mStatusFragment.equals("1"))
        {

            addFuncOldFrag();

            mMaleCount = 0;
            mFemaleCount = 0;
        }


//
//        List<String> categories = new ArrayList<String>();
//        if (mFacultyFlag){categories.add(FACULTYRESIDENCE);}
//        categories.add(BOYSHOSTEL);
//        categories.add(GIRLSHOSTEL);
//
//
//        ArrayAdapter<String> mPrefAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//        mPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        mPref.setAdapter(mPrefAdapter);



        mPref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).roomPref = mSelectedPreference = adapterView.getItemAtPosition(i).toString();
                    Log.i("Preference Room",mRoomName);
                    Log.i("Selected Preference",mSelectedPreference);
                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrefInt=i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Nothing Selected for",mRoomName);
                //Log.i("Nothing Selected Pref",mSelectedPreference);
            }
        });

        mCloseBtn.setOnClickListener(this);

        if (mStatusFragment.equals("0")) {
            FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomType = mRoomType = 0;
        }
        else
        {
            mRoomType = 0;
        }
        RadioGroup mRG1 = (RadioGroup) vu1.findViewById(R.id.radioGroupRoomFlat);
        final RadioButton mRadioRoom =(RadioButton) vu1.findViewById(R.id.radioRoom);
        final RadioButton mRadioFlat =(RadioButton) vu1.findViewById(R.id.radioFlat);
        mRadioRoom.setChecked(true);
        mRG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mRadioRoom.isChecked())
                {
                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomType = mRoomType=0;
                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrice = mRoomPrice=1500;
                    String mPdisplay = getResources().getString(R.string.price_rs) +" "+ String.valueOf(mRoomPrice);
                    mRoomPriceTextView.setText(mPdisplay);
                    ((FacultyRoomAddActivity)getActivity()).updateRoomsAndPrice();

                }
                else if (mRadioFlat.isChecked())
                {
                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomType = mRoomType=1;
                    FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrice = mRoomPrice=2500;
                    String mPdisplay = getResources().getString(R.string.price_rs) +" "+ String.valueOf(mRoomPrice);
                    mRoomPriceTextView.setText(mPdisplay);
                    ((FacultyRoomAddActivity)getActivity()).updateRoomsAndPrice();
                }

                Log.i("Room Type changed for",mRoomName);
                Log.i("RoomType",String.valueOf(mRoomType));
            }
        });



        mRoomDet.setText(mRoomName);


        if (mStatusFragment.equals("1")) {



            if (FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag)!=null) {
                mRoomPrice = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrice;
                mRoomType = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomType;
                mFemaleCount = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount;
                mMaleCount = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount;
                mSelectedPreference = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).roomPref;
                mRoomName = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).roomName;
//                mWhatCanBeBooked = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook;
//                addAll(mWhatCanBeBooked);
                Log.i("Price", String.valueOf(mRoomPrice));
                mPdisplay = getResources().getString(R.string.price_rs) +" "+ String.valueOf(mRoomPrice);
                mRoomPriceTextView.setText(String.valueOf(mPdisplay));
                mCounterFemale.setText(String.valueOf(mFemaleCount));
                mCounterMale.setText(String.valueOf(mMaleCount));
                 if (mRoomType==0)
                {
                    mRadioRoom.setChecked(true);
                }
                else
                {
                    mRadioFlat.setChecked(true);
                }



//
//                if(mSelectedPreference.equals(FACULTYRESIDENCE))
//                {
//                    mPref.setSelection(0);
//                }
//                else if(mSelectedPreference.equals(BOYSHOSTEL))
//                {
//                    mPref.setSelection(1);
//
//                }
//                else if(mSelectedPreference.equals(GIRLSHOSTEL))
//                {
//                    mPref.setSelection(2);
//                }



                Log.i("Price", String.valueOf(mRoomPrice));
                Log.i("ValsUpdated", "All Values Updated ");
            }
            else
            {
                Log.i("TAG NOT FOUND",String.valueOf(mRoomTag));
            }


        }




        mListener.onFragmentDataRequest(mRoomTag,mSelectedPreference,mMaleCount,mFemaleCount,mRoomType,mRoomPrice);
        return vu1;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentDelete(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void addAll(String type)
    {
        List<String> categories = new ArrayList<String>();

        if (type.equalsIgnoreCase(CANBOOKALLLOCATIONS))
        {
            mWhatCanBeBooked = CANBOOKALLLOCATIONS;


            if (mFacultyFlag) {categories.add(FACULTYRESIDENCE);}
            categories.add(GIRLSHOSTEL);
            categories.add(BOYSHOSTEL);
            if (mStatusFragment.equals("0")) {
                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook =mWhatCanBeBooked=  CANBOOKALLLOCATIONS;

            }



        }
        else if (type.equalsIgnoreCase(CANBOOKFACULTYBOYS) )
        {

            if (mFacultyFlag){categories.add(FACULTYRESIDENCE);}
            categories.add(BOYSHOSTEL);

            mWhatCanBeBooked = CANBOOKFACULTYBOYS;
            if (mStatusFragment.equals("0")) {
                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook =mWhatCanBeBooked=  CANBOOKFACULTYBOYS;
            }



        }
        else if(type.equalsIgnoreCase(CANBOOKFACULTYGIRLS))
        {

            if (mFacultyFlag) {categories.add(FACULTYRESIDENCE);}
            categories.add(GIRLSHOSTEL);

            mWhatCanBeBooked = CANBOOKFACULTYGIRLS;
            if (mStatusFragment.equals("0")) {
                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook =mWhatCanBeBooked=  CANBOOKFACULTYGIRLS;


            }





        }


        mPrefAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        mPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCategories=mCat = categories;
        mPref.setAdapter(mPrefAdapter);
       // mSelectedPreference =
    }



    public void addFuncOldFrag()
    {
       // List<String> categories = new ArrayList<String>();

        mWhatCanBeBooked = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCanBook;
        mCat = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mCategories;
        Log.i("FragmentType",mCat.toString());
        mPrefAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCat);
        mPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPref.setAdapter(mPrefAdapter);
        mPref.setSelection(FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrefInt);
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonCloseFragment)
        {

            if (mListener != null)
            {
            mListener.onFragmentDelete(mRoomTag);
            }
            //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        else if (view.getId() == R.id.minusFemale)
        {
            if (mFemaleCount>0)
            {
                mFemaleCount--;
                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount=mFemaleCount;
                mCounterFemale.setText(String.valueOf(mFemaleCount));

                if(mFemaleCount==0)
                {
                    if (mMaleCount==0) {
                        addAll(CANBOOKALLLOCATIONS);
                    }
                    else
                    {
                        addAll(CANBOOKFACULTYBOYS);
                    }

                }
                else if (mFemaleCount==1)
                {
                    if (mMaleCount==1)
                    {

                       addAll(CANBOOKFACULTYBOYS);

                    }
                    else if(mMaleCount==0)
                    {
                       addAll(CANBOOKFACULTYGIRLS);
                    }
                }
            }
            else
            {
                Snackbar.make(view, "Female count cannot be less than zero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        }
        else if (view.getId() == R.id.plusFemale)
        {
            if(mMaleCount+mFemaleCount>=2)
            {
                Snackbar.make(view, "Maximum 2 person can stay in a room", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
            else
            {
                mFemaleCount++;

                if(mFemaleCount==2)
                {
                   addAll(CANBOOKFACULTYGIRLS);
                }
                else if (mFemaleCount==1)
                {
                    if(mMaleCount==1)
                    {
                        addAll(CANBOOKFACULTYBOYS);
                    }
                    else if(mMaleCount==0)
                    {
                        addAll(CANBOOKFACULTYGIRLS);
                    }
                }

                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount=mFemaleCount;
                mCounterFemale.setText(String.valueOf(mFemaleCount));
            }

        }
        else if (view.getId() == R.id.minusMale)
        {
            if (mMaleCount>0)
            {
                mMaleCount--;

                if(mMaleCount == 1)
                {
                   addAll(CANBOOKFACULTYBOYS);
                }

                if(mMaleCount==0)
                {
                    if(mFemaleCount==0) {
                        addAll(CANBOOKALLLOCATIONS);
                    }
                    else if(mFemaleCount==1)
                    {
                        addAll(CANBOOKFACULTYGIRLS);
                    }

                }


                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount=mMaleCount;
                mCounterMale.setText(String.valueOf(mMaleCount));
            }
            else
            {
                Snackbar.make(view, "Male count cannot be less than zero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

        }
        else if (view.getId()==R.id.plusMale)
        {
            if(mMaleCount+mFemaleCount>=2)
            {
                Snackbar.make(view, "Maximum 2 person can stay in a room", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
            else
            {

                mMaleCount++;
                FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount=mMaleCount;
                mCounterMale.setText(String.valueOf(mMaleCount));
                if (mMaleCount>=1)
                {
                    addAll(CANBOOKFACULTYBOYS);


                }

            }

        }

    }

    public void updateRoomIdsTextView() {

        String roomnewval = getResources().getString(R.string.room_details) + " " + FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).id;
        mRoomDet.setText(roomnewval);



    }

    public void UpdateOldFragments(String roomTagKey) {

        mRoomTag=roomTagKey;
        mRoomPrice = FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomPrice;
        mRoomType= FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mRoomType;
        mFemaleCount=FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mFemaleCount;
        mMaleCount=FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).mMaleCount;
        mSelectedPreference=FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).roomPref;
        mRoomName=FacultyHomeActivity.mAllRoomsDetails.get(mRoomTag).roomName;

        Log.i("Price",String.valueOf(mRoomPrice));
        mRoomPriceTextView.setText(String.valueOf(mRoomPrice));
        mCounterFemale.setText(String.valueOf(mFemaleCount));
        mCounterMale.setText(String.valueOf(mMaleCount));
        Log.i("Price",String.valueOf(mRoomPrice));
    }



    public interface OnFragmentInteractionListener {

        void onFragmentDelete(String roomTag);
        void onFragmentDataRequest(String roomTag, String pref,int malecount,int femalecount, int roomtype,int price);
    }
}
