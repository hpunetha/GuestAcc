package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private int mBackCount=0;

    private static final int CONST_SIGNIN = 100;
    FirebaseAuth mFbAuth;
    GoogleSignInClient mGSClient;
    public static int wrongAccFlag = 0;
    GoogleSignInAccount mLoginAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null)
        {
            wrongAccFlag = savedInstanceState.getInt("AccFlag");

        }
        Log.d("Flag for Wrong Acc : ", "onCreate Method => " + wrongAccFlag);

        SignInButton mSignInButton = (SignInButton) findViewById(R.id.signIn);

        //Sign in object
        GoogleSignInOptions mGSOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGSClient = GoogleSignIn.getClient(this,mGSOptions);
        mFbAuth = FirebaseAuth.getInstance();

        mSignInButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                signIn();
            }
        });



    }



    private void authenticateFirebase(GoogleSignInAccount mGoogleAcct) {
        Log.d("MSG : ", "Firebase authentication for :" + mGoogleAcct.getId());
        //checkIIITD(mGoogleAcct);
        finalAuthFirebase(mGoogleAcct);

    }

    private void finalAuthFirebase(GoogleSignInAccount mGoogleAcct)
    {
        AuthCredential mCreds = GoogleAuthProvider.getCredential(mGoogleAcct.getIdToken(), null);
        mFbAuth.signInWithCredential(mCreds)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            FirebaseUser mUserID = mFbAuth.getCurrentUser();
                            Log.d("MSG : ", "Successful Login => " + mUserID.getDisplayName());
                            signInSuccessful(mUserID);

                        } else {

                            Log.w("MSG : ", "Login failed => ", task.getException());
                            signInFailed();
                        }


                    }
                });
    }

    private void checkIIITD(GoogleSignInAccount mGoogleAcct)
    {
        String mEmail = mGoogleAcct.getEmail();
        String mEmailTokens[] = mEmail.split("@");
        String mDomain = mEmailTokens[mEmailTokens.length-1];
        if (mDomain.equalsIgnoreCase("iiitd.ac.in"))
        {
            finalAuthFirebase(mGoogleAcct);

        }
        else
        {
            Log.d("Just Before Login ", "CheckIIITD Method Else=> " + mGoogleAcct.getDisplayName());

            Toast.makeText(this,"Please login using the iiitd gmail account",Toast.LENGTH_SHORT).show();
            wrongAccFlag=-1;
            FirebaseAuth.getInstance().signOut();
            mGSClient=null;

            Intent mRefresh = new Intent(this, MainActivity.class);
            mRefresh.putExtra("AccFlag",wrongAccFlag);
            startActivity(mRefresh);
            this.finish(); //
        }
    }

    private void signInFailed()
    {
    }

    @Override
    public void onBackPressed() {
        mBackCount++;

        if (mBackCount==1)
        {
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();


        } else if (mBackCount>1)
        {
            this.finish();
        }
// empty so nothing happens
    }

    private void signInSuccessful(FirebaseUser mUserID)
    {
        Toast.makeText(this,"Signed in as " + mUserID.getDisplayName(),Toast.LENGTH_SHORT).show();
        Intent mLoginTypeLoad = new Intent(this, TypeLoginActivity.class);
        startActivity(mLoginTypeLoad);
    }

    @Override
    public  void onStart()
    {
        super.onStart();
//        Log.d("Flag for Wrong Acc : ", "onStart Method => " + wrongAccFlag);
//
//
//        if (mFbAuth.getCurrentUser()!=null && wrongAccFlag!=-1)
//        {
//            Log.d("Shouldnt exec new run ", "onStart Method => " + wrongAccFlag);
//            signInSuccessful(mFbAuth.getCurrentUser());
//
//        }
//        else{
//            mFbAuth.signOut();
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONST_SIGNIN)
        {
            Task<GoogleSignInAccount> mSignInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try

            {
                mLoginAcc = mSignInTask.getResult(ApiException.class);
                authenticateFirebase(mLoginAcc);
            }
            catch (ApiException e)
            {

                Log.w("MSG : ", "Google sign in failed => " + e.getMessage());

            }
        }
    }


    private void signIn()
    {
        Intent signInIntent = mGSClient.getSignInIntent();
        startActivityForResult(signInIntent, CONST_SIGNIN);

    }


}
