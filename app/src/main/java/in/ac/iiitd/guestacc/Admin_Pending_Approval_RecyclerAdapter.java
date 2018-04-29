package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakesh on 19/4/18.
 */

public class Admin_Pending_Approval_RecyclerAdapter extends RecyclerView.Adapter<Admin_Pending_Approval_RecyclerAdapter.ViewHolder>
{
    View v;
    private LayoutInflater inflater ;
    private ItemClickListener mClickListener ;
    private List<Admin_Data_PendingApproval> data ;
    private Button acceptMainButton ;
    private Button cancelMainButton ;
    RelativeLayout relativeLayout ;
    Context context;



    Admin_Pending_Approval_RecyclerAdapter(Context con, List<Admin_Data_PendingApproval> data)
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
        v = inflater.inflate(R.layout.admin_pending_approval_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {

        //TODO change position

        Admin_Data_PendingApproval dataRow = data.get(position) ;    // change position
        holder.reqIDText.setText(dataRow.reqID);

        holder.projectNameText.setText(dataRow.projectName);

        holder.femalesText.setText(dataRow.females);
        holder.malesText.setText(dataRow.males);
        holder.fundedByText.setText(dataRow.fundedBy);
        holder.typeText.setText(dataRow.type);
        holder.reasonText.setText(dataRow.reason);
        holder.dateText.setText(dataRow.date);

    }


   // stores and recycle views as they are scrolled

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView reqIDText ;
        private TextView dateText ;
        private TextView typeText ;
        private TextView fundedByText ;
        private TextView reasonText ;      private TextView roomText ;
        private TextView malesText ;      private TextView projectNameText ;
        private TextView femalesText ;    private TextView guest1Text ;
        private TextView preferenceText ;          private TextView guest2Text ;



            ViewHolder(View itemView)
            {
                super(itemView) ;

                reqIDText = itemView.findViewById(R.id.ReqID) ;
                dateText =  itemView.findViewById(R.id.Date) ;
                typeText = itemView.findViewById(R.id.typeText) ;
                fundedByText = itemView.findViewById(R.id.fundedbyText) ;
                reasonText = itemView.findViewById(R.id.reasonText) ;
                malesText = itemView.findViewById(R.id.maleNo) ;
                femalesText = itemView.findViewById(R.id.femaleNo) ;
                preferenceText = itemView.findViewById(R.id.preference) ;
                roomText = itemView.findViewById(R.id.Room) ;
                projectNameText = itemView.findViewById(R.id.projectNameText) ;
                guest1Text = itemView.findViewById(R.id.GuestType1) ;
                guest2Text = itemView.findViewById(R.id.GuestType2) ;

                acceptMainButton = itemView.findViewById(R.id.accept) ;
                cancelMainButton = itemView.findViewById(R.id.cancel) ;

                relativeLayout = itemView.findViewById(R.id.expandable) ;

                // make recycler row clickable
                itemView.setOnClickListener(this);


                // listen to the accept button
                acceptMainButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                    mClickListener.onButtonClick(v,getAdapterPosition());
                        // getting context from main activity
                       // dialogSelect.show(((AppCompatActivity)context).getSupportFragmentManager(),"123");
                    }
                });


            }

            @Override
        // onclick listener for view
        public void onClick(View v)
            {
               if(mClickListener!=null)

               {
                   mClickListener.onItemClick(v,getAdapterPosition(),data.get(getAdapterPosition()).roomsData);
               }

                //Log.e("CLICK",v.toString()) ;
               // v.setClickable(false);

            }
    }

    // allows click events to be caught
    void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener ;
    }


    public interface ItemClickListener
    {
        void onItemClick(View view, int position, List<Admin_Data_PendingApproval_RoomData> adminDataPendingApprovalRoomData) ;
        void onButtonClick(View v,int position) ;
    }

}
