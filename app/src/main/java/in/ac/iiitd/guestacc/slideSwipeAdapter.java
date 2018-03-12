package in.ac.iiitd.guestacc;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hpunetha on 3/12/2018.
 */

public class slideSwipeAdapter extends PagerAdapter
{
    private int [] mImg_Res = {R.drawable.hp_room1,R.drawable.hp_room2,R.drawable.hp_room3,R.drawable.hp_room4,R.drawable.hp_room5};

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

}
