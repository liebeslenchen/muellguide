package mawi.muellguidems.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickBtnParseTest(View view) {
		Intent intentParseTest = new Intent(this, ParseTestActivity.class);
		startActivity(intentParseTest);
	}

	public void onClickBtnMaps(View view) {
		Intent mapsIntent = new Intent(this, MapsActivity.class);
		// mapsIntent.putExtra("objectId", "QZY4KqAckH");
		// mapsIntent.putExtra("entsorgungsartId", "QPFaiU38rg");
		startActivity(mapsIntent);
	}
}
