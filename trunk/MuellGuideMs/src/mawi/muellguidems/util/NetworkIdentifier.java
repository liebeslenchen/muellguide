package mawi.muellguidems.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkIdentifier {
	private ConnectivityManager connectivityManager;

	public static enum NetworkCondition {
		NO_CONNECTION, AIRPLANE_MODE, SLOW_MOBILE, FAST_MOBILE, WIFI, UNKNOWN;
	}

	public NetworkIdentifier(Context context) {
		connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public NetworkCondition getNetworkStatus() {

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		NetworkCondition networkCondition = NetworkCondition.UNKNOWN;

		@SuppressWarnings("unused")
		boolean isConnected = false;

		if (info != null && info.isConnected()) {

			final int type = info.getType();
			final int subtype = info.getSubtype();

			networkCondition = NetworkCondition.UNKNOWN;

			switch (type) {
			case ConnectivityManager.TYPE_WIFI: {
				networkCondition = NetworkCondition.WIFI;
				break;
			}
			case ConnectivityManager.TYPE_MOBILE:
			case ConnectivityManager.TYPE_MOBILE_DUN:
			case ConnectivityManager.TYPE_MOBILE_HIPRI:
			case ConnectivityManager.TYPE_MOBILE_MMS:
			case ConnectivityManager.TYPE_MOBILE_SUPL: {
				switch (subtype) {
				case TelephonyManager.NETWORK_TYPE_LTE:
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: {
					networkCondition = NetworkCondition.FAST_MOBILE;
					break;
				}
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_IDEN:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				default: {
					networkCondition = NetworkCondition.SLOW_MOBILE;
					break;
				}
				}
			}
			default:
				break;
			}
		} else if (info == null || !info.isConnected()) {

			networkCondition = NetworkCondition.NO_CONNECTION;
		}

		return networkCondition;
	}

}
