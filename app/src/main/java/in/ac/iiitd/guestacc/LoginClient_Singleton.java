package in.ac.iiitd.guestacc;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by hpunetha on 4/30/2018.
 */


//Reference ==>https://stackoverflow.com/questions/25031669/passing-the-googleapiclient-obj-from-one-activity-to-another

public class LoginClient_Singleton
{
    private static LoginClient_Singleton mIns =null;

    public static GoogleSignInClient mGoogleClient =null;

    private LoginClient_Singleton()
    {

    }

    public static LoginClient_Singleton getInstance(GoogleSignInClient mClient)
    {
        if(mIns==null)
        {
            mIns = new LoginClient_Singleton();
            if(mGoogleClient==null)
            {
                mGoogleClient=mClient;
            }

        }

        return mIns;

    }

    public GoogleSignInClient getClient()
    {
        return mGoogleClient;
    }


}
