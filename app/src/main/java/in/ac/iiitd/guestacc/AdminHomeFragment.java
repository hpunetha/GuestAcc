package in.ac.iiitd.guestacc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kd on 15/4/18.
 */

public class AdminHomeFragment extends Fragment {

    CardView adminHomeCardView;
    TextView mTextViewBookedRooms, mTextViewAvailableRooms;

    //Database

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminHomeView = inflater.inflate(R.layout.fragment_admin_home,container,false);

        mTextViewBookedRooms = (TextView)adminHomeView.findViewById(R.id.adminBookedRooms);
        mTextViewAvailableRooms = (TextView)adminHomeView.findViewById(R.id.adminRoomsAvailable);



        adminHomeCardView = (CardView) adminHomeView.findViewById(R.id.adminHomeCardView1);

        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView2);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView3);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        adminHomeCardView = (CardView)adminHomeView.findViewById(R.id.adminHomeCardView4);
        adminHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(AdminHomeActivity.this, Item1.class);
                //startActivity(intent);
            }
        });

        return adminHomeView;
    }
}
