package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TypeLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_login);


        final RadioButton mRadioStudent = (RadioButton) findViewById(R.id.radioStudent);
        final RadioButton mRadioFaculty = (RadioButton) findViewById(R.id.radioFaculty);
        final RadioButton mRadioAdmin = (RadioButton) findViewById(R.id.radioAdmin);
        RadioGroup mRadioGroupLoginType= (RadioGroup) findViewById(R.id.radioGroupLoginType);

        mRadioGroupLoginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                loginCheck(mRadioStudent,mRadioFaculty,mRadioAdmin);

            }
        });

    }

    private void loginCheck(RadioButton mRadioStudent, RadioButton mRadioFaculty, RadioButton mRadioAdmin)
    {
        Boolean mRadioStudentChecked = mRadioStudent.isChecked();
        Boolean mRadioFacultyChecked = mRadioFaculty.isChecked();
        Boolean mRadioAdminChecked = mRadioAdmin.isChecked();

        if (mRadioStudentChecked)
        {
            Intent mUserHome = new Intent(this, UserHomeActivity.class);
            startActivity(mUserHome);

        }
        else if (mRadioFacultyChecked)
        {

        }
        else if (mRadioAdminChecked)
        {

        }
    }
}
