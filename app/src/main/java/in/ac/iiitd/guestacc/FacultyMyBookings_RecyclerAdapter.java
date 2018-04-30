package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class FacultyMyBookings_RecyclerAdapter extends RecyclerView.Adapter<FacultyMyBookings_RecyclerAdapter.ViewHolder>
{
    View v;
    private LayoutInflater inflater ;
    private ValidateItemClickListener mClickListener ;
    private List<FacultyMyBookings_Data> data ;
    private Button approveMainButton ;
    private Button cancelMainButton ;
    RelativeLayout relativeLayout ;
    Context context;

    DatabaseReference mFireBaseReference;

    FacultyMyBookings_RecyclerAdapter(Context con, List<FacultyMyBookings_Data> data)
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
        v = inflater.inflate(R.layout.activity_faculty_my_bookings_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {
        //TODO : change position

        FacultyMyBookings_Data dataRow = data.get(position) ;
        holder.reqID.setText(dataRow.reqId);
        holder.persons.setText(dataRow.getPersons());
        holder.rooms.setText(dataRow.getRooms());
        holder.total.setText(dataRow.getTotal());
        holder.roomstatus.setText(dataRow.getRoomStatus());




    }


    // stores and recycle views as they are scrolled

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView reqID ;
        private TextView persons ;
        private TextView rooms ;
        private TextView total ;
        private TextView roomstatus;


        ViewHolder(View itemView)
        {
            super(itemView) ;

            reqID = itemView.findViewById(R.id.reqid_textfaculty) ;
            persons =  itemView.findViewById(R.id.npersonsfaculty) ;
            rooms = itemView.findViewById(R.id.roomname_cancel_bookingsfaculty) ;
            total = itemView.findViewById(R.id.totalPrice) ;
            roomstatus = itemView.findViewById(R.id.textViewStatus);



            // make recycler row clickable
            itemView.setOnClickListener(this);



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
