package guardiangear.raidan.com.guardiangear;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            setContentView(R.layout.settingsscrn);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void beginCountdown(){
        //TODO: 10 second countdown before emergencyNumber is dialed
        //TODO: add obnoxious noise
        final Button bigButton = (Button) findViewById(R.id.bigButton);
        bigButton.setBackgroundColor(Color.RED);
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                String text = "Seconds remaining: " + millisUntilFinished / 1000;
                text +=  "\nuntil dialing emergency number!";
                text += "\n\n\n Press button to cancel!";
                bigButton.setText(text);
            }

            public void onFinish() {
                bigButton.setText("Calling Emergency Number!");
                dial();
            }
        }.start();
    }//end of beginCountdown method

    public void dial(){
        //TODO: dial the emergencyNumber
    }

}
