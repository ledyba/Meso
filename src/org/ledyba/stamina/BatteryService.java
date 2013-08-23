package org.ledyba.stamina;

import org.ledyba.stamina.BatteryReceiver.BatteryListener;

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

	private final BroadcastReceiver batteryReceiver_ = new BatteryReceiver(new BatteryListener(){
		@Override
		public void onBatteryChanged(BatteryState state) {
			sendNotification(makeNotification(state));
		}
		
	});
	private final int YOUR_PI_REQ_CODE = 0;
	
	private Bitmap createBitmap(final String text) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTextSize(22);

		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		FontMetrics fm = paint.getFontMetrics();
		final int width = (int) paint.measureText(text);
		final int height = (int) (Math.abs(fm.top) + fm.bottom);

		// 描画領域ピッタリのビットマップ作成
		int margin = 1; // ギリギリすぎるので上下左右に多少余裕を取る
		Bitmap bmp = Bitmap.createBitmap(width + margin * 2, height + margin * 2, Bitmap.Config.ARGB_8888);

		// ビットマップからキャンバスを作成してテキスト描画
		Canvas cv = new Canvas(bmp);
		cv.drawText(text, margin, Math.abs(fm.ascent) + margin, paint);
		return bmp;
	}

	private Notification makeNotification(BatteryState bt) {
		Intent notificationIntent = new Intent(this, BatteryService.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, YOUR_PI_REQ_CODE, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		//final RemoteViews v = new RemoteViews(getPackageName(), R.layout.bar);
		final int pint = (int)bt.getLeftPercentage();
		final int resId =
				pint >= 70 ? BatteryResources.getGreen(pint):
				pint >  20 ? BatteryResources.getWhite(pint):BatteryResources.getRed(pint);
		//v.setTextViewText(R.id.text, percentage);
		return (new android.support.v4.app.NotificationCompat.Builder(BatteryService.this))
		.setContentIntent(contentIntent)
		.setAutoCancel(false)
		//.setContent(v)
		.setSmallIcon(resId)
		.setLargeIcon(createBitmap(String.format("%d%%", pint)))
		.setDefaults(0)
		.setPriority(NotificationCompat.PRIORITY_MIN)
		.setOngoing(true)
		.setWhen(System.currentTimeMillis())
		.setContentTitle(String.format("Battery Level: %d%%", pint))
		.setContentText(String.format("%s / Temperature:%.1f℃ / Voltage: %dmV", bt.getStatus(), bt.getTemperature(), bt.getVoltage()))
		.build();
	}

	private void sendNotification(final Notification noti) {
		final NotificationManager notimgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notimgr.notify(1, noti);
	}
	private void removeNotification(){
		final NotificationManager notimgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notimgr.cancelAll();
	}

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
		{
			final IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_BATTERY_CHANGED);
			registerReceiver(batteryReceiver_, filter);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryReceiver_);
		removeNotification();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
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
	private final static String STOP_INTENT = "org.ledyba.stamina.StopService";
	public static void stopResidentIfActive(Context context){
		Intent it = new Intent(context, BatteryService.class);
		it.setAction(STOP_INTENT);
		context.startService(it);
	}
}
