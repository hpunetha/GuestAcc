package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_CancelBookings_RecyclerAdapter extends RecyclerView.Adapter<Admin_CancelBookings_RecyclerAdapter.ViewHolder> {
    View v;
    private LayoutInflater inflater ;
    private CancelItemClickListener mClickListener ;
    private List<Admin_Data_CancelBookings> data ;
    private Button cancellationButton ;
    private Button cancelButton ;
    RelativeLayout relativeLayout ;
    Context context;


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

        Admin_Data_CancelBookings dataRow = data.get(0) ;

        holder.reqID.setText(dataRow.getReqID());
        holder.totalGuest.setText(dataRow.getGuests());
        holder.roomNames.setText(dataRow.getRooms());
        holder.nRooms.setText(dataRow.getNrooms());
        holder.startDate.setText(dataRow.getStartDate());
        holder.endDate.setText(dataRow.getEndDate());


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




        ViewHolder(View itemView)
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
                    // getting context from main activity
                    // dialogSelect.show(((AppCompatActivity)context).getSupportFragmentManager(),"123");
                }
            });

            cancellationButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
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
