package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomHauptmenueAdapter;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	ListView lvMenu;
	ArrayList<AdapterSingleItem> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvMenu = (ListView) findViewById(R.id.lvMenu);
		data = DAO.getHauptmenueEintraege();
		setList();

		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {

					NetworkCondition netzwerkStatus = MuellGuideMsApplication
							.getNetzwerkStatus();
					if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
						MainActivity.this.showToastIfNecessary(netzwerkStatus);
						return;
					}

					String selectedItemId = data.get(position).getId();
					Intent intent = null;

					if (selectedItemId.equals("muelltrennung")) {
						// Mülltrennung aufrufen:
						intent = new Intent(getBaseContext(),
								MuelltrennungActivity.class);
					} else if (selectedItemId.equals("entsorgung")) {
						// Entsorgung-Activity aufrufen:
						intent = new Intent(getBaseContext(),
								EntsorgungActivity.class);
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
				} catch (Exception ex) {
					Toast.makeText(MainActivity.this, ex.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void setList() {

		CustomHauptmenueAdapter adapter = new CustomHauptmenueAdapter(
				getBaseContext(), data);
		lvMenu.setAdapter(adapter);

	}
}
