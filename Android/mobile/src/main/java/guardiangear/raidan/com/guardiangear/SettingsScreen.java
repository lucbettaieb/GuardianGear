package guardiangear.raidan.com.guardiangear;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class SettingsScreen extends Activity {


    String emergencyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsscrn);

        SharedPreferences prefs = this.getSharedPreferences("com.raidan.guardiangear", Context.MODE_PRIVATE);
        emergencyNumber = prefs.getString("Emergency_Phone_Number_String","");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void buddon(View v){
        if(v.getId() == findViewById(R.id.cancelButton).getId()){
            Log.d("Error","Cancel Button Pressed!!!");
            finish();
        }
        if(v.getId() == findViewById(R.id.saveButton).getId()){
            Log.d("Error","Save Button Pressed!!!");
            //TODO: make the emergencyNumber a saved piece of data
            emergencyNumber = ((EditText) findViewById(R.id.phoneText)).getText().toString();
            SharedPreferences prefs = this.getSharedPreferences("com.raidan.guardiangear", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Emergency_Phone_Number_String", emergencyNumber);
            editor.commit();
            finish();
        }
    }
}
