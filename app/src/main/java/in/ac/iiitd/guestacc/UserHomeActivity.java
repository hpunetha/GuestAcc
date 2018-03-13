package in.ac.iiitd.guestacc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class UserHomeActivity extends AppCompatActivity {
    int mDateVal =0;
    Date mToDate;
    int mRoomNum;
    int mMaleNum,mFemNum;
    int mMenuPressCount=0;
    final int maxRoom = 10;
    final int maxMale = 10;
    final int maxFemale =10;

    final int maxSlidePics = 5;

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
        final ImageButton mImageBtnMenu = (ImageButton) findViewById(R.id.imageBtnMenu);
        final ImageView mImageViewPicsShow = (ImageView) findViewById(R.id.imageViewPicsShow);
        final Button mBtnCheckAvail = (Button) findViewById(R.id.btnCheckAvail);

        mBtnCheckAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mViewAv = new Intent(UserHomeActivity.this, ViewAvailability.class);
               // mViewAv.putExtra("UserType",0);
                startActivity(mViewAv);
            }
        });

        mImageBtnMenu.setVisibility(View.INVISIBLE);

        final FloatingActionButton mFloatActionBtnMenu = (FloatingActionButton) findViewById(R.id.floatActionBtnMenu);
        mRoomNum=0;
        mMaleNum=0;
        mFemNum=0;

        //Initialize the values of edit texts
        String mSetRoom = getString(R.string.room) + String.valueOf(mRoomNum);
        mEditTextRoomCount.setText(mSetRoom);

        String mSetMaleCount = getString(R.string.male) +String.valueOf(mMaleNum);
        mEditTextMaleCount.setText(mSetMaleCount);

        String mSetFemaleCount = getString(R.string.female) + String.valueOf(mFemNum);
        mEditTextFemaleCount.setText(mSetFemaleCount);
        // initialization ends

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
        mToDate =mPreDate;
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
                if (mDateVal ==1)
                {
                    String mTextIn = getString(R.string.check_in)+ mSimpleDF.format(mCal.getTime());
                    mToDate =mCal.getTime();
                    mEditTextFromDate.setText(mTextIn);
                    Calendar mChkCal = Calendar.getInstance();
                    mChkCal.setTime(mToDate);
                    mChkCal.add(Calendar.DATE,1);
                    String mTextOut = getString(R.string.check_out) + mSimpleDF.format(mChkCal.getTime());
                    mEditTextToDate.setText(mTextOut);
                }
                else if (mDateVal ==2)
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

                mDateVal =1;
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
                mDateVal =2;

                Calendar mChkCal = Calendar.getInstance();
                mChkCal.setTime(mToDate);
                mChkCal.add(Calendar.DATE,1);
                //Date mPreNextDate = mChkCal.getTime();


                mDatePickDialog.getDatePicker().setMinDate(mChkCal.getTimeInMillis());
                mDatePickDialog.show();
            }
        });

        mEditTextRoomCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog(view);

            }
        });

        mEditTextMaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        mEditTextFemaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        //Taken code end


        mFloatActionBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMenuPressCount++;
                mFloatActionBtnMenu.setImageResource(R.drawable.menuicon2);



                PopupMenu mPopup = new PopupMenu(UserHomeActivity.this,mFloatActionBtnMenu);
                mPopup.getMenuInflater().inflate(R.menu.user_popupmenu,mPopup.getMenu());

                mPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(UserHomeActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                mPopup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        mFloatActionBtnMenu.setImageResource(R.drawable.menuicon);
                    }
                });

                mPopup.show();

            }
        });



//        mImageBtnMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMenuPressCount++;
//                mImageBtnMenu.setImageResource(R.drawable.menuicon2);
//
//
//
//                PopupMenu mPopup = new PopupMenu(UserHomeActivity.this,mImageBtnMenu);
//                mPopup.getMenuInflater().inflate(R.menu.user_popupmenu,mPopup.getMenu());
//
//                mPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        Toast.makeText(UserHomeActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
//
//                mPopup.setOnDismissListener(new PopupMenu.OnDismissListener() {
//                    @Override
//                    public void onDismiss(PopupMenu popupMenu) {
//                        mImageBtnMenu.setImageResource(R.drawable.menuicon);
//                    }
//                });
//
//                mPopup.show();
//            }
//        });

    }

    private void openDialog(View view)
    {
        AlertDialog.Builder mBuild = new AlertDialog.Builder(UserHomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_user_home_details_dialog,null);
        final TextView mRooms = (TextView) mView.findViewById(R.id.textViewRoomCount);
        final TextView mMales = (TextView) mView.findViewById(R.id.textViewMaleCount);
        final TextView mFemales = (TextView) mView.findViewById(R.id.textViewFemaleCount);

        mRooms.setText(String.valueOf(mRoomNum));
        mMales.setText(String.valueOf(mMaleNum));
        mFemales.setText(String.valueOf(mFemNum));

        final Button mApply = (Button) mView.findViewById(R.id.btnApply);
        final Button mCancel = (Button) mView.findViewById(R.id.btnCancel);
        final ImageButton mImageBtnIncreRoomCount = (ImageButton) mView.findViewById(R.id.imageBtnIncreRoomCount);
        final ImageButton mImageBtnReduceRoomCount = (ImageButton) mView.findViewById(R.id.imageBtnReduceRoomCount);
        final ImageButton mImageBtnIncreMaleCount = (ImageButton)mView.findViewById(R.id.imageBtnIncreMaleCount);
        final ImageButton mImageBtnReduceMaleCount = ( ImageButton)mView.findViewById(R.id.imageBtnReduceMaleCount);
        final ImageButton mImageBtnIncreFemaleCount = (ImageButton) mView.findViewById(R.id.imageBtnIncreFemaleCount);
        final ImageButton mImageBtnReduceFemaleCount = (ImageButton) mView.findViewById(R.id.imageBtnReduceFemaleCount);



        mBuild.setView(mView);
        final AlertDialog mAlertDialog = mBuild.create();
        mAlertDialog.show();

        mImageBtnIncreRoomCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int temproom = Integer.parseInt(mRooms.getText().toString());
                temproom += 1;
                if (temproom<maxRoom) {
                    mRooms.setText(String.valueOf(temproom));
                }
                else
                {
                    Log.w("Error","Maximum value of Rooms Reached");
                }

            }
        });

        mImageBtnReduceRoomCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int temproom = Integer.parseInt(mRooms.getText().toString());
                temproom -= 1;
                if (temproom>=0) {
                    mRooms.setText(String.valueOf(temproom));
                }
                else
                {
                    Log.w("Error","Minimum value of Rooms Reached");
                }
            }
        });

        mImageBtnIncreMaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempmale = Integer.parseInt(mMales.getText().toString());
                tempmale += 1;
                if (tempmale<maxMale) {
                    mMales.setText(String.valueOf(tempmale));
                }
                else
                {
                    Log.w("Error","Maximum value of Males Reached");
                }

            }
        });

        mImageBtnReduceMaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempmale = Integer.parseInt(mMales.getText().toString());
                tempmale -= 1;

                if (tempmale>=0) {
                    mMales.setText(String.valueOf(tempmale));
                }
                else
                {
                    Log.w("Error","Minimum value of Males Reached");
                }
            }
        });

        mImageBtnIncreFemaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempfemale = Integer.parseInt(mFemales.getText().toString());
                tempfemale += 1;
                if (tempfemale<maxFemale) {
                    mFemales.setText(String.valueOf(tempfemale));
                }
                else
                {
                    Log.w("Error","Maximum value of Females Reached");
                }
            }
        });

        mImageBtnReduceFemaleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempfemale = Integer.parseInt(mFemales.getText().toString());
                tempfemale -= 1;

                if (tempfemale>=0) {
                    mFemales.setText(String.valueOf(tempfemale));
                }
                else
                {
                    Log.w("Error","Minimum value of Females Reached");
                }
            }
        });

        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.i("success","success");
                mRoomNum = Integer.parseInt(mRooms.getText().toString());
                mMaleNum = Integer.parseInt(mMales.getText().toString());
                mFemNum = Integer.parseInt(mFemales.getText().toString());


                final EditText mEditTextRoomCount= (EditText) findViewById(R.id.editTextRoom);
                final EditText mEditTextMaleCount= (EditText) findViewById(R.id.editTextMaleCount);
                final EditText mEditTextFemaleCount= (EditText) findViewById(R.id.editTextFemaleCount);

                String mSetRoom = getString(R.string.room) + String.valueOf(mRoomNum);
                mEditTextRoomCount.setText(mSetRoom);

                String mSetMaleCount = getString(R.string.male) +String.valueOf(mMaleNum);
                mEditTextMaleCount.setText(mSetMaleCount);

                String mSetFemaleCount = getString(R.string.female) + String.valueOf(mFemNum);
                mEditTextFemaleCount.setText(mSetFemaleCount);
                mAlertDialog.hide();
            }
        });


        mCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                mAlertDialog.hide();
            }
        });
    }

}
