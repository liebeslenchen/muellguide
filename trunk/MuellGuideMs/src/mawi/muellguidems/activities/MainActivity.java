package mawi.muellguidems.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView lvMenu;
	ArrayList<HashMap<String, String>> dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setList();

		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {
					String strId = dataSource.get(position).get("id");
					String value = dataSource.get(position).get("listItem");

					Intent intent = null;

					switch (position) {
					case 0:
						// Mülltrennung aufrufen:
						intent = new Intent(getBaseContext(),
								MuelltrennungActivity.class);
						break;
					case 1:
						// Entsorgung-Activity aufrufen:
						intent = new Intent(getBaseContext(),
								EntsorgungActivity.class);
						break;
					case 2:
						// Feedback-Activity aufrufen:
						intent = new Intent(getBaseContext(),
								FeedbackActivity.class);
						break;
					case 3:
						break;
					case 4:
						// Testwiese aufrufen:
						intent = new Intent(getBaseContext(),
								TestActivity.class);
						break;
					default:
						break;
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
		lvMenu = (ListView) findViewById(R.id.lvMenu);

		dataSource = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("hauptmenue_item", "Mülltrennung");
		item.put("hauptmenue_subitem", "Was gehört in welche Tonne?");

		dataSource.add(item);

		item = new HashMap<String, String>();
		item.put("hauptmenue_item", "Entsorgung");
		item.put("hauptmenue_subitem", "Wo werde ich meinen Müll los?");

		dataSource.add(item);

		item = new HashMap<String, String>();
		item.put("hauptmenue_item", "Feedback");
		item.put("hauptmenue_subitem", "Änderungen melden, Wünsche äußern...");

		dataSource.add(item);

		item = new HashMap<String, String>();
		item.put("hauptmenue_item", "Hilfe");
		item.put("hauptmenue_subitem", "Symbolerkärung usw.");

		dataSource.add(item);

		item = new HashMap<String, String>();
		item.put("hauptmenue_item", "Testwiese");
		item.put("hauptmenue_subitem", "Testen und so...");

		dataSource.add(item);

		SimpleAdapter adapter = new SimpleAdapter(this, dataSource,
				R.layout.hauptmenue_item, new String[] { "hauptmenue_item",
						"hauptmenue_subitem" }, new int[] {
						R.id.tvHauptmenueItem, R.id.tvHauptmenueSubItem });
		lvMenu.setAdapter(adapter);

	}
}
