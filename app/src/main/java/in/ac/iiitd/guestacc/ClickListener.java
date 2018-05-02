package in.ac.iiitd.guestacc;

import android.view.View;


public interface ClickListener {

    public void onItemClick(View v, int position);

    void onPositionClicked(int position);

    void onLongClicked(int position);
}
