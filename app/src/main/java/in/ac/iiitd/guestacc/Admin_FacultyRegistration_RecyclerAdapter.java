package in.ac.iiitd.guestacc;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rakesh on 28/4/18.
 */

public class Admin_FacultyRegistration_RecyclerAdapter extends RecyclerView.Adapter<Admin_FacultyRegistration_RecyclerAdapter.ViewHolder> implements Serializable
{
    View v;
    private LayoutInflater inflater ;
    public FacultyRegistrationItemClickListener mClickListener ;
    private List<Admin_Data_Faculty_Registration> data ;
    private Button cancelButton ;
    private Button acceptButton;
    Context context ;
    DatabaseReference join_requests = FirebaseDatabase.getInstance().getReference("join_requests");
    DatabaseReference faculty_staff = FirebaseDatabase.getInstance().getReference("faculty_staff");
    DatabaseReference pending = FirebaseDatabase.getInstance().getReference(MainActivity.JOIN_REQUESTS);




    Admin_FacultyRegistration_RecyclerAdapter(Context con , List<Admin_Data_Faculty_Registration> data)
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
        v = inflater.inflate(R.layout.admin_facultyregistration_recycler_row,parent,false) ;
        return new ViewHolder(v) ;
    }


    @Override
    //binds data to textview in each row
    public void onBindViewHolder(ViewHolder holder , int position)
    {

        Admin_Data_Faculty_Registration dataRow = data.get(position) ;

        holder.name.setText(dataRow.getName()) ;
        holder.email.setText(dataRow.getEmail()) ;
        holder.type.setText(dataRow.getType()) ;


    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name ;
        private TextView email ;
        private TextView type ;


        ViewHolder(final View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.faculty_name) ;
            email = itemView.findViewById(R.id.faculty_email) ;
            type = itemView.findViewById(R.id.faculty_type) ;

            cancelButton = itemView.findViewById(R.id.reject_faculty2);
            acceptButton = itemView.findViewById(R.id.accept_faculty) ;


            //itemView.setOnClickListener(this);
            acceptButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // TODO : Listen
                    Log.e("RECYCLER in accept id",join_requests.child(email.getText().toString().replace("@iiitd.ac.in","")).getKey());
                    Log.e("RECYCLER in accept name",name.getText().toString());
                    JoinRequest joinRequest = new JoinRequest();
                    joinRequest.emailid=email.getText().toString();
                    joinRequest.name=name.getText().toString();

                    Log.e("type.getText==>",type.getText().toString());
                    if(type.getText().toString().equals("faculty")) {
                        faculty_staff.child("faculty").child(email.getText().toString().replace("@iiitd.ac.in","")).setValue(joinRequest);
                    }
                    else if (type.getText().toString().equals("staff")) {
                        faculty_staff.child("staff").child(email.getText().toString().replace("@iiitd.ac.in","")).setValue(joinRequest);
                    }

                    join_requests.child(email.getText().toString().replace("@iiitd.ac.in","")).getRef().removeValue();
                    data.remove(getAdapterPosition());
                    Snackbar.make(view, "Join request added sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    if(data.size()==0)
                    {
                        ((Admin_FacultyRegistration)context).finish();
                    }

                    notifyDataSetChanged();
                    }

            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("req-------->",email.getText().toString().replace("@iiitd.ac.in",""));
                    join_requests.child(email.getText().toString().replace("@iiitd.ac.in","")).getRef().removeValue();
                    data.remove(getAdapterPosition());
                    if(data.size()==0)
                    {
                        ((Admin_FacultyRegistration)context).finish();
                    }
                    Snackbar.make(view, "Join request deleted sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Log.e("RECYCLER","Cancel clicked");
                    notifyDataSetChanged();
                }
            });

        }


    }

    void setClickListener(FacultyRegistrationItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener ;
    }


    public interface FacultyRegistrationItemClickListener
    {
        void onItemClick(View view, int position) ;
        void onButtonClick(View view,int position) ;
    }

}
