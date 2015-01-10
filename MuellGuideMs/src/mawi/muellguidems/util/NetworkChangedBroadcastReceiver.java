package mawi.muellguidems.util;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkChangedBroadcastReceiver extends BroadcastReceiver {

	Context currentContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

			this.currentContext = context;

			NetworkIdentifier networkStatus = new NetworkIdentifier(context);
			NetworkCondition type = networkStatus.getNetworkStatus();

			MuellGuideMsApplication.showToastIfNecessary(context, type);

		}

	}

}
