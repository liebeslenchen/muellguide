package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomHauptmenueAdapter;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	ListView lvMenu;
	ArrayList<AdapterSingleItem> data;

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle("Daten werden geladen");
		progressDialog.setMessage("Bitte warten...");
		progressDialog.setIndeterminate(true);

		lvMenu = (ListView) findViewById(R.id.lvMenu);

		setList();

		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {

					if (data != null) {

						NetworkCondition netzwerkStatus = MuellGuideMsApplication
								.getNetzwerkStatus();

						String selectedItemId = data.get(position).getId();
						Intent intent = null;

						if (selectedItemId.equals("muelltrennung")) {

							if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
								MainActivity.this
										.showToastIfNecessary(netzwerkStatus);
								return;
							}

							// Mülltrennung aufrufen:

							intent = new Intent(getBaseContext(),
									MuelltrennungActivity.class);

						} else if (selectedItemId.equals("entsorgung")) {

							if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
								MainActivity.this
										.showToastIfNecessary(netzwerkStatus);
								return;
							}

							// Entsorgung-Activity aufrufen:
							intent = new Intent(getBaseContext(),
									EntsorgungActivity.class);

							progressDialog.show();

						} else if (selectedItemId.equals("hilfe")) {

						} else if (selectedItemId.equals("feedback")) {
							// Feedback-Activity aufrufen:
							intent = new Intent(getBaseContext(),
									FeedbackActivity.class);
						} else if (selectedItemId.equals("test")) {
							// Testwiese aufrufen:
							intent = new Intent(getBaseContext(),
									TestActivity.class);
						}

						startActivity(intent);
					}

				} catch (Exception ex) {
					Toast.makeText(MainActivity.this, ex.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (progressDialog.isShowing())
			progressDialog.cancel();
	}

	public void setList() {

		try {
			data = DAO.getHauptmenueEintraege();

			if (data != null) {
				CustomHauptmenueAdapter adapter = new CustomHauptmenueAdapter(
						getBaseContext(), data);
				lvMenu.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(),
					getString(R.string.fehler_beim_laden), Toast.LENGTH_LONG)
					.show();
		}

	}

	private class AsyncIntentStarter extends AsyncTask<Class<?>, Void, Void> {

		@Override
		protected Void doInBackground(Class<?>... params) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(getBaseContext(), params[0]);
			startActivity(intent);
			return null;
		}
	}
}
