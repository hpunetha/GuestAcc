package in.ac.iiitd.guestacc;

import java.io.Serializable;

/**
 * Created by priya on 1/5/18.
 */

public class JoinRequest implements Serializable {
    public String emailid;
    public String name;
    public String timestamp;
    public String type;
    public String year_of_joining;
}
