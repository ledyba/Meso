package org.ledyba.meso.battery;

import org.ledyba.meso.battery.BatteryReceiver.BatteryListener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BatteryService extends Service {
	private final static String TAG = "BatteryService";
	private final int YOUR_PI_REQ_CODE = 0;
	private BatteryNotifer notifer_;

	private final BroadcastReceiver batteryReceiver_ = new BatteryReceiver(new BatteryListener(){
		@Override
		public void onBatteryChanged(BatteryState state) {
			notifer_.notifyBatteryState( state );
		}
		
	});
	public BatteryService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "bind from: " + intent.toString());
		return binder;
	}

	protected final IBinder binder = new Binder() {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		this.notifer_ = new BatteryNotifer(this, YOUR_PI_REQ_CODE);
		{
			final IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(batteryReceiver_, filter);
			Log.d(TAG, "Intent filter registered: "+filter.toString());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryReceiver_);
		notifer_.removeNotifications();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand: "+intent.getAction());
		if(STOP_INTENT.equals(intent.getAction())){
			stopResident();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public BatteryService startResident(Context context) {
		Log.d(TAG, "Service has been started from: " + context.toString());
		Intent intent = new Intent(context, this.getClass());
		intent.putExtra("type", "start");
		context.startService(intent);

		return this;
	}

	public void stopResident() {
		// サービス自体を停止
		stopSelf();
	}
	private final static String STOP_INTENT = "org.ledyba.meso.StopService";
	public static void stopResidentIfActive(Context context){
		Intent it = new Intent(context, BatteryService.class);
		it.setAction(STOP_INTENT);
		context.startService(it);
	}
}
