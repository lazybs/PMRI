package lazybs.pmri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        int level = extras.getInt(PowerConnectionReceiver.EXTRA_BATTERY_LOW, -1);
        boolean acCharging = extras.getBoolean(PowerConnectionReceiver.EXTRA_CHARGING_AC, false);
        boolean usbCharging = extras.getBoolean(PowerConnectionReceiver.EXTRA_CHARGING_USB, false);

        if (level > -1) {
            new AlertDialog.Builder(this)
                    .setTitle("Battery low")
                    .setMessage("You want to see nearby sockets??")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(new Intent (MainActivity2.this,MapsActivity.class));

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        if (acCharging || usbCharging) {
            new AlertDialog.Builder(this)
                    .setTitle("New socket found!")
                    .setMessage("Are you sure you want to share?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity2.this, "Shared!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNeutralButton("With extras", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent (MainActivity2.this,ShareActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
