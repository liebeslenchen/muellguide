package mawi.muellguidems.activities;

import mawi.muellguidems.util.MapUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class FeedbackActivity extends Activity implements LocationListener {

	Spinner spinner;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		// Beim Starten setzen, sonst folgt eine NullPointerException!
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);

		spinner = (Spinner) findViewById(R.id.spnrFeedbackSubject);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.feedback_betreffoptionen,
				android.R.layout.simple_spinner_item);

		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		Intent myIntent = getIntent();
		String gpsText = myIntent.getStringExtra("gpsText");

		if (gpsText != null) {
			EditText editText = (EditText) findViewById(R.id.etFeedbackMessage);
			editText.setText(gpsText);

		}
	}

	public void onClickBtnEnterGPS(View v) {
		// Klick-Effekt anzeigen wenn Button gedrückt wird
		v.startAnimation(MuellGuideMsApplication.BUTTON_CLICK_ANIMATION);

		// Aktuellen Standort auslesen

		if (locationManager != null) {
			// Alert, wenn alle Dienste deaktiviert sind
			if (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				// AlertDialog für Feedback
				AlertDialog.Builder builder = new AlertDialog.Builder(
						FeedbackActivity.this);
				builder.setMessage(R.string.gps_keineDiensteAktiviert);
				// Hinzufügen der Buttons
				builder.setNegativeButton(R.string.zurueck,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User bricht denn Dialog ab
							}
						});
				builder.setPositiveButton(R.string.gps_einstellungenAendern,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Einstellungen aufrufen
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						});
				// Dialog anzeigen
				AlertDialog dialog = builder.create();
				dialog.show();
			} else {
				LatLng location = MapUtils.getCurrentLocation(locationManager);
				if (location != null) {
					EditText editText = (EditText) findViewById(R.id.etFeedbackMessage);
					String test = "Mein aktueller Standort: ("
							+ location.latitude + ", " + location.longitude
							+ "). ";
					editText.setText(editText.getText() + "\n" + test);
				} else {
					// Toast, wenn der Standort nicht ausgelesen werden konnte
					Toast.makeText(getBaseContext(),
							R.string.keinenStandortGefunden, Toast.LENGTH_LONG)
							.show();
				}
			}
		}

	}

	public void onClickBtnSendFeedback(View v) {
		// Klick-Effekt anzeigen wenn Button gedrückt wird
		v.startAnimation(MuellGuideMsApplication.BUTTON_CLICK_ANIMATION);

		String betreff = spinner.getItemAtPosition(
				spinner.getSelectedItemPosition()).toString();
		String text = ((EditText) findViewById(R.id.etFeedbackMessage))
				.getText().toString();

		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		// Auf E-Mail Apps einschränken
		emailIntent.setType("message/rfc822");
		emailIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "info.muellguidems@gmail.com" });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, betreff);
		emailIntent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(emailIntent, "Feedback senden..."));
	}

	@Override
	public void onLocationChanged(Location location) {
		// nichts tun!
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// nichts tun!

	}

	@Override
	public void onProviderEnabled(String provider) {
		// nichts tun!
	}

	@Override
	public void onProviderDisabled(String provider) {
		// nichts tun!
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

}
