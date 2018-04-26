package in.ac.iiitd.guestacc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentPersonalGuestDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String tag;
    private String gender;
    private String roomId;
    private String roomType;
    private String prefered_location;

    private OnFragmentInteractionListener mListener;

    public FragmentPersonalGuestDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to_date create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tag Parameter 1.
     * @param gender Parameter 2.
     * @return A new instance of fragment FragmentPersonalGuestDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPersonalGuestDetails newInstance(String tag, String gender, String roomId,String roomType, String prefered_location) {
        FragmentPersonalGuestDetails fragment = new FragmentPersonalGuestDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tag);
        args.putString(ARG_PARAM2, gender);
        args.putString(ARG_PARAM3, roomId);
        args.putString(ARG_PARAM4, roomType);
        args.putString(ARG_PARAM5, prefered_location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tag = getArguments().getString(ARG_PARAM1);
            gender = getArguments().getString(ARG_PARAM2);
            roomId = getArguments().getString(ARG_PARAM3);
            roomType = getArguments().getString(ARG_PARAM4);
            prefered_location = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_guest, container, false);

        TextView mGender = (TextView) view.findViewById(R.id.txtGender);
        TextView mRoomId = (TextView) view.findViewById(R.id.txtRoomId);
        mGender.setText(gender);
        mRoomId.setText(roomId);
        final ImageButton btn_Expand = (ImageButton)view.findViewById(R.id.btnExpand);
        final ImageButton btn_Colapse = (ImageButton)view.findViewById(R.id.btnColapse);
        final EditText et_FirstName = (EditText)view.findViewById(R.id.et_FirstName);
       // final EditText et_LastName = (EditText)view.findViewById(R.id.et_LastName);
        final EditText et_Age = (EditText)view.findViewById(R.id.et_Age);
        final ImageView ImageView_guest      = (ImageView)view.findViewById(R.id.ImageView_guest);

        final Guest guest = new Guest();
        ImageView_guest.setImageResource(R.drawable.guestimage);
        btn_Expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Colapse.setVisibility(View.VISIBLE);
                et_FirstName.setVisibility(View.VISIBLE);
               // et_LastName.setVisibility(View.VISIBLE);
                et_Age.setVisibility(View.VISIBLE);
                btn_Expand.setVisibility(View.GONE);
            }
        });
        btn_Colapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Expand.setVisibility(View.VISIBLE);
                et_FirstName.setVisibility(View.GONE);
                //et_LastName.setVisibility(View.GONE);
                et_Age.setVisibility(View.GONE);
                btn_Colapse.setVisibility(View.GONE);
            }
        });
        et_FirstName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                guest.setName(s.toString());
                //BookingDetail.hm_guestDetails.put(String.valueOf(tag),guest);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


        et_Age.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                guest.setAge(s.toString());
                //BookingDetail.hm_guestDetails.put(String.valueOf(tag),guest);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        guest.setGender(gender);
        guest.setAssociated_room_id(roomId);
        guest.setRoom_type(roomType);
        guest.setPrefered_location(prefered_location);
        guest.setAllocated_room("");
        BookingDetail.hm_guestDetails.put(String.valueOf(tag),guest);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
