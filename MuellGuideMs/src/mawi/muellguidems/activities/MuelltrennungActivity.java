package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.adapter.CustomMuelltrennungExpandableAdapter;
import mawi.muellguidems.util.DAO;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MuelltrennungActivity extends BaseActivity {

	ExpandableListView elvMuelltrennung;
	CustomMuelltrennungExpandableAdapter adapter;
	EditText etSearchMuelltrennung;
	AsyncParseLoader asyncTask;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muelltrennung);

		elvMuelltrennung = (ExpandableListView) findViewById(R.id.elvMuelltrennung);
		etSearchMuelltrennung = (EditText) findViewById(R.id.etSearchMuelltrennung);

		etSearchMuelltrennung.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				MuelltrennungActivity.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				return;

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				return;
			}
		});

		new AsyncParseLoader().execute();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private class AsyncParseLoader extends
			AsyncTask<Void, Integer, ArrayList<AdapterGroupItem>> {

		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MuelltrennungActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Daten werden geladen");
			progressDialog.setMessage("Bitte warten...");
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		protected ArrayList<AdapterGroupItem> doInBackground(Void... params) {

			try {
				ArrayList<AdapterGroupItem> data = DAO
						.getAlleGegenstaendeFuerExpandableAdapter();

				return data;
			}

			catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(),
						getString(R.string.fehler_beim_laden),
						Toast.LENGTH_LONG).show();

				return new ArrayList<AdapterGroupItem>();
			}

		}

		protected void onPostExecute(ArrayList<AdapterGroupItem> result) {
			if (progressDialog.isShowing())
				progressDialog.cancel();

			try {
				if (result != null) {
					adapter = new CustomMuelltrennungExpandableAdapter(
							getBaseContext(), result);

					elvMuelltrennung.setAdapter(adapter);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
