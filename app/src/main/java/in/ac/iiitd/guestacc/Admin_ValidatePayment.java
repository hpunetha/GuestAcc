package in.ac.iiitd.guestacc;

import android.app.ProgressDialog;
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

public class Admin_ValidatePayment extends AppCompatActivity implements Admin_ValidatePayment_RecyclerAdapter.ValidateItemClickListener
{


    RecyclerView recyclerView ;
    Admin_ValidatePayment_RecyclerAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_validate_payment);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...Please Wait ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        //progress.show();
       // progress.dismiss();

        List<Admin_Data_ValidatePayment> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_ValidatePayment()) ;
        }


        recyclerView = (RecyclerView)findViewById(R.id.validate_payment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_ValidatePayment_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View v,int position)
    {

    }
}
