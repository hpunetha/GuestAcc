package in.ac.iiitd.guestacc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hpunetha on 5/3/2018.
 */
public class FacultyRegistrationActivityTest {
    @Test
    public void checkIIITDMailTest() throws Exception {

         assertTrue(FacultyRegistrationActivity.checkIIITDMailId("himanshu17016@iiitd.ac.in"));

        assertFalse(FacultyRegistrationActivity.checkIIITDMailId("hpunetha@gmail.com"));
    }

}