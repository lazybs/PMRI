package lazybs.pmri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SettingsActivity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity3);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity3.this);
        int storedPreference = preferences.getInt("storedInt", 30);
        EditText e = (EditText) findViewById(R.id.editText2);
        e.setText("" + storedPreference);

        Button button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity3.this);
                EditText e = (EditText) findViewById(R.id.editText2);
                int storedPreference = Integer.parseInt(e.getText().toString());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("storedInt", storedPreference); // value to store
                editor.commit();
                finish();
            }
        });
    }

}
