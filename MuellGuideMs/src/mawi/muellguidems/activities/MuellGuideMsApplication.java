package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Gegenstand;
import mawi.muellguidems.parseobjects.OeffungszeitenContainer;
import mawi.muellguidems.parseobjects.OeffungszeitenRecyclinghof;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.parseobjects.TestObject;
import mawi.muellguidems.util.NetworkIdentifier;
import android.app.Application;
import android.content.Context;
import android.view.animation.AlphaAnimation;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MuellGuideMsApplication extends Application {

	private static Context context;
	private static NetworkIdentifier netzwerkIdentifier;
	public static boolean toastForSlowConnectionAlreadyShown;

	// Animation mit der Klick-Effekte bei Buttons realisiert werden können
	public final static AlphaAnimation BUTTON_CLICK_ANIMATION = new AlphaAnimation(
			1F, 0.7F);

	@Override
	public void onCreate() {
		super.onCreate();

		context = this;

		// Objekt zum Prüfen des Netzwerkstatus
		netzwerkIdentifier = new NetworkIdentifier(context);

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
		return context;
	}

	public static NetworkIdentifier.NetworkCondition getNetzwerkStatus() {
		return netzwerkIdentifier.getNetworkStatus();
	}
}
