package in.ac.iiitd.guestacc;

/**
 * Created by kd on 24/4/18.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Admin_StatusForToday_MainScreen extends AppCompatActivity {
    List<Admin_StatusForToday_Data> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statusfortoday_activity_main);
        mData = new ArrayList<>();
        mData.add(new Admin_StatusForToday_Data("BH1"));
        mData.add(new Admin_StatusForToday_Data("BH2"));
        mData.add(new Admin_StatusForToday_Data("GH1"));
        mData.add(new Admin_StatusForToday_Data("GH2"));
        mData.add(new Admin_StatusForToday_Data("FR1"));
        mData.add(new Admin_StatusForToday_Data("FR2"));
        mData.add(new Admin_StatusForToday_Data("FF1"));

        RecyclerView myrv = (RecyclerView)findViewById(R.id.admin_statusfortoday_recyclerView);
        Admin_StatusForToday_RecyclerViewAdapter mAdapter = new Admin_StatusForToday_RecyclerViewAdapter(this,mData);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(mAdapter);
    }
}
