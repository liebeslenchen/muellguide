package mawi.muellguidems.activities;

import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FeedbackActivity extends BaseActivity {

	Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

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
	}

	public void onClickBtnSendFeedback(View v) {

		NetworkCondition netzwerkStatus = MuellGuideMsApplication
				.getNetzwerkStatus();
		if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
			FeedbackActivity.this.showToastIfNecessary(netzwerkStatus);
			return;
		}

		String betreff = spinner.getItemAtPosition(
				spinner.getSelectedItemPosition()).toString();
		String text = ((EditText) findViewById(R.id.etFeedbackMessage))
				.getText().toString();

		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "info.muellguidems@gmail.com" });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, betreff);
		emailIntent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(emailIntent, "Feedback senden..."));
	}
}
