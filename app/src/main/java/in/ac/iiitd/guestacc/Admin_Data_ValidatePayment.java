package in.ac.iiitd.guestacc;

/**
 * Created by rakesh on 22/4/18.
 */

public class Admin_Data_ValidatePayment
{
    String reqId ;
    String persons ;
    String rooms;
    String total ;


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

    Admin_Data_ValidatePayment(String reqId, String persons, String rooms, String total)
    {
        this.persons = persons ;
        this.reqId = reqId ;
        this.rooms = rooms ;
        this.total = total ;
    }

    Admin_Data_ValidatePayment()
    {
        this.persons = "2" ;
        this.reqId = "1" ;
        this.rooms = "2";
        this.total = "4500" ;
    }



}
