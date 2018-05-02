package in.ac.iiitd.guestacc;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class Admin_StatusForToday_RecyclerViewAdapter extends RecyclerView.Adapter<Admin_StatusForToday_RecyclerViewAdapter.MyViewHolder>{


    private Context mContext;
    private List<Admin_StatusForToday_Data> mData;

    public Admin_StatusForToday_RecyclerViewAdapter(Context mContext, List<Admin_StatusForToday_Data> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_admin_statusfortoday_cardviewitems,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewRoom.setText(mData.get(position).getRoomNameStatusForToday());
        //holder.mAdminStatusForTodayCardView.setCardBackgroundColor(Color.GREEN);
        holder.mAdminStatusForTodayCardView.setCardBackgroundColor(mData.get(position).getRoomColor());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTextViewRoom;
        CardView mAdminStatusForTodayCardView;
        public MyViewHolder(View itemView){
            super(itemView);

            mAdminStatusForTodayCardView = (CardView)itemView.findViewById(R.id.admin_status_for_today_cardView);
            mTextViewRoom = (TextView)itemView.findViewById(R.id.text_roomid);
        }
    }
}
