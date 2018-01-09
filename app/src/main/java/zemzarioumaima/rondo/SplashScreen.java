package zemzarioumaima.rondo;

/**
 * Created by macbookpro on 05/08/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        Thread lTimer = new Thread() {

            public void run() {

                try {
                    int lTimer1 = 0;
                    while (lTimer1 <3000) {
                        sleep(100);
                        lTimer1 = lTimer1 + 100;
                    }
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));

                } catch (InterruptedException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        lTimer.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}