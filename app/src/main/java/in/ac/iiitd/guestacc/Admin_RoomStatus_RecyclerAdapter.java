package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakesh on 30/4/18.
 */

public class Admin_RoomStatus_RecyclerAdapter extends RecyclerView.Adapter<Admin_RoomStatus_RecyclerAdapter.ViewHolder>
{
    View v;
    private LayoutInflater inflater ;
    private ItemClickListener mClickListener ;
    private List<Admin_Data_RoomStatus> data ;



    Admin_RoomStatus_RecyclerAdapter(Context con, List<Admin_Data_RoomStatus> data)
    {
        this.inflater = LayoutInflater.from(con);
        this.data = data ;


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
        v = inflater.inflate(R.layout.admin_room_status_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {



        String bh = data.get(position).getBh() ;    // change position
        String room = data.get(position).getRoom();
        holder.boysh.setText(bh);
        holder.room.setText(room);



    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView boysh;
        private TextView room ;
        ViewHolder(View itemView)
        {
            super(itemView) ;



            boysh = itemView.findViewById(R.id.admin_room_status_recycler_bh) ;
            room = itemView.findViewById(R.id.admin_status_recycler_room ) ;


            itemView.setOnClickListener(this);
        }


        @Override
        // onclick listener for view
        public void onClick(View v)
        {
            if(mClickListener!=null)

            {
                mClickListener.onItemClick(v,getAdapterPosition());
            }

            //Log.e("CLICK",v.toString()) ;
            // v.setClickable(false);

        }
    }

    void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener ;
    }


    public interface ItemClickListener
    {
        void onItemClick(View view, int position) ;
        //void onButtonClick(View v,int position) ;
    }




}
