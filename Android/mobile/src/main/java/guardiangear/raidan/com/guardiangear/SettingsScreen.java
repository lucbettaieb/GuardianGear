package guardiangear.raidan.com.guardiangear;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;



public class SettingsScreen extends Activity {


    int emergencyNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = sharedPref.getInt("Emergency Phone Number",8675309);
        ((EditText)findViewById(R.id.phoneText)).setText(defaultValue);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void buddon(View v){
        if(v.getId() == findViewById(R.id.cancelButton).getId()){
            finish();
        }
        if(v.getId() == findViewById(R.id.saveButton).getId()){
            //TODO: make the emergencyNumber a saved piece of data
            emergencyNumber = Integer.parseInt(((EditText) findViewById(R.id.phoneText)).getText().toString());
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Emergency Phone Number", emergencyNumber);
            editor.commit();
            finish();
        }
    }
}
