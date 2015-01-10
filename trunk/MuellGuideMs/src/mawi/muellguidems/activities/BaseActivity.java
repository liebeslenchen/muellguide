package mawi.muellguidems.activities;

import mawi.muellguidems.util.NetworkChangedBroadcastReceiver;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentNetworkType = MuellGuideMsApplication.getNetzwerkStatus();
		MuellGuideMsApplication.showToastIfNecessary(getBaseContext(),
				currentNetworkType);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(broadcastReceiver, intentFilter);

		currentNetworkType = MuellGuideMsApplication.getNetzwerkStatus();
		MuellGuideMsApplication.showToastIfNecessary(getBaseContext(),
				currentNetworkType);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (broadcastReceiver != null) {
			unregisterReceiver(broadcastReceiver);
		}
	}
}
