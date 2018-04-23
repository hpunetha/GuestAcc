package in.ac.iiitd.guestacc;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kd on 22/4/18.
 */

public class AdminRoomStatusFragment extends Fragment {
    Date mToDate;
    int mDateVal;
    String mSendFromDate,mSendToDate;
    EditText mEditTextFromDate, mEditTextToDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminRoomStatus = inflater.inflate(R.layout.fragment_admin_room_status,container,false);

        mEditTextFromDate = (EditText)adminRoomStatus.findViewById(R.id.editTextAdminRoomFrom);
        mEditTextToDate = (EditText)adminRoomStatus.findViewById(R.id.editTextAdminRoomTo);

        //*****************************************Calendar instances*********************************************

        final String mFormat = "E, MMM d";
        SimpleDateFormat mPreSDFormat = new SimpleDateFormat(mFormat, Locale.ENGLISH);
        Date mPreDate = new Date();
        mToDate =mPreDate;
        String mPreTextCheckin = getString(R.string.check_in)+  mPreSDFormat.format(mPreDate);
        mEditTextFromDate.setText(mPreTextCheckin);
        mSendFromDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mPreDate.getTime());

        Calendar mPreCal = Calendar.getInstance();
        mPreCal.setTime(mPreDate);
        mPreCal.add(Calendar.DATE,1);
        Date mPreNextDate = mPreCal.getTime();

        String mPreTextCheckout = getString(R.string.check_out)+  mPreSDFormat.format(mPreNextDate);
        mEditTextToDate.setText(mPreTextCheckout);
        mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mPreNextDate.getTime());


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

                if (mDateVal ==1)
                {
                    String mTextIn = getString(R.string.check_in)+ mSimpleDF.format(mCal.getTime());

                    mSendFromDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mCal.getTime());

                    mToDate =mCal.getTime();
                    mEditTextFromDate.setText(mTextIn);
                    Calendar mChkCal = Calendar.getInstance();
                    mChkCal.setTime(mToDate);
                    mChkCal.add(Calendar.DATE,1);
                    String mTextOut = getString(R.string.check_out) + mSimpleDF.format(mChkCal.getTime());
                    mEditTextToDate.setText(mTextOut);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mChkCal.getTime());

                }
                else if (mDateVal ==2)
                {
                    String text = getString(R.string.check_out) + mSimpleDF.format(mCal.getTime());
                    mEditTextToDate.setText(text);

                    mSendToDate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(mCal.getTime());
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

                mDateVal =1;
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
                mDateVal =2;

                Calendar mChkCal = Calendar.getInstance();
                mChkCal.setTime(mToDate);
                mChkCal.add(Calendar.DATE,1);
                //Date mPreNextDate = mChkCal.getTime();

                mDatePickDialog.getDatePicker().setMinDate(mChkCal.getTimeInMillis());
                mDatePickDialog.show();
            }
        });

        //*********************************************************************************************************************
        return adminRoomStatus;
    }
}
