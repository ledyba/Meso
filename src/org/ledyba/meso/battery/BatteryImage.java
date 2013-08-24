package org.ledyba.meso.battery;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;

public class BatteryImage {

	private BatteryImage() {
	}

	public static Bitmap createTextBitmap(final String text) {
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

}
