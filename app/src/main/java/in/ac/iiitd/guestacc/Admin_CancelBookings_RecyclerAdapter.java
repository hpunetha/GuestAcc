package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;



public class Admin_CancelBookings_RecyclerAdapter extends RecyclerView.Adapter<Admin_CancelBookings_RecyclerAdapter.ViewHolder> {
    View v;
    private LayoutInflater inflater ;
    private CancelItemClickListener mClickListener ;
    private List<Admin_Data_CancelBookings> data ;
    private Button cancellationButton ;
    private Button cancelButton ;
    RelativeLayout relativeLayout ;
    ValueEventListener mDbRefBookingsListener;
    Context context;
    DatabaseReference mBOOKING_FINAL = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL);
    String CancelledRequestUserId;



    Admin_CancelBookings_RecyclerAdapter(Context con, List<Admin_Data_CancelBookings> data)
    {
        this.inflater = LayoutInflater.from(con);
        this.data = data ;
        this.context = con ;
    }

    @Override
    public int getItemCount()
    {
        return data.size() ;
    }


    @Override
    // inflates row layout from xml
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        v = inflater.inflate(R.layout.admin_cancel_bookings_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {
        //TODO : change position

        Admin_Data_CancelBookings dataRow = data.get(position) ;

        holder.reqID.setText(dataRow.getReqID());
        holder.totalGuest.setText(dataRow.getGuests());
        holder.roomNames.setText(dataRow.getRooms());
        holder.nRooms.setText(dataRow.getNrooms());
        holder.startDate.setText(dataRow.getStartDate());
        holder.endDate.setText(dataRow.getEndDate());
        //holder.reason.setText(dataRow.getReason());

    }


    // stores and recycle views as they are scrolled

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView reqID ;
        private TextView totalGuest ;
        private TextView roomNames ;
        private TextView nRooms ;
        private EditText reason ;
        private TextView startDate ;
        private TextView endDate ;
        //private Button button;




        ViewHolder(final View itemView)
        {
            super(itemView) ;

            reqID = itemView.findViewById(R.id.reqid_cancel_bookings) ;
            roomNames =  itemView.findViewById(R.id.roomname_cancel_bookings) ;
            nRooms = itemView.findViewById(R.id.nrooms_cancel_bookings) ;
            totalGuest = itemView.findViewById(R.id.total_guest_cancel_bookings) ;
            reason = itemView.findViewById(R.id.edit_text_cancel_bookings) ;
            startDate = itemView.findViewById(R.id.start_date) ;
            endDate = itemView.findViewById(R.id.end_sate) ;

            cancelButton = itemView.findViewById(R.id.cancel_cancel_bookings) ;
            cancellationButton = itemView.findViewById(R.id.confirm_cancel_cancel_bookings) ;

            // make recycler row clickable
            itemView.setOnClickListener(this);


            // listen to the accept button
            cancelButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    RelativeLayout hiddenLayout = (RelativeLayout) itemView.findViewById(R.id.hidden_layout_cancel_bookings) ;

                    if(hiddenLayout.getVisibility()==View.GONE)
                    {
                        hiddenLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {

                        hiddenLayout.setVisibility(View.GONE);
                    }
                    // getting context from main activity
                    // dialogSelect.show(((AppCompatActivity)context).getSupportFragmentManager(),"123");
                }
            });

            cancellationButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d("getStartDate()=>",data.get(getAdapterPosition()).getStartDate());
                    Log.d("getReqID()=>", data.get(getAdapterPosition()).getReqID());

                   if (reason!=null)
                   {

                       mBOOKING_FINAL.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               CancelledRequestUserId =  dataSnapshot.child(data.get(getAdapterPosition()).getStartDate()).child( data.get(getAdapterPosition()).getReqID()).child("userid").getValue().toString();
                               mBOOKING_FINAL.child(data.get(getAdapterPosition()).getStartDate()).child( data.get(getAdapterPosition()).getReqID()).child("booking_status").setValue(MainActivity.CANCELLED);
                               mBOOKING_FINAL.child(data.get(getAdapterPosition()).getStartDate()).child( data.get(getAdapterPosition()).getReqID()).child("modification_reason").setValue(reason.getText().toString());
                               DatabaseReference user = FirebaseDatabase.getInstance().getReference("user"+"/"+CancelledRequestUserId);
                               Log.d("CancelledRequestUserId", "=====================>>>>>>>>>>");
                               Log.d("CancelledRequestUserId",CancelledRequestUserId);
                               user.child(data.get(getAdapterPosition()).getReqID()).child("status").setValue(MainActivity.CANCELLED);

                               Log.d("dataListsize",String.valueOf(data.size()));
                               //data.clear();

                               data.remove(getAdapterPosition());
                               notifyDataSetChanged();

                               Log.d("dataListsize",String.valueOf(data.size()));

                               Snackbar.make(((View)itemView.findViewById( R.id.start_date)), "Request cancelled sucessfully", Snackbar.LENGTH_LONG)
                                       .setAction("Action", null).show();
                           }


                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                   }
                   else
                   {
                       Snackbar.make(v, "Please give a reason for cancellation", Snackbar.LENGTH_LONG)
                               .setAction("Action", null).show();
                   }
                    // getting context from main activity
                    // dialogSelect.show(((AppCompatActivity)context).getSupportFragmentManager(),"123");
                }
            });

        }

        @Override
        // onclick listener for view
        public void onClick(View v)
        {
            if(mClickListener!=null) mClickListener.onItemClick(v,getAdapterPosition());

            //Log.e("CLICK",v.toString()) ;
            v.setClickable(false);

        }
    }

    // allows click events to be caught
    void setClickListener(CancelItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener ;
    }


    public interface CancelItemClickListener
    {
        void onItemClick(View view, int position) ;
    }

}
