package in.ac.iiitd.guestacc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by priya on 18/4/18.
 */

public class Booking  implements Serializable {
    public String booking_status;
    public String from_date;
    public String to_date;
    public String fundedby_institute_details;
    public String fundedby_project_pinvestigator;
    public String fundedby_project_pname;
    public String fundedby_personalbooking; //Self,Visitor
    public String fundedby_personal_officialbooking; //True
    public String paidby_personal_officialbooking;//Self,Visitor
    public String modification_reason;
    public String no_of_days;
    public String purpose_of_visit;
    public String request_type_personal_or_official;
    public String timestamp;

    public String raised_on;

    public String getFundedby_personalbooking() {
        return fundedby_personalbooking;
    }

    public void setFundedby_personalbooking(String fundedby_personalbooking) {
        this.fundedby_personalbooking = fundedby_personalbooking;
    }

    public String getFundedby_personal_officialbooking() {
        return fundedby_personal_officialbooking;
    }

    public void setFundedby_personal_officialbooking(String fundedby_personal_officialbooking) {
        this.fundedby_personal_officialbooking = fundedby_personal_officialbooking;
    }

    public String getPaidby_personal_officialbooking() {
        return paidby_personal_officialbooking;
    }

    public void setPaidby_personal_officialbooking(String paidby_personal_officialbooking) {
        this.paidby_personal_officialbooking = paidby_personal_officialbooking;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFundedby_institute_details() {
        return fundedby_institute_details;
    }

    public void setFundedby_institute_details(String fundedby_institute_details) {
        this.fundedby_institute_details = fundedby_institute_details;
    }

    public String getFundedby_project_pinvestigator() {
        return fundedby_project_pinvestigator;
    }

    public void setFundedby_project_pinvestigator(String fundedby_project_pinvestigator) {
        this.fundedby_project_pinvestigator = fundedby_project_pinvestigator;
    }

    public String getFundedby_project_pname() {
        return fundedby_project_pname;
    }

    public void setFundedby_project_pname(String fundedby_project_pname) {
        this.fundedby_project_pname = fundedby_project_pname;
    }


    public String getModification_reason() {
        return modification_reason;
    }

    public void setModification_reason(String modification_reason) {
        this.modification_reason = modification_reason;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getPurpose_of_visit() {
        return purpose_of_visit;
    }

    public void setPurpose_of_visit(String purpose_of_visit) {
        this.purpose_of_visit = purpose_of_visit;
    }

    public String getRequest_type_personal_or_official() {
        return request_type_personal_or_official;
    }

    public void setRequest_type_personal_or_official(String request_type_personal_or_official) {
        this.request_type_personal_or_official = request_type_personal_or_official;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTotal_booking_price() {
        return total_booking_price;
    }

    public void setTotal_booking_price(String total_booking_price) {
        this.total_booking_price = total_booking_price;
    }

    public String total_booking_price;
    public ArrayList<Guest> guests = new ArrayList<Guest>();

    public String getRaised_on() {
        return raised_on;
    }

    public void setRaised_on(String raised_on) {
        this.raised_on = raised_on;
    }
}
