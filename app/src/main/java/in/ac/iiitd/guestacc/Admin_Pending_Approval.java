package in.ac.iiitd.guestacc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Admin_Pending_Approval extends AppCompatActivity implements Admin_Pending_Approval_RecyclerAdapter.ItemClickListener
,Admin_DialogSelect_PendingDetails.DialogClickListener
{

    RecyclerView recyclerView ;
    Admin_Pending_Approval_RecyclerAdapter adapter ;
    final Admin_Pending_Approval context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_pending_approval);

        List<Admin_Data_PendingApproval> data = new ArrayList<>();

        for(int i=0;i<20;i++)
        {
            data.add(new Admin_Data_PendingApproval()) ;
        }

         recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Admin_Pending_Approval_RecyclerAdapter(this, data);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
    }





    @Override
    public void onItemClick(View view , int position , List<Admin_Data_PendingApproval_RoomData> adminDataPendingApprovalRoomData)
    {


           View row = recyclerView.getLayoutManager().findViewByPosition(position) ;

             LinearLayout midLinearLayout = (LinearLayout) row.findViewById(R.id.midlayout) ;

             // if the view is already clicked, then hide it and remove all the views attached to it
             if(midLinearLayout.getVisibility()==View.VISIBLE)
            {
                midLinearLayout.setVisibility(View.GONE);
                midLinearLayout.removeAllViews();
                return ;
            }


        final View[] cardview= new View[adminDataPendingApprovalRoomData.size()];

           for(int i = 0; i< adminDataPendingApprovalRoomData.size(); i++)
           {
               Admin_Data_PendingApproval_RoomData roomDetails = adminDataPendingApprovalRoomData.get(i) ;

               cardview[i] = (View)getLayoutInflater().inflate(R.layout.admin_pending_approval_card_view, null);

               //set TextView from from data

               ((TextView)cardview[i].findViewById(R.id.Room)).setText("Room "+String.valueOf(i));
               ((TextView)cardview[i].findViewById(R.id.GuestType1)).setText(roomDetails.guest1);
               ((TextView)cardview[i].findViewById(R.id.GuestType2)).setText(roomDetails.guest2);
               ((TextView)cardview[i].findViewById(R.id.preference)).setText(roomDetails.preference);
               Button allocateButton = (Button)cardview[i].findViewById(R.id.allocate);
               RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

               /*********************     listen to the allocate button  *********************/

               final Integer index = i ;

               allocateButton.setOnClickListener(new View.OnClickListener()
               {
                   @Override

                   public void onClick(View v)
                   {
                       Admin_DialogSelect_PendingDetails dialogSelect = new Admin_DialogSelect_PendingDetails();//new Admin_DialogSelect_PendingDetails(cardview[index]) ;

                       // send reference to dialogselect
                       dialogSelect.setCardview(cardview[index],dialogSelect);
                       // set clicklistener using context of mainactivity
                       dialogSelect.setClickListener(context);
                       // get context from calling activity
                       dialogSelect.show(getSupportFragmentManager(),"123");

                   }
               });

              // cardView[i].setLayoutParams(layoutparams);
              // cardView[i].addView(cardview);
               midLinearLayout.addView(cardview[i]);
           }

          midLinearLayout.setVisibility(View.VISIBLE);
        //midLinearLayout.animate().translationY(midLinearLayout.getHeight());
       //rel.setVisibility(View.VISIBLE);
       // notifyDatasetChanged() ;
    }

    @Override
    public void onDialogClick(View cardview,String type)
    {
        // use this method on Dialog clicks
       // Log.e("TOAST",cardview.toString(),null);


        // Do when return from dialog

    }

    @Override
    public void onButtonClick(View v,int position)
    {
        Log.e("DATABASE",String.valueOf(position)) ;
    }
}
