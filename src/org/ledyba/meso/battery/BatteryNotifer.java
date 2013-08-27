package org.ledyba.meso.battery;

import java.util.Locale;

import org.ledyba.meso.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public final class BatteryNotifer {
	
	private final Context context_;
	private final int pendingIntentCode_;

	public BatteryNotifer(final Context ctx, final int pendingIntentCode) {
		this.context_ = ctx;
		this.pendingIntentCode_ = pendingIntentCode;
	}
	private final NotificationManager getNotificationManager(){
		return (NotificationManager) context_.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	private final PendingIntent createContentIntent(){
		final Intent notificationIntent = new Intent(context_, MainActivity.class);
		return PendingIntent.getActivity(context_, pendingIntentCode_, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	}
	
	private final int getNotificationIconResouceId(final int leftPercentage){
		return
				leftPercentage >= 70 ? BatteryResources.getGreen(leftPercentage):
				leftPercentage >= 20 ? BatteryResources.getWhite(leftPercentage):
									   BatteryResources.getRed(leftPercentage);
	}
	
	private final Bitmap createPercentageBitmap( final int leftPercentage ){
		return BatteryImage.createTextBitmap(String.format(Locale.JAPANESE, "%d%%", leftPercentage));
	}
	
	private final String createContentTitle( final BatteryState bs ){
		return String.format(Locale.JAPANESE, "Battery Level: %d%%", bs.getLeftPercentage());
	}
	
	private final String createContentText( final BatteryState bs ){
		return String.format(Locale.JAPANESE, "%s / Temp:%.1f℃ / Volt: %dmV", bs.getStatus(), bs.getTemperature(), bs.getVoltage());
	}
	
	public final void notifyBatteryState( final BatteryState bs ) {
		sendNotification( buildNotification(bs) );
	}

	public final void sendNotification(final Notification noti) {
		getNotificationManager().notify(1, noti);
	}
	public final void removeNotifications(){
		getNotificationManager().cancelAll();
	}

	private final Notification buildNotification(final BatteryState bs){
		
		final NotificationCompat.Builder builder = (new android.support.v4.app.NotificationCompat.Builder(context_));
		builder.setContentIntent(createContentIntent())
		.setAutoCancel(false)
		.setSmallIcon( getNotificationIconResouceId(bs.getLeftPercentage()) )
		.setDefaults(0)
		.setPriority(NotificationCompat.PRIORITY_MAX)
		.setOngoing(true)
		.setWhen(System.currentTimeMillis())
		.setContentTitle( createContentTitle(bs) )
		.setContentText( createContentText(bs) );
		if( Build.VERSION.SDK_INT >= 14 ) {
			builder.setLargeIcon( createPercentageBitmap( bs.getLeftPercentage() ));
		}
		return builder.build();
	}

}
