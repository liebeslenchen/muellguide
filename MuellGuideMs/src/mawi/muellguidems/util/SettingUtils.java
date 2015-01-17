package mawi.muellguidems.util;

import android.content.Context;
import android.provider.Settings;

public class SettingUtils {
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;

	}
}
