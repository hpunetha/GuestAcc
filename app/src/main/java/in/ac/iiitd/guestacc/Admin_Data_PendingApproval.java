package in.ac.iiitd.guestacc;

import java.util.ArrayList;
import java.util.List;



/*This is data model class, it uses Admin_Data_PendingApproval_RoomData object*/
public class Admin_Data_PendingApproval
{

    String reqID ;
    String date ;
    String type ;
    String fundedBy ;
    String reason ;
    String males ;
    String females ;
    String projectName ;
    String TotalPrice;
    List<Admin_Data_PendingApproval_RoomData> roomsData;

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFundedBy() {
        return fundedBy;
    }

    public void setFundedBy(String fundedBy) {
        this.fundedBy = fundedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMales() {
        return males;
    }

    public void setMales(String males) {
        this.males = males;
    }

    public String getFemales() {
        return females;
    }

    public void setFemales(String females) {
        this.females = females;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Admin_Data_PendingApproval_RoomData> getRoomsData() {
        return roomsData;
    }

    public void setRoomsData(List<Admin_Data_PendingApproval_RoomData> roomsData) {
        this.roomsData = roomsData;
    }

    public Admin_Data_PendingApproval(String reqID, String date, String type, String fundedBy, String reason, String males, String females, String projectName, String TotalPrice, List<Admin_Data_PendingApproval_RoomData> roomsData) {
        this.roomsData = new ArrayList<>();
        this.reqID = reqID;
        this.date = date;
        this.type = type;
        this.fundedBy = fundedBy;
        this.reason = reason;
        this.males = males;
        this.females = females;
        this.projectName = projectName;
        this.roomsData = roomsData;
        this.TotalPrice = TotalPrice;
    }

    Admin_Data_PendingApproval()
    {

       /* Add two dummy rooms   */

       roomsData = new ArrayList<>();
        roomsData.add(new Admin_Data_PendingApproval_RoomData()) ;
        roomsData.add(new Admin_Data_PendingApproval_RoomData()) ;

        reqID = "1" ;
        date = "23 March - 1 April" ;
        type = "Official";
        fundedBy = "Project" ;
        males = "1" ;
        females = "1" ;
        reason = "Reason of Visit" ;
        projectName = "GuestRoom" ;
    }
}
