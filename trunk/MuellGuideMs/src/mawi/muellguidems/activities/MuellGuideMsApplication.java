package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Gegenstand;
import mawi.muellguidems.parseobjects.OeffungszeitenContainer;
import mawi.muellguidems.parseobjects.OeffungszeitenRecyclinghof;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.parseobjects.TestObject;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MuellGuideMsApplication extends Application {

	private static Context appContext;
	private static NetworkIdentifier netzwerkIdentifier;
	public static boolean toastForSlowConnectionAlreadyShown;

	// Animation mit der Klick-Effekte bei Buttons realisiert werden können
	public final static AlphaAnimation BUTTON_CLICK_ANIMATION = new AlphaAnimation(
			1F, 0.7F);

	@Override
	public void onCreate() {
		super.onCreate();

		appContext = getApplicationContext();

		// Objekt zum Prüfen des Netzwerkstatus
		netzwerkIdentifier = new NetworkIdentifier(appContext);

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(this);

		// Parse-Objekte registrieren
		ParseObject.registerSubclass(TestObject.class);
		ParseObject.registerSubclass(Standort.class);
		ParseObject.registerSubclass(Bezirk.class);
		ParseObject.registerSubclass(Entsorgungsart.class);
		ParseObject.registerSubclass(Gegenstand.class);
		ParseObject.registerSubclass(OeffungszeitenRecyclinghof.class);
		ParseObject.registerSubclass(OeffungszeitenContainer.class);

		// Add your initialization code here
		Parse.initialize(this, "bRJ7kGmcbXI36Ft0kHL3u2G3KGKV5aVK8kwjjZPM",
				"ZLY3xn8IXUVSBUP25DN1cgtwMlUWnPlvPovYkmCP");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}

	public static Context getContext() {
		return appContext;
	}

	public static NetworkIdentifier.NetworkCondition getNetzwerkStatus() {
		return netzwerkIdentifier.getNetworkStatus();
	}

	/**
	 * Zeigt einen Info-Toast zur aktuellen Datenverbindung an, sofern diese
	 * schlechter als 3G ist !
	 * 
	 * @param type
	 */
	public static void showToastIfNecessary(Context context,
			NetworkCondition type) {

		if (toastForSlowConnectionAlreadyShown
				& type == NetworkCondition.SLOW_MOBILE) {
			return;
		}

		switch (type) {
		case AIRPLANE_MODE:
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(R.string.alert_Flugmodus);
			// Hinzufügen der Buttons
			builder.setNegativeButton(R.string.zurueck,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User bricht denn Dialog ab
						}
					});
			builder.setPositiveButton(R.string.alert_FlugmodusChange,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Einstellungen aufrufen
							try {
								Intent flightModeIntent = new Intent(
										android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
								flightModeIntent
										.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								getContext().startActivity(flightModeIntent);
							} catch (Exception e) {
								return;
							}
						}
					});
			// Dialog anzeigen
			AlertDialog dialog = builder.create();
			dialog.show();

			toastForSlowConnectionAlreadyShown = false;
			break;
		case NO_CONNECTION:
			Toast.makeText(context, R.string.alert_Netzwerk, Toast.LENGTH_LONG)
					.show();
			toastForSlowConnectionAlreadyShown = false;
			break;
		case SLOW_MOBILE:
			Toast.makeText(context, R.string.alert_NetzwerkLangsam,
					Toast.LENGTH_LONG).show();
			toastForSlowConnectionAlreadyShown = true;
			break;
		default:
			toastForSlowConnectionAlreadyShown = false;
			break;
		}
	}
}
