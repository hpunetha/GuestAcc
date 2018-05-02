package in.ac.iiitd.guestacc;

/**
 * Created by rakesh on 30/4/18.
 */

public class Admin_Data_RoomStatus
{
    String type ;
    String room ;
    String color ;

    public String getType() {
        return type;
    }

    public void setType(String bh) {
        this.type = bh;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    Admin_Data_RoomStatus(String type , String room , String color)
    {
        this.type = type ;
        this.color = color ;
        this.room = room ;
    }
}
