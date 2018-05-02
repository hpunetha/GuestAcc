package in.ac.iiitd.guestacc;



public class Admin_Data_CancelBookings
{
    public Admin_Data_CancelBookings(String reqID, String rooms, String guests, String nrooms, String startDate, String endDate) {
        this.reqID = reqID;
        this.rooms = rooms;
        this.guests = guests;
        this.nrooms = nrooms;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    String reqID;
    String rooms;
    String guests;
    String nrooms ;
    String startDate ;
    String reason;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    String endDate  ;



    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getNrooms() {
        return nrooms;
    }

    public void setNrooms(String nrooms) {
        this.nrooms = nrooms;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
    //    Admin_Data_CancelBookings()
//    {
//        reqID = "1" ;
//        // TODO : add a separate data type
//
//        rooms = "BH1 BH2";
//        guests="2" ;
//        nrooms = "2" ;
//        startDate = "29 April" ;
//        endDate = "2 May" ;
//
//    }
}
