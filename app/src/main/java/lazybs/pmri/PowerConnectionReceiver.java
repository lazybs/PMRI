package lazybs.pmri;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.preference.PreferenceManager;

public class PowerConnectionReceiver extends BroadcastReceiver {
    static final String EXTRA_BATTERY_LOW = "lazybs.pmri..EXTRA_BATTERY_LOW";
    static final String EXTRA_CHARGING_USB = "lazybs.pmri..EXTRA_CHARGING_USB";
    static final String EXTRA_CHARGING_AC = "lazybs.pmri..EXTRA_CHARGING_AC";

    public PowerConnectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                             status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int storedPreference = preferences.getInt("storedInt", 30);

        if (level < storedPreference) {
            sendIntent.putExtra(this.EXTRA_BATTERY_LOW, level);
        }
        if (isCharging) {
                sendIntent.putExtra(this.EXTRA_CHARGING_USB, usbCharge);
                sendIntent.putExtra(this.EXTRA_CHARGING_AC, acCharge);
        }
        if (level < storedPreference || isCharging) {
            sendIntent.setClassName("lazybs.pmri", "lazybs.pmri.MainActivity2");
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
        }
    }

}
