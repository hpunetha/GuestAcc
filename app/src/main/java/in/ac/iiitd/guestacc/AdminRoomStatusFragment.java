package in.ac.iiitd.guestacc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kd on 22/4/18.
 */

public class AdminRoomStatusFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View adminRoomStatus = inflater.inflate(R.layout.fragment_admin_room_status,container,false);

        return adminRoomStatus;
    }
}
