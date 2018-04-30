package in.ac.iiitd.guestacc;


public class FacultyMyBookings_Data
{
    String reqId ;
    String persons ;
    String rooms;
    String total ;
    String roomStatus;


    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setRoomStatus (String roomStatus){this.roomStatus=roomStatus;}

    FacultyMyBookings_Data(String reqId, String persons, String rooms, String total,String roomStatus)
    {
        this.persons = persons ;
        this.reqId = reqId ;
        this.rooms = rooms ;
        this.total = total ;
        this.roomStatus=roomStatus;
    }

    FacultyMyBookings_Data()
    {
        this.persons = "2" ;
        this.reqId = "1" ;
        this.rooms = "2";
        this.total = "4500" ;
        this.roomStatus="pending_approval";
    }


    public String getRoomStatus() {
        return roomStatus;
    }
}
