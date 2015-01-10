package mawi.muellguidems.util;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class AirplaneModeChangedBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(intent.ACTION_AIRPLANE_MODE_CHANGED)) {
			if (isAirplaneModeOn(context)) {
				MuellGuideMsApplication.showToastIfNecessary(context,
						NetworkCondition.AIRPLANE_MODE);
			}
		}

	}

	/**
	 * Gibt an, ob der FLUGMODUS aktiviert ist. Funktioniert nur unter Version
	 * 4.2 (!) Zur Info: Offenbar gibt es bei der Abfrage des FLUGMODUS einen
	 * Unterschied zwischen Versionen unterhalb 4.2 und oberhalb dieser Version.
	 * Die aktuelle Methode in diesem Projekt unterstützt die Abfrage unterhalb
	 * dieser Version. Um auf Smartphones ab Version 4.2 den FLUGMODUS abfragen
	 * zu können, benötigt man entsprechende Bibliotheken, die offenbar in
	 * 4.0-er Projekt nicht zur Verfügung stehen. Somit funktioniert diese
	 * Methode momentan nur bei Android-Versionen unterhalb 4.2 !
	 * 
	 * @param context
	 * @return true if enabled.
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static boolean isAirplaneModeOn(Context context) {

		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;

	}

}
