package org.ledyba.meso;

import org.ledyba.meso.battery.BatteryService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	public BootReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent == null ? "(null)" : intent.getAction();
		Log.d("OnBoot", "Received: "+action);
		if( Intent.ACTION_BOOT_COMPLETED.equals(action)){
			onBoot(context);
		}
	}
	private void onBoot(Context context){
		new BatteryService().startResident(context);
	}

}
