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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOfficialGuestDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOfficialGuestDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOfficialGuestDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    private String tag;
    private String gender;
    private String roomId;
    private String roomType;
    private String prefered_location;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentOfficialGuestDetails() {
        // Required empty public constructor
    }

    public static FragmentOfficialGuestDetails newInstance(String tag, String gender, String roomId,String roomType, String prefered_location) {
        FragmentOfficialGuestDetails fragment = new FragmentOfficialGuestDetails();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_official_guest_details, container, false);
        TextView mGender = (TextView) view.findViewById(R.id.txtGenderOfficial);
        TextView mRoomId = (TextView) view.findViewById(R.id.txtRoomIdOfficial);
        mGender.setText(gender);
        mRoomId.setText(roomId);

        final ImageButton btnExpandOfficial = (ImageButton)view.findViewById(R.id.btnExpandOfficial);
        final ImageButton btnColapseOfficial = (ImageButton)view.findViewById(R.id.btnColapseOfficial);
        final EditText et_NameOfficial = (EditText)view.findViewById(R.id.et_NameOfficial);
        final EditText et_AgeOfficial = (EditText)view.findViewById(R.id.et_AgeOfficial);
        final ImageView ImageView_guestOfficial      = (ImageView)view.findViewById(R.id.ImageView_guestOfficial);

        final Guest guest = new Guest();
        ImageView_guestOfficial.setImageResource(R.drawable.guestimage);
        btnExpandOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnColapseOfficial.setVisibility(View.VISIBLE);
                et_NameOfficial.setVisibility(View.VISIBLE);
                // et_LastName.setVisibility(View.VISIBLE);
                et_AgeOfficial.setVisibility(View.VISIBLE);
                btnExpandOfficial.setVisibility(View.GONE);
            }
        });

        btnColapseOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExpandOfficial.setVisibility(View.VISIBLE);
                et_NameOfficial.setVisibility(View.GONE);
                //et_LastName.setVisibility(View.GONE);
                et_AgeOfficial.setVisibility(View.GONE);
                btnColapseOfficial.setVisibility(View.GONE);
            }
        });



        et_NameOfficial.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                guest.setName(s.toString());
               // BookingDetail.hm_guestDetails.put(String.valueOf(tag),guest);

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

        et_AgeOfficial.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                guest.setAge(s.toString());
               // BookingDetail.hm_guestDetails.put(String.valueOf(tag),guest);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
