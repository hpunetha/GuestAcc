package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 28/4/18.
 */

public class Admin_FacultyRegistration extends AppCompatActivity implements Admin_FacultyRegistration_RecyclerAdapter.FacultyRegistrationItemClickListener

{

    RecyclerView recyclerView ;
    Admin_FacultyRegistration_RecyclerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_faculty_registration);


        List<Admin_Data_Faculty_Registration> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_Faculty_Registration("AAAA","Faculty","123@iiitd.ac.in")) ;
        }



        recyclerView = (RecyclerView)findViewById(R.id.faculty_registration_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_FacultyRegistration_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View v, int position)
    {

    }

}
