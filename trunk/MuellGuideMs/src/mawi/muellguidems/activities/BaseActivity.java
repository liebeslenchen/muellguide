package mawi.muellguidems.activities;

import mawi.muellguidems.util.AirplaneModeChangedBroadcastReceiver;
import mawi.muellguidems.util.NetworkChangedBroadcastReceiver;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Diese Klasse dient als Basis-Klasse für die anderen Activities und soll Logik
 * implementieren, die für alle oder den Großteil aller Activities gilt (z.B.
 * Einsatz von allgemeinen BroadcastReceivern). Dadurch soll Mehraufwand bzw.
 * redundanter Code vermieden werden.
 */
public class BaseActivity extends Activity {

	private final NetworkChangedBroadcastReceiver networkBroadcastReceiver = new NetworkChangedBroadcastReceiver();
	private final AirplaneModeChangedBroadcastReceiver airplaneModeBroadcastReceiver = new AirplaneModeChangedBroadcastReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		intentFilter.addAction("android.intent.action.AIRPLANE_MODE");

		registerReceiver(networkBroadcastReceiver, intentFilter);
		registerReceiver(airplaneModeBroadcastReceiver, intentFilter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterAllBroadcastReceiver();
	}

	private void unregisterAllBroadcastReceiver() {
		if (networkBroadcastReceiver != null) {
			unregisterReceiver(networkBroadcastReceiver);

		}

		if (airplaneModeBroadcastReceiver != null) {
			unregisterReceiver(airplaneModeBroadcastReceiver);
		}
	}
}
