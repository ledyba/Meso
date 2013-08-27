package org.ledyba.meso.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {
	
	private final String TAG="BatteryReceiver";
	public interface BatteryListener {
		void onBatteryChanged(BatteryState state);
	}
	final BatteryListener listener_;

	public BatteryReceiver(BatteryListener listener) {
		listener_ = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent == null){
			return;
		}
		if (!BatteryState.isBatteryIntent(intent)) {
			final String action = intent.getAction();
			final String detail = String.format("We need \"%s\", but \"%s\" has been received.", Intent.ACTION_BATTERY_CHANGED, action);
			throw new IllegalArgumentException(detail);
		}
		Log.d(TAG, "Intent received: " + intent.toString());
		listener_.onBatteryChanged(new BatteryState(intent));
	}

}
