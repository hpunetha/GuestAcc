package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_CancelBookings extends AppCompatActivity implements Admin_CancelBookings_RecyclerAdapter.CancelItemClickListener
{
   Admin_CancelBookings_RecyclerAdapter adapter ;
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cancel_bookings);


        // TODO : make layout expandable
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Admin_Data_CancelBookings> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_CancelBookings()) ;
        }

        recyclerView = (RecyclerView)findViewById(R.id.cancel_bookings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_CancelBookings_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View v, int position)
    {

    }
}
