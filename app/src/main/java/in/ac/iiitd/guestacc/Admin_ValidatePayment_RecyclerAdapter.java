package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_ValidatePayment_RecyclerAdapter extends RecyclerView.Adapter<Admin_ValidatePayment_RecyclerAdapter.ViewHolder>
{
    FirebaseDatabase mDatabase;
    View v;
    private LayoutInflater inflater ;
    private ValidateItemClickListener mClickListener ;
    private List<Admin_Data_ValidatePayment> data ;
    private Button approveMainButton ;
    private Button cancelMainButton ;
    RelativeLayout relativeLayout ;
    Context context;

    DatabaseReference mFireBaseReference;
    DatabaseReference mBOOKING_FINAL = FirebaseDatabase.getInstance().getReference(MainActivity.BOOKING_FINAL);
    DatabaseReference mPendingPayment = FirebaseDatabase.getInstance().getReference(MainActivity.PENDING_REQUESTS+"/"+MainActivity.PENDING_PAYMENT);


    String ValidatePaymentusername = "";
    DatabaseReference user = FirebaseDatabase.getInstance().getReference("user/"+ValidatePaymentusername);


    Admin_ValidatePayment_RecyclerAdapter(Context con, List<Admin_Data_ValidatePayment> data)
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
        v = inflater.inflate(R.layout.admin_validate_payment_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {
        //TODO : change position

        Admin_Data_ValidatePayment dataRow = data.get(position) ;
        holder.reqID.setText(dataRow.reqId);
        holder.persons.setText(dataRow.getPersons());
        holder.rooms.setText(dataRow.getRooms());
        holder.total.setText(dataRow.getTotal());
        holder.fromDate.setText(dataRow.getFromDate());




    }


    // stores and recycle views as they are scrolled

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView reqID ;
        private TextView persons ;
        private TextView rooms ;
        private TextView total ;
        private TextView fromDate;




        ViewHolder(View itemView)
        {
            super(itemView) ;

            reqID = itemView.findViewById(R.id.reqid_text) ;
            persons =  itemView.findViewById(R.id.npersons) ;
            rooms = itemView.findViewById(R.id.roomname_cancel_bookings);
            total = itemView.findViewById(R.id.total) ;
            fromDate= itemView.findViewById(R.id.fromDateTextViewValue);

            approveMainButton = itemView.findViewById(R.id.approve_validate) ;
            cancelMainButton = itemView.findViewById(R.id.cancel_validate) ;

            // make recycler row clickable
            itemView.setOnClickListener(this);


            // listen to the accept button
            approveMainButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

//                    Log.i("Date ",data.get(getAdapterPosition()).fromDate);
//
//                    // getting context from main activity
//                    // dialogSelect.show(((AppCompatActivity)context).getSupportFragmentManager(),"123");
//
//
//
//                    try {
//                        //email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                        mDatabase = FirebaseDatabase.getInstance();
//                    }
//                    catch (NullPointerException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//
//
//                    DatabaseReference myRef = mDatabase.getReference(MainActivity.BOOKING_FINAL);
//
//                    Log.i("Data =>",data.toString());
//
//                    myRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });

                    mBOOKING_FINAL.child(data.get(getAdapterPosition()).getFromDate()).child( data.get(getAdapterPosition()).getReqId()).child("booking_status").setValue(MainActivity.COMPLETED);

                    user.child( data.get(getAdapterPosition()).getReqId()).child("status").setValue(MainActivity.COMPLETED);
                    mPendingPayment.child(data.get(getAdapterPosition()).getReqId()).getRef().removeValue();

                    data.remove(getAdapterPosition());
                    Snackbar.make(v, "Request validated sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    notifyDataSetChanged();



                }
            });

            cancelMainButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mBOOKING_FINAL.child(data.get(getAdapterPosition()).getFromDate()).child( data.get(getAdapterPosition()).getReqId()).child("booking_status").setValue(MainActivity.CANCELLED);

                    user.child( data.get(getAdapterPosition()).getReqId()).child("status").setValue(MainActivity.CANCELLED);
                    mPendingPayment.child(data.get(getAdapterPosition()).getReqId()).getRef().removeValue();

                    data.remove(getAdapterPosition());
                    Snackbar.make(v, "Request cancelled sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    notifyDataSetChanged();
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
    void setClickListener(ValidateItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener ;
    }


    public interface ValidateItemClickListener
    {
        void onItemClick(View view, int position) ;
    }

}
