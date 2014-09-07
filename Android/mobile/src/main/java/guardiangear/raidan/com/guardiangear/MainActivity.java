package guardiangear.raidan.com.guardiangear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    boolean countdownBegun = false;
    String emergencyNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences("com.raidan.guardiangear", Context.MODE_PRIVATE);
        emergencyNumber = prefs.getString("Emergency_Phone_Number_String","");
        final Button bigButton = (Button) findViewById(R.id.bigButton);
        bigButton.setText("Call Emergency Number\n"+emergencyNumber);
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
            Intent myIntent = new Intent(this, SettingsScreen.class);
            this.startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void crash(View v){
        beginCountdown();
    }

    public void beginCountdown(){
        if(countdownBegun) return;
        countdownBegun = true;
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
        Uri number = Uri.parse("tel:"+emergencyNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
}