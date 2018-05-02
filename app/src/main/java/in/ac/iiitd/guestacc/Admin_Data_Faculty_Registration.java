package in.ac.iiitd.guestacc;



public class Admin_Data_Faculty_Registration
{
    String name ;
    String type ;
    String email ;
    String key ;


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

    public String getKey() {
        return key;
    }

    Admin_Data_Faculty_Registration(String name , String type , String email, String key)

    {
        this.name = name ;
        this.type = type ;
        this.email = email ;
        this.key = key ;
    }
}
