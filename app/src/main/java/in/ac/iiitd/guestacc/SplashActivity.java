package in.ac.iiitd.guestacc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView mGuest = (TextView) findViewById(R.id.textViewGuestTitle);
        TextView mStay = (TextView) findViewById(R.id.textViewStayTitle);
        TextView mGueStay = (TextView) findViewById(R.id.textViewGueStayTitle);
        mGueStay.setAlpha(0f);
        mGuest.animate().translationXBy(200f).alpha(0f).setDuration(1200);
        mStay.animate().translationXBy(-150f).alpha(0f).setDuration(1200);
        mGueStay.animate().alpha(1f).setDuration(2600);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent loadMain = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(loadMain);
                SplashActivity.this.finish();

            }
        }, 3000);
    }
}
