package org.ledyba.meso.battery;

import org.ledyba.meso.R;

import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public final class BatteryState {
	private final int max_;
	private final int level_;
	private final String health_;
	private final String status_;
	private final String plugged_;
	private final boolean present_;
	private final int voltage_;
	private final float temperature_;
	private final String technology_;
	private final int smallIcon_;
	
	private final int cacheLeftPercentage_;
	
	private final static String TAG="BatteryState";

	public BatteryState(Intent intent){
		this.max_ = intent.getIntExtra("scale", 100);
		this.level_ = intent.getIntExtra("level", 0);
		this.health_ = healthToString(intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN));
		this.plugged_ = plugToString(intent.getIntExtra("plugged", 0));
		this.status_ = statusToString(intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN));
		this.present_ = intent.getBooleanExtra("present", false);
		this.voltage_ = intent.getIntExtra("voltage", 0);
		this.temperature_ = intent.getIntExtra("temperature", 0) / 10.0f;
		this.technology_ = intent.getStringExtra("technology");
		this.smallIcon_ = intent.getIntExtra("icon-small", 0);
		
		cacheLeftPercentage_ = getLevel()*100/getMax();
	}
	
	private final String healthToString(final int health) {
		switch (health) {
		case BatteryManager.BATTERY_HEALTH_UNKNOWN:
			return "Unknown";
		case BatteryManager.BATTERY_HEALTH_DEAD:
			return "Dead";
		case BatteryManager.BATTERY_HEALTH_GOOD:
			return "Good";
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			return "Overheat";
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			return "Over Voltage";
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			return "Unspecified Failure";
		default:
			Log.w(TAG, String.format("Unknwon Health: %d", health));
			return "????";
		}
	}
	private final String plugToString(int plug) {
		switch (plug) {
		case BatteryManager.BATTERY_PLUGGED_AC:
			return "AC";
		case BatteryManager.BATTERY_PLUGGED_USB:
			return "USB";
		case BatteryManager.BATTERY_PLUGGED_WIRELESS:
			return "Wireless";
		case 0:
			return "??????";
		default:
			Log.w(TAG, String.format("Unknwon Health: %d", plug));
			return "??????";
		}
	}
	private final String statusToString(int status){
		switch (status) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			return "Charging";
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			return "Discharging";
		case BatteryManager.BATTERY_STATUS_FULL:
			return "Full";
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			return "Not Charging";
		case BatteryManager.BATTERY_STATUS_UNKNOWN:
			return "Unknown";
		default:
			Log.w(TAG, String.format("Unknwon Status: %d", status));
			return "??????";
		}
	}

	public static boolean isBatteryIntent(final Intent it) {
		return Intent.ACTION_BATTERY_CHANGED.equals(it.getAction());
	}

	public int getMax() {
		return max_;
	}
	
	public int getLeftPercentage(){
		return cacheLeftPercentage_;
	}

	public int getLevel() {
		return level_;
	}

	public String getHealth() {
		return health_;
	}

	public String getStatus() {
		return status_;
	}

	public String getPlugged() {
		return plugged_;
	}

	public boolean isPresent() {
		return present_;
	}

	public int getVoltage() {
		return voltage_;
	}

	public float getTemperature() {
		return temperature_;
	}

	public String getTechnology() {
		return technology_;
	}

	public int getSmallIcon() {
		return smallIcon_;
	}

}

enum BatteryResources {
	_0(R.drawable.img0_red, R.drawable.img0_green, R.drawable.img0_white),
	_1(R.drawable.img1_red, R.drawable.img1_green, R.drawable.img1_white),
	_2(R.drawable.img2_red, R.drawable.img2_green, R.drawable.img2_white),
	_3(R.drawable.img3_red, R.drawable.img3_green, R.drawable.img3_white),
	_4(R.drawable.img4_red, R.drawable.img4_green, R.drawable.img4_white),
	_5(R.drawable.img5_red, R.drawable.img5_green, R.drawable.img5_white),
	_6(R.drawable.img6_red, R.drawable.img6_green, R.drawable.img6_white),
	_7(R.drawable.img7_red, R.drawable.img7_green, R.drawable.img7_white),
	_8(R.drawable.img8_red, R.drawable.img8_green, R.drawable.img8_white),
	_9(R.drawable.img9_red, R.drawable.img9_green, R.drawable.img9_white),
	_10(R.drawable.img10_red, R.drawable.img10_green, R.drawable.img10_white),
	_11(R.drawable.img11_red, R.drawable.img11_green, R.drawable.img11_white),
	_12(R.drawable.img12_red, R.drawable.img12_green, R.drawable.img12_white),
	_13(R.drawable.img13_red, R.drawable.img13_green, R.drawable.img13_white),
	_14(R.drawable.img14_red, R.drawable.img14_green, R.drawable.img14_white),
	_15(R.drawable.img15_red, R.drawable.img15_green, R.drawable.img15_white),
	_16(R.drawable.img16_red, R.drawable.img16_green, R.drawable.img16_white),
	_17(R.drawable.img17_red, R.drawable.img17_green, R.drawable.img17_white),
	_18(R.drawable.img18_red, R.drawable.img18_green, R.drawable.img18_white),
	_19(R.drawable.img19_red, R.drawable.img19_green, R.drawable.img19_white),
	_20(R.drawable.img20_red, R.drawable.img20_green, R.drawable.img20_white),
	_21(R.drawable.img21_red, R.drawable.img21_green, R.drawable.img21_white),
	_22(R.drawable.img22_red, R.drawable.img22_green, R.drawable.img22_white),
	_23(R.drawable.img23_red, R.drawable.img23_green, R.drawable.img23_white),
	_24(R.drawable.img24_red, R.drawable.img24_green, R.drawable.img24_white),
	_25(R.drawable.img25_red, R.drawable.img25_green, R.drawable.img25_white),
	_26(R.drawable.img26_red, R.drawable.img26_green, R.drawable.img26_white),
	_27(R.drawable.img27_red, R.drawable.img27_green, R.drawable.img27_white),
	_28(R.drawable.img28_red, R.drawable.img28_green, R.drawable.img28_white),
	_29(R.drawable.img29_red, R.drawable.img29_green, R.drawable.img29_white),
	_30(R.drawable.img30_red, R.drawable.img30_green, R.drawable.img30_white),
	_31(R.drawable.img31_red, R.drawable.img31_green, R.drawable.img31_white),
	_32(R.drawable.img32_red, R.drawable.img32_green, R.drawable.img32_white),
	_33(R.drawable.img33_red, R.drawable.img33_green, R.drawable.img33_white),
	_34(R.drawable.img34_red, R.drawable.img34_green, R.drawable.img34_white),
	_35(R.drawable.img35_red, R.drawable.img35_green, R.drawable.img35_white),
	_36(R.drawable.img36_red, R.drawable.img36_green, R.drawable.img36_white),
	_37(R.drawable.img37_red, R.drawable.img37_green, R.drawable.img37_white),
	_38(R.drawable.img38_red, R.drawable.img38_green, R.drawable.img38_white),
	_39(R.drawable.img39_red, R.drawable.img39_green, R.drawable.img39_white),
	_40(R.drawable.img40_red, R.drawable.img40_green, R.drawable.img40_white),
	_41(R.drawable.img41_red, R.drawable.img41_green, R.drawable.img41_white),
	_42(R.drawable.img42_red, R.drawable.img42_green, R.drawable.img42_white),
	_43(R.drawable.img43_red, R.drawable.img43_green, R.drawable.img43_white),
	_44(R.drawable.img44_red, R.drawable.img44_green, R.drawable.img44_white),
	_45(R.drawable.img45_red, R.drawable.img45_green, R.drawable.img45_white),
	_46(R.drawable.img46_red, R.drawable.img46_green, R.drawable.img46_white),
	_47(R.drawable.img47_red, R.drawable.img47_green, R.drawable.img47_white),
	_48(R.drawable.img48_red, R.drawable.img48_green, R.drawable.img48_white),
	_49(R.drawable.img49_red, R.drawable.img49_green, R.drawable.img49_white),
	_50(R.drawable.img50_red, R.drawable.img50_green, R.drawable.img50_white),
	_51(R.drawable.img51_red, R.drawable.img51_green, R.drawable.img51_white),
	_52(R.drawable.img52_red, R.drawable.img52_green, R.drawable.img52_white),
	_53(R.drawable.img53_red, R.drawable.img53_green, R.drawable.img53_white),
	_54(R.drawable.img54_red, R.drawable.img54_green, R.drawable.img54_white),
	_55(R.drawable.img55_red, R.drawable.img55_green, R.drawable.img55_white),
	_56(R.drawable.img56_red, R.drawable.img56_green, R.drawable.img56_white),
	_57(R.drawable.img57_red, R.drawable.img57_green, R.drawable.img57_white),
	_58(R.drawable.img58_red, R.drawable.img58_green, R.drawable.img58_white),
	_59(R.drawable.img59_red, R.drawable.img59_green, R.drawable.img59_white),
	_60(R.drawable.img60_red, R.drawable.img60_green, R.drawable.img60_white),
	_61(R.drawable.img61_red, R.drawable.img61_green, R.drawable.img61_white),
	_62(R.drawable.img62_red, R.drawable.img62_green, R.drawable.img62_white),
	_63(R.drawable.img63_red, R.drawable.img63_green, R.drawable.img63_white),
	_64(R.drawable.img64_red, R.drawable.img64_green, R.drawable.img64_white),
	_65(R.drawable.img65_red, R.drawable.img65_green, R.drawable.img65_white),
	_66(R.drawable.img66_red, R.drawable.img66_green, R.drawable.img66_white),
	_67(R.drawable.img67_red, R.drawable.img67_green, R.drawable.img67_white),
	_68(R.drawable.img68_red, R.drawable.img68_green, R.drawable.img68_white),
	_69(R.drawable.img69_red, R.drawable.img69_green, R.drawable.img69_white),
	_70(R.drawable.img70_red, R.drawable.img70_green, R.drawable.img70_white),
	_71(R.drawable.img71_red, R.drawable.img71_green, R.drawable.img71_white),
	_72(R.drawable.img72_red, R.drawable.img72_green, R.drawable.img72_white),
	_73(R.drawable.img73_red, R.drawable.img73_green, R.drawable.img73_white),
	_74(R.drawable.img74_red, R.drawable.img74_green, R.drawable.img74_white),
	_75(R.drawable.img75_red, R.drawable.img75_green, R.drawable.img75_white),
	_76(R.drawable.img76_red, R.drawable.img76_green, R.drawable.img76_white),
	_77(R.drawable.img77_red, R.drawable.img77_green, R.drawable.img77_white),
	_78(R.drawable.img78_red, R.drawable.img78_green, R.drawable.img78_white),
	_79(R.drawable.img79_red, R.drawable.img79_green, R.drawable.img79_white),
	_80(R.drawable.img80_red, R.drawable.img80_green, R.drawable.img80_white),
	_81(R.drawable.img81_red, R.drawable.img81_green, R.drawable.img81_white),
	_82(R.drawable.img82_red, R.drawable.img82_green, R.drawable.img82_white),
	_83(R.drawable.img83_red, R.drawable.img83_green, R.drawable.img83_white),
	_84(R.drawable.img84_red, R.drawable.img84_green, R.drawable.img84_white),
	_85(R.drawable.img85_red, R.drawable.img85_green, R.drawable.img85_white),
	_86(R.drawable.img86_red, R.drawable.img86_green, R.drawable.img86_white),
	_87(R.drawable.img87_red, R.drawable.img87_green, R.drawable.img87_white),
	_88(R.drawable.img88_red, R.drawable.img88_green, R.drawable.img88_white),
	_89(R.drawable.img89_red, R.drawable.img89_green, R.drawable.img89_white),
	_90(R.drawable.img90_red, R.drawable.img90_green, R.drawable.img90_white),
	_91(R.drawable.img91_red, R.drawable.img91_green, R.drawable.img91_white),
	_92(R.drawable.img92_red, R.drawable.img92_green, R.drawable.img92_white),
	_93(R.drawable.img93_red, R.drawable.img93_green, R.drawable.img93_white),
	_94(R.drawable.img94_red, R.drawable.img94_green, R.drawable.img94_white),
	_95(R.drawable.img95_red, R.drawable.img95_green, R.drawable.img95_white),
	_96(R.drawable.img96_red, R.drawable.img96_green, R.drawable.img96_white),
	_97(R.drawable.img97_red, R.drawable.img97_green, R.drawable.img97_white),
	_98(R.drawable.img98_red, R.drawable.img98_green, R.drawable.img98_white),
	_99(R.drawable.img99_red, R.drawable.img99_green, R.drawable.img99_white),
	_100(R.drawable.img100_red, R.drawable.img100_green, R.drawable.img100_white);
	public final int RedResourceId;
	public final int GreenResourceId;
	public final int WhiteResourceId;
	private BatteryResources(int red, int green, int white) {
		RedResourceId = red;
		GreenResourceId = green;
		WhiteResourceId = white;
	}
	static public int getWhite(int i){
		return BatteryResources.values()[i].WhiteResourceId;
	}
	static public int getGreen(int i){
		return BatteryResources.values()[i].GreenResourceId;
	}
	static public int getRed(int i){
		return BatteryResources.values()[i].RedResourceId;
	}
}

