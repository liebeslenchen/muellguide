package mawi.muellguidems.activities;

import mawi.muellguidems.util.NetworkChangedBroadcastReceiver;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Diese Klasse dient als Basis-Klasse für die anderen Activities und soll Logik
 * implementieren, die für alle oder den Großteil aller Activities gilt (z.B.
 * Netzwerkprüfung). Dadurch soll Mehraufwand bzw. redundanter Code vermieden
 * werden.
 */
public class BaseActivity extends Activity {

	private final NetworkChangedBroadcastReceiver broadcastReceiver = new NetworkChangedBroadcastReceiver();

	// Aktueller Netzwerk-Status
	private NetworkCondition currentNetworkType;

	/**
	 * Gibt an, ob der Toast für langsame Internetverbindung bereits einmal
	 * angezeigt wurde. Falls ja, soll dieser nicht nochmal angezeigt werden,
	 * bis zwischendurch eine schnellere Verbindung aktiv war.
	 */
	private boolean toastForSlowConnectionAlreadyShown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentNetworkType = MuellGuideMsApplication.getNetzwerkStatus();
		showToastIfNecessary(currentNetworkType);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter();
		registerReceiver(broadcastReceiver, intentFilter);
		currentNetworkType = MuellGuideMsApplication.getNetzwerkStatus();
		showToastIfNecessary(currentNetworkType);

	}

	/**
	 * Zeigt einen Info-Toast zur aktuellen Datenverbindung an, sofern diese
	 * schlechter als 3G ist !
	 * 
	 * @param type
	 */
	protected void showToastIfNecessary(NetworkCondition type) {

		if (toastForSlowConnectionAlreadyShown
				&& type == NetworkCondition.SLOW_MOBILE)
			return;

		switch (type) {
		case NO_CONNECTION:
			Toast.makeText(
					getBaseContext(),
					"Achtung! Es besteht momentan KEINE  Netzwerkverbindung! Die App kann nicht ohne eine bestehende Verbindung ausgeführt werden!",
					Toast.LENGTH_LONG).show();
			toastForSlowConnectionAlreadyShown = false;
			break;
		case SLOW_MOBILE:
			Toast.makeText(
					getBaseContext(),
					"Achtung! Ihre Netzwerkverbindung ist momentan sehr langsam. Dadurch werden einige Daten u.U. langsamer geladen!",
					Toast.LENGTH_LONG).show();
			toastForSlowConnectionAlreadyShown = true;
		default:
			toastForSlowConnectionAlreadyShown = false;
			break;
		}
	}
}
