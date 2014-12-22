package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.parseobjects.TestObject;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MuellGuideMsApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(this);

		// Add your initialization code here
		Parse.initialize(this, "bRJ7kGmcbXI36Ft0kHL3u2G3KGKV5aVK8kwjjZPM",
				"ZLY3xn8IXUVSBUP25DN1cgtwMlUWnPlvPovYkmCP");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

		// Parse-Objekte registrieren
		ParseObject.registerSubclass(TestObject.class);
		ParseObject.registerSubclass(Standort.class);
	}
}
