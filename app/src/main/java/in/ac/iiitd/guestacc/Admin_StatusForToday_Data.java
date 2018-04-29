package in.ac.iiitd.guestacc;

import android.graphics.Color;

/**
 * Created by kd on 24/4/18.
 */

public class Admin_StatusForToday_Data {
    String roomNameStatusForToday;
    int roomColor;

    public int getRoomColor() {
        return roomColor;
    }

    public void setRoomColor(int roomColor) {
        this.roomColor = roomColor;
    }

    public Admin_StatusForToday_Data(String roomNameStatusForToday, int roomColor) {
        this.roomNameStatusForToday = roomNameStatusForToday;
        this.roomColor = roomColor;
    }

    public String getRoomNameStatusForToday() {
        return roomNameStatusForToday;
    }

    public void setRoomNameStatusForToday(String roomNameStatusForToday) {
        this.roomNameStatusForToday = roomNameStatusForToday;
    }
}
