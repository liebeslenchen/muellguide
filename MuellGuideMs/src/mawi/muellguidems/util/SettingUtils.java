package mawi.muellguidems.util;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.content.Context;
import android.provider.Settings;

public class SettingUtils {
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;

	}

	public static boolean isConnectedToNetwork(Context context) {
		NetworkCondition condition = MuellGuideMsApplication
				.getNetzwerkStatus();
		if (condition == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
			return false;
		}
		return true;

	}
}
