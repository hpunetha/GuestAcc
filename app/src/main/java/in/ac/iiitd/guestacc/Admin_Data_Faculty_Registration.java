package in.ac.iiitd.guestacc;

/**
 * Created by rakesh on 28/4/18.
 */

public class Admin_Data_Faculty_Registration
{
    String name ;
    String type ;
    String email ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    Admin_Data_Faculty_Registration(String name , String type , String email)
    {
        this.name = name ;
        this.type = type ;
        this.email = email ;
    }
}
