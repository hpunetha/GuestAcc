package in.ac.iiitd.guestacc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.support.v4.app.DialogFragment;


public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {
    int dateval=0;
    Date toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        final TextView mTextViewCheckAvail = (TextView)findViewById(R.id.textViewCheckAvail);
        final EditText mEditTextFromDate= (EditText) findViewById(R.id.editTextFrom);
        final EditText mEditTextToDate= (EditText) findViewById(R.id.editTextTo);
        final EditText mEditTextRoomCount= (EditText) findViewById(R.id.editTextRoom);
        final EditText mEditTextMaleCount= (EditText) findViewById(R.id.editTextMaleCount);
        final EditText mEditTextFemaleCount= (EditText) findViewById(R.id.editTextFemaleCount);


        mTextViewCheckAvail.setTranslationY(-500f);
        mTextViewCheckAvail.setAlpha(0f);
        mEditTextFromDate.setTranslationX(-500f);
        mEditTextFromDate.setAlpha(0f);
        mEditTextToDate.setTranslationX(500f);
        mEditTextToDate.setAlpha(0f);
        mEditTextRoomCount.setTranslationX(-500f);
        mEditTextRoomCount.setAlpha(0f);
        mEditTextMaleCount.setTranslationY(1500f);
        mEditTextMaleCount.setAlpha(0f);

        mEditTextFemaleCount.setTranslationX(500f);
        mTextViewCheckAvail.animate().translationYBy(500f).alpha(1f).setDuration(400);
        mEditTextFromDate.animate().translationXBy(500f).alpha(1f).setDuration(400);
        mEditTextToDate.animate().translationXBy(-500f).alpha(1f).setDuration(400);
        mEditTextRoomCount.animate().translationXBy(500f).alpha(1f).setDuration(400);
        mEditTextMaleCount.animate().translationYBy(-1500f).alpha(1f).setDuration(400);
        mEditTextFemaleCount.animate().translationXBy(-500f).alpha(1f).setDuration(400);

        final String mFormat = "E, MMM d";
        SimpleDateFormat mPreSDFormat = new SimpleDateFormat(mFormat, Locale.US);
        Date mPreDate = new Date();
        toDate=mPreDate;
        String mPreTextCheckin = getString(R.string.check_in)+  mPreSDFormat.format(mPreDate);
        mEditTextFromDate.setText(mPreTextCheckin);

        Calendar mPreCal = Calendar.getInstance();
        mPreCal.setTime(mPreDate);
        mPreCal.add(Calendar.DATE,1);
        Date mPreNextDate = mPreCal.getTime();
       // final Calendar mPreCalFinal = mPreCal;
        String mPreTextCheckout = getString(R.string.check_out)+  mPreSDFormat.format(mPreNextDate);
        mEditTextToDate.setText(mPreTextCheckout);


        //Taken from source : ->
        final Calendar mCal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            //DatePickerDialog.

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCal.set(Calendar.YEAR, year);
                mCal.set(Calendar.MONTH, monthOfYear);
                mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                String mFormat = "E, MMM d";
                SimpleDateFormat mSimpleDF = new SimpleDateFormat(mFormat, Locale.US);
                if (dateval==1)
                {
                    String mTextIn = getString(R.string.check_in)+ mSimpleDF.format(mCal.getTime());
                    toDate=mCal.getTime();
                    mEditTextFromDate.setText(mTextIn);
                    Calendar mChkCal = Calendar.getInstance();
                    mChkCal.setTime(toDate);
                    mChkCal.add(Calendar.DATE,1);
                    String mTextOut = getString(R.string.check_out) + mSimpleDF.format(mChkCal.getTime());
                    mEditTextToDate.setText(mTextOut);
                }
                else if (dateval==2)
                {
                    String text = getString(R.string.check_out) + mSimpleDF.format(mCal.getTime());
                    mEditTextToDate.setText(text);
                }
            }

        };

        mEditTextFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(UserHomeActivity.this, date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));

                dateval=1;
                mDatePickDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                mDatePickDialog.show();
            }
        });

        mEditTextToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickDialog;
                mDatePickDialog = new DatePickerDialog(UserHomeActivity.this, date, mCal
                        .get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                        mCal.get(Calendar.DAY_OF_MONTH));
                dateval=2;

                Calendar mChkCal = Calendar.getInstance();
                mChkCal.setTime(toDate);
                mChkCal.add(Calendar.DATE,1);
                //Date mPreNextDate = mChkCal.getTime();


                mDatePickDialog.getDatePicker().setMinDate(mChkCal.getTimeInMillis());
                mDatePickDialog.show();
            }
        });

        mEditTextRoomCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuild = new AlertDialog.Builder(UserHomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_user_home_details_dialog,null);
                final TextView mRooms = (TextView) mView.findViewById(R.id.textViewRoomCount);
                final TextView mMales = (TextView) mView.findViewById(R.id.textViewMaleCount);
                final TextView mFemales = (TextView) mView.findViewById(R.id.textViewFemaleCount);
                final Button mApply = (Button) mView.findViewById(R.id.btnApply);
                mApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("success","success");
                    }
                });

                mBuild.setView(mView);
                AlertDialog mAlertDialog = mBuild.create();
                mAlertDialog.show();

            }
        });

        //Taken code end


    }

    @Override
    public void onClick(View view) {

    }
}
