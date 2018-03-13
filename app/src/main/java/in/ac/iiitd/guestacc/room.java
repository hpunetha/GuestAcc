package in.ac.iiitd.guestacc;

/**
 * Created by KunalChoudhary on 3/6/2018.
 */

public class room {

    private int room_id;
    private String RoomTitle, availabitily;
    private double price;
    private int image;

    public room(int id, String title, String availabitily, double price, int guest_room_image) {
        this.room_id = id;
        this.RoomTitle = title;
        this.availabitily = availabitily;
        this.price = price;
        this.image = guest_room_image;
    }

    public int getId() {
        return room_id;
    }

    public String getTitle() {
        return RoomTitle;
    }

    public String getAvailabitily() {
        return availabitily;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }


}
