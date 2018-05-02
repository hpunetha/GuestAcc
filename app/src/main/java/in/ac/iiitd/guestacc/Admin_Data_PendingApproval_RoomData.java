package in.ac.iiitd.guestacc;

/**
 * Created by rakesh on 19/4/18.
 */

public class Admin_Data_PendingApproval_RoomData
{
    String guest1 ;
    String guest2 ;
    String preference ;
    String guest1AllocatedRoom ;
    String guest2AllocatedRoom ;


    public String getGuest1() {
        return guest1;
    }

    public void setGuest1(String guest1) {
        this.guest1 = guest1;
    }

    public String getGuest2() {
        return guest2;
    }

    public void setGuest2(String guest2) {
        this.guest2 = guest2;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getGuest1AllocatedRoom() {
        return guest1AllocatedRoom;
    }

    public void setGuest1AllocatedRoom(String guest1AllocatedRoom) {
        this.guest1AllocatedRoom = guest1AllocatedRoom;
    }

    public String getGuest2AllocatedRoom() {
        return guest2AllocatedRoom;
    }

    public void setGuest2AllocatedRoom(String guest2AllocatedRoom) {
        this.guest2AllocatedRoom = guest2AllocatedRoom;
    }

    Admin_Data_PendingApproval_RoomData()
    {

        preference = "BH1" ;
        guest1 = "Male" ;
        guest2 = "Female" ;
    }

    public Admin_Data_PendingApproval_RoomData(String guest1, String guest2, String preference) {

        this.guest1 = guest1;
        this.guest2 = guest2;
        this.preference = preference;

    }
}
