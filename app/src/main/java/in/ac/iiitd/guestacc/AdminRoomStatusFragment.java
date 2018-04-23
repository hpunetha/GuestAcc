package in.ac.iiitd.guestacc;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by kd on 22/4/18.
 */

public class AdminRoomStatusFragment extends Fragment {
    Date mToDate;
    int mDateVal;
    String mSendFromDate,mSendToDate;
    Button mAdminRoomAvailability;
    EditText mEditTextFromDate, mEditTextToDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminRoomStatus = inflater.inflate(R.layout.fragment_admin_room_status,container,false);

        mEditTextFromDate = (EditText)adminRoomStatus.findViewById(R.id.editTextAdminRoomFrom);
        mEditTextToDate = (EditText)adminRoomStatus.findViewById(R.id.editTextAdminRoomTo);
        mAdminRoomAvailability = (Button)adminRoomStatus.findViewById(R.id.button_adminRoomCheckAvailability);

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

        /*mAdminRoomAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date mFromDate,mToDate1;
                List<Date> mDateList;
                Calendar mCalender;
                mFromDate=mToDate;

                Log.i("sendToDate",mSendToDate);
                Log.i("fromDate",mSendFromDate);

                //Reference=> https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
                DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                try {
                    mFromDate = mDateFormat.parse(mSendFromDate);
                    mToDate1 = mDateFormat.parse(mSendToDate);
                    // Reference=>   https://stacverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java

                    mDateList= new ArrayList<Date>();
                    mCalender= new GregorianCalendar();
                    mCalender.setTime(mFromDate);
                    while (mCalender.getTime().before(mToDate1))
                    {
                        Date result = mCalender.getTime();
                        mDateList.add(result);
                        mCalender.add(Calendar.DATE, 1);
                    }

                    Log.i("Date List",mDateList.toString());

                }
        });*/
        //*********************************************************************************************************************
        return adminRoomStatus;
    }
}
