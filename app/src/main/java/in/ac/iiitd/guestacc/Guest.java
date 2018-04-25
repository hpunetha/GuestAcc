package in.ac.iiitd.guestacc;

/**
 * Created by priya on 15/4/18.
 */

public class Guest {
    public String getItemtag() {
        return itemtag;
    }

    public void setItemtag(String itemtag) {
        this.itemtag = itemtag;
    }

    String itemtag;
    String name;
    //String Last_Name;
    String age;
    String gender;
    String associated_room_id;
    String allocated_room;
    String prefered_location;
    String room_type;

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }



    public String getAllocated_room() {
        return allocated_room;
    }

    public void setAllocated_room(String allocated_room) {
        this.allocated_room = allocated_room;
    }

    public String getPrefered_location() {
        return prefered_location;
    }

    public void setPrefered_location(String prefered_location) {
        this.prefered_location = prefered_location;
    }



//    public Guest(String itemtag,String first_Name, String last_Name, Integer age, String gender, String room) {
//        itemtag = itemtag;
//        name = first_Name;
//        Last_Name = last_Name;
//        age = age;
//        gender = gender;
//        associated_room_id = room;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAssociated_room_id() {
        return associated_room_id;
    }

    public void setAssociated_room_id(String associated_room_id) {
        this.associated_room_id = associated_room_id;
    }
}
