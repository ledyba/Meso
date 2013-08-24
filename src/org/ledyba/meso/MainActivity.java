package org.ledyba.meso;

import org.ledyba.meso.battery.BatteryReceiver;
import org.ledyba.meso.battery.BatteryReceiver.BatteryListener;
import org.ledyba.meso.battery.BatteryService;
import org.ledyba.meso.battery.BatteryState;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ConfigMaster master_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		master_ = new ConfigMaster(this);
		if(master_.isNotificationEnabled()){
			new BatteryService().startResident(this);
		}
		setStatus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private final BroadcastReceiver batteryReceiver_ = new BatteryReceiver(new BatteryListener(){
		@Override
		public void onBatteryChanged(BatteryState state) {
			setBatteryState(state);
		}
		
	});
	
	private void setBatteryState(BatteryState st){
		final TextView level = (TextView)findViewById(R.id.level);
		final TextView health = (TextView)findViewById(R.id.health);
		final TextView status = (TextView)findViewById(R.id.status);
		final TextView voltage = (TextView)findViewById(R.id.voltage);
		final TextView temp = (TextView)findViewById(R.id.temperature);
		
		level.setText(String.format("%d%%", (int)st.getLeftPercentage()));
		health.setText(String.format("%s", st.getHealth()));
		status.setText(String.format("%s", st.getStatus()));
		voltage.setText(String.format("%dmV", st.getVoltage()));
		temp.setText(String.format("%.1f℃", st.getTemperature()));
	}
	
	private void setStatus(){
		((CheckBox)findViewById(R.id.notification_button)).setChecked(master_.isNotificationEnabled());
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(batteryReceiver_);
	}

	@Override
	protected void onResume() {
		super.onResume();
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryReceiver_, filter);
	}
	
	public void onNotificationChanged(final View v){
		final CheckBox box = (CheckBox) v;
		if(!box.isChecked()){
			master_.setNotificationEnabled(false);
			BatteryService.stopResidentIfActive(this);
		}else{
			master_.setNotificationEnabled(true);
			new BatteryService().startResident(this);
		}
	}
}
