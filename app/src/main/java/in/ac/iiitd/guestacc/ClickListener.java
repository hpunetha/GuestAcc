package in.ac.iiitd.guestacc;

import android.view.View;

/**
 * Created by KunalChoudhary on 3/12/2018.
 */

public interface ClickListener {

    public void onItemClick(View v, int position);

    void onPositionClicked(int position);

    void onLongClicked(int position);
}
