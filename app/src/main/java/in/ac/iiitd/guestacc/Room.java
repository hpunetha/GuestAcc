package in.ac.iiitd.guestacc;

/**
 * Created by KunalChoudhary on 3/6/2018.
 */

public class Room {

    private String room_id;
    private String RoomTitle;
    private String availability;
    private double price;
    private int image;

    public Room(String id, String title, String availability, double price, int guest_room_image) {
        this.room_id = id;
        this.RoomTitle = title;
        this.availability = availability;
        this.price = price;
        this.image = guest_room_image;
    }

    public Room()
    {
    }

    public String getId() {
        return room_id;
    }

    public String getTitle() {
        return RoomTitle;
    }

    public String getAvailability() {
        return availability;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }


    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
